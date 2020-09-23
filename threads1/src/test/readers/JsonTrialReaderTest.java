package readers;

import com.google.gson.stream.JsonReader;
import utilityfactories.TrialReaderFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.*;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class JsonTrialReaderTest {

    String configFileName = "src/main/resources/testconfig.properties";
    TrialDao trialReaderImplJson;
    @Mock
    JsonReader reader;

    @Before
    public void init() {
        trialReaderImplJson = TrialReaderFactory.getTrialDAO(configFileName
                , "jsonreader");
        MockitoAnnotations.initMocks(this);
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
                , new ExtraTrial("Egor", 50, 60, 20));
        assertTrue(trialReaderImplJson.nextTrial().isEmpty());
        assertEquals(trialReaderImplJson.nextTrial().orElse(null)
                , new ExtraTrial("Dimon", 10, 11, 44));
        assertFalse(trialReaderImplJson.hasTrial());
        trialReaderImplJson.close();
    }

    @Test
    public void closeTest() throws Exception {
        MockitoAnnotations.initMocks(this);
        Field field = trialReaderImplJson.getClass().getDeclaredField("reader");
        field.setAccessible(true);
        field.set(trialReaderImplJson, reader);
        trialReaderImplJson.close();
        verify(reader).close();
    }
}