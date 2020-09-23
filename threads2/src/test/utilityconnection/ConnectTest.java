package utilityconnection;

import myexceptions.WrongArgumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ConnectTest {

    String configFileName = "src/main/resources/testconfig.properties";
    Connection connection;

    @Before
    public void init() {
        connection = Connect.getConnection("root", "root");
    }

    @Test
    public void getConnectionTest() throws SQLException {
        assertFalse(connection.isClosed());
    }

    @Test(expected = WrongArgumentException.class)
    public void getConnectionTestSQLException() {
        connection = Connect.getConnection("trials", "trials");
    }

    @Test
    public void isDataBaseExistTest() throws SQLException {
        assertTrue(Connect.isDataBaseExist(connection, "trials1"));
        assertFalse(Connect.isDataBaseExist(connection, "training"));
    }

    @Test
    public void isTableExistTest() throws SQLException {
        assertTrue(Connect.isTableExist(connection, "trials1", "trials1"));
        assertFalse(Connect.isTableExist(connection, "trials", "training"));
    }

    @Test
    public void getConnectionParamsTest() {
        assertEquals(Arrays.toString(new String[]{"trial", "trial", "root", "root"}),
                Arrays.toString(Connect.getConnectionParams("trial.trial.sql",
                        configFileName)));
    }

    @Test(expected = WrongArgumentException.class)
    public void getConnectionParamsExtraParamsTest() {
        Connect.getConnectionParams("trial.trial.trial.sql",
                configFileName);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }
}

