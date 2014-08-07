/*
 * Copyright (c) Modular IT Limited.
 */

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
 * Builder object for creating instances of {@link InstanceFactory} and {@link InstanceArrayFactory} for use in the {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 */
public abstract class InstanceBuilder {

	/**
	 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
	 * 
	 * @author Stewart Bissett
	 */
	public interface InstanceFactory<T> {

		/**
		 * Create a value of type T.
		 * 
		 * @return an instance of type T
		 */
		public T createValue();
	}

	/**
	 * Interface to be implemented by classes which can provide arrays of values to a {@link BeanBuilder}
	 * 
	 * @author Stewart Bissett
	 */
	public interface InstanceArrayFactory<T> {

		/**
		 * Create an array of type T.
		 * @param type the scalar type the array will be composed of
		 * @param size the size of the array to create
		 * @return an array of type T
		 */
		public T[] createValue(final Class<T> type, final int size);
	}

	/**
	 * Creates an {@link InstanceFactory} which returns the supplied value.
	 * @param value the value to return
	 * @return an {@link InstanceFactory} which returns the supplied value
	 */
	public static <T> InstanceFactory<T> theValue(final T value) {
		return new InstanceFactory<T>() {

			public T createValue() {
				return value;
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a null value.
	 * @return an {@link InstanceFactory} which returns a null value.
	 */
	public static InstanceFactory<Object> aNullValue() {
		return new InstanceFactory<Object>() {

			public Object createValue() {
				return null;
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link String}.
	 * @return an {@link InstanceFactory} which returns a random {@link String}.
	 */
	public static InstanceFactory<String> aRandomString() {
		return new InstanceFactory<String>() {

			public String createValue() {
				return RandomBuilder.aRandomString();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Integer}.
	 * @return an {@link InstanceFactory} which returns a random {@link Integer}.
	 */
	public static InstanceFactory<Integer> aRandomInteger() {
		return new InstanceFactory<Integer>() {

			public Integer createValue() {
				return RandomBuilder.aRandomInteger();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Short}.
	 * @return an {@link InstanceFactory} which returns a random {@link Short}.
	 */
	public static InstanceFactory<Short> aRandomShort() {
		return new InstanceFactory<Short>() {

			public Short createValue() {
				return RandomBuilder.aRandomShort();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Long}.
	 * @return an {@link InstanceFactory} which returns a random {@link Long}.
	 */
	public static InstanceFactory<Long> aRandomLong() {
		return new InstanceFactory<Long>() {

			public Long createValue() {
				return RandomBuilder.aRandomLong();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Double}.
	 * @return an {@link InstanceFactory} which returns a random {@link Double}.
	 */
	public static InstanceFactory<Double> aRandomDouble() {
		return new InstanceFactory<Double>() {

			public Double createValue() {
				return RandomBuilder.aRandomDouble();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Float}.
	 * @return an {@link InstanceFactory} which returns a random {@link Float}.
	 */
	public static InstanceFactory<Float> aRandomFloat() {
		return new InstanceFactory<Float>() {

			public Float createValue() {
				return RandomBuilder.aRandomFloat();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Boolean}.
	 * @return an {@link InstanceFactory} which returns a random {@link Boolean}.
	 */
	public static InstanceFactory<Boolean> aRandomBoolean() {
		return new InstanceFactory<Boolean>() {

			public Boolean createValue() {
				return RandomBuilder.aRandomBoolean();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Date}.
	 * @return an {@link InstanceFactory} which returns a random {@link Date}.
	 */
	public static InstanceFactory<Date> aRandomDate() {
		return new InstanceFactory<Date>() {

			public Date createValue() {
				return RandomBuilder.aRandomDate();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link BigDecimal}.
	 * @return an {@link InstanceFactory} which returns a random {@link BigDecimal}.
	 */
	public static InstanceFactory<BigDecimal> aRandomDecimal() {
		return new InstanceFactory<BigDecimal>() {

			public BigDecimal createValue() {
				return RandomBuilder.aRandomDecimal();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Byte}.
	 * @return an {@link InstanceFactory} which returns a random {@link Byte}.
	 */
	public static InstanceFactory<Byte> aRandomByte() {
		return new InstanceFactory<Byte>() {

			public Byte createValue() {
				return (byte) nextInt(Byte.MAX_VALUE);
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random array of bytes
	 * @return an {@link InstanceFactory} which returns a random array of bytes.
	 */
	public static InstanceFactory<byte[]> aRandomByteArray() {
		return new InstanceFactory<byte[]>() {

			public byte[] createValue() {
				return RandomBuilder.aRandomByteArray();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random {@link Character}.
	 * @return an {@link InstanceFactory} which returns a random {@link Character}.
	 */
	public static InstanceFactory<Character> aRandomChar() {
		return new InstanceFactory<Character>() {

			public Character createValue() {
				return RandomBuilder.aRandomChar();
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random enum from the supplied {@link Enum} type.
	 * @param enumType the enumeration type to create a random value form
	 * @return an {@link InstanceFactory} which returns a random enum from the supplied {@link Enum} type
	 */
	public static <E> InstanceFactory<E> aRandomEnum(final Class<E> enumType) {
		return new InstanceFactory<E>() {

			public E createValue() {
				E[] enumerationValues = enumType.getEnumConstants();
				if (enumerationValues.length == 0) {
					return null;
				} else {
					return enumerationValues[nextInt(enumerationValues.length)];
				}
			}
		};
	}

	/**
	 * Creates an {@link InstanceArrayFactory} which returns a random array of the given type.
	 * @param typeFactory the {@link InstanceFactory} to use to create each instance in the random array
	 * @return an {@link InstanceArrayFactory} which returns a random array of the given type.
	 */
	public static <A> InstanceArrayFactory<A> aRandomArrayOf(final InstanceFactory<A> typeFactory) {
		return new InstanceArrayFactory<A>() {

			@SuppressWarnings("unchecked")
			public A[] createValue(final Class<A> type, final int size) {
				Object array = Array.newInstance(type, size);
				if (array != null) {
					for (int i = 0; i < size; ++i) {
						Array.set(array, i, typeFactory.createValue());
					}
				}
				return (A[]) array;
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a new unpopulated instance of the given type.
	 * @param type the type to create a new instance of
	 * @return an {@link InstanceArrayFactory} which returns a new unpopulated instance of the given type.
	 */
	public static <T> InstanceFactory<T> aNewInstanceOf(final Class<T> type) {
		return new InstanceFactory<T>() {

			public T createValue() {
				try {
					return type.newInstance();
				} catch (Exception e) {
					throw new InstanceBuilderException("Failed to instantiate instance of '" + type.getCanonicalName() + "'", e);
				}
			}
		};
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random instance of the given type by using one of the supplied {@link InstanceFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link InstanceFactory} which returns a random instance of the given type by using one of the supplied {@link InstanceFactory} instances.
	 */
	public static <T> InstanceFactory<T> oneOf(final InstanceFactory<T>... factories) {
		return oneOf(Arrays.asList(factories));
	}

	/**
	 * Creates an {@link InstanceFactory} which returns a random instance of the given type by using one of the supplied {@link InstanceFactory} instances.
	 * @param factories the factories to select from when creating the random value
	 * @return an {@link InstanceFactory} which returns a random instance of the given type by using one of the supplied {@link InstanceFactory} instances.
	 */
	public static <T> InstanceFactory<T> oneOf(final Collection<InstanceFactory<T>> factories) {
		return new InstanceFactory<T>() {

			private final List<InstanceFactory<T>> candidates = new ArrayList<InstanceFactory<T>>(factories);

			public T createValue() {
				return candidates.get(nextInt(candidates.size())).createValue();
			}
		};
	}
}
