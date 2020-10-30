package me.progbloom.datagrid.app.intercom;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RemoteStorageValue {

    @NotNull
    private final String value;

    @NotNull
    private final Long epochMilli;

    public RemoteStorageValue(@NotNull String value, @NotNull Long epochMilli) {
        this.value = Objects.requireNonNull(value);
        this.epochMilli = Objects.requireNonNull(epochMilli);
    }

    @NotNull
    public String getValue() {
        return value;
    }

    @NotNull
    public Long getEpochMilli() {
        return epochMilli;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteStorageValue that = (RemoteStorageValue) o;
        return value.equals(that.value) &&
            epochMilli.equals(that.epochMilli);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, epochMilli);
    }

    @Override
    public String toString() {
        return "RemoteStorageValue{" +
            "value='" + value + '\'' +
            ", epochTime=" + epochMilli +
            '}';
    }
}

