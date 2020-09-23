package trialsfactory.writerserializers;

import trials.*;

public class ExtraTrialCSVSerializer extends TrialCSVSerializer {

    @Override
    public String serialize(Trial trial) {
        ExtraTrial extraTrial = (ExtraTrial) trial;
        return String.format("%s;%s", super.serialize(extraTrial), extraTrial.getMark3());
    }
}
