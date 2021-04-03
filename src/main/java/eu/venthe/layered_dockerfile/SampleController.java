package eu.venthe.layered_dockerfile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/")
    public String getExample() {
        return "Example";
    }
}
