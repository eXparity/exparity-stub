package uk.co.it.modular.beans;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * @author Stewart Bissett
 * 
 * @deprecated Moved to {@link org.exparity.test.builder.InstanceFactories}
 */
@Deprecated
public abstract class InstanceFactories {

	public static <T> InstanceFactory<T> theValue(final T value) {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.theValue(value));
	}

	public static InstanceFactory<Object> aNullValue() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aNullValue());
	}

	public static InstanceFactory<String> aRandomString() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomString());
	}

	public static InstanceFactory<Integer> aRandomInteger() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomInteger());
	}

	public static InstanceFactory<Short> aRandomShort() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomShort());
	}

	public static InstanceFactory<Long> aRandomLong() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomLong());
	}

	public static InstanceFactory<Double> aRandomDouble() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomDouble());
	}

	public static InstanceFactory<Float> aRandomFloat() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomFloat());
	}

	public static InstanceFactory<Boolean> aRandomBoolean() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomBoolean());
	}

	public static InstanceFactory<Date> aRandomDate() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomDate());
	}

	public static InstanceFactory<BigDecimal> aRandomDecimal() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomDecimal());
	}

	public static InstanceFactory<Byte> aRandomByte() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomByte());
	}

	public static InstanceFactory<Character> aRandomChar() {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomChar());
	}

	public static <E> InstanceFactory<E> aRandomEnum(final Class<E> enumType) {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomEnum(enumType));
	}

	public static <A> ArrayFactory<A> aRandomArrayOf(final InstanceFactory<A> typeFactory) {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aRandomArrayOf(InstanceAdapters.adapt(typeFactory)));
	}

	public static <T> InstanceFactory<T> aNewInstanceOf(final Class<T> type) {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.aNewInstanceOf(type));
	}

	public static <T> InstanceFactory<T> oneOf(final InstanceFactory<T>... factories) {
		return oneOf(Arrays.asList(factories));
	}

	public static <T> InstanceFactory<T> oneOf(final Collection<InstanceFactory<T>> factories) {
		return InstanceAdapters.adapt(org.exparity.test.builder.InstanceFactories.oneOf(InstanceAdapters.adapt(factories)));
	}

}
