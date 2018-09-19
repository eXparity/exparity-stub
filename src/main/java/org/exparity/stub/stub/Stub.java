
package org.exparity.stub.stub;

import static java.lang.System.identityHashCode;
import static org.exparity.stub.core.ValueFactories.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.exparity.stub.core.ValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class Stub<T> implements MethodInterceptor {

    @SuppressWarnings({ "serial", "rawtypes" })
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
            put(LocalDate.class, aRandomLocalDate());
            put(LocalTime.class, aRandomLocalTime());
            put(LocalDateTime.class, aRandomLocalDateTime());
            put(ZonedDateTime.class, aRandomZonedDateTime());
            put(Instant.class, aRandomInstant());
        }
    };

    private static final Logger LOG = LoggerFactory.getLogger(Stub.class);

    private final StubDefinition<T> definition;
    private final StubFactory factory;

    public Stub(final StubDefinition<T> definition, final StubFactory factory) {
        this.definition = definition;
        this.factory = factory;
    }

    private final ConcurrentMap<Integer, ConcurrentMap<Method, Object>> cache = new ConcurrentHashMap<>();

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy)
            throws Throwable {
        if (isEquals(method)) {
            // equals cannot reliably be ignored or proxied. If ignored then an implementation
            // which relies on local variables will throw NPE whereas if returning a random boolean
            // then the stub can't function in data structures which require equals to function
            // The compromise is to to use the default equals method where the objects are the same instance
            return args.length == 1 && obj == args[0];
        } else if (isHashCode(method)) {
            // Hashcode cannot reliably be ignored or proxied. If ignored then an implementation
            // which relies on local variables will throw NPE whereas if returning a random number
            // then the stub can't function in data structures which require consistent hashcodes
            // The compromise is to to use the identity hashcode
            return System.identityHashCode(obj);
        } else if (isProxiableMethod(method)) {
            LOG.debug("Proxy method [{}]", proxy.getSignature());
            cache.putIfAbsent(identityHashCode(obj), new ConcurrentHashMap<Method, Object>());
            ConcurrentMap<Method, Object> cachedMethod = cache.get(identityHashCode(obj));
            cachedMethod.computeIfAbsent(method,
                    (m) -> createValue(new StubDefinition<>(m.getGenericReturnType(),
                            this.definition)));
            return cachedMethod.get(method);
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }

    public Class<T> getRawType() {
        return this.definition.getActualType();
    }

    @SuppressWarnings({ "unchecked" })
    private <E> E createValue(final StubDefinition<E> definition) {

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
                ValueFactory<?> factory = RANDOM_FACTORIES.get(type);
                if (factory != null) {
                    return (E) factory.createValue();
                } else if (type.isEnum()) {
                    return (E) aRandomEnum(type).createValue();
                } else if (type == Void.TYPE) {
                    return (E) Void.TYPE;
                } else {
                    return this.factory.createStub(definition);
                }
            }
        }

    }

    private <E> Object createArray(final Class<E> type) {
        Object array = Array.newInstance(type, this.definition.aRandomCollectionSize());
        for (int i = 0; i < Array.getLength(array); ++i) {
            Array.set(array,
                    i,
                    createValue(new StubDefinition<E>(type,
                            this.definition)));
        }
        return array;
    }

    private <E> Set<E> createSet(final Type type, final int length) {
        Set<E> set = new HashSet<>();
        for (int i = 0; i < length; ++i) {
            E value = createValue(new StubDefinition<E>(type,
                    this.definition));
            if (value != null) {
                set.add(value);
            }
        }
        return set;
    }

    private <E> List<E> createList(final Type type, final int length) {
        List<E> list = new ArrayList<>();
        for (int i = 0; i < length; ++i) {
            E value = createValue(new StubDefinition<E>(type,
                    this.definition));
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    private <K, V> Map<K, V> createMap(final Type keyType, final Type valueType, final int length) {
        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < length; ++i) {
            K key = createValue(new StubDefinition<K>(keyType,
                    this.definition));
            if (key != null) {
                map.put(key,
                        createValue(new StubDefinition<V>(valueType,
                                this.definition)));
            }
        }
        return map;
    }

    private boolean isEquals(final Method method) {
        return "equals".equals(method.getName());
    }

    private boolean isHashCode(final Method method) {
        return "hashcode".equals(method.getName());
    }

    private boolean isProxiableMethod(final Method method) {
        switch (method.getName()) {
        case "iterator":
        case "finalize":
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