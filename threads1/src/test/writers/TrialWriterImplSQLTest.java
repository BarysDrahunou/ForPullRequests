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

public class TrialWriterImplSQLTest {

    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    static TrialWriterImplSQL trialWriterImplSQL;

    @BeforeClass
    public static void init(){
        trialWriterImplSQL = new TrialWriterImplSQL("trials", "trials", "root", "root");
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() throws NoSuchFieldException {
        Field field = TrialWriterImplSQL.class.getDeclaredField("preparedStatement");
        assertNotNull(field);
    }

    @Test(expected = WrongArgumentException.class)
    public void constructorTestFails(){
        new TrialWriterImplSQL("trials1", "trials1", "root", "root");
    }

    @Test
    public void writeTrial() throws NoSuchFieldException, IllegalAccessException, SQLException {
        Field field = TrialWriterImplSQL.class.getDeclaredField("preparedStatement");
        field.setAccessible(true);
        field.set(trialWriterImplSQL, preparedStatement);
        trialWriterImplSQL.writeTrial(new Trial("Ment", 1, 1));
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void close() throws Exception {
        Field field = TrialWriterImplSQL.class.getDeclaredField("connection");
        field.setAccessible(true);
        field.set(trialWriterImplSQL, connection);
        trialWriterImplSQL.close();
        verify(connection).close();
    }
}