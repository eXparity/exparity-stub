package org.exparity.stub.testutils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stewart.Bissett
 */
public class BeanBuilderTestTypes {

    public static class Employee extends Person {

        private Person manager;

        public Person getManager() {
            return this.manager;
        }

        public void setManager(final Person manager) {
            this.manager = manager;
        }

    }

    public static class Manager extends Person {

        private List<Person> employees;

        public List<Person> getEmployees() {
            return this.employees;
        }

        public void setEmployees(final List<Person> employees) {
            this.employees = employees;
        }

    }

    public static class Car {

        private Engine engine;
        private List<Wheel> wheels;

        public Car(final Engine engine, final List<Wheel> wheels) {
            this.engine = engine;
            this.wheels = wheels;
        }

        public Car() {}

        public Engine getEngine() {
            return this.engine;
        }

        public void setEngine(final Engine engine) {
            this.engine = engine;
        }

        public List<Wheel> getWheels() {
            return this.wheels;
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

        public Engine() {}

        public BigDecimal getCapacity() {
            return this.capacity;
        }

        public void setCapacity(final BigDecimal capacity) {
            this.capacity = capacity;
        }
    }

    public static class Wheel {

        private Integer diameter;
        private List<Nut> nuts = new ArrayList<Nut>();

        public Wheel(final Integer diameter) {
            this.diameter = diameter;
        }

        public Wheel() {}

        public Integer getDiameter() {
            return this.diameter;
        }

        public void setDiameter(final Integer diameter) {
            this.diameter = diameter;
        }

        public List<Nut> getNuts() {
            return this.nuts;
        }

        public void setNuts(final List<Nut> nuts) {
            this.nuts = nuts;
        }
    }

    public static class Nut {

        private boolean locking;

        public Nut(final boolean locking) {
            this.locking = locking;
        }

        public Nut() {}

        public void setLocking(final boolean locking) {
            this.locking = locking;
        }

        public boolean isLocking() {
            return this.locking;
        }
    }

    public static class Person {

        private List<Person> siblings;
        private String firstname, surname;

        public Person(final String firstname, final String surname) {
            this.firstname = firstname;
            this.surname = surname;
        }

        public Person() {}

        public String getFirstname() {
            return this.firstname;
        }

        public void setFirstname(final String firstname) {
            this.firstname = firstname;
        }

        public String getSurname() {
            return this.surname;
        }

        public void setSurname(final String surname) {
            this.surname = surname;
        }

        public List<Person> getSiblings() {
            return this.siblings;
        }

        public void setSiblings(final List<Person> siblings) {
            this.siblings = siblings;
        }
    }

    public static enum FuelType {
        DIESEL, PETROL, LPG
    }

    public static enum EmptyEnum {};

    public static class ShapeSorter {
        private Shape shape;

        public Shape getShape() {
            return this.shape;
        }

        public void setShape(final Shape shape) {
            this.shape = shape;
        }
    }

    public static interface Shape {
        public Integer numberOfSides();
    }

    public static class Circle implements Shape {
        @Override
        public Integer numberOfSides() {
            return 0;
        }
    }

    public static class Square implements Shape {
        @Override
        public Integer numberOfSides() {
            return 4;
        }
    }

    public static class Triangle implements Shape {
        @Override
        public Integer numberOfSides() {
            return 3;
        }
    }
}
