package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
class RuleSetsTest extends ArchitectureTest {
    @ArchTest
    private final ArchTests CODING_RULES = ArchTests.in(CodingRulesTest.class);

    @ArchTest
    private final ArchTests NAMING_CONVENTION_RULES = ArchTests.in(ControllerNamingConventionTest.class);
}
