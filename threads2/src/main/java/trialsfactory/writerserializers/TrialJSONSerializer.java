package trialsfactory.writerserializers;

import com.google.gson.stream.JsonWriter;
import trials.Trial;

import java.io.IOException;


public class TrialJSONSerializer  {

    public void serialize(Trial trial, JsonWriter writer) throws IOException {
        writer.beginObject();
        writeEntireTrial(trial, writer);
        writer.endObject();
    }

    private void writeEntireTrial(Trial trial, JsonWriter writer) throws IOException {
        writer.name("class").value(trial.getClass().getSimpleName());
        writer.name("args");
        writer.beginObject();
        writeArgs(trial, writer);
        writer.endObject();
    }

    protected void writeArgs(Trial trial, JsonWriter writer) throws IOException {
        writer.name("account").value(trial.getAccount());
        writer.name("mark1").value(trial.getMark1());
        writer.name("mark2").value(trial.getMark2());
    }

}
