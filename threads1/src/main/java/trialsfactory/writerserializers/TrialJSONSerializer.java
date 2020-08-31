package trialsfactory.writerserializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import trials.Trial;

public class TrialJSONSerializer {

    public String serialize(Trial trial) {
        return new Gson().toJson(getJsonObject(trial));
    }

    protected JsonObject getJsonObject(Trial trial) {
        JsonObject mainJsonObject = new JsonObject();
        JsonObject subObject = new JsonObject();
        mainJsonObject.addProperty("class", trial.getClass().getSimpleName());
        subObject.addProperty("account", trial.getAccount());
        subObject.addProperty("mark1", trial.getMark1());
        subObject.addProperty("mark2", trial.getMark2());
        mainJsonObject.add("args", subObject);
        return mainJsonObject;
    }
}
