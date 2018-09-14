package org.exparity.stub.testutils.type;

public class ConstructorOnlyEnumTypes {

    public static enum EnumValues {
        VALUE_1, VALUE_2
    };

    private final ConstructorOnlyEnumTypes.EnumValues enumValue;

    public ConstructorOnlyEnumTypes(final EnumValues enumValue) {
        this.enumValue = enumValue;
    }

    public ConstructorOnlyEnumTypes.EnumValues getEnumValue() {
        return enumValue;
    }

}