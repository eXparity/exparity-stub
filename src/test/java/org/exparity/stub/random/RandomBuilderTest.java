package org.exparity.stub.random;

import static org.exparity.stub.random.RandomBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.exparity.stub.core.ValueFactories;
import org.exparity.stub.random.RandomBuilder.RandomRestriction;
import org.exparity.stub.testutils.type.AllTypes;
import org.exparity.stub.testutils.type.Car;
import org.exparity.stub.testutils.type.Circle;
import org.exparity.stub.testutils.type.CollectionOfGenerics;
import org.exparity.stub.testutils.type.Employee;
import org.exparity.stub.testutils.type.EmptyEnum;
import org.exparity.stub.testutils.type.Engine;
import org.exparity.stub.testutils.type.FuelType;
import org.exparity.stub.testutils.type.Immutable;
import org.exparity.stub.testutils.type.Manager;
import org.exparity.stub.testutils.type.NoDefaultConstructor;
import org.exparity.stub.testutils.type.Person;
import org.exparity.stub.testutils.type.Shape;
import org.exparity.stub.testutils.type.ShapeSorter;
import org.exparity.stub.testutils.type.Square;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit tests for {@link RandomBuilder}
 *
 * @author Stewart Bissett
 */
public class RandomBuilderTest {

    @Test
    public void canBuildARandomString() {
        assertThat(aRandomString(), isA(String.class));
    }

    @Test
    public void canBuildARandomStringOfSpecifiedSize() {
        Integer randomSize = aRandomInteger(3, 100);
        assertThat(aRandomString(randomSize).length(), equalTo(randomSize));
    }

    @Test
    public void canBuildARandomInteger() {
        assertThat(aRandomInteger(), isA(Integer.class));
    }

    @Test
    public void canBuildARandomIntegerWithinRange() {
        assertThat(aRandomInteger(1, 5), isOneOf(1, 2, 3, 4, 5));
    }

    @Test
    public void canBuildARandomShort() {
        assertThat(aRandomShort(), isA(Short.class));
    }

    @Test
    public void canBuildARandomShortWithinRange() {
        assertThat(aRandomShort((short) 1, (short) 5), isOneOf((short) 1, (short) 2, (short) 3, (short) 4, (short) 5));
    }

    @Test
    public void canBuildARandomShortWithSize() {
        assertThat(aRandomShort((short) 3, (short) 3), equalTo((short) 3));
    }

    @Test
    public void canBuildARandomLong() {
        assertThat(aRandomLong(), isA(Long.class));
    }

    @Test
    public void canBuildARandomLongWithinRange() {
        assertThat(aRandomLong(1, 5), isOneOf(1L, 2L, 3L, 4L, 5L));
    }

    @Test
    public void canBuildARandomLongWithSize() {
        assertThat(aRandomLong(7, 7), equalTo(7L));
    }

    @Test
    public void canBuildARandomDouble() {
        assertThat(aRandomDouble(), isA(Double.class));
    }

    @Test
    public void canBuildARandomFloat() {
        assertThat(aRandomFloat(), isA(Float.class));
    }

    @Test
    public void canBuildARandomBoolean() {
        assertThat(aRandomBoolean(), isOneOf(Boolean.TRUE, Boolean.FALSE));
    }

    @Test
    public void canBuildARandomDate() {
        assertThat(aRandomDate(), isA(Date.class));
    }

    @Test
    public void canBuildARandomLocalDate() {
        assertThat(aRandomLocalDate(), isA(LocalDate.class));
    }

    @Test
    public void canBuildARandomLocalDateTime() {
        assertThat(aRandomLocalDateTime(), isA(LocalDateTime.class));
    }

    @Test
    public void canBuildARandomZonedDateTime() {
        assertThat(aRandomZonedDateTime(), isA(ZonedDateTime.class));
    }

    @Test
    public void canBuildARandomInstant() {
        assertThat(aRandomInstant(), isA(Instant.class));
    }

    @Test
    public void canBuildARandomDecimal() {
        assertThat(aRandomDecimal(), isA(BigDecimal.class));
    }

    @Test
    public void canBuildARandomByte() {
        assertThat(aRandomByte(), isA(Byte.class));
    }

    @Test
    public void canBuildARandomByteArray() {
        assertThat(aRandomByteArray(), isA(byte[].class));
    }

    @Test
    public void canBuildARandomChar() {
        assertThat(aRandomChar(), isA(Character.class));
    }

    @Test
    public void canBuildARandomEnum() {
        assertThat(aRandomEnum(FuelType.class), isOneOf(FuelType.DIESEL, FuelType.PETROL, FuelType.LPG));
    }

    @Test(expected = RandomBuilderException.class)
    public void canFailToBuildARandomEmptyEnum() {
        aRandomEnum(EmptyEnum.class);
    }

    @Test
    public void canBuildARandomArrayOfEnums() {
        assertThat(aRandomArrayOfEnum(FuelType.class),
                hasItemInArray(isOneOf(FuelType.DIESEL, FuelType.PETROL, FuelType.LPG)));
    }

    @Test
    public void canBuildARandomArrayOfEnumsOfSize() {
        FuelType[] array = aRandomArrayOfEnum(FuelType.class, 1, 5);
        assertThat(array, hasItemInArray(isOneOf(FuelType.DIESEL, FuelType.PETROL, FuelType.LPG)));
        assertThat(array.length, lessThanOrEqualTo(5));
        assertThat(array.length, greaterThanOrEqualTo(1));
    }

    @Test
    public void canBuildARandomArrayOfObjects() {
        assertThat(aRandomArrayOf(AllTypes.class), hasItemInArray(instanceOf(AllTypes.class)));
    }

    @Test
    public void canBuildARandomArrayOfObjectsOfSize() {
        AllTypes[] array = aRandomArrayOf(AllTypes.class, 1, 5);
        assertThat(array, hasItemInArray(instanceOf(AllTypes.class)));
        assertThat(array.length, lessThanOrEqualTo(5));
        assertThat(array.length, greaterThanOrEqualTo(1));
    }

    @Test
    public void canBuildARandomArrayOfObjectsOfExactSize() {
        assertThat(aRandomArrayOf(AllTypes.class, 3, 3), arrayWithSize(3));
    }

    @Test
    public void canBuildARandomCollectionOfObjects() {
        assertThat(aRandomCollectionOf(AllTypes.class), hasItem(any(AllTypes.class)));
    }

    @Test
    public void canBuildARandomCollectionOfObjectsOfSize() {
        Collection<AllTypes> collection = aRandomCollectionOf(AllTypes.class, 1, 5);
        assertThat(collection, hasItem(any(AllTypes.class)));
        assertThat(collection.size(), lessThanOrEqualTo(5));
        assertThat(collection.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void canBuildARandomCollectionOfObjectsOfExactSize() {
        assertThat(aRandomCollectionOf(AllTypes.class, 3, 3), hasSize(3));
    }

    @Test
    public void canBuildARandomListOfObjects() {
        assertThat(aRandomListOf(AllTypes.class), hasItem(any(AllTypes.class)));
    }

    @Test
    public void canBuildARandomListOfObjectsOfSize() {
        List<AllTypes> collection = aRandomListOf(AllTypes.class, 1, 5);
        assertThat(collection, hasItem(any(AllTypes.class)));
        assertThat(collection.size(), lessThanOrEqualTo(5));
        assertThat(collection.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void canBuildARandomListOfObjectsOfExactSize() {
        assertThat(aRandomListOf(AllTypes.class, 3, 3), hasSize(3));
    }

    @Test
    public void canBuildARandomInstanceOf() {
        assertThat(aRandomInstanceOf(AllTypes.class), any(AllTypes.class));
    }

    @Test
    public void canBuildARandomInstanceWithNoDefaultConstructor() {
        NoDefaultConstructor instance = aRandomInstanceOf(NoDefaultConstructor.class);
        assertThat(instance, any(NoDefaultConstructor.class));
        assertThat(instance.getValue(), notNullValue());
    }

    @Test
    public void canBuildARandomInstanceOfImmutableType() {
        Immutable instance = aRandomInstanceOf(Immutable.class);
        assertThat(instance, any(Immutable.class));
        assertThat(instance.getValue(), notNullValue());
    }

    @Test
    public void canBuildARandomInstanceAndSetProperty() {
        BigDecimal capacity = new BigDecimal("1.8");
        Car random = aRandomInstanceOf(Car.class, property("capacity", capacity));
        assertThat(random.getEngine().getCapacity(), Matchers.equalTo(capacity));
    }

    @Test
    public void canBuildARandomInstanceAndSetPropertyFromFactory() {
        BigDecimal option1 = new BigDecimal("1.8"), option2 = new BigDecimal("3.6");
        Car random = aRandomInstanceOf(Car.class, property("capacity", oneOf(option1, option2)));
        assertThat(random.getEngine().getCapacity(), Matchers.anyOf(equalTo(option1), equalTo(option2)));
    }

    @Test
    public void canBuildARandomInstanceAndSetPropertyMixedCase() {
        BigDecimal capacity = new BigDecimal("1.8");
        assertThat(aRandomInstanceOf(Car.class, property("Capacity", capacity)).getEngine().getCapacity(),
                Matchers.equalTo(capacity));
    }

    @Test
    public void canBuildARandomInstanceAndExcludeProperty() {
        assertThat(aRandomInstanceOf(Car.class, excludeProperty("capacity")).getEngine().getCapacity(), nullValue());
    }

    @Test
    public void canBuildARandomInstanceAndExcludePropertyMixedCase() {
        assertThat(aRandomInstanceOf(Car.class, excludeProperty("Capacity")).getEngine().getCapacity(), nullValue());
    }

    @Test
    public void canBuildARandomInstanceAndSetPath() {
        BigDecimal capacity = new BigDecimal("1.8");
        Car random = aRandomInstanceOf(Car.class, path("car.engine.capacity", capacity));
        assertThat(random.getEngine().getCapacity(), equalTo(capacity));
    }

    @Test
    public void canBuildARandomInstanceAndSetPathFromFactory() {
        BigDecimal option1 = new BigDecimal("1.8"), option2 = new BigDecimal("3.6");
        Car random = aRandomInstanceOf(Car.class, path("car.engine.capacity", oneOf(option1, option2)));
        assertThat(random.getEngine().getCapacity(), anyOf(equalTo(option1), equalTo(option2)));
    }

    @Test
    public void canBuildARandomInstanceAndSetPathMixedCase() {
        BigDecimal capacity = new BigDecimal("1.8");
        assertThat(aRandomInstanceOf(Car.class, path("Car.Engine.Capacity", capacity)).getEngine().getCapacity(),
                Matchers.equalTo(capacity));
    }

    @Test
    public void canBuildARandomInstanceAndExcludePath() {
        assertThat(aRandomInstanceOf(Car.class, excludePath("car.engine.capacity")).getEngine().getCapacity(),
                nullValue());
    }

    @Test
    public void canBuildARandomInstanceAndExcludePathMixedCase() {
        assertThat(aRandomInstanceOf(Car.class, excludePath("Car.Engine.Capacity")).getEngine().getCapacity(),
                nullValue());
    }

    @Test
    public void canBuildARandomInstanceSpecifySubType() {
        assertThat(aRandomInstanceOf(Employee.class, subtype(Person.class, Manager.class)).getManager(),
                instanceOf(Manager.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void canBuildARandomInstanceSpecifyTwoOrMoreSubType() {
        ShapeSorter shapeSorter = aRandomInstanceOf(ShapeSorter.class,
                subtype(Shape.class, Square.class, Circle.class));
        assertThat(shapeSorter.getShape(), anyOf(instanceOf(Square.class), instanceOf(Circle.class)));
    }

    @Test
    public void canBuildARandomInstanceAndAssignCollectionSize() {
        int size = aRandomInteger(2, 10);
        Manager manager = aRandomInstanceOf(Manager.class, collectionSize(size));
        assertThat(manager.getSiblings(), Matchers.hasSize(size));
        assertThat(manager.getEmployees(), Matchers.hasSize(size));
    }

    @Test
    public void canBuildARandomInstanceAndLimitCollectionSize() {
        int min = aRandomInteger(4, 7), max = aRandomInteger(8, 12);
        Manager manager = aRandomInstanceOf(Manager.class, collectionSize(min, max));
        assertThat(manager.getSiblings().size(), greaterThanOrEqualTo(min));
        assertThat(manager.getSiblings().size(), lessThanOrEqualTo(max));
        assertThat(manager.getEmployees().size(), greaterThanOrEqualTo(min));
        assertThat(manager.getEmployees().size(), lessThanOrEqualTo(max));
    }

    @Test
    public void canBuildARandomInstanceAndAssignCollectionSizeForPath() {
        int size = aRandomInteger(2, 10);
        Manager manager = aRandomInstanceOf(Manager.class, collectionSizeForPath("manager.employees", size));
        assertThat(manager.getEmployees(), Matchers.hasSize(size));
    }

    @Test
    public void canBuildARandomInstanceAndLimitCollectionSizeForPath() {
        int min = aRandomInteger(4, 7), max = aRandomInteger(8, 12);
        Manager manager = aRandomInstanceOf(Manager.class, collectionSizeForPath("manager.employees", min, max));
        assertThat(manager.getEmployees().size(), greaterThanOrEqualTo(min));
        assertThat(manager.getEmployees().size(), lessThanOrEqualTo(max));
    }

    @Test
    public void canBuildARandomInstanceAndAssignCollectionSizeForProperty() {
        int size = aRandomInteger(2, 10);
        Manager manager = aRandomInstanceOf(Manager.class, collectionSizeForProperty("employees", size));
        assertThat(manager.getEmployees(), Matchers.hasSize(size));
    }

    @Test
    public void canBuildARandomInstanceAndLimitCollectionSizeForProperty() {
        int min = aRandomInteger(4, 7), max = aRandomInteger(8, 12);
        Manager manager = aRandomInstanceOf(Manager.class, collectionSizeForProperty("employees", min, max));
        assertThat(manager.getEmployees().size(), greaterThanOrEqualTo(min));
        assertThat(manager.getEmployees().size(), lessThanOrEqualTo(max));
    }

    @Test
    public void canBuildARandomInstanceAndSetPathWithIndex() {
        Integer diameter = aRandomInteger();
        Car car = aRandomInstanceOf(Car.class,
                collectionSizeForPath("car.wheels", 4),
                path("car.wheels[3].diameter", diameter));
        assertThat(car.getWheels().get(3).getDiameter(), equalTo(diameter));
    }

    @Test
    public void canBuildARandomInstanceAndLimitCollectionSizeForPathWithIndex() {
        int nuts = aRandomInteger(6, 10);
        Car car = aRandomInstanceOf(Car.class,
                collectionSizeForPath("car.wheels", 4),
                collectionSizeForPath("car.wheels[2].nuts", nuts));
        assertThat(car.getWheels().get(2).getNuts(), hasSize(nuts));
    }

    @Test
    public void canBuildARandomInstanceAndUseFactoryForType() {
        Engine engine = new Engine(new BigDecimal("4.0"));
        Car car = aRandomInstanceOf(Car.class, factory(Engine.class, ValueFactories.theValue(engine)));
        assertThat(car.getEngine(), equalTo(engine));
    }

    @Test
    public void canBuildARandomInstanceAndUseLamdbaFactory() {
        Engine engine = new Engine(new BigDecimal("4.0"));
        Car car = aRandomInstanceOf(Car.class, factory(Engine.class, () -> engine));
        assertThat(car.getEngine(), equalTo(engine));
    }

    @Test
    public void canBuildARandomInstanceWithAListOfRestrictions() {
        int nuts = aRandomInteger(6, 10);
        List<RandomRestriction> restrictions = Arrays.asList(collectionSizeForPath("car.wheels", 4),
                collectionSizeForPath("car.wheels[2].nuts", nuts));
        Car car = aRandomInstanceOf(Car.class, restrictions);
        assertThat(car.getWheels().get(2).getNuts(), hasSize(nuts));
    }

    @Test
    public void canReturnOneOfARangeOfItems() {
        String value1 = aRandomString(), value2 = value1 + aRandomString();
        Set<String> usedNames = new HashSet<>();
        for (int i = 0; i < 100; ++i) {
            usedNames.add(RandomBuilder.oneOf(value1, value2));
        }
        assertThat(usedNames, Matchers.containsInAnyOrder(value1, value2));
    }

    @Test
    public void canReturnOneOfASingleItem() {
        String value1 = aRandomString();
        assertThat(RandomBuilder.oneOf(value1), equalTo(value1));
    }

    @Test
    public void canBuildACollectionOfGenerics() {
        CollectionOfGenerics result = RandomBuilder.aRandomInstanceOf(CollectionOfGenerics.class);
        assertThat(result.getValues(), not(empty()));
    }

}
