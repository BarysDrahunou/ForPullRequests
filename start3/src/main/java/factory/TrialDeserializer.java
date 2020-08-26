package factory;

import com.google.gson.*;
import factory.validators.ExtraTrialValidator;
import factory.validators.TrialValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrialDeserializer implements JsonDeserializer<Optional<Trial>> {

    private static final Logger LOGGER = LogManager.getLogger();

    private enum TrialKind {

        TRIAL(new TrialValidator(Trial.class)),
        LIGHT_TRIAL(new TrialValidator(LightTrial.class)),
        STRONG_TRIAL(new TrialValidator(StrongTrial.class)),
        EXTRA_TRIAL(new ExtraTrialValidator(ExtraTrial.class));

        private final TrialValidator validator;

        TrialKind(TrialValidator validator) {
            this.validator = validator;
        }

        Trial getTrial(JsonElement element) throws InvocationTargetException
                , NoSuchMethodException, InstantiationException, IllegalAccessException {
            return validator.getValidTrial(element);
        }

    }

    @Override
    public Optional<Trial> deserialize(JsonElement element,
                                       Type type,
                                       JsonDeserializationContext context) {
        try {
            String trialKind = Arrays.stream(((Class<?>) type)
                    .getSimpleName()
                    .split("(?<=[a-z])(?=[A-Z])"))
                    .map(String::toUpperCase)
                    .collect(Collectors.joining("_"));
            return Optional.of(TrialKind.valueOf(trialKind).getTrial(element));
        } catch (InvocationTargetException | NoSuchMethodException
                | InstantiationException | IllegalAccessException | JsonSyntaxException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

}