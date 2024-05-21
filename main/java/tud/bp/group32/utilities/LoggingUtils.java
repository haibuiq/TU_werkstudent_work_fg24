package tud.bp.group32.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Utility class used for the Logging operation of the FG24.
 */
public class LoggingUtils {
    /**
     * String for the name FG24_LOGGER.
     */
    private static final String FG24_LOGGER = "FG24_LOGGER";
    /**
     * String that asks the user to choose the save location for
     * the Logging file.
     */
    private static final String SELECT_LOG_LOCATION =
            "Protokolldatei Speicherort auswählen:";
    /**
     * String that indicates the Logging operation has been interrupted by the
     * user.
     */
    private static final String OPERATION_CANCELED =
            "Protokolldatei Operation wurde von dem Nutzer abgebrochen.";
    /**
     * String that indicates the Logging operation has either been interrupted
     * by the user, or an error has occurred.
     */
    private static final String OPERATION_CANCELED_OR_ERROR =
            "Protokolldatei Operation wurde von dem Nutzer abgebrochen"
                    + "oder ein Fehler aufgetreten ist.";
    /**
     * String that indicates there is an error with choosing the save location
     * for the Logging file.
     */
    private static final String LOCATION_ERROR =
            "Fehler bei dem Auswählen von Protokolldatei Speicherort.";
    /**
     * String that indicates the logging is currently not possible.
     */
    private static final String LOG_NOT_POSSIBLE =
            "Protokollieren ist derzeit nicht möglich.";
    /**
     * String that indicates the path has been changed.
     */
    private static final String PATH_CHANGED = "Protokolldatei wurde geändert.";
    private static String newLocation(final String newPath) {
        return "Protokolldateipfad ist jetzt: " + newPath;
    }

    /**
     * Singleton instance.
     */
    private static LoggingUtils instance;

    /**
     * Logger object.
     */
    private final Logger logger;
    /**
     * Simple file logging Handler.
     */
    private static FileHandler fileHandler;

    /**
     * DEBUG Level.
     */
    public static final Level DEBUG = new Level("DEBUG",
            Level.INFO.intValue() - 2) {
        @Override
        public String getLocalizedName() {
            return "DEBUG";
        }
    };

    /**
     * DEBUG_CRITICAL Level.
     */
    public static final Level DEBUG_CRITICAL = new Level("DEBUG_CRITICAL",
            Level.INFO.intValue() - 1) {
        @Override
        public String getLocalizedName() {
            return "DEBUG_CRITICAL";
        }
    };

    /**
     * ERROR Level.
     */
    public static final Level ERROR = new Level("ERROR",
            Level.WARNING.intValue() + 1) {
        @Override
        public String getLocalizedName() {
            return "ERROR";
        }
    };

    /**
     * Constructor of LoggingUtils (without parameter).
     */
    public LoggingUtils() {
        // Create a logger
        logger = Logger.getLogger(FG24_LOGGER);

        // Ask the user for the log file location using JFileChooser
        String logFilePath = getLogFileLocation();
        if (logFilePath == null) {
            // User canceled the operation or an error occurred
            System.out.println(OPERATION_CANCELED_OR_ERROR);
            return;
        }
        setLoggerLocation(logFilePath);
    }

    /**
     * Constructor of LoggingUtils (with parameter).
     * @param path path of logger.
     */
    public LoggingUtils(final String path) {
        this.logger = Logger.getLogger(FG24_LOGGER);
        setLoggerLocation(path);
    }

    /**
     * This method sets the new location for the logger.
     * @param logPath new path/location.
     */
    public void setLoggerLocation(final String logPath) {
        try {
            // Create a FileHandler to write logs to the specified file
            boolean previousHandler = false;
            if (fileHandler != null) {
                previousHandler = true;
                writeInLog(Level.INFO, newLocation(logPath));
                logger.removeHandler(fileHandler);
            }
            // 'true' for append mode
            fileHandler = new FileHandler(logPath, true);
            fileHandler.setFormatter(new SimpleTextFormatter());
            logger.addHandler(fileHandler);
            // Set the logger level to capture all levels
            logger.setLevel(Level.ALL);
            if (previousHandler) {
                writeInLog(Level.INFO, PATH_CHANGED);
            }
        } catch (Exception e) {
            ExceptionRoutines.exceptionRoutineSafe(e, null);
        }
    }

    /**
     * This method checks if the instance exists.
     * @return boolean value of the result.
     */
    public static boolean instanceExists() {
        return instance != null;
    }

    /**
     * This method checks if an instance was already created.
     * If not, it creates a new instance.
     * @return a new instance
     */
    public static LoggingUtils getInstance() {
        if (instance == null) {
            instance = new LoggingUtils();
        }
        return instance;
    }

    /**
     * @param level message level identifier.
     * @param logText Logging message.
     */
    public void writeInLog(final Level level, final String logText) {
        if (fileHandler == null) {
            System.out.println(LOG_NOT_POSSIBLE);
            return;
        }

        // Log message String in parameter
        logger.log(level, "[" + getCurrentDateTime() + "]  |  " + logText);
    }

    //singleton
    private static String getLogFileLocation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(SELECT_LOG_LOCATION);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (path.endsWith(".txt")) {
                return path;
            } else {
                return path + ".txt";
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, OPERATION_CANCELED);
            return null;
        } else {
            JOptionPane.showMessageDialog(null, LOCATION_ERROR);
            return null;
        }
    }

    private static final class SimpleTextFormatter extends Formatter {
        @Override
        public String format(final LogRecord record) {
            return record.getLevel() + " " + record.getMessage()
                    + System.lineSeparator();
        }
    }

    /**
     * @return the current date and time
     */
    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MMM dd, yyyy h:mm:ss a");
        return dateFormat.format(new Date());
    }
}
