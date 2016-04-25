package org.exparity.stub.testutils.type;

public class Nut {

    private boolean locking;

    public Nut(final boolean locking) {
        this.locking = locking;
    }

    public Nut() {}

    public void setLocking(final boolean locking) {
        this.locking = locking;
    }

    public boolean isLocking() {
        return this.locking;
    }
}