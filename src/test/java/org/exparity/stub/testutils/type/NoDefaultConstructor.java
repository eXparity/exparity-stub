package org.exparity.stub.testutils.type;

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