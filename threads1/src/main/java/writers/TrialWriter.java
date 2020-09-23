package writers;

import buffer.TrialBuffer;
import trials.*;

import static constants.TrialsConstants.*;

public class TrialWriter {

    private final TrialBuffer trialBuffer;
    private final TrialConsumer consumer;

    public TrialWriter(TrialBuffer trialBuffer, TrialConsumer consumer) {
        this.trialBuffer = trialBuffer;
        this.consumer = consumer;
    }

    public void go() {
        for (Trial trial = trialBuffer.takeTrial();
             !trial.equals(FINAL_TRIAL);
             trial = trialBuffer.takeTrial()) {
            consumer.writeTrial(trial);
        }
    }
}
