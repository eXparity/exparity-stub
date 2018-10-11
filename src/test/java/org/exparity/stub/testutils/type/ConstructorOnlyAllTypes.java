package org.exparity.stub.testutils.type;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConstructorOnlyAllTypes {

    public static enum EnumValues {
        VALUE_1, VALUE_2
    };

    private final ConstructorOnlyAllTypes.EnumValues enumValue;
    private final String stringValue;
    private final Integer integerObjectValue;
    private final int integerValue;
    private final Long longObjectValue;
    private final long longValue;
    private final Short shortObjectValue;
    private final short shortValue;
    private final Double doubleObjectValue;
    private final double doubleValue;
    private final Float floatObjectValue;
    private final float floatValue;
    private final Character charObjectValue;
    private final char charValue;
    private final Byte byteObjectValue;
    private final byte byteValue;
    private final Date dateValue;
    private final LocalDate localDateValue;
    private final LocalTime localTimeValue;
    private final LocalDateTime localDateTimeValue;
    private final ZonedDateTime zonedlDateTimeValue;
    private final Instant instantValue;
    private final Duration durationValue;
    private final BigDecimal bigDecimalValue;
    private final int[] array;
    private final Collection<String> collection;
    private final List<String> list;
    private final Set<String> set;
    private final Map<Long, String> map;
    private final boolean booleanValue;
    private final Boolean booleanObjectValue;

    public ConstructorOnlyAllTypes(final EnumValues enumValue,
            final String stringValue,
            final Integer integerObjectValue,
            final int integerValue,
            final Long longObjectValue,
            final long longValue,
            final Short shortObjectValue,
            final short shortValue,
            final Double doubleObjectValue,
            final double doubleValue,
            final Float floatObjectValue,
            final float floatValue,
            final Character charObjectValue,
            final char charValue,
            final Byte byteObjectValue,
            final byte byteValue,
            final Date dateValue,
            final LocalDate localDateValue,
            final LocalTime localTimeValue,
            final LocalDateTime localDateTimeValue,
            final ZonedDateTime zonedlDateTimeValue,
            final Instant instantValue,
            final BigDecimal bigDecimalValue,
            final int[] array,
            final Collection<String> collection,
            final List<String> list,
            final Set<String> set,
            final Map<Long, String> map,
            final boolean booleanValue,
            final Boolean booleanObjectValue,
            final Duration durationValue) {
        this.enumValue = enumValue;
        this.stringValue = stringValue;
        this.integerObjectValue = integerObjectValue;
        this.integerValue = integerValue;
        this.longObjectValue = longObjectValue;
        this.longValue = longValue;
        this.shortObjectValue = shortObjectValue;
        this.shortValue = shortValue;
        this.doubleObjectValue = doubleObjectValue;
        this.doubleValue = doubleValue;
        this.floatObjectValue = floatObjectValue;
        this.floatValue = floatValue;
        this.charObjectValue = charObjectValue;
        this.charValue = charValue;
        this.byteObjectValue = byteObjectValue;
        this.byteValue = byteValue;
        this.dateValue = dateValue;
        this.localDateValue = localDateValue;
        this.localTimeValue = localTimeValue;
        this.localDateTimeValue = localDateTimeValue;
        this.zonedlDateTimeValue = zonedlDateTimeValue;
        this.instantValue = instantValue;
        this.bigDecimalValue = bigDecimalValue;
        this.array = array;
        this.collection = collection;
        this.list = list;
        this.set = set;
        this.map = map;
        this.booleanValue = booleanValue;
        this.booleanObjectValue = booleanObjectValue;
        this.durationValue = durationValue;
    }

    public Duration getDurationValue() {
        return durationValue;
    }

    public ConstructorOnlyAllTypes.EnumValues getEnumValue() {
        return enumValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Integer getIntegerObjectValue() {
        return integerObjectValue;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public Long getLongObjectValue() {
        return longObjectValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public Short getShortObjectValue() {
        return shortObjectValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public Double getDoubleObjectValue() {
        return doubleObjectValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public Float getFloatObjectValue() {
        return floatObjectValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public Character getCharObjectValue() {
        return charObjectValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public Byte getByteObjectValue() {
        return byteObjectValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public LocalDate getLocalDateValue() {
        return localDateValue;
    }

    public LocalTime getLocalTimeValue() {
        return localTimeValue;
    }

    public LocalDateTime getLocalDateTimeValue() {
        return localDateTimeValue;
    }

    public ZonedDateTime getZonedlDateTimeValue() {
        return zonedlDateTimeValue;
    }

    public Instant getInstantValue() {
        return instantValue;
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }

    public int[] getArray() {
        return array;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public List<String> getList() {
        return list;
    }

    public Set<String> getSet() {
        return set;
    }

    public Map<Long, String> getMap() {
        return map;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public Boolean getBooleanObjectValue() {
        return booleanObjectValue;
    }

}