package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import readers.*;

import java.util.*;

import static constants.ExceptionsMessages.*;
import static constants.ReaderWriterConstants.*;

public class TrialReaderFactory {

    private static final Map<String, TrialDao> trialDaoMap = new HashMap<>();

    static {
        trialDaoMap.put(CSV, new CsvTrialReader());
        trialDaoMap.put(JSON, new JsonTrialReader());
        trialDaoMap.put(SQL, new SqlTrialReader());
    }

    public static TrialDao getTrialDAO
            (String configurationFileName, String readerNameInProperties) {
        String reader = PropertiesUtilClass.getIfPropertyExists(configurationFileName,
                readerNameInProperties);
        String typeOfReader = FilenameUtils.getExtension(reader).toUpperCase();
        TrialDao trialDao = trialDaoMap.computeIfAbsent(typeOfReader, key -> {
            throw new WrongArgumentException(INCORRECT_EXTENSION, reader);
        });
        trialDao.setReader(reader, configurationFileName);
        return trialDao;
    }
}
