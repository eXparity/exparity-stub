package org.exparity.stub.stub;

import static org.exparity.stub.core.ValueFactories.aNewInstanceOf;
import static org.exparity.stub.core.ValueFactories.oneOf;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.exparity.stub.core.ValueFactory;

/**
 * Builder object for creating a stub object which returns random values for all
 * calls
 *
 * @author Stewart Bissett
 */
@SuppressWarnings("unchecked")
public class StubBuilder<T> {

    /**
     * Return an instance of a {@link StubBuilder} for the given type which is
     * populated with random values. For example:
     *
     * <pre>
     * Person aPerson = StubBuilder.aRandomInstanceOf(Person.class).build()
     * </pre>
     *
     * @param type the type to return the {@link StubBuilder} for
     */
    public static <T> StubBuilder<T> aRandomStubOf(final Class<T> type) {
        if (isGenericType(type)) {
            throw new IllegalArgumentException(
                    "Use StubBuilder.aRandomStubOf(final TypeReference<T> typeRef) method to create prototypes for generic types. See javadocs on method for example.");
        }
        return new StubBuilder<T>(type);
    }

    /**
     * Create a new prototype instance against a generic type against which
     * expectations can be set. For example
     * </p>
     *
     * <pre>
     * List&lt;String&gt; expected = Expectamundo.prototype(new TypeReference&lt;List&lt;String&gt;&gt;(){})
     * </pre>
     *
     * @param typeRef An instance of a {@link TypeReference} parameterized with
     *            the type to prototype
     * @param <T> The class of the prototype to create
     * @return A new instance of the type against which expectations can be set
     */

    /**
     * Return an instance of a {@link StubBuilder} for the given generic type
     * which is populated with random values. For example:
     *
     * <pre>
     * MyType<String> value = StubBuilder.aRandomInstanceOf(new TypeReference&lt;MyType&lt;String&gt;&gt;(){})
     *      .build()
     * </pre>
     *
     * @param type the type to return the {@link StubBuilder} for
     */
    public static <T> StubBuilder<T> aRandomStubOf(final TypeReference<T> type) {
        return new StubBuilder<T>(type.getType());
    }

    private final StubDefinition<T> definition;
    private final StubFactory factory = new StubFactory();

    private StubBuilder(final Type type) {
        this.definition = new StubDefinition<T>(type);
    }

    private StubBuilder(final Class<T> type) {
        this.definition = new StubDefinition<T>(type);
    }

    /**
     * Configure the builder to populate any properties of the given type with a
     * value created by the supplied value factory. For example
     * </p>
     *
     * <pre>
     * Person aPerson = StubBuilder.aRandomInstanceOf(Person.class)
     *                        .with(Date.class, ValueFactories.oneOf(APR(5,1975), APR(5,1985)))
     *                        .build()
     * </pre>
     *
     * @param type the type of property to use the factory for
     * @param factory the factory to use to create the value
     */
    public <V> StubBuilder<T> with(final Class<V> type, final ValueFactory<V> factory) {
        this.definition.addOverride(type, factory);
        return this;
    }

    /**
     * Configure the builder to populate any properties of the given type with a
     * value created by the supplied value factory. For example
     * </p>
     *
     * <pre>
     * Person aPerson = StubBuilder.aRandomInstanceOf(Person.class)
     *                        .factory(Date.class, ValueFactories.oneOf(APR(5,1975), APR(5,1985)))
     *                        .build()
     * </pre>
     *
     * @param type the type of property to use the factory for
     * @param factory the factory to use to create the value
     */
    public <X> StubBuilder<T> factory(final Class<X> type, final ValueFactory<X> factory) {
        this.definition.addOverride(type, factory);
        return this;
    }

    /**
     * Configure the builder to set the size of a collections. For example
     * </p>
     *
     * <pre>
     * Person aPerson = StubBuilder.aRandomInstanceOf(Person.class)
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
     * Person aPerson = StubBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeRangeOf(2,10)
     *                        .build()
     * </pre>
     *
     * @param min the minimum size to create the collections
     * @param max the maximum size to create the collections
     */
    public StubBuilder<T> collectionSizeRangeOf(final int min, final int max) {
        this.definition.setCollectionSizeRange(min, max);
        return this;
    }

    /**
     * Configure the builder to use a particular subtype when instantiating a
     * super type. For example
     * </p>
     *
     * <pre>
     * ShapeSorter aSorter = StubBuilder.aRandomInstanceOf(ShapeSorter.class)
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
     * ShapeSorter aSorter = StubBuilder.aRandomInstanceOf(ShapeSorter.class)
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
     * Person aPerson = StubBuilder.aRandomInstanceOf(Person.class).build()
     * </pre>
     */
    public T build() {
        return this.factory.createStub(this.definition);
    }

    private <X> List<ValueFactory<X>> createInstanceOfFactoriesForTypes(final Class<? extends X>... subtypes) {
        List<ValueFactory<X>> factories = new ArrayList<ValueFactory<X>>();
        for (Class<? extends X> subtype : subtypes) {
            factories.add((ValueFactory<X>) aNewInstanceOf(subtype));
        }
        return factories;
    }

    private static boolean isGenericType(final Class<?> type) {
        return type.getTypeParameters() != null && type.getTypeParameters().length > 0;
    }

}
