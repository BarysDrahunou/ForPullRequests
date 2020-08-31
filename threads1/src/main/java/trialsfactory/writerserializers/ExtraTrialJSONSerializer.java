package trialsfactory.writerserializers;

import com.google.gson.JsonObject;
import trials.*;

public class ExtraTrialJSONSerializer extends TrialJSONSerializer {

    @Override
    protected JsonObject getJsonObject(Trial trial) {
        ExtraTrial extraTrial= (ExtraTrial) trial;
        JsonObject jsonObject= super.getJsonObject(trial);
        JsonObject subObject=jsonObject.getAsJsonObject("args");
        subObject.addProperty("mark3",extraTrial.getMark3());
        return jsonObject;
    }
}
