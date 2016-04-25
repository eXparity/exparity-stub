package org.exparity.stub.testutils.type;

import java.util.List;

public class Manager extends Person {

    private List<Person> employees;

    public List<Person> getEmployees() {
        return this.employees;
    }

    public void setEmployees(final List<Person> employees) {
        this.employees = employees;
    }

}