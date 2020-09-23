package trialsfactory.writerserializers;

import com.google.gson.stream.JsonWriter;
import myexceptions.WrongArgumentException;
import trials.Trial;

import java.io.IOException;

import static constants.ExceptionsMessages.*;
import static constants.TrialsConstants.*;


public class TrialJSONSerializer {

    public void serialize(Trial trial, JsonWriter writer) {
        try {
            writer.beginObject();
            writeEntireTrial(trial, writer);
            writer.endObject();
        } catch (IOException e) {
            throw new WrongArgumentException(IO_EXCEPTION_WITH_WRITER, trial.toString(), e);
        }
    }

    private void writeEntireTrial(Trial trial, JsonWriter writer) throws IOException {
        writer.name(TRIAL_CLASS).value(trial.getClass().getSimpleName());
        writer.name(TRIAL_ARGS);
        writer.beginObject();
        writeArgs(trial, writer);
        writer.endObject();
    }

    protected void writeArgs(Trial trial, JsonWriter writer) throws IOException {
        writer.name(TRIAL_ACCOUNT).value(trial.getAccount());
        writer.name(TRIAL_MARK1).value(trial.getMark1());
        writer.name(TRIAL_MARK2).value(trial.getMark2());
    }
}
