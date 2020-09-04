package trialsfactory.writerserializers;

import com.google.gson.stream.JsonWriter;
import trials.*;

import java.io.IOException;

public class ExtraTrialJSONSerializer extends TrialJSONSerializer {
    @Override
    protected void writeArgs(Trial trial, JsonWriter writer) throws IOException {
        super.writeArgs(trial, writer);
        writer.name("mark3").value(((ExtraTrial)trial).getMark3());
    }
}
