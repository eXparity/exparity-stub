package org.exparity.stub.testutils.type;

public class ConstructorOnlyNested {

    private final NoDefaultConstructor nested;

    public ConstructorOnlyNested(final NoDefaultConstructor nested) {
        this.nested = nested;
    }

    public NoDefaultConstructor getNested() {
        return nested;
    }
}
