package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import eu.venthe.archunit.library.stereotypes.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

public class SpringCodingRulesTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule springSingletonComponentsShouldOnlyHaveFinalFields = classes()
            .that().areAnnotatedWith(Service.class)
            .or().areAnnotatedWith(Component.class)
            .and().areNotAnnotatedWith(ConfigurationProperties.class)
            .or().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .or().areAnnotatedWith(Repository.class)
            .should().haveOnlyFinalFields();

    @ArchTest
    private final ArchRule springFieldDependencyInjectionShouldNotBeUsed = noFields()
            .should().beAnnotatedWith(Autowired.class);
}
