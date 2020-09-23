import buffer.TrialBuffer;
import myexceptions.WrongArgumentException;
import utilityfactories.*;
import readers.*;
import writers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

import static constants.ReaderWriterConstants.*;

public class Runner {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        String configFileName = args.length != 0 ? args[0] : DEFAULT_PATH_TO_PROPERTIES;
        try (TrialDao trialDao = TrialReaderFactory.getTrialDAO
                (configFileName, READER_NAME_IN_PROPERTIES);
             TrialConsumer trialConsumer = TrialWriterFactory.getConsumer
                     (configFileName, WRITER_NAME_IN_PROPERTIES)) {
            TrialBuffer trialBuffer = new TrialBuffer();
            TrialReader reader = new TrialReader(trialBuffer, trialDao);
            TrialWriter writer = new TrialWriter(trialBuffer, trialConsumer);
            new Thread(reader).start();
            writer.go();
        } catch (IOException | SQLException | WrongArgumentException e) {
            LOGGER.error(e);
        }
    }
}

