package writers;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrialWriterImplCSVTest {
    @Mock
    FileWriter output;
    @Mock
    Trial trial;
    TrialConsumer trialConsumer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void writeTrialTest() throws IOException, NoSuchFieldException, IllegalAccessException {
        trialConsumer = new TrialWriterImplCSV("writer.csv");
        Field field = TrialWriterImplCSV.class.getDeclaredField("output");
        field.setAccessible(true);
        field.set(trialConsumer, output);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        trialConsumer.writeTrial(trial);
        verify(output, times(1)).write(argumentCaptor.capture());
    }

    @Test(expected = WrongArgumentException.class)
    public void writeTrialTestAlreadyExist(){
        trialConsumer = new TrialWriterImplCSV("writer.csv");
    }

    @Test
    public void closeTest() throws Exception {
        trialConsumer = new TrialWriterImplCSV("newWriter.csv");
        Field field = TrialWriterImplCSV.class.getDeclaredField("output");
        field.setAccessible(true);
        field.set(trialConsumer, output);
        trialConsumer.close();
        verify(output).close();
    }
}