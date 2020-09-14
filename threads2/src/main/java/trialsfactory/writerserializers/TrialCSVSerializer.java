package trialsfactory.writerserializers;

import trials.Trial;

public class TrialCSVSerializer {

    public String serialize(Trial trial) {
        return String.format("%s;%s;%s;%s", trial.getClass().getSimpleName()
                , trial.getAccount(), trial.getMark1(), trial.getMark2());
    }
}
