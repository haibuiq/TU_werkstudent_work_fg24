package tud.bp.group32.reader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.student.Student;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.ExceptionRoutines;
import tud.bp.group32.utilities.LoggingUtils;
import tud.bp.group32.utilities.StudentUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Generic Abstract class to read an XLSX File conforming to the Student
 * Interface and it's implementations.
 * @param <T> Subtype of Student, refers to the Type of the
 *            "Saved-Student-Objects" as Rows in the Xlsx File
 *            (for example: StudentControlfile or StudentSP015...)
 */
public abstract class XlsxFileReader<T extends Student> {
    /**
     * The path to the file to be read.
     */
    private String filePath;
    /**
     * The name of the file type to be used in Strings like Titles.
     */
    private String type;
    /**
     * A GUI hook for showing a Dialog box with an error message.
     */
    private final GUIExceptionHandler gui;

    /**
     * A constructor for the XLSXFileReader abstract class which sets
     * the filePath and the fileType, although setting gui to null, thus
     * assuming there's no GUI instance, and we're working in CLI Mode.
     * @param filePathParam The path to the file to be read.
     * @param typeParam The name of the file type to be used
     *                  in Strings like Titles.
     */
    public XlsxFileReader(final String filePathParam, final String typeParam) {
        this.filePath = filePathParam;
        this.type = typeParam;
        this.gui = null;
        DynamicProperties.initProperties(null);
    }

    /**
     * A constructor for the XLSXFileReader abstract class which sets
     * the filePath and the fileType, as well as a GUI hook.
     * @param filePathParam The path to the file to be read.
     * @param typeParam The name of the file type to be used
     *                  in Strings like Titles.
     * @param guiParam The GUI hook for Exception Throwing
     */
    public XlsxFileReader(final String filePathParam, final String typeParam,
                          final GUIExceptionHandler guiParam) {
        this.filePath = filePathParam;
        this.type = typeParam;
        this.gui = guiParam;
        DynamicProperties.initProperties(guiParam);
    }

    /**
     * Skeleton Method for reading all Rows in the XlsxFile.
     *
     * @return List containing all the Student Objects
     *         (of a specific Student Type T) in the File.
     * @throws ExecutionException Execution Exception to be thrown.
     */
    public final List<T> readAll() throws ExecutionException {
        List<T> list = new ArrayList<T>();
        // Using try-with-resources to automatically close the resources,
        // after any exit way of the try-catch block.
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            // Assuming there is only one sheet in the workbook
            Sheet sheet = workbook.getSheetAt(0);
            // Iterate through rows
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Create an Object from a Row and add it to List
                T t = createFromRow(row);
                // Header should not be included
                if (t != null && StudentUtils.isNumeric(t.getMatrikelnr())) {
                    list.add(t);
                }
            }
        } catch (Exception exc) {
            ExceptionRoutines.exceptionRoutine(exc, gui);
        }
        return list;
    }

    /**
     * Wrapper Method for reading all the rows and logging the result.
     * @param log Logger instance
     * @return The list of students
     * @throws ExecutionException Interruption Exception from readAll.
     */
    public final List<T> readAll(final LoggingUtils log)
            throws ExecutionException {
        List<T> list = this.readAll();
        log.writeInLog(
            Level.INFO,
            LogConstants.SUCCESSFULLY_READ(list.size(), this.type));
        return list;
    }

    /**
     * Wrapper Method for reading all the rows as a HashMap
     * and logging the result.
     * @param log Logger instance
     * @return The Hash Map of students
     * @throws ExecutionException Interruption Exception from readAll.
     */
    public final HashMap<String, T> readAllAsHashMap(final LoggingUtils log)
                                            throws ExecutionException {
        HashMap<String, T> map = this.readAllAsHashMap();
        log.writeInLog(
            Level.INFO,
            LogConstants.SUCCESSFULLY_READ(map.size(), this.type));
        return map;
    }

    /**
     * This method reads all the student in the XlsxFile
     * and returns a Student HashMap with MatrikrlNr as key.
     * @return HashMap of Student with MatrikrlNr as key.
     * @throws ExecutionException Interruption Exception from readAll.
     */
    public final HashMap<String, T> readAllAsHashMap()
                            throws ExecutionException {
        HashMap<String, T> map = new HashMap<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                T student = createFromRow(row);
                if (student == null) {
                    continue;
                }
                String matrikelnr = student.getMatrikelnr();
                if (StudentUtils.isNumeric(matrikelnr)) {
                    if (map.containsKey(matrikelnr)) {
                        LoggingUtils.getInstance().writeInLog(
                            Level.WARNING,
                            LogConstants.STUDENT_ALREADY_EXISTS(
                                matrikelnr, type));
                        if (Student.TAKE_DATA_FROM_FIRST_ENTRY) {
                            continue;
                        }
                    }
                    map.put(matrikelnr, student);
                }
            }
        } catch (Exception exc) {
            ExceptionRoutines.exceptionRoutine(exc, gui);
        }

        return map;
    }

    /**
     * Abstract method to provide how each row should be read from a specific
     * XLSX file type. To be implemented in the subclasses.
     *
     * @param row A (non-header) row of the XLSX file to be read.
     * @return Object of Type T (Specific Subclass of Student),
     *         holding all the values of the read row.
     */
    protected abstract T createFromRow(Row row);
    /**
     * This Method reads all the Students in the XlsxFile that satisfy
     * the given predicate (Filter).
     * @param pred from Type Predicate<T>, the predicate which is checked for.
     * @return List of the Students of Type T, that satisfy the predicate.
     * @throws ExecutionException Interruption Exception from readAll.
     */
    public final List<T> readAllWithFilter(final Predicate<T> pred)
            throws ExecutionException {
        return readAll().stream().filter(pred).collect(Collectors.toList());
    }

    /**
     * Getter for attribute filePath.
     * @return attribute filePath.
     */
    public final String getFilePath() {
        return filePath;
    }
}

