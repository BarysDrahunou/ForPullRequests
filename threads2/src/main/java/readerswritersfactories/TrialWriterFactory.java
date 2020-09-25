package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import writers.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialWriterFactory implements AutoCloseable {

    private static final Map<String, TrialConsumer> TRIAL_CONSUMERS_MAP = new HashMap<>();
    private TrialConsumer trialConsumer;

    static {
        TRIAL_CONSUMERS_MAP.put(CSV, new CsvTrialWriter());
        TRIAL_CONSUMERS_MAP.put(JSON, new JsonTrialWriter());
        TRIAL_CONSUMERS_MAP.put(SQL, new SqlTrialWriter());
    }

    public TrialConsumer getConsumer(String configurationFileName, String writerNameInProperties) {
        String writer = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                writerNameInProperties);
        String writerType = FilenameUtils.getExtension(writer).toUpperCase();
        TrialConsumer trialConsumer = TRIAL_CONSUMERS_MAP.computeIfAbsent(writerType, key -> {
            throw new WrongArgumentException(INCORRECT_EXTENSION, writer);
        });
        trialConsumer.setWriter(writer, configurationFileName);
        this.trialConsumer = trialConsumer;
        return trialConsumer;
    }

    @Override
    public void close() throws IOException, SQLException {
        trialConsumer.close();
    }
}
