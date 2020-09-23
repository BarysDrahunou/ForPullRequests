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

    private static final Map<String, TrialConsumer> trialConsumersMap = new HashMap<>();
    private TrialConsumer trialConsumer;

    static {
        trialConsumersMap.put(CSV, new CsvTrialWriter());
        trialConsumersMap.put(JSON, new JsonTrialWriter());
        trialConsumersMap.put(SQL, new SqlTrialWriter());
    }

    public TrialConsumer getConsumer(String configurationFileName, String writerNameInProperties) {
        String writer = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                writerNameInProperties);
        String typeOfWriter = FilenameUtils.getExtension(writer).toUpperCase();
        TrialConsumer trialConsumer = trialConsumersMap.computeIfAbsent(typeOfWriter, key -> {
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
