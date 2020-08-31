package buffer;

import myexceptions.WrongArgumentException;
import trials.Trial;

public class TrialBuffer {

    private Trial trial;
    private volatile boolean emptyBufferFlag = true;

    public synchronized void putTrial(Trial trial) {
        try {
            synchronized (this) {
                while (!emptyBufferFlag) {
                    wait();
                }
                emptyBufferFlag = false;
                this.trial = trial;
                notifyAll();
            }
        } catch (InterruptedException e) {
            throw new WrongArgumentException("Thread has been interrupted"
                    , Thread.currentThread().getName());
        }
    }

    public synchronized Trial takeTrial() {
        try {
            synchronized (this) {
                while (emptyBufferFlag) {
                    wait();
                }
                emptyBufferFlag = true;
                notifyAll();
                return trial;
            }
        } catch (InterruptedException e) {
            throw new WrongArgumentException("Thread has been interrupted"
                    , Thread.currentThread().getName());
        }
    }
}
