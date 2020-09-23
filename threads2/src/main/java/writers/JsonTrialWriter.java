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

    private JsonWriter writer;
    private static final Map<String, TrialJSONSerializer> trialJSONSerializersMap = new HashMap<>();

    static {
        trialJSONSerializersMap.put(TRIAL, new TrialJSONSerializer());
        trialJSONSerializersMap.put(LIGHT_TRIAL, new TrialJSONSerializer());
        trialJSONSerializersMap.put(STRONG_TRIAL, new TrialJSONSerializer());
        trialJSONSerializersMap.put(EXTRA_TRIAL, new ExtraTrialJSONSerializer());
    }

    public JsonTrialWriter() {
    }

    @Override
    public void writeTrial(Trial trial) {
        String trialKind = WriterUtilClass.getTrialKind(trial);
        trialJSONSerializersMap.get(trialKind).serialize(trial, writer);
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
