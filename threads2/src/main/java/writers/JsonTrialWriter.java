package writers;

import com.google.gson.stream.JsonWriter;
import myexceptions.WrongArgumentException;
import trials.*;
import trialsfactory.writerserializers.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;
import static constants.TrialsConstants.*;
import static constants.TrialsConstants.EXTRA_TRIAL;

public class JsonTrialWriter implements TrialConsumer {

    private static final Map<String, TrialJSONSerializer> TRIAL_JSON_SERIALIZERS_MAP = new HashMap<>();
    private JsonWriter writer;

    static {
        TRIAL_JSON_SERIALIZERS_MAP.put(TRIAL, new TrialJSONSerializer());
        TRIAL_JSON_SERIALIZERS_MAP.put(LIGHT_TRIAL, new TrialJSONSerializer());
        TRIAL_JSON_SERIALIZERS_MAP.put(STRONG_TRIAL, new TrialJSONSerializer());
        TRIAL_JSON_SERIALIZERS_MAP.put(EXTRA_TRIAL, new ExtraTrialJSONSerializer());
    }

    public JsonTrialWriter() {
    }

    @Override
    public void writeTrial(Trial trial) {
        String trialKind = WriterUtilClass.getTrialKind(trial);
        TRIAL_JSON_SERIALIZERS_MAP.get(trialKind).serialize(trial, writer);
    }

    @Override
    public void setWriter(String writer, String configurationFileName) {
        WriterUtilClass.isWriterAlreadyExists(writer);
        try {
            this.writer = new JsonWriter(new FileWriter(OUTPUT_PATH + writer));
            this.writer.beginArray();
        } catch (IOException e) {
            throw new WrongArgumentException(IO_EXCEPTION_WITH_CREATION_OF_WRITER, writer, e);
        }
    }

    @Override
    public void close() throws IOException {
        this.writer.endArray();
        writer.close();
    }
}
