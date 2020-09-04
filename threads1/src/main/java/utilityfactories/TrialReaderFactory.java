package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import readers.*;

import java.io.*;
import java.sql.SQLException;


public class TrialReaderFactory implements AbstractFactory {

    private static final String NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE="username";
    private static final String NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE="password";
    private static String configFileName;

    public static TrialDao getTrialDAO(String configurationFileName, String readerNameInProperties)
            throws IOException, SQLException, ClassNotFoundException {
        configFileName=configurationFileName;
        String reader = AbstractFactory.getIfPropertyExists(configurationFileName
                , readerNameInProperties);
        try {
            String typeOfReader = FilenameUtils.getExtension(reader).toUpperCase();
            return ReaderImplementationKind.valueOf(typeOfReader).getTrialDAO(reader);
        } catch (NullPointerException e) {
            throw new WrongArgumentException("File with this name doesn't exist", reader, e);
        } catch (IllegalArgumentException e) {
            throw new WrongArgumentException("Incorrect extension format", reader, e);
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
                        ,NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE);
                String password = AbstractFactory.getIfPropertyExists(configFileName
                        ,NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE);
                return new TrialReaderImplSql(nameOfDataBase, nameOfTable, login, password);
            }
        };

        abstract TrialDao getTrialDAO(String inputValue) throws SQLException, ClassNotFoundException, IOException;
    }
}
