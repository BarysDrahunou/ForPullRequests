package utilityfactories;

import myexceptions.WrongArgumentException;
import org.apache.commons.io.FilenameUtils;
import writers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class TrialWriterFactory implements AbstractFactory {
    private static Properties properties;

    public static TrialConsumer getConsumer(String configurationFileName, String writerNameInProperties) throws IOException, SQLException, ClassNotFoundException, WrongArgumentException {
        properties = AbstractFactory.getProperties(configurationFileName);
        String writer = AbstractFactory.getProperty(properties, writerNameInProperties);
        if (writer == null) {
            throw new WrongArgumentException("Property with this name doesn't exist"
                    , writerNameInProperties);
        }
        try {
            String typeOfWriter = FilenameUtils.getExtension(writer).toUpperCase();
            return TrialWriterFactory.WriterImplementationKind.valueOf(typeOfWriter)
                    .getTrialConsumer(writer);
        } catch (WrongArgumentException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new WrongArgumentException("Incorrect extension format", writer);
        }
    }

    private enum WriterImplementationKind {
        CSV {
            @Override
            TrialConsumer getTrialConsumer(String line) {
                return new TrialWriterImplCSV(line);
            }
        },
        JSONL {
            @Override
            TrialConsumer getTrialConsumer(String line) {
                return new TrialWriterImplJson(line);
            }
        },
        SQL {
            @Override
            TrialConsumer getTrialConsumer(String line) throws SQLException, ClassNotFoundException {
                String[] dataBaseAndTableNames = FilenameUtils.removeExtension(line).split("\\.");
                if (dataBaseAndTableNames.length != 2) {
                    throw new WrongArgumentException("incorrect name of writer file", line);
                }
                String nameOfDataBase = dataBaseAndTableNames[0];
                String nameOfTable = dataBaseAndTableNames[1];
                String login = AbstractFactory.getProperty(properties, "username");
                String password = AbstractFactory.getProperty(properties, "password");
                checkForPresence(login, password);
                return new TrialWriterImplSQL(nameOfDataBase, nameOfTable, login, password);
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

        abstract TrialConsumer getTrialConsumer(String line) throws SQLException
                , ClassNotFoundException, FileNotFoundException;
    }
}
