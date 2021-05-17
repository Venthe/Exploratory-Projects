package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static eu.venthe.archunit.architecture.conditions.HaveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType.haveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType;

public class ApplicationComponentsTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule useCaseClassesShouldBeAnnotatedWithServiceAnnotation = classes()
            .that().haveSimpleNameEndingWith("UseCase")
            .should().beAnnotatedWith(Service.class);

    @ArchTest
    private final ArchRule noUseCaseClassesShouldResideOutsideTheApplicationLayer = noClasses()
            .that().haveSimpleNameEndingWith("UseCase")
            .should().resideOutsideOfPackage(APPLICATION_PACKAGE);

    @ArchTest
    private final ArchRule noClassesWithServiceAnnotationShouldResideOutsideTheApplicationLayer = noClasses()
            .that().areAnnotatedWith(Service.class)
            .should().resideOutsideOfPackage(APPLICATION_PACKAGE);

    @ArchTest
    private final ArchRule useCaseClassesShouldHaveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType = classes()
            .that().haveSimpleNameEndingWith("UseCase")
            .should(haveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType);

}
