package org.exparity.stub.testutils.type;

import java.math.BigDecimal;

public class Engine {

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