import myexceptions.WrongArgumentException;
import trials.Trial;
import readerswritersfactories.*;
import readers.*;
import writers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

import static constants.ReaderWriterConstants.*;

public class Runner {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        String configFileName = args.length != 0 ? args[0] : DEFAULT_PATH_TO_PROPERTIES;
        try (TrialReaderFactory trialReaderFactory = new TrialReaderFactory();
             TrialWriterFactory trialWriterFactory = new TrialWriterFactory()) {
            BlockingQueue<Trial> drop = new ArrayBlockingQueue<>(QUEUE_SIZE, true);
            List<TrialDao> allTrialDao = trialReaderFactory.getTrialDAO(configFileName, READERS_NAME_IN_PROPERTIES);
            ExecutorService executorService = Executors.newFixedThreadPool(allTrialDao.size());
            for (TrialDao trialDao : allTrialDao) {
                TrialReader reader = new TrialReader(drop, trialDao);
                executorService.execute(reader);
            }
            TrialConsumer trialConsumer = trialWriterFactory.getConsumer(configFileName, WRITER_NAME_IN_PROPERTIES);
            TrialWriter writer = new TrialWriter(drop, trialConsumer, allTrialDao.size());
            writer.go();
            executorService.shutdown();
        } catch (IOException | SQLException | WrongArgumentException e) {
            LOGGER.error(e);
        }
    }
}

