package org.exparity.stub.testutils.type;

import java.util.List;

public class Car {

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