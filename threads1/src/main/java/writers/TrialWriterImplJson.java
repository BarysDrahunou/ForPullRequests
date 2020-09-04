package writers;

import com.google.gson.stream.JsonWriter;
import myexceptions.WrongArgumentException;
import trials.*;
import trialsfactory.writerserializers.*;

import java.io.*;

import static writers.TrialWriter.OUTPUT_PATH;

public class TrialWriterImplJson implements TrialConsumer {

    private final JsonWriter writer;


    public TrialWriterImplJson(String writer) throws IOException {
        TrialConsumer.isWriterAlreadyExists(writer, OUTPUT_PATH);
        this.writer = new JsonWriter(new FileWriter(OUTPUT_PATH + writer));
        this.writer.beginArray();
    }

    private enum JSONSerializerKind {

        TRIAL(new TrialJSONSerializer()),
        LIGHT_TRIAL(new TrialJSONSerializer()),
        STRONG_TRIAL(new TrialJSONSerializer()),
        EXTRA_TRIAL(new ExtraTrialJSONSerializer());

        private final TrialJSONSerializer trialJSONSerializer;

        JSONSerializerKind(TrialJSONSerializer trialJSONSerializer) {
            this.trialJSONSerializer = trialJSONSerializer;
        }

        void serialize(Trial trial, JsonWriter writer) throws IOException {
            trialJSONSerializer.serialize(trial, writer);
        }

    }

    @Override
    public void writeTrial(Trial trial) {
        String trialKind = TrialConsumer.getTrialKind(trial);
        try {
            JSONSerializerKind.valueOf(trialKind).serialize(trial, writer);
        } catch (IOException e) {
            throw new WrongArgumentException("There are some problems with serialization"
                    , trial.toString(), e);
        }
    }

    @Override
    public void close() throws Exception {
        this.writer.endArray();
        writer.close();
    }
}
