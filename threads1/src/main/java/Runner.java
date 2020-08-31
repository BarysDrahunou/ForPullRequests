import buffer.TrialBuffer;
import utilityfactories.*;
import readers.*;
import writers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Runner {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        String configFileName = args[0];
        String readerNameInProperties = "reader";
        String writerNameInProperties = "writer";
        try (TrialDao trialDao = TrialReaderFactory.getTrialDAO(configFileName, readerNameInProperties);
             TrialConsumer trialConsumer = TrialWriterFactory.getConsumer(args[0], writerNameInProperties)) {
            TrialBuffer trialBuffer = new TrialBuffer();
            TrialReader reader = new TrialReader(trialBuffer, trialDao);
            TrialWriter writer = new TrialWriter(trialBuffer, trialConsumer);
            new Thread(reader).start();
            writer.go();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}

