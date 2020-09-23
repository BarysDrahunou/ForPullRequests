package writers;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SqlTrialWriterTest {

    static String configFileName = "src/main/resources/testconfig.properties";
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    static SqlTrialWriter sqlTrialWriter;

    @BeforeClass
    public static void init()  {
        sqlTrialWriter = new SqlTrialWriter();
        sqlTrialWriter.setWriter("trials3.trials3.sql",configFileName);
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() throws NoSuchFieldException {
        Field field = SqlTrialWriter.class.getDeclaredField("preparedStatement");
        assertNotNull(field);
    }

    @Test(expected = WrongArgumentException.class)
    public void constructorTestFails(){
        new SqlTrialWriter();
        sqlTrialWriter.setWriter("trials1.trials1.sql",configFileName);
    }

    @Test
    public void writeTrial() throws NoSuchFieldException, IllegalAccessException, SQLException {
        Field field = SqlTrialWriter.class.getDeclaredField("preparedStatement");
        field.setAccessible(true);
        field.set(sqlTrialWriter, preparedStatement);
        sqlTrialWriter.writeTrial(new Trial("Ment", 1, 1));
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void close() throws Exception {
        Field field = SqlTrialWriter.class.getDeclaredField("connection");
        field.setAccessible(true);
        field.set(sqlTrialWriter, connection);
        sqlTrialWriter.close();
        verify(connection).close();
    }
}