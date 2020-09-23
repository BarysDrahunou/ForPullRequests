package readers;

import utilityfactories.TrialReaderFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.*;

import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class CsvTrialReaderTest {

    String configFileName = "src/main/resources/testconfig.properties";
    @Mock
    Scanner scanner;
    TrialDao trialReaderImplCSV;

    @Before
    public void init()  {
        trialReaderImplCSV = TrialReaderFactory.getTrialDAO(configFileName
                , "csvtestreader");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void hasTrialTest() {
        assertTrue(trialReaderImplCSV.hasTrial());
    }

    @Test
    public void nextTrialTestAndFalseHasTrial() {
        assertEquals(trialReaderImplCSV.nextTrial().orElse(null)
                , new Trial("Vitali", 10, 22));
        assertEquals(trialReaderImplCSV.nextTrial().orElse(null)
                , new ExtraTrial("Dimon", 10, 11, 44));
        assertTrue(trialReaderImplCSV.nextTrial().isEmpty());
        assertTrue(trialReaderImplCSV.nextTrial().isEmpty());
        assertTrue(trialReaderImplCSV.nextTrial().isEmpty());
        assertTrue(trialReaderImplCSV.nextTrial().isEmpty());
        assertFalse(trialReaderImplCSV.hasTrial());
    }

    @Test
    public void closeTest() throws Exception {
        Field field = trialReaderImplCSV.getClass().getDeclaredField("scanner");
        field.setAccessible(true);
        field.set(trialReaderImplCSV, scanner);
        trialReaderImplCSV.close();
        verify(scanner).close();
    }
}