package test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:/"},
        plugin = {"pretty"},
        tags = {"@Smoke", "not @Disabled"}
)
public class TestRunner {
}
