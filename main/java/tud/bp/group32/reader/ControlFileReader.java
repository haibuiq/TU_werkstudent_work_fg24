package tud.bp.group32.reader;

import static tud.bp.group32.utilities.StudentUtils.deriveAuszeichnung;
import static tud.bp.group32.utilities.StudentUtils.readCellAsString;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.constants.AnredeTyp;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.DynamicProperties;

/**
 * Class designated to reading the Control File.
 */
public final class ControlFileReader
        extends XlsxFileReader<StudentControlfile> {

    /**
     * Constructor for creating an instance of the ControlfileReader.
     * @param filePath The path to the Control File.
     */
    public ControlFileReader(final String filePath) {
        super(filePath, LogConstants.CONTROL_FILE);
    }

    @Override
    protected StudentControlfile createFromRow(final Row row) {

        String matrikelnum;
        // if cell Null
        if (row.getCell(DynamicProperties.CONTROL_MATRIKELNR) == null) {
            matrikelnum = AnredeTyp.NOT_DEFINED;
        } else if (row.getCell(DynamicProperties.CONTROL_MATRIKELNR)
                .getCellType() == CellType.NUMERIC) {
            matrikelnum = String.valueOf(
                    (int) row.getCell(DynamicProperties.CONTROL_MATRIKELNR)
                            .getNumericCellValue());
        } else { // if cell BLANK
            matrikelnum = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_MATRIKELNR));
        }

        String anrede = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_ANREDE));
        String abschAbkrz = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_ABSCHLUSS_ABK));
        String abschArt = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_ABSCHLUSS_ART));
        String vorName = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_VORNAME));
        String nachName = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_NACHNAME));
        String studgang = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_STUDIENGANG));
        String abschArbeit = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_ABSCH_ARBEIT));
        boolean doubleDeg = determineBooleanFromCell(
                row.getCell(DynamicProperties.CONTROL_DD));
        String pruef = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_PRUEFERIN));
        Auszeichnung auszeich = deriveAuszeichnung(
                readCellAsString(
                        row.getCell(DynamicProperties.CONTROL_URTEIL)));
        String abschNote = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_ABSCHLUSS_NOTE));
        String urteil = readCellAsString(
                row.getCell(DynamicProperties.CONTROL_URTEIL));

        boolean willHaveSlide;
        // if cell Null
        willHaveSlide = determineBooleanFromCell(
                row.getCell(DynamicProperties.CONTROL_WILL_HAVE_SLIDE));

        return new StudentControlfile(
            matrikelnum, anrede, abschAbkrz, abschArt,
            vorName, nachName, studgang, abschArbeit,
            doubleDeg, pruef, auszeich, willHaveSlide, abschNote, urteil);
    }

    private boolean determineBooleanFromCell(final Cell cell) {
        if (cell == null) {
            return false;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue() == 1;
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().equals(DynamicProperties.JA);
        }
        return false;
    }

}
