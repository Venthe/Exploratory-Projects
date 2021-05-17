package eu.venthe.archunit.architecture;

import com.tngtech.archunit.example.layers.SomeBusinessInterface;
import com.tngtech.archunit.example.layers.SomeOtherBusinessInterface;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.base.DescribedPredicate.lessThanOrEqualTo;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
public class RestrictNumberOfClassesWithACertainPropertyTest extends ArchitectureTest {

    @ArchTest
    static final ArchRule no_new_classes_should_implement_SomeBusinessInterface =
            classes().that().implement(SomeBusinessInterface.class)
                    .should().containNumberOfElements(lessThanOrEqualTo(1))
                    .because("from now on new classes should implement " + SomeOtherBusinessInterface.class.getName());

}
