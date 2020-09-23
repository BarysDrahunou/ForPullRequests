package trialsfactory.writerserializers;

import trials.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrialSQLSerializer {

    private static final int MARK3_IN_TRIAL = 0;
    private static final int CLASSNAME_PARAMETER_INDEX = 1;
    private static final int ACCOUNT_PARAMETER_INDEX = 2;
    private static final int MARK1_PARAMETER_INDEX = 3;
    private static final int MARK2_PARAMETER_INDEX = 4;
    private static final int MARK3_PARAMETER_INDEX = 5;

    public PreparedStatement setPreparedStatementAndGet
            (Trial trial, PreparedStatement preparedStatement) throws SQLException {
        setPreparedStatementValues(preparedStatement, trial.getClass().getSimpleName(),
                trial.getAccount(), trial.getMark1(), trial.getMark2(), MARK3_IN_TRIAL);
        return preparedStatement;
    }

    protected void setPreparedStatementValues
            (PreparedStatement preparedStatement, String nameOfClass, String account, int mark1,
             int mark2, int mark3) throws SQLException {
        preparedStatement.setString(CLASSNAME_PARAMETER_INDEX, nameOfClass);
        preparedStatement.setString(ACCOUNT_PARAMETER_INDEX, account);
        preparedStatement.setInt(MARK1_PARAMETER_INDEX, mark1);
        preparedStatement.setInt(MARK2_PARAMETER_INDEX, mark2);
        preparedStatement.setInt(MARK3_PARAMETER_INDEX, mark3);
    }
}
