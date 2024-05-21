package tud.bp.group32.UI;

import java.awt.Window;

import javax.swing.JOptionPane;

import tud.bp.group32.constants.GUIConstants;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.LoggingUtils;

public final class PropertiesDateiModal extends AbstractModal {
    /**
     * Creates an instance of the PropertiesDateiModal,
     * which serves to allow the users to easily
     * choose a newe location for the properties file.
     * @param owner The GUI instance to which this modal belongs to.
     */
    public PropertiesDateiModal(final Window owner) {
        super(owner);
    }

    protected void browse() {
        LoggingUtils log = LoggingUtils.instanceExists()
                        ? LoggingUtils.getInstance()
                        : null;
        String newPath = FileChooser.chooseFile(
            this, LogConstants.PROTOCOL,
            FileChooser.TXT_FILTER, false, log);
        setInputText(newPath);
    }

    protected boolean changePath() {
        String newPath = getInputText();
        if (GUIUtils.isValidPath(newPath)) {
            LoggingUtils log = LoggingUtils.instanceExists()
                            ? LoggingUtils.getInstance()
                            : null;
            DynamicProperties.loadNewProperties(newPath, log);
            return true;
        } else {
            GUIUtils.showDialog(this, "Fehlermeldung",
                GUIConstants.INVALID_PATH(newPath), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    protected String getModalTitle() {
        return "Properties-Datei";
    }

    protected String getModalContent() {
        return "Neue Properties-Datei eingeben:";
    }
}
