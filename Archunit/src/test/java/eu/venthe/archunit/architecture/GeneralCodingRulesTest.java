package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

public class GeneralCodingRulesTest extends ArchitectureTest {

    @ArchTest
    private final ArchRule noClassesShouldUseStandardStreams = noClasses()
            .should(ACCESS_STANDARD_STREAMS);

    @ArchTest
    private final ArchRule noClassesShouldThrowGenericExceptions = noClasses()
            .should(THROW_GENERIC_EXCEPTIONS);

    @ArchTest
    private final ArchRule noClassesShouldUseStandardLogging = noClasses()
            .should(USE_JAVA_UTIL_LOGGING);
}
