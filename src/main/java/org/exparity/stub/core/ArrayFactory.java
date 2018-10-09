/*
 * Copyright (c) Modular IT Limited.
 */
package org.exparity.stub.core;

import org.exparity.stub.bean.BeanBuilder;

/**
 * Interface to be implemented by classes which can provide arrays of values to a {@link BeanBuilder}
 *
 * @author Stewart Bissett
 *
 * @deprecated No longer supported
 */
@FunctionalInterface
@Deprecated
public interface ArrayFactory<T> {

    /**
     * Create an array of type T.
     * @param type the scalar type the array will be composed of
     * @param size the size of the array to create
     * @return an array of type T
     */
    public T[] createValue(final Class<T> type, final int size);
}