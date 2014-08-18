
package org.exparity.test.builder;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

/**
 * Static factory for creating instances of {@link ValueFactory} and {@link ArrayFactory} for use in the {@link BeanBuilder} and {@link RandomBuilder}
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
		return new ValueFactory<T>() {

			public T createValue() {
				return value;
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a null value.
	 * @return an {@link ValueFactory} which returns a null value.
	 */
	public static ValueFactory<Object> aNullValue() {
		return new ValueFactory<Object>() {

			public Object createValue() {
				return null;
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link String}.
	 * @return an {@link ValueFactory} which returns a random {@link String}.
	 */
	public static ValueFactory<String> aRandomString() {
		return new ValueFactory<String>() {

			public String createValue() {
				return RandomBuilder.aRandomString();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Integer}.
	 * @return an {@link ValueFactory} which returns a random {@link Integer}.
	 */
	public static ValueFactory<Integer> aRandomInteger() {
		return new ValueFactory<Integer>() {

			public Integer createValue() {
				return RandomBuilder.aRandomInteger();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Short}.
	 * @return an {@link ValueFactory} which returns a random {@link Short}.
	 */
	public static ValueFactory<Short> aRandomShort() {
		return new ValueFactory<Short>() {

			public Short createValue() {
				return RandomBuilder.aRandomShort();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Long}.
	 * @return an {@link ValueFactory} which returns a random {@link Long}.
	 */
	public static ValueFactory<Long> aRandomLong() {
		return new ValueFactory<Long>() {

			public Long createValue() {
				return RandomBuilder.aRandomLong();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Double}.
	 * @return an {@link ValueFactory} which returns a random {@link Double}.
	 */
	public static ValueFactory<Double> aRandomDouble() {
		return new ValueFactory<Double>() {

			public Double createValue() {
				return RandomBuilder.aRandomDouble();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Float}.
	 * @return an {@link ValueFactory} which returns a random {@link Float}.
	 */
	public static ValueFactory<Float> aRandomFloat() {
		return new ValueFactory<Float>() {

			public Float createValue() {
				return RandomBuilder.aRandomFloat();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Boolean}.
	 * @return an {@link ValueFactory} which returns a random {@link Boolean}.
	 */
	public static ValueFactory<Boolean> aRandomBoolean() {
		return new ValueFactory<Boolean>() {

			public Boolean createValue() {
				return RandomBuilder.aRandomBoolean();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Date}.
	 * @return an {@link ValueFactory} which returns a random {@link Date}.
	 */
	public static ValueFactory<Date> aRandomDate() {
		return new ValueFactory<Date>() {

			public Date createValue() {
				return RandomBuilder.aRandomDate();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link BigDecimal}.
	 * @return an {@link ValueFactory} which returns a random {@link BigDecimal}.
	 */
	public static ValueFactory<BigDecimal> aRandomDecimal() {
		return new ValueFactory<BigDecimal>() {

			public BigDecimal createValue() {
				return RandomBuilder.aRandomDecimal();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Byte}.
	 * @return an {@link ValueFactory} which returns a random {@link Byte}.
	 */
	public static ValueFactory<Byte> aRandomByte() {
		return new ValueFactory<Byte>() {

			public Byte createValue() {
				return (byte) nextInt(Byte.MAX_VALUE);
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random array of bytes
	 * @return an {@link ValueFactory} which returns a random array of bytes.
	 */
	public static ValueFactory<byte[]> aRandomByteArray() {
		return new ValueFactory<byte[]>() {

			public byte[] createValue() {
				return RandomBuilder.aRandomByteArray();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random {@link Character}.
	 * @return an {@link ValueFactory} which returns a random {@link Character}.
	 */
	public static ValueFactory<Character> aRandomChar() {
		return new ValueFactory<Character>() {

			public Character createValue() {
				return RandomBuilder.aRandomChar();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random enum from the supplied {@link Enum} type.
	 * @param enumType the enumeration type to create a random value form
	 * @return an {@link ValueFactory} which returns a random enum from the supplied {@link Enum} type
	 */
	public static <E> ValueFactory<E> aRandomEnum(final Class<E> enumType) {
		return new ValueFactory<E>() {

			public E createValue() {
				E[] enumerationValues = enumType.getEnumConstants();
				if (enumerationValues.length == 0) {
					throw new ValueFactoryException("Enumeration " + enumType.getName() + "has no values");
				} else {
					return enumerationValues[nextInt(enumerationValues.length)];
				}
			}
		};
	}

	/**
	 * Creates an {@link ArrayFactory} which returns a random array of the given type.
	 * @param typeFactory the {@link ValueFactory} to use to create each instance in the random array
	 * @return an {@link ArrayFactory} which returns a random array of the given type.
	 */
	public static <A> ArrayFactory<A> aRandomArrayOf(final ValueFactory<A> typeFactory) {
		return new ArrayFactory<A>() {

			@SuppressWarnings("unchecked")
			public A[] createValue(final Class<A> type, final int size) {
				Object array = Array.newInstance(type, size);
				for (int i = 0; i < size; ++i) {
					Array.set(array, i, typeFactory.createValue());
				}
				return (A[]) array;
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a new unpopulated instance of the given type.
	 * @param type the type to create a new instance of
	 * @return an {@link ArrayFactory} which returns a new unpopulated instance of the given type.
	 */
	public static <T> ValueFactory<T> aNewInstanceOf(final Class<T> type) {
		return new ValueFactory<T>() {

			public T createValue() {
				try {
					return type.newInstance();
				} catch (Exception e) {
					throw new ValueFactoryException("Failed to instantiate instance of '" + type.getCanonicalName() + "'", e);
				}
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 */
	public static <T> ValueFactory<T> oneOf(final ValueFactory<T>... factories) {
		return oneOf(Arrays.asList(factories));
	}

	/**
	 * Creates an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link ValueFactory} which returns a random instance of the given type by using one of the supplied {@link ValueFactory} instances.
	 */
	public static <T> ValueFactory<T> oneOf(final Collection<ValueFactory<T>> factories) {
		return new ValueFactory<T>() {

			private final List<ValueFactory<T>> candidates = new ArrayList<ValueFactory<T>>(factories);

			public T createValue() {
				return candidates.get(nextInt(candidates.size())).createValue();
			}
		};
	}

	/**
	 * Creates an {@link ValueFactory} which randomly returns one of the supplied instances.
	 * @param instances the instances to select from when creating the random value
	 * @return an {@link ValueFactory} which randomly returns one of the supplied instances.
	 */
	public static <T> ValueFactory<T> oneOf(final T... instances) {
		return new ValueFactory<T>() {

			public T createValue() {
				return instances[nextInt(instances.length)];
			}
		};
	}

}
