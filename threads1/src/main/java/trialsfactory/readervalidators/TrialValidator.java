package trialsfactory.readervalidators;

import myexceptions.WrongArgumentException;
import trials.*;

import java.util.Objects;

public class TrialValidator {

    private final Trial trial;

    public TrialValidator(Trial trial) {
        this.trial = trial;
    }

    public Trial getValidTrial(String account
            , int mark1, int mark2, int mark3) {
        Trial trial = this.trial.getCopy();
        validateFieldsAndSet(account, mark1, mark2, trial);
        return trial;
    }

    protected void validateFieldsAndSet(String account, int mark1, int mark2, Trial trial) {
        trial.setAccount(validateStringField(account));
        trial.setMark1(validateIntegerField(mark1));
        trial.setMark2(validateIntegerField(mark2));
    }

    private String validateStringField(String account) {
        if (Objects.nonNull(account) && !account.isEmpty()) {
            return account;
        } else {
            throw new WrongArgumentException("The field is invalid ", account);
        }
    }

    protected int validateIntegerField(int fieldValue) {
        if (fieldValue >= 0 && fieldValue <= 100) {
            return fieldValue;
        } else {
            throw new WrongArgumentException("The field is invalid ", String.valueOf(fieldValue));
        }
    }

}