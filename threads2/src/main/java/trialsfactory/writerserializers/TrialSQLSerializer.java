package trialsfactory.writerserializers;

import trials.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrialSQLSerializer {
    public PreparedStatement setPreparedStatementAndGet(Trial trial, PreparedStatement preparedStatement) throws SQLException {
     setPreparedStatementValues(preparedStatement,trial.getClass().getSimpleName()
             ,trial.getAccount(),trial.getMark1(),trial.getMark2(),0);
     return preparedStatement;
    }
    protected void setPreparedStatementValues(PreparedStatement preparedStatement
    , String nameOfClass, String account, int mark1, int mark2, int mark3) throws SQLException {
        preparedStatement.setString(1, nameOfClass);
        preparedStatement.setString(2, account);
        preparedStatement.setInt(3, mark1);
        preparedStatement.setInt(4, mark2);
        preparedStatement.setInt(5, mark3);
    }
}
