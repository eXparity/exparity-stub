package org.exparity.stub.testutils.type;

public class Immutable {

    private final String value;

    public Immutable(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
