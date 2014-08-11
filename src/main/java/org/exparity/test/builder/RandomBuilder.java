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
	 * Interface to be implemented by all classes which can restrict the behaviour of the random builder and how it assigns values to properties, which types, it creates, etc
	 */
	public static interface RandomRestriction {

		/**
		 * Apply the restriction to the {@link BeanBuilder} instance.
		 * @param the instance to apply the restriction to
		 */
		public void applyTo(final BeanBuilder<?> builder);
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
	 * Build a random {@link String} containing alphanumeric characters.
	 * @return a random {@link String}.
	 */
	public static String aRandomString() {
		return randomAlphanumeric(MAX_STRING_LENGTH);
	}

	/**
	 * Build a random {@link Integer}.
	 * @return a random {@link Integer}.
	 */
	public static Integer aRandomInteger() {
		return Integer.valueOf(nextInt());
	}

	/**
	 * Build a random {@link Integer} within the supplied minumum and maximum range.
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
	 * Build a random {@link Short}.
	 * @return a random {@link Short}.
	 */
	public static Short aRandomShort() {
		return Short.valueOf((short) nextInt(Short.MAX_VALUE));
	}

	/**
	 * Build a random {@link Short} within the supplied minumum and maximum range.
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
	 * Build a random {@link Long}.
	 * @return a random {@link Long}.
	 */
	public static Long aRandomLong() {
		return Long.valueOf(nextLong());
	}

	/**
	 * Build a random {@link Long} within the supplied minumum and maximum range.
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

	/**
	 * Build a random {@link Double}.
	 * @return a random {@link Double}.
	 */
	public static Double aRandomDouble() {
		return Double.valueOf(nextDouble());
	}

	/**
	 * Build a random {@link Float}.
	 * @return a random {@link Float}.
	 */
	public static Float aRandomFloat() {
		return Float.valueOf(nextFloat());
	}

	/**
	 * Build a random {@link Boolean}.
	 * @return a random {@link Boolean}.
	 */
	public static Boolean aRandomBoolean() {
		return Boolean.valueOf(nextBoolean());
	}

	/**
	 * Build a random {@link Date} with an year either side of today.
	 * @return a random {@link Date} with an year either side of today.
	 */
	public static Date aRandomDate() {
		return addSeconds(new Date(), nextInt(SECONDS_IN_A_YEAR));
	}

	/**
	 * Build a random {@link BigDecimal}.
	 * @return a random {@link BigDecimal}.
	 */
	public static BigDecimal aRandomDecimal() {
		return BigDecimal.valueOf(nextInt()).round(new MathContext(10, RoundingMode.HALF_UP)).movePointLeft(nextInt(5));
	}

	/**
	 * Build a random {@link Byte}.
	 * @return a random {@link Byte}.
	 */
	public static Byte aRandomByte() {
		return (byte) nextInt(Byte.MAX_VALUE);
	}

	/**
	 * Build a random array of bytes
	 * @return a random array of bytes
	 */
	public static byte[] aRandomByteArray() {
		byte[] array = new byte[aRandomInteger(2, 1000)];
		for (int i = 0; i < array.length; ++i) {
			array[i] = aRandomByte();
		}
		return array;
	}

	/**
	 * Build a random {@link Character}.
	 * @return a random {@link Character}.
	 */
	public static Character aRandomChar() {
		return randomAlphabetic(1).charAt(0);
	}

	/**
	 * Build a random instance of the supplied enumeration
	 * @param enumType the enumeration to create a random instance of
	 * @return a random instance of the supplied enumeration.
	 */
	public static <E extends Enum<E>> E aRandomEnum(final Class<E> enumType) {
		E[] enumerationValues = enumType.getEnumConstants();
		if (enumerationValues.length == 0) {
			return null;
		} else {
			return enumerationValues[nextInt(enumerationValues.length)];
		}
	}

	/**
	 * Build an array of random values from the supplied enumeration.
	 * @param enumType the enumeration to create an array of
	 * @return an array of random values from the supplied enumeration.
	 */
	public static <E extends Enum<E>> E[] aRandomArrayOfEnum(final Class<E> enumType) {
		return aRandomArrayOfEnum(enumType, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	/**
	 * Build an array of random values from the supplied enumeration of a given size
	 * @param enumType the enumeration to create an array of
	 * @param min the minimum size the array can be
	 * @param max the maxiumum size the array can be
	 * @return an array of random values from the supplied enumeration.
	 */
	public static <E extends Enum<E>> E[] aRandomArrayOfEnum(final Class<E> enumType, final int min, final int max) {
		return InstanceBuilder.aRandomArrayOf(InstanceBuilder.aRandomEnum(enumType)).createValue(enumType, aRandomInteger(min, max));
	}

	/**
	 * Build an array of randomly populated instances of the supplied type
	 * @param type the type to create an array of
	 * @return an array of randomly populated instances of the supplied type
	 */
	public static <T> T[] aRandomArrayOf(final Class<T> type) {
		return aRandomArrayOf(type, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	/**
	 * Build an array of randomly populated instances of the supplied type if a given size
	 * @param type the type to create an array of
	 * @param min the minimum size the array can be
	 * @param max the maxiumum size the array can be
	 * @return an array of randomly populated instances of the supplied type
	 */
	public static <T> T[] aRandomArrayOf(final Class<T> type, final int min, final int max) {
		return InstanceBuilder.aRandomArrayOf(instanceFactoryFor(type)).createValue(type, aRandomInteger(min, max));
	}

	/**
	 * Build a collection of randomly populated instances of the supplied type
	 * @param type the type to create a collection of
	 * @return a collection of randomly populated instances of the supplied type
	 */
	public static <T> Collection<T> aRandomCollectionOf(final Class<T> type) {
		return aRandomCollectionOf(type, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	/**
	 * Build a collection of randomly populated instances of the supplied type if a given size
	 * @param type the type to create a collection of
	 * @param min the minimum size the collection can be
	 * @param max the maxiumum size the collection can be
	 * @return a collection of randomly populated instances of the supplied type if a given size
	 */
	public static <T> Collection<T> aRandomCollectionOf(final Class<T> type, final int min, final int max) {
		return aRandomListOf(type, min, max);
	}

	/**
	 * Build a list of randomly populated instances of the supplied type
	 * @param type the type to create a list of
	 * @return a list of randomly populated instances of the supplied type
	 */
	public static <T> List<T> aRandomListOf(final Class<T> type) {
		return aRandomListOf(type, DEFAULT_MIN_ARRAY_SIZE, DEFAULT_MAX_ARRAY_SIZE);
	}

	/**
	 * Build a random list of randomly populated instances of the supplied type if a given size
	 * @param type the type to create a random list of
	 * @param min the minimum size the list can be
	 * @param max the maxiumum size the list can be
	 * @return a random list of randomly populated instances of the supplied type
	 */
	public static <T> List<T> aRandomListOf(final Class<T> type, final int min, final int max) {
		return Arrays.asList(aRandomArrayOf(type, min, max));
	}

	/**
	 * Build a random instance of the supplied type. An array of restrictions can be supplied as arguments if control is required over how some of the properties are populated
	 * @param type the type to create a random instance of
	 * @param restrictions an array of restrictions which control how the random instance is built
	 * @return a random instance of the supplied type
	 */
	public static <T> T aRandomInstanceOf(final Class<T> type, final RandomRestriction... restrictions) {
		return instanceFactoryFor(type, restrictions).createValue();
	}

	/**
	 * Build a random instance of the supplied type
	 * @param type the type to create a random instance of
	 * @param restrictions a list of restrictions which control how the random instance is built
	 * @return a random instance of the supplied type
	 */
	public static <T> T aRandomInstanceOf(final Class<T> type, final List<RandomRestriction> restrictions) {
		return instanceFactoryFor(type, restrictions.toArray(new RandomRestriction[0])).createValue();
	}

	@SuppressWarnings("unchecked")
	private static <T> InstanceFactory<T> instanceFactoryFor(final Class<T> type, final RandomRestriction... restrictions) {
		InstanceFactory<T> factory = RANDOM_FACTORIES.get(type);
		if (factory == null) {
			BeanBuilder<T> builder = BeanBuilder.aRandomInstanceOf(type);
			for (RandomRestriction restriction : restrictions) {
				restriction.applyTo(builder);
			}
			factory = InstanceBuilder.theValue(builder.build());
		}
		return factory;
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which assigns the value to the specific property
	 * @param property the property to assign e.g. "name"
	 * @param value the value to assign the property e.g. "Jane Doe"
	 */
	public static RandomRestriction property(final String property, final Object value) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.withProperty(property, value);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which assigns an {@link InstanceFactory} derived value to the specific property
	 * @param property the property to assign
	 * @param value the value to assign the property
	 */
	public static RandomRestriction property(final String property, final InstanceFactory<?> factory) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.withProperty(property, factory);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will prevent the property being assigned any value
	 * @param property the path to assign e.g. "name"
	 */
	public static RandomRestriction excludeProperty(final String property) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.excludeProperty(property);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which assigns the value to the specific path
	 * @param path the path to assign e.g. "person.name"
	 * @param value the value to assign the path e.g. "Jane Doe"
	 */
	public static RandomRestriction path(final String path, final Object value) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.withPath(path, value);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which assigns an {@link InstanceFactory} derived value to the specific path
	 * @param path the path to assign e.g. "person.name"
	 * @param value the value to assign the path
	 */
	public static RandomRestriction path(final String path, final InstanceFactory<?> factory) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.withPath(path, factory);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will prevent the value at the path being assigned any value
	 * @param path the path to assign e.g. "person.name"
	 */
	public static RandomRestriction excludePath(final String path) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.excludePath(path);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will use the given subtype of a super type when instantiating the subtype.
	 * @param superType the super type
	 * @param subType the sub type to use when creating an instance of the super type
	 */
	public static <P> RandomRestriction subtype(final Class<P> superType, final Class<? extends P> subType) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.usingType(superType, subType);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will use any of the given sub types of a super type when instantiating the super type.
	 * @param superType the super type
	 * @param subType the sub types to use when creating an instance of the super type
	 */
	public static <P> RandomRestriction subtype(final Class<P> superType, final Class<? extends P>... subTypes) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.usingType(superType, subTypes);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will limit the size of all collections to the specified size
	 * @param size the size of the collection
	 */
	public static <P> RandomRestriction collectionSize(final int size) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.aCollectionSizeOf(size);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will limit the size of all collections to within the specified range
	 * @param min the minimum size the collections can be
	 * @param max the maximum size the collections can be
	 */
	public static <P> RandomRestriction collectionSize(final int min, final int max) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.aCollectionSizeRangeOf(min, max);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will set the size of the collection at the specified path
	 * @param path the path to assign the collection size of e.g. "person.siblings"
	 * @param size the size of the collection
	 */
	public static <P> RandomRestriction collectionSizeForPath(final String path, final int size) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.aCollectionSizeForPathOf(path, size);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will limit the size of the collection at the specifed path
	 * @param min the minimum size the collections can be
	 * @param max the maximum size the collections can be
	 */
	public static <P> RandomRestriction collectionSizeForPath(final String path, final int min, final int max) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.aCollectionSizeRangeForPathOf(path, min, max);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will set the size of the collection at the specified property
	 * @param property the property to assign the collection size of e.g. "person.siblings"
	 * @param size the size of the collection
	 */
	public static <P> RandomRestriction collectionSizeForProperty(final String property, final int size) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.aCollectionSizeForPropertyOf(property, size);
			}
		};
	}

	/**
	 * Build and instance of a {@link RandomRestriction} which will limit the size of the collection at the specifed property
	 * @param min the minimum size the collections can be
	 * @param max the maximum size the collections can be
	 */
	public static <P> RandomRestriction collectionSizeForProperty(final String property, final int min, final int max) {
		return new RandomRestriction() {

			public void applyTo(final BeanBuilder<?> builder) {
				builder.aCollectionSizeRangeForPropertyOf(property, min, max);
			}
		};
	}

}
