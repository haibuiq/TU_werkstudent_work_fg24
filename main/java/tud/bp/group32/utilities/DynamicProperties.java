package tud.bp.group32.utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.constants.DefaultProperties;
import tud.bp.group32.constants.GUIConstants;
import tud.bp.group32.constants.LogConstants;

/**
 * A class to handle the dynamic properties of the application.
 */
public class DynamicProperties {

    /**
     * Constructor to prevent instantiation of the class.
     */
    protected DynamicProperties() {
        throw new UnsupportedOperationException();
    }

    private static Properties properties;
    private static GUIExceptionHandler gui;
    private static String IGNORE_SEMESTER_VAL = "egal";

    public static int GROUP_SIZE;
    public static boolean SHOW_BOX_RIGHT_SIDE;

    // Alumnifile Column Names
    public static int ALUMNI_MATRIKELNR;
    public static int ALUMNI_VORNAME;
    public static int ALUMNI_NACHNAME;
    public static String ALUMNI_CSV_SEPARATOR;

    // SP015 Column Names
    public static int SP015_MATRIKELNR;
    public static int SP015_ABSCHLUSSABKUERZUNG;
    public static int SP015_ABSCHLUSSART;
    public static int SP015_ANREDE;
    public static int SP015_NACHNAME;
    public static int SP015_VORNAME;
    public static int SP015_STUDIENGANG;
    public static int SP015_URTEIL;
    public static int SP015_PRUEFERIN;
    public static int SP015_ABSCHLUSSARBEIT;
    public static int SP015_DOUBLEDEGREE;
    public static int SP015_ABSCHLUSSNOTE;

    // Freitagsliste Column Names
    public static int FREITAG_NACHNAME;
    public static int FREITAG_VORNAME;
    public static int FREITAG_MATRIKELNR;
    public static int FREITAG_ABSCHLUSSABKUERZUNG;
    public static int FREITAG_STUDIENGANG;
    public static int FREITAG_DOUBLEDEGREE;
    public static int FREITAG_AUSZEICHNUNG;
    public static int FREITAG_SEMESTER;

    // Kontrolldatei Column Names
    public static int CONTROL_MATRIKELNR;
    public static int CONTROL_ANREDE;
    public static int CONTROL_ABSCHLUSS_ABK;
    public static int CONTROL_ABSCHLUSS_ART;
    public static int CONTROL_VORNAME;
    public static int CONTROL_NACHNAME;
    public static int CONTROL_STUDIENGANG;
    public static int CONTROL_ABSCH_ARBEIT;
    public static int CONTROL_DD;
    public static int CONTROL_PRUEFERIN;
    public static int CONTROL_URTEIL;
    public static int CONTROL_ABSCHLUSS_NOTE;
    public static int CONTROL_WILL_HAVE_SLIDE;
    public static int CONTROL_IS_IN_SP015;
    public static int CONTROL_IS_IN_ALUMNI;
    public static int CONTROL_COMMENT;

    // Generated Controlfile Headers
    public static String HEADER_MATRIKELNR;
    public static String HEADER_ANREDE;
    public static String HEADER_NACHNAME;
    public static String HEADER_VORNAME;
    public static String HEADER_ABSCHLUSS;
    public static String HEADER_DD;
    public static String HEADER_ABSCHLUSS_ABK;
    public static String HEADER_STUDIENGANG;
    public static String HEADER_ABSCHLUSSNOTE;
    public static String HEADER_URTEIL;
    public static String HEADER_THESIS_VORSITZ;
    public static String HEADER_THESIS;
    public static String HEADER_WILL_HAVE_SLIDE;
    public static String HEADER_SP015_PRESENT;
    public static String HEADER_ALUMNI_PRESENT;
    public static String HEADER_COMMENT;

    public static String SLIDES_MAIN_TITLE;
    public static String SLIDES_TITLE_NO;
    public static String SLIDES_TITLE_SG;
    public static String SLIDES_TITLE_MA;
    public static double SLIDES_STUDENT_FONT_SIZE_SM;
    public static double SLIDES_STUDENT_FONT_SIZE_LG;
    public static double SLIDES_GROUPFOTO_FONT_SIZE_SM;
    public static double SLIDES_GROUPFOTO_FONT_SIZE_LG;
    public static double SLIDES_DEGREE_CONTENT_FONT_SIZE;


    public static String JA;
    public static String NEIN;

    private static final String DEFAULT_PROPS = "./default.properties";

    /**
     * A method to initialize the properties for the DynamicProperties class.
     * Uses the default Properties defined in DefaultProperties.java
     * @param guiInstance The GUIExceptionHandler to be used for error handling
     */
    public static void initProperties(final GUIExceptionHandler guiInstance) {
        if (properties == null) {
                tryLoadDefault(guiInstance);
                gui = guiInstance;
                loadProperties();
        }
    }

    private static void tryLoadDefault(final GUIExceptionHandler guiInstance) {
        var props = new Properties();
        try {
            props.load(new FileInputStream(DEFAULT_PROPS));
            parsePropertiesFile(props, null);
            properties = props;
            loadProperties();
        } catch (Exception e) {
            properties = new Properties(DefaultProperties.getDefaults());
            if (gui != null) {
                gui.catchException(
                    GUIConstants.PROPERTIES_EXC_TITLE,
                    GUIConstants.PROPERTIES_DEFAULT_EXC);
            }
        }
    }

    /**
     * A method to load the properties from the properties file,
     * onto the variables.
     * This method is called after a properties file is loaded.
    */
    public static void loadProperties() {
        GROUP_SIZE = Integer.parseInt(properties
                .getProperty("groupSize"));
        SHOW_BOX_RIGHT_SIDE = Boolean.parseBoolean(properties
                .getProperty("kastenAnzeigen"));

        // Alumnifile Column Names
        ALUMNI_MATRIKELNR = StudentUtils.columnNameToIndex(properties
                .getProperty("alumni.spalten.matrikelnr"));
        ALUMNI_VORNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("alumni.spalten.vorname"));
        ALUMNI_NACHNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("alumni.spalten.nachname"));
        ALUMNI_CSV_SEPARATOR = properties
                .getProperty("alumni.spalten.csvSeparator");

        // SP015 Column Names
        SP015_MATRIKELNR = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.matrikelnr"));
        SP015_ABSCHLUSSABKUERZUNG = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.abschlussAbkuerzung"));
        SP015_ABSCHLUSSART = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.abschlussArt"));
        SP015_ANREDE = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.anrede"));
        SP015_NACHNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.nachname"));
        SP015_VORNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.vorname"));
        SP015_STUDIENGANG = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.studiengang"));
        SP015_URTEIL = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.urteil"));
        SP015_PRUEFERIN = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.thesisVorsitz"));
        SP015_ABSCHLUSSARBEIT = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.abschlussArbeit"));
        SP015_DOUBLEDEGREE = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.doubleDegree"));
        SP015_ABSCHLUSSNOTE = StudentUtils.columnNameToIndex(properties
                .getProperty("sp015.spalten.abschlussNote"));

        // Freitagsliste Column Names
        FREITAG_NACHNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.nachname"));
        FREITAG_VORNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.vorname"));
        FREITAG_MATRIKELNR = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.matrikelnr"));
        FREITAG_ABSCHLUSSABKUERZUNG = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.abschlussAbkuerzung"));
        FREITAG_STUDIENGANG = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.studiengang"));
        FREITAG_DOUBLEDEGREE = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.doubleDegree"));
        FREITAG_AUSZEICHNUNG = StudentUtils.columnNameToIndex(properties
                .getProperty("freitag.spalten.auszeichnung"));
        var semValue = properties.getProperty("freitag.spalten.semester");
        FREITAG_SEMESTER = semValue.equalsIgnoreCase(IGNORE_SEMESTER_VAL)
                ? -1
                : StudentUtils.columnNameToIndex(properties
                        .getProperty("freitag.spalten.semester"));

        // Kontrolldatei Column Names
        CONTROL_MATRIKELNR = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.matrikelnr"));
        CONTROL_ANREDE = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.anrede"));
        CONTROL_ABSCHLUSS_ABK = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.abschlussAbk"));
        CONTROL_ABSCHLUSS_ART = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.abschlussArt"));
        CONTROL_VORNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.vorname"));
        CONTROL_NACHNAME = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.nachname"));
        CONTROL_STUDIENGANG = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.studiengang"));
        CONTROL_ABSCH_ARBEIT = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.abschlussArbeit"));
        CONTROL_DD = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.doubleDegree"));
        CONTROL_PRUEFERIN = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.thesisVorsitz"));
        CONTROL_URTEIL = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.urteil"));
        CONTROL_ABSCHLUSS_NOTE = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.abschlussNote"));
        CONTROL_WILL_HAVE_SLIDE = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.folieGenerieren"));
        CONTROL_IS_IN_SP015 = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.sp015Anwesend"));
        CONTROL_IS_IN_ALUMNI = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.alumniAnwesend"));
        CONTROL_COMMENT = StudentUtils.columnNameToIndex(properties
                .getProperty("kontroll.spalten.kommentar"));

        // Generated Controlfile Headers
        HEADER_MATRIKELNR = properties
                .getProperty("kontroll.header.matrikelnr");
        HEADER_ANREDE = properties
                .getProperty("kontroll.header.anrede");
        HEADER_NACHNAME = properties
                .getProperty("kontroll.header.nachname");
        HEADER_VORNAME = properties
                .getProperty("kontroll.header.vorname");
        HEADER_ABSCHLUSS = properties
                .getProperty("kontroll.header.abschluss");
        HEADER_DD = properties
                .getProperty("kontroll.header.doubleDegree");
        HEADER_ABSCHLUSS_ABK = properties
                .getProperty("kontroll.header.abschlussAbk");
        HEADER_STUDIENGANG = properties
                .getProperty("kontroll.header.studiengang");
        HEADER_ABSCHLUSSNOTE = properties
                .getProperty("kontroll.header.abschlussNote");
        HEADER_URTEIL = properties
                .getProperty("kontroll.header.urteil");
        HEADER_THESIS_VORSITZ = properties
                .getProperty("kontroll.header.thesisVorsitz");
        HEADER_THESIS = properties
                .getProperty("kontroll.header.thesis");
        HEADER_WILL_HAVE_SLIDE = properties
                .getProperty("kontroll.header.folieGenerieren");
        HEADER_SP015_PRESENT = properties
                .getProperty("kontroll.header.sp015Anwesend");
        HEADER_ALUMNI_PRESENT = properties
                .getProperty("kontroll.header.alumniAnwesend");
        HEADER_COMMENT = properties
                .getProperty("kontroll.header.kommentar");

        SLIDES_MAIN_TITLE = properties
                .getProperty("folien.hauptTitel");
        SLIDES_TITLE_NO = properties
                .getProperty("folien.titelOhne");
        SLIDES_TITLE_SG = properties
                .getProperty("folien.titelSilber");
        SLIDES_TITLE_MA = properties
                .getProperty("folien.titelGold");

        SLIDES_STUDENT_FONT_SIZE_LG = Double.parseDouble(properties
                .getProperty("folien.studenten.grossSchriftGroesse"));
        SLIDES_STUDENT_FONT_SIZE_SM = Double.parseDouble(properties
                .getProperty("folien.studenten.kleinSchriftGroesse"));
        SLIDES_GROUPFOTO_FONT_SIZE_LG = Double.parseDouble(properties
                .getProperty("folien.gruppenfoto.grossSchriftGroesse"));
        SLIDES_GROUPFOTO_FONT_SIZE_SM = Double.parseDouble(properties
                .getProperty("folien.gruppenfoto.kleinSchriftGroesse"));
        SLIDES_DEGREE_CONTENT_FONT_SIZE = Double.parseDouble(properties
                .getProperty("folien.abschluss.schriftGroesse"));

        JA = properties
                .getProperty("ja");
        NEIN = properties
                .getProperty("nein");
    }

    private static void parsePropertiesFile(
                    final Properties prop, final LoggingUtils log)
                    throws PropertiesException {
                ArrayList<String> missingKeys = new ArrayList<>();
                DefaultProperties.getDefaults().forEach((key, value) -> {
                    if (key instanceof String) {
                        String keyString = (String) key;
                        if (!prop.containsKey(keyString)
                            && !prop.containsKey(key)) {
                            missingKeys.add(keyString);
                        }
                    }
                });
                if (!missingKeys.isEmpty()) {
                    log.writeInLog(Level.WARNING,
                        GUIConstants.PROPERTIES_MISSING_KEYS(missingKeys));
                    throw new PropertiesException(
                        GUIConstants.PROPERTIES_MISSING_KEYS_HTML(missingKeys));
                }
            }

    /**
     * A method to load new properties from a given path.
     * In case the loading fails, the previous properties will be retained.
     * @param path The path to the properties file
     */
    public static void loadNewProperties(
            final String path, final LoggingUtils log) {
        try {
            var newProps = new Properties();
            newProps.load(new FileInputStream(path));
            parsePropertiesFile(newProps, log);
            properties = newProps;
            loadProperties();
        } catch (Exception e) {
            if (e instanceof PropertiesException) {
                if (gui != null) {
                    gui.catchException(
                        GUIConstants.PROPERTIES_EXC_TITLE,
                        e.getMessage());
                }
            } else {
                if (log != null) {
                    log.writeInLog(
                        Level.WARNING, GUIConstants.PROPERTIES_READ_EXC
                        + e.getMessage());
                }
                if (gui != null) {
                    gui.catchException(
                        GUIConstants.PROPERTIES_EXC_TITLE,
                        GUIConstants.PROPERTIES_READ_EXC
                        + GUIConstants.CHECK_LOG);
                }
            }
        }
    }

    /**
     * A method to generate a properties file at a given path.
     * In case the generation fails, an error message will be shown.
     * @param path The path to the properties file
     */
    public static void generatePropertiesFile(final String path) {
        try {
            properties.store(new java.io.FileOutputStream(path), null);
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

    /**
     * A method to get the value of a property from the properties file.
     * @param key The key of the property
     * @return The value of the property
     */
    public static String getValue(final String key) {
        return properties.getProperty(key);
    }

    protected static class PropertiesException extends Exception {
        public PropertiesException(final String message) {
            super(message);
        }
    }
}
