package writers;

import buffer.TrialBuffer;
import trials.Trial;

public class TrialWriter {

    private final TrialBuffer trialBuffer;
    private final TrialConsumer consumer;

    public TrialWriter(TrialBuffer trialBuffer, TrialConsumer consumer) {
        this.trialBuffer = trialBuffer;
        this.consumer = consumer;
    }

    public void go() {

        for (Trial trial = trialBuffer.takeTrial();
             !trial.equals(new Trial("Final trial", 0, 0));
             trial = trialBuffer.takeTrial()) {
            consumer.writeTrial(trial);
        }
    }
}
