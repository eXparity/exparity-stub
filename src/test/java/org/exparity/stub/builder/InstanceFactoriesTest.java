
package org.exparity.stub.builder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.exparity.stub.builder.ValueFactories;
import org.exparity.stub.builder.ValueFactory;
import org.exparity.stub.builder.ValueFactoryException;
import org.exparity.stub.builder.BeanBuilderTestTypes.Car;
import org.exparity.stub.builder.BeanBuilderTestTypes.EmptyEnum;
import org.exparity.stub.builder.BeanBuilderTestTypes.FuelType;
import org.exparity.stub.builder.BeanBuilderTestTypes.NoDefaultConstructor;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.exparity.stub.builder.ValueFactories.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for the {@link ValueFactories} class
 * 
 * @author Stewart Bissett
 */
@SuppressWarnings("unchecked")
public class InstanceFactoriesTest {

	@Test
	public void canCreateANewInstanceOf() {
		checkResult(aNewInstanceOf(Car.class), any(Car.class));
	}

	@Test(expected = ValueFactoryException.class)
	public void canFailToCreateANewInstanceOfWithNoDefaultConstructir() {
		checkResult(aNewInstanceOf(NoDefaultConstructor.class), any(NoDefaultConstructor.class));
	}

	@Test
	public void canCreateANullValue() {
		checkResult(aNullValue(), nullValue());
	}

	@Test
	public void canCreateARandomArrayOf() {
		Boolean[] array = aRandomArrayOf(aRandomBoolean()).createValue(Boolean.class, 5);
		assertThat(array, arrayWithSize(5));
		assertThat(array, hasItemInArray(any(Boolean.class)));
	}

	@Test
	public void canCreateARandomBoolean() {
		checkResult(aRandomBoolean(), any(Boolean.class));
	}

	@Test
	public void canCreateARandomByte() {
		checkResult(aRandomByte(), any(Byte.class));
	}

	@Test
	public void canCreateARandomByteArray() {
		checkResult(aRandomByteArray(), any(byte[].class));
	}

	@Test
	public void canCreateARandomChar() {
		checkResult(aRandomChar(), any(Character.class));
	}

	@Test
	public void canCreateARandomDate() {
		checkResult(aRandomDate(), any(Date.class));
	}

	@Test
	public void canCreateARandomBigDecimal() {
		checkResult(aRandomDecimal(), any(BigDecimal.class));
	}

	@Test
	public void canCreateARandomDouble() {
		checkResult(aRandomDouble(), any(Double.class));
	}

	@Test
	public void canCreateARandomEnum() {
		checkResult(aRandomEnum(FuelType.class), any(FuelType.class));
	}

	@Test(expected = ValueFactoryException.class)
	public void canFailToCreateARandomEmptyEnum() {
		checkResult(aRandomEnum(EmptyEnum.class), any(EmptyEnum.class));
	}

	@Test
	public void canCreateARandomFloat() {
		checkResult(aRandomFloat(), any(Float.class));
	}

	@Test
	public void canCreateARandomInteger() {
		checkResult(aRandomInteger(), any(Integer.class));
	}

	@Test
	public void canCreateARandomLong() {
		checkResult(aRandomLong(), any(Long.class));
	}

	@Test
	public void canCreateARandomShort() {
		checkResult(aRandomShort(), any(Short.class));
	}

	@Test
	public void canCreateARandomString() {
		checkResult(aRandomString(), any(String.class));
	}

	@Test
	public void canCreateOneOfFactoryFromInstances() {
		checkResult(oneOf("Smith", "Brown"), anyOf(equalTo("Smith"), equalTo("Brown")));
	}

	@Test
	public void canCreateOneOfFactoryFromArrayOfInstanceFactories() {
		checkResult(oneOf(theValue("Smith"), theValue("Brown")), anyOf(equalTo("Smith"), equalTo("Brown")));
	}

	@Test
	public void canCreateOneOfFactoryFromListOfInstanceFactories() {
		checkResult(oneOf(Arrays.asList(theValue("Smith"), theValue("Brown"))), anyOf(equalTo("Smith"), equalTo("Brown")));
	}

	@Test
	public void canCreateTheValueFactory() {
		checkResult(theValue("Smith"), equalTo("Smith"));
	}

	private <T> void checkResult(final ValueFactory<T> factory, final Matcher<T> matcher) {
		assertThat(factory.createValue(), matcher);
	}
}
