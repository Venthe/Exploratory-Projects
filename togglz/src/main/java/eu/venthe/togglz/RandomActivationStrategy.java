package eu.venthe.togglz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.togglz.core.activation.GradualActivationStrategy;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.spi.ActivationStrategy;
import org.togglz.core.user.FeatureUser;

import java.util.Random;

@Slf4j
@Component
public class RandomActivationStrategy extends GradualActivationStrategy implements ActivationStrategy {
    public static final String PARAM_PERCENTAGE = "percentage";
    private static final Random random = new Random();

    @Override
    public String getId() {
        return "random";
    }

    @Override
    public String getName() {
        return "Random activation strategy";
    }

    @Override
    public boolean isActive(FeatureState featureState, FeatureUser user) {
        var percentage = percentage(featureState);

        if (percentage == 100) {
            return true;
        } else if (percentage == 0) {
            return false;
        }

        return randomPercent() < percentage;
    }

    private double randomPercent() {
        return random.nextDouble() * 100;
    }

    private int percentage(FeatureState featureState) {

        String percentageAsString = featureState.getParameter(PARAM_PERCENTAGE);
        try {
            var integer = Integer.parseInt(percentageAsString);
            if (integer > 100) throw new NumberFormatException("Percentage is too high");
            if (integer < 0) throw new NumberFormatException("Percentage is too low");
            return integer;
        } catch (NumberFormatException e) {
            log.error("Invalid gradual rollout percentage for feature " + featureState.getFeature().name() + ": "
                    + percentageAsString);
        }
        return 0;
    }
}
