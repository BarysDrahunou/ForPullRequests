package writers;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import trials.Trial;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileWriterImplTest {
    @Mock
    FileWriter output;
    @Mock
    Trial trial;
    static FileWriterImpl fileWriter;

    @BeforeClass
    public static void init() {
        fileWriter = new TrialWriterImplCSV("writer.csv");
    }

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        Field field = FileWriterImpl.class.getDeclaredField("output");
        field.setAccessible(true);
        field.set(fileWriter, output);
    }

    @Test
    public void writeTrial() throws IOException {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        fileWriter.writeTrial(trial);
        verify(output, times(2)).write(argumentCaptor.capture());
        assertEquals(argumentCaptor.getAllValues().get(0), fileWriter.serializeTrial(trial));
        assertEquals(argumentCaptor.getAllValues().get(1), System.lineSeparator());
    }

    @Test
    public void close() throws Exception {
        fileWriter.close();
        verify(output).close();
    }
}