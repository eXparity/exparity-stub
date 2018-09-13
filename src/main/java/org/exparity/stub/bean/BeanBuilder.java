package org.exparity.stub.bean;

import static java.lang.System.identityHashCode;
import static org.apache.commons.lang.StringUtils.lowerCase;
import static org.exparity.beans.Type.type;
import static org.exparity.stub.core.ValueFactories.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.exparity.beans.Type;
import org.exparity.beans.core.BeanNamingStrategy;
import org.exparity.beans.core.BeanPropertyPath;
import org.exparity.beans.core.TypeProperty;
import org.exparity.beans.core.naming.ForceRootNameNamingStrategy;
import org.exparity.beans.core.naming.LowerCaseNamingStrategy;
import org.exparity.stub.core.ValueFactory;
import org.exparity.stub.random.RandomBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder object for instantiating and populating objects which follow the Java beans standards conventions for
 * getter/setters
 *
 * @author Stewart Bissett
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeanBuilder<T> {

    private static final Logger LOG = LoggerFactory.getLogger(BeanBuilder.class);

    /**
     * Return an instance of a {@link BeanBuilder} for the given type which can then be populated with values either
     * manually or automatically. For example:
     *
     * <pre>
     *
     * Person aPerson = BeanBuilder.anInstanceOf(Person.class)
     * 		.path("person.firstName", "Bob")
     * 		.build()
     * </pre>
     *
     * @param type the type to return the {@link BeanBuilder} for
     */
    public static <T> BeanBuilder<T> anInstanceOf(final Class<T> type) {
        return new BeanBuilder<>(type, BeanBuilderType.NULL, new LowerCaseNamingStrategy());
    }

    /**
     * Return an instance of a {@link BeanBuilder} for the given type which can then be populated with values either
     * manually or automatically. For example:
     *
     * <pre>
     * Person aPerson = BeanBuilder.anInstanceOf(Person.class, "instance")
     *                        .path("instance.firstName", "Bob")
     *                        .build()
     * </pre>
     *
     * @param type the type to return the {@link BeanBuilder} for
     * @param rootName the name give to the root entity when referencing paths
     */
    public static <T> BeanBuilder<T> anInstanceOf(final Class<T> type, final String rootName) {
        return new BeanBuilder<>(type,
                BeanBuilderType.NULL,
                new ForceRootNameNamingStrategy(new LowerCaseNamingStrategy(), rootName));
    }

    /**
     * Return an instance of a {@link BeanBuilder} for the given type which is populated with empty objects but
     * collections, maps, etc which have empty objects. For example:
     *
     * <pre>
     *
     * Person aPerson = BeanBuilder.anEmptyInstanceOf(Person.class).path(&quot;person.firstName&quot;, &quot;Bob&quot;).build();
     * </pre>
     *
     * @param type the type to return the {@link BeanBuilder} for
     */
    public static <T> BeanBuilder<T> anEmptyInstanceOf(final Class<T> type) {
        return new BeanBuilder<>(type, BeanBuilderType.EMPTY, new LowerCaseNamingStrategy());
    }

    /**
     * Return an instance of a {@link BeanBuilder} for the given type which is populated with empty objects but
     * collections, maps, etc which have empty objects. For example:
     *
     * <pre>
     * Person aPerson = BeanBuilder.anEmptyInstanceOf(Person.class, "instance")
     *                        .path("instance.firstName", "Bob")
     *                        .build()
     * </pre>
     *
     * @param type the type to return the {@link BeanBuilder} for
     * @param rootName the name give to the root entity when referencing paths
     */
    public static <T> BeanBuilder<T> anEmptyInstanceOf(final Class<T> type, final String rootName) {
        return new BeanBuilder<>(type,
                BeanBuilderType.EMPTY,
                new ForceRootNameNamingStrategy(new LowerCaseNamingStrategy(), rootName));
    }

    /**
     * Return an instance of a {@link BeanBuilder} for the given type which is populated with random values. For
     * example:
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     * 		.path("person.firstName", "Bob")
     * 		.build()
     * </pre>
     *
     * @param type the type to return the {@link BeanBuilder} for
     */
    public static <T> BeanBuilder<T> aRandomInstanceOf(final Class<T> type) {
        return new BeanBuilder<>(type, BeanBuilderType.RANDOM, new LowerCaseNamingStrategy());
    }

    /**
     * Return an instance of a {@link BeanBuilder} for the given type which is populated with random values. For
     * example:
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class,"instance")
     *                        .path("instance.firstName", "Bob")
     *                        .build()
     * </pre>
     *
     * @param type the type to return the {@link BeanBuilder} for
     */
    public static <T> BeanBuilder<T> aRandomInstanceOf(final Class<T> type, final String rootName) {
        return new BeanBuilder<>(type,
                BeanBuilderType.RANDOM,
                new ForceRootNameNamingStrategy(new LowerCaseNamingStrategy(), rootName));
    }

    private final Set<String> excludedProperties = new HashSet<>();
    private final Set<String> excludedPaths = new HashSet<>();
    private final Map<String, ValueFactory> paths = new HashMap<>();
    private final Map<String, ValueFactory> properties = new HashMap<>();
    private final Map<Class<?>, ValueFactory> types = new HashMap<>();
    private final Class<T> type;
    private final BeanBuilderType builderType;
    private final BeanNamingStrategy naming;;
    private CollectionSize defaultCollectionSize = new CollectionSize(1, 5);
    private final Map<String, CollectionSize> collectionSizeForPaths = new HashMap<>();
    private final Map<String, CollectionSize> collectionSizeForProperties = new HashMap<>();

    private BeanBuilder(final Class<T> type, final BeanBuilderType builderType, final BeanNamingStrategy naming) {
        this.type = type;
        this.builderType = builderType;
        this.naming = naming;
    }

    /**
     * Configure the builder to populate the given property or path with the supplied value. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .with("firstName", "Bob")
     *                        .build()
     * </pre>
     *
     * @param propertyOrPathName the property or path name to set the value on
     * @param value the value to assign the property or path
     */
    public BeanBuilder<T> with(final String propertyOrPathName, final Object value) {
        return with(propertyOrPathName, theValue(value));
    }

    /**
     * Configure the builder to populate any properties of the given type with a value created by the supplied value
     * factory. For example
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
    public <V> BeanBuilder<T> with(final Class<V> type, final ValueFactory<V> factory) {
        this.types.put(type, factory);
        return this;
    }

    /**
     * Configure the builder to populate the given property or path with a value created by the supplied value factory.
     * For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .with("firstName", ValueFactories.oneOf("Bob", "Alice"))
     *                        .build()
     * </pre>
     *
     * @param propertyOrPathName the property or path name to set the value on
     * @param factory the factory to use to create the value
     */
    public BeanBuilder<T> with(final String propertyOrPathName, final ValueFactory<?> factory) {
        path(propertyOrPathName, factory);
        property(propertyOrPathName, factory);
        return this;
    }

    /**
     * Configure the builder to populate the given property with the supplied value. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .property("firstName", "Bob")
     *                        .build()
     * </pre>
     *
     * @param propertyName the property to set the value on
     * @param value the value to assign the property
     */
    public BeanBuilder<T> property(final String propertyName, final Object value) {
        return property(propertyName, theValue(value));
    }

    /**
     * Configure the builder to populate the given property with a value created by the supplied value factory. For
     * example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .property("firstName", ValueFactories.oneOf("Bob", "Alice"))
     *                        .build()
     * </pre>
     *
     * @param propertyName the property to set the value on
     * @param factory the factory to use to create the value
     */
    public BeanBuilder<T> property(final String propertyName, final ValueFactory<?> factory) {
        this.properties.put(lowerCase(propertyName), factory);
        return this;
    }

    /**
     * Configure the builder to populate any properties of the given type with a value created by the supplied value
     * factory. For example
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
    public <X> BeanBuilder<T> factory(final Class<X> type, final ValueFactory<X> factory) {
        this.types.put(type, factory);
        return this;
    }

    /**
     * Configure the builder to exclude the given property from being populated. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .excludeProperty("firstName")
     *                        .build()
     * </pre>
     *
     * @param propertyName the property to exclude
     */
    public BeanBuilder<T> excludeProperty(final String propertyName) {
        this.excludedProperties.add(lowerCase(propertyName));
        return this;
    }

    /**
     * Configure the builder to populate the given path with the supplied value. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .path("person.firstName", "Bob")
     *                        .build()
     * </pre>
     *
     * @param path the path to set the value on
     * @param value the value to assign the path
     */
    public BeanBuilder<T> path(final String path, final Object value) {
        return path(path, theValue(value));
    }

    /**
     * Configure the builder to populate the given path with a value created by the supplied value factory. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .path("person.firstName", ValueFactories.oneOf("Bob", "Alice"))
     *                        .build()
     * </pre>
     *
     * @param path the path to set the value on
     * @param factory the factory to use to create the value
     */
    public BeanBuilder<T> path(final String path, final ValueFactory<?> factory) {
        this.paths.put(lowerCase(path), factory);
        return this;
    }

    /**
     * Configure the builder to exclude the given path from being populated. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .excludeProperty("person.firstName")
     *                        .build()
     * </pre>
     *
     * @param path the path to exclude
     */
    public BeanBuilder<T> excludePath(final String path) {
        this.excludedPaths.add(lowerCase(path));
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
    public BeanBuilder<T> collectionSizeOf(final int size) {
        return collectionSizeRangeOf(size, size);
    }

    /**
     * Configure the builder to set the size of a collections to within a given range. For example
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
    public BeanBuilder<T> collectionSizeRangeOf(final int min, final int max) {
        this.defaultCollectionSize = new CollectionSize(min, max);
        return this;
    }

    /**
     * Configure the builder to set the size of a collection at a path to within a given range. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeRangeForPropertyOf("sublings", 1,3)
     *                        .build()
     * </pre>
     *
     * @param property the name of the property to limit the collection size of
     * @param min the minimum size to create the collections
     * @param max the maximum size to create the collections
     */
    public BeanBuilder<T> collectionSizeRangeForPropertyOf(final String property, final int min, final int max) {
        this.collectionSizeForProperties.put(property, new CollectionSize(min, max));
        return this;
    }

    /**
     * Configure the builder to set the size of a collection at a path. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeForPropertyOf("sublings", 3)
     *                        .build()
     * </pre>
     *
     * @param property the name of the property to limit the collection size of
     * @param size the size to create the collection
     */
    public BeanBuilder<T> collectionSizeForPropertyOf(final String property, final int size) {
        return collectionSizeRangeForPropertyOf(property, size, size);
    }

    /**
     * Configure the builder to set the size of a collection for a path to within a given range. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeForPathOf("person.sublings", 1, 3)
     *                        .build()
     * </pre>
     *
     * @param path the path to limit the collection size of
     * @param size the size to create the collection
     */
    public BeanBuilder<T> collectionSizeForPathOf(final String path, final int size) {
        return collectionSizeRangeForPathOf(path, size, size);
    }

    /**
     * Configure the builder to set the size of a collection at a path to within a given range. For example
     * </p>
     *
     * <pre>
     * Person aPerson = BeanBuilder.aRandomInstanceOf(Person.class)
     *                        .collectionSizeRangeForPathOf("person.siblings", 1,3)
     *                        .build()
     * </pre>
     *
     * @param path the name of the path to limit the collection size of
     * @param min the minimum size to create the collection
     * @param max the maximum size to create the collection
     */
    public BeanBuilder<T> collectionSizeRangeForPathOf(final String path, final int min, final int max) {
        this.collectionSizeForPaths.put(path, new CollectionSize(min, max));
        return this;
    }

    /**
     * Configure the builder to use a particular subtype when instantiating a super type. For example
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
    public <X> BeanBuilder<T> subtype(final Class<X> supertype, final Class<? extends X> subtype) {
        return with(supertype, oneOf(createInstanceOfFactoriesForTypes(subtype)));
    }

    /**
     * Configure the builder to use any of of a particular subtype when instantiating a super type. For example
     * </p>
     *
     * <pre>
     * ShapeSorter aSorter = BeanBuilder.aRandomInstanceOf(ShapeSorter.class)
     *                        .subtype(Shape.class, Square.class, Circle.class, Triangle.class)
     *                        .build()
     * </pre>
     *
     * @param supertype the type of the super type
     * @param subtypes the subtypes to pick from when instantiating the super type
     */
    public <X> BeanBuilder<T> subtype(final Class<X> supertype, final Class<? extends X>... subtypes) {
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
        return populate(createNewInstance(),
                new BeanPropertyPath(this.naming.describeRoot(this.type)),
                new Stack(type(this.type)));
    }

    private <I> I populate(final I instance, final BeanPropertyPath path, final Stack stack) {
        if (instance != null) {
            for (TypeProperty property : type(instance, this.naming).propertyList()) {
                populateProperty(instance, property, path.append(property.getName()), stack);
            }
            return instance;
        } else {
            return instance;
        }
    }

    private void populateProperty(final Object instance,
            final TypeProperty property,
            final BeanPropertyPath path,
            final Stack stack) {

        if (isExcludedPath(path) || isExcludedProperty(property)) {
            LOG.trace("Ignore [{}]. Explicity excluded", path);
            return;
        }

        ValueFactory factory = factoryForPath(property, path);
        if (factory != null) {
            assignValue(instance, property, path, createValue(factory, this.type), stack);
            return;
        }

        if (isPropertySet(instance, property) || isChildOfAssignedPath(path) || isOverflowing(property, path, stack)) {
            return;
        }

        if (property.isArray()) {
            property.setValue(instance, createArray(property.getType().getComponentType(), path, stack));
        } else {
            java.lang.reflect.Type[] params = property.getMutator().getGenericParameterTypes();
            java.lang.reflect.Type firstParam = params[0];
            Class<?> typeParameter = getActualType(firstParam, 0);
            if (property.isMap()) {
                property.setValue(instance,
                        createMap(typeParameter, property.getTypeParameter(1), collectionSize(path), path, stack));
            } else if (property.isSet()) {
                property.setValue(instance, createSet(typeParameter, collectionSize(path), path, stack));
            } else if (property.isList() || property.isCollection()) {
                property.setValue(instance, createList(typeParameter, collectionSize(path), path, stack));
            } else {
                assignValue(instance, property, path, createValue(property.getType()), stack);
            }
        }
    }

    private Object[] createArguments(final java.lang.reflect.Type[] types) {
        if (types.length == 0) {
            return new Object[0];
        } else {
            Object[] args = new Object[types.length];
            for (int i = 0; i < types.length; ++i) {
                Type type = Type.type(getActualType(types[i], 0));
                if (type.is(Map.class)) {
                    args[i] = Collections.emptyMap();
                } else if (type.is(Set.class)) {
                    args[i] = Collections.emptySet();
                } else if (type.is(List.class) || type.is(Collection.class)) {
                    args[i] = Collections.emptyList();
                } else {
                    args[i] = createValue(getActualType(types[i], 0));
                }
            }
            return args;
        }
    }

    private Class<? extends Object> getActualType(final java.lang.reflect.Type type, final int typeOrdinal) {
        if (type instanceof Class) {
            return (Class<? extends Object>) type;
        } else if (type instanceof ParameterizedType) {
            return getActualType(((ParameterizedType) type).getActualTypeArguments()[typeOrdinal], 0);
        } else {
            throw new IllegalArgumentException("Unknown type subclass '" + type.getClass());
        }
    }

    private boolean isPropertySet(final Object instance, final TypeProperty property) {
        if (property.getValue(instance) == null) {
            return false;
        } else if (property.isCollection()) {
            return !property.getValue(instance, Collection.class).isEmpty();
        } else if (property.isMap()) {
            return !property.getValue(instance, Map.class).isEmpty();
        } else {
            return true;
        }
    }

    private boolean isOverflowing(final TypeProperty property, final BeanPropertyPath path, final Stack stack) {
        if (stack.contains(property.getType())) {
            LOG.trace("Ignore {}. Avoids stack overflow caused by type {}", path, property.getTypeSimpleName());
            return true;
        }
        for (Class<?> genericType : property.getTypeParameters()) {
            if (stack.contains(genericType)) {
                LOG.trace("Ignore {}. Avoids stack overflow caused by type {}", path, genericType.getSimpleName());
                return true;
            }
        }

        return false;
    }

    private ValueFactory factoryForPath(final TypeProperty property, final BeanPropertyPath path) {
        return selectNotNull(this.paths.get(path.fullPath()),
                this.paths.get(path.fullPathWithNoIndexes()),
                this.properties.get(property.getName()));
    }

    private boolean isExcludedProperty(final TypeProperty property) {
        return this.excludedProperties.contains(property.getName());
    }

    private boolean isExcludedPath(final BeanPropertyPath path) {
        return this.excludedPaths.contains(path.fullPath()) || this.excludedPaths.contains(path
                .fullPathWithNoIndexes());
    }

    private boolean isChildOfAssignedPath(final BeanPropertyPath path) {
        for (String assignedPath : this.paths.keySet()) {
            if (path.startsWith(assignedPath + ".")) {
                LOG.trace("Ignore {}. Child of assigned path {}", path, assignedPath);
                return true;
            }
        }
        return false;
    }

    private void assignValue(final Object instance,
            final TypeProperty property,
            final BeanPropertyPath path,
            final Object value,
            final Stack stack) {
        if (value != null) {
            LOG.trace("Assign {} value [{}:{}]",
                    new Object[] { path, value.getClass().getSimpleName(), identityHashCode(value) });
            property.setValue(instance, populate(value, path, stack.append(type(value))));
        } else {
            LOG.trace("Assign {} value [null]", path);
        }
    }

    private <E> E createValue(final Class<E> type) {

        for (Entry<Class<?>, ValueFactory> keyedFactory : this.types.entrySet()) {
            if (type.isAssignableFrom(keyedFactory.getKey())) {
                ValueFactory factory = keyedFactory.getValue();
                return (E) createValue(factory, type);
            }
        }

        switch (this.builderType) {
        case RANDOM: {
            ValueFactory factory = RANDOM_FACTORIES.get(type);
            if (factory != null) {
                return (E) createValue(factory, type);
            } else if (type.isEnum()) {
                return createValue(aRandomEnum(type), type);
            } else {
                return createValue(aNewInstanceOf(type), type);
            }
        }
        case EMPTY: {
            ValueFactory factory = EMPTY_FACTORIES.get(type);
            if (factory != null) {
                return (E) createValue(factory, type);
            } else if (type.isEnum()) {
                return null;
            } else {
                return createValue(aNewInstanceOf(type), type);
            }
        }
        default:
            return null;
        }
    }

    private <E> E createValue(final ValueFactory<E> factory, final Class<E> type) {
        E value = factory != null ? factory.createValue() : null;
        LOG.trace("Create Value [{}] for Type [{}]", value, type(type).simpleName());
        return value;
    }

    private <E> Object createArray(final Class<E> type, final BeanPropertyPath path, final Stack stack) {
        switch (this.builderType) {
        case EMPTY:
        case RANDOM:
            Object array = Array.newInstance(type, collectionSize(path));
            for (int i = 0; i < Array.getLength(array); ++i) {
                Array.set(array, i, populate(createValue(type), path.appendIndex(i), stack.append(type)));
            }
            return array;
        default:
            return null;
        }
    }

    private <E> Set<E> createSet(final Class<E> type,
            final int length,
            final BeanPropertyPath path,
            final Stack stack) {
        switch (this.builderType) {
        case EMPTY:
        case RANDOM:
            Set<E> set = new HashSet<>();
            for (int i = 0; i < length; ++i) {
                E value = populate(createValue(type), path.appendIndex(i), stack.append(type));
                if (value != null) {
                    set.add(value);
                }
            }
            return set;
        default:
            return null;
        }
    }

    private <E> List<E> createList(final Class<E> type,
            final int length,
            final BeanPropertyPath path,
            final Stack stack) {
        switch (this.builderType) {
        case EMPTY:
        case RANDOM:
            List<E> list = new ArrayList<>();
            for (int i = 0; i < length; ++i) {
                E value = populate(createValue(type), path.appendIndex(i), stack.append(type));
                if (value != null) {
                    list.add(value);
                }
            }
            return list;
        default:
            return null;
        }
    }

    private <K, V> Map<K, V> createMap(final Class<K> keyType,
            final Class<V> valueType,
            final int length,
            final BeanPropertyPath path,
            final Stack stack) {
        switch (this.builderType) {
        case EMPTY:
        case RANDOM:
            Map<K, V> map = new HashMap<>();
            for (int i = 0; i < length; ++i) {
                K key = populate(createValue(keyType), path.appendIndex(i), stack.append(keyType).append(valueType));
                if (key != null) {
                    map.put(key, createValue(valueType));
                }
            }
            return map;
        default:
            return null;
        }
    }

    private T createNewInstance() {
        try {
            Constructor<T> constructor = findConstructor();
            java.lang.reflect.Type[] params = constructor.getGenericParameterTypes();
            Object[] initargs = createArguments(params);
            return constructor.newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanBuilderException("Failed to instantiate '" + this.type + "'. Error [" + e.getMessage() + "]",
                    e);
        }
    }

    private Constructor<T> findConstructor() {
        List<Constructor<T>> constructors = Arrays.asList((Constructor<T>[]) this.type.getConstructors());
        Collections.sort(constructors, new Comparator<Constructor<T>>() {

            @Override
            public int compare(final Constructor<T> o1, final Constructor<T> o2) {
                return Integer.valueOf(o1.getParameterCount()).compareTo(o2.getParameterCount());
            }
        });
        return constructors.get(0);
    }

    private int collectionSize(final BeanPropertyPath path) {
        return selectNotNull(this.collectionSizeForPaths.get(path.fullPath()),
                this.collectionSizeForPaths.get(path.fullPathWithNoIndexes()),
                this.collectionSizeForProperties.get(propertyName(path)),
                this.defaultCollectionSize).aRandomSize();
    }

    private String propertyName(final BeanPropertyPath path) {
        return StringUtils.substringAfterLast(path.fullPathWithNoIndexes(), ".");
    }

    private <X> List<ValueFactory<X>> createInstanceOfFactoriesForTypes(final Class<? extends X>... subtypes) {
        List<ValueFactory<X>> factories = new ArrayList<>();
        for (Class<? extends X> subtype : subtypes) {
            factories.add((ValueFactory<X>) aNewInstanceOf(subtype));
        }
        return factories;
    }

    private static class Stack {

        private final Type[] stack;

        private Stack(final Type type) {
            this(new Type[] { type });
        }

        private Stack(final Type[] stack) {
            this.stack = stack;
        }

        public boolean contains(final Class<?> type) {
            int hits = 0;
            for (Type entry : this.stack) {
                if (entry.is(type)) {
                    if ((++hits) > 1) {
                        return true;
                    }
                }
            }
            return false;
        }

        public Stack append(final Class<?> value) {
            return append(type(value));
        }

        public Stack append(final Type value) {
            Type[] newStack = Arrays.copyOf(this.stack, this.stack.length + 1);
            newStack[this.stack.length] = value;
            return new Stack(newStack);
        }
    }

    private static class CollectionSize {

        private final int min, max;

        public CollectionSize(final int min, final int max) {
            this.min = min;
            this.max = max;
        }

        public int aRandomSize() {
            if (this.min == this.max) {
                return this.min;
            } else {
                return RandomBuilder.aRandomInteger(this.min, this.max);
            }
        }
    }

    @SuppressWarnings("serial")
    private static final Map<Class<?>, ValueFactory> RANDOM_FACTORIES = new HashMap<Class<?>, ValueFactory>() {

        {
            put(Short.class, aRandomShort());
            put(short.class, aRandomShort());
            put(Integer.class, aRandomInteger());
            put(int.class, aRandomInteger());
            put(Long.class, aRandomLong());
            put(long.class, aRandomLong());
            put(Double.class, aRandomDouble());
            put(double.class, aRandomDouble());
            put(Float.class, aRandomFloat());
            put(float.class, aRandomFloat());
            put(Boolean.class, aRandomBoolean());
            put(boolean.class, aRandomBoolean());
            put(Byte.class, aRandomByte());
            put(byte.class, aRandomByte());
            put(Character.class, aRandomChar());
            put(char.class, aRandomChar());
            put(String.class, aRandomString());
            put(BigDecimal.class, aRandomDecimal());
            put(Date.class, aRandomDate());
            put(LocalDate.class, aRandomLocalDate());
            put(LocalTime.class, aRandomLocalTime());
            put(LocalDateTime.class, aRandomLocalDateTime());
            put(ZonedDateTime.class, aRandomZonedDateTime());
            put(Instant.class, aRandomInstant());
        }
    };

    private static final Map<Class<?>, ValueFactory> EMPTY_FACTORIES = new HashMap<Class<?>, ValueFactory>() {

        private static final long serialVersionUID = 1L;

        {
            put(Short.class, aNullValue());
            put(short.class, theValue((short) 0));
            put(Integer.class, aNullValue());
            put(int.class, theValue(0));
            put(Long.class, aNullValue());
            put(long.class, theValue(0));
            put(Double.class, aNullValue());
            put(double.class, theValue(0.0));
            put(Float.class, aNullValue());
            put(float.class, theValue((float) 0.0));
            put(Boolean.class, aNullValue());
            put(boolean.class, theValue(false));
            put(Byte.class, aNullValue());
            put(byte.class, theValue((byte) 0));
            put(Character.class, aNullValue());
            put(char.class, theValue((char) 0));
            put(String.class, aNullValue());
            put(BigDecimal.class, aNullValue());
            put(Date.class, aNullValue());
            put(LocalDate.class, aNullValue());
            put(LocalTime.class, aNullValue());
            put(LocalDateTime.class, aNullValue());
            put(ZonedDateTime.class, aNullValue());
            put(Instant.class, aNullValue());
        }
    };

    private static enum BeanBuilderType {
        RANDOM, EMPTY, NULL
    }

    private <O> O selectNotNull(final O... options) {
        for (O option : options) {
            if (option != null) {
                return option;
            }
        }
        return null;
    }

}
