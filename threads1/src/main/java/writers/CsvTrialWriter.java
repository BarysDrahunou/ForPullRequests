package writers;

import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;
import trialsfactory.writerserializers.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;
import static constants.TrialsConstants.*;

public class CsvTrialWriter implements TrialConsumer {

    private static final Logger LOGGER = LogManager.getLogger();
    protected FileWriter output;
    private static final Map<String, TrialCSVSerializer> trialCSVSerializersMap = new HashMap<>();

    static {
        trialCSVSerializersMap.put(TRIAL, new TrialCSVSerializer());
        trialCSVSerializersMap.put(LIGHT_TRIAL, new TrialCSVSerializer());
        trialCSVSerializersMap.put(STRONG_TRIAL, new TrialCSVSerializer());
        trialCSVSerializersMap.put(EXTRA_TRIAL, new ExtraTrialCSVSerializer());
    }

    public CsvTrialWriter() {
    }

    @Override
    public void writeTrial(Trial trial) {
        try {
            output.write(serializeTrial(trial) + System.lineSeparator());
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private String serializeTrial(Trial trial) {
        String trialKind = WriterUtilClass.getTrialKind(trial);
        return trialCSVSerializersMap.get(trialKind).serialize(trial);
    }

    @Override
    public void setWriter(String writer, String configurationFileName) {
        WriterUtilClass.isWriterAlreadyExists(writer);
        try {
            this.output = new FileWriter(OUTPUT_PATH + writer, true);
        } catch (IOException e) {
            throw new WrongArgumentException(IO_EXCEPTION_WITH_CREATION_OF_WRITER, writer, e);
        }
    }

    @Override
    public void close() throws IOException {
        output.close();
    }
}
