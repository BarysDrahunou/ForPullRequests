package readers;

import utilityfactories.TrialReaderFactory;
import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class TrialReaderImplSqlTest {
    TrialDao trialReaderImplSQL;
    @Mock
    Connection connection;

    @Before
    public void init() throws SQLException, IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        trialReaderImplSQL = TrialReaderFactory.getTrialDAO("testconfig.properties"
                , "sqlreader");
        MockitoAnnotations.initMocks(this);
        Field field = trialReaderImplSQL.getClass().getDeclaredField("connection");
        field.setAccessible(true);
        field.set(trialReaderImplSQL, connection);
    }

    @Test(expected = WrongArgumentException.class)
    public void dataBaseDoesNotExist() throws SQLException, IOException, ClassNotFoundException {
        TrialReaderFactory.getTrialDAO("testconfig.properties"
                , "databasedoesnotexist");
    }

    @Test(expected = WrongArgumentException.class)
    public void tableDoesNotExist() throws SQLException, IOException, ClassNotFoundException {
        TrialReaderFactory.getTrialDAO("testconfig.properties"
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
                , new Trial("Kent", 11, 22));
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