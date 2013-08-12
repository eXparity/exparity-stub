/*
 * Copyright (c) Modular IT Limited.
 */

package uk.co.it.modular.beans;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.math.RandomUtils.*;
import static org.apache.commons.lang.time.DateUtils.addSeconds;

/**
 * @author <a href="mailto:stewart@modular-it.co.uk">Stewart Bissett</a>
 */
public abstract class InstanceFactories {

	private static final int MAX_STRING_LENGTH = 50;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int HOURS_PER_DAY = 24;
	private static final int DAYS_PER_YEAR = 365;
	private static final int SECONDS_IN_A_YEAR = MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_YEAR;

	public static <T> InstanceFactory<T> theValue(final T value) {
		return new InstanceFactory<T>() {

			public T createValue() {
				return value;
			}
		};
	}

	public static InstanceFactory<Object> aNullValue() {
		return new InstanceFactory<Object>() {

			public Object createValue() {
				return null;
			}
		};
	}

	public static InstanceFactory<String> aRandomString() {
		return new InstanceFactory<String>() {

			public String createValue() {
				return randomAlphanumeric(MAX_STRING_LENGTH);
			}
		};
	}

	public static InstanceFactory<Integer> aRandomInteger() {
		return new InstanceFactory<Integer>() {

			public Integer createValue() {
				return Integer.valueOf(nextInt());
			}
		};
	}

	public static InstanceFactory<Short> aRandomShort() {
		return new InstanceFactory<Short>() {

			public Short createValue() {
				return Short.valueOf((short) nextInt(Short.MAX_VALUE));
			}
		};
	}

	public static InstanceFactory<Long> aRandomLong() {
		return new InstanceFactory<Long>() {

			public Long createValue() {
				return Long.valueOf(nextLong());
			}
		};
	}

	public static InstanceFactory<Double> aRandomDouble() {
		return new InstanceFactory<Double>() {

			public Double createValue() {
				return Double.valueOf(nextDouble());
			}
		};
	}

	public static InstanceFactory<Float> aRandomFloat() {
		return new InstanceFactory<Float>() {

			public Float createValue() {
				return Float.valueOf(nextFloat());
			}
		};
	}

	public static InstanceFactory<Boolean> aRandomBoolean() {
		return new InstanceFactory<Boolean>() {

			public Boolean createValue() {
				return Boolean.valueOf(nextBoolean());
			}
		};
	}

	public static InstanceFactory<Date> aRandomDate() {
		return new InstanceFactory<Date>() {

			public Date createValue() {
				return addSeconds(new Date(), nextInt(SECONDS_IN_A_YEAR));
			}
		};
	}

	public static InstanceFactory<BigDecimal> aRandomDecimal() {
		return new InstanceFactory<BigDecimal>() {

			public BigDecimal createValue() {
				return BigDecimal.valueOf(nextDouble());
			}
		};
	}

	public static InstanceFactory<Byte> aRandomByte() {
		return new InstanceFactory<Byte>() {

			public Byte createValue() {
				return (byte) nextInt(Byte.MAX_VALUE);
			}
		};
	}

	public static InstanceFactory<Character> aRandomChar() {
		return new InstanceFactory<Character>() {

			public Character createValue() {
				return randomAlphabetic(1).charAt(0);
			}
		};
	}

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

	public static <A> ArrayFactory<A> aRandomArrayOf(final InstanceFactory<A> typeFactory) {
		return new ArrayFactory<A>() {

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

	public static <T> InstanceFactory<T> aNewInstanceOf(final Class<T> type) {
		return new InstanceFactory<T>() {

			public T createValue() {
				try {
					return type.newInstance();
				} catch (Exception e) {
					throw new BeanBuilderException("Failed to instantiate instance of '" + type.getCanonicalName() + "'", e);
				}
			}
		};
	}

	public static <T> InstanceFactory<T> oneOf(final InstanceFactory<T>... factories) {
		return oneOf(Arrays.asList(factories));
	}

	public static <T> InstanceFactory<T> oneOf(final Collection<InstanceFactory<T>> factories) {
		return new InstanceFactory<T>() {

			private final List<InstanceFactory<T>> candidates = new ArrayList<InstanceFactory<T>>(factories);

			public T createValue() {
				return candidates.get(nextInt(candidates.size())).createValue();
			}
		};
	}
}
