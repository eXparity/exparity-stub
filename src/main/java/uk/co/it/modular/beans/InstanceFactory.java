package uk.co.it.modular.beans;

/**
 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 * 
 * @deprecated Moved to {@link org.exparity.test.builder.InstanceFactories.InstanceFactory}
 */
@Deprecated
public interface InstanceFactory<T> {

	/*
	 * @deprecated Moved to {@link org.exparity.test.builder.InstanceFactories.InstanceFactory}
	 */
	@Deprecated
	public T createValue();
}
