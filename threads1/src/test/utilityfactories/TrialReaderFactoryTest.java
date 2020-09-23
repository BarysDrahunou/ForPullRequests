package utilityfactories;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;
import readers.TrialDao;
import readers.CsvTrialReader;
import readers.JsonTrialReader;
import readers.SqlTrialReader;

import static org.junit.Assert.*;

public class TrialReaderFactoryTest {

    String configFileName = "src/main/resources/testconfig.properties";
    TrialDao csvImpl;
    TrialDao jsonImpl;
    TrialDao sqlImpl;

    @Before
    public void init(){
        csvImpl = new CsvTrialReader();
        csvImpl.setReader("threads1.csv",configFileName);
        jsonImpl = new JsonTrialReader();
        jsonImpl.setReader("threads1.json",configFileName);
        sqlImpl = new SqlTrialReader();
        sqlImpl.setReader("trials1.trials1.sql",configFileName);
    }

    @Test
    public void getTrialDAOTest() {
        assertEquals(csvImpl.getClass(), TrialReaderFactory.getTrialDAO(configFileName
                , "csvreader").getClass());
        assertEquals(jsonImpl.getClass(), TrialReaderFactory.getTrialDAO(configFileName
                , "jsonreader").getClass());
        assertEquals(sqlImpl.getClass(), TrialReaderFactory.getTrialDAO(configFileName
                , "sqlreader").getClass());
    }

    @Test(expected = WrongArgumentException.class)
    public void getTrialDAOWrongArgumentExceptionTest()  {
        try {
            TrialReaderFactory.getTrialDAO(configFileName
                    , "csvreaders");
        } catch (WrongArgumentException e) {
            try {
                TrialReaderFactory.getTrialDAO(configFileName
                        , "faultyreader1");
            } catch (WrongArgumentException e1) {
                try {
                    TrialReaderFactory.getTrialDAO(configFileName
                            , "faultyreader2");
                } catch (WrongArgumentException e2) {
                    try {
                        TrialReaderFactory.getTrialDAO(configFileName
                                , "faultyreader3");
                    } catch (WrongArgumentException e3) {
                        TrialReaderFactory.getTrialDAO(configFileName
                                , "noexistreader");
                    }
                }
            }
        }
    }
}