package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
public abstract class ArchitectureTest {
    public static final String BASE_PACKAGE = "eu.venthe.archunit..";
    public static final String ADAPTERS_PACKAGE = BASE_PACKAGE + "adapters..";
    public static final String PRIMARY_ADAPTERS_PACKAGE = BASE_PACKAGE + "user_interface.adapters..";
    public static final String SECONDARY_ADAPTERS_PACKAGE = BASE_PACKAGE + "infrastructure.adapters..";
    public static final String CORE_PACKAGE = BASE_PACKAGE + "core..";
    public static final String APPLICATION_PACKAGE = CORE_PACKAGE + "application..";
    public static final String DOMAIN_PACKAGE = CORE_PACKAGE + "domain..";

}
