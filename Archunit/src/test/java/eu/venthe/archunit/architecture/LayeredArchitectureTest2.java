package eu.venthe.archunit.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

public class LayeredArchitectureTest2 extends ArchitectureTest {

    private static final String DOMAIN_LAYER = "domain layer";
    private static final String APPLICATION_LAYER = "application layer";
    private static final String ADAPTERS_LAYER = "adapters layer";

    private static Architectures.LayeredArchitecture portsAndAdaptersArchitecture = Architectures
            .layeredArchitecture()
            .layer(DOMAIN_LAYER).definedBy(DOMAIN_PACKAGE)
            .layer(APPLICATION_LAYER).definedBy(APPLICATION_PACKAGE)
            .layer(ADAPTERS_LAYER).definedBy(ADAPTERS_PACKAGE);

    @ArchTest
    private final ArchRule domainLayerShouldOnlyBeAccessedByApplicationLayer = portsAndAdaptersArchitecture.whereLayer(DOMAIN_LAYER)
            .mayOnlyBeAccessedByLayers(APPLICATION_LAYER);

    @ArchTest
    private final ArchRule applicationLayerMayOnlyBeAccessedByAdaptersLayer = portsAndAdaptersArchitecture
            .whereLayer(APPLICATION_LAYER)
            .mayOnlyBeAccessedByLayers(ADAPTERS_LAYER);

    @ArchTest
    private final ArchRule adaptersLayerShouldNotBeAccessedByAnyLayer = portsAndAdaptersArchitecture.whereLayer(ADAPTERS_LAYER)
            .mayNotBeAccessedByAnyLayer();
}
