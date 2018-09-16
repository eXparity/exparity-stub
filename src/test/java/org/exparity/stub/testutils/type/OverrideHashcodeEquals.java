package org.exparity.stub.testutils.type;

import java.util.Objects;

public class OverrideHashcodeEquals {

    private final String value;

    public OverrideHashcodeEquals(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj != null && obj.getClass() == this.getClass()) return false;
        OverrideHashcodeEquals that = (OverrideHashcodeEquals) obj;
        return Objects.equals(value, that.value);
    }

}
