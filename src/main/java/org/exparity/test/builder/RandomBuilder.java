/*
 * Copyright (c) Modular IT Limited.
 */

package org.exparity.test.builder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.exparity.test.builder.InstanceBuilder.InstanceFactory;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.math.RandomUtils.*;
import static org.apache.commons.lang.time.DateUtils.addSeconds;

/**
 * Builder object for instantiating and populating random instances of Java types.
 * 
 * @author Stewart Bissett
 */
public abstract class RandomBuilder {

	/**
	 * Helper class which can be used to override individual properties in the randomly build instance
	 */
	public static class RandomPropertyReplacer {

		private final String property;
		private final Object value;

		public RandomPropertyReplacer(final String property, final Object value) {
			this.property = property;
			this.value = value;
		}

		private void applyTo(final BeanBuilder<?> builder) {
			builder.with(property, value);
		}

	}

	@SuppressWarnings({
			"rawtypes", "serial"
	})
	private static final Map<Class, InstanceFactory> RANDOM_FACTORIES = new HashMap<Class, InstanceFactory>() {

		{
			put(Short.class, InstanceBuilder.aRandomShort());
			put(short.class, InstanceBuilder.aRandomShort());
			put(Integer.class, InstanceBuilder.aRandomInteger());
			put(int.class, InstanceBuilder.aRandomInteger());
			put(Long.class, InstanceBuilder.aRandomLong());
			put(long.class, InstanceBuilder.aRandomLong());
			put(Double.class, InstanceBuilder.aRandomDouble());
			put(double.class, InstanceBuilder.aRandomDouble());
			put(Float.class, InstanceBuilder.aRandomFloat());
			put(float.class, InstanceBuilder.aRandomFloat());
			put(Boolean.class, InstanceBuilder.aRandomBoolean());
			put(boolean.class, InstanceBuilder.aRandomBoolean());
			put(Byte.class, InstanceBuilder.aRandomByte());
			put(byte.class, InstanceBuilder.aRandomByte());
			put(Character.class, InstanceBuilder.aRandomChar());
			put(char.class, InstanceBuilder.aRandomChar());
			put(String.class, InstanceBuilder.aRandomString());
			put(BigDecimal.class, InstanceBuilder.aRandomDecimal());
			put(Date.class, InstanceBuilder.aRandomDate());
		}
	};

	private static final int DEFAULT_MAX_ARRAY_SIZE = 10;
	private static final int DEFAULT_MIN_ARRAY_SIZE = 2;
	private static final int MAX_STRING_LENGTH = 50;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int HOURS_PER_DAY = 24;
	private static final int DAYS_PER_YEAR = 365;
	private static final int SECONDS_IN_A_YEAR = MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_YEAR;

	/**
	 * Return a random {@link String} containing alphanumeric characters.
	 * @return a random {@link String}.
	 */
	public static String aRandomString() {
		return randomAlphanumeric(MAX_STRING_LENGTH);
	}

	/**
	 * Return a random {@link Integer}.
	 * @return a random {@link Integer}.
	 */
	public static Integer aRandomInteger() {
		return Integer.valueOf(nextInt());
	}

	/**
	 * Return a random {@link Integer} within the supplied minumum and maximum range.
	 * @param min the minimum value can be
	 * @param max the maximum value can be
	 * @return a random {@link Integer} within the supplied minumum and maximum range.
	 */
	public static Integer aRandomInteger(final int min, final int max) {
		if (min == max) {
			return min;
		} else {
			return min + Integer.valueOf(nextInt(max - min));
		}
	}

	/**
	 * Return a random {@link Short}.
	 * @return a random {@link Short}.
	 */
	public static Short aRandomShort() {
		return Short.valueOf((short) nextInt(Short.MAX_VALUE));
	}

	/**
	 * Return a random {@link Short} within the supplied minumum and maximum range.
	 * @param min the minimum value can be
	 * @param max the maximum value can be
	 * @return a random {@link Short} within the supplied minumum and maximum range.
	 */
	public static Short aRandomShort(final short min, final short max) {
		if (min == max) {
			return min;
		} else {
			return Short.valueOf((short) (min + nextInt(max - min)));
		}
	}

	/**
	 * Return a random {@link Long}.
	 * @return a random {@link Long}.
	 */
	public static Long aRandomLong() {
		return Long.valueOf(nextLong());
	}

	/**
	 * Return a random {@link Long} within the supplied minumum and maximum range.
	 * @param min the minimum value can be
	 * @param max the maximum value can be
	 * @return a random {@link Long} within the supplied minumum and maximum range.
	 */
	public static Long aRandomLong(final int min, final int max) {
		if (min == max) {
			return (long) min;
		} else {
			return Long.valueOf(min + nextInt(max - min));
		}
	}

	public static Double aRandomDouble() {
		return Double.valueOf(nextDouble());
	}

	public static Float aRandomFloat() {
		return Float.valueOf(nextFloat());
	}

	public static Boolean aRandomBoolean() {
		return Boolean.valueOf(nextBoolean());
	}

	public static Date aRandomDate() {
		return addSeconds(new Date(), nextInt(SECONDS_IN_A_YEAR));
	}

	public static BigDecimal aRandomDecimal() {
		return BigDecimal.valueOf(nextInt()).round(new MathContext(10, RoundingMode.HALF_UP)).movePointLeft(nextInt(5));
	}

	public static Byte aRandomByte() {
		return (byte) nextInt(Byte.MAX_VALUE);
	}

	public static byte[] aRandomByteArray() {
		byte[] array = new byte[aRandomInteger(2,1000)];
		for (int i = 0; i < array.length; ++i) {
			array[i] = aRandomByte();
		}
		return array;
	}

	public static Character aRandomChar() {
		return randomAlphabetic(1).charAt(0);
	}

	public static <E extends Enum<E>> E aRandomEnum(final Class<E> enumType) {
		E[] enumerationValues = enumType.getEnumConstants();
		if (enumerationValues.length == 0) {
			return null;
		} else {
			return enumerationValues[nextInt(enumerationValues.length)];
		}
	}

	public static <E extends Enum<E>> E[] aRandomArrayOfEnum(final Class<E> enumType) {
		return aRandomArrayOfEnum(enumType, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	public static <E extends Enum<E>> E[] aRandomArrayOfEnum(final Class<E> enumType, final int min, final int max) {
		return InstanceBuilder.aRandomArrayOf(InstanceBuilder.aRandomEnum(enumType)).createValue(enumType, aRandomInteger(min, max));
	}

	public static <T> T[] aRandomArrayOf(final Class<T> type) {
		return aRandomArrayOf(type, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	public static <T> T[] aRandomArrayOf(final Class<T> type, final int min, final int max) {
		return InstanceBuilder.aRandomArrayOf(instanceFactoryFor(type)).createValue(type, aRandomInteger(min, max));
	}

	public static <T> Collection<T> aRandomCollectionOf(final Class<T> type) {
		return aRandomCollectionOf(type, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	public static <T> Collection<T> aRandomCollectionOf(final Class<T> type, final int min, final int max) {
		return aRandomListOf(type, min, max);
	}

	public static <T> List<T> aRandomListOf(final Class<T> type) {
		return aRandomListOf(type, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	public static <T> List<T> aRandomListOf(final Class<T> type, final int min, final int max) {
		return Arrays.asList(aRandomArrayOf(type, min, max));
	}

	public static <T> T aRandomInstanceOf(final Class<T> type, final RandomPropertyReplacer... replacements) {
		return instanceFactoryFor(type, replacements).createValue();
	}

	@SuppressWarnings("unchecked")
	private static <T> InstanceFactory<T> instanceFactoryFor(final Class<T> type, final RandomPropertyReplacer... replacements) {
		InstanceFactory<T> factory = RANDOM_FACTORIES.get(type);
		if (factory == null) {
			BeanBuilder<T> builder = BeanBuilder.aRandomInstanceOf(type);
			for (RandomPropertyReplacer replacement : replacements) {
				replacement.applyTo(builder);
			}
			factory = InstanceBuilder.theValue(builder.build());
		}
		return factory;
	}

	public static RandomPropertyReplacer property(final String property, final Object value) {
		return new RandomPropertyReplacer(property, value);
	}

}
