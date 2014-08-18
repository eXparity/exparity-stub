package uk.co.it.modular.beans;

/**
 * Interface to be implemented by classes which can provide values to a {@link BeanBuilder}
 * 
 * @author Stewart Bissett
 * 
 * @deprecated Moved to {@link org.exparity.test.builder.ValueFactories.ValueFactory}
 */
@Deprecated
public interface InstanceFactory<T> {

	/*
	 * @deprecated Moved to {@link org.exparity.test.builder.ValueFactories.ValueFactory}
	 */
	@Deprecated
	public T createValue();
}
