package writers;

import trialsfactory.writerserializers.*;
import utilityconnection.Connect;
import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.*;

import java.sql.*;
import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;
import static constants.TrialsConstants.*;

public class SqlTrialWriter implements TrialConsumer {

    private static final String SQL_FOR_DATABASE_CREATION = "CREATE DATABASE %s";
    private static final String SQL_FOR_TABLE_CREATION = "CREATE TABLE %s.%s ("
            + "ID int(15)  NOT NULL AUTO_INCREMENT ,"
            + "CLASS VARCHAR(45) NOT NULL,"
            + "ACCOUNT VARCHAR(45) NOT NULL,"
            + "MARK1 int(45) NOT NULL,"
            + "MARK2 int(45) NOT NULL,"
            + "MARK3 int(45) NOT NULL,"
            + "PRIMARY KEY (ID))";
    private static final String SQL_FOR_INSERTION = "INSERT INTO %s.%s " +
            "(CLASS, ACCOUNT, MARK1, MARK2, MARK3) VALUES (?,?,?,?,?)";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, TrialSQLSerializer> TRIAL_SQL_SERIALIZERS_MAP = new HashMap<>();
    private Connection connection;
    private PreparedStatement preparedStatement;

    static {
        TRIAL_SQL_SERIALIZERS_MAP.put(TRIAL, new TrialSQLSerializer());
        TRIAL_SQL_SERIALIZERS_MAP.put(LIGHT_TRIAL, new TrialSQLSerializer());
        TRIAL_SQL_SERIALIZERS_MAP.put(STRONG_TRIAL, new TrialSQLSerializer());
        TRIAL_SQL_SERIALIZERS_MAP.put(EXTRA_TRIAL, new ExtraTrialSQLSerializer());
    }

    public SqlTrialWriter() {
    }

    @Override
    public void writeTrial(Trial trial) {
        String trialKind = WriterUtilClass.getTrialKind(trial);
        try {
            preparedStatement = TRIAL_SQL_SERIALIZERS_MAP.get(trialKind)
                    .setPreparedStatementAndGet(trial, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void setWriter(String writer, String configurationFileName) {
        String[] connectionParams = Connect.getConnectionParams(writer, configurationFileName);
        preparedStatement = getPreparedStatement(connectionParams[DATABASE_NAME_PARAM],
                connectionParams[TABLE_NAME_PARAM], connectionParams[LOGIN_PARAM],
                connectionParams[PASSWORD_PARAM]);
    }

    private PreparedStatement getPreparedStatement(String nameOfDataBase, String nameOfTable,
                                                   String login, String password) {
        try {
            connection = Connect.getConnection(login, password);
            boolean isDataBaseExist = Connect.isDataBaseExist(connection, nameOfDataBase);
            boolean isTableExist = Connect.isTableExist(connection, nameOfDataBase, nameOfTable);
            if (!isTableExist) {
                if (!isDataBaseExist) {
                    String sqlForDataBase = String.format(SQL_FOR_DATABASE_CREATION,
                            nameOfDataBase);
                    createDataBaseStructure(connection, sqlForDataBase);
                }
                String sqlForTable = String.format(SQL_FOR_TABLE_CREATION,
                        nameOfDataBase, nameOfTable);
                createDataBaseStructure(connection, sqlForTable);
                String sql = String.format(SQL_FOR_INSERTION,
                        nameOfDataBase, nameOfTable);
                return connection.prepareStatement(sql);
            } else {
                throw new WrongArgumentException(TABLE_ALREADY_EXISTS,
                        String.format("%s.%s", nameOfDataBase, nameOfTable));
            }
        } catch (SQLException e) {
            throw new WrongArgumentException(SQL_EXCEPTION, nameOfDataBase, e);
        }
    }

    private void createDataBaseStructure(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
