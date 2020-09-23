package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import readers.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialReaderFactory implements AutoCloseable {

    private List<TrialDao> trialDaoList;
    private static final Map<String, TrialDao> trialDaoMap = new HashMap<>();

    static {
        trialDaoMap.put(CSV, new CsvTrialReader());
        trialDaoMap.put(JSON, new JsonTrialReader());
        trialDaoMap.put(SQL, new SqlTrialReader());
    }

    public List<TrialDao> getTrialDAO(String configurationFileName, String readerNameInProperties) {
        String reader = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                readerNameInProperties);
        String[] allReaders = reader.split(CSV_SEPARATOR);
        List<TrialDao> allTrialDao = new ArrayList<>();
        for (String singleReader : allReaders) {
            String typeOfReader = FilenameUtils.getExtension(singleReader).toUpperCase();
            TrialDao trialDao = trialDaoMap.computeIfAbsent(typeOfReader, key -> {
                throw new WrongArgumentException(INCORRECT_EXTENSION, reader);
            });
            trialDao.setReader(reader, configurationFileName);
            allTrialDao.add(trialDao);
        }
        this.trialDaoList = allTrialDao;
        return allTrialDao;
    }

    @Override
    public void close() throws IOException, SQLException {
        for (TrialDao trialDao : trialDaoList) {
            trialDao.close();
        }
    }
}
