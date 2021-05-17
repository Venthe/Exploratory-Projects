package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
class RuleLibraryTest extends ArchitectureTest {
    @ArchTest
    static final ArchTests LIBRARY = ArchTests.in(RuleSetsTest.class);

    @ArchTest
    static final ArchTests FURTHER_CODING_RULES = ArchTests.in(CodingRulesTest.class);

    @ArchTest
    static final ArchTests SLICES_ISOLATION_RULES = ArchTests.in(SlicesIsolationTest.class);
}
