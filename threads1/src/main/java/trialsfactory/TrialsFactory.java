package trialsfactory;

import myexceptions.WrongArgumentException;
import trialsfactory.readervalidators.*;
import trials.*;
import org.apache.logging.log4j.*;

import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;
import static constants.TrialsConstants.*;

public class TrialsFactory {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, TrialValidator> TRIAL_VALIDATORS_MAP = new HashMap<>();

    static {
        TRIAL_VALIDATORS_MAP.put(TRIAL, new TrialValidator(new Trial()));
        TRIAL_VALIDATORS_MAP.put(LIGHT_TRIAL, new TrialValidator(new LightTrial()));
        TRIAL_VALIDATORS_MAP.put(STRONG_TRIAL, new TrialValidator(new StrongTrial()));
        TRIAL_VALIDATORS_MAP.put(EXTRA_TRIAL, new ExtraTrialValidator(new ExtraTrial()));
    }

    public static Optional<Trial> getTrial(String className, String account
            , int mark1, int mark2, int mark3) {
        String trialKind = className
                .replaceAll(REGEXP_FOR_SPLIT, REPLACEMENT)
                .toUpperCase();
        try {
            TrialValidator trialValidator = getTrialValidator(trialKind);
            return Optional.of(trialValidator.getValidTrial(account, mark1, mark2, mark3));
        } catch (WrongArgumentException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    private static TrialValidator getTrialValidator(String trialKind) {
        return TRIAL_VALIDATORS_MAP.computeIfAbsent(trialKind, key -> {
            throw new WrongArgumentException(CLASS_DOES_NOT_EXIST, trialKind);
        });
    }
}