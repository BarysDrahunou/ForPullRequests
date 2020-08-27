package factory.validators;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import trials.*;

public class ExtraTrialValidator extends TrialValidator {

    public ExtraTrialValidator(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public ExtraTrial getValidTrial(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        if (jsonObject.size() < 4) {
            throw new JsonSyntaxException("Not enough arguments");
        }
        ExtraTrial trial = new ExtraTrial();
        super.validateFieldsAndSet(trial, jsonObject);
        int mark3 = jsonObject.get("mark3").getAsInt();
        trial.setMark3(validateIntegerField(mark3));
        checkExtraData(jsonObject);
        return trial;
    }

    @Override
    protected void removeUsedData(JsonObject jsonObject) {
        super.removeUsedData(jsonObject);
        jsonObject.remove("mark3");
    }
}
