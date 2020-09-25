package readerswritersfactories;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialReaderFactory implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, TrialDao> TRIAL_DAO_PATTERNS_MAP = new HashMap<>();
    private List<TrialDao> trialDaoList;

    static {
        TRIAL_DAO_PATTERNS_MAP.put(CSV, new CsvTrialReader());
        TRIAL_DAO_PATTERNS_MAP.put(JSON, new JsonTrialReader());
        TRIAL_DAO_PATTERNS_MAP.put(SQL, new SqlTrialReader());
    }

    public List<TrialDao> getTrialDAO(String configurationFileName, String readerNameInProperties) {
        String reader = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                readerNameInProperties);
        String[] allReaders = reader.split(CSV_SEPARATOR);
        List<TrialDao> allTrialDao = new ArrayList<>();
        for (String singleReader : allReaders) {
            String readerType = FilenameUtils.getExtension(singleReader).toUpperCase();
            TrialDao trialDaoPattern = TRIAL_DAO_PATTERNS_MAP.get(readerType);
            if (trialDaoPattern != null) {
                TrialDao trialDAO = trialDaoPattern.getCopy();
                trialDAO.setReader(singleReader, configurationFileName);
                allTrialDao.add(trialDAO);
            } else {
                LOGGER.error(String.format("%s - %s", INCORRECT_EXTENSION, singleReader));
            }
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
