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

public interface InterfaceOfAllTypes {

    public static enum EnumValues {
        VALUE_1, VALUE_2
    };

    public InterfaceOfAllTypes.EnumValues getEnumValue();

    public boolean isBooleanValue();

    public Boolean getBooleanObjectValue();

    public String getStringValue();

    public Integer getIntegerObjectValue();

    public int getIntegerValue();

    public Long getLongObjectValue();

    public long getLongValue();

    public Short getShortObjectValue();

    public short getShortValue();

    public Double getDoubleObjectValue();

    public double getDoubleValue();

    public Float getFloatObjectValue();

    public float getFloatValue();

    public Character getCharObjectValue();

    public char getCharValue();

    public Byte getByteObjectValue();

    public byte getByteValue();

    public Date getDateValue();

    public LocalDate getLocalDateValue();

    public LocalTime getLocalTimeValue();

    public LocalDateTime getLocalDateTimeValue();

    public ZonedDateTime getZonedlDateTimeValue();

    public Instant getInstantValue();

    public BigDecimal getBigDecimalValue();

    public int[] getArray();

    public Collection<String> getCollection();

    public List<String> getList();

    public Set<String> getSet();

    public Map<Long, String> getMap();

    public Map<String, String[]> getArrayMap();

    public String[] getStringArray();
}
