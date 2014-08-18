package uk.co.it.modular.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Colleciton of adapter to functions to convert {@link ValueFactory} to {@link org.exparity.test.builder.ValueFactories.ValueFactory}
 * 
 * @author Stewart Bissett
 */
@Deprecated
abstract class InstanceAdapters {

	static <T> InstanceFactory<T> adapt(final org.exparity.test.builder.ValueFactory<T> from) {
		return new InstanceFactory<T>() {

			public T createValue() {
				return from.createValue();
			}
		};
	}

	static <T> Collection<org.exparity.test.builder.ValueFactory<T>> adapt(final Collection<InstanceFactory<T>> from) {
		List<org.exparity.test.builder.ValueFactory<T>> adapted = new ArrayList<org.exparity.test.builder.ValueFactory<T>>();
		for (InstanceFactory<T> toadapt : from) {
			adapted.add(InstanceAdapters.adapt(toadapt));
		}
		return adapted;
	}

	static <T> org.exparity.test.builder.ValueFactory<T> adapt(final InstanceFactory<T> from) {
		return new org.exparity.test.builder.ValueFactory<T>() {

			public T createValue() {
				return from.createValue();
			}
		};
	}

	static <A> ArrayFactory<A> adapt(final org.exparity.test.builder.ArrayFactory<A> from) {
		return new ArrayFactory<A>() {

			public A[] createValue(final Class<A> type, final int size) {
				return from.createValue(type, size);
			}
		};
	}

}
