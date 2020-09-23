package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;

import writers.*;

import static org.junit.Assert.*;

public class TrialWriterFactoryTest {

    TrialConsumer sqlConsumer;
    String configFileName = "src/main/resources/testconfig.properties";
    TrialWriterFactory trialWriterFactory;

    @Before
    public void init(){
        sqlConsumer = new SqlTrialWriter();
        trialWriterFactory=new TrialWriterFactory();
    }

    @Test
    public void getConsumerTest() {
        sqlConsumer.setWriter("trials.trials.sql", configFileName);
        assertEquals(CsvTrialWriter.class, trialWriterFactory.getConsumer(configFileName
                , "csvwriter").getClass());
        assertEquals(JsonTrialWriter.class, trialWriterFactory.getConsumer(configFileName
                , "jsonwriter").getClass());
        assertEquals(SqlTrialWriter.class, trialWriterFactory.getConsumer(configFileName
                , "sqlwriter").getClass());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = WrongArgumentException.class)
    public void getConsumerWrongArgumentExceptionTest() {
        try {
            trialWriterFactory.getConsumer(configFileName
                    , "somewriter");
        } catch (WrongArgumentException e) {
            try {
                trialWriterFactory.getConsumer(configFileName
                        , "faultywriter1");
            } catch (WrongArgumentException e1) {
                try {
                    trialWriterFactory.getConsumer(configFileName
                            , "faultywriter2");
                } catch (WrongArgumentException e2) {
                    try {
                        trialWriterFactory.getConsumer(configFileName
                                , "sqlwriters").getClass();
                    } catch (WrongArgumentException e3) {
                        trialWriterFactory.getConsumer(configFileName, "sqlfaultywriter");
                    }
                }
            }
        }
    }
}