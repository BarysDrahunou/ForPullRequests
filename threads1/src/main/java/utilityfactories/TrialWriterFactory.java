package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import writers.*;

import java.util.HashMap;
import java.util.Map;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialWriterFactory {

    private static final Map<String, TrialConsumer> trialConsumersMap = new HashMap<>();

    static {
        trialConsumersMap.put(CSV, new CsvTrialWriter());
        trialConsumersMap.put(JSON, new JsonTrialWriter());
        trialConsumersMap.put(SQL, new SqlTrialWriter());
    }

    public static TrialConsumer getConsumer
            (String configurationFileName, String writerNameInProperties) {
        String writer = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                writerNameInProperties);
        String typeOfWriter = FilenameUtils.getExtension(writer).toUpperCase();
        TrialConsumer trialConsumer = trialConsumersMap.computeIfAbsent(typeOfWriter, key -> {
            throw new WrongArgumentException(INCORRECT_EXTENSION, writer);
        });
        trialConsumer.setWriter(writer, configurationFileName);
        return trialConsumer;
    }
}
