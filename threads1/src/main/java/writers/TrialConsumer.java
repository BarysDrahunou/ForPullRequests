package writers;

import myexceptions.WrongArgumentException;
import trials.Trial;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface TrialConsumer extends AutoCloseable {

    void writeTrial(Trial trial);

    static void isWriterAlreadyExists(String writer, String path) {
        File tempFile = new File(path + writer);
        boolean exists = tempFile.exists();
        if (exists) {
            throw new WrongArgumentException("Writer already exists", writer);
        }
    }
    static String getTrialKind(Trial trial) {
        return Arrays.stream(trial
                .getClass()
                .getSimpleName()
                .split("(?<=[a-z])(?=[A-Z])"))
                .map(String::toUpperCase)
                .collect(Collectors.joining("_"));
    }
}
