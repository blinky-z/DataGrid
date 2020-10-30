package me.progbloom.datagrid.app.intercom;

import me.progbloom.datagrid.app.data.storage.Storage;
import me.progbloom.datagrid.app.data.storage.StorageValue;
import me.progbloom.datagrid.app.intercom.dto.request.NodeWriteRequestDto;
import me.progbloom.datagrid.app.intercom.dto.response.NodeReadResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Контроллер для внутреннего общения нод в кластере по HTTP протоколу.
 */
@Controller
@RequestMapping("inter-api")
public class InterCommunicationController {

    private final Storage storage;

    public InterCommunicationController(Storage storage) {
        this.storage = storage;
    }

    @RequestMapping(value = "read", method = RequestMethod.GET)
    public ResponseEntity<NodeReadResponseDto> read(@RequestParam(name = "id", required = true) Long id) {
        Optional<StorageValue> value = storage.get(id);
        if (value.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
            new NodeReadResponseDto(value.get().getValue(), value.get().getTimestamp().toEpochMilli())
        );
    }

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public ResponseEntity<Void> write(@RequestBody(required = true) NodeWriteRequestDto request) {
        storage.put(request.getId(), request.getData());
        return ResponseEntity.ok().build();
    }
}
