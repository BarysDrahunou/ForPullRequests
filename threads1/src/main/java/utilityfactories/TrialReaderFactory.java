package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import readers.*;

import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialReaderFactory {

    private static final Map<String, TrialDao> TRIAL_DAO_PATTERNS_MAP = new HashMap<>();

    static {
        TRIAL_DAO_PATTERNS_MAP.put(CSV, new CsvTrialReader());
        TRIAL_DAO_PATTERNS_MAP.put(JSON, new JsonTrialReader());
        TRIAL_DAO_PATTERNS_MAP.put(SQL, new SqlTrialReader());
    }

    public static TrialDao getTrialDAO
            (String configurationFileName, String readerNameInProperties) {
        String reader = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                readerNameInProperties);
        String readerType = FilenameUtils.getExtension(reader).toUpperCase();
        TrialDao trialDao = TRIAL_DAO_PATTERNS_MAP.computeIfAbsent(readerType, key -> {
            throw new WrongArgumentException(INCORRECT_EXTENSION, reader);
        });
        trialDao.setReader(reader, configurationFileName);
        return trialDao;
    }
}
