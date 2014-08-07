
package org.exparity.test.builder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.exparity.test.builder.BeanUtilTestFixture.AllTypes;
import org.exparity.test.builder.BeanUtilTestFixture.FuelType;
import org.junit.Test;
import static org.exparity.test.builder.RandomBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
	public void canBuildARandomLong() {
		assertThat(aRandomLong(), isA(Long.class));
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

	@Test
	public void canBuildARandomArrayOfEnums() {
		assertThat(aRandomArrayOfEnum(FuelType.class), hasItemInArray(isOneOf(FuelType.DIESEL, FuelType.PETROL, FuelType.LPG)));
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
	public void canBuildARandomyrratOfObjectsOfExactSize() {
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

}
