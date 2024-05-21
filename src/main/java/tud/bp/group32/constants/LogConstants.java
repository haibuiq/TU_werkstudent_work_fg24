package tud.bp.group32.constants;

/**
 * Logging constants to be used in logs and in the Main methods
 */
public class LogConstants {
    public static final String PROTOCOL = "Protokolldatei";
    public static final String SP015 = "SP015";
    public static final String FREITAGSLISTE = "Freitagsliste";
    public static final String ALUMNIDATEI = "Alumni-tool Datei";
    public static final String CONTROL_FILE = "Kontrolldatei";
    public static final String PROPERTIES = "Properties-Datei";
    public static final String SLIDE_SET = "Foliensatz";
    public static final String SLIDES = "Folien";
    public static final String BACHELOR_SLIDES = "Bachelors " + SLIDES;
    public static final String MASTER_SLIDES = "Masters " + SLIDES;
    public static final String INTERNATIONAL_SLIDES = "International " + SLIDES;
    public static final String DD_SLIDES = "Double Degree " + SLIDES;
    public static final String TEMPLATE = "PowerPoint Vorlage";
    public static final String FILE_NOT_XLSX = "DATEITYP IST NICHT XLSX!!";
    public static final String FILE_NOT_CSV = "DATEITYP IST NICHT CSV!!";
    public static final String NO_FILE_CHOSEN = "Keine Datei ausgewählt!!";
    public static final String EMAIL = "FolienGenerator@gmail.com";
    public static final String CONTACT_US = "\n\nWenn Sie diesen Fehler nicht erkennen, wenden Sie sich bitte per E-Mail an das FG24-Team."
            +
            "\nBitte beschreiben Sie, wie das Problem aufgetreten ist und fügen Sie diese Datei bei.\n" +
            "E-Mail Adresse: " + EMAIL;
    public static final String PRESS_ENTER = "Drücken Sie die \"Enter\" Taste um FG24 zu schließen.";
    public static final String EXIT_SUCCESS = "FG24 hat die Ausführung erfolgreich beendet.";
    public static final String EXIT_ERROR = "FG24 hat die Ausführung nicht erfolgreich beendet. Prüfen Sie die Protokolldatei, um den Fehler zu sehen.";
    public static final String SUCCESSFUL_SLIDES = "Folien wurden erfolgreich erzeugt!";
    public static final String STATS_SLIDE = "Statistiksfolie";

    public static final String TEMPLATE_NOT_EMPTY = "Vorlage ist nicht leer. Schon vorhandene Folien werden gelöscht...";

    public static final String STUDENT_ALREADY_EXISTS(String matrikelnr, String fileName) {
        return "Student mit Matrikelnr. " + matrikelnr + " ist mehrmals in die " + fileName + " vorhanden.";
    } 

    
    /** 
     * @param students
     * @param abschlussart
     * @return String
     */
    public static String SLIDES_CREATED(int students, String abschlussart) {
        return students + " " + abschlussart + " Folien erzeugt.";
    }

    public static String FILE_NOT_FOUND(String file) {
        return "Datei nicht gefunden: " + file;
    }

    public static String UNEXPECTED_IO_ERROR(String error) {
        if (error == null) {
            error = "";
        } else {
            error = "\n" + error;
        }
        return "Unerwartete IO Fehler aufgetreten." + error;
    }

    public static String UNEXPECTED_ERROR(String error) {
        if (error == null) {
            error = "";
        } else {
            error = "\n" + error;
        }
        return "Unerwartete Fehler aufgetreten: " + error;
    }

    public static String CRASH(String error) {
        return "Ein kritischer Fehler ist aufgetreten und das Programm wurde abgestürzt. \n" + error;
    };

    public static String COUNTED(int inSlides, int notInSlides) {
        int totalStudents = inSlides + notInSlides;
        return "Die Dateien wurden erfolgreich verglichen. Aus " + totalStudents + " werden " + inSlides
                + " in die Folien aufgenommen, " + notInSlides + " aber nicht.";
    }

    public static String SUCCESSFULLY_READ(int numberOfStudents, String type) {
        return numberOfStudents + " Studenten aus " + type + " erfolgreich anerkannt und gelesen.";
    }

    public static String UNEXPECTED_FILE_TYPE(String expected, String actual) {
        return "UNERWARTETE DATEITYP: " + actual + " statt " + expected + " ausgewählt.";
    }

    public static String AUSGEWAEHLT(String file) {
        return file + " ausgewählt.";
    }

    public static String SPEICHERORT_AUSGEWAEHLT(String file, String path) {
        return "Speicherort von " + file + " ausgewählt: " + path;
    }
}
