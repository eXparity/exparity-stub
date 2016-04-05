package org.exparity.stub.stub;

import static java.lang.reflect.Modifier.isFinal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import org.exparity.stub.core.ValueFactory;
import org.exparity.stub.random.RandomBuilder;
import org.exparity.stub.stub.Stub.CollectionSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stewart Bissett
 */
public class StubFactory {

    private static final Logger LOG = LoggerFactory.getLogger(StubFactory.class);

    private final ProxyFactory proxyFactory = new ProxyFactory();

    @SuppressWarnings("rawtypes")
    public <T> T createPrototype(final Class<T> type,
            final Map<Class<?>, ValueFactory> types,
            final CollectionSize collectionSize) {
        if (type.isEnum()) {
            return RandomBuilder.aRandomInstanceOf(type);
        } else if (isFinal(type.getModifiers())) {
            throw new IllegalArgumentException("Final classes cannot be prototyped");
        } else if (isGenericType(type)) {
            throw new IllegalArgumentException(
                    "Use Expectamundo.prototype(final TypeReference<T> typeRef) method to create prototypes for generic types. See javadocs on method for example.");
        } else {
            T proxy = createProxy(new Stub<T>(type, getGenericTypeArguments(type), this, types, collectionSize));
            LOG.info("Proxied {} using [{}]", type.getSimpleName(), proxy);
            return proxy;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object createPrototype(final Class<?> type,
            final Map<Class<?>, ValueFactory> types,
            final Map<String, Type> typeMap,
            final CollectionSize collectionSize) {
        if (isFinal(type.getModifiers())) {
            throw new IllegalArgumentException("Final classes cannot be prototyped");
        }
        Stub prototype = new Stub(type, typeMap, this, types, collectionSize);
        Object proxy = createProxy(prototype);
        LOG.info("Proxied {} using [{}]", type.getSimpleName(), proxy);
        return proxy;
    }

    private <T> T createProxy(final Stub<T> prototype) {
        return proxyFactory.createProxy(prototype.getRawType(), prototype);
    }

    private Map<String, Class<?>> getGenericTypeArguments(final Class<?> type) {
        if (!isGenericType(type) && type.getGenericSuperclass() instanceof ParameterizedType) {
            return getGenericTypeArguments(type.getGenericSuperclass());
        } else {
            return new HashMap<>();
        }
    }

    private Map<String, Class<?>> getGenericTypeArguments(final Type genericType) {
        Map<String, Class<?>> parameterizedTypes = new HashMap<String, Class<?>>();
        if (genericType instanceof ParameterizedType) {
            Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            TypeVariable<?>[] typeKeys = ((Class<?>) ((ParameterizedType) genericType).getRawType())
                    .getTypeParameters();
            for (int i = 0; i < typeKeys.length; ++i) {
                parameterizedTypes.put(typeKeys[i].getName(), (Class<?>) typeArguments[i]);
            }
            return parameterizedTypes;
        }
        return parameterizedTypes;
    }

    private <T> boolean isGenericType(final Class<T> type) {
        return type.getTypeParameters() != null && type.getTypeParameters().length > 0;
    }
}
