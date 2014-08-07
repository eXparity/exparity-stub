/*
 * Copyright (c) Modular IT Limited.
 */

package uk.co.it.modular.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.exparity.test.builder.InstanceBuilder.InstanceArrayFactory;

/**
 * Colleciton of adapter to functions to convert {@link InstanceFactory} to {@link org.exparity.test.builder.InstanceBuilder.InstanceFactory}
 * 
 * @author Stewart Bissett
 */
@SuppressWarnings("deprecation")
abstract class InstanceAdapters {

	static <T> InstanceFactory<T> adapt(final org.exparity.test.builder.InstanceBuilder.InstanceFactory<T> from) {
		return new InstanceFactory<T>() {

			public T createValue() {
				return from.createValue();
			}
		};
	}

	static <T> Collection<org.exparity.test.builder.InstanceBuilder.InstanceFactory<T>> adapt(final Collection<InstanceFactory<T>> from) {
		List<org.exparity.test.builder.InstanceBuilder.InstanceFactory<T>> adapted = new ArrayList<org.exparity.test.builder.InstanceBuilder.InstanceFactory<T>>();
		for (InstanceFactory<T> toadapt : from) {
			adapted.add(InstanceAdapters.adapt(toadapt));
		}
		return adapted;
	}

	static <T> org.exparity.test.builder.InstanceBuilder.InstanceFactory<T> adapt(final InstanceFactory<T> from) {
		return new org.exparity.test.builder.InstanceBuilder.InstanceFactory<T>() {

			public T createValue() {
				return from.createValue();
			}
		};
	}

	static <A> ArrayFactory<A> adapt(final InstanceArrayFactory<A> from) {
		return new ArrayFactory<A>() {

			public A[] createValue(final Class<A> type, final int size) {
				return from.createValue(type, size);
			}
		};
	}

}
