import buffer.TrialBuffer;
import utilityfactories.*;
import readers.*;
import writers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;


public class Runner {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_PATH_TO_PROPERTIES = "src/main/resources/configuration.properties";

    public static void main(String[] args) {
        String configFileName = args.length != 0 ? args[0] : DEFAULT_PATH_TO_PROPERTIES;
        String readerNameInProperties = "reader";
        String writerNameInProperties = "writer";
        try (TrialDao trialDao = TrialReaderFactory.getTrialDAO(configFileName, readerNameInProperties);
             TrialConsumer trialConsumer = TrialWriterFactory.getConsumer(configFileName, writerNameInProperties)) {
            TrialBuffer trialBuffer = new TrialBuffer();
            TrialReader reader = new TrialReader(trialBuffer, trialDao);
            TrialWriter writer = new TrialWriter(trialBuffer, trialConsumer);
            new Thread(reader).start();
            writer.go();
        } catch (IOException | SQLException e) {
            LOGGER.error(e);
        }
    }
}

