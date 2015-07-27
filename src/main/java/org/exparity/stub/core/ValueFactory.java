/*
 * Copyright (c) Modular IT Limited.
 */
package org.exparity.stub.core;

/**
 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 */
@FunctionalInterface
public interface ValueFactory<T> {

	/**
	 * Create a value of type T.
	 * 
	 * @return an instance of type T
	 */
	public T createValue();
}