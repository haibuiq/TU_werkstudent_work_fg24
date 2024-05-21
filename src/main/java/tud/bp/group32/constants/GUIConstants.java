package tud.bp.group32.constants;

import java.util.List;

public class GUIConstants {
    public static final String TITLE = "FolienGenerator24";
    public static final String VERSION = "v2.0";
    public static final String FILE_VALIDATION_ERROR = "Folgende Fehler sind aufgetreten:";
    public static final String SUCCESS = "Erfolg";
    public static final String ERROR = "Fehler";
    public static final String CHECK_LOG = "Siehe Protokolldatei für mehr Details.";
    public static final String COMPARING = "Dateien werden verglichen...";
    public static final String GROUPING_SORTING = "Ausgabe wird sortiert...";
    public static final String PROPERTIES_EXC_TITLE = "Properties-Datei Fehler";
    public static final String PROPERTIES_READ_EXC = "Fehler beim Lesen der Properties-Datei. Die vorherigen Werte werden verwendet. ";
    public static final String PROPERTIES_DEFAULT_EXC = "Fehler beim Lesen der Default Properties-Datei oder Default Properties-Datei existiert nicht. Die originale Festkodierte Werte werden verwendet. ";
    public static final String PROPERTIES_WRITE_EXC = "Fehler beim Schreiben der Properties-Datei. ";
    
    public static final String PROPERTIES_MISSING_KEYS_HTML(List<String> missingKeys) {
        String out = "<html>Folgende Schl\u00fcssel "
            + "fehlen in der eingegebene Properties-Datei: ";
        out += "<ul>";
        for (int i = 0;
                i < (missingKeys.size() < 10 ? missingKeys.size() : 10); i++) {
            out += "<li>" + missingKeys.get(i) + "</li>";
        }
        if (missingKeys.size() > 10) {
            return out + "</ul>und weitere...</html>";
        } else {
            return out + "</ul></html>";
        }
    }

    public static final String PROPERTIES_MISSING_KEYS(List<String> missingKeys) {
        String out = "<html>Folgende Schl\u00fcssel "
            + "fehlen in der eingegebene Properties-Datei: ";
        for (int i = 0;
                i < (missingKeys.size() < 10 ? missingKeys.size() : 10); i++) {
            out += missingKeys.get(i) + "\n";
        }
        if (missingKeys.size() > 10) {
            out += "und weitere...\n";
        }
        return out;
    }

    public static final String READING(String fileName) {
        return fileName + " wird gelesen...";
    }

    public static final String PREPARING(String element) {
        return element + " wird vorbereitet...";
    }

    public static final String WRITING_OUTPUT(boolean plural, String fileName) {
        return fileName + (plural ? " werden erzeugt..." : " wird erzeugt...");
    }

    public static final String PERCENT_READING(String fileName, int progress) {
        return progress + "% - " + fileName + " wird gelesen...";
    }
    
    public static final String PERCENT_COMPARING(int progress) {
        return progress + "% - Dateien werden verglichen...";
    }
    
    public static final String PERCENT_GROUPING_SORTING(int progress) {
        return progress + "% - Ausgabe wird sortiert...";
    }

    public static final String PERCENT_WRITING_OUTPUT(int progress, String fileName) {
        return progress + "% - " + fileName + " wird erzeugt...";
    }
    
    public static final String SUCCESSFULLY_CREATED (boolean plural, String fileName) {
        return fileName + (plural ? " wurden erfolgreich erzeugt." : " wurde erfolgreich erzeugt" );
    }

    public static String FILE_NOT_FOUND(String fileName, String filePath) {
        if (fileName != null) {
            return fileName + " konnte nicht gefunden werden: " + filePath;
        }
        return "Datei konnte nicht gefunden werden: " + filePath;
    }
    public static String INVALID_PATH(String path) {
        return "Dateipfad ist ung\u00fcltig: " + path;
    }
}
