Exparity Stub  [![Build Status](https://travis-ci.org/eXparity/exparity-stub.svg?branch=master)](https://travis-ci.org/eXparity/exparity-stub) [![Coverage Status](https://coveralls.io/repos/eXparity/exparity-stub/badge.png?branch=master)](https://coveralls.io/r/eXparity/exparity-stub?branch=master)
=============
A Java library to support creation of test stubs. The library can create complete object graphs populated completely randomly, or can be populated with a mix of random and configured values.

Licensed under [BSD License][].

Downloads
---------
You can obtain exparity-stub jar from [maven central][]. To include your project in:

A maven project

    <dependency>
        <groupId>org.exparity</groupId>
        <artifactId>exparity-stub</artifactId>
        <version>1.1.0</version>
    </dependency>

A project which uses ivy for dependency management

    <dependency org="org.exparity" name="exparity-stub" rev="1.1.0"/>
            
Usage
-------------

Stub objects can be created using the static methods exposed on either the RandomBuilder class.

The RandomBuilder can be used to create random values for primitives, for arrays, and for complete object graphs where the classes follow the Java Beans naming standard for getters and settter. For example

	String randomName = RandomBuilder.aRandomString();
	Gender raGender = RandomBuilder.aRandomEnum(Gender.class);
	Person aPerson = RandomBuilder.aRandomInstanceOf(Person.class);
	Person []  aCrowd = RandomBuilder.aRandomArrayOf(Person.class);
	List<Person>  aCrowd = RandomBuilder.aRandomListOf(Person.class);

The RandomBuilder can be configured to restrict how certain properties, types, paths, subtypes, and collections are built. For example.

	Person aPerson = RandomBuilder.aRandomInstanceOf(Person.class,
	RandomBuilder.path("person.siblings.firstName","Bob"));
	Person aPerson = RandomBuilder.aRandomInstanceOf(Person.class, RandomBuilder.property("firstName","Bob"));
	Person aPersonWithBrothersAndSisters = RandomBuilder.aRandomInstanceOf(Person.class,
	RandomBuilder.collectionSizeForPath("person.sibling",2,5));
	  
or after static importing

	Person aPerson = aRandomInstanceOf(Person.class, path("person.siblings.firstName","Bob"));
	Person aPerson = aRandomInstanceOf(Person.class, property("firstName","Bob"));
	Person aPersonWithBrothersAndSisters = aRandomInstanceOf(Person.class, collectionSizeForPath("person.sibling",2,5));

Multiple restrictions can be applied at the same time. For example

	Person aPerson = aRandomInstanceOf(Person.class, collectionSizeForPath("person.sibling",2), path("person.siblings.firstName","Bob"));

The RandomBuilder class includes includes factory methods for:

* __aRandomArrayOf__ - Create a random array of an class which implements the Java Beans getters and setters
* __aRandomArrayOfEnum__ - Create a random array of an Enum
* __aRandomBoolean__ - Create a random Boolean
* __aRandomByte__ - Create a random Byte
* __aRandomByteArray__ - Create a random byte[]
* __aRandomChar__ - Create a random Character
* __aRandomCollectionOf__ - Create a random collection of a class which implements the Java Beans getters and setters
* __aRandomDate__ - Create a random Boolean
* __aRandomEnum__ - Create a random instance of an Enum
* __aRandomDecimal__ - Create a random Boolean
* __aRandomDouble__ - Create a random Double
* __aRandomFloat__ - Create a random Float
* __aRandomInteger__ - Create a random Integer
* __aRandomInstanceOf__ - Create a random instance of class which implements the Java Beans getters and setters
* __aRandomListOf__ - Create a random list of a class which implements the Java Beans getters and setters
* __aRandomLong__ - Create a random Long
* __aRandomShort__ - Create a random Short
* __aRandomString__ - Create a random String

It also includes factory methods for the restrictions to be applied when building an instance of an object:

* __path__ - Restrict the value of a path
* __property__ - Restrict the value of a property
* __excludePath__ - Exclude a path from being populated randomly
* __excludeProperty__ - Exclude a property from being populated randomly
* __subtype__ - Define which subtype to use when instantiating a subtype
* __collectionSize__ - Define the size to use for collections
* __collectionSizeForPath__ - Define the size to use for the collection for a path
* __collectionSizeForProperty__ - Define the size to use for the collection for a property

The Javadocs include examples on all methods so you can look there for examples for specific methods

Source
------
The source is structured along the lines of the maven standard folder structure for a jar project.

  * Core classes [src/main/java]
  * Unit tests [src/test/java]

The source includes a pom.xml for building with Maven 

Acknowledgements
----------------
Developers:
  * Stewart Bissett

[BSD License]: http://opensource.org/licenses/BSD-3-Clause
[Maven central]: http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22hamcrest-date%22
