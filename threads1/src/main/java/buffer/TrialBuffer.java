package buffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

public class TrialBuffer {

    private static final Logger LOGGER = LogManager.getLogger();
    private Trial trial;
    private volatile boolean isBufferEmpty = true;

    public synchronized void putTrial(Trial trial) {
        try {
            while (!isBufferEmpty) {
                wait();
            }
            isBufferEmpty = false;
            this.trial = trial;
            notifyAll();
        } catch (InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Trial takeTrial() {
        while (isBufferEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                LOGGER.error(e);
                Thread.currentThread().interrupt();
            }
        }
        isBufferEmpty = true;
        notifyAll();
        return trial;
    }
}