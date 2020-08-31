package writers;

import utilityfactories.TrialWriterFactory;
import org.junit.Before;
import org.junit.Test;
import trials.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TrialWriterImplCSVTest {
    FileWriterImpl fileWriterImpl;
    @Before
    public void init() throws SQLException, IOException, ClassNotFoundException {
        fileWriterImpl = (FileWriterImpl) TrialWriterFactory
                .getConsumer("testconfig.properties"
                        , "csvwriter");
    }

    @Test
    public void serializeTrial() {
        Trial lightTrial = new LightTrial("trial", 10, 10);
        Trial trial = new Trial("trial", 10, 10);
        Trial strongTrial = new StrongTrial("trial", 10, 10);
        Trial extraTrial = new ExtraTrial("trial", 10, 10,11);
        assertEquals(fileWriterImpl.serializeTrial(trial), "Trial;trial;10;10");
        assertEquals(fileWriterImpl.serializeTrial(lightTrial), "LightTrial;trial;10;10");
        assertEquals(fileWriterImpl.serializeTrial(strongTrial), "StrongTrial;trial;10;10");
        assertEquals(fileWriterImpl.serializeTrial(extraTrial), "ExtraTrial;trial;10;10;11");
        assertNotEquals(fileWriterImpl.serializeTrial(trial), "Trial;trials;10;10");
    }
}