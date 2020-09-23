package trialsfactory.readervalidators;

import myexceptions.WrongArgumentException;
import trials.*;

import static constants.ExceptionsMessages.*;
import static constants.TrialsConstants.*;

public class TrialValidator {

    private final Trial trial;

    public TrialValidator(Trial trial) {
        this.trial = trial;
    }

    public Trial getValidTrial(String account, int mark1,
                               int mark2, int mark3) {
        Trial trial = this.trial.getCopy();
        validateFieldsAndSet(account, mark1, mark2, trial);
        return trial;
    }

    protected void validateFieldsAndSet(String account, int mark1,
                                        int mark2, Trial trial) {
        trial.setAccount(validateStringField(account));
        trial.setMark1(validateIntegerField(mark1));
        trial.setMark2(validateIntegerField(mark2));
    }

    private String validateStringField(String account) {
        if (account != null && !account.isEmpty()) {
            return account;
        } else {
            throw new WrongArgumentException(INVALID_FIELD, account);
        }
    }

    protected int validateIntegerField(int fieldValue) {
        if (fieldValue >= MIN_ACCEPTABLE_MARK && fieldValue <= MAX_ACCEPTABLE_MARK) {
            return fieldValue;
        } else {
            throw new WrongArgumentException(INVALID_FIELD, String.valueOf(fieldValue));
        }
    }
}