package tud.bp.group32.UI;

public interface GUIExceptionHandler {
    /**
     * A GUI hook for showing a Dialog box with an error message.
     * Allows for easily catching exceptions in the GUI Level,
     * thrown from the lower Logic levels.
     * @param title The Title of the dialog box.
     * @param content The content of the dialog box (ideally in HTML format).
     */
    void catchException(String title, String content);
    /**
     * A GUI hook for showing a Dialog box with an informative message.
     * Allows for easily displaying messages in the GUI Level.
     * @param title The title of the dialog box.
     * @param information The content of the dialog box (Can be in HTML format)
     */
    void showInformation(String title, String information);
}
