package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import readers.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;


public class TrialReaderFactory implements AbstractFactory, AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE = "username";
    private static final String NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE = "password";
    private static String configFileName;
    private List<TrialDao> trialDaoList;

    public List<TrialDao> getTrialDAO(String configurationFileName, String readerNameInProperties) {
        configFileName = configurationFileName;
        String reader = AbstractFactory.getIfPropertyExists(configurationFileName
                , readerNameInProperties);
        String[] allReaders = reader.split(";");
        List<TrialDao> allTrialDao = new ArrayList<>();
        for (String singleReader : allReaders) {
            try {
                String typeOfReader = FilenameUtils.getExtension(singleReader).toUpperCase();
                allTrialDao.add(ReaderImplementationKind.valueOf(typeOfReader).getTrialDAO(singleReader));
            } catch (SQLException | ClassNotFoundException | IOException | WrongArgumentException e) {
                LOGGER.error(e);
            } catch (NullPointerException e) {
                LOGGER.error(String.format("File with this name doesn't exist: %s", singleReader));
            } catch (IllegalArgumentException e) {
                LOGGER.error(String.format("Incorrect extension format: %s", singleReader));
            }
        }
        this.trialDaoList = allTrialDao;
        return allTrialDao;
    }

    @Override
    public void close() throws Exception {
        for (TrialDao trialDao : trialDaoList) {
            trialDao.close();
        }
    }

    private enum ReaderImplementationKind {
        CSV {
            @Override
            TrialDao getTrialDAO(String inputValue) throws FileNotFoundException {
                return new TrialReaderImplCSV(inputValue);
            }
        },
        JSON {
            @Override
            TrialDao getTrialDAO(String inputValue) throws IOException {
                return new TrialReaderImplJson(inputValue);
            }
        },
        SQL {
            @Override
            TrialDao getTrialDAO(String inputValue) throws SQLException, ClassNotFoundException {
                String[] dataBaseAndTableNames = FilenameUtils.removeExtension(inputValue).split("\\.");
                if (dataBaseAndTableNames.length != 2) {
                    throw new WrongArgumentException("incorrect name of reader file", inputValue);
                }
                String nameOfDataBase = dataBaseAndTableNames[0];
                String nameOfTable = dataBaseAndTableNames[1];
                String login = AbstractFactory.getIfPropertyExists(configFileName
                        , NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE);
                String password = AbstractFactory.getIfPropertyExists(configFileName
                        , NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE);
                return new TrialReaderImplSql(nameOfDataBase, nameOfTable, login, password);
            }
        };

        abstract TrialDao getTrialDAO(String inputValue) throws SQLException, ClassNotFoundException, IOException;
    }
}
