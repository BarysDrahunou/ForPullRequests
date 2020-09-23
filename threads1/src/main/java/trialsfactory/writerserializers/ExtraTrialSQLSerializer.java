package trialsfactory.writerserializers;

import trials.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExtraTrialSQLSerializer extends TrialSQLSerializer {

    @Override
    public PreparedStatement setPreparedStatementAndGet
            (Trial trial, PreparedStatement preparedStatement) throws SQLException {
        super.setPreparedStatementValues(preparedStatement, trial.getClass().getSimpleName(),
                trial.getAccount(), trial.getMark1(), trial.getMark2(),
                ((ExtraTrial) trial).getMark3());
        return preparedStatement;
    }
}
