package me.progbloom.datagrid.app.data;

import me.progbloom.datagrid.app.config.web.error.ErrorCode;
import me.progbloom.datagrid.app.config.web.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public ResponseEntity<Long> saveData(@RequestBody(required = true) String data) {
        Long id = dataService.save(data);
        return ResponseEntity.ok(id);
    }

    @RequestMapping(value = "read/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> readData(@PathVariable(name = "id", required = true) Long id) {
        Optional<String> data = dataService.read(id);
        if (data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(ErrorCode.DATA_NOT_FOUND));
        }
        return ResponseEntity.ok(data.get());
    }
}
