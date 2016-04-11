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

public class StubDefinition {

    private final Map<String, Type> typeParameters = new HashMap<>();
    private final Type type;
    private final Map<Class<?>, ValueFactory<?>> overrides = new HashMap<>();
    private int collectionSizeMin = 1, collectionSizeMax = 5;

    public StubDefinition(final Type type, final StubDefinition parent) {
        this(type);
        overrides.putAll(parent.overrides);
        collectionSizeMin = parent.collectionSizeMin;
        collectionSizeMax = parent.collectionSizeMax;
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

    public Class<?> getActualType() {
        return getActualType(type);
    }

    public Type getRawType() {
        return type;
    }

    public void setCollectionSizeRange(final int min, final int max) {
        collectionSizeMin = min;
        collectionSizeMax = max;
    }

    public <T> void addOverride(final Class<T> type, final ValueFactory<T> factory) {
        overrides.put(type, factory);
    }

    public Class<?> getActualType(final Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable<?>) {
            return getActualType(getTypeByParameter(((TypeVariable<?>) type).getName()));
        } else {
            throw new RuntimeException("Failed to get actual type for '" + type + "'");
        }
    }

    public Type getTypeByParameter(final String typeVariable) {
        Type type = typeParameters.get(typeVariable);
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

//    private static Map<String, Type> getTypeArguments(final Class<?> type) {
//        if (isGenericType(type) && type.getGenericSuperclass() instanceof ParameterizedType) {
//            return getTypeArguments((ParameterizedType) type.getGenericSuperclass());
//        } else {
//            return new HashMap<>();
//        }
//    }

    private static <T> boolean isGenericType(final Class<T> type) {
        return type.getTypeParameters() != null && type.getTypeParameters().length > 0;
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
        return (collectionSizeMin == collectionSizeMax) ? collectionSizeMin : aRandomInteger(collectionSizeMin,
                collectionSizeMax);
    }

    public Optional<ValueFactory<?>> getOverrideValueFactoryByType(final Class<?> type) {
        for (Entry<Class<?>, ValueFactory<?>> keyedFactory : overrides.entrySet()) {
            if (type.isAssignableFrom(keyedFactory.getKey())) {
                return Optional.of(keyedFactory.getValue());
            }
        }
        return Optional.empty();
    }

}
