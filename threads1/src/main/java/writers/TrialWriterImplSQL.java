package writers;

import trialsfactory.writerserializers.*;
import utilityconnection.Connect;
import myexceptions.WrongArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trials.*;

import java.sql.*;

public class TrialWriterImplSQL implements TrialConsumer {

    private static final String SQL_FOR_DATABASE_CREATION="CREATE DATABASE %s";
    private static final String SQL_FOR_TABLE_CREATION="CREATE TABLE %s.%s ("
            + "ID int(15)  NOT NULL AUTO_INCREMENT ,"
            + "CLASS VARCHAR(45) NOT NULL,"
            + "ACCOUNT VARCHAR(45) NOT NULL,"
            + "MARK1 int(45) NOT NULL,"
            + "MARK2 int(45) NOT NULL,"
            + "MARK3 int(45) NOT NULL,"
            + "PRIMARY KEY (ID))";
    private static final String SQL_FOR_INSERTION="INSERT INTO %s.%s " +
            "(CLASS, ACCOUNT, MARK1, MARK2, MARK3) VALUES (?,?,?,?,?)";

    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;
    private PreparedStatement preparedStatement;

    private enum SQLSerializerKind {

        TRIAL(new TrialSQLSerializer()),
        LIGHT_TRIAL(new TrialSQLSerializer()),
        STRONG_TRIAL(new TrialSQLSerializer()),
        EXTRA_TRIAL(new ExtraTrialSQLSerializer());

        private final TrialSQLSerializer trialSQLSerializer;

        SQLSerializerKind(TrialSQLSerializer trialSQLSerializer) {
            this.trialSQLSerializer = trialSQLSerializer;
        }

        PreparedStatement setPreparedStatementAndGet(Trial trial, PreparedStatement preparedStatement) throws SQLException {
            return trialSQLSerializer.setPreparedStatementAndGet(trial, preparedStatement);
        }

    }

    public TrialWriterImplSQL(String nameOfDataBase, String nameOfTable
            , String login, String password) throws ClassNotFoundException, SQLException {
        preparedStatement = getPreparedStatement(nameOfDataBase, nameOfTable, login, password);
    }

    private PreparedStatement getPreparedStatement(String nameOfDataBase, String nameOfTable
            , String login, String password) throws ClassNotFoundException, SQLException {
        connection = Connect.getConnection(login, password);
        boolean isDataBaseExist = Connect.isDataBaseExist(connection, nameOfDataBase);
        boolean isTableExist = Connect.isTableExist(connection, nameOfDataBase, nameOfTable);
        if (!isTableExist) {
            if (!isDataBaseExist) {
                String sqlForDataBase = String.format(SQL_FOR_DATABASE_CREATION, nameOfDataBase);
                createDataBaseStructure(connection, sqlForDataBase);
            }
            String sqlForTable = String.format(SQL_FOR_TABLE_CREATION, nameOfDataBase, nameOfTable);
            createDataBaseStructure(connection, sqlForTable);
            String sql = String.format(SQL_FOR_INSERTION
                    , nameOfDataBase, nameOfTable);
            return connection.prepareStatement(sql);
        } else {
            throw new WrongArgumentException("This table already exist"
                    , String.format("%s.%s", nameOfDataBase, nameOfTable));
        }
    }

    private void createDataBaseStructure(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    @Override
    public void writeTrial(Trial trial) {
        String trialKind = TrialConsumer.getTrialKind(trial);
        try {
            preparedStatement = SQLSerializerKind.valueOf(trialKind).setPreparedStatementAndGet(trial, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
