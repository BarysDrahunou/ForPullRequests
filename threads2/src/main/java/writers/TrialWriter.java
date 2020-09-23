package writers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.util.concurrent.BlockingQueue;

import static constants.TrialsConstants.*;

public class TrialWriter {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ZERO = 0;
    private final BlockingQueue<Trial> blockingQueue;
    private final TrialConsumer consumer;
    private int amountOfLeftReaders;

    public TrialWriter(BlockingQueue<Trial> blockingQueue, TrialConsumer consumer, int amountOfReaders) {
        this.blockingQueue = blockingQueue;
        this.consumer = consumer;
        this.amountOfLeftReaders = amountOfReaders;
    }

    public void go() {
        try {
            while (amountOfLeftReaders > ZERO) {
                Trial trial = blockingQueue.take();
                if (trial.equals(FINAL_TRIAL)) {
                    amountOfLeftReaders--;
                } else {
                    consumer.writeTrial(trial);
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        }
    }
}
