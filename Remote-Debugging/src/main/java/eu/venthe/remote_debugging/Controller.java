package eu.venthe.remote_debugging;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/{variable}")
    String get(@PathVariable String variable) {
        return variable;
    }
}
