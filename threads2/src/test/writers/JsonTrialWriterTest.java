package writers;

import com.google.gson.stream.JsonWriter;
import org.junit.Test;
import trials.Trial;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

public class JsonTrialWriterTest {

    String OUTPUT_PATH = "src/main/outputfolder/";
    JsonWriter writer;
    TrialConsumer trialConsumer;

    @Test
    public void writeTrialTest() throws IOException, NoSuchFieldException, IllegalAccessException {
        Trial trial = new Trial("Vitalya", 11, 22);
        this.writer = new JsonWriter(new FileWriter(OUTPUT_PATH + "writerTestJson.json"));
        trialConsumer = new JsonTrialWriter();
        trialConsumer.setWriter("jsonTest.json",OUTPUT_PATH);
        Field field = JsonTrialWriter.class.getDeclaredField("writer");
        field.setAccessible(true);
        field.set(trialConsumer, writer);
        trialConsumer.writeTrial(trial);
    }

    @Test
    public void closeTest() throws Exception {
        writer= mock(JsonWriter.class);
        trialConsumer = new JsonTrialWriter();
        trialConsumer.setWriter("newWriter.json",OUTPUT_PATH);
        Field field = JsonTrialWriter.class.getDeclaredField("writer");
        field.setAccessible(true);
        field.set(trialConsumer, writer);
        trialConsumer.close();
        verify(writer,times(1)).close();
    }
}