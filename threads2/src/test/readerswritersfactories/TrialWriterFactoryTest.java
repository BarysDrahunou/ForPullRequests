package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.junit.Test;

import writers.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TrialWriterFactoryTest {
    TrialConsumer sqlConsumer;
    String configFileName = "src/main/resources/testconfig.properties";

    @Test
    public void getConsumerTest() throws SQLException, IOException, ClassNotFoundException {
        sqlConsumer = new TrialWriterImplSQL("trials", "tria"
                , "root", "root");
        assertEquals(TrialWriterImplCSV.class, TrialWriterFactory.getConsumer(configFileName
                , "csvwriter").getClass());
        assertEquals(TrialWriterImplJson.class, TrialWriterFactory.getConsumer(configFileName
                , "jsonwriter").getClass());
        assertEquals(TrialWriterImplSQL.class, TrialWriterFactory.getConsumer(configFileName
                , "sqlwriter").getClass());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = WrongArgumentException.class)
    public void getConsumerWrongArgumentExceptionTest() throws SQLException, IOException, ClassNotFoundException {
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