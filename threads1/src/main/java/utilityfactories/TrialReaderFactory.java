package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import readers.*;

import java.io.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class TrialReaderFactory implements AbstractFactory {
    private static Properties properties;

    public static TrialDao getTrialDAO(String configurationFileName, String readerNameInProperties)
            throws IOException, SQLException, ClassNotFoundException, WrongArgumentException {

        properties = AbstractFactory.getProperties(configurationFileName);
        String reader = AbstractFactory.getProperty(properties, readerNameInProperties);
        if (reader == null) {
            throw new WrongArgumentException("Property with this name doesn't exist", readerNameInProperties);
        }
        try {
            String typeOfReader = FilenameUtils.getExtension(reader).toUpperCase();
            return ReaderImplementationKind.valueOf(typeOfReader).getTrialDAO(reader);
        } catch (NullPointerException e) {
            throw new WrongArgumentException("File with this name doesn't exist", reader);
        } catch (WrongArgumentException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new WrongArgumentException("Incorrect extension format", reader);
        }
    }

    private enum ReaderImplementationKind {
        CSV {
            @Override
            TrialDao getTrialDAO(String inputValue) throws FileNotFoundException {
                return new TrialReaderImplCSV(inputValue);
            }
        },
        JSONL {
            @Override
            TrialDao getTrialDAO(String inputValue) throws FileNotFoundException {
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
                String nameOfDataBase=dataBaseAndTableNames[0];
                String nameOfTable=dataBaseAndTableNames[1];
                String login = AbstractFactory.getProperty(properties, "username");
                String password = AbstractFactory.getProperty(properties, "password");
                checkForPresence(login, password);
                return new TrialReaderImplSql(nameOfDataBase,nameOfTable,login,password);
            }
        };

        private static void checkForPresence(String login, String password) {
            if (Objects.isNull(login)) {
                throw new WrongArgumentException("Can't find a property", "username");
            }
            if (Objects.isNull(password)) {
                throw new WrongArgumentException("Can't find a property", "password");
            }
        }

        abstract TrialDao getTrialDAO(String inputValue) throws SQLException, ClassNotFoundException, FileNotFoundException;
    }
}
