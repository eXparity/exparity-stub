package org.exparity.stub.testutils.type;

import java.util.List;

/**
 * Test model simulating an interface for a service
 */
public interface Service {

    public List<Car> getCarByMake(String make);

    void addCar(Car car);

    Car createCar();

    ConstructorOnlyNested createConstructorOnlyNested();
}
