package org.exparity.stub.testutils.type;

public class Employee extends Person {

    private Person manager;

    public Person getManager() {
        return this.manager;
    }

    public void setManager(final Person manager) {
        this.manager = manager;
    }

}