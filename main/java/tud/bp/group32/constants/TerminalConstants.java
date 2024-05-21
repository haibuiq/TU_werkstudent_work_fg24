package tud.bp.group32.constants;

public class TerminalConstants {
    public static final String WELCOME = "Willkommen bei FolienGenerator 24. Verwenden Sie die folgenden Befehle, um FG24 zu bedienen: \n";
    public static final String USAGE_DEBUG =
            "sp015 sp015_location                 : Read SP015 file and output it's parsed contents \n" +
            "freitagsliste freitagsliste_location : Read Freitagsliste file and output it's parsed contents \n" +
            "alumnilist alumnilist_location       : Read SP015 file and output it's parsed contents \n" +
            "group integer                        : Separate the given number into groups\n" +
            "generatekontroll input_sp015 input_freitag input_alumni : Generate Kontrolldatei" +
            "                                       A prompt will later open to ask where you want to save it.\n" +
            "exit                                 : Exit the program";
    public static final String USAGE =
            "generatekontroll   : Kontrolldatei erzeugen. Es werden Dialogfenster geöffnet, die Sie bei der \n" +
            "                     Auswahl der Dateien unterstützen.\n" +
            "generatefolien     : Folien aus eine Kontrolldatei erzeugen.\n" +
            "exit               : FolienGenerator24 schließen.";

    public static final String QUOTED_ARGUMENT_RECOGNITION_REGEX = "\\s+(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
}
