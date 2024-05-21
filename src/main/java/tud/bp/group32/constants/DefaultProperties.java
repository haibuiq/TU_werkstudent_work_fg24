package tud.bp.group32.constants;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.logging.Level;


import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.utilities.LoggingUtils;

public class DefaultProperties {
    private static Properties defaults = null;
    private static LinkedHashMap<String, String> map = null;
    public static final int GROUP_SIZE                     =  5;
    public static final boolean SHOW_BOX_RIGHT_SIDE        = true;

    // Alumnifile Column Names
    public static final String ALUMNI_MATRIKELNR           = "P";
    public static final String ALUMNI_VORNAME              = "C";
    public static final String ALUMNI_NACHNAME             = "D";
    public static final String ALUMNI_CSV_SEPARATOR        = ";";

    // SP015 Column Names 
    public static final String SP015_MATRIKELNR            = "A";
    public static final String SP015_ABSCHLUSSABKUERZUNG   = "U";
    public static final String SP015_ABSCHLUSSART          = "S";
    public static final String SP015_ANREDE                = "C";
    public static final String SP015_NACHNAME              = "D";
    public static final String SP015_VORNAME               = "E";
    public static final String SP015_STUDIENGANG           = "V";
    public static final String SP015_URTEIL                = "AL";
    public static final String SP015_PRUEFERIN             = "AQ";
    public static final String SP015_ABSCHLUSSARBEIT       = "AR";
    public static final String SP015_DOUBLEDEGREE          = "R";
    public static final String SP015_ABSCHLUSSNOTE         = "AC";

    // Freitagsliste Column Names 
    public static final String FREITAG_NACHNAME            = "C";
    public static final String FREITAG_VORNAME             = "D";
    public static final String FREITAG_MATRIKELNR          = "E";
    public static final String FREITAG_ABSCHLUSSABKUERZUNG = "F";
    public static final String FREITAG_STUDIENGANG         = "G";
    public static final String FREITAG_DOUBLEDEGREE        = "N";
    public static final String FREITAG_AUSZEICHNUNG        = "O";
    public static final String FREITAG_SEMESTER            = "M";

    // Kontrolldatei Column Names
    public static final String CONTROL_MATRIKELNR          = "A";
    public static final String CONTROL_ANREDE              = "B";
    public static final String CONTROL_NACHNAME            = "C";
    public static final String CONTROL_VORNAME             = "D";
    public static final String CONTROL_ABSCHLUSS_ART       = "E";
    public static final String CONTROL_DD                  = "F";
    public static final String CONTROL_ABSCHLUSS_ABK       = "G";
    public static final String CONTROL_STUDIENGANG         = "H";
    public static final String CONTROL_ABSCHLUSS_NOTE      = "I";
    public static final String CONTROL_URTEIL              = "J";
    public static final String CONTROL_PRUEFERIN           = "K";
    public static final String CONTROL_ABSCH_ARBEIT        = "L";
    public static final String CONTROL_WILL_HAVE_SLIDE     = "M";
    public static final String CONTROL_IS_IN_SP015         = "N";
    public static final String CONTROL_IS_IN_ALUMNI        = "O";
    public static final String CONTROL_COMMENT             = "P";

    // Generated Controlfile Headers
    public static final String HEADER_MATRIKELNR = "Matrikelnummer";
    public static final String HEADER_ANREDE = "Anrede";
    public static final String HEADER_NACHNAME = "Nachname";
    public static final String HEADER_VORNAME = "Vorname";
    public static final String HEADER_ABSCHLUSS = "Abschluss";
    public static final String HEADER_DD = "Double Degree";
    public static final String HEADER_ABSCHLUSS_ABK = "Abschlussabkürzung";
    public static final String HEADER_STUDIENGANG = "Studiengang";
    public static final String HEADER_ABSCHLUSSNOTE = "Abschlussnote";
    public static final String HEADER_URTEIL = "Urteil";
    public static final String HEADER_THESIS_VORSITZ = "Vorsitz";
    public static final String HEADER_THESIS = "Abschlussarbeit";
    public static final String HEADER_WILL_HAVE_SLIDE = "Folie generieren";
    public static final String HEADER_SP015_PRESENT = "SP015 - Anwesend";
    public static final String HEADER_ALUMNI_PRESENT = "Alumniliste - Anwesend";
    public static final String HEADER_COMMENT = "Kommentar";

    public static final String SLIDES_MAIN_TITLE = "Der Fachbereich gratuliert";
    public static final String SLIDES_TITLE_NO = "zum erfolgreichen Abschluss";
    public static final String SLIDES_TITLE_SG = "zum sehr guten Abschluss";
    public static final String SLIDES_TITLE_MA =
                                        "zum Abschluss mit Auszeichnung";
    public static final double SLIDES_STUDENT_FONT_SIZE_SM = 20.;
    public static final double SLIDES_STUDENT_FONT_SIZE_LG = 28.;
    public static final double SLIDES_GROUPFOTO_FONT_SIZE_SM = 23.;
    public static final double SLIDES_GROUPFOTO_FONT_SIZE_LG = 28.;
    public static final double SLIDES_DEGREE_CONTENT_FONT_SIZE = 64.;

    public static final String JA = "JA";
    public static final String NEIN = "NEIN";

    private static final LinkedHashMap<String, String> getLinkedHashMap() {
        if (map != null) {
            return map;
        }
        LinkedHashMap<String,String> newMap = new LinkedHashMap<>();
        newMap.put("groupSize", String.valueOf(GROUP_SIZE));
        newMap.put("kastenAnzeigen", String.valueOf(SHOW_BOX_RIGHT_SIDE));
        newMap.put("alumni.spalten.matrikelnr", ALUMNI_MATRIKELNR);
        newMap.put("alumni.spalten.vorname", ALUMNI_VORNAME);
        newMap.put("alumni.spalten.nachname", ALUMNI_NACHNAME);
        newMap.put("alumni.spalten.csvSeparator", ALUMNI_CSV_SEPARATOR);
        newMap.put("sp015.spalten.matrikelnr", SP015_MATRIKELNR);
        newMap.put("sp015.spalten.abschlussAbkuerzung",
                SP015_ABSCHLUSSABKUERZUNG);
        newMap.put("sp015.spalten.abschlussArt", SP015_ABSCHLUSSART);
        newMap.put("sp015.spalten.anrede", SP015_ANREDE);
        newMap.put("sp015.spalten.nachname", SP015_NACHNAME);
        newMap.put("sp015.spalten.vorname", SP015_VORNAME);
        newMap.put("sp015.spalten.studiengang", SP015_STUDIENGANG);
        newMap.put("sp015.spalten.urteil", SP015_URTEIL);
        newMap.put("sp015.spalten.thesisVorsitz", SP015_PRUEFERIN);
        newMap.put("sp015.spalten.abschlussArbeit", SP015_ABSCHLUSSARBEIT);
        newMap.put("sp015.spalten.doubleDegree", SP015_DOUBLEDEGREE);
        newMap.put("sp015.spalten.abschlussNote", SP015_ABSCHLUSSNOTE);
        newMap.put("freitag.spalten.nachname", FREITAG_NACHNAME);
        newMap.put("freitag.spalten.vorname", FREITAG_VORNAME);
        newMap.put("freitag.spalten.matrikelnr", FREITAG_MATRIKELNR);
        newMap.put("freitag.spalten.abschlussAbkuerzung",
                FREITAG_ABSCHLUSSABKUERZUNG);
        newMap.put("freitag.spalten.studiengang", FREITAG_STUDIENGANG);
        newMap.put("freitag.spalten.doubleDegree", FREITAG_DOUBLEDEGREE);
        newMap.put("freitag.spalten.auszeichnung", FREITAG_AUSZEICHNUNG);
        newMap.put("freitag.spalten.semester", FREITAG_SEMESTER);
        newMap.put("kontroll.spalten.matrikelnr", CONTROL_MATRIKELNR);
        newMap.put("kontroll.spalten.anrede", CONTROL_ANREDE);
        newMap.put("kontroll.spalten.abschlussAbk", CONTROL_ABSCHLUSS_ABK);
        newMap.put("kontroll.spalten.abschlussArt", CONTROL_ABSCHLUSS_ART);
        newMap.put("kontroll.spalten.vorname", CONTROL_VORNAME);
        newMap.put("kontroll.spalten.nachname", CONTROL_NACHNAME);
        newMap.put("kontroll.spalten.studiengang", CONTROL_STUDIENGANG);
        newMap.put("kontroll.spalten.abschlussArbeit",
                CONTROL_ABSCH_ARBEIT);
        newMap.put("kontroll.spalten.doubleDegree", CONTROL_DD);
        newMap.put("kontroll.spalten.thesisVorsitz", CONTROL_PRUEFERIN);
        newMap.put("kontroll.spalten.urteil", CONTROL_URTEIL);
        newMap.put("kontroll.spalten.abschlussNote",
                CONTROL_ABSCHLUSS_NOTE);
        newMap.put("kontroll.spalten.folieGenerieren",
                CONTROL_WILL_HAVE_SLIDE);
        newMap.put("kontroll.spalten.sp015Anwesend", CONTROL_IS_IN_SP015);
        newMap.put("kontroll.spalten.alumniAnwesend", CONTROL_IS_IN_ALUMNI);
        newMap.put("kontroll.spalten.kommentar", CONTROL_COMMENT);
        newMap.put("kontroll.header.matrikelnr", HEADER_MATRIKELNR);
        newMap.put("kontroll.header.anrede", HEADER_ANREDE);
        newMap.put("kontroll.header.nachname", HEADER_NACHNAME);
        newMap.put("kontroll.header.vorname", HEADER_VORNAME);
        newMap.put("kontroll.header.abschluss", HEADER_ABSCHLUSS);
        newMap.put("kontroll.header.doubleDegree", HEADER_DD);
        newMap.put("kontroll.header.abschlussAbk", HEADER_ABSCHLUSS_ABK);
        newMap.put("kontroll.header.studiengang", HEADER_STUDIENGANG);
        newMap.put("kontroll.header.abschlussNote", HEADER_ABSCHLUSSNOTE);
        newMap.put("kontroll.header.urteil", HEADER_URTEIL);
        newMap.put("kontroll.header.thesisVorsitz", HEADER_THESIS_VORSITZ);
        newMap.put("kontroll.header.thesis", HEADER_THESIS);
        newMap.put("kontroll.header.folieGenerieren",
                HEADER_WILL_HAVE_SLIDE);
        newMap.put("kontroll.header.sp015Anwesend", HEADER_SP015_PRESENT);
        newMap.put("kontroll.header.alumniAnwesend", HEADER_ALUMNI_PRESENT);
        newMap.put("kontroll.header.kommentar", HEADER_COMMENT);
        newMap.put("folien.hauptTitel", SLIDES_MAIN_TITLE);
        newMap.put("folien.titelOhne", SLIDES_TITLE_NO);
        newMap.put("folien.titelSilber", SLIDES_TITLE_SG);
        newMap.put("folien.titelGold", SLIDES_TITLE_MA);
        newMap.put("folien.studenten.grossSchriftGroesse",
                String.valueOf(SLIDES_STUDENT_FONT_SIZE_LG));
        newMap.put("folien.studenten.kleinSchriftGroesse",
                String.valueOf(SLIDES_STUDENT_FONT_SIZE_SM));
        newMap.put("folien.gruppenfoto.grossSchriftGroesse",
                String.valueOf(SLIDES_GROUPFOTO_FONT_SIZE_LG));
        newMap.put("folien.gruppenfoto.kleinSchriftGroesse",
                String.valueOf(SLIDES_GROUPFOTO_FONT_SIZE_SM));
        newMap.put("folien.abschluss.schriftGroesse",
                String.valueOf(SLIDES_DEGREE_CONTENT_FONT_SIZE));
        newMap.put("ja", JA);
        newMap.put("nein", NEIN);
        map = newMap;
        return newMap;
    }

    public static final Properties getDefaults() {
        if (defaults != null) {
            return defaults;
        }
        Properties properties = new Properties();
        properties.setProperty("groupSize", String.valueOf(GROUP_SIZE));
        properties.setProperty("kastenAnzeigen", String.valueOf(
                SHOW_BOX_RIGHT_SIDE));
        properties.setProperty("alumni.spalten.matrikelnr",
                ALUMNI_MATRIKELNR);
        properties.setProperty("alumni.spalten.vorname", ALUMNI_VORNAME);
        properties.setProperty("alumni.spalten.nachname", ALUMNI_NACHNAME);
        properties.setProperty("alumni.spalten.csvSeparator",
                ALUMNI_CSV_SEPARATOR);
        properties.setProperty("sp015.spalten.matrikelnr",
                SP015_MATRIKELNR);
        properties.setProperty("sp015.spalten.abschlussAbkuerzung",
                SP015_ABSCHLUSSABKUERZUNG);
        properties.setProperty("sp015.spalten.abschlussArt",
                SP015_ABSCHLUSSART);
        properties.setProperty("sp015.spalten.anrede", SP015_ANREDE);
        properties.setProperty("sp015.spalten.nachname", SP015_NACHNAME);
        properties.setProperty("sp015.spalten.vorname", SP015_VORNAME);
        properties.setProperty("sp015.spalten.studiengang",
                SP015_STUDIENGANG);
        properties.setProperty("sp015.spalten.urteil", SP015_URTEIL);
        properties.setProperty("sp015.spalten.thesisVorsitz",
                SP015_PRUEFERIN);
        properties.setProperty("sp015.spalten.abschlussArbeit",
                SP015_ABSCHLUSSARBEIT);
        properties.setProperty("sp015.spalten.doubleDegree",
                SP015_DOUBLEDEGREE);
        properties.setProperty("sp015.spalten.abschlussNote",
                SP015_ABSCHLUSSNOTE);
        properties.setProperty("freitag.spalten.nachname",
                FREITAG_NACHNAME);
        properties.setProperty("freitag.spalten.vorname", FREITAG_VORNAME);
        properties.setProperty("freitag.spalten.matrikelnr",
                FREITAG_MATRIKELNR);
        properties.setProperty("freitag.spalten.abschlussAbkuerzung",
                FREITAG_ABSCHLUSSABKUERZUNG);
        properties.setProperty("freitag.spalten.studiengang",
                FREITAG_STUDIENGANG);
        properties.setProperty("freitag.spalten.doubleDegree",
                FREITAG_DOUBLEDEGREE);
        properties.setProperty("freitag.spalten.auszeichnung",
                FREITAG_AUSZEICHNUNG);
        properties.setProperty("freitag.spalten.semester",
                FREITAG_SEMESTER);
        properties.setProperty("kontroll.spalten.matrikelnr",
                CONTROL_MATRIKELNR);
        properties.setProperty("kontroll.spalten.anrede", CONTROL_ANREDE);
        properties.setProperty("kontroll.spalten.abschlussAbk",
                CONTROL_ABSCHLUSS_ABK);
        properties.setProperty("kontroll.spalten.abschlussArt",
                CONTROL_ABSCHLUSS_ART);
        properties.setProperty("kontroll.spalten.vorname", CONTROL_VORNAME);
        properties.setProperty("kontroll.spalten.nachname",
                CONTROL_NACHNAME);
        properties.setProperty("kontroll.spalten.studiengang",
                CONTROL_STUDIENGANG);
        properties.setProperty("kontroll.spalten.abschlussArbeit",
                CONTROL_ABSCH_ARBEIT);
        properties.setProperty("kontroll.spalten.doubleDegree", CONTROL_DD);
        properties.setProperty("kontroll.spalten.thesisVorsitz",
                CONTROL_PRUEFERIN);
        properties.setProperty("kontroll.spalten.urteil", CONTROL_URTEIL);
        properties.setProperty("kontroll.spalten.abschlussNote",
                CONTROL_ABSCHLUSS_NOTE);
        properties.setProperty("kontroll.spalten.folieGenerieren",
                CONTROL_WILL_HAVE_SLIDE);
        properties.setProperty("kontroll.spalten.sp015Anwesend",
                CONTROL_IS_IN_SP015);
        properties.setProperty("kontroll.spalten.alumniAnwesend",
                CONTROL_IS_IN_ALUMNI);
        properties.setProperty("kontroll.spalten.kommentar",
                CONTROL_COMMENT);
        properties.setProperty("kontroll.header.matrikelnr",
                HEADER_MATRIKELNR);
        properties.setProperty("kontroll.header.anrede", HEADER_ANREDE);
        properties.setProperty("kontroll.header.nachname", HEADER_NACHNAME);
        properties.setProperty("kontroll.header.vorname", HEADER_VORNAME);
        properties.setProperty("kontroll.header.abschluss",
                HEADER_ABSCHLUSS);
        properties.setProperty("kontroll.header.doubleDegree", HEADER_DD);
        properties.setProperty("kontroll.header.abschlussAbk",
                HEADER_ABSCHLUSS_ABK);
        properties.setProperty("kontroll.header.studiengang",
                HEADER_STUDIENGANG);
        properties.setProperty("kontroll.header.abschlussNote",
                HEADER_ABSCHLUSSNOTE);
        properties.setProperty("kontroll.header.urteil", HEADER_URTEIL);
        properties.setProperty("kontroll.header.thesisVorsitz",
                HEADER_THESIS_VORSITZ);
        properties.setProperty("kontroll.header.thesis", HEADER_THESIS);
        properties.setProperty("kontroll.header.folieGenerieren",
                HEADER_WILL_HAVE_SLIDE);
        properties.setProperty("kontroll.header.sp015Anwesend",
                HEADER_SP015_PRESENT);
        properties.setProperty("kontroll.header.alumniAnwesend",
                HEADER_ALUMNI_PRESENT);
        properties.setProperty("kontroll.header.kommentar", HEADER_COMMENT);
        properties.setProperty("folien.hauptTitel", SLIDES_MAIN_TITLE);
        properties.setProperty("folien.titelOhne", SLIDES_TITLE_NO);
        properties.setProperty("folien.titelSilber", SLIDES_TITLE_SG);
        properties.setProperty("folien.titelGold", SLIDES_TITLE_MA);
        properties.setProperty("folien.studenten.grossSchriftGroesse",
                String.valueOf(SLIDES_STUDENT_FONT_SIZE_LG));
        properties.setProperty("folien.studenten.kleinSchriftGroesse",
                String.valueOf(SLIDES_STUDENT_FONT_SIZE_SM));
        properties.setProperty("folien.gruppenfoto.grossSchriftGroesse",
                String.valueOf(SLIDES_GROUPFOTO_FONT_SIZE_LG));
        properties.setProperty("folien.gruppenfoto.kleinSchriftGroesse",
                String.valueOf(SLIDES_GROUPFOTO_FONT_SIZE_SM));
        properties.setProperty("folien.abschluss.schriftGroesse",
                String.valueOf(SLIDES_DEGREE_CONTENT_FONT_SIZE));
        properties.setProperty("ja", JA);
        properties.setProperty("nein", NEIN);
        defaults = properties;
        return properties;
    }

    private static final void writer(String path) throws Exception {
        String[] previous = null;
        try (PrintStream writer = new PrintStream(path)) {
            var it = getLinkedHashMap().entrySet().iterator();
            while (it.hasNext()) {
                String next = it.next().toString();

                // Add an empty line between categories
                String[] split = next.split("\\.");
                if (previous != null
                        && split.length > 1
                        && previous.length > 1
                        && !(split[0].equals(previous[0])
                            && split[1].equals(previous[1]))) {
                    writer.print("\n");
                }

                writer.println(next);
                previous = split;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static final void generateDefaultPropertiesFile(
            GUIExceptionHandler gui, String path) {
        try {
            writer(path);
            if (gui != null) {
                gui.showInformation(GUIConstants.SUCCESS,
                GUIConstants.SUCCESSFULLY_CREATED(
                    false,
                           LogConstants.PROPERTIES));
            }
        } catch (Exception e) {
            LoggingUtils.getInstance().writeInLog(
                Level.WARNING,
                GUIConstants.PROPERTIES_WRITE_EXC + e.getMessage());
            if (gui != null) {
                gui.catchException(
                    GUIConstants.PROPERTIES_EXC_TITLE,
                    GUIConstants.PROPERTIES_WRITE_EXC
                    + GUIConstants.CHECK_LOG);
            }
        }
    }
}
