import trials.Trial;
import readerswritersfactories.*;
import readers.*;
import writers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


public class Runner {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_PATH_TO_PROPERTIES = "src/main/resources/configuration.properties";

    public static void main(String[] args) {
        String configFileName = args.length != 0 ? args[0] : DEFAULT_PATH_TO_PROPERTIES;
        String readersNameInProperties = "readers";
        String writerNameInProperties = "writer";
        int numberOfElementsOfQueue = new Random().nextInt(100) + 1;
        try (TrialReaderFactory trialReaderFactory = new TrialReaderFactory();
             TrialConsumer trialConsumer = TrialWriterFactory.getConsumer(configFileName, writerNameInProperties)) {
            BlockingQueue<Trial> drop = new ArrayBlockingQueue<>(numberOfElementsOfQueue, true);
            List<TrialDao> allTrialDao = trialReaderFactory.getTrialDAO(configFileName, readersNameInProperties);
            ExecutorService executorService = Executors.newFixedThreadPool(allTrialDao.size());
            for (TrialDao trialDao : allTrialDao) {
                TrialReader reader = new TrialReader(drop, trialDao);
                executorService.execute(reader);
            }
            TrialWriter writer = new TrialWriter(drop, trialConsumer, allTrialDao.size());
            writer.go();
            executorService.shutdown();
        } catch (IOException | SQLException | IllegalArgumentException e) {
            LOGGER.error(e);
        }
    }
}

