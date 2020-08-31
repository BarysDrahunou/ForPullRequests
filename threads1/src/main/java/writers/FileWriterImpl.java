package writers;

import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class FileWriterImpl implements TrialConsumer {

    private static final Logger LOGGER = LogManager.getLogger();
    protected static final String OUTPUT_PATH = "src/main/outputfolder/";
    protected FileWriter output;

    public FileWriterImpl(String writer) {
        isWriterAlreadyExists(writer);
        try {
            this.output = new FileWriter(OUTPUT_PATH + writer, true);
        } catch (IOException e) {
            throw new WrongArgumentException("There is a problem with creation of FileWriter", writer);
        }
    }

    private void isWriterAlreadyExists(String writer) {
        File tempFile = new File(OUTPUT_PATH + writer);
        boolean exists = tempFile.exists();
        if (exists) {
            throw new WrongArgumentException("Writer already exists", writer);
        }

    }

    @Override
    public void writeTrial(Trial trial) {
        try {
            output.write(serializeTrial(trial));
            output.write(System.lineSeparator());
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    protected abstract String serializeTrial(Trial trial);

    protected String getTrialKind(Trial trial) {
        return Arrays.stream(trial
                .getClass()
                .getSimpleName()
                .split("(?<=[a-z])(?=[A-Z])"))
                .map(String::toUpperCase)
                .collect(Collectors.joining("_"));
    }

    @Override
    public void close() throws Exception {
        output.close();
    }
}
