package me.progbloom.datagrid.app.intercom.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Запрос на запись данных в ноду.
 */
public final class NodeWriteRequestDto {

    /**
     * ID данных.
     */
    @NotNull
    @JsonProperty("id")
    private final Long id;

    /**
     * Данные.
     */
    @NotNull
    @JsonProperty("data")
    private final String data;

    @JsonCreator
    public NodeWriteRequestDto(@NotNull @JsonProperty("id") Long id,
                               @NotNull @JsonProperty("data") String data) {
        this.id = Objects.requireNonNull(id);
        this.data = Objects.requireNonNull(data);
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWriteRequestDto that = (NodeWriteRequestDto) o;
        return id.equals(that.id) &&
            data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data);
    }

    @Override
    public String toString() {
        return "WriteRequestDto{" +
            "id=" + id +
            ", data='" + data + '\'' +
            '}';
    }
}
