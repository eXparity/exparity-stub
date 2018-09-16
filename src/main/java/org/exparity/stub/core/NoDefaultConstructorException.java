package org.exparity.stub.core;

/**
 * Typed exception so it's explicit when failure is due to a missing default constructor
 *
 * @author Stewart Bissett
 */
public class NoDefaultConstructorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoDefaultConstructorException(final Class<?> type, final InstantiationException e) {
        super("Class '" + type.getName() + "' has no default constructor", e);
    }
}
