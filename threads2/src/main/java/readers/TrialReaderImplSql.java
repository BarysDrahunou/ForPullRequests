package readers;

import utilityconnection.Connect;
import trialsfactory.FactoryOfTrials;
import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.Trial;

import java.sql.*;
import java.util.Optional;

public class TrialReaderImplSql implements TrialDao {

    private static final String SQL_FOR_TRIALS =
            "SELECT CLASS, ACCOUNT, MARK1, MARK2, MARK3 FROM %s.%s LIMIT 1 OFFSET ?";
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;
    private final PreparedStatement preparedStatement;
    private int counter;

    public TrialReaderImplSql(String nameOfDataBase, String nameOfTable, String login, String password)
            throws ClassNotFoundException, SQLException {
        preparedStatement = getPreparedStatement(nameOfDataBase, nameOfTable, login, password);
    }

    private PreparedStatement getPreparedStatement(String nameOfDataBase, String nameOfTable
            , String login, String password) throws ClassNotFoundException, SQLException {
        connection = Connect.getConnection(login, password);
        if (!Connect.isDataBaseExist(connection, nameOfDataBase)) {
            throw new WrongArgumentException("Database with this name doesn't exist", nameOfDataBase);
        }
        if (!Connect.isTableExist(connection, nameOfDataBase, nameOfTable)) {
            throw new WrongArgumentException("This table doesn't exist", nameOfTable);
        }
        String sql = String.format(SQL_FOR_TRIALS, nameOfDataBase, nameOfTable);
        return connection.prepareStatement(sql);
    }

    @Override
    public Optional<Trial> nextTrial() {
        try {
            ResultSet resultSet = getResultSet();
            counter++;
            if (resultSet.next()) {
                String className = resultSet.getString("CLASS");
                String account = resultSet.getString("ACCOUNT");
                int mark1 = resultSet.getInt("MARK1");
                int mark2 = resultSet.getInt("MARK2");
                int mark3 = resultSet.getInt("MARK3");
                return FactoryOfTrials.getTrial(className, account, mark1, mark2, mark3);
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

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    private ResultSet getResultSet() throws SQLException {
        preparedStatement.setInt(1, counter);
        return preparedStatement.executeQuery();
    }

}
