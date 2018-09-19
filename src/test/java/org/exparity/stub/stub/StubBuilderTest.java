package org.exparity.stub.stub;

import static org.exparity.beans.Bean.bean;
import static org.exparity.stub.random.RandomBuilder.aRandomInstanceOf;
import static org.exparity.stub.random.RandomBuilder.aRandomString;
import static org.exparity.stub.stub.StubBuilder.aRandomStubOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.exparity.beans.core.BeanProperty;
import org.exparity.beans.core.BeanVisitor;
import org.exparity.stub.testutils.type.AllTypes;
import org.exparity.stub.testutils.type.Car;
import org.exparity.stub.testutils.type.Circle;
import org.exparity.stub.testutils.type.CollectionOfGenerics;
import org.exparity.stub.testutils.type.ConstructorOnlyNested;
import org.exparity.stub.testutils.type.Employee;
import org.exparity.stub.testutils.type.GenericType;
import org.exparity.stub.testutils.type.Manager;
import org.exparity.stub.testutils.type.NoDefaultConstructor;
import org.exparity.stub.testutils.type.OverrideHashcodeEquals;
import org.exparity.stub.testutils.type.Person;
import org.exparity.stub.testutils.type.PrivateConstructor;
import org.exparity.stub.testutils.type.Service;
import org.exparity.stub.testutils.type.Shape;
import org.exparity.stub.testutils.type.ShapeSorter;
import org.exparity.stub.testutils.type.Square;
import org.exparity.stub.testutils.type.Wheel;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Stewart.Bissett
 */
public class StubBuilderTest {

    @Test
    public void canRandomlyFillAGraph() {
        Car car = aRandomStubOf(Car.class).build();
        assertThat(car.getEngine(), Matchers.notNullValue());
        assertThat(car.getEngine().getCapacity(), Matchers.notNullValue());
        assertThat(car.getWheels().size(), Matchers.greaterThan(0));
        assertThat(car.getWheels().get(0).getDiameter(), Matchers.notNullValue());
    }

    @Test
    public void canRandomlyFillASimpleObject() {
        AllTypes allTypes = aRandomStubOf(AllTypes.class).build();
        bean(allTypes).visit(new BeanVisitor() {

            @Override
            public void visit(final BeanProperty property,
                    final Object current,
                    final Object[] stack,
                    final AtomicBoolean stop) {
                assertThat("Expected " + property + " to not be null", property.getValue(), notNullValue());
            }
        });
    }

    @Test
    public void canSetAnOverrideTypeFactory() {
        final Integer overrideValue = 12345;
        Car car = aRandomStubOf(Car.class).with(Wheel.class, () -> new Wheel(overrideValue)).build();
        for (Wheel wheel : car.getWheels()) {
            assertThat(wheel.getDiameter(), equalTo(overrideValue));
        }
    }

    @Test
    public void canSetCollectionSize() {
        int expectedSize = 1;
        Car car = aRandomStubOf(Car.class).collectionSizeOf(expectedSize).build();
        assertThat(car.getWheels(), hasSize(expectedSize));
    }

    @Test
    public void canSetSubTypes() {
        Employee employee = aRandomStubOf(Employee.class).subtype(Person.class, Manager.class).build();
        assertThat(employee.getManager(), instanceOf(Manager.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canSetOneOrMoreSubTypes() {
        ShapeSorter shapeSorter = aRandomStubOf(ShapeSorter.class).subtype(Shape.class, Square.class, Circle.class)
                .build();
        assertThat(shapeSorter.getShape(), anyOf(instanceOf(Square.class), instanceOf(Circle.class)));
    }

    @Test
    public void canCreateAnInstanceOfAClassWithNoDefaultConstructor() {
        NoDefaultConstructor instance = aRandomStubOf(NoDefaultConstructor.class).build();
        assertThat(instance.getValue(), notNullValue());
    }

    @Test
    @Ignore
    public void canCreateAnInstanceWithPrivateConstructor() {
        PrivateConstructor instance = aRandomStubOf(PrivateConstructor.class).build();
        assertThat(instance.getValue(), notNullValue());
    }

    @Test
    public void canCreateAnInstanceOfAnInterface() {
        Shape instance = aRandomStubOf(Shape.class).build();
        assertThat(instance.numberOfSides(), notNullValue());
    }

    @Test
    public void canCreateAnInstanceOfAServiceInterface() {
        Service service = aRandomStubOf(Service.class).build();
        assertThat(service.getCarByMake(aRandomString()), not(empty()));
        assertThat(service.createCar(), instanceOf(Car.class));
        assertThat(service.createConstructorOnlyNested(), instanceOf(ConstructorOnlyNested.class));
        service.addCar(aRandomInstanceOf(Car.class)); // Check invoking void method
    }

    @Test
    public void canCreateAnInstanceWithGenericTypes() {
        CollectionOfGenerics instance = aRandomStubOf(CollectionOfGenerics.class).build();
        assertThat(instance.getValues(), not(empty()));
    }

    @Test
    public void canCreateAnInstanceWithOverridenHashcodeEquals() {
        OverrideHashcodeEquals instance = aRandomStubOf(OverrideHashcodeEquals.class).build();
        assertThat(instance.getValue(), instanceOf(String.class));
    }

    @Test
    public void canUseStubInASet() {
        OverrideHashcodeEquals instance = aRandomStubOf(OverrideHashcodeEquals.class).build();
        Set<OverrideHashcodeEquals> instances = new HashSet<>();
        instances.add(instance);
        instances.add(instance);
        assertThat(instances, hasSize(1));
        assertThat(instances, hasItem(instance));
    }

    @Test
    public void canUseStubInAMap() {
        OverrideHashcodeEquals instance = aRandomStubOf(OverrideHashcodeEquals.class).build();
        Map<String, OverrideHashcodeEquals> instances = new HashMap<>();
        instances.put(instance.getValue(), instance);
        instances.put(instance.getValue(), instance);
        assertThat(instances, hasKey(instance.getValue()));
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = IllegalArgumentException.class)
    public void canFailToCreateAnInstanceOfAGenericType() {
        GenericType instance = aRandomStubOf(GenericType.class).build();
        assertThat(instance.getValue(), notNullValue());
    }

    @Test(expected = FinalClassException.class)
    public void canFailToCreateAnInstanceOfAFinalType() {
        String instance = aRandomStubOf(String.class).build();
        assertThat(instance.toString(), notNullValue());
    }

    @Test
    public void canCreateAnInstanceOfAGenericTypeUsingTypeReference() {
        GenericType<String> instance = aRandomStubOf(new TypeReference<GenericType<String>>() {}).build();
        assertThat(instance.getValue(), notNullValue());
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = IllegalArgumentException.class)
    public void canFailIfTypeNotSuppliedToTypeReference() {
        GenericType instance = aRandomStubOf(new TypeReference<GenericType>() {}).build();
        assertThat(instance.getValue(), notNullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canFailIfNonGenericTypeSuppliedToTypeReference() {
        String instance = aRandomStubOf(new TypeReference<String>() {}).build();
        assertThat(instance.toString(), notNullValue());
    }

}
