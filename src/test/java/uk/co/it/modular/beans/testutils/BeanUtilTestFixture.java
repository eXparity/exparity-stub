/*
 * Copyright (c) Modular IT Limited.
 */

package uk.co.it.modular.beans.testutils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stewart.Bissett
 */
public class BeanUtilTestFixture {

	public static class Employee extends Person {

		private Person manager;

		public Person getManager() {
			return manager;
		}

		public void setManager(final Person manager) {
			this.manager = manager;
		}

	}

	public static class Manager extends Person {

		private List<Person> employees;

		public List<Person> getEmployees() {
			return employees;
		}

		public void setEmployees(final List<Person> employees) {
			this.employees = employees;
		}

	}

	public static class Thrower {

		int property = 0;

		public void setProperty(final int property) {
			throw new RuntimeException();
		}

		public int getProperty() {
			throw new RuntimeException();
		}
	}

	public static class AllTypes {

		public static enum EnumValues {
			VALUE_1, VALUE_2
		};

		private EnumValues enumValue;
		private String stringValue;
		private Integer integerObjectValue;
		private int integerValue;
		private Long longObjectValue;
		private long longValue;
		private Short shortObjectValue;
		private short shortValue;
		private Double doubleObjectValue;
		private double doubleValue;
		private Float floatObjectValue;
		private float floatValue;
		private Character charObjectValue;
		private char charValue;
		private Byte byteObjectValue;
		private byte byteValue;
		private Date dateValue;
		private BigDecimal bigDecimalValue;
		private int[] array;
		private Collection<String> collection;
		private List<String> list;
		private Set<String> set;
		private Map<Long, String> map;

		public EnumValues getEnumValue() {
			return enumValue;
		}

		public void setEnumValue(final EnumValues enumValue) {
			this.enumValue = enumValue;
		}

		public Collection<String> getCollection() {
			return collection;
		}

		public void setCollection(final Collection<String> collection) {
			this.collection = collection;
		}

		public List<String> getList() {
			return list;
		}

		public void setList(final List<String> list) {
			this.list = list;
		}

		public Set<String> getSet() {
			return set;
		}

		public void setSet(final Set<String> set) {
			this.set = set;
		}

		public Map<Long, String> getMap() {
			return map;
		}

		public void setMap(final Map<Long, String> map) {
			this.map = map;
		}

		public int[] getArray() {
			return array;
		}

		public void setArray(final int[] array) {
			this.array = array;
		}

		public boolean isBooleanValue() {
			return booleanValue;
		}

		public void setBooleanValue(final boolean booleanValue) {
			this.booleanValue = booleanValue;
		}

		public Boolean getBooleanObjectValue() {
			return booleanObjectValue;
		}

		public void setBooleanObjectValue(final Boolean booleanObjectValue) {
			this.booleanObjectValue = booleanObjectValue;
		}

		private boolean booleanValue;
		private Boolean booleanObjectValue;

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(final String stringValue) {
			this.stringValue = stringValue;
		}

		public Integer getIntegerObjectValue() {
			return integerObjectValue;
		}

		public void setIntegerObjectValue(final Integer integerObjectValue) {
			this.integerObjectValue = integerObjectValue;
		}

		public int getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(final int integerValue) {
			this.integerValue = integerValue;
		}

		public Long getLongObjectValue() {
			return longObjectValue;
		}

		public void setLongObjectValue(final Long longObjectValue) {
			this.longObjectValue = longObjectValue;
		}

		public long getLongValue() {
			return longValue;
		}

		public void setLongValue(final long longValue) {
			this.longValue = longValue;
		}

		public Short getShortObjectValue() {
			return shortObjectValue;
		}

		public void setShortObjectValue(final Short shortObjectValue) {
			this.shortObjectValue = shortObjectValue;
		}

		public short getShortValue() {
			return shortValue;
		}

		public void setShortValue(final short shortValue) {
			this.shortValue = shortValue;
		}

		public Double getDoubleObjectValue() {
			return doubleObjectValue;
		}

		public void setDoubleObjectValue(final Double doubleObjectValue) {
			this.doubleObjectValue = doubleObjectValue;
		}

		public double getDoubleValue() {
			return doubleValue;
		}

		public void setDoubleValue(final double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public Float getFloatObjectValue() {
			return floatObjectValue;
		}

		public void setFloatObjectValue(final Float floatObjectValue) {
			this.floatObjectValue = floatObjectValue;
		}

		public float getFloatValue() {
			return floatValue;
		}

		public void setFloatValue(final float floatValue) {
			this.floatValue = floatValue;
		}

		public Character getCharObjectValue() {
			return charObjectValue;
		}

		public void setCharObjectValue(final Character charObjectValue) {
			this.charObjectValue = charObjectValue;
		}

		public char getCharValue() {
			return charValue;
		}

		public void setCharValue(final char charValue) {
			this.charValue = charValue;
		}

		public Byte getByteObjectValue() {
			return byteObjectValue;
		}

		public void setByteObjectValue(final Byte byteObjectValue) {
			this.byteObjectValue = byteObjectValue;
		}

		public byte getByteValue() {
			return byteValue;
		}

		public void setByteValue(final byte byteValue) {
			this.byteValue = byteValue;
		}

		public Date getDateValue() {
			return dateValue;
		}

		public void setDateValue(final Date dateValue) {
			this.dateValue = dateValue;
		}

		public BigDecimal getBigDecimalValue() {
			return bigDecimalValue;
		}

		public void setBigDecimalValue(final BigDecimal bigDecimalValue) {
			this.bigDecimalValue = bigDecimalValue;
		}

	}

	public static class Car {

		private Engine engine;
		private List<Wheel> wheels;

		public Car(final Engine engine, final List<Wheel> wheels) {
			this.engine = engine;
			this.wheels = wheels;
		}

		public Car() {
		}

		public Engine getEngine() {
			return engine;
		}

		public void setEngine(final Engine engine) {
			this.engine = engine;
		}

		public List<Wheel> getWheels() {
			return wheels;
		}

		public void setWheels(final List<Wheel> wheels) {
			this.wheels = wheels;
		}
	}

	public static class Engine {

		private BigDecimal capacity;

		public Engine(final BigDecimal capacity) {
			this.capacity = capacity;
		}

		public Engine() {
		}

		public BigDecimal getCapacity() {
			return capacity;
		}

		public void setCapacity(final BigDecimal capacity) {
			this.capacity = capacity;
		}
	}

	public static class Wheel {

		private Integer diameter;

		public Wheel(final Integer diameter) {
			this.diameter = diameter;
		}

		public Wheel() {
		}

		public Integer getDiameter() {
			return diameter;
		}

		public void setDiameter(final Integer diameter) {
			this.diameter = diameter;
		}
	}

	public static class SetterWithNotArgs {

		public void setProperty() {

		}

		public String getProperty() {
			return "";
		}
	}

	public class NoDefaultConstructor {

		private String value;

		public NoDefaultConstructor(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(final String value) {
			this.value = value;
		}

	}

	public static class GetterWithArgs {

		public void setProperty(final String value) {

		}

		public String getProperty(final String value) {
			return value;
		}
	}

	public static class TypeMismatch {

		public void setProperty(final String value) {

		}

		public Boolean getProperty() {
			return true;
		}
	}

	public static class OverloadedSetter {

		public void setProperty(final String propery) {

		}

		public void setProperty(final Boolean propery) {

		}

		public String getProperty() {
			return "";
		}
	}

	public static class NameMismatch {

		public void setPropertyCalledOneThing(final String property) {
		}

		public String getPropertyCalledAnother() {
			return "";
		}
	}

	public static class Person {

		private List<Person> siblings;
		private String firstname, surname;

		public Person(final String firstname, final String surname) {
			this.firstname = firstname;
			this.surname = surname;
		}

		public Person() {
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(final String firstname) {
			this.firstname = firstname;
		}

		public String getSurname() {
			return surname;
		}

		public void setSurname(final String surname) {
			this.surname = surname;
		}

		public List<Person> getSiblings() {
			return siblings;
		}

		public void setSiblings(final List<Person> siblings) {
			this.siblings = siblings;
		}
	}
}
