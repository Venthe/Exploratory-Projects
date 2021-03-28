package eu.venthe.togglz;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;

@RequiredArgsConstructor
@RestController
public class Controller {
    private final FeatureManager featureManager;

    @GetMapping("/data")
    int data() {
        if (featureManager.isActive(() -> "BAR")) {
            return 5;
        }
        return 10;
    }
}
