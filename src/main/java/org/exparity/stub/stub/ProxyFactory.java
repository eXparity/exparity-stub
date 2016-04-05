/**
 * 
 */
package org.exparity.stub.stub;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import org.apache.commons.lang.ArrayUtils;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for creating a proxy instance
 * 
 * @author Stewart Bissett
 */
public class ProxyFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ProxyFactory.class);

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
