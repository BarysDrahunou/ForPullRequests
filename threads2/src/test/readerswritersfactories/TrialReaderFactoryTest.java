package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.junit.Before;
import org.junit.Test;
import readers.TrialDao;
import readers.CsvTrialReader;
import readers.JsonTrialReader;
import readers.SqlTrialReader;

import java.util.List;

import static org.junit.Assert.*;

public class TrialReaderFactoryTest {

    String configFileName = "src/main/resources/testconfig.properties";
    TrialDao csvImpl;
    TrialDao jsonImpl;
    TrialDao sqlImpl;
    TrialReaderFactory trialReaderFactory;

    @Before
    public void init() {
        csvImpl = new CsvTrialReader();
        csvImpl.setReader("threads1.csv", configFileName);
        jsonImpl = new JsonTrialReader();
        jsonImpl.setReader("threads1.json", configFileName);
        sqlImpl = new SqlTrialReader();
        sqlImpl.setReader("trials1.trials1.sql", configFileName);
        trialReaderFactory = new TrialReaderFactory();
    }

    @Test
    public void getTrialDAOTest() {
        List<TrialDao> trialDaoList = trialReaderFactory.getTrialDAO(configFileName, "readers");
        assertEquals(3, trialDaoList.size());
        assertEquals(csvImpl.getClass(), trialDaoList.get(0).getClass());
        assertEquals(jsonImpl.getClass(), trialDaoList.get(1).getClass());
        assertEquals(sqlImpl.getClass(), trialDaoList.get(2).getClass());
    }

    @Test(expected = WrongArgumentException.class)
    public void getTrialDAOIncorrectSQLFileNameTest() {
        trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader3");
    }

    @Test(expected = WrongArgumentException.class)
    public void getTrialDAONPETest() {
        trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader2");
    }

    @Test(expected = WrongArgumentException.class)
    public void getTrialDAOIllegalArgumentExceptionTest() {
        trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader1");
    }

    @Test(expected = WrongArgumentException.class)
    public void getTrialDAOWrongArgumentExceptionTest() {
        trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader4");
    }
}