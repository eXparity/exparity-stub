package org.exparity.stub.testutils.type;

import java.util.ArrayList;
import java.util.List;

public class Wheel {

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