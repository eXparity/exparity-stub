/*
 * Copyright (c) Modular IT Limited.
 */
package org.exparity.test.builder;

/**
 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 */
public interface ValueFactory<T> {

	/**
	 * Create a value of type T.
	 * 
	 * @return an instance of type T
	 */
	public T createValue();
}