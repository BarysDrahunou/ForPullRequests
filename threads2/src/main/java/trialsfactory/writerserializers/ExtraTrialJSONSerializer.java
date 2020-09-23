package trialsfactory.writerserializers;

import com.google.gson.stream.JsonWriter;
import trials.*;

import java.io.IOException;

import static constants.TrialsConstants.TRIAL_MARK3;

public class ExtraTrialJSONSerializer extends TrialJSONSerializer {

    @Override
    protected void writeArgs(Trial trial, JsonWriter writer) throws IOException {
        super.writeArgs(trial, writer);
        writer.name(TRIAL_MARK3).value(((ExtraTrial) trial).getMark3());
    }
}
