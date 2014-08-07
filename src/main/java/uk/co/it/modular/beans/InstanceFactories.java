/*
 * Copyright (c) Modular IT Limited.
 */

package uk.co.it.modular.beans;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import org.exparity.test.builder.InstanceBuilder;

/**
 * @author Stewart Bissett
 * 
 * @deprecated Moved to {@link org.exparity.test.builder.InstanceBuilder}
 */
@Deprecated
public abstract class InstanceFactories {

	public static <T> InstanceFactory<T> theValue(final T value) {
		return InstanceAdapters.adapt(InstanceBuilder.theValue(value));
	}

	public static InstanceFactory<Object> aNullValue() {
		return InstanceAdapters.adapt(InstanceBuilder.aNullValue());
	}

	public static InstanceFactory<String> aRandomString() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomString());
	}

	public static InstanceFactory<Integer> aRandomInteger() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomInteger());
	}

	public static InstanceFactory<Short> aRandomShort() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomShort());
	}

	public static InstanceFactory<Long> aRandomLong() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomLong());
	}

	public static InstanceFactory<Double> aRandomDouble() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomDouble());
	}

	public static InstanceFactory<Float> aRandomFloat() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomFloat());
	}

	public static InstanceFactory<Boolean> aRandomBoolean() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomBoolean());
	}

	public static InstanceFactory<Date> aRandomDate() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomDate());
	}

	public static InstanceFactory<BigDecimal> aRandomDecimal() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomDecimal());
	}

	public static InstanceFactory<Byte> aRandomByte() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomByte());
	}

	public static InstanceFactory<Character> aRandomChar() {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomChar());
	}

	public static <E> InstanceFactory<E> aRandomEnum(final Class<E> enumType) {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomEnum(enumType));
	}

	public static <A> ArrayFactory<A> aRandomArrayOf(final InstanceFactory<A> typeFactory) {
		return InstanceAdapters.adapt(InstanceBuilder.aRandomArrayOf(InstanceAdapters.adapt(typeFactory)));
	}

	public static <T> InstanceFactory<T> aNewInstanceOf(final Class<T> type) {
		return InstanceAdapters.adapt(InstanceBuilder.aNewInstanceOf(type));
	}

	public static <T> InstanceFactory<T> oneOf(final InstanceFactory<T>... factories) {
		return oneOf(Arrays.asList(factories));
	}

	public static <T> InstanceFactory<T> oneOf(final Collection<InstanceFactory<T>> factories) {
		return InstanceAdapters.adapt(InstanceBuilder.oneOf(InstanceAdapters.adapt(factories)));
	}

}
