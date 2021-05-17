package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.dependencies.SliceRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class SlicesTest extends ArchitectureTest {

    @ArchTest
    private final SliceRule layersShouldBeFreeOfCycles = slices()
            .matching("com.company.app.(*)..")
            .should().beFreeOfCycles();

    @ArchTest
    private final SliceRule adaptersShouldNotDependOnEachOther = slices()
            .matching("com.company.app.adapters.(**)")
            .should().notDependOnEachOther();
}
