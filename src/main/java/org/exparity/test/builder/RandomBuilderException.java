
package org.exparity.test.builder;

/**
 * @author Stewart Bissett
 */
public class RandomBuilderException extends RuntimeException {

	private static final long serialVersionUID = 3915822809921345204L;

	public RandomBuilderException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RandomBuilderException(final String message) {
		super(message);
	}

}
