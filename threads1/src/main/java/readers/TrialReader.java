package readers;

import buffer.TrialBuffer;
import trials.*;

import java.util.Optional;

import static constants.TrialsConstants.*;

public class TrialReader implements Runnable {

    private final TrialBuffer trialBuffer;
    private final TrialDao trialDao;

    public TrialReader(TrialBuffer trialBuffer, TrialDao trialDao) {
        this.trialBuffer = trialBuffer;
        this.trialDao = trialDao;
    }

    @Override
    public void run() {
        while (trialDao.hasTrial()) {
            Optional<Trial> trial = trialDao.nextTrial();
            trial.ifPresent(trialBuffer::putTrial);
        }
        trialBuffer.putTrial(FINAL_TRIAL);
    }
}
