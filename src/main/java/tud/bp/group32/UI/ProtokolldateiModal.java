package tud.bp.group32.UI;

import java.awt.Window;

import javax.swing.JOptionPane;

import tud.bp.group32.constants.GUIConstants;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.LoggingUtils;

/**
 * @author Amar Ademi
 */
public final class ProtokolldateiModal extends AbstractModal {
    /**
     * Creates an instance of the ProtokolldateiModal,
     * which serves to allow the users to easily
     * choose a new save location for the log file.
     * @param owner The GUI instance to which this modal belongs to.
     */
    public ProtokolldateiModal(final Window owner) {
        super(owner);
    }

    protected void browse() {
        LoggingUtils log = LoggingUtils.getInstance();
        String newPath = FileChooser.chooseFile(
            this, LogConstants.PROTOCOL,
            FileChooser.TXT_FILTER, true, log);
        setInputText(newPath);
    }

    protected boolean changePath() {
        String newPath = getInputText();
        if (GUIUtils.isValidPath(newPath)) {
            LoggingUtils log = LoggingUtils.getInstance();
            log.setLoggerLocation(newPath);
            return true;
        } else {
            GUIUtils.showDialog(this, "Fehlermeldung",
                GUIConstants.INVALID_PATH(newPath), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    protected String getModalTitle() {
        return "Protokolldatei Speicherort";
    }

    protected String getModalContent() {
        return "Protokolldatei Speicherort eingeben:";
    }
}
