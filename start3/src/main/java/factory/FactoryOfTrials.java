package factory;

import trials.ExtraTrial;
import trials.Trial;
import com.google.gson.*;
import org.apache.logging.log4j.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class FactoryOfTrials {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();

    public static Optional<Trial> getTrial(JsonObject jsonObject) {
        try {
            JsonObject args = jsonObject.get("args").getAsJsonObject();
            String className = jsonObject.get("class").getAsString();
            Class<?> clazz = Class.forName("trials." + className);
            Trial trial = GSON.fromJson(args, (Type) clazz);
            checkExtraData(jsonObject, className);
            return checkArgumentsOfTrial(trial, clazz);
        } catch (ClassNotFoundException | NumberFormatException
                | NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    private static void checkExtraData(JsonObject jsonObject, String className) {
        jsonObject.remove("class");
        JsonObject args = jsonObject.get("args").getAsJsonObject();
        String[] propertiesToRemove = new String[]{"account", "mark1", "mark2"};
        Arrays.stream(propertiesToRemove).forEach(args::remove);
        if (className.equals("ExtraTrial")) {
            args.remove("mark3");
        }
        if (args.toString().length() == 2) {
            jsonObject.remove("args");
        }
        if (jsonObject.toString().length() > 2) {
            LOGGER.warn(String.format("Redundant data - %s", jsonObject));
        }
    }

    private static Optional<Trial> checkArgumentsOfTrial(Trial trial, Class<?> clazz) throws NoSuchMethodException
            , IllegalAccessException, InvocationTargetException, InstantiationException {
        if (isAllMarksValid(trial) && isAccountValid(trial)) {
            return Optional.of((Trial) clazz.getConstructor(clazz).newInstance(trial));
        } else {
            return Optional.empty();
        }
    }
    private static boolean isAllMarksValid(Trial trial) {
        boolean firstMarkValid=isMarkValid(trial.getMark1());
        boolean secondMarkValid=isMarkValid(trial.getMark2());
        boolean thirdMarkValid= !(trial instanceof ExtraTrial) || isMarkValid(((ExtraTrial) trial).getMark3());
        return firstMarkValid&&secondMarkValid&&thirdMarkValid;
    }

    private static boolean isMarkValid(int mark) {
        return mark >= 0 && mark <= 100;
    }

    private static boolean isAccountValid(Trial trial) {
        String account = trial.getAccount();
        return Objects.nonNull(account) && !account.isEmpty();
    }
}
