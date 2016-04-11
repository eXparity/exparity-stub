
package org.exparity.stub.stub;

import static org.exparity.stub.core.ValueFactories.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.exparity.stub.core.ValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@SuppressWarnings("rawtypes")
class Stub<T> implements MethodInterceptor {

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

    private static final Logger LOG = LoggerFactory.getLogger(Stub.class);

    private final StubDefinition definition;
    private final StubFactory factory;

    public Stub(final StubDefinition definition, final StubFactory factory) {
        this.definition = definition;
        this.factory = factory;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy)
            throws Throwable {
        if (isProxiableMethod(method)) {
            LOG.info("Intercept [{}]", proxy.getSignature());
            return createValue(new StubDefinition(method.getGenericReturnType(), this.definition));
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }

    @SuppressWarnings("unchecked")
    public Class<T> getRawType() {
        return (Class<T>) this.definition.getActualType();
    }

    @SuppressWarnings({ "unchecked" })
    private <E> E createValue(final StubDefinition definition) {

        Class<?> type = definition.getActualType();
        Optional<ValueFactory<?>> override = definition.getOverrideValueFactoryByType(type);
        if (override.isPresent()) {
            return (E) override.get().createValue();
        }

        if (type.isArray()) {
            return (E) createArray(type.getComponentType());
        } else {
            if (Map.class.isAssignableFrom(type)) {
                Type keyType = definition.getTypeByParameter("K");
                Type valueType = definition.getTypeByParameter("V");
                return (E) createMap(keyType, valueType, definition.aRandomCollectionSize());
            } else if (Set.class.isAssignableFrom(type)) {
                Type elementType = definition.getTypeByParameter("E");
                return (E) createSet(elementType, definition.aRandomCollectionSize());
            } else if (List.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type)) {
                Type elementType = definition.getTypeByParameter("E");
                return (E) createList(elementType, definition.aRandomCollectionSize());
            } else {
                ValueFactory factory = RANDOM_FACTORIES.get(type);
                if (factory != null) {
                    return (E) factory.createValue();
                } else if (type.isEnum()) {
                    return (E) aRandomEnum(type).createValue();
                } else {
                    return (E) this.factory.createStub(definition);
                }
            }
        }

    }

    private <E> Object createArray(final Class<E> type) {
        Object array = Array.newInstance(type, this.definition.aRandomCollectionSize());
        for (int i = 0; i < Array.getLength(array); ++i) {
            Array.set(array, i, createValue(new StubDefinition(type, this.definition)));
        }
        return array;
    }

    private <E> Set<E> createSet(final Type type, final int length) {
        Set<E> set = new HashSet<E>();
        for (int i = 0; i < length; ++i) {
            E value = createValue(new StubDefinition(type, this.definition));
            if (value != null) {
                set.add(value);
            }
        }
        return set;
    }

    private <E> List<E> createList(final Type type, final int length) {
        List<E> list = new ArrayList<E>();
        for (int i = 0; i < length; ++i) {
            E value = createValue(new StubDefinition(type, this.definition));
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    private <K, V> Map<K, V> createMap(final Type keyType, final Type valueType, final int length) {
        Map<K, V> map = new HashMap<K, V>();
        for (int i = 0; i < length; ++i) {
            K key = createValue(new StubDefinition(keyType, this.definition));
            if (key != null) {
                map.put(key, createValue(new StubDefinition(valueType, this.definition)));
            }
        }
        return map;
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

    @Override
    public String toString() {
        return "Stub [" + this.definition.describe() + "]";
    }
}