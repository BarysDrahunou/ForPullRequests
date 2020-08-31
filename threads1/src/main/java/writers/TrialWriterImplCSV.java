package writers;

import trials.*;
import trialsfactory.writerserializers.*;

public class TrialWriterImplCSV extends FileWriterImpl {

    public TrialWriterImplCSV(String writer) {
        super(writer);
    }

    private enum CSVSerializerKind {

        TRIAL(new TrialCSVSerializer()),
        LIGHT_TRIAL(new TrialCSVSerializer()),
        STRONG_TRIAL(new TrialCSVSerializer()),
        EXTRA_TRIAL(new ExtraTrialCSVSerializer());

        private final TrialCSVSerializer trialCSVSerializer;

        CSVSerializerKind(TrialCSVSerializer trialCSVSerializer) {
            this.trialCSVSerializer = trialCSVSerializer;
        }

        String serialize(Trial trial) {
            return trialCSVSerializer.serialize(trial);
        }

    }

    @Override
    protected String serializeTrial(Trial trial) {
        String trialKind = getTrialKind(trial);
        return CSVSerializerKind.valueOf(trialKind).serialize(trial);
    }
}
