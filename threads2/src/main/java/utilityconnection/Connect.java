package utilityconnection;

import java.sql.*;

public class Connect {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306?serverTimezone=UTC";

    public static Connection getConnection(String login, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, login, password);
    }

    public static boolean isDataBaseExist(Connection connection, String dataBaseName) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getCatalogs()) {
            while (resultSet.next()) {
                String nameOfDataBases = resultSet.getString(1);
                if (nameOfDataBases.equals(dataBaseName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isTableExist(Connection connection, String dataBaseName, String tableName) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        try (ResultSet tables = dbm.getTables(dataBaseName, null, tableName, null)) {
            return tables.next();
        }
    }
}
