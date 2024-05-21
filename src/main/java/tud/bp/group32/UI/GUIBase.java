package tud.bp.group32.UI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import net.miginfocom.swing.MigLayout;
import tud.bp.group32.Main;
import tud.bp.group32.constants.DefaultProperties;
import tud.bp.group32.constants.GUIConstants;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.LoggingUtils;

/**
 * The Main GUI Frame Class. This class holds all the components and most of
 * the logic behind the GUI of FG24.
 * @author Amar Ademi
 */
public class GUIBase extends JFrame implements GUIExceptionHandler {
    /**
     * Index for the Summer Semester entry in the Semesterpicker.
     */
    static final int SUMMER_SEMESTER_IDX = 1;
    /**
     * Index for the Winter Semester entry in the Semesterpicker.
     */
    static final int WINTER_SEMESTER_IDX = 0;
    /**
     * Keeps track of whether FG24 is currently running some process.
     */
    private boolean busy = false;
    /**
     * Font Size of the main titles in both tabs.
     */
    private final int mainTitleFontSize = 20;

    /**
     * Creates an instance of the GUI Program.
     * @throws ClassNotFoundException Possible LookAndFeel Exception
     *                                         - should never happen.
     * @throws InstantiationException          Same as above.
     * @throws IllegalAccessException          Same as above.
     * @throws UnsupportedLookAndFeelException Same as above.
     * @throws IOException
     */
    public GUIBase() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException, IOException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setIconImage(GUIUtils.getIcon());
        initComponents();
        setSemesterProperties();
        initMenuBar();
        setResizable(false);
        setTitle(GUIConstants.TITLE + " " + GUIConstants.VERSION);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // Set the frame to be visible
        setVisible(true);
        DynamicProperties.initProperties(this);
    }

    @Override
    public final void catchException(final String title, final String content) {
        GUIUtils.showDialog(this, title, content, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public final void showInformation(final String title,
                                      final String information) {
        GUIUtils.showDialog(this, title,
                information,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method used to update this instance's Kontrolldatei Progressbar String.
     * This method is to be used in conjuction with
     * <code>GUIUtils.safeUpdatePercentage()</code>.
     * @param s The string that should be set in the KD Progressbar.
     */
    public void setkdProgressBarString(final String s) {
        SwingUtilities.invokeLater(() -> {
            kdProgressBar.setString(s);
        });
    }

    /**
     * Method used to update this instance's Kontrolldatei Progressbar Percent.
     * @param p int The percentage value to set the KD Progressbar to.
     */
    public void setkdProgressBarPercent(final int p) {
        SwingUtilities.invokeLater(() -> {
            kdProgressBar.setValue(p);
        });
    }

    /**
     * Method used to set this instance's KD Progressbar to visible.
     * Side effect to that is the rest of the GUI becomes unusable (disabled).
     * This is intended behavior as the visibility of the Progressbar
     * indicates that FG24 is currently busy and is generating something.
     * @param b boolean Value to set the visibility to.
     */
    public void setkdProgressBarVisible(final boolean b) {
        SwingUtilities.invokeLater(() -> {
            kdGenerateButton.setVisible(!b);
            kdProgressBar.setVisible(b);
            toggleProgramBusy(b);
        });
    }

    /**
     * Gets the current semester from the Spinner and deducer in a formatted
     * String.
     * @return The current semester from the spinner.
     */
    public String getSemester() {
        final String soSe = "SoSe";
        final String wiSe = "WiSe";
        if (this.kdWiSoPicker.getSelectedIndex() == SUMMER_SEMESTER_IDX) {
            return soSe + " " + String.valueOf(kdSemYearSpinner.getValue());
        } else { // Assuming it's WiSe
            return wiSe + " " + kdSemYearSpinner.getValue()
                    + "/"
                    + kdSemYearDeducer.getText();
        }
    }

    /**
     * Method used to update this instance's FolienGen Progressbar String.
     * This method is to be used in conjuction with
     * <code>GUIUtils.safeUpdatePercentage()</code>.
     * @param s The string that should be set in the Folien Progressbar.
     */
    public void setFolienProgressBarString(final String s) {
        SwingUtilities.invokeLater(() -> {
            folienProgressBar.setString(s);
        });
    }

    /**
     * Method used to update this instance's FolienGen Progressbar Percent.
     * @param p int The percentage value to set the Folien Progressbar to.
     */
    public void setFolienProgressBarPercent(final int p) {
        SwingUtilities.invokeLater(() -> {
            folienProgressBar.setValue(p);
        });
    }

    /**
     * Method used to set this instance's Folien Progressbar to visible.
     * Side effect to that is the rest of the GUI becomes unusable (disabled).
     * This is intended behavior as the visibility of the Progressbar
     * indicates that FG24 is currently busy and is generating something.
     * @param b boolean Value to set the visibility to.
     */
    public void setFolienProgressBarVisible(final boolean b) {
        SwingUtilities.invokeLater(() -> {
            folienGenerateButton.setVisible(!b);
            folienProgressBar.setVisible(b);
            toggleProgramBusy(b);
        });
    }

    /**
     * Toggles the program between a Busy state and a normal state.
     * In the busy state, all Elements in both tabs are disabled and unusable.
     * @param b boolean If true, program is busy and everything is disabled.
     */
    private void toggleProgramBusy(final boolean b) {
        busy = b;
        protokollMenu.setEnabled(!b);
        tabbedPane.setEnabled(!b);
        for (Component cp : kontrolldateiTab.getComponents()) {
            cp.setEnabled(!b);
        }
        for (Component cp : folienTab.getComponents()) {
            cp.setEnabled(!b);
        }
        // The deducer is always disabled.
        kdSemYearDeducer.setEnabled(false);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        var gui = this;

        protokollMenu = new JMenu("Protokolldatei");
        changeLogLocationButton = new JMenuItem(
            "Protokolldatei Speicherort Ändern", KeyEvent.VK_P);
        changeLogLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                protokollDateiModal.setVisible(true);
            }
        });
        protokollMenu.add(changeLogLocationButton);
        menuBar.add(protokollMenu);

        propertiesMenu = new JMenu("Properties-Datei");
        importPropertiesButton = new JMenuItem(
            "Neue Properties Datei Importieren", KeyEvent.VK_I);
        importPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                propertiesDateiModal.setVisible(true);
            }
        });
        exportPropertiesButton = new JMenuItem(
            "Aktuelle Properties Datei Exportieren...", KeyEvent.VK_E);
        exportPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        gui, LogConstants.PROPERTIES,
                        FileChooser.TXT_FILTER, true);
                DynamicProperties.generatePropertiesFile(path);
            }
        });
        exportDefaultPropertiesButton = new JMenuItem(
            "Default Properties Datei Exportieren...", KeyEvent.VK_D);
        exportDefaultPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        gui, LogConstants.PROPERTIES,
                        FileChooser.TXT_FILTER, true);
                DefaultProperties.generateDefaultPropertiesFile(gui, path);
            }
        });
        propertiesMenu.add(importPropertiesButton);
        propertiesMenu.add(exportPropertiesButton);
        propertiesMenu.add(exportDefaultPropertiesButton);
        menuBar.add(propertiesMenu);

        setJMenuBar(menuBar);
    }

    private void deduceYear() {
        int newValue = Integer.parseInt(kdSemYearSpinner.getValue().toString());
        kdSemYearSpinner.setValue(newValue);
        if (kdWiSoPicker.getSelectedIndex() == SUMMER_SEMESTER_IDX) {
            kdSemYearDeducer.setText("");
            return;
        } else {
            kdSemYearDeducer.setText("" + (newValue + 1));
        }
    }

    private void setSemesterProperties() {
        // Initialize the Spinner with the current year
        final int currentMillenium = 2000;
        final int maxSpinnerValue = 99;
        int currentYear = Calendar.getInstance()
                .get(Calendar.YEAR) - currentMillenium;
        SpinnerNumberModel numberModel =
                new SpinnerNumberModel(currentYear, 0, maxSpinnerValue, 1);
        kdSemYearSpinner.setModel(numberModel);
        deduceYear(); // To fill in the deducer field as default is WiSe

        // Ensures that negative values and letters aren't accepted
        JFormattedTextField txt =
                ((JSpinner.NumberEditor) kdSemYearSpinner.getEditor())
                                                        .getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        // Ensures that the Semester Spinner can't go to negative.
        kdSemYearSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                deduceYear();
            }
        });

        // Updates the Deducer based on whether it's SoSe or WiSe
        kdWiSoPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                switch (kdWiSoPicker.getSelectedIndex()) {
                    case WINTER_SEMESTER_IDX:
                        int spinnerValue = Integer.parseInt(
                                kdSemYearSpinner.getValue().toString());
                        kdSemYearDeducer.setText("" + (spinnerValue + 1));
                        break;
                    case SUMMER_SEMESTER_IDX:
                        kdSemYearDeducer.setText("");
                    default:
                        break;
                }
            }

        });
    }

    private void addActionListeners() {
        var log = LoggingUtils.getInstance();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(
                final java.awt.event.WindowEvent windowEvent) {
                // Prevent closing the frame while the operation is running
                if (!busy) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
        });

        kdAnmeldelisteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        LogConstants.ALUMNIDATEI,
                        FileChooser.CSV_FILTER, false, log);
                kdAnmeldelisteInput.setText(path);
            }
        });

        kdFreitagslisteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        LogConstants.FREITAGSLISTE,
                        FileChooser.XLSX_FILTER, false, log);
                kdFreitagslisteInput.setText(path);
            }
        });

        kdSP015Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        LogConstants.SP015,
                        FileChooser.XLSX_FILTER, false, log);
                kdSP015Input.setText(path);
            }
        });

        kdOutPathButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        LogConstants.CONTROL_FILE,
                        FileChooser.XLSX_FILTER, true, log);
                kdOutPathInput.setText(path);
            }

        });

        kdGenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                generateKD();
            }
        });

        folienKontrolldateiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String path = FileChooser.chooseFile(
                        LogConstants.CONTROL_FILE,
                        FileChooser.XLSX_FILTER, false, log);
                folienKontrolldateiInput.setText(path);
            }
        });

        folienVorlageButton.addActionListener((e) -> {
            String path = FileChooser.chooseFile(
                    LogConstants.TEMPLATE,
                    FileChooser.PPTX_FILTER, false, log);
            folienVorlageInput.setText(path);
        });

        folienOutButton.addActionListener((e) -> {
            String path = FileChooser.chooseFile(
                LogConstants.SLIDE_SET,
                FileChooser.PPTX_FILTER, true, log);
            folienOutInput.setText(path);
        });

        folienGenerateButton.addActionListener((e) -> {
            generateFolien();
        });
    }

    private void generateFolien() {
        Thread workThread = new Thread(() -> {
            ArrayList<String> errors = new ArrayList<>();
            String kdPath = folienKontrolldateiInput.getText();
            String vorlagePath = folienVorlageInput.getText();
            String folienOutPath = folienOutInput.getText();

            if (kdPath.equalsIgnoreCase("GROUP32")) {
                GUIUtils.showDialog(this, "Easter Egg",
                    "amar bayrem hai thai omar and ivo deserve a 1.0!",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verify Inputs
            if (!GUIUtils.fileExists(kdPath)) {
                errors.add(GUIConstants.FILE_NOT_FOUND(
                        LogConstants.CONTROL_FILE, kdPath));
            }
            if (!GUIUtils.fileExists(vorlagePath)) {
                errors.add(GUIConstants.FILE_NOT_FOUND(
                        LogConstants.TEMPLATE, vorlagePath));
            }
            if (!GUIUtils.isValidPath(folienOutPath)) {
                errors.add(GUIConstants.INVALID_PATH(
                        folienOutPath));
            }

            if (!errors.isEmpty()) {
                showListError(errors);
                return;
            }

            setFolienProgressBarVisible(true);
            try {
                Main.generateSlidesFromControlFile(
                        kdPath, vorlagePath, folienOutPath, this);
            } catch (ExecutionException e) {
                setFolienProgressBarVisible(false);
                return;
            }
            setFolienProgressBarVisible(false);
            GUIUtils.showDialog(this, GUIConstants.SUCCESS,
                    GUIConstants.SUCCESSFULLY_CREATED(
                            false, LogConstants.SLIDE_SET),
                    JOptionPane.INFORMATION_MESSAGE);
        });
        workThread.start();
    }

    private void generateKD() {
        Thread workThread = new Thread(() -> {
            LoggingUtils log = LoggingUtils.getInstance();
            ArrayList<String> errors = new ArrayList<>();
            String sp015Path = kdSP015Input.getText();
            String freitagslistePath = kdFreitagslisteInput.getText();
            String alumnilistePath = kdAnmeldelisteInput.getText();
            String outputPath = kdOutPathInput.getText();

            // Verify Inputs
            if (!GUIUtils.fileExists(sp015Path)) {
                errors.add(GUIConstants.FILE_NOT_FOUND(
                    LogConstants.SP015, sp015Path));
            }
            if (!GUIUtils.fileExists(freitagslistePath)) {
                errors.add(GUIConstants.FILE_NOT_FOUND(
                    LogConstants.FREITAGSLISTE, freitagslistePath));
            }
            if (!GUIUtils.fileExists(alumnilistePath)) {
                errors.add(GUIConstants.FILE_NOT_FOUND(
                    LogConstants.ALUMNIDATEI, alumnilistePath));
            }
            if (!GUIUtils.isValidPath(outputPath)) {
                errors.add(GUIConstants.INVALID_PATH(outputPath));
            }

            if (!errors.isEmpty()) {
                showListError(errors);
                return;
            }

            setkdProgressBarVisible(true);
            try {
                Main.generateControlFile(outputPath, sp015Path,
                        freitagslistePath, alumnilistePath, log, this);
            } catch (ExecutionException e) {
                setkdProgressBarVisible(false);
                return;
            }
            setkdProgressBarVisible(false);

            GUIUtils.showDialog(this, GUIConstants.SUCCESS,
                    GUIConstants.SUCCESSFULLY_CREATED(
                            false, LogConstants.CONTROL_FILE),
                    JOptionPane.INFORMATION_MESSAGE);
        });

        workThread.start();
    }

    private void showListError(final ArrayList<String> list) {
        if (list == null) {
            return;
        }
        String errorText = GUIConstants.FILE_VALIDATION_ERROR + "<br/> <ul>";
        for (String string : list) {
            errorText += "<li>" + string + "</li>";
        }
        errorText = "<html>" + errorText + "</ul></html>";

        GUIUtils.showDialog(this, "Fehlermeldung",
                errorText, JOptionPane.OK_OPTION);
    }

    private void initComponents() {
        // GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amar Ademi
        DefaultComponentFactory compFactory =
                DefaultComponentFactory.getInstance();
        this.mainWindow = new JPanel();
        this.tabbedPane = new JTabbedPane();
        this.kontrolldateiTab = new JPanel();
        this.kdMainTitle = compFactory.createTitle("Kontrolldatei Erzeugen");
        this.kdSubHeader1 = new JLabel();
        this.kdSP015Header = new JLabel();
        this.kdSP015Input = new JTextField();
        this.kdSP015Button = new JButton();
        this.kdFreitagslisteHeader = new JLabel();
        this.kdFreitagslisteInput = new JTextField();
        this.kdFreitagslisteButton = new JButton();
        this.kdAnmeldelisteHeader = new JLabel();
        this.kdAnmeldelisteInput = new JTextField();
        this.kdAnmeldelisteButton = new JButton();
        this.kdSeparator = new JSeparator();
        this.kdSubHeader2 = new JLabel();
        this.kdOutPathInput = new JTextField();
        this.kdOutPathHeader = new JLabel();
        this.kdOutPathButton = new JButton();
        this.kdSemesterTitle = new JLabel();
        this.kdWiSoPicker = new JComboBox<>();
        this.kdSemYearSpinner = new JSpinner();
        this.kdSemYearDeducer = new JTextField();
        this.kdGenerateButton = new JButton();
        this.kdProgressBar = new JProgressBar();
        this.folienProgressBar = new JProgressBar();
        this.folienTab = new JPanel();
        this.folienMainTitle = compFactory.createTitle("Folien Erzeugen");
        this.folienSubHeader1 = new JLabel();
        this.folienKontrolldateiHeader = new JLabel();
        this.folienKontrolldateiInput = new JTextField();
        this.folienKontrolldateiButton = new JButton();
        this.folienVorlageHeader = new JLabel();
        this.folienVorlageInput = new JTextField();
        this.folienVorlageButton = new JButton();
        this.folienSubHeader2 = new JLabel();
        this.folienOutHeader = new JLabel();
        this.folienOutInput = new JTextField();
        this.folienOutButton = new JButton();
        this.folienGenerateButton = new JButton();

        final int normalWidth = 600;
        final int normalHeight = 450;
        final int minWidth = 400;
        final int minHeight = 400;
        final Dimension normalDimension =
                new Dimension(normalWidth, normalHeight);
        final Dimension minDimension = new Dimension(minWidth, minHeight);

        this.mainWindow.setPreferredSize(normalDimension);
        this.mainWindow.setMaximumSize(normalDimension);
        this.mainWindow.setMinimumSize(normalDimension);
        this.mainWindow.setLayout(new MigLayout(
            "fill,hidemode 3,align center top",
            // columns
            "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]",
            // rows
            "[]"
            + "[]"
            + "[]"
            + "[]14"
            + "[]"
            + "[]14"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]0"
            + "[]0"
            + "[]0"
            + "[]"));
        this.tabbedPane.setMinimumSize(minDimension);
        initKDTab();
        this.tabbedPane.addTab("Kontrolldatei", this.kontrolldateiTab);
        initFolienTab();
        this.tabbedPane.addTab("Folien", this.folienTab);
        this.mainWindow.add(this.tabbedPane, "cell 0 0 8 14,dock center");
        this.protokollDateiModal = new ProtokolldateiModal(this);
        this.propertiesDateiModal = new PropertiesDateiModal(this);

        addActionListeners();
        // JFormDesigner - End of component initialization
        //GEN-END:initComponents  @formatter:on
        // Prepare the JFrame for displaying
        Container contentPane = getContentPane();
        contentPane.add(mainWindow);
    }

    private void initKDTab() {
        this.kontrolldateiTab.setLayout(new MigLayout(
            "hidemode 3,align center top",
            // columns
            "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[91,fill]"
            + "[132:49,shrink 0,fill]",
            // rows
            "[]"
            + "[]"
            + "[]"
            + "[]14"
            + "[]"
            + "[]14"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"));
        //---- kdMainTitle ----
        this.kdMainTitle.setFont(new Font(
                    "Segoe UI", Font.BOLD, mainTitleFontSize));
        this.kontrolldateiTab.add(this.kdMainTitle, "cell 0 0 3 1");
        //---- kdSubHeader1 ----
        this.kdSubHeader1.setText("Eingaben");
        this.kdSubHeader1.setToolTipText("Eingaben");
        this.kdSubHeader1.setFont(this.kdSubHeader1.getFont().deriveFont(
                    this.kdSubHeader1.getFont().getStyle() | Font.BOLD));
        this.kontrolldateiTab.add(this.kdSubHeader1, "cell 0 1 3 1");
        //---- kdSP015Header ----
        this.kdSP015Header.setText("SP015 (TUCaN) Datei");
        this.kontrolldateiTab.add(this.kdSP015Header, "cell 0 2 3 1");
        this.kontrolldateiTab.add(this.kdSP015Input, "cell 0 3 7 1");
        //---- kdSP015Button ----
        this.kdSP015Button.setText("Datei Ausw\u00e4hlen...");
        this.kontrolldateiTab.add(this.kdSP015Button, "cell 7 3");
        //---- kdFreitagslisteHeader ----
        this.kdFreitagslisteHeader.setText("Freitagsliste");
        this.kontrolldateiTab.add(this.kdFreitagslisteHeader, "cell 0 4 3 1");
        this.kontrolldateiTab.add(this.kdFreitagslisteInput, "cell 0 5 7 1");
        //---- kdFreitagslisteButton ----
        this.kdFreitagslisteButton.setText("Datei Ausw\u00e4hlen...");
        this.kontrolldateiTab.add(this.kdFreitagslisteButton, "cell 7 5");
        //---- kdAnmeldelisteHeader ----
        this.kdAnmeldelisteHeader.setText("Anmeldeliste (AlumniTool Liste)");
        this.kontrolldateiTab.add(this.kdAnmeldelisteHeader, "cell 0 6 3 1");
        this.kontrolldateiTab.add(this.kdAnmeldelisteInput, "cell 0 7 7 1");
        //---- kdAnmeldelisteButton ----
        this.kdAnmeldelisteButton.setText("Datei Ausw\u00e4hlen...");
        this.kontrolldateiTab.add(this.kdAnmeldelisteButton, "cell 7 7");
        this.kontrolldateiTab.add(this.kdSeparator, "cell 0 8 8 1");
        //---- kdSubHeader2 ----
        this.kdSubHeader2.setText("Ausgabeeinstellungen");
        this.kdSubHeader2.setToolTipText("Eingaben");
        this.kdSubHeader2.setFont(this.kdSubHeader2.getFont().deriveFont(
                    this.kdSubHeader2.getFont().getStyle() | Font.BOLD));
        this.kontrolldateiTab.add(this.kdSubHeader2, "cell 0 9 3 1");
        this.kontrolldateiTab.add(this.kdOutPathInput, "cell 0 11 7 1");
        //---- kdOutPathHeader ----
        this.kdOutPathHeader.setText("Kontrolldatei Ausgabepfad");
        this.kontrolldateiTab.add(this.kdOutPathHeader, "cell 0 10 3 1");
        //---- kdOutPathButton ----
        this.kdOutPathButton.setText("Datei Ausw\u00e4hlen...");
        this.kontrolldateiTab.add(this.kdOutPathButton, "cell 7 11");
        //---- kdSemesterTitle ----
        this.kdSemesterTitle.setText("Semester");
        this.kontrolldateiTab.add(this.kdSemesterTitle, "cell 0 13 3 1");
        //---- kdWiSoPicker ----
        this.kdWiSoPicker.setModel(new DefaultComboBoxModel<>(new String[] {
            "WiSe",
            "SoSe"
        }));
        this.kontrolldateiTab.add(this.kdWiSoPicker, "cell 0 14");
        this.kontrolldateiTab.add(this.kdSemYearSpinner, "cell 1 14");
        //---- kdSemYearDeducer ----
        this.kdSemYearDeducer.setEnabled(false);
        this.kontrolldateiTab.add(this.kdSemYearDeducer, "cell 2 14");
        //---- kdGenerateButton ----
        this.kdGenerateButton.setText("Kontrolldatei generieren!");
        this.kontrolldateiTab.add(this.kdGenerateButton, "cell 0 15 8 2");
        //--- kdProgressBar ---
        this.kdProgressBar.setVisible(false);
        this.kdProgressBar.setStringPainted(true);
        this.kontrolldateiTab.add(this.kdProgressBar, "cell 0 15 8 2");
    }

    private void initFolienTab() {
        this.folienTab.setLayout(new MigLayout(
            "hidemode 3,align center top",
            // columns
            "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[100,fill]"
            + "[91,fill]"
            + "[132:49,shrink 0,fill]",
            // rows
            "[]"
            + "[]"
            + "[]"
            + "[]14"
            + "[]"
            + "[]14"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"
            + "[]"));
        //---- folienMainTitle ----
        this.folienMainTitle.setFont(new Font(
            "Segoe UI", Font.BOLD, mainTitleFontSize));
        this.folienTab.add(this.folienMainTitle, "cell 0 0 3 1");
        //---- folienSubHeader1 ----
        this.folienSubHeader1.setText("Eingaben");
        this.folienSubHeader1.setToolTipText("Eingaben");
        this.folienSubHeader1.setFont(this.folienSubHeader1
                .getFont().deriveFont(
                    this.folienSubHeader1.getFont().getStyle() | Font.BOLD));
        this.folienTab.add(this.folienSubHeader1, "cell 0 1 3 1");
        //---- folienKontrolldateiHeader ----
        this.folienKontrolldateiHeader.setText("Kontrolldatei");
        this.folienTab.add(this.folienKontrolldateiHeader, "cell 0 2 3 1");
        this.folienTab.add(this.folienKontrolldateiInput, "cell 0 3 7 1");
        //---- folienKontrolldateiButton ----
        this.folienKontrolldateiButton.setText("Datei Ausw\u00e4hlen...");
        this.folienTab.add(this.folienKontrolldateiButton, "cell 7 3");
        //---- folienVorlageHeader ----
        this.folienVorlageHeader.setText("PowerPoint Vorlage");
        this.folienTab.add(this.folienVorlageHeader, "cell 0 4 3 1");
        this.folienTab.add(this.folienVorlageInput, "cell 0 5 7 1");
        //---- folienVorlageButton ----
        this.folienVorlageButton.setText("Datei Ausw\u00e4hlen...");
        this.folienTab.add(this.folienVorlageButton, "cell 7 5");
        //---- folienSubHeader2 ----
        this.folienSubHeader2.setText("Ausgabeeinstellungen");
        this.folienSubHeader2.setToolTipText("Eingaben");
        this.folienSubHeader2.setFont(
            this.folienSubHeader2.getFont().deriveFont(
                this.folienSubHeader2.getFont().getStyle() | Font.BOLD));
        this.folienTab.add(this.folienSubHeader2, "cell 0 7 3 1");
        //---- folienOutHeader ----
        this.folienOutHeader.setText("Folien Ausgabepfad");
        this.folienTab.add(this.folienOutHeader, "cell 0 8 3 1");
        this.folienTab.add(this.folienOutInput, "cell 0 9 7 1");
        //---- folienOutButton ----
        this.folienOutButton.setText("Datei Ausw\u00e4hlen...");
        this.folienTab.add(this.folienOutButton, "cell 7 9");
        //---- folienGenerateButton ----
        this.folienGenerateButton.setText("Folien generieren!");
        this.folienTab.add(this.folienGenerateButton, "cell 0 10 8 2");
        //--- folienProgressBar ---
        this.folienProgressBar.setVisible(false);
        this.folienProgressBar.setStringPainted(true);
        this.folienTab.add(this.folienProgressBar, "cell 0 10 8 2");
    }

    /**
     * The menu Bar.
     */
    private JMenuBar menuBar;
    /**
     * The Protokolldatei menu inside the menu bar.
     */
    private JMenu protokollMenu;
        /**
     * The Properties Datei menu inside the menu bar.
     */
    private JMenu propertiesMenu;
    /**
     * The changeProtocolDateiLocation Button.
     */
    private JMenuItem changeLogLocationButton;
    /**
     * The exportProperties Button.
     */
    private JMenuItem exportPropertiesButton;
    /**
     * The exportDefaultProperties Button.
     */
    private JMenuItem exportDefaultPropertiesButton;
    /**
     * The importPropertiesButton.
     */
    private JMenuItem importPropertiesButton;

    // JFormDesigner - Variables declaration - DO NOT MODIFY
    // GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amar Ademi
    /**
     * The main JPanel holding everything inside.
     */
    private JPanel mainWindow;
    /**
     * The tabs.
     */
    private JTabbedPane tabbedPane;
    /**
     * The Controlfile generation tab.
     */
    private JPanel kontrolldateiTab;
    /**
     * The Control file main title.
     */
    private JLabel kdMainTitle;
    /**
     * The Control file subheader.
     */
    private JLabel kdSubHeader1;
    /**
     * The SP015 Control file header.
     */
    private JLabel kdSP015Header;
    /**
     * The SP015 Control file input bar.
     */
    private JTextField kdSP015Input;
    /**
     * The SP015 Control Browse file button.
     */
    private JButton kdSP015Button;
    /**
     * The Freitagsliste Control file header.
     */
    private JLabel kdFreitagslisteHeader;
    /**
     * The Freitagsliste Control file input bar.
     */
    private JTextField kdFreitagslisteInput;
    /**
     * The Freitagsliste Control Browse file button.
     */
    private JButton kdFreitagslisteButton;
    /**
     * The Alumniliste Control file header.
     */
    private JLabel kdAnmeldelisteHeader;
    /**
     * The Alumniliste Control file input bar.
     */
    private JTextField kdAnmeldelisteInput;
    /**
     * The Alumniliste Control Browse file button.
     */
    private JButton kdAnmeldelisteButton;
    /**
     * The separator between the inputs and the output in KDtab.
     */
    private JSeparator kdSeparator;
    /**
     * The KD Output subheader.
     */
    private JLabel kdSubHeader2;
    /**
     * The KD output path input box.
     */
    private JTextField kdOutPathInput;
    /**
     * The KD Output path header.
     */
    private JLabel kdOutPathHeader;
    /**
     * The KD Output path button.
     */
    private JButton kdOutPathButton;
    /**
     * The KD Semester Picker title.
     */
    private JLabel kdSemesterTitle;
    /**
     * The KD Semester Picker.
     */
    private JComboBox<String> kdWiSoPicker;
    /**
     * The KD Semester Spinner.
     */
    private JSpinner kdSemYearSpinner;
    /**
     * The KD Semester Year Deducer (second box, only for WiSe).
     */
    private JTextField kdSemYearDeducer;
    /**
     * The Generate KD Button.
     */
    private JButton kdGenerateButton;
    /**
     * The KD Progress Bar.
     */
    private JProgressBar kdProgressBar;
    /**
     * The Folien Progress Bar.
     */
    private JProgressBar folienProgressBar;
    /**
     * The Folien Tab.
     */
    private JPanel folienTab;
    /**
     * The Folien Main title.
     */
    private JLabel folienMainTitle;
    /**
     * The Folien Inputs Subheader.
     */
    private JLabel folienSubHeader1;
    /**
     * The Folien Controlfile input header.
     */
    private JLabel folienKontrolldateiHeader;
    /**
     * The Folien Controlfile input text box.
     */
    private JTextField folienKontrolldateiInput;
    /**
     * The Folien Controlfile browse file button.
     */
    private JButton folienKontrolldateiButton;
    /**
     * The Folien Presentation Template Header.
     */
    private JLabel folienVorlageHeader;
    /**
     * The Folien Presentation Template Input Box.
     */
    private JTextField folienVorlageInput;
    /**
     * The Folien Presentation Template Browse file button.
     */
    private JButton folienVorlageButton;
    /**
     * The Folien Output subheader2.
     */
    private JLabel folienSubHeader2;
    /**
     * The Folien Output Header.
     */
    private JLabel folienOutHeader;
    /**
     * The Folien Output input box.
     */
    private JTextField folienOutInput;
    /**
     * The Folien Output browse file button.
     */
    private JButton folienOutButton;
    /**
     * The Generate folien button.
     */
    private JButton folienGenerateButton;
    /**
     * The Dialog for selecting a new protocol file.
     */
    private ProtokolldateiModal protokollDateiModal;
    /**
     * The Dialog for selecting a new Properties file.
     */
    private PropertiesDateiModal propertiesDateiModal;
    // JFormDesigner - End of variables declaration
    //GEN-END:variables  @formatter:on
}
