package writers;

import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;
import trialsfactory.writerserializers.ExtraTrialCSVSerializer;
import trialsfactory.writerserializers.TrialCSVSerializer;

import java.io.FileWriter;
import java.io.IOException;

import static writers.TrialWriter.OUTPUT_PATH;

public class TrialWriterImplCSV implements TrialConsumer {

    private static final Logger LOGGER = LogManager.getLogger();
    protected FileWriter output;

    private enum CSVSerializerKind {

        TRIAL(new TrialCSVSerializer()),
        LIGHT_TRIAL(new TrialCSVSerializer()),
        STRONG_TRIAL(new TrialCSVSerializer()),
        EXTRA_TRIAL(new ExtraTrialCSVSerializer());

        private final TrialCSVSerializer trialCSVSerializer;

        CSVSerializerKind(TrialCSVSerializer trialCSVSerializer) {
            this.trialCSVSerializer = trialCSVSerializer;
        }

        String serialize(Trial trial) {
            return trialCSVSerializer.serialize(trial);
        }

    }

    public TrialWriterImplCSV(String writer) {
        TrialConsumer.isWriterAlreadyExists(writer, OUTPUT_PATH);
        try {
            this.output = new FileWriter(OUTPUT_PATH + writer, true);
        } catch (IOException e) {
            throw new WrongArgumentException("There is a problem with creation of a FileWriter", writer,e);
        }
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
        String trialKind = TrialConsumer.getTrialKind(trial);
        return CSVSerializerKind.valueOf(trialKind).serialize(trial);
    }

    @Override
    public void close() throws Exception {
        output.close();
    }
}
