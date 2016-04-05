
package org.exparity.stub.stub;

import static org.exparity.stub.core.ValueFactories.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.exparity.stub.core.ValueFactory;
import org.exparity.stub.random.RandomBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@SuppressWarnings("rawtypes")
class Stub<T> implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(Stub.class);

    /**
     * Value object defining a collection bounds
     */
    static class CollectionSize {

        private final int min, max;

        public CollectionSize(final int min, final int max) {
            this.min = min;
            this.max = max;
        }

        public int aRandomSize() {
            if (min == max) {
                return min;
            } else {
                return RandomBuilder.aRandomInteger(min, max);
            }
        }
    }

    private final Map<Class<?>, ValueFactory> types;
    private final Class<T> rawType;
    private final Map<String, Class<?>> typeParameters;
    private final StubFactory factory;
    private final CollectionSize collectionSize;

    public Stub(final Class<T> rawType,
            final Map<String, Class<?>> typeParameters,
            final StubFactory factory,
            final Map<Class<?>, ValueFactory> types,
            final CollectionSize collectionSize) {
        this.types = types;
        this.rawType = rawType;
        this.typeParameters = typeParameters;
        this.factory = factory;
        this.collectionSize = collectionSize;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy)
            throws Throwable {
        if (isProxiableMethod(method)) {
            LOG.info("Intercept [{}]", proxy.getSignature());
            return createValue(getClassForPrototype(method), getGenericTypeArguments(method.getGenericReturnType()));
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }

    public Class<T> getRawType() {
        return rawType;
    }

    private boolean isProxiableMethod(final Method method) {
        switch (method.getName()) {
        case "equals":
        case "iterator":
        case "finalize":
        case "hashCode":
        case "toString":
            return false;
        default:
            return method.getReturnType() != null;
        }
    }

    private Class<?> getClassForPrototype(final Method method) {
        Type genericType = method.getGenericReturnType();
        if (genericType instanceof Class) {
            return (Class<?>) genericType;
        } else if (genericType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericType).getRawType();
        } else if (genericType instanceof TypeVariable<?>) {
            return typeParameters.get(((TypeVariable<?>) genericType).getName());
        } else {
            throw new RuntimeException("Failed to get prototype class for '" + genericType + "'");
        }
    }

    private Class<?> getTypeToCreate(final Type genericType) {
        if (genericType instanceof Class) {
            return (Class<?>) genericType;
        } else if (genericType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericType).getRawType();
        } else {
            throw new RuntimeException("Failed to get protype class for '" + genericType + "'");
        }
    }

    @SuppressWarnings({ "unchecked" })
    private <E> E createValue(final Type genericType, final Map<String, Type> typeMap) {

        Class<?> type = getTypeToCreate(genericType);

        for (Entry<Class<?>, ValueFactory> keyedFactory : types.entrySet()) {
            if (type.isAssignableFrom(keyedFactory.getKey())) {
                ValueFactory factory = keyedFactory.getValue();
                return (E) createValue(factory, type);
            }
        }

        if (type.isArray()) {
            return (E) createArray(type.getComponentType(), typeMap);
        } else {
            if (Map.class.isAssignableFrom(type)) {
                return (E) createMap(typeMap.get("K"), typeMap.get("V"), typeMap, collectionSize.aRandomSize());
            } else if (Set.class.isAssignableFrom(type)) {
                return (E) createSet(typeMap.get("E"), typeMap, collectionSize.aRandomSize());
            } else if (List.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type)) {
                return (E) createList(typeMap.get("E"), typeMap, collectionSize.aRandomSize());
            } else {
                ValueFactory factory = RANDOM_FACTORIES.get(type);
                if (factory != null) {
                    return (E) createValue(factory, type);
                } else if (type.isEnum()) {
                    return (E) aRandomEnum(type).createValue();
                } else {
                    return (E) this.factory.createPrototype(type, types, typeMap, collectionSize);
                }
            }
        }

    }

    private <E> E createValue(final ValueFactory<E> factory, final Class<E> type) {
        return factory != null ? factory.createValue() : null;
    }

    private <E> Object createArray(final Class<E> type, final Map<String, Type> typeParameters) {
        Object array = Array.newInstance(type, collectionSize.aRandomSize());
        for (int i = 0; i < Array.getLength(array); ++i) {
            Array.set(array, i, createValue(type, typeParameters));
        }
        return array;
    }

    private <E> Set<E> createSet(final Type type, final Map<String, Type> typeMap, final int length) {
        Set<E> set = new HashSet<E>();
        for (int i = 0; i < length; ++i) {
            E value = createValue(type, typeMap);
            if (value != null) {
                set.add(value);
            }
        }
        return set;
    }

    private <E> List<E> createList(final Type type, final Map<String, Type> typeMap, final int length) {
        List<E> list = new ArrayList<E>();
        for (int i = 0; i < length; ++i) {
            E value = createValue(type, typeMap);
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    private <K, V> Map<K, V> createMap(final Type keyType,
            final Type valueType,
            final Map<String, Type> typeMap,
            final int length) {
        Map<K, V> map = new HashMap<K, V>();
        for (int i = 0; i < length; ++i) {
            K key = createValue(keyType, typeMap);
            if (key != null) {
                map.put(key, createValue(valueType, typeMap));
            }
        }
        return map;
    }

    @SuppressWarnings("serial")
    private static final Map<Class<?>, ValueFactory> RANDOM_FACTORIES = new HashMap<Class<?>, ValueFactory>() {
        {
            put(Short.class, aRandomShort());
            put(short.class, aRandomShort());
            put(Integer.class, aRandomInteger());
            put(int.class, aRandomInteger());
            put(Long.class, aRandomLong());
            put(long.class, aRandomLong());
            put(Double.class, aRandomDouble());
            put(double.class, aRandomDouble());
            put(Float.class, aRandomFloat());
            put(float.class, aRandomFloat());
            put(Boolean.class, aRandomBoolean());
            put(boolean.class, aRandomBoolean());
            put(Byte.class, aRandomByte());
            put(byte.class, aRandomByte());
            put(Character.class, aRandomChar());
            put(char.class, aRandomChar());
            put(String.class, aRandomString());
            put(BigDecimal.class, aRandomDecimal());
            put(Date.class, aRandomDate());
        }
    };

    private Map<String, Type> getGenericTypeArguments(final Type genericType) {

        if (!(genericType instanceof ParameterizedType)) {
            return Collections.emptyMap();
        }

        ParameterizedType type = (ParameterizedType) genericType;
        Type[] typeArguments = type.getActualTypeArguments();
        TypeVariable<?>[] typeKeys = ((Class<?>) type.getRawType()).getTypeParameters();
        Map<String, Type> parameterizedTypes = new HashMap<>();
        for (int i = 0; i < typeKeys.length; ++i) {
            parameterizedTypes.put(typeKeys[i].getName(), typeArguments[i]);
        }

        return parameterizedTypes;
    }

    @Override
    public String toString() {
        return "Stub [" + rawType.getSimpleName() + "]";
    }
}