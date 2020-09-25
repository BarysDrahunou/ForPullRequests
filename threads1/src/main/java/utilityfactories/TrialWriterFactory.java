package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import writers.*;

import java.util.HashMap;
import java.util.Map;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialWriterFactory {

    private static final Map<String, TrialConsumer> TRIAL_CONSUMERS_MAP = new HashMap<>();

    static {
        TRIAL_CONSUMERS_MAP.put(CSV, new CsvTrialWriter());
        TRIAL_CONSUMERS_MAP.put(JSON, new JsonTrialWriter());
        TRIAL_CONSUMERS_MAP.put(SQL, new SqlTrialWriter());
    }

    public static TrialConsumer getConsumer
            (String configurationFileName, String writerNameInProperties) {
        String writer = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                writerNameInProperties);
        String writerType = FilenameUtils.getExtension(writer).toUpperCase();
        TrialConsumer trialConsumer = TRIAL_CONSUMERS_MAP.computeIfAbsent(writerType, key -> {
            throw new WrongArgumentException(INCORRECT_EXTENSION, writer);
        });
        trialConsumer.setWriter(writer, configurationFileName);
        return trialConsumer;
    }
}
