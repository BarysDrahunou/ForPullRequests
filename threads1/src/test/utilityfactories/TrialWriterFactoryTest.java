package utilityfactories;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;

import writers.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TrialWriterFactoryTest {
    TrialConsumer csvConsumer;
    TrialConsumer jsonConsumer;
    TrialConsumer sqlConsumer;

    @Before
    public void init() {
        csvConsumer = new TrialWriterImplCSV("csv.csv");
        jsonConsumer = new TrialWriterImplJson("json.jsonl");

    }

    @Test
    public void getConsumerTest() throws SQLException, IOException, ClassNotFoundException {
        sqlConsumer = new TrialWriterImplSQL("trials", "tria", "root", "root");
        assertEquals(csvConsumer.getClass(), TrialWriterFactory.getConsumer("testconfig.properties"
                , "csvwriter").getClass());
        assertEquals(jsonConsumer.getClass(), TrialWriterFactory.getConsumer("testconfig.properties"
                , "jsonwriter").getClass());
        assertEquals(sqlConsumer.getClass(), TrialWriterFactory.getConsumer("testconfig.properties"
                , "sqlwriter").getClass());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = WrongArgumentException.class)
    public void getConsumerWrongArgumentExceptionTest() throws SQLException, IOException, ClassNotFoundException {
        try {
            TrialWriterFactory.getConsumer("testconfig.properties"
                    , "somewriter");
        } catch (WrongArgumentException e) {
            try {
                TrialWriterFactory.getConsumer("testconfig.properties"
                        , "faultywriter1");
            } catch (WrongArgumentException e1) {
                try {
                    TrialWriterFactory.getConsumer("testconfig.properties"
                            , "faultywriter2");
                } catch (WrongArgumentException e2) {
                    try {
                        TrialWriterFactory.getConsumer("testconfig.properties"
                                , "sqlwriters").getClass();
                    } catch (WrongArgumentException e3) {

                            TrialWriterFactory.getConsumer("testconfig.properties", "sqlfaultywriter");
                    }
                }
            }
        }
    }
}