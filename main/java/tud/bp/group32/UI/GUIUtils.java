package tud.bp.group32.UI;

import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tud.bp.group32.utilities.ImageLoader;
import tud.bp.group32.utilities.LoggingUtils;

public final class GUIUtils {
    private GUIUtils() {
        throw new UnsupportedOperationException();
    }
    /**
     * Fetches the icon of the Program from the resources folder.
     * @return Image The icon.
     */
    public static Image getIcon() {
        String iconPath = "images/FG24Logo.png";
        Image icon;
        try {
            icon = ImageIO.read(new ImageLoader()
                    .getResourceAsStream(iconPath));
        } catch (IOException e) {
            LoggingUtils.getInstance().writeInLog(LoggingUtils.ERROR,
                "Icon konnte nicht geladen werden.");
            return null;
        }
        return icon;
    }

    /**
     * A method used to safely update the String and percentage value of
     * the ProgressBars.
     * @param gui The GUI instance whose ProgressBar we want to update.
     * @param folienBar if false, we're talking about the kdProgressBar
     *                  otherwise the folienProgressBar.
     * @param percent The percentage value we want to set the ProgressBar to.
     * @param text the String we want to display on the ProgressBar.
     */
    public static void safeUpdatePercentage(
        final GUIBase gui,
        final boolean folienBar,
        final float percent,
        final String text) {
        if (gui == null) {
            return;
        }
        int roundedPercent = Math.round(percent);
        if (folienBar) {
            gui.setFolienProgressBarString(roundedPercent + "% - " + text);
            gui.setFolienProgressBarPercent(roundedPercent);
        } else {
            gui.setkdProgressBarString(roundedPercent + "% - " + text);
            gui.setkdProgressBarPercent(roundedPercent);
        }

    }

    /**
     * Checks whether a given path is a valid and accessible path.
     * @param path The path to be checked.
     * @return boolean is the path valid and writable/accessible.
     */
    public static boolean isValidPath(final String path) {
        if (path == null || path.isBlank()) {
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            return file.canWrite();
        } else {
            try {
                file.createNewFile();
                file.delete();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * A method that checks whether a file exists in the given path.
     * @param path The path to be checked
     * @return boolean Does the file exist
     */
    public static boolean fileExists(final String path) {
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * Shows a pop-up dialog that highjacks the screen and displays the passed
     * content.
     * @param owner The owner of the dialog that becomes unresponsive.
     * @param title The title of the dialog window.
     * @param content The content of the dialog. Can be of any kind.
     *                Strongly suggested to format it in HTML!
     * @param type The type of Dialog. Use constants from JOptionPane Class.
     */
    public static void showDialog(final Window owner, final String title,
                                  final String content, final int type) {
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new JDialog(owner);
            dialog.setIconImage(getIcon());
            dialog.setModal(true);
            JOptionPane.showMessageDialog(dialog, content, title, type);
        });
    }
}
