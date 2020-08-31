package utilityfactories;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;
import readers.TrialDao;
import readers.TrialReaderImplCSV;
import readers.TrialReaderImplJson;
import readers.TrialReaderImplSql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TrialReaderFactoryTest {
    TrialDao csvImpl;
    TrialDao jsonImpl;
    TrialDao sqlImpl;

    @Before
    public void init() throws FileNotFoundException, SQLException, ClassNotFoundException {
        csvImpl = new TrialReaderImplCSV("threads1.csv");
        jsonImpl = new TrialReaderImplJson("threads1.jsonl");
        sqlImpl = new TrialReaderImplSql("trials1", "trials1"
                , "root", "root");
    }

    @Test
    public void getTrialDAOTest() throws SQLException, IOException, ClassNotFoundException {
        assertEquals(csvImpl.getClass(), TrialReaderFactory.getTrialDAO("testconfig.properties"
                , "csvreader").getClass());
        assertEquals(jsonImpl.getClass(), TrialReaderFactory.getTrialDAO("testconfig.properties"
                , "jsonreader").getClass());
        assertEquals(sqlImpl.getClass(), TrialReaderFactory.getTrialDAO("testconfig.properties"
                , "sqlreader").getClass());
    }

    @Test(expected = WrongArgumentException.class)
    public void getTrialDAOWrongArgumentExceptionTest() throws SQLException, IOException, ClassNotFoundException {
        try {
            TrialReaderFactory.getTrialDAO("testconfig.properties"
                    , "csvreaders");
        } catch (WrongArgumentException e) {
            try {
                TrialReaderFactory.getTrialDAO("testconfig.properties"
                        , "faultyreader1");
            } catch (WrongArgumentException e1) {
                try {
                    TrialReaderFactory.getTrialDAO("testconfig.properties"
                            , "faultyreader2");
                } catch (WrongArgumentException e2) {
                    try {
                        TrialReaderFactory.getTrialDAO("testconfig.properties"
                                , "faultyreader3");
                    } catch (WrongArgumentException e3) {
                        TrialReaderFactory.getTrialDAO("testconfig.properties"
                                , "noexistreader");
                    }
                }

            }
        }
    }
}