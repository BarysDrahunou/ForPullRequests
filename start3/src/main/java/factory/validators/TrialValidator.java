package factory.validators;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class TrialValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Trial trial;

    public TrialValidator(Class<? extends Trial> trialClass) {
        this.trial = createTrial(trialClass);
    }
    private Trial createTrial(Class<? extends Trial> trialClass){
        try {
            return trialClass.getConstructor().newInstance();
        } catch (NoSuchMethodException |
                IllegalAccessException | InvocationTargetException | InstantiationException e) {
            LOGGER.error(e);
            return new Trial();
        }
    }

    public Trial getValidTrial(JsonObject jsonObject) {
        Trial trial=this.trial.copy();
        validateSetAndCheckExtraData(jsonObject, 3, trial);
        return trial;
    }

    protected void validateSetAndCheckExtraData(JsonObject jsonObject, int size, Trial trial) {
        checkArgs(jsonObject, size);
        validateFieldsAndSet(trial, jsonObject);
        checkExtraData(jsonObject);
    }

    private void checkArgs(JsonObject jsonObject, int size) {
        if (jsonObject.size() < size) {
            throw new JsonSyntaxException("Not enough arguments");
        }
    }

    protected void validateFieldsAndSet(Trial trial, JsonObject jsonObject) {
        String account = jsonObject.get("account").getAsString();
        int mark1 = jsonObject.get("mark1").getAsInt();
        int mark2 = jsonObject.get("mark2").getAsInt();
        trial.setAccount(validateStringField(account));
        trial.setMark1(validateIntegerField(mark1));
        trial.setMark2(validateIntegerField(mark2));
    }

    private String validateStringField(String account) {
        if (Objects.nonNull(account) && !account.isEmpty()) {
            return account;
        } else {
            throw new JsonSyntaxException("The field is invalid: " + account);
        }
    }

    protected int validateIntegerField(int fieldValue) {
        if (fieldValue >= 0 && fieldValue <= 100) {
            return fieldValue;
        } else {
            throw new JsonSyntaxException("The field is invalid: " + fieldValue);
        }
    }

    private void checkExtraData(JsonObject jsonObject) {
        removeUsedData(jsonObject);
        if (jsonObject.size() > 0) {
            LOGGER.warn(String.format("Redundant data - %s", jsonObject));
        }
    }

    protected void removeUsedData(JsonObject jsonObject) {
        jsonObject.remove("account");
        jsonObject.remove("mark1");
        jsonObject.remove("mark2");
    }

}