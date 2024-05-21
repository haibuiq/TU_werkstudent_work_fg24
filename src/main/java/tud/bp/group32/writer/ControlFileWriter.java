package tud.bp.group32.writer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.student.Student;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.ExceptionRoutines;
import tud.bp.group32.utilities.LoggingUtils;
import tud.bp.group32.utilities.StudentUtils;

import javax.swing.JFileChooser;


/**
 * Class to write the control file in .xlsx format.
 * This is a utility class, hence it should not have a public constructor (in
 * compliance with checkstyle).
 */
public class ControlFileWriter {

    /**
     * String for adding xlsx to file Path.
     */
    private static String newFilePath;
    /**
     * Column index of MatrikelNr in the Control File.
     */
    private static final int MATRIKELNR_CELL =
                DynamicProperties.CONTROL_MATRIKELNR;

    /**
     * Column index of Anrede in the Control File.
     */
    private static final int ANREDE_CELL =
                DynamicProperties.CONTROL_ANREDE;

    /**
     * Column index of Nachname in the Control File.
     */
    private static final int NACHNAME_CELL =
                DynamicProperties.CONTROL_NACHNAME;

    /**
     * Column index of Vorname in the Control File.
     */
    private static final int VORNAME_CELL =
                DynamicProperties.CONTROL_VORNAME;

    /**
     * Column index of Abschluss in the Control File.
     */
    private static final int ABSCHLUSS_CELL =
                DynamicProperties.CONTROL_ABSCHLUSS_ART;

    /**
     * Column index of Double Degree in the Control File.
     */
    private static final int DOUBLE_DEGREE_CELL =
                DynamicProperties.CONTROL_DD;

    /**
     * Column index of Abschlussabkürzung in the Control File.
     */
    private static final int ABSCHLUSS_ABK_CELL =
                DynamicProperties.CONTROL_ABSCHLUSS_ABK;

    /**
     * Column index of Studiengang in the Control File.
     */
    private static final int STUDIENGANG_CELL =
                DynamicProperties.CONTROL_STUDIENGANG;

    /**
     * Column index of Abschlussnote in the Control File.
     */
    private static final int ABSCHLUSS_NOTE_CELL =
                DynamicProperties.CONTROL_ABSCHLUSS_NOTE;

    /**
     * Column index of Urteil in the Control File.
     */
    private static final int URTEIL_CELL =
                DynamicProperties.CONTROL_URTEIL;

    /**
     * Column index of Prüfer (Vorsitz) in the Control File.
     */
    private static final int THESIS_VORSITZ_CELL =
                DynamicProperties.CONTROL_PRUEFERIN;

    /**
     * Column index of Abschlussarbeit in the Control File.
     */
    private static final int ABSCHLUSS_THESIS_CELL =
                DynamicProperties.CONTROL_ABSCH_ARBEIT;

    /**
     * Column index of Bestätigte Teilnahme in the Control File.
     */
    private static final int FOLIE_GENERIEREN_CELL =
                DynamicProperties.CONTROL_WILL_HAVE_SLIDE;

    /**
     * Column index of SP015 - Anwesend in the Control File.
     */
    private static final int SP015_ANWESEND_CELL =
                DynamicProperties.CONTROL_IS_IN_SP015;

    /**
     * Column index of Alumniliste - Anwesend in the Control File.
     */
    private static final int ALUMNILISTE_ANWESEND_CELL =
                DynamicProperties.CONTROL_IS_IN_ALUMNI;

    /**
     * Column index of the file path.
     */
    private static final int COMMENT_INDEX = DynamicProperties.CONTROL_COMMENT;

    protected ControlFileWriter() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }


    /**
     * Method used to generate a Control file. This method works
     * without a file chooser and expects the output Path to be given
     * as a parameter. This is a wrapper method that calls the overloaded
     * method without a log.
     * @param filePath file path of the control file.
     * @param controlfileList list used to create control file.
     * @param gui The GUI hook for throwing exceptions.
     */
    public static void writeKontrolldatei(final String filePath,
            final List<StudentControlfile>
            controlfileList,
            final GUIExceptionHandler gui) throws ExecutionException {
        writeKontrolldatei(filePath, controlfileList, gui, null);
    }

    /**
     * Method used to generate a Control file. This method works
     * without a file chooser and expects the output Path to be given
     * as a parameter. This method expects a log instance to be passed on to it.
     * Wrapper method above is used to call this method with a null
     * log instance, which is allowed, but that's what the wrapper exists for.
     * @param filePath file path of the control file.
     * @param controlfileList list used to create control file.
     * @param gui The GUI hook for throwing exceptions.
     * @param log The log instance to be used for logging.
     */
    public static void writeKontrolldatei(final String filePath,
            final List<StudentControlfile>
            controlfileList,
            final GUIExceptionHandler gui,
            final LoggingUtils log) throws ExecutionException {
        DynamicProperties.initProperties(gui);

        if (!filePath.endsWith(".xlsx")) {
            newFilePath = filePath + ".xlsx";
        } else {
            newFilePath = filePath;
        }

        // Creating a table...
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet");
            var style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // This creates the initial header row in the table
            Row row = sheet.createRow(0);
            // This creates the cells in the header row
            setHeaderRows(style, row);
            // creates entries for each student in the HashMap
            Iterator<StudentControlfile> iterator = controlfileList.iterator();
            for (int i = 1; i < controlfileList.size() + 1; i++) {
                if (iterator.hasNext()) {
                    StudentControlfile student = iterator.next();
                    Row newRow = sheet.createRow(i);
                    var matrikelnr = StudentUtils
                                .cleanMatrikelnr(student.getMatrikelnr());
                    newRow.createCell(MATRIKELNR_CELL).setCellValue(matrikelnr);
                    newRow.createCell(ANREDE_CELL)
                            .setCellValue(student.getAnrede());
                    newRow.createCell(NACHNAME_CELL)
                            .setCellValue(student.getNachname());
                    newRow.createCell(VORNAME_CELL)
                            .setCellValue(student.getVorname());
                    newRow.createCell(ABSCHLUSS_CELL)
                            .setCellValue(student.getAbschlussart());
                    newRow.createCell(DOUBLE_DEGREE_CELL)
                            .setCellValue(student.getDoubleDegree()
                                    ? DynamicProperties.JA
                                    : DynamicProperties.NEIN);
                    newRow.createCell(ABSCHLUSS_ABK_CELL)
                            .setCellValue(student.getAbschlussAbkuerzung());
                    newRow.createCell(STUDIENGANG_CELL)
                            .setCellValue(student.getStudiengang());
                    newRow.createCell(ABSCHLUSS_NOTE_CELL)
                            .setCellValue(student.getAbschlussNote());
                    newRow.createCell(URTEIL_CELL)
                            .setCellValue(student.getUrteil());
                    newRow.createCell(THESIS_VORSITZ_CELL)
                            .setCellValue(student.getPrueferIn());
                    newRow.createCell(ABSCHLUSS_THESIS_CELL)
                            .setCellValue(student.getAbschlussarbeit());
                    newRow.createCell(FOLIE_GENERIEREN_CELL)
                            .setCellValue(student.getWillHaveSlide()
                                    ? DynamicProperties.JA
                                    : DynamicProperties.NEIN);
                    newRow.createCell(SP015_ANWESEND_CELL)
                            .setCellValue(student.getIsInSP015()
                                    ? DynamicProperties.JA
                                    : DynamicProperties.NEIN);
                    newRow.createCell(ALUMNILISTE_ANWESEND_CELL)
                            .setCellValue(student.getIsInAlumnifile()
                                    ? DynamicProperties.JA
                                    : DynamicProperties.NEIN);
                    if (matrikelnr.length() != Student.MATRIKELNR_LENGTH) {
                        String out = ("Matrikelnr. " + (
                            matrikelnr.length() < Student.MATRIKELNR_LENGTH
                                ? "zu kurz: " : "zu lang: ")
                                + student.getMatrikelnr());
                        newRow.createCell(COMMENT_INDEX)
                                .setCellValue(newFilePath);
                                if (log != null) {
                                    log.writeInLog(Level.WARNING, out);
                                }
                    }

                }
            }

            // Writing to an Excel file at the specified location
            try (FileOutputStream fileOut = new FileOutputStream(newFilePath)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            ExceptionRoutines.exceptionRoutine(e, gui);
        }
    }

    private static void setHeaderRows(final CellStyle style, final Row row) {
        Cell cell1 = row.createCell(MATRIKELNR_CELL);
        cell1.setCellValue(DynamicProperties.HEADER_MATRIKELNR);
        cell1.setCellStyle(style);

        Cell cell2 = row.createCell(ANREDE_CELL);
        cell2.setCellValue(DynamicProperties.HEADER_ANREDE);
        cell2.setCellStyle(style);

        Cell cell3 = row.createCell(NACHNAME_CELL);
        cell3.setCellValue(DynamicProperties.HEADER_NACHNAME);
        cell3.setCellStyle(style);

        Cell cell4 = row.createCell(VORNAME_CELL);
        cell4.setCellValue(DynamicProperties.HEADER_VORNAME);
        cell4.setCellStyle(style);

        Cell cell5 = row.createCell(ABSCHLUSS_CELL);
        cell5.setCellValue(DynamicProperties.HEADER_ABSCHLUSS);
        cell5.setCellStyle(style);

        Cell cell6 = row.createCell(DOUBLE_DEGREE_CELL);
        cell6.setCellValue(DynamicProperties.HEADER_DD);
        cell6.setCellStyle(style);

        Cell cell7 = row.createCell(ABSCHLUSS_ABK_CELL);
        cell7.setCellValue(DynamicProperties.HEADER_ABSCHLUSS_ABK);
        cell7.setCellStyle(style);

        Cell cell8 = row.createCell(STUDIENGANG_CELL);
        cell8.setCellValue(DynamicProperties.HEADER_STUDIENGANG);
        cell8.setCellStyle(style);

        Cell cell9 = row.createCell(ABSCHLUSS_NOTE_CELL);
        cell9.setCellValue(DynamicProperties.HEADER_ABSCHLUSSNOTE);
        cell9.setCellStyle(style);

        Cell cell10 = row.createCell(URTEIL_CELL);
        cell10.setCellValue(DynamicProperties.HEADER_URTEIL);
        cell10.setCellStyle(style);

        Cell cell11 = row.createCell(THESIS_VORSITZ_CELL);
        cell11.setCellValue(DynamicProperties.HEADER_THESIS_VORSITZ);
        cell11.setCellStyle(style);

        Cell cell12 = row.createCell(ABSCHLUSS_THESIS_CELL);
        cell12.setCellValue(DynamicProperties.HEADER_THESIS);
        cell12.setCellStyle(style);

        Cell cell13 = row.createCell(FOLIE_GENERIEREN_CELL);
        cell13.setCellValue(DynamicProperties.HEADER_WILL_HAVE_SLIDE);
        cell13.setCellStyle(style);

        Cell cell14 = row.createCell(SP015_ANWESEND_CELL);
        cell14.setCellValue(DynamicProperties.HEADER_SP015_PRESENT);
        cell14.setCellStyle(style);

        Cell cell15 = row.createCell(ALUMNILISTE_ANWESEND_CELL);
        cell15.setCellValue(DynamicProperties.HEADER_ALUMNI_PRESENT);
        cell15.setCellStyle(style);

        Cell cell16 = row.createCell(COMMENT_INDEX);
        cell16.setCellValue(DynamicProperties.HEADER_COMMENT);
        cell16.setCellStyle(style);
    }



    /**
     * @param controlfileList list used to create the Kontrolldatei
     */
public static void writer(
        final List<StudentControlfile> controlfileList)
        throws ExecutionException {

    // specify the location of the Kontrolldatei to be created
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("KontrollDatei");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        // Get the selected file
      writeKontrolldatei(fileChooser.getSelectedFile()
              .getAbsolutePath(), controlfileList, null);
    }
}
}
