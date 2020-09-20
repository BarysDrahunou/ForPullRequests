package readerswritersfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import writers.*;

import java.io.IOException;
import java.sql.SQLException;


public class TrialWriterFactory implements AbstractFactory {
    private static final String NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE = "username";
    private static final String NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE = "password";
    private static String configFileName;

    public static TrialConsumer getConsumer(String configurationFileName, String writerNameInProperties) {
        configFileName = configurationFileName;
        String writer = AbstractFactory.getIfPropertyExists(configurationFileName
                , writerNameInProperties);
        try {
            String typeOfWriter = FilenameUtils.getExtension(writer).toUpperCase();
            return WriterImplementationKind.valueOf(typeOfWriter)
                    .getTrialConsumer(writer);
        } catch (WrongArgumentException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new WrongArgumentException("Incorrect extension format", writer, e);
        }
    }

    private enum WriterImplementationKind {
        CSV {
            @Override
            TrialConsumer getTrialConsumer(String line) {
                return new TrialWriterImplCSV(line);
            }
        },
        JSON {
            @Override
            TrialConsumer getTrialConsumer(String line) {
                return new TrialWriterImplJson(line);
            }
        },
        SQL {
            @Override
            TrialConsumer getTrialConsumer(String line) {
                String[] dataBaseAndTableNames = FilenameUtils.removeExtension(line).split("\\.");
                if (dataBaseAndTableNames.length != 2) {
                    throw new WrongArgumentException("incorrect name of writer file", line);
                }
                String nameOfDataBase = dataBaseAndTableNames[0];
                String nameOfTable = dataBaseAndTableNames[1];
                String login = AbstractFactory.getIfPropertyExists(configFileName
                        , NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE);
                String password = AbstractFactory.getIfPropertyExists(configFileName
                        , NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE);
                return new TrialWriterImplSQL(nameOfDataBase, nameOfTable, login, password);
            }
        };

        abstract TrialConsumer getTrialConsumer(String line);
    }
}
