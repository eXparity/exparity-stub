/*
 * Copyright (c) Modular IT Limited.
 */

package uk.co.it.modular.beans;

/**
 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 */
public interface ArrayFactory<T> {

	public T[] createValue(final Class<T> type, final int size);
}
