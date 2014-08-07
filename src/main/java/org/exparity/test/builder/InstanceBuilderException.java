
package org.exparity.test.builder;

/**
 * @author Stewart Bissett
 */
public class InstanceBuilderException extends RuntimeException {

	private static final long serialVersionUID = 3815822809921345204L;

	public InstanceBuilderException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InstanceBuilderException(final String message) {
		super(message);
	}

}
