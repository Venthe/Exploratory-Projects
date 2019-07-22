package test;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.venthe.gherkindemo.GherkinDemoApplication;
import eu.venthe.gherkindemo.SampleService;
import eu.venthe.gherkindemo.dependency.SampleDependency;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = GherkinDemoApplication.class)
@ActiveProfiles("mock")
@RequiredArgsConstructor
public class TestMockStepDefinitions {
    @NonNull
    private final SampleService sampleService;
    @NonNull
    private final SampleDependency sampleDependency;


    private String state;

    @Given("a persistence set up with {string}")
    public void aPersistenceSetUpWith(String arg0) {
        sampleDependency.setPersistence(arg0);
    }

    @When("I execute step two")
    public void iExecuteStepTwo() {
        state = sampleService.secondStep();
    }

    @Then("I get result {string}")
    public void iGetResult(String arg0) {
        Assertions.assertThat(arg0).isEqualTo(state);
    }
}
