package me.progbloom.datagrid.app.data;

import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping("/")
    public String home() {
        return "Hello, World!";
    }

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public Long saveData(@RequestBody(required = true) String data) {
        return dataService.save(data);
    }

    @RequestMapping(value = "read/{id}", method = RequestMethod.GET)
    public String readData(@PathVariable(name = "id", required = true) Long id) {
        return dataService.read(id).orElse(null);
    }
}
