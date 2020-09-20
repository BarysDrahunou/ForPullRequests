package writers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.util.concurrent.BlockingQueue;

public class TrialWriter {

    private static final Logger LOGGER = LogManager.getLogger();
    protected static final String OUTPUT_PATH = "src/main/outputfolder/";
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
            while (amountOfLeftReaders > 0) {
                Trial trial = blockingQueue.take();
                if (trial.equals(new Trial("Final trial", 0, 0))) {
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
