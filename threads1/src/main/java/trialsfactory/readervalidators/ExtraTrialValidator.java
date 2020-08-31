package trialsfactory.readervalidators;

import trials.*;

public class ExtraTrialValidator extends TrialValidator {

    private ExtraTrial extraTrial;

    public ExtraTrialValidator(Trial trial) {
        super(trial);
    }

    private int mark3;

    @Override
    public ExtraTrial getValidTrial(String account
            , int mark1, int mark2, int mark3) {
        this.mark3 = mark3;
        this.extraTrial = new ExtraTrial();
        validateFieldsAndSet(account, mark1, mark2, extraTrial);
        return extraTrial;
    }

    @Override
    protected void validateFieldsAndSet(String account, int mark1, int mark2, Trial trial) {
        super.validateFieldsAndSet(account, mark1, mark2, trial);
        extraTrial.setMark3(validateIntegerField(mark3));
    }

}
