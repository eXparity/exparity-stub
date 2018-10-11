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

public class AllTypes {

    public static enum EnumValues {
        VALUE_1, VALUE_2
    };

    private AllTypes.EnumValues enumValue;
    private boolean booleanValue;
    private Boolean booleanObjectValue;
    private String stringValue;
    private Integer integerObjectValue;
    private int integerValue;
    private Long longObjectValue;
    private long longValue;
    private Short shortObjectValue;
    private short shortValue;
    private Double doubleObjectValue;
    private double doubleValue;
    private Float floatObjectValue;
    private float floatValue;
    private Character charObjectValue;
    private char charValue;
    private Byte byteObjectValue;
    private byte byteValue;
    private Date dateValue;
    private LocalDate localDateValue;
    private LocalTime localTimeValue;
    private LocalDateTime localDateTimeValue;
    private ZonedDateTime zonedlDateTimeValue;
    private Instant instantValue;
    private Duration durationValue;
    private BigDecimal bigDecimalValue;
    private int[] array;
    private Collection<String> collection;
    private List<String> list;
    private Set<String> set;
    private Map<Long, String> map;
    private Map<String, String[]> arrayMap;
    private String[] stringArray;

    public Map<String, String[]> getArrayMap() {
        return arrayMap;
    }

    public void setArrayMap(final Map<String, String[]> arrayMap) {
        this.arrayMap = arrayMap;
    }

    public AllTypes.EnumValues getEnumValue() {
        return this.enumValue;
    }

    public void setEnumValue(final AllTypes.EnumValues enumValue) {
        this.enumValue = enumValue;
    }

    public Collection<String> getCollection() {
        return this.collection;
    }

    public void setCollection(final Collection<String> collection) {
        this.collection = collection;
    }

    public List<String> getList() {
        return this.list;
    }

    public void setList(final List<String> list) {
        this.list = list;
    }

    public Set<String> getSet() {
        return this.set;
    }

    public void setSet(final Set<String> set) {
        this.set = set;
    }

    public Map<Long, String> getMap() {
        return this.map;
    }

    public void setMap(final Map<Long, String> map) {
        this.map = map;
    }

    public int[] getArray() {
        return this.array;
    }

    public void setArray(final int[] array) {
        this.array = array;
    }

    public boolean isBooleanValue() {
        return this.booleanValue;
    }

    public void setBooleanValue(final boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Boolean getBooleanObjectValue() {
        return this.booleanObjectValue;
    }

    public void setBooleanObjectValue(final Boolean booleanObjectValue) {
        this.booleanObjectValue = booleanObjectValue;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getIntegerObjectValue() {
        return this.integerObjectValue;
    }

    public void setIntegerObjectValue(final Integer integerObjectValue) {
        this.integerObjectValue = integerObjectValue;
    }

    public int getIntegerValue() {
        return this.integerValue;
    }

    public void setIntegerValue(final int integerValue) {
        this.integerValue = integerValue;
    }

    public Long getLongObjectValue() {
        return this.longObjectValue;
    }

    public void setLongObjectValue(final Long longObjectValue) {
        this.longObjectValue = longObjectValue;
    }

    public long getLongValue() {
        return this.longValue;
    }

    public void setLongValue(final long longValue) {
        this.longValue = longValue;
    }

    public Short getShortObjectValue() {
        return this.shortObjectValue;
    }

    public void setShortObjectValue(final Short shortObjectValue) {
        this.shortObjectValue = shortObjectValue;
    }

    public short getShortValue() {
        return this.shortValue;
    }

    public void setShortValue(final short shortValue) {
        this.shortValue = shortValue;
    }

    public Double getDoubleObjectValue() {
        return this.doubleObjectValue;
    }

    public void setDoubleObjectValue(final Double doubleObjectValue) {
        this.doubleObjectValue = doubleObjectValue;
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public void setDoubleValue(final double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Float getFloatObjectValue() {
        return this.floatObjectValue;
    }

    public void setFloatObjectValue(final Float floatObjectValue) {
        this.floatObjectValue = floatObjectValue;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(final float floatValue) {
        this.floatValue = floatValue;
    }

    public Character getCharObjectValue() {
        return this.charObjectValue;
    }

    public void setCharObjectValue(final Character charObjectValue) {
        this.charObjectValue = charObjectValue;
    }

    public char getCharValue() {
        return this.charValue;
    }

    public void setCharValue(final char charValue) {
        this.charValue = charValue;
    }

    public Byte getByteObjectValue() {
        return this.byteObjectValue;
    }

    public void setByteObjectValue(final Byte byteObjectValue) {
        this.byteObjectValue = byteObjectValue;
    }

    public byte getByteValue() {
        return this.byteValue;
    }

    public void setByteValue(final byte byteValue) {
        this.byteValue = byteValue;
    }

    public Date getDateValue() {
        return this.dateValue;
    }

    public void setDateValue(final Date dateValue) {
        this.dateValue = dateValue;
    }

    public BigDecimal getBigDecimalValue() {
        return this.bigDecimalValue;
    }

    public void setBigDecimalValue(final BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }

    public Instant getInstantValue() {
        return this.instantValue;
    }

    public void setInstantValue(final Instant instantValue) {
        this.instantValue = instantValue;
    }

    public LocalDateTime getLocalDateTimeValue() {
        return this.localDateTimeValue;
    }

    public void setLocalDateTimeValue(final LocalDateTime localDateTimeValue) {
        this.localDateTimeValue = localDateTimeValue;
    }

    public LocalDate getLocalDateValue() {
        return this.localDateValue;
    }

    public void setLocalDateValue(final LocalDate localDateValue) {
        this.localDateValue = localDateValue;
    }

    public LocalTime getLocalTimeValue() {
        return this.localTimeValue;
    }

    public void setLocalTimeValue(final LocalTime localTimeValue) {
        this.localTimeValue = localTimeValue;
    }

    public ZonedDateTime getZonedlDateTimeValue() {
        return this.zonedlDateTimeValue;
    }

    public void setZonedlDateTimeValue(final ZonedDateTime zonedlDateTimeValue) {
        this.zonedlDateTimeValue = zonedlDateTimeValue;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(final String[] stringArray) {
        this.stringArray = stringArray;
    }

    public Duration getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(final Duration durationValue) {
        this.durationValue = durationValue;
    }

}
