package test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:/"},
        plugin = {"pretty", "json:target/cucumber-report.json", "html:target/cucumber" },
        strict = true,
        tags = {"@Smoke", "not @Disabled"}
)
public class TestRunner {
}
