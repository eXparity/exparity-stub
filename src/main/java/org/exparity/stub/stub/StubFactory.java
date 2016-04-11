package org.exparity.stub.stub;

import org.apache.commons.lang.ArrayUtils;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author Stewart Bissett
 */
public class StubFactory {

    private static final Logger LOG = LoggerFactory.getLogger(StubFactory.class);

    public <T> T createPrototype(final StubDefinition definition) {
        if (definition.isFinal()) {
            throw new IllegalArgumentException("Final classes cannot be prototyped");
        } else {
            T proxy = createProxy(new Stub<T>(definition, this));
            LOG.info("Proxied {} using [{}]", definition.describe(), proxy);
            return proxy;
        }
    }

    private <T> T createProxy(final Stub<T> stub) {
        return createProxy(stub.getRawType(), stub);
    }

    public <T> T createProxy(final Class<T> rawType, final MethodInterceptor callback, final Class<?>... interfaces) {
        return createProxyInstance(createProxyType(rawType, callback, interfaces));
    }

    private <T> T createProxyInstance(final Class<T> proxyType) {
        Objenesis instantiatorFactory = new ObjenesisStd();
        ObjectInstantiator<T> instanceFactory = instantiatorFactory.getInstantiatorOf(proxyType);
        T instance = instanceFactory.newInstance();
        LOG.debug("Produce Proxy Instance [{}] for [{}]", System.identityHashCode(instance), proxyType.getName());
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> createProxyType(final Class<T> rawType,
            final MethodInterceptor callback,
            final Class<?>... interfaces) {
        Enhancer classFactory = new Enhancer();
        if (rawType.isInterface()) {
            classFactory.setInterfaces((Class[]) ArrayUtils.add(interfaces, rawType));
        } else {
            classFactory.setSuperclass(rawType);
            if (interfaces.length > 0) {
                classFactory.setInterfaces(interfaces);
            }
        }
        classFactory.setCallbackType(callback.getClass());
        Class<T> proxyType = classFactory.createClass();
        Enhancer.registerCallbacks(proxyType, new Callback[] { callback });
        return proxyType;
    }

}
