package eu.venthe.archunit.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.example.layers.controller.one.UseCaseOneTwoController;
import com.tngtech.archunit.example.layers.controller.two.UseCaseTwoController;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.Slice;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
public class SlicesIsolationTest extends ArchitectureTest {
    @ArchTest
    static final ArchRule controllers_should_only_use_their_own_slice =
            slices().matching("..controller.(*)..").namingSlices("Controller $1")
                    .as("Controllers").should().notDependOnEachOther();

    @ArchTest
    static final ArchRule specific_controllers_should_only_use_their_own_slice =
            slices().matching("..controller.(*)..").namingSlices("Controller $1")
                    .that(containDescription("Controller one"))
                    .or(containDescription("Controller two"))
                    .as("Controllers one and two").should().notDependOnEachOther();

    @ArchTest
    static final ArchRule controllers_should_only_use_their_own_slice_with_custom_ignore =
            slices().matching("..controller.(*)..").namingSlices("Controller $1")
                    .as("Controllers").should().notDependOnEachOther()
                    .ignoreDependency(UseCaseOneTwoController.class, UseCaseTwoController.class)
                    .ignoreDependency(nameMatching(".*controller\\.three.*"), alwaysTrue());

    private static DescribedPredicate<Slice> containDescription(final String descriptionPart) {
        return new DescribedPredicate<Slice>("contain description '%s'", descriptionPart) {
            @Override
            public boolean apply(Slice input) {
                return input.getDescription().contains(descriptionPart);
            }
        };
    }
}
