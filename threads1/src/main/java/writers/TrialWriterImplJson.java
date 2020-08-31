package writers;

import trials.*;
import trialsfactory.writerserializers.*;

public class TrialWriterImplJson extends FileWriterImpl {

    public TrialWriterImplJson(String writer) {
        super(writer);
    }

    private enum JSONSerializerKind {

        TRIAL(new TrialJSONSerializer()),
        LIGHT_TRIAL(new TrialJSONSerializer()),
        STRONG_TRIAL(new TrialJSONSerializer()),
        EXTRA_TRIAL(new ExtraTrialJSONSerializer());

        private final TrialJSONSerializer trialJSONSerializer;

        JSONSerializerKind(TrialJSONSerializer trialJSONSerializer) {
            this.trialJSONSerializer=trialJSONSerializer;
        }

        String serialize(Trial trial) {
            return trialJSONSerializer.serialize(trial);
        }

    }
    @Override
    protected String serializeTrial(Trial trial) {
        String trialKind = getTrialKind(trial);
        return JSONSerializerKind.valueOf(trialKind).serialize(trial);
    }
}
