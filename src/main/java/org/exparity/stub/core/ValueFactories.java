
package org.exparity.stub.core;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.exparity.stub.random.RandomBuilder;


/**
 * Static factory for creating instances of {@link ValueFactory} and {@link ArrayFactory} for use in the {@link org.exparity.stub.bean.BeanBuilder} and {@link RandomBuilder}
 *
 * @author Stewart Bissett
 */
public abstract class ValueFactories {

	/**
	 * Creates an {@link ValueFactory} which returns the supplied value.
	 * @param value the value to return
	 * @return an {@link ValueFactory} which returns the supplied value
	 */
	public static <T> ValueFactory<T> theValue(final T value) {
		return () -> value;
	}

	/**
	 * Creates an {@link ValueFactory} which returns a null value.
	 * @return an {@link ValueFactory} which returns a null value.
	 */
	public static ValueFactory<Object> aNullValue() {
		return () -> null;
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link String}.
	 * @return an {@link ValueFactory} which returns a random {@link String}.
	 */
	public static ValueFactory<String> aRandomString() {
		return () -> RandomBuilder.aRandomString();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Integer}.
	 * @return an {@link ValueFactory} which returns a random {@link Integer}.
	 */
	public static ValueFactory<Integer> aRandomInteger() {
		return () -> RandomBuilder.aRandomInteger();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Short}.
	 * @return an {@link ValueFactory} which returns a random {@link Short}.
	 */
	public static ValueFactory<Short> aRandomShort() {
		return () -> RandomBuilder.aRandomShort();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Long}.
	 * @return an {@link ValueFactory} which returns a random {@link Long}.
	 */
	public static ValueFactory<Long> aRandomLong() {
		return () -> RandomBuilder.aRandomLong();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Double}.
	 * @return an {@link ValueFactory} which returns a random {@link Double}.
	 */
	public static ValueFactory<Double> aRandomDouble() {
		return () -> RandomBuilder.aRandomDouble();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Float}.
	 * @return an {@link ValueFactory} which returns a random {@link Float}.
	 */
	public static ValueFactory<Float> aRandomFloat() {
		return () -> RandomBuilder.aRandomFloat();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Boolean}.
	 * @return an {@link ValueFactory} which returns a random {@link Boolean}.
	 */
	public static ValueFactory<Boolean> aRandomBoolean() {
		return () -> RandomBuilder.aRandomBoolean();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Date}.
	 * @return an {@link ValueFactory} which returns a random {@link Date}.
	 */
	public static ValueFactory<Date> aRandomDate() {
		return () -> RandomBuilder.aRandomDate();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link LocalDate}.
	 * @return an {@link ValueFactory} which returns a random {@link LocalDate}.
	 */
	public static ValueFactory<LocalDate> aRandomLocalDate() {
		return () -> RandomBuilder.aRandomLocalDate();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link LocalDateTime}.
	 * @return an {@link ValueFactory} which returns a random {@link LocalDateTime}.
	 */
	public static ValueFactory<LocalDateTime> aRandomLocalDateTime() {
		return () -> RandomBuilder.aRandomLocalDateTime();
	}

	   /**
     * Creates an {@link ValueFactory} which returns a random {@link LocalTime}.
     * @return an {@link ValueFactory} which returns a random {@link LocalTime}.
     */
    public static ValueFactory<LocalTime> aRandomLocalTime() {
        return () -> RandomBuilder.aRandomLocalTime();
    }

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link ZonedDateTime}.
	 * @return an {@link ValueFactory} which returns a random {@link ZonedDateTime}.
	 */
	public static ValueFactory<ZonedDateTime> aRandomZonedDateTime() {
		return () -> RandomBuilder.aRandomZonedDateTime();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Instant}.
	 * @return an {@link ValueFactory} which returns a random {@link Instant}.
	 */
	public static ValueFactory<Instant> aRandomInstant() {
		return () -> RandomBuilder.aRandomInstant();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link BigDecimal}.
	 * @return an {@link ValueFactory} which returns a random {@link BigDecimal}.
	 */
	public static ValueFactory<BigDecimal> aRandomDecimal() {
		return () -> RandomBuilder.aRandomDecimal();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Byte}.
	 * @return an {@link ValueFactory} which returns a random {@link Byte}.
	 */
	public static ValueFactory<Byte> aRandomByte() {
		return () -> RandomBuilder.aRandomByte();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random array of bytes
	 * @return an {@link ValueFactory} which returns a random array of bytes.
	 */
	public static ValueFactory<byte[]> aRandomByteArray() {
		return () -> RandomBuilder.aRandomByteArray();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Character}.
	 * @return an {@link ValueFactory} which returns a random {@link Character}.
	 */
	public static ValueFactory<Character> aRandomChar() {
		return () -> RandomBuilder.aRandomChar();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random enum from the supplied {@link Enum} type.
	 * @param enumType the enumeration type to create a random value form
	 * @return an {@link ValueFactory} which returns a random enum from the supplied {@link Enum} type
	 */
	public static <E> ValueFactory<E> aRandomEnum(final Class<E> enumType) {
		return () -> {
			E[] enumerationValues = enumType.getEnumConstants();
			if (enumerationValues.length == 0) {
				throw new ValueFactoryException("Enumeration " + enumType.getName() + "has no values");
			} else {
				return enumerationValues[nextInt(enumerationValues.length)];
			}
		};
	}

	/**
	 * Creates an {@link ArrayFactory} which returns a random array of the given type.
	 * @param typeFactory the {@link ValueFactory} to use to create each instance in the random array
	 * @param size the size of the array
	 * @return an {@link ArrayFactory} which returns a random array of the given type.
	 */
	@SuppressWarnings("unchecked")
	public static <A> ValueFactory<A[]> aRandomArrayOf(final ValueFactory<A> typeFactory, final int size) {
		return () -> {
		    A instance = typeFactory.createValue();
		    Object array = Array.newInstance(instance.getClass(), size);
		    if ( size > 0 ) {
                Array.set(array, 0, instance);
    			for (int i = 1; i < size; ++i) {
    				Array.set(array, i, typeFactory.createValue());
    			}
		    }
			return (A[]) array;
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a new unpopulated instance of the given type.
	 * @param type the type to create a new instance of
	 * @return an {@link ArrayFactory} which returns a new unpopulated instance of the given type.
	 */
	public static <T> ValueFactory<T> anEmptyInstanceOf(final Class<T> type) {
		return () -> {
			try {
				return type.newInstance();
			} catch (InstantiationException e) {
			    throw new NoDefaultConstructorException(type, e);
			} catch (Exception e) {
				throw new ValueFactoryException(
						"Failed to instantiate instance of '" + type.getCanonicalName() + "'",
							e);
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ValueFactory<T> oneOf(final ValueFactory<T>... factories) {
		return oneOf(Arrays.asList(factories));
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 */
	public static <T> ValueFactory<T> oneOf(final Collection<ValueFactory<T>> factories) {
		return () -> new ArrayList<>(factories).get(nextInt(factories.size())).createValue();
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 */
	public static <T> ValueFactory<T> oneOf(final List<ValueFactory<T>> factories) {
		return () -> factories.get(nextInt(factories.size())).createValue();
	}

	/**
	 * Creates an {@link ValueFactory} which randomly returns one of the supplied instances.
	 * @param instances the instances to select from when creating the random value
	 * @return an {@link ValueFactory} which randomly returns one of the supplied instances.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ValueFactory<T> oneOf(final T... instances) {
		return () -> instances[nextInt(instances.length)];
	}
}
