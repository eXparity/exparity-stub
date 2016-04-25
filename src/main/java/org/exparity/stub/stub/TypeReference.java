
package org.exparity.stub.stub;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Helper class to allow generic type information to be interrogated when constructing a stub
 *
 * @author Stewart.Bissett
 */
public abstract class TypeReference<T> {

    private final Type type;

    protected TypeReference() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new IllegalArgumentException(
                    "TypeReference must be provided with generic type e.g. TypeReference<List<String>>.");
        }
        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        if (this.type instanceof Class) {
            throw new IllegalArgumentException(
                    "TypeReference must be provided with generic type parameter e.g. TypeReference<List<String>>.");
        }
    }

    /**
     * Gets the referenced type.
     */
    public Type getType() {
        return this.type;
    }
}