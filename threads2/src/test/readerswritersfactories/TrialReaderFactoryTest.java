package readerswritersfactories;

import org.junit.Before;
import org.junit.Test;
import readers.TrialDao;
import readers.TrialReaderImplCSV;
import readers.TrialReaderImplJson;
import readers.TrialReaderImplSql;

import java.util.List;

import static org.junit.Assert.*;

public class TrialReaderFactoryTest {

    String configFileName = "src/main/resources/testconfig.properties";
    TrialDao csvImpl;
    TrialDao jsonImpl;
    TrialDao sqlImpl;
    TrialReaderFactory trialReaderFactory;

    @Before
    public void init(){
        csvImpl = new TrialReaderImplCSV("threads1.csv");
        jsonImpl = new TrialReaderImplJson("threads1.json");
        sqlImpl = new TrialReaderImplSql("trials1", "trials1"
                , "root", "root");
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

    @Test
    public void getTrialDAOIncorrectSQLFileNameTest() {
        assertEquals(0, trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader3").size());
    }

    @Test
    public void getTrialDAONPETest() {
        assertEquals(0, trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader2").size());
    }

    @Test
    public void getTrialDAOIllegalArgumentExceptionTest() {
        assertEquals(0, trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader1").size());
    }

    @Test
    public void getTrialDAOWrongArgumentExceptionTest() {
        assertEquals(0, trialReaderFactory
                .getTrialDAO(configFileName, "faultyreader4").size());
    }
}