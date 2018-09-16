package org.exparity.stub.stub;

/**
 * Typed exception so it's explicit when failure is due to a missing default constructor
 *
 * @author Stewart Bissett
 */
public class FinalClassException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FinalClassException(final Class<?> type) {
        super("Unable to proxy final Class '" + type.getName() + "'");
    }
}
