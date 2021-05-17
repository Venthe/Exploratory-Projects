package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import eu.venthe.archunit.library.stereotypes.*;
import org.springframework.stereotype.Controller;

public class PrimaryAdaptersComponentsTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule controllerClassesShouldBeAnnotatedWithControllerOrRestControllerAnnotation = ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().beAnnotatedWith(Controller.class)
            .orShould().beAnnotatedWith(RestController.class);

    @ArchTest
    private final ArchRule noClassesWithControllerOrRestControllerAnnotationShouldResideOutsideOfPrimaryAdaptersPackages = ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .should().resideOutsideOfPackage(PRIMARY_ADAPTERS_PACKAGE);

    @ArchTest
    private final ArchRule controllerClassesShouldNotDependOnEachOther = ArchRuleDefinition.noClasses()
            .that().haveSimpleNameEndingWith("Controller")
            .should().dependOnClassesThat().haveSimpleNameEndingWith("Controller");

    @ArchTest
    private final ArchRule publicControllerMethodsShouldBeAnnotatedWithARequestMapping = ArchRuleDefinition.methods()
            .that().arePublic()
            .and().areDeclaredInClassesThat().resideInAPackage("..adapters.primary.web..")
            .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Controller")
            .and().areDeclaredInClassesThat().areAnnotatedWith(Controller.class)
            .or().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
            .should().beAnnotatedWith(RequestMapping.class)
            .orShould().beAnnotatedWith(GetMapping.class)
            .orShould().beAnnotatedWith(PostMapping.class)
            .orShould().beAnnotatedWith(PatchMapping.class)
            .orShould().beAnnotatedWith(DeleteMapping.class);
}
