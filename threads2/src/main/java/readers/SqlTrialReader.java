package readers;

import utilityconnection.Connect;
import trialsfactory.TrialsFactory;
import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.sql.*;
import java.util.Optional;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class SqlTrialReader implements TrialDao {

    private static final String SQL_FOR_TRIALS =
            "SELECT CLASS, ACCOUNT, MARK1, MARK2, MARK3 FROM %s.%s LIMIT 1 OFFSET ?";
    public static final String COLUMN_CLASS = "CLASS";
    public static final String COLUMN_ACCOUNT = "ACCOUNT";
    public static final String COLUMN_MARK1 = "MARK1";
    public static final String COLUMN_MARK2 = "MARK2";
    public static final String COLUMN_MARK3 = "MARK3";
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int counter;

    public SqlTrialReader() {
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            ResultSet resultSet = getResultSet();
            counter++;
            if (resultSet.next()) {
                String className = resultSet.getString(COLUMN_CLASS);
                String account = resultSet.getString(COLUMN_ACCOUNT);
                int mark1 = resultSet.getInt(COLUMN_MARK1);
                int mark2 = resultSet.getInt(COLUMN_MARK2);
                int mark3 = resultSet.getInt(COLUMN_MARK3);
                return TrialsFactory.getTrial(className, account, mark1, mark2, mark3);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean hasTrial() {
        try {
            return getResultSet().next();
        } catch (SQLException e) {
            LOGGER.error(e);
            return false;
        }
    }

    private ResultSet getResultSet() throws SQLException {
        preparedStatement.setInt(PREPARED_STATEMENT_PARAM, counter);
        return preparedStatement.executeQuery();
    }

    @Override
    public void setReader(String reader, String configurationFileName) {
        String[] connectionParams = Connect.getConnectionParams(reader, configurationFileName);
        preparedStatement = getPreparedStatement(connectionParams[DATABASE_NAME_PARAM],
                connectionParams[TABLE_NAME_PARAM], connectionParams[LOGIN_PARAM],
                connectionParams[PASSWORD_PARAM]);
    }

    private PreparedStatement getPreparedStatement(String nameOfDataBase, String nameOfTable,
                                                   String login, String password) {
        try {
            connection = Connect.getConnection(login, password);
            if (!Connect.isDataBaseExist(connection, nameOfDataBase)) {
                throw new WrongArgumentException(DATABASE_DOES_NOT_EXIST, nameOfDataBase);
            }
            if (!Connect.isTableExist(connection, nameOfDataBase, nameOfTable)) {
                throw new WrongArgumentException(TABLE_DOES_NOT_EXIST, nameOfTable);
            }
            String sql = String.format(SQL_FOR_TRIALS, nameOfDataBase, nameOfTable);
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new WrongArgumentException(SQL_EXCEPTION, nameOfDataBase, e);
        }
    }

    @Override
    public TrialDao getCopy() {
        return new SqlTrialReader();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
