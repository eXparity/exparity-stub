package uk.co.it.modular.beans;

/**
 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 * 
 * @deprecated See {@link org.exparity.test.builder.InstanceFactories.InstanceArrayFactory}
 */
@Deprecated
public interface ArrayFactory<T> {

	/**
	 * @deprecated See {@link org.exparity.test.builder.InstanceFactories.InstanceArrayFactory}
	 */
	@Deprecated
	public T[] createValue(final Class<T> type, final int size);
}
