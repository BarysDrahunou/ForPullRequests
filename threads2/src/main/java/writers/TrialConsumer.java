package writers;

import myexceptions.WrongArgumentException;
import trials.Trial;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
        return trial
                .getClass()
                .getSimpleName()
                .replaceAll("([^_])([A-Z])", "$1_$2")
                .toUpperCase();
    }

    @Override
    void close() throws SQLException, IOException;
}
