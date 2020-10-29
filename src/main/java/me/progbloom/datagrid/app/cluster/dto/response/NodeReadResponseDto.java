package me.progbloom.datagrid.app.cluster.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Ответ на чтение данных с ноды.
 */
public final class NodeReadResponseDto {

    /**
     * Данные.
     */
    @NotNull
    @JsonProperty("data")
    private final String data;

    /**
     * Время сохранения данных.
     */
    @NotNull
    @JsonProperty("epochMilli")
    private final Long timestamp;

    @JsonCreator
    public NodeReadResponseDto(@NotNull @JsonProperty("data") String data,
                               @NotNull @JsonProperty("epochMilli") Long timestamp) {
        this.data = Objects.requireNonNull(data);
        this.timestamp = Objects.requireNonNull(timestamp);
    }

    @NotNull
    public String getData() {
        return data;
    }

    @NotNull
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeReadResponseDto that = (NodeReadResponseDto) o;
        return data.equals(that.data) &&
            timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, timestamp);
    }

    @Override
    public String toString() {
        return "NodeReadResponseDto{" +
            "data='" + data + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
