package constants;

import java.util.Random;

public class ReaderWriterConstants {

    public static final int QUEUE_SIZE = new Random().nextInt(100) + 1;
    public static final String DEFAULT_PATH_TO_PROPERTIES = "src/main/resources/configuration.properties";
    public static final String READER_NAME_IN_PROPERTIES = "reader";
    public static final String WRITER_NAME_IN_PROPERTIES = "writer";
    public static final String NAME_OF_LOGIN_PROPERTY_INTO_CONFIG_FILE = "username";
    public static final String NAME_OF_PASSWORD_PROPERTY_INTO_CONFIG_FILE = "password";
    public static final String CSV = "CSV";
    public static final String JSON = "JSON";
    public static final String SQL = "SQL";
    public static final String OUTPUT_PATH = "src/main/outputfolder/";
    public static final String CSV_SEPARATOR = ";";
    public static final String REGEXP_EXTENSION = "\\.";
    public static final String REGEXP_FOR_SPLIT = "([^_])([A-Z])";
    public static final String REPLACEMENT = "$1_$2";
    public static final int DATABASE_NAME_PARAM = 0;
    public static final int TABLE_NAME_PARAM = 1;
    public static final int LOGIN_PARAM = 2;
    public static final int PASSWORD_PARAM = 3;
    public static final int DATABASE_PARAMS = 2;
    public static final int PREPARED_STATEMENT_PARAM = 1;
}
