package factory.validators;

import com.google.gson.JsonObject;
import trials.*;

public class ExtraTrialValidator extends TrialValidator {

    ExtraTrial extraTrial;

    public ExtraTrialValidator(Class<? extends Trial> trialClass) {
        super(trialClass);
    }

    @Override
    public ExtraTrial getValidTrial(JsonObject jsonObject) {
        this.extraTrial = new ExtraTrial();
        validateSetAndCheckExtraData(jsonObject, 4, extraTrial);
        return extraTrial;
    }

    @Override
    protected void validateFieldsAndSet(Trial trial, JsonObject jsonObject) {
        super.validateFieldsAndSet(trial, jsonObject);
        int mark3 = jsonObject.get("mark3").getAsInt();
        extraTrial.setMark3(validateIntegerField(mark3));
    }

    @Override
    protected void removeUsedData(JsonObject jsonObject) {
        super.removeUsedData(jsonObject);
        jsonObject.remove("mark3");
    }
}
