package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class DomainComponentsTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule domainClassesShouldOnlyBeAccessedByOtherDomainClassesOrTheApplicationLayer = classes()
            .that().resideInAPackage(DOMAIN_PACKAGE)
            .should().onlyBeAccessed().byAnyPackage(DOMAIN_PACKAGE, APPLICATION_PACKAGE);

    @ArchTest
    private final ArchRule domainClassesShouldOnlyDependOnDomainOrStdLibClasses = classes()
            .that().resideInAPackage(DOMAIN_PACKAGE)
            .should().onlyAccessClassesThat().resideInAnyPackage(DOMAIN_PACKAGE);
}
