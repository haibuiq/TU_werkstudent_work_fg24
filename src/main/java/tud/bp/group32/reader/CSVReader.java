package tud.bp.group32.reader;

import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.constants.AnredeTyp;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.student.Student;
import tud.bp.group32.student.StudentAlumnifile;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.ExceptionRoutines;
import tud.bp.group32.utilities.LoggingUtils;
import tud.bp.group32.utilities.StudentUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class CSVReader {
     /**
     * Path which stores CSV-Datei.
     */
    private String filePath;

    /**
     * Which type of File.
     */
    private String type;
    /**
     * GUI Instance for exception handling.
     */
    private GUIExceptionHandler gui;

    //Konstruktor...

    /**
     * The CSV constructor sets file path.
     *
     * @param constructorFilePath path to the CSV file
     */
    public CSVReader(final String constructorFilePath) {
        this.filePath = constructorFilePath;
        this.type = LogConstants.ALUMNIDATEI;
        this.gui = null;
        DynamicProperties.initProperties(null);
    }

    /**
     * The CSV constructor sets file path and GUI.
     * @param newFilePath path to the CSV file
     * @param newGui GUI instance for exception handling
     */
    public CSVReader(final String newFilePath,
                    final GUIExceptionHandler newGui) {
        this.filePath = newFilePath;
        this.type = LogConstants.ALUMNIDATEI;
        this.gui = newGui;
        DynamicProperties.initProperties(newGui);
    }

    /**
    * reads all table entries from csv table and stores them in a hashmap.
     *  @return hashmap with stored entries from table
    */
    @SuppressWarnings("unused") // There is a toggle for this code.
    public final HashMap<String, StudentAlumnifile> readAll()
                                throws ExecutionException {
        HashMap<String, StudentAlumnifile> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath),
                        StandardCharsets.UTF_8)
            )
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row =
                        line.split(DynamicProperties.ALUMNI_CSV_SEPARATOR);
                cleanUpQuotes(row);
                String matrikelnr = "";
                if (row.length - 1 >= DynamicProperties.ALUMNI_MATRIKELNR) {
                    matrikelnr = row[DynamicProperties.ALUMNI_MATRIKELNR];
                }
                matrikelnr = StudentUtils.cleanMatrikelnr(matrikelnr);

                // If the row doesn't contain a valid Matrikelnummer
                // (is not a student), ignore it.
                if (matrikelnr.isBlank()
                        || !StudentUtils.isNumeric(matrikelnr)) {
                            continue;
                        }

                if (Student.TAKE_DATA_FROM_FIRST_ENTRY
                        && map.containsKey(matrikelnr)) {
                    LoggingUtils.getInstance().writeInLog(
                            Level.WARNING,
                            LogConstants.STUDENT_ALREADY_EXISTS(
                                    matrikelnr,
                                    LogConstants.ALUMNIDATEI));
                    continue;
                }
                map.put(matrikelnr, createStudentFromRow(row));
            }
        } catch (Exception e) {
            ExceptionRoutines.exceptionRoutine(e, gui);
        }

        return map;
    }

    private void cleanUpQuotes(final String[] row) {
        for (int i = 0; i < row.length; i++) {
            row[i] = row[i].replaceAll("\"", "");
        }
    }

    /**
     * writes map information in the log.
     * @param log log file to be written into.
     * @return hashmap.
     */
    public final HashMap<String, StudentAlumnifile> readAll(
            final LoggingUtils log) throws ExecutionException {
        var map = readAll();
        log.writeInLog(Level.INFO,
                LogConstants.SUCCESSFULLY_READ(map.size(), this.type));
        log.writeInLog(Level.INFO,
                LogConstants.SUCCESSFULLY_READ(map.size(), this.type));
        return map;
    }

    private  StudentAlumnifile createStudentFromRow(final String[] row) {
        return new StudentAlumnifile(
                row[DynamicProperties.ALUMNI_MATRIKELNR],
                row[DynamicProperties.ALUMNI_VORNAME],
                row[DynamicProperties.ALUMNI_NACHNAME],
                AnredeTyp.NOT_DEFINED);
    }

    /**
     * reads a single row from csv table with
     * given file path and creates a student object.
     * @param filepath path to csv file.
     * @return null.
     */
    public final StudentAlumnifile readSingleRow(final String filepath)
                                            throws ExecutionException {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filepath))) {
            reader.readLine(); // Skip the first line
            String[] row = reader.readLine()
                    .split(DynamicProperties.ALUMNI_CSV_SEPARATOR);
            return createStudentFromRow(row);
        } catch (Exception e) {
            ExceptionRoutines.exceptionRoutine(e, gui);
        }
        return null;
    }
}

