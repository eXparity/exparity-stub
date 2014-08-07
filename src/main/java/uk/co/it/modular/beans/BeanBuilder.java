
package uk.co.it.modular.beans;

import static uk.co.it.modular.beans.InstanceFactories.theValue;

/**
 * Builder object for instantiating and populating objects which follow the Java beans standards conventions for getter/setters
 * 
 * @author Stewart Bissett
 * 
 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
 */
@Deprecated
public class BeanBuilder<T> {

	/**
	 * Return an instance of a {@link BeanBuilder} for the given type which can then be populated with values either manually or automatically. For example:
	 * 
	 * <pre>
	 * BeanBuilder.anInstanceOf(Person.class).build();
	 * </pre>
	 * @param type the type to return the {@link BeanBuilder} for
	 * 
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public static <T> BeanBuilder<T> anInstanceOf(final Class<T> type) {
		return new BeanBuilder<T>(org.exparity.test.builder.BeanBuilder.anInstanceOf(type));
	}

	/**
	 * Return an instance of a {@link BeanBuilder} for the given type which can then be populated with values either manually or automatically. For example:
	 * 
	 * <pre>
	 * BeanBuilder.anInstanceOf(Person.class, &quot;person&quot;).build();
	 * </pre>
	 * @param type the type to return the {@link BeanBuilder} for
	 * @param rootName the name give to the root entity when referencing paths
	 * 
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public static <T> BeanBuilder<T> anInstanceOf(final Class<T> type, final String rootName) {
		return new BeanBuilder<T>(org.exparity.test.builder.BeanBuilder.anInstanceOf(type, rootName));
	}

	/**
	 * Return an instance of a {@link BeanBuilder} for the given type which is populated with empty objects but collections, maps, etc which have empty objects. For example:
	 * 
	 * <pre>
	 * BeanBuilder.anEmptyInstanceOf(Person.class).build();
	 * </pre>
	 * @param type the type to return the {@link BeanBuilder} for
	 * 
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public static <T> BeanBuilder<T> anEmptyInstanceOf(final Class<T> type) {
		return new BeanBuilder<T>(org.exparity.test.builder.BeanBuilder.anEmptyInstanceOf(type));
	}

	/**
	 * Return an instance of a {@link BeanBuilder} for the given type which is populated with empty objects but collections, maps, etc which have empty objects. For example:
	 * 
	 * <pre>
	 * BeanBuilder.anEmptyInstanceOf(Person.class).build();
	 * </pre>
	 * @param type the type to return the {@link BeanBuilder} for
	 * @param rootName the name give to the root entity when referencing paths
	 * 
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public static <T> BeanBuilder<T> anEmptyInstanceOf(final Class<T> type, final String rootName) {
		return new BeanBuilder<T>(org.exparity.test.builder.BeanBuilder.anEmptyInstanceOf(type, rootName));
	}

	/**
	 * Return an instance of a {@link BeanBuilder} for the given type which is populated with random values. For example:
	 * 
	 * <pre>
	 * BeanBuilder.aRandomInstanceOf(Person.class).build();
	 * </pre>
	 * @param type the type to return the {@link BeanBuilder} for
	 * 
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public static <T> BeanBuilder<T> aRandomInstanceOf(final Class<T> type) {
		return new BeanBuilder<T>(org.exparity.test.builder.BeanBuilder.aRandomInstanceOf(type));
	}

	/**
	 * Return an instance of a {@link BeanBuilder} for the given type which is populated with random values. For example:
	 * 
	 * <pre>
	 * BeanBuilder.aRandomInstanceOf(Person.class).build();
	 * </pre>
	 * @param type the type to return the {@link BeanBuilder} for
	 * 
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public static <T> BeanBuilder<T> aRandomInstanceOf(final Class<T> type, final String rootName) {
		return new BeanBuilder<T>(org.exparity.test.builder.BeanBuilder.aRandomInstanceOf(type, rootName));
	}

	private final org.exparity.test.builder.BeanBuilder<T> delegate;

	private BeanBuilder(final org.exparity.test.builder.BeanBuilder<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> with(final String propertyOrPathName, final Object value) {
		return with(propertyOrPathName, theValue(value));
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public <V> BeanBuilder<T> with(final Class<V> type, final InstanceFactory<V> factory) {
		delegate.with(type, InstanceAdapters.adapt(factory));
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> with(final String propertyOrPathName, final InstanceFactory<?> factory) {
		withPath(propertyOrPathName, factory);
		withProperty(propertyOrPathName, factory);
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> withPropertyValue(final String propertyName, final Object value) {
		return withProperty(propertyName, value);
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> withProperty(final String propertyName, final Object value) {
		return withProperty(propertyName, theValue(value));
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> withProperty(final String propertyName, final InstanceFactory<?> factory) {
		this.delegate.withProperty(propertyName, InstanceAdapters.adapt(factory));
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> excludeProperty(final String propertyName) {
		this.delegate.excludeProperty(propertyName);
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> withPathValue(final String path, final Object value) {
		return withPath(path, value);
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> withPath(final String path, final Object value) {
		return withPath(path, theValue(value));
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> withPath(final String path, final InstanceFactory<?> factory) {
		this.delegate.withPath(path, InstanceAdapters.adapt(factory));
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> excludePath(final String path) {
		this.delegate.excludePath(path);
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> aCollectionSizeOf(final int size) {
		return aCollectionSizeRangeOf(size, size);
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public BeanBuilder<T> aCollectionSizeRangeOf(final int min, final int max) {
		this.delegate.aCollectionSizeRangeOf(min, max);
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public <X> BeanBuilder<T> usingType(final Class<X> klass, final Class<? extends X> subtypes) {
		this.delegate.usingType(klass, subtypes);
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public <X> BeanBuilder<T> usingType(final Class<X> klass, final Class<? extends X>... subtypes) {
		this.delegate.usingType(klass, subtypes);
		return this;
	}

	/**
	 * @deprecated Use {@link org.exparity.test.builder.BeanBuilder}
	 */
	@Deprecated
	public T build() {
		return delegate.build();
	}

}
