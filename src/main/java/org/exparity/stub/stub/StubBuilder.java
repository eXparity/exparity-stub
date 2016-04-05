package org.exparity.stub.stub;

import static org.exparity.stub.core.ValueFactories.aNewInstanceOf;
import static org.exparity.stub.core.ValueFactories.oneOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.exparity.stub.core.ValueFactory;
import org.exparity.stub.stub.Stub.CollectionSize;

/**
 * Builder object for instantiating and populating objects which follow the Java
 * beans standards conventions for getter/setters
 *
 * @author Stewart Bissett
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StubBuilder<T> {

    /**
     * Return an instance of a {@link StubBuilder} for the given type which is
     * populated with random values. For example:
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     * 		.path("person.firstName", "Bob")
     * 		.build()
     * </pre>
     *
     * @param type the type to return the {@link StubBuilder} for
     */
    public static <T> StubBuilder<T> aRandomStubOf(final Class<T> type) {
        return new StubBuilder<T>(type);
    }

    private final Map<Class<?>, ValueFactory> types = new HashMap<Class<?>, ValueFactory>();
    private final Class<T> type;
    private CollectionSize collectionSize = new CollectionSize(1, 5);
    private final StubFactory factory = new StubFactory();

    private StubBuilder(final Class<T> type) {
        this.type = type;
    }

    /**
     * Configure the builder to populate any properties of the given type with a
     * value created by the supplied value factory. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .with(Date.class, ValueFactories.oneOf(APR(5,1975), APR(5,1985)))
     *                        .build()
     * </pre>
     *
     * @param type the type of property to use the factory for
     * @param factory the factory to use to create the value
     */
    public <V> StubBuilder<T> with(final Class<V> type, final ValueFactory<V> factory) {
        this.types.put(type, factory);
        return this;
    }

    /**
     * Configure the builder to populate any properties of the given type with a
     * value created by the supplied value factory. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .factory(Date.class, ValueFactories.oneOf(APR(5,1975), APR(5,1985)))
     *                        .build()
     * </pre>
     *
     * @param type the type of property to use the factory for
     * @param factory the factory to use to create the value
     */
    public <X> StubBuilder<T> factory(final Class<X> type, final ValueFactory<X> factory) {
        types.put(type, factory);
        return this;
    }

    /**
     * Configure the builder to set the size of a collections. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeOf(5)
     *                        .build()
     * </pre>
     *
     * @param size the size to create the collections
     */
    public StubBuilder<T> collectionSizeOf(final int size) {
        return collectionSizeRangeOf(size, size);
    }

    /**
     * Configure the builder to set the size of a collections to within a given
     * range. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeRangeOf(2,10)
     *                        .build()
     * </pre>
     *
     * @param min the minimum size to create the collections
     * @param max the maximum size to create the collections
     */
    public StubBuilder<T> collectionSizeRangeOf(final int min, final int max) {
        this.collectionSize = new CollectionSize(min, max);
        return this;
    }

    /**
     * Configure the builder to use a particular subtype when instantiating a
     * super type. For example
     * </p>
     *
     * <pre>
     * ShapeSorter aSorter = BeanBuilder.aRandomInstanceOf(ShapeSorter.class)
     *                        .subtype(Shape.class, Square.class)
     *                        .build()
     * </pre>
     *
     * @param supertype the type of the super type
     * @param subtype the subtype to use when instantiating the super type
     */
    public <X> StubBuilder<T> subtype(final Class<X> supertype, final Class<? extends X> subtype) {
        return with(supertype, oneOf(createInstanceOfFactoriesForTypes(subtype)));
    }

    /**
     * Configure the builder to use any of of a particular subtype when
     * instantiating a super type. For example
     * </p>
     *
     * <pre>
     * ShapeSorter aSorter = BeanBuilder.aRandomInstanceOf(ShapeSorter.class)
     *                        .subtype(Shape.class, Square.class, Circle.class, Triangle.class)
     *                        .build()
     * </pre>
     *
     * @param supertype the type of the super type
     * @param subtypes the subtypes to pick from when instantiating the super
     *            type
     */
    public <X> StubBuilder<T> subtype(final Class<X> supertype, final Class<? extends X>... subtypes) {
        return with(supertype, oneOf(createInstanceOfFactoriesForTypes(subtypes)));
    }

    /**
     * Build the configured instance. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .path("person.firstName", "Bob")
     *                        .path("person.age", oneOf(25,35))
     *                        .build()
     * </pre>
     */
    public T build() {
        return factory.createPrototype(type, types, collectionSize);
    }

    private <X> List<ValueFactory<X>> createInstanceOfFactoriesForTypes(final Class<? extends X>... subtypes) {
        List<ValueFactory<X>> factories = new ArrayList<ValueFactory<X>>();
        for (Class<? extends X> subtype : subtypes) {
            factories.add((ValueFactory<X>) aNewInstanceOf(subtype));
        }
        return factories;
    }
}
