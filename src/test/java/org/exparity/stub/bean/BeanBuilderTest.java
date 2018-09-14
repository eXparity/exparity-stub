package org.exparity.stub.bean;

import static org.exparity.beans.Bean.bean;
import static org.exparity.stub.bean.BeanBuilder.aRandomInstanceOf;
import static org.exparity.stub.bean.BeanBuilder.anEmptyInstanceOf;
import static org.exparity.stub.bean.BeanBuilder.anInstanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import org.exparity.beans.core.BeanProperty;
import org.exparity.beans.core.BeanPropertyException;
import org.exparity.beans.core.BeanVisitor;
import org.exparity.stub.core.NoDefaultConstructorException;
import org.exparity.stub.core.ValueFactory;
import org.exparity.stub.testutils.type.AllTypes;
import org.exparity.stub.testutils.type.Car;
import org.exparity.stub.testutils.type.Circle;
import org.exparity.stub.testutils.type.Employee;
import org.exparity.stub.testutils.type.Engine;
import org.exparity.stub.testutils.type.Manager;
import org.exparity.stub.testutils.type.NoDefaultConstructor;
import org.exparity.stub.testutils.type.Person;
import org.exparity.stub.testutils.type.Shape;
import org.exparity.stub.testutils.type.ShapeSorter;
import org.exparity.stub.testutils.type.Square;
import org.exparity.stub.testutils.type.Wheel;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * @author Stewart.Bissett
 */
public class BeanBuilderTest {

	@Test
	public void canRandomlyFillAGraph() {
		Car car = aRandomInstanceOf(Car.class).build();
		assertThat(car.getEngine(), Matchers.notNullValue());
		assertThat(car.getEngine().getCapacity(), Matchers.notNullValue());
		assertThat(car.getWheels().size(), Matchers.greaterThan(0));
		assertThat(car.getWheels().get(0).getDiameter(), Matchers.notNullValue());
	}

	@Test
	public void canFillARandomGraphAndNameRoot() {
		BigDecimal capacity = new BigDecimal(2.4);
		Car car = aRandomInstanceOf(Car.class, "instance").path("instance.engine.capacity", capacity).build();
		assertThat(car.getEngine().getCapacity(), Matchers.equalTo(capacity));
	}

	@Test
	public void canRandomlyFillASimpleObject() {
		AllTypes allTypes = aRandomInstanceOf(AllTypes.class).build();
		bean(allTypes).visit(new BeanVisitor() {

			@Override
            public void visit(final BeanProperty property, final Object current, final Object[] stack, final AtomicBoolean stop) {
				assertThat("Expected " + property + " to not be null", property.getValue(), notNullValue());
			}
		});
	}

	@Test
	public void canCreateAnNullSimpleObject() {
		AllTypes allTypes = anInstanceOf(AllTypes.class).build();
		bean(allTypes).visit(new BeanVisitor() {

			@Override
            public void visit(final BeanProperty property, final Object current, final Object[] stack, final AtomicBoolean stop) {
				if (!property.isPrimitive()) {
					assertThat("Expected " + property + " to not be null", property.getValue(), nullValue());
				}
			}
		});
	}

	@Test
	public void canCreateAnEmptySimpleObject() {
		AllTypes allTypes = anEmptyInstanceOf(AllTypes.class).build();
		bean(allTypes).visit(new BeanVisitor() {

			@Override
            public void visit(final BeanProperty property, final Object current, final Object[] stack, final AtomicBoolean stop) {
				if (!property.isCollection() && !property.isMap() && !property.isPrimitive() && !property.isArray() && !property.isEnum()) {
					assertThat("Expected " + property + " to not be null", property.getValue(), nullValue());
				}
			}
		});
	}

	@Test
	public void canCreateAnEmptyGraph() {
		Car car = anEmptyInstanceOf(Car.class).build();
		assertThat(car.getEngine().getCapacity(), Matchers.nullValue());
		assertThat(car.getWheels().size(), Matchers.greaterThan(0));
		assertThat(car.getWheels().get(0).getDiameter(), Matchers.nullValue());
	}

	@Test
	public void canCreateAnEmptyGraphAndNameRoot() {
		BigDecimal capacity = new BigDecimal(2.4);
		Car car = anEmptyInstanceOf(Car.class, "instance").path("instance.engine.capacity", capacity).build();
		assertThat(car.getEngine().getCapacity(), Matchers.equalTo(capacity));
	}

	@Test
	public void canSetAnOverrideProperty() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("capacity", overrideValue).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test
	public void canSetAnOverridePropertyWithMixedCase() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("Capacity", overrideValue).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test(expected = BeanPropertyException.class)
	public void canSetAnOverridePropertyIncorrectly() {
		aRandomInstanceOf(Car.class).with("capacity", 1234L).build();
	}

	@Test
	public void canSetAnOverridePropertyFactory() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("engine", new ValueFactory<Engine>() {

			@Override
            public Engine createValue() {
				return new Engine(new BigDecimal("4.0"));
			}
		}).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test
	public void canSetAnOverrideTypeFactory() {
		final Integer overrideValue = 12345;
		Car car = aRandomInstanceOf(Car.class).with(Wheel.class, new ValueFactory<Wheel>() {

			@Override
            public Wheel createValue() {
				return new Wheel(overrideValue);
			}
		}).build();
		for (Wheel wheel : car.getWheels()) {
			assertThat(wheel.getDiameter(), equalTo(overrideValue));
		}
	}

	@Test
	public void canSetAnOverridePropertyOnOverride() {
		BigDecimal capacity = new BigDecimal("4.0");
		Engine engine = aRandomInstanceOf(Engine.class).build();
		Car car = aRandomInstanceOf(Car.class).with("engine", engine).with("capacity", capacity).build();
		assertThat(car.getEngine(), theInstance(engine));
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(capacity));
	}

	@Test
	public void canSetAnOverridePropertyShortForm() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("capacity", overrideValue).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test
	public void canRandomlyFillAGraphWithoutOverflow() {
		Person person = aRandomInstanceOf(Person.class).collectionSizeOf(1).build();
		assertThat(person.getFirstname(), notNullValue());
		assertThat(person.getSurname(), notNullValue());
		assertThat(person.getSiblings(), hasSize(1));
		assertThat(person.getSiblings().get(0).getFirstname(), notNullValue());
		assertThat(person.getSiblings().get(0).getSurname(), notNullValue());
		assertThat(person.getSiblings().get(0).getSiblings(), nullValue());
	}

	@Test
	public void canSetAnOverridePropertyByPath() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("car.engine.capacity", overrideValue).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test
	public void canSetAnOverridePropertyByPathWithMixedCase() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("car.EngIne.CAPacity", overrideValue).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test
	public void canSetAnOverridePropertyByIndexedPath() {
		int overrideDiameter = 1234;
		Car car = aRandomInstanceOf(Car.class).collectionSizeOf(4).with("car.wheels[1].diameter", overrideDiameter).build();
		assertThat(car.getWheels().get(1).getDiameter(), equalTo(overrideDiameter));
	}

	@Test
	public void canSetAnOverridePropertyByIndexedPathUsingMixedCase() {
		int overrideDiameter = 1234;
		Car car = aRandomInstanceOf(Car.class).collectionSizeOf(4).with("CAR.wHeels[1].diAmeter", overrideDiameter).build();
		assertThat(car.getWheels().get(1).getDiameter(), equalTo(overrideDiameter));
	}

	@Test
	public void canSetAnPropertyByPathShortForm() {
		BigDecimal overrideValue = new BigDecimal("4.0");
		Car car = aRandomInstanceOf(Car.class).with("car.engine.capacity", overrideValue).build();
		assertThat(car.getEngine().getCapacity(), comparesEqualTo(overrideValue));
	}

	@Test
	public void canExcludeAProperty() {
		Car car = aRandomInstanceOf(Car.class).excludeProperty("capacity").build();
		assertThat(car.getEngine().getCapacity(), nullValue());
	}

	@Test
	public void canExcludeAPropertyUsingMixedCase() {
		Car car = aRandomInstanceOf(Car.class).excludeProperty("Capacity").build();
		assertThat(car.getEngine().getCapacity(), nullValue());
	}

	@Test
	public void canExcludeAPath() {
		Car car = aRandomInstanceOf(Car.class).excludePath("car.engine.capacity").build();
		assertThat(car.getEngine().getCapacity(), nullValue());
	}

	@Test
	public void canExcludeAPathUseMixedCase() {
		Car car = aRandomInstanceOf(Car.class).excludePath("CaR.EnGine.capacity").build();
		assertThat(car.getEngine().getCapacity(), nullValue());
	}

	@Test
	public void canSetCollectionSize() {
		int expectedSize = 1;
		Car car = aRandomInstanceOf(Car.class).collectionSizeOf(expectedSize).build();
		assertThat(car.getWheels(), hasSize(expectedSize));
	}

	@Test
	public void canSetSubTypes() {
		Employee employee = aRandomInstanceOf(Employee.class).subtype(Person.class, Manager.class).build();
		assertThat(employee.getManager(), instanceOf(Manager.class));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void canSetOneOrMoreSubTypes() {
		ShapeSorter shapeSorter = aRandomInstanceOf(ShapeSorter.class).subtype(Shape.class, Square.class, Circle.class).build();
		assertThat(shapeSorter.getShape(), anyOf(instanceOf(Square.class), instanceOf(Circle.class)));
	}

	@Test(expected = NoDefaultConstructorException.class)
	public void canNotCreateAnInstanceWithNoDefaultConstructor() {
		aRandomInstanceOf(NoDefaultConstructor.class).build();
	}

	@Test
	public void canCreateBigDecimalsWithAPrecisionLessThan10() {
		Engine engine = aRandomInstanceOf(Engine.class).build();
		assertThat(engine.getCapacity().precision(), lessThanOrEqualTo(10));
	}
}
