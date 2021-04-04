package eu.venthe.layered_dockerfile;

import eu.venthe.layered_dockerfile.super_utils.SuperUtils;
import eu.venthe.layered_dockerfile.super_utils.utils.ShouldBeSeparated;
import eu.venthe.layered_dockerfile.utils.Ni;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.text.MessageFormat.format;

@RestController
public class SampleController {
    @GetMapping("/")
    public String getExample() {
        return format("Example {0}! {1}; {2}.", Ni.ni(), SuperUtils.getValue(), ShouldBeSeparated.getValue());
    }
}
