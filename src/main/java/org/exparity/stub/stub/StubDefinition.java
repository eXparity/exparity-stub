package org.exparity.stub.stub;

import static org.exparity.stub.random.RandomBuilder.aRandomInteger;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.exparity.stub.core.ValueFactory;

/**
 * Encapsulate the definition of the stub to be created by the {@link StubFactory}
 *
 * @author Stewart Bissett
 */
class StubDefinition<T> {

    private final Map<String, Type> typeParameters = new HashMap<>();
    private final Type type;
    private final Map<Class<?>, ValueFactory<?>> overrides = new HashMap<>();
    private int collectionSizeMin = 1, collectionSizeMax = 5;

    public StubDefinition(final Type type, final StubDefinition<?> parent) {
        this.type = type;
        this.typeParameters.putAll(parent.typeParameters);
        this.typeParameters.putAll(getTypeArguments(type));
        this.overrides.putAll(parent.overrides);
        this.collectionSizeMin = parent.collectionSizeMin;
        this.collectionSizeMax = parent.collectionSizeMax;
    }

    public StubDefinition(final Type type) {
        this(type, getTypeArguments(type));
    }

    public StubDefinition(final Class<?> type) {
        this(type, getTypeArguments(type));
    }

    private StubDefinition(final Type type, final Map<String, Type> typeParameters) {
        this.typeParameters.putAll(typeParameters);
        this.type = type;
    }

    public boolean isFinal() {
        return Modifier.isFinal(getActualType().getModifiers());
    }

    public Class<T> getActualType() {
        return getActualType(this.type);
    }

    public Type getRawType() {
        return this.type;
    }

    public void setCollectionSizeRange(final int min, final int max) {
        this.collectionSizeMin = min;
        this.collectionSizeMax = max;
    }

    public <O> void addOverride(final Class<O> type, final ValueFactory<O> factory) {
        this.overrides.put(type, factory);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getActualType(final Type type) {
        if (type instanceof Class) {
            return (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable<?>) {
            return getActualType(getTypeByParameter(((TypeVariable<?>) type).getName()));
        } else {
            throw new RuntimeException("Failed to get actual type for '" + type + "'");
        }
    }

    public Type getTypeByParameter(final String typeVariable) {
        Type type = this.typeParameters.get(typeVariable);
        if (type != null) {
            return type;
        } else {
            throw new IllegalArgumentException("Unknown type variable '" + typeVariable + "' in '" + this.type + "'");
        }
    }

    private static Map<String, Type> getTypeArguments(final Type type) {
        if (type instanceof ParameterizedType) {
            return getTypeArguments((ParameterizedType) type);
        } else {
            return new HashMap<>();
        }
    }

    private static Map<String, Type> getTypeArguments(final ParameterizedType genericType) {
        ParameterizedType type = genericType;
        Type[] typeArguments = type.getActualTypeArguments();
        TypeVariable<?>[] typeKeys = ((Class<?>) type.getRawType()).getTypeParameters();
        Map<String, Type> parameterizedTypes = new HashMap<>();
        for (int i = 0; i < typeKeys.length; ++i) {
            parameterizedTypes.put(typeKeys[i].getName(), typeArguments[i]);
        }
        return parameterizedTypes;
    }

    public String describe() {
        return getActualType().getSimpleName();
    }

    public int aRandomCollectionSize() {
        return (this.collectionSizeMin == this.collectionSizeMax) ? this.collectionSizeMin : aRandomInteger(
                this.collectionSizeMin, this.collectionSizeMax);
    }

    public Optional<ValueFactory<?>> getOverrideValueFactoryByType(final Class<?> type) {
        for (Entry<Class<?>, ValueFactory<?>> keyedFactory : this.overrides.entrySet()) {
            if (type.isAssignableFrom(keyedFactory.getKey())) {
                return Optional.of(keyedFactory.getValue());
            }
        }
        return Optional.empty();
    }

}
