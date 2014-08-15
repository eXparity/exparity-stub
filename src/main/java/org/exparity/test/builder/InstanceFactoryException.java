
package org.exparity.test.builder;

/**
 * @author Stewart Bissett
 */
public class InstanceFactoryException extends RuntimeException {

	private static final long serialVersionUID = 3815822809921345204L;

	public InstanceFactoryException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InstanceFactoryException(final String message) {
		super(message);
	}

}
