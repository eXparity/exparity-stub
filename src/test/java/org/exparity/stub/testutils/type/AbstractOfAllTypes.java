package org.exparity.stub.testutils.type;

import java.math.BigDecimal;
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

public abstract class AbstractOfAllTypes {

    public static enum EnumValues {
        VALUE_1, VALUE_2
    };

    public abstract AbstractOfAllTypes.EnumValues getEnumValue();

    public abstract boolean isBooleanValue();

    public abstract Boolean getBooleanObjectValue();

    public abstract String getStringValue();

    public abstract Integer getIntegerObjectValue();

    public abstract int getIntegerValue();

    public abstract Long getLongObjectValue();

    public abstract long getLongValue();

    public abstract Short getShortObjectValue();

    public abstract short getShortValue();

    public abstract Double getDoubleObjectValue();

    public abstract double getDoubleValue();

    public abstract Float getFloatObjectValue();

    public abstract float getFloatValue();

    public abstract Character getCharObjectValue();

    public abstract char getCharValue();

    public abstract Byte getByteObjectValue();

    public abstract byte getByteValue();

    public abstract Date getDateValue();

    public abstract LocalDate getLocalDateValue();

    public abstract LocalTime getLocalTimeValue();

    public abstract LocalDateTime getLocalDateTimeValue();

    public abstract ZonedDateTime getZonedlDateTimeValue();

    public abstract Instant getInstantValue();

    public abstract BigDecimal getBigDecimalValue();

    public abstract int[] getArray();

    public abstract Collection<String> getCollection();

    public abstract List<String> getList();

    public abstract Set<String> getSet();

    public abstract Map<Long, String> getMap();

    public abstract Map<String, String[]> getArrayMap();

    public abstract String[] getStringArray();
}
