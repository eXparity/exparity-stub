package org.exparity.stub.testutils.type;

public class ConstructorOnlyPrimitiveTypes {

    private final int integerValue;
    private final long longValue;
    private final short shortValue;
    private final double doubleValue;
    private final float floatValue;
    private final char charValue;
    private final byte byteValue;
    private final int[] array;
    private final boolean booleanValue;

    public ConstructorOnlyPrimitiveTypes(final int integerValue,
            final long longValue,
            final short shortValue,
            final double doubleValue,
            final float floatValue,
            final char charValue,
            final byte byteValue,
            final int[] array,
            final boolean booleanValue) {
        this.integerValue = integerValue;
        this.longValue = longValue;
        this.shortValue = shortValue;
        this.doubleValue = doubleValue;
        this.floatValue = floatValue;
        this.charValue = charValue;
        this.byteValue = byteValue;
        this.array = array;
        this.booleanValue = booleanValue;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public int[] getArray() {
        return array;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

}