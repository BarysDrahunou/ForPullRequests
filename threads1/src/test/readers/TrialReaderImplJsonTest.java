package readers;

import utilityfactories.TrialReaderFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class TrialReaderImplJsonTest {
    TrialDao trialReaderImplJson;
    @Mock
    FileReader fileReader;
    @Before
    public void init() throws SQLException, IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        trialReaderImplJson = TrialReaderFactory.getTrialDAO("testconfig.properties"
                , "jsonreader");
        MockitoAnnotations.initMocks(this);
        Field field = trialReaderImplJson.getClass().getDeclaredField("fileReader");
        field.setAccessible(true);
        field.set(trialReaderImplJson, fileReader);
    }
    @Test
    public void hasTrial() {
        assertTrue(trialReaderImplJson.hasTrial());
    }
    @Test
    public void nextTrialTestAndFalseHasTrial() throws Exception {
        assertEquals(trialReaderImplJson.nextTrial().orElse(null)
                , new Trial("Vitali", 10, 22));
        assertEquals(trialReaderImplJson.nextTrial().orElse(null)
                , new ExtraTrial("Dimon", 10, 11, 44));
        assertEquals(trialReaderImplJson.nextTrial().orElse(null)
                , new ExtraTrial("Egor", 50, 60,20));
        assertTrue(trialReaderImplJson.nextTrial().isEmpty());
        assertEquals(trialReaderImplJson.nextTrial().orElse(null)
                , new ExtraTrial("Dimon", 10, 11, 44));

        assertFalse(trialReaderImplJson.hasTrial());
        trialReaderImplJson.close();
    }
    @Test
    public void closeTest() throws Exception {
        trialReaderImplJson.close();
        verify(fileReader).close();
    }
}