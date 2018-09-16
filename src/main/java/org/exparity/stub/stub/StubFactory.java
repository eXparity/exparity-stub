package org.exparity.stub.stub;

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
class StubFactory {

    private static final Logger LOG = LoggerFactory.getLogger(StubFactory.class);

    public <T> T createStub(final StubDefinition<T> definition) {
        if (definition.isFinal()) {
            throw new FinalClassException(definition.getActualType());
        } else {
            T proxy = createProxy(new Stub<>(definition, this));
            LOG.debug("Create Proxy [{}] for [{}]", proxy, definition.describe());
            return proxy;
        }
    }

    private <T> T createProxy(final Stub<T> stub) {
        return createProxy(stub.getRawType(), stub);
    }

    public <T> T createProxy(final Class<T> rawType, final MethodInterceptor callback) {
        return createProxyInstance(createProxyType(rawType, callback));
    }

    private <T> T createProxyInstance(final Class<T> proxyType) {
        Objenesis instantiatorFactory = new ObjenesisStd();
        ObjectInstantiator<T> instanceFactory = instantiatorFactory.getInstantiatorOf(proxyType);
        T instance = instanceFactory.newInstance();
        LOG.debug("Produce Proxy Instance [{}] for [{}]", System.identityHashCode(instance), proxyType.getName());
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> createProxyType(final Class<T> rawType, final MethodInterceptor callback) {
        Enhancer classFactory = new Enhancer();
        if (rawType.isInterface()) {
            classFactory.setInterfaces(new Class[] { rawType });
        } else {
            classFactory.setSuperclass(rawType);
        }
        classFactory.setCallbackType(callback.getClass());
        Class<T> proxyType = classFactory.createClass();
        Enhancer.registerCallbacks(proxyType, new Callback[] { callback });
        return proxyType;
    }

}
