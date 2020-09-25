package writers;

import myexceptions.WrongArgumentException;
import trials.Trial;

import java.io.File;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public final class WriterUtilClass {

    private WriterUtilClass() {
    }

    static void isWriterAlreadyExists(String writer) {
        File tempFile = new File(OUTPUT_PATH + writer);
        boolean isWriterExists = tempFile.exists();
        if (isWriterExists) {
            throw new WrongArgumentException(WRITER_ALREADY_EXISTS, writer);
        }
    }

    static String getTrialKind(Trial trial) {
        return trial
                .getClass()
                .getSimpleName()
                .replaceAll(REGEXP_FOR_SPLIT, REPLACEMENT)
                .toUpperCase();
    }
}
