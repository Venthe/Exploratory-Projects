package eu.venthe.togglz.programmatic;

import org.togglz.core.Feature;
import org.togglz.core.annotation.*;

public enum MyFeatures implements Feature {

    @Label("Enable database caching")
    @Performance
    DATABASE_CACHING,

    @FeatureGroup("Performance Improvements")
    PERFORMANCE_IMPROVEMENTS,

    @EnabledByDefault
    @Label("Some other one")
    OTHER_FEATURE,

    /**
     * {@link org.togglz.core.activation.ReleaseDateActivationStrategy}
     */
    @DefaultActivationStrategy(
            id = "release-date",
            parameters = {
                    @ActivationParameter(name = "date", value = "28.03.2020"),
                    @ActivationParameter(name = "time", value = "12:00"),
            }
    )
    @Label("Some other one 2")
    @InfoLink("whatever")
    @Owner("Venthe")
    OTHER_FEATURE_2;

}
