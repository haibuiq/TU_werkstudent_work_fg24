package tud.bp.group32.reader;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import tud.bp.group32.student.StudentSP015;
import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.StudentUtils;

import static tud.bp.group32.utilities.StudentUtils.deriveAuszeichnung;
import static tud.bp.group32.utilities.StudentUtils.readCellAsString;

import java.util.Objects;

/**
 * Class designated to reading the SP015 File.
 */
public class SP015Reader extends XlsxFileReader<StudentSP015> {
    /**
     * Constructor for creating an instance without a hook to GUI.
     * This should only be used in cases where the program is run
     * in CLI Mode.
     * @param filePath The path to the SP015 File.
     */
    public SP015Reader(final String filePath) {
        super(filePath, LogConstants.SP015);
    }

    /**
     * Constructor for creating an instance with a hook to the GUI.
     * This should only be used in cases where the program is run
     * in GUI Mode.
     * @param filePath The path to the SP015 File.
     * @param gui The GUI hook to be used for throwing errors.
     */
    public SP015Reader(final String filePath, final GUIExceptionHandler gui) {
        super(filePath, LogConstants.SP015, gui);
    }

    /**
     * Decides whether a student is a Double Degree student by
     * checking the Degree Type column code, whether it matches
     * with the Double Degree code which as of 2024-02-01 is "5M".
     * @param code The code read from the column.
     * @return True if the student is a Double Degree Student.
     */
    private boolean deriveDD(final String code) {
        return Objects.equals(code, "5M");
    }

    @Override
    protected final StudentSP015 createFromRow(final Row row) {
        // Auszeichnung is derived from Urteil

        String matrikelnr;

        var matrikelnrCell = row.getCell(DynamicProperties.SP015_MATRIKELNR);
        if (row.getCell(DynamicProperties.SP015_MATRIKELNR)
                .getCellType() == CellType.NUMERIC) {
            var numericValue = (int) matrikelnrCell.getNumericCellValue();
            matrikelnr = String.valueOf(numericValue);
        } else {
            matrikelnr = readCellAsString(matrikelnrCell);
        }

        matrikelnr = StudentUtils.cleanMatrikelnr(matrikelnr);

        String anrede = readCellAsString(
            row.getCell(DynamicProperties.SP015_ANREDE));
        String abschlussAbkuerzung = readCellAsString(
            row.getCell(DynamicProperties.SP015_ABSCHLUSSABKUERZUNG));
        String abschlussart = readCellAsString(
            row.getCell(DynamicProperties.SP015_ABSCHLUSSART));
        String nachname = readCellAsString(
            row.getCell(DynamicProperties.SP015_NACHNAME));
        String vorname = readCellAsString(
            row.getCell(DynamicProperties.SP015_VORNAME));
        String studiengang = readCellAsString(
            row.getCell(DynamicProperties.SP015_STUDIENGANG));
        String prueferin = readCellAsString(
            row.getCell(DynamicProperties.SP015_PRUEFERIN));
        String abschlussArbeit = readCellAsString(
            row.getCell(DynamicProperties.SP015_ABSCHLUSSARBEIT));
        String abschlussNote = readCellAsString(
            row.getCell(DynamicProperties.SP015_ABSCHLUSSNOTE));
        // In SP015, DDs are signified by a 5M in the column R.
        boolean doubleDegree = deriveDD(
            readCellAsString(row.getCell(
                                DynamicProperties.SP015_DOUBLEDEGREE)));
        String urteil = readCellAsString(
            row.getCell(DynamicProperties.SP015_URTEIL));
        Auszeichnung auszeichnung = deriveAuszeichnung(urteil);

        return new StudentSP015(
                matrikelnr,
                anrede,
                abschlussAbkuerzung,
                abschlussart,
                vorname,
                nachname,
                studiengang,
                abschlussArbeit,
                doubleDegree,
                prueferin,
                auszeichnung,
                abschlussNote,
                urteil);
    }
}
