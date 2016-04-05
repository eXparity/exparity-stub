
package org.exparity.stub.stub;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Stewart Bissett
 */

/**
 * Helper class to allow generic type inforrmation to be interrogated when constructing a prototype.
 *
 * @author Stewart.Bissett
 */
public abstract class TypeReference<T> {

	private final Type type;

	protected TypeReference() {
		Type superclass = getClass().getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new IllegalArgumentException("TypeReference must be provided with generic type parameter e.g. TypeReference<List<String>>.");
		}
		this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
	}

	/**
	 * Gets the referenced type.
	 */
	public Type getType() {
		return this.type;
	}
}