package utilityconnection;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import utilityfactories.PropertiesUtilClass;

import java.sql.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public final class Connect {

    public Connect() {
    }

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
    private static final int DATABASE_NAME_COLUMN = 1;

    public static Connection getConnection(String login, String password) {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, login, password);
        } catch (ClassNotFoundException e) {
            throw new WrongArgumentException(NOT_FOUND_CLASS_FOR_DRIVER, DRIVER, e);
        } catch (SQLException e) {
            throw new WrongArgumentException(NO_CONNECTION, URL, e);
        }
    }

    public static boolean isDataBaseExist
            (Connection connection, String dataBaseName) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getCatalogs()) {
            while (resultSet.next()) {
                if (resultSet.getString(DATABASE_NAME_COLUMN).equals(dataBaseName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isTableExist
            (Connection connection, String dataBaseName, String tableName) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        try (ResultSet tables = dbm.getTables(dataBaseName, null,
                tableName, null)) {
            return tables.next();
        }
    }

    public static String[] getConnectionParams(String dataSource, String configurationFileName) {
        String[] dataBaseAndTableNames = FilenameUtils
                .removeExtension(dataSource)
                .split(REGEXP_EXTENSION);
        if (dataBaseAndTableNames.length != DATABASE_PARAMS) {
            throw new WrongArgumentException(INCORRECT_WRITER_NAME, dataSource);
        }
        String dataBasename = dataBaseAndTableNames[DATABASE_NAME_PARAM];
        String tableName = dataBaseAndTableNames[TABLE_NAME_PARAM];
        String login = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE);
        String password = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE);
        return new String[]{dataBasename, tableName, login, password};
    }
}
