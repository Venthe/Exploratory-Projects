package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import eu.venthe.archunit.library.model.AbstractController;
import eu.venthe.archunit.library.stereotypes.MyController;
import eu.venthe.archunit.library.stereotypes.MyService;
import org.springframework.context.annotation.Configuration;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
public class NamingConventionTest extends ArchitectureTest {

    @ArchTest
    static ArchRule services_should_be_prefixed =
            classes()
                    .that().resideInAPackage("..service..")
                    .and().areAnnotatedWith(MyService.class)
                    .should().haveSimpleNameStartingWith("Service");

    @ArchTest
    static ArchRule controllers_should_not_have_Gui_in_name =
            classes()
                    .that().resideInAPackage("..controller..")
                    .should().haveSimpleNameNotContaining("Gui");

    @ArchTest
    static ArchRule controllers_should_be_suffixed =
            classes()
                    .that().resideInAPackage("..controller..")
                    .or().areAnnotatedWith(MyController.class)
                    .or().areAssignableTo(AbstractController.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static ArchRule classes_named_controller_should_be_in_a_controller_package =
            classes()
                    .that().haveSimpleNameContaining("Controller")
                    .should().resideInAPackage("..controller..");

    @ArchTest
    private final ArchRule allConfigurationClassesShouldBeInConfigurationPackage = noClasses()
            .that().areAnnotatedWith(Configuration.class)
            .should().resideInAPackage("..configuration..");
}
