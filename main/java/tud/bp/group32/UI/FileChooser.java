package tud.bp.group32.UI;

import tud.bp.group32.constants.AnredeTyp;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.LoggingUtils;

import java.awt.Window;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.logging.Level;


public final class FileChooser {
    private FileChooser() {
        throw new UnsupportedOperationException();
    }
    // +++ CONSTANTS +++
    /**
     * XLSX FileFilter used to ensure that only XLSX Files can be picked
     * by the FileChooser when passed to the FileChooser.
     */
    public static final FileFilter XLSX_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            // Allow only files with a ".xlsx" extension
            return file.isDirectory()
                || file.getName().toLowerCase().endsWith(".xlsx");
        }

        @Override
        public String getDescription() {
            return "xlsx";
        }
    };
    /**
     * CSV FileFilter used to ensure that only CSV Files can be picked
     * by the FileChooser when passed to the FileChooser.
     */
    public static final FileFilter CSV_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            // Allow only files with a ".csv" extension
            return file.isDirectory()
                || file.getName().toLowerCase().endsWith(".csv");
        }

        @Override
        public String getDescription() {
            return "csv";
        }
    };
    /**
     * PPTX FileFilter used to ensure that only PPTX Files can be picked
     * by the FileChooser when passed to the FileChooser.
     */
    public static final FileFilter PPTX_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            // Allow only files with a ".pptx" extension
            return file.isDirectory()
                || file.getName().toLowerCase().endsWith(".pptx");
        }

        @Override
        public String getDescription() {
            return "pptx";
        }
    };
    /**
     * TXT FileFilter used to ensure that only TXT Files can be picked
     * by the FileChooser when passed to the FileChooser.
     */
    public static final FileFilter TXT_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            // Allow only files with a ".txt" extension
            return file.isDirectory()
                || file.getName().toLowerCase().endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "txt";
        }
    };

    // +++ METHODS +++

    /**
     * Wrapper for the chooseFile Method with logging built in.
     * @param owner The GUI Instance to whom the FileChooser belongs.
     * @param fileKind Kind of file that needs to be opened, e.g. "SP015", to be
     *                 appended to the title
     * @param filter File extension filter. Use constants defined in this class
     * @param saveFile True if savePath should be chosen, false if file.
     * @param log      log instance
     * @return Output from <code> chooseFile(String fileKind, FileFilter filter,
     *         boolean saveFile) </code>
     */
    public static String chooseFile(
            final Window owner, final String fileKind, final FileFilter filter,
            final boolean saveFile, final LoggingUtils log) {
        String filePath = chooseFile(owner, fileKind, filter, saveFile);
        if (filePath == null) {
            log.writeInLog(
                    LoggingUtils.ERROR,
                    LogConstants.NO_FILE_CHOSEN);
            return AnredeTyp.NOT_DEFINED;
        }
        if (saveFile) {
            if (!filePath.endsWith(filter.getDescription())) {
                filePath += "." + filter.getDescription();
            }
            log.writeInLog(
                    Level.INFO,
                    LogConstants.SPEICHERORT_AUSGEWAEHLT(fileKind, filePath));
        } else {
            if (filePath.isBlank()) {
                log.writeInLog(
                        LoggingUtils.ERROR,
                        LogConstants.NO_FILE_CHOSEN);
                return AnredeTyp.NOT_DEFINED;
            }
            String actualFileExtension = getFileExtension(filePath);
            String expectedFileExtension = filter.getDescription();
            if (!actualFileExtension.equalsIgnoreCase(expectedFileExtension)) {
                log.writeInLog(
                        LoggingUtils.ERROR,
                        LogConstants.UNEXPECTED_FILE_TYPE(
                            expectedFileExtension, actualFileExtension));
            } else {
                log.writeInLog(
                        Level.INFO,
                        LogConstants.AUSGEWAEHLT(fileKind));
            }
        }
        return filePath;
    }

    // Extracts the Extension from a file path.
    private static String getFileExtension(final String filePath) {
        if (filePath.isBlank()) {
            return AnredeTyp.NOT_DEFINED;
        }
        String[] split = filePath.split("\\.");
        return split[split.length - 1];
    }

    /**
     * Opens a File Chooser dialogue through which a file Path is extracted.
     * @param owner The GUI Instance to whom the FileChooser belongs.
     * @param fileKind Kind of file that needs to be opened, e.g. "SP015", to be
     *                 appended to the title
     * @param filter File extension filter. Use constants defined in this class
     * @param saveFile True if savePath should be chosen, false if file.
     * @return file path of the corresponding file as String (to be used
     *         by all the Reader files)
     */
    public static String chooseFile(
            final Window owner, final String fileKind,
            final FileFilter filter, final boolean saveFile) {
        String filePath = null;
        String currentDirectory = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser();
        int result;
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (filter != null) {
            fileChooser.setFileFilter(filter);
        }
        if (saveFile) {
            fileChooser.setSelectedFile(new File(currentDirectory, fileKind));
            fileChooser.setDialogTitle(
                fileKind + " Name und Speicherort auswählen:");
            result = fileChooser.showSaveDialog(owner);
        } else {
            fileChooser.setCurrentDirectory(new File(currentDirectory));
            fileChooser.setDialogTitle(fileKind + " auswählen:");
            result = fileChooser.showOpenDialog(owner);
        }
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
            if (!GUIUtils.fileExists(filePath) && saveFile) {
                if (!filePath.endsWith(filter.getDescription())) {
                    return filePath + "." + filter.getDescription();
                }
            }
        }
        return filePath;
    }

    /**
     * Wrapper method for chooseFile - without Logging, with Filter.
     * @param fileKind Name of the file to be chosen.
     * @param filter File extension filter. Use constants defined in this class
     * @param saveFile True if savePath should be chosen, false if file.
     * @return Chosen file path
     */
    public static String chooseFile(
            final String fileKind, final FileFilter filter,
            final boolean saveFile) {
        return chooseFile(null, fileKind, filter, saveFile);
    }

    /**
     * Wrapper method for chooseFile - Without Logging.
     * @param fileType Name of the file to be chosen.
     * @param saveFile True if savePath should be chosen, false if file.
     * @return Chosen file path.
     */
    public static String chooseFile(
            final String fileType, final boolean saveFile) {
        return chooseFile(fileType, null, saveFile);
    }

    /**
     * Wrapper method for ChooseFile - without filter.
     * @param fileType Name of the file to be chosen.
     * @param saveFile True if savePath should be chosen, false if file.
     * @param log      Logging instance
     * @return Chosen file path.
     */
    public static String chooseFile(
            final String fileType, final boolean saveFile,
            final LoggingUtils log) {
        return chooseFile(fileType, null, saveFile, log);
    }

    /**
     * Wrapper method for chooseFile - without owner.
     * @param fileKind Name of the file to be chosen.
     * @param filter File extension Filter. Use constants defined in this class
     * @param saveFile True if savePath should be chosen, false if file.
     * @param log      Logging instance.
     * @return Chosen file path.
     */
    public static String chooseFile(
            final String fileKind,
            final FileFilter filter, final boolean saveFile,
            final LoggingUtils log) {
        return chooseFile(null, fileKind, filter, saveFile, log);
    }
}
