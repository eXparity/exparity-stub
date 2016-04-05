package org.exparity.stub.testutils;

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