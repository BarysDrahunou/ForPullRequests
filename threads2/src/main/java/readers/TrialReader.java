package readers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public class TrialReader implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private final BlockingQueue<Trial> blockingQueue;
    private final TrialDao trialDao;

    public TrialReader(BlockingQueue<Trial> blockingQueue, TrialDao trialDao) {
        this.blockingQueue = blockingQueue;
        this.trialDao = trialDao;
    }

    @Override
    public void run() {
        try {
            while (trialDao.hasTrial()) {
                Optional<Trial> trial = trialDao.nextTrial();
                if (trial.isPresent()) {
                    blockingQueue.put(trial.get());
                }
            }
            blockingQueue.put(new Trial("Final trial", 0, 0));
        } catch (InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        }
    }
}
