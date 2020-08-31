package readers;

import buffer.TrialBuffer;
import trials.Trial;

import java.util.Optional;

public class TrialReader implements Runnable {

    private final TrialBuffer trialBuffer;
    private final TrialDao trialDao;

    public TrialReader(TrialBuffer trialBuffer, TrialDao trialDao) {
        this.trialBuffer = trialBuffer;
        this.trialDao = trialDao;
    }

    @Override
    public void run() {
        boolean nextFlag = trialDao.hasTrial();
        while (nextFlag) {
            Optional<Trial> trial = trialDao.nextTrial();
            nextFlag = trialDao.hasTrial();
            trial.ifPresent(trialBuffer::putTrial);
        }
        trialBuffer.putTrial(new Trial("Final trial",0,0));
    }

}
