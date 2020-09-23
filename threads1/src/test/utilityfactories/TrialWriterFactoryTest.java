package utilityfactories;

import myexceptions.WrongArgumentException;
import org.junit.Test;

import writers.*;

import static org.junit.Assert.*;

public class TrialWriterFactoryTest {

    TrialConsumer sqlConsumer;
    String configFileName = "src/main/resources/testconfig.properties";

    @Test
    public void getConsumerTest() {
        sqlConsumer = new SqlTrialWriter();
        sqlConsumer.setWriter("trials.trials.sql", configFileName);
        assertEquals(CsvTrialWriter.class, TrialWriterFactory.getConsumer(configFileName
                , "csvwriter").getClass());
        assertEquals(JsonTrialWriter.class, TrialWriterFactory.getConsumer(configFileName
                , "jsonwriter").getClass());
        assertEquals(SqlTrialWriter.class, TrialWriterFactory.getConsumer(configFileName
                , "sqlwriter").getClass());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = WrongArgumentException.class)
    public void getConsumerWrongArgumentExceptionTest() {
        try {
            TrialWriterFactory.getConsumer(configFileName
                    , "somewriter");
        } catch (WrongArgumentException e) {
            try {
                TrialWriterFactory.getConsumer(configFileName
                        , "faultywriter1");
            } catch (WrongArgumentException e1) {
                try {
                    TrialWriterFactory.getConsumer(configFileName
                            , "faultywriter2");
                } catch (WrongArgumentException e2) {
                    try {
                        TrialWriterFactory.getConsumer(configFileName
                                , "sqlwriters").getClass();
                    } catch (WrongArgumentException e3) {
                        TrialWriterFactory.getConsumer(configFileName, "sqlfaultywriter");
                    }
                }
            }
        }
    }
}