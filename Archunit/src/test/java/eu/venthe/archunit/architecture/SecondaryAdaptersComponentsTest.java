package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import eu.venthe.archunit.library.stereotypes.Document;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class SecondaryAdaptersComponentsTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule repositoryClassesShouldBeAnnotatedWithRepositoryAnnotation = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .and().areNotInterfaces()
            .should().beAnnotatedWith(Repository.class);

    @ArchTest
    private final ArchRule noClassesWithRepositoryAnnotationShouldResideOutsideOfSecondaryAdaptersPackages = noClasses()
            .that().areAnnotatedWith(Repository.class)
            .should().resideOutsideOfPackage(SECONDARY_ADAPTERS_PACKAGE);

    @ArchTest
    private final ArchRule documentClassesShouldBeAnnotatedWithDocumentAnnotation = classes()
            .that().haveSimpleNameEndingWith("Document")
            .should().beAnnotatedWith(Document.class);

    @ArchTest
    private final ArchRule noClassesWithDocumentAnnotationShouldResideOutsideOfSecondaryAdaptersPackages = noClasses()
            .that().areAnnotatedWith(Document.class)
            .should().resideOutsideOfPackage(SECONDARY_ADAPTERS_PACKAGE);
}
