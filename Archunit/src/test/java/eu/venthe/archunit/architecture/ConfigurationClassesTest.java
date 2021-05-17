package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.context.annotation.Configuration;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ConfigurationClassesTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule configurationClassesShouldBeAnnotatedWithConfigurationAnnotation = classes()
            .that().haveSimpleNameEndingWith("Configuration")
            .should().beAnnotatedWith(Configuration.class);

    @ArchTest
    private final ArchRule configurationClassesShouldBeSuffixed = classes()
            .that().areAnnotatedWith(Configuration.class)
            .should().haveSimpleNameEndingWith("Configuration");

    @ArchTest
    private final ArchRule noClassesWithConfigurationAnnotationShouldResideOutsideOfAdaptersLayerPackages = noClasses()
            .that().areAnnotatedWith(Configuration.class)
            .should().resideOutsideOfPackage("..adapters..");
}
