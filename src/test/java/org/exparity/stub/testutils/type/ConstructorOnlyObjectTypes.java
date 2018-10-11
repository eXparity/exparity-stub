package org.exparity.stub.testutils.type;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class ConstructorOnlyObjectTypes {

    public static enum EnumValues {
        VALUE_1, VALUE_2
    };

    private final String stringValue;
    private final Integer integerObjectValue;
    private final Long longObjectValue;
    private final Short shortObjectValue;
    private final Double doubleObjectValue;
    private final Float floatObjectValue;
    private final Character charObjectValue;
    private final Byte byteObjectValue;
    private final Date dateValue;
    private final LocalDate localDateValue;
    private final LocalTime localTimeValue;
    private final LocalDateTime localDateTimeValue;
    private final ZonedDateTime zonedDateTimeValue;
    private final Duration duration;
    private final Instant instantValue;
    private final BigDecimal bigDecimalValue;
    private final Boolean booleanObjectValue;

    public ConstructorOnlyObjectTypes(final String stringValue,
            final Integer integerObjectValue,
            final Long longObjectValue,
            final Short shortObjectValue,
            final Double doubleObjectValue,
            final Float floatObjectValue,
            final Character charObjectValue,
            final Byte byteObjectValue,
            final Date dateValue,
            final LocalDate localDateValue,
            final LocalTime localTimeValue,
            final LocalDateTime localDateTimeValue,
            final ZonedDateTime zonedlDateTimeValue,
            final Instant instantValue,
            final BigDecimal bigDecimalValue,
            final Boolean booleanObjectValue,
            final Duration duration) {
        this.duration = duration;
        this.stringValue = stringValue;
        this.integerObjectValue = integerObjectValue;
        this.longObjectValue = longObjectValue;
        this.shortObjectValue = shortObjectValue;
        this.doubleObjectValue = doubleObjectValue;
        this.floatObjectValue = floatObjectValue;
        this.charObjectValue = charObjectValue;
        this.byteObjectValue = byteObjectValue;
        this.dateValue = dateValue;
        this.localDateValue = localDateValue;
        this.localTimeValue = localTimeValue;
        this.localDateTimeValue = localDateTimeValue;
        this.zonedDateTimeValue = zonedlDateTimeValue;
        this.instantValue = instantValue;
        this.bigDecimalValue = bigDecimalValue;
        this.booleanObjectValue = booleanObjectValue;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Integer getIntegerObjectValue() {
        return integerObjectValue;
    }

    public Long getLongObjectValue() {
        return longObjectValue;
    }

    public Short getShortObjectValue() {
        return shortObjectValue;
    }

    public Double getDoubleObjectValue() {
        return doubleObjectValue;
    }

    public Float getFloatObjectValue() {
        return floatObjectValue;
    }

    public Character getCharObjectValue() {
        return charObjectValue;
    }

    public Byte getByteObjectValue() {
        return byteObjectValue;
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

    public ZonedDateTime getZonedDateTimeValue() {
        return zonedDateTimeValue;
    }

    public Instant getInstantValue() {
        return instantValue;
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }

    public Boolean getBooleanObjectValue() {
        return booleanObjectValue;
    }

}