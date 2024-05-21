package tud.bp.group32.reader;

import static tud.bp.group32.utilities.StudentUtils.deriveAuszeichnung;
import static tud.bp.group32.utilities.StudentUtils.readCellAsString;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import tud.bp.group32.student.StudentFreitagsliste;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.StudentUtils;
import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.constants.ArtOfAbschluss;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.constants.Studiengang;

public class FreitagslisteReader extends XlsxFileReader<StudentFreitagsliste> {
    /**
     * The semester to compare the students' party invitation semester with.
     */
    private String currentSemesterValue = "";

    /**
     * Constructor of the FreitagslisteReader class (with 1 parameter).
     * @param filePath path to the Freitagsliste.
     */
    public FreitagslisteReader(final String filePath) {
        super(filePath, LogConstants.FREITAGSLISTE);
    }

    /**
     * Constructor of the FreitagslisteReader class (with 2 parameters).
     * @param filePath path to the Freitagliste.
     * @param gui a GUI hook for showing a Dialog box with an error message.
     * @param semester Current semester, usually picked from the spinner.
     */
    public FreitagslisteReader(final String filePath,
                               final GUIExceptionHandler gui,
                               final String semester) {
        super(filePath, LogConstants.FREITAGSLISTE, gui);
        this.currentSemesterValue = DynamicProperties.FREITAG_SEMESTER == -1
                                    ? ""
                                    : semester;
    }

    /**
     * Changed into "final" as checkstyle suggests. This class is designed
     * to be extended, but this method did not have a comment explaining
     * how the extension could be safely done. So if the class is not
     * intended to be extended, then this method should either be "final",
     * or marked as "static/final/abstract/empty", or an annotation that
     * allows extension should be added.
     * @param row a (non-header) row of the XLSX file to be read.
     * @return a Student of the Freitagsliste.
     */
    @Override
    protected final StudentFreitagsliste createFromRow(final Row row) {
        // Check if Student is invited to the correct Absolventenfeier
        if (currentSemesterValue != "") {
            var semesterCell = readCellAsString(
                row.getCell(DynamicProperties.FREITAG_SEMESTER));
            if (!semesterCell.contains(currentSemesterValue)) {
                return null;
            }
        }
        String matrikelnr;

        // Ensure that the Matrikelnummer is read correctly,
        // to prevent any type incompatibility issues.
        if (row.getCell(DynamicProperties.ALUMNI_MATRIKELNR)
                .getCellType() == CellType.NUMERIC) {
            matrikelnr =
                String.valueOf((int)
                        row.getCell(DynamicProperties.FREITAG_MATRIKELNR)
                        .getNumericCellValue());
        } else {
            // Cell is null or Blank or String.
            matrikelnr = readCellAsString(
                        row.getCell(DynamicProperties.FREITAG_MATRIKELNR));
        }

        matrikelnr = StudentUtils.cleanMatrikelnr(matrikelnr);

        // Read the main properties
        String nachname            = readCellAsString(row
                .getCell(DynamicProperties.FREITAG_NACHNAME));
        String vorname             = readCellAsString(row
                .getCell(DynamicProperties.FREITAG_VORNAME));
        String abschlussAbkuerzung = readCellAsString(row
                .getCell(DynamicProperties.FREITAG_ABSCHLUSSABKUERZUNG));
        String studiengang         = readCellAsString(row
                .getCell(DynamicProperties.FREITAG_STUDIENGANG));
        boolean doubleDegree       = readCellAsString(row
                .getCell(DynamicProperties.FREITAG_DOUBLEDEGREE))
                .equals(ArtOfAbschluss.DD_SHORT);
        Auszeichnung auszeichnung  = deriveAuszeichnung(readCellAsString(row
                .getCell(DynamicProperties.FREITAG_AUSZEICHNUNG)));

        // In Freitagsliste, Studiengänge are written in short form usually.
        // This method will expand them to their long form.
        studiengang = expandStudiengang(studiengang);

        return new StudentFreitagsliste(matrikelnr, vorname, nachname,
                studiengang, abschlussAbkuerzung, doubleDegree, auszeichnung);
    }

    /**
     * Expands the short form of a Studiengang to it's respective long form.
     * @param shortStudiengang Short form of a Studiengang
     * @return Long form of that Studiengang.
     */
    private String expandStudiengang(final String shortStudiengang) {
        var newStudiengang = StringUtils.deleteWhitespace(shortStudiengang);

        if (newStudiengang.equalsIgnoreCase(Studiengang.ESE_SHORT)) {
            return Studiengang.ESE_FULL;
        } else if (newStudiengang.equalsIgnoreCase(Studiengang.ETIT_SHORT)) {
            return Studiengang.ETIT_FULL;
        } else if (newStudiengang.equalsIgnoreCase(Studiengang.ICE_SHORT)) {
            return Studiengang.ICE_FULL;
        } else if (newStudiengang.equalsIgnoreCase(Studiengang.IST_SHORT)) {
            return Studiengang.IST_FULL;
        } else if (newStudiengang.equalsIgnoreCase(Studiengang.MEC_SHORT)) {
            return Studiengang.MEC_FULL;
        } else if (newStudiengang.equalsIgnoreCase(Studiengang
                .MEDTEC_SHORT)) {
            return Studiengang.MEDTEC_FULL;
        } else {
            return newStudiengang;
        }

    }

}
