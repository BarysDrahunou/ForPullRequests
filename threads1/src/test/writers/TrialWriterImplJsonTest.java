package writers;

import utilityfactories.TrialWriterFactory;
import org.junit.Before;
import org.junit.Test;
import trials.ExtraTrial;
import trials.Trial;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TrialWriterImplJsonTest {
    FileWriterImpl trialWriterImplJson;
    @Before
    public void init() throws SQLException, IOException, ClassNotFoundException {
        trialWriterImplJson =(FileWriterImpl) TrialWriterFactory
                .getConsumer("testconfig.properties"
                        , "jsonwriter");
    }
    @Test
    public void serializeTrial() {
        Trial trial = new Trial("trial", 10, 10);
        String jsonString="{\"class\":\"Trial\",\"args\":{\"account\":\"trial\",\"mark1\":10,\"mark2\":10}}";
        assertEquals(trialWriterImplJson.serializeTrial(trial), jsonString);
        Trial extraTrial = new ExtraTrial("trial", 10, 10,10);
        String jsonString1="{\"class\":\"ExtraTrial\",\"args\":{\"account\":\"trial\",\"mark1\":10,\"mark2\":10,\"mark3\":10}}";
        assertEquals(trialWriterImplJson.serializeTrial(extraTrial), jsonString1);
    }
}