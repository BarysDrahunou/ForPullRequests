package trialsfactory;

import trialsfactory.readervalidators.ExtraTrialValidator;
import trialsfactory.readervalidators.TrialValidator;
import trials.*;
import org.apache.logging.log4j.*;

import java.util.*;

public class FactoryOfTrials {

    private static final Logger LOGGER = LogManager.getLogger();

    private enum TrialKind {

        TRIAL(new TrialValidator(new Trial())),
        LIGHT_TRIAL(new TrialValidator(new LightTrial())),
        STRONG_TRIAL(new TrialValidator(new StrongTrial())),
        EXTRA_TRIAL(new ExtraTrialValidator(new ExtraTrial()));

        private final TrialValidator validator;

        TrialKind(TrialValidator validator) {
            this.validator = validator;
        }

        Trial getTrial(String account
                , int mark1, int mark2, int mark3) {
            return validator.getValidTrial(account, mark1, mark2, mark3);
        }

    }

    public static Optional<Trial> getTrial(String className, String account
            , int mark1, int mark2, int mark3) {
        String trialKind = className
                .replaceAll("([^_])([A-Z])", "$1_$2")
                .toUpperCase();
        try {
            return Optional.of(TrialKind.valueOf(trialKind).getTrial(account, mark1, mark2, mark3));
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            return Optional.empty();
        }

    }

}