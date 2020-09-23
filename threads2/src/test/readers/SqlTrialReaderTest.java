package readers;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import readerswritersfactories.TrialReaderFactory;
import trials.*;

import java.lang.reflect.Field;
import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@SuppressWarnings("all")
public class SqlTrialReaderTest {

    String configFileName = "src/main/resources/testconfig.properties";
    TrialDao trialReaderImplSQL;
    @Mock
    Connection connection;

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        trialReaderImplSQL = new TrialReaderFactory().getTrialDAO(configFileName
                , "sqlreader").get(0);
        MockitoAnnotations.initMocks(this);
        Field field = trialReaderImplSQL.getClass().getDeclaredField("connection");
        field.setAccessible(true);
        field.set(trialReaderImplSQL, connection);
    }

    @Test(expected = WrongArgumentException.class)
    public void dataBaseDoesNotExist() {
        new TrialReaderFactory().getTrialDAO(configFileName
                , "databasedoesnotexist");
    }

    @Test(expected = WrongArgumentException.class)
    public void tableDoesNotExist() {
        new TrialReaderFactory().getTrialDAO(configFileName
                , "tabledoesnotexist");
    }

    @Test
    public void hasTrialTest() {
        assertTrue(trialReaderImplSQL.hasTrial());
    }

    @Test
    public void nextTrialAndFalseHasTrialTest() throws Exception {
        trialReaderImplSQL.hasTrial();
        assertEquals(trialReaderImplSQL.nextTrial().orElse(null)
                , new Trial("Ment", 22, 22));
        trialReaderImplSQL.hasTrial();
        assertEquals(trialReaderImplSQL.nextTrial().orElse(null)
                , new ExtraTrial("Kot", 11, 2, 3));
        trialReaderImplSQL.hasTrial();
        assertEquals(trialReaderImplSQL.nextTrial().orElse(null)
                , new LightTrial("Paul", 22, 22));
        assertTrue(trialReaderImplSQL.nextTrial().isEmpty());
        assertFalse(trialReaderImplSQL.hasTrial());
        trialReaderImplSQL.close();
    }

    @Test
    public void closeTest() throws Exception {
        trialReaderImplSQL.close();
        verify(connection).close();
    }
}