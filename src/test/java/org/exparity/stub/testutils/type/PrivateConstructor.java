package org.exparity.stub.testutils.type;

public class PrivateConstructor {

    private String value;

    private PrivateConstructor(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}