package factory;

import trials.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

import java.lang.reflect.*;
import java.util.*;

public class FactoryOfTrials {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final TrialDeserializer TRIAL_DESERIALIZER = new TrialDeserializer();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Trial.class, TRIAL_DESERIALIZER)
            .registerTypeAdapter(LightTrial.class, TRIAL_DESERIALIZER)
            .registerTypeAdapter(StrongTrial.class, TRIAL_DESERIALIZER)
            .registerTypeAdapter(ExtraTrial.class, TRIAL_DESERIALIZER)
            .create();

    public static Optional<Trial> getTrial(JsonObject jsonObject) {
        try {
            String className = jsonObject.get("class").getAsString();
            JsonObject args = jsonObject.get("args").getAsJsonObject();
            Type classType = Class.forName("trials." + className);
            checkExtraData(jsonObject);
            return GSON.fromJson(args, classType);

        } catch (ClassNotFoundException | IllegalArgumentException
                | NullPointerException | UnsupportedOperationException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    private static void checkExtraData(JsonObject jsonObject) {
        jsonObject.remove("class");
        jsonObject.remove("args");
        if (jsonObject.size() > 0) {
            LOGGER.warn(String.format("Redundant data - %s", jsonObject));
        }
    }

}
