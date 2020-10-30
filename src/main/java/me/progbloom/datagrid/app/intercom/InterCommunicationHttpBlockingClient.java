package me.progbloom.datagrid.app.intercom;

import me.progbloom.datagrid.app.intercom.dto.request.NodeWriteRequestDto;
import me.progbloom.datagrid.app.intercom.dto.response.NodeReadResponseDto;
import me.progbloom.datagrid.app.intercom.exception.InterCommunicationReadException;
import me.progbloom.datagrid.app.intercom.exception.InterCommunicationWriteException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * Блокирующий клиент для общения между нодами по HTTP протоколу.
 */
@Component
class InterCommunicationHttpBlockingClient implements InterCommunicationClient {

    private final Logger log = LoggerFactory.getLogger(InterCommunicationHttpBlockingClient.class);

    public static final String PATH_PREFIX = "inter-api";

    private final RestTemplate restTemplate;

    public InterCommunicationHttpBlockingClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @NotNull
    public Optional<RemoteStorageValue> read(@NotNull final URI nodeAddress, @NotNull final Long id) {
        Objects.requireNonNull(nodeAddress);
        Objects.requireNonNull(id);

        log.debug("Чтение данных с ноды... ID данных: {}, Адрес: {}", id, nodeAddress);
        ResponseEntity<NodeReadResponseDto> response;
        try {
            response = restTemplate.exchange(
                basePathBuilder(nodeAddress)
                    .pathSegment("read")
                    .queryParam("id", id).toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                NodeReadResponseDto.class
            );
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        } catch (RestClientException ex) {
            throw new InterCommunicationReadException(
                String.format("Ошибка чтения данных из ноды. Хост: %s, ID данных: %s", nodeAddress, id));
        }
        NodeReadResponseDto body = Objects.requireNonNull(response.getBody());
        log.debug("Данные с ноды прочитаны. ID данных: {}, Адрес: {}", id, nodeAddress);
        RemoteStorageValue value = new RemoteStorageValue(body.getData(), body.getTimestamp());
        return Optional.of(value);
    }

    @Override
    public void write(@NotNull final URI nodeAddress, @NotNull final Long id, @NotNull final String data) {
        Objects.requireNonNull(nodeAddress);
        Objects.requireNonNull(id);
        Objects.requireNonNull(data);

        log.debug("Запись данных на ноду... ID данных: {}, Адрес: {}", id, nodeAddress);
        try {
            restTemplate.exchange(
                basePathBuilder(nodeAddress)
                    .pathSegment("write").toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(new NodeWriteRequestDto(id, data)),
                String.class
            );
        } catch (RestClientException ex) {
            throw new InterCommunicationWriteException(String.format("Ошибка записи в ноду. Хост: %s, ID данных: %s",
                nodeAddress, id));
        }
        log.debug("Данные на ноду успешно записаны.. ID данных: {}, Адрес: {}", id, nodeAddress);
    }

    @NotNull
    private UriComponentsBuilder basePathBuilder(@NotNull final URI host) {
        Objects.requireNonNull(host);
        return UriComponentsBuilder.fromUri(host)
            .path(PATH_PREFIX);
    }
}
