package tud.bp.group32;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import tud.bp.group32.UI.FileChooser;
import tud.bp.group32.UI.GUIUtils;
import tud.bp.group32.UI.GUIBase;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.constants.TerminalConstants;
import tud.bp.group32.generator.DegreeSlide;
import tud.bp.group32.generator.GroupfotoSlideGenerator;
import tud.bp.group32.generator.OtherSlideSetting;
import tud.bp.group32.generator.SlideGenerator;
import tud.bp.group32.generator.TableSlideGenerator;
import tud.bp.group32.generatorutils.SlidesDataProvider;
import tud.bp.group32.reader.CSVReader;
import tud.bp.group32.reader.ControlFileReader;
import tud.bp.group32.reader.FreitagslisteReader;
import tud.bp.group32.reader.SP015Reader;
import tud.bp.group32.student.StudentSP015;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.ExceptionRoutines;
import tud.bp.group32.utilities.LoggingUtils;
import tud.bp.group32.utilities.StudentUtils;
import tud.bp.group32.writer.ControlFileWriter;
import tud.bp.group32.student.StudentFreitagsliste;
import tud.bp.group32.student.StudentAlumnifile;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.constants.ArtOfAbschluss;
import tud.bp.group32.constants.GUIConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.Objects;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.SwingUtilities;

public final class Main {
    /**
     * Constant for toggling DEBUG MODE.
     */
    public static final boolean DEBUG_MODE = false;
    /**
     * CLI Mode indicates whether there is a GUI instance or not.
     */
    private static boolean cliMode = false;

    private Main() {
        // Private constructor to prevent instantiation.
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Main entry point for the program.
     * @param args Arguments with which FG24 is called.
     * Calling FG24 with the arg "cli" will start FG24 in Command Line mode.
     * By default, FG24 runs with the GUI.
     * @throws ExecutionException Interruption exception to crash the program.
     */
    public static void main(final String[] args) throws ExecutionException {
        if (args.length != 0 && args[0].equalsIgnoreCase("cli")) {
            cliMode = true;
            DynamicProperties.initProperties(null);
            cliMode();
        } else {
            SwingUtilities.invokeLater(() -> {
                try {
                    new GUIBase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Getter method for CliMode Variable.
     * @return is the program running in CLI mode or GUI mode.
     */
    public static boolean isCliMode() {
        return cliMode;
    }

    /**
     * Defines the behavior of the program in command Line mode.
     * Shows a short summary of possible commands, then expects input from user.
     * Input is parsed and program behaves according to the summary.
     * @throws ExecutionException Interruption exception to crash the program.
     */
    private static void cliMode() throws ExecutionException {
        String[] commands = new String[0];
        String usage;
        if (DEBUG_MODE) {
            usage = TerminalConstants.USAGE_DEBUG;
        } else {
            usage = TerminalConstants.USAGE;
        }
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println(TerminalConstants.WELCOME + usage);
        while (!exit) {
            String input = scanner.nextLine();
            commands = input.split(
                TerminalConstants.QUOTED_ARGUMENT_RECOGNITION_REGEX);
            for (int i = 0; i < commands.length; i++) {
                commands[i] = commands[i].replaceAll("\"", "");
            }
            // show the no arguments message which lists the possible arguments
            if (Objects.equals(commands[0], "help")) {
                System.out.println("Verwendung: \n" + usage);
                commands = new String[0];
                continue;
            }
            // If there are arguments, parse them accordingly
            switch (commands[0].toLowerCase()) {
                case "sp015":
                    if (checkArguments(1, commands.length)) {
                        exit = true;
                        readSP015(commands[1]);
                    } else {
                        commands = new String[0];
                    }
                    break;
                case "group":
                    if (checkArguments(1, commands.length)) {
                        exit = true;
                        group(commands[1]);
                    } else {
                        commands = new String[0];
                    }
                    break;
                case "alumnilist":
                    if (checkArguments(1, commands.length)) {
                        exit = true;
                        readAlumnilist(commands[1]);
                    } else {
                        commands = new String[0];
                    }
                    break;
                case "freitagsliste":
                    if (checkArguments(1, commands.length)) {
                        exit = true;
                        readFreitagsliste(commands[1]);
                    } else {
                        commands = new String[0];
                    }
                    break;
                case "generatekontroll":
                    exit = true;
                    generateControlFileWithChoosers();
                    break;
                case "generatefolien":
                    exit = true;
                    generateSlidesFromControlFile();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println(
                        "\nFalsche Befehl gegeben: \""
                        + commands[0]
                        + "\". Verwendung: \n" + usage);
                    commands = new String[0];
                    break;
            }
        }

        System.out.println(
            LogConstants.EXIT_SUCCESS + LogConstants.PRESS_ENTER);

        // Wait for the user to press Enter
        scanner.nextLine();

        // Close the Scanner to avoid resource leaks
        scanner.close();
    }

    /**
     * Wrapper Method that generates Slides from ControlFile, with FileChoosers.
     * This is used in CLI Mode, to make selecting files easier.
     * Kinda nonsense but was required as our user could not use a CLI reliably.
     * @throws ExecutionException Interruption exception.
     */
    private static void generateSlidesFromControlFile()
            throws ExecutionException {
        LoggingUtils log = LoggingUtils.getInstance();

        // Taking the file paths
        String controlFilePath;
        String templatePath;
        String slidesOutputPath;
        controlFilePath = FileChooser.chooseFile(
                LogConstants.CONTROL_FILE, FileChooser.XLSX_FILTER, false, log);
        templatePath = FileChooser.chooseFile(
                LogConstants.TEMPLATE, FileChooser.PPTX_FILTER, false, log);
        slidesOutputPath = FileChooser.chooseFile(
                LogConstants.SLIDE_SET, FileChooser.PPTX_FILTER, true, log);

        generateSlidesFromControlFile(
                controlFilePath, templatePath, slidesOutputPath, null);
    }

    /**
     * Method that generates Slides from Control File.
     * This version of the method expects to be given the Paths to each file.
     * This method is called by the overloaded wrapper method
     * generateSlidesFromControlFile() which has no parameters.
     * @param controlFilePath Path to the control file.
     * @param templatePath Path to the PowerPoint Template file.
     * @param slidesOutputPath Path where the slides should be saved.
     * @param gui GUI Instance, for updating the progress bar if in GUI mode.
     * @throws ExecutionException Interruption Exception.
     */
    public static void generateSlidesFromControlFile(
        final String controlFilePath,
        final String templatePath,
        final String slidesOutputPath,
        final GUIBase gui) throws ExecutionException {

        LoggingUtils log = LoggingUtils.getInstance();
        // Opening and reading Controlfile
        GUIUtils.safeUpdatePercentage(
            gui,
            true,
            0,
            GUIConstants.READING(LogConstants.CONTROL_FILE));
        ControlFileReader reader = new ControlFileReader(controlFilePath);
        List<StudentControlfile> students = reader.readAll(log);
        SlidesDataProvider provider = new SlidesDataProvider(students);
        final int fivePercent = 5;

        try {
            // Preparing Presentation constants
            GUIUtils.safeUpdatePercentage(
                gui,
                true,
                fivePercent,
                GUIConstants.PREPARING(LogConstants.TEMPLATE));
            FileInputStream templateStream =
                new FileInputStream(templatePath);
            FileOutputStream presentationStream =
                new FileOutputStream(slidesOutputPath);
            XMLSlideShow slides = new XMLSlideShow(templateStream);
            templateStream.close();

            // Clear all previous slides from presentation
            OtherSlideSetting.removeExistingSlides(slides, log);

            // TOGGLE FOOTER ON/OFF - 2nd param is the Footer String:
            // OtherSlideSetting.changeMasterFooterPropertyTwo(slides, null);

            // Prepare the Statistics slide
            var dataColumn = provider.getSecondColumnOfStatisticsTable();

            GUIUtils.safeUpdatePercentage(
                gui,
                true,
                fivePercent,
                GUIConstants.WRITING_OUTPUT(false, slidesOutputPath));

            // +++ GENERATING THE SLIDES! +++
            // Generate the Statistics table
            TableSlideGenerator.generateSlide(slides, dataColumn);
            // Then Generate all other slides
            generationProcedure(gui, provider, slides, log, presentationStream);

            // The following procedure can be refactored in a method.
            log.writeInLog(Level.INFO, LogConstants.SUCCESSFUL_SLIDES);

            // Open the PPTX file for the user if possible.
            if (Desktop.isDesktopSupported()) {
                // Get the Desktop instance
                Desktop desktop = Desktop.getDesktop();
                var slidesFile = new File(slidesOutputPath);

                // Check if the file exists
                if (slidesFile.exists()) {
                    // Open the file using the default associated program
                    desktop.open(slidesFile);
                }
            }
        } catch (Exception e) {
            ExceptionRoutines.exceptionRoutine(e, gui);
        }
    }

    /**
     * The procedure used to generate the Student Slides.
     * This procedure does not include any slides that don't have to do with
     * the students themselves. That means no Statistics table,
     * as well as no final Slide.
     * @param gui The GUI instance for updating the progress bar.
     * @param provider The Students Data Provider for the Slides.
     * @param slides The XMLSlideShow instance where the slides are generated.
     * @param log The Logger instance.
     * @param presentationStream The Stream for saving the Slides file.
     * @throws IOException IOException in case something goes wrong while
     *                     saving the presentation. Handled by caller.
     */
    private static void generationProcedure(
        final GUIBase gui,
        final SlidesDataProvider provider,
        final XMLSlideShow slides,
        final LoggingUtils log,
        final FileOutputStream presentationStream) throws IOException {
        // This var holds the last students from an abschluss, so they
        // can be shown with the first students of the next abschlussart
        List<StudentControlfile> prev = null;

        // Announce in ProgBar & Generate the Bachelors Slides
        final int twelvePercent = 12;
        GUIUtils.safeUpdatePercentage(
            gui,
            true,
            twelvePercent,
            GUIConstants.WRITING_OUTPUT(
                true,
                LogConstants.BACHELOR_SLIDES));
        var bachelorsIterator = provider.getBachelorsIterator();
        if (bachelorsIterator.hasNext()) {
            DegreeSlide.generateWithStudents(
                    slides,
                    ArtOfAbschluss.ART_BACHELOR_SC,
                    bachelorsIterator.peekNext()
                            .toArray(new StudentControlfile[0]));
            prev = SlideGenerator.generateSlidesLoop(
                slides, bachelorsIterator);
        }
        log.writeInLog(Level.INFO,
            LogConstants.SLIDES_CREATED(
                bachelorsIterator.getSum(), ArtOfAbschluss.BACHELORS));

        // Announce in ProgBar & Generate the Masters Slides
        final int thirtyFivePercent = 35;
        GUIUtils.safeUpdatePercentage(
            gui,
            true,
            thirtyFivePercent,
            GUIConstants.WRITING_OUTPUT(
                true,
                LogConstants.MASTER_SLIDES));
        var mastersIterator = provider.getMastersIterator();
        if (mastersIterator.hasNext()) {
            if (prev != null) {
                GroupfotoSlideGenerator.generate(
                    slides,
                    StudentUtils.listToArray(prev),
                    StudentUtils.listToArray(mastersIterator.peekNext()));
            }
            DegreeSlide.generate(slides, ArtOfAbschluss.ART_MASTER_SC);
            prev = SlideGenerator.generateSlidesLoop(
                slides, mastersIterator);
        }
        log.writeInLog(Level.INFO,
            LogConstants.SLIDES_CREATED(
                mastersIterator.getSum(), ArtOfAbschluss.MASTERS));

        // Announce in ProgBar & Generate the International Slides
        final int seventyPercent = 70;
        GUIUtils.safeUpdatePercentage(
            gui,
            true,
            seventyPercent,
            GUIConstants.WRITING_OUTPUT(
                true,
                LogConstants.INTERNATIONAL_SLIDES));
        var internationalIterator = provider.getInternationalIterator();
        if (internationalIterator.hasNext()) {
            if (prev != null) {
                GroupfotoSlideGenerator.generate(
                    slides,
                    StudentUtils.listToArray(prev),
                    StudentUtils.listToArray(
                        internationalIterator.peekNext()));
            }
            DegreeSlide.generate(slides, ArtOfAbschluss.ART_MASTER_INT);
            prev = SlideGenerator.generateSlidesLoop(
                slides, internationalIterator);
        }
        log.writeInLog(Level.INFO,
                LogConstants.SLIDES_CREATED(
                    internationalIterator.getSum(),
                    ArtOfAbschluss.INTERNATIONAL));

        // Announce in ProgBar & Generate the DD Slides
        final int eightyPercent = 80;
        GUIUtils.safeUpdatePercentage(
            gui,
            true,
            eightyPercent,
            GUIConstants.WRITING_OUTPUT(
                true,
                LogConstants.DD_SLIDES));
        var ddIterator = provider.getDDIterator();
        if (ddIterator.hasNext()) {
            if (prev != null) {
                GroupfotoSlideGenerator.generate(
                    slides,
                    StudentUtils.listToArray(prev),
                    StudentUtils.listToArray(ddIterator.peekNext()));
            }
            DegreeSlide.generate(slides, ArtOfAbschluss.DD_SLIDE);
            prev = SlideGenerator.generateSlidesLoop(slides, ddIterator);
        }
        log.writeInLog(Level.INFO,
            LogConstants.SLIDES_CREATED(
                ddIterator.getSum(),
                ArtOfAbschluss.DD));

        // Generate the last Groupfoto Slide
        if (prev != null) {
            GroupfotoSlideGenerator.generate(
                slides,
                StudentUtils.listToArray(prev),
                null);
        }

        final int ninetyFivePercent = 95;
        GUIUtils.safeUpdatePercentage(
            gui,
            true,
            ninetyFivePercent,
            GUIConstants.WRITING_OUTPUT(
                false,
                LogConstants.SLIDE_SET));
        slides.write(presentationStream);
        slides.close();
    }

    /**
     * Checks whether the given command has the correct amount of args.
     * @param expected Expected number of arguments.
     * @param actual Actual number of arguments.
     * @return boolean Whether the command has the correct nr. of Args.
     */
    private static boolean checkArguments(
        final int expected, final int actual) {
        // Since first argument will be the actual "command", we do -- on actual
        if (actual - 1  != expected) {
            System.out.println(
                "Anzahl Argumenten ist nicht richtig."
                + "Bitte prüfen Sie,"
                + "ob Sie die richtige Argumenten gegeben haben.");
            return false;
        }
        return true;
    }

    /**
     * MANUAL TEST METHOD.
     * Procedure used to read an SP015 file.
     * Creates a StudentSP015 object out of each row, then prints them all.
     * @param arg1 The path to the SP015 file.
     * @throws ExecutionException Interruption Exception.
     */
    private static void readSP015(final String arg1) throws ExecutionException {
        List<StudentSP015> list;
        SP015Reader reader = new SP015Reader(arg1);
        list = reader.readAll();
        list.forEach((System.out::println));
    }

    /**
     * MANUAL TEST METHOD.
     * Procedure used to list the results of the getBalancedSumList method.
     * @param numberToBalance The path to the SP015 file.
     */
    private static void group(final String numberToBalance) {
        int[] array = StudentUtils.getBalancedSumList(
            Integer.parseInt(numberToBalance));
        System.out.print("[");
        for (int i : array) {
            System.out.print(i + ",");
        }
        System.out.print("]");
    }

    /**
     * MANUAL TEST METHOD.
     * Procedure used to read an Alumnilist file.
     * Creates a StudentAlumnifile object out of each row, then prints them all.
     * @param arg1 The path to the Alumnilist file.
     * @throws ExecutionException Interruption Exception.
     */
    private static void readAlumnilist(final String arg1)
            throws ExecutionException {
        HashMap<String, StudentAlumnifile> map;
        CSVReader reader = new CSVReader(arg1);
        map = reader.readAll();
        map.forEach((matrikelnr, student) -> {
            System.out.println(student);
        });
    }

    /**
     * MANUAL TEST METHOD.
     * Procedure used to read a Freitagsliste file.
     * Creates a StudentFreitagsliste object out of each row,
     * then prints them all.
     * @param arg1 The path to the Freitagsliste file.
     * @throws ExecutionException Interruption Exception.
     */
    private static void readFreitagsliste(final String arg1)
            throws ExecutionException {
        List<StudentFreitagsliste> list;
        FreitagslisteReader reader = new FreitagslisteReader(arg1);
        list = reader.readAll();
        list.forEach((System.out::println));
    }

    /**
     * Wrapper Method that generates ControlFile from lists, with FileChoosers.
     * This is used in CLI Mode, to make selecting files easier.
     * Kinda nonsense but was required as our user could not use a CLI reliably.
     * @throws ExecutionException Interruption Exception.
     */
    private static void generateControlFileWithChoosers()
            throws ExecutionException {
        LoggingUtils log = LoggingUtils.getInstance();
        String pathSP015 = FileChooser.chooseFile(
            LogConstants.SP015,
            FileChooser.XLSX_FILTER,
            false,
            log);
        String pathFreitagsliste = FileChooser.chooseFile(
            LogConstants.FREITAGSLISTE,
            FileChooser.XLSX_FILTER,
            false,
            log);
        String pathAlumnifile = FileChooser.chooseFile(
            LogConstants.ALUMNIDATEI,
            FileChooser.CSV_FILTER,
            false,
            log);

        generateControlFile(
            null,
            pathSP015,
            pathFreitagsliste,
            pathAlumnifile,
            log,
            null);
    }

    /**
     * The procedure used to actually Generate the ControlFile.
     * This method works on the principle that all paths are known.
     * @param outputPath The Path where the ControlFile will be written.
     * @param pathSP015 Path to SP015 file.
     * @param pathFreitagsliste Path to Freitagsliste File.
     * @param pathAlumnifile Path to Alumnifile.
     * @param log Logger instance.
     * @param gui GUI Instance for updating progress bar.
     * @throws ExecutionException Interruption Exception.
     */
    public static void generateControlFile(
        final String outputPath,
        final String pathSP015,
        final String pathFreitagsliste,
        final String pathAlumnifile,
        final LoggingUtils log,
        final GUIBase gui) throws ExecutionException {
        if (gui != null) {
            gui.setkdProgressBarString(
                GUIConstants.PERCENT_READING(LogConstants.SP015, 0));
            gui.setkdProgressBarPercent(0);
        }
        SP015Reader sp015reader = new SP015Reader(pathSP015, gui);
        HashMap<String, StudentSP015> sp015 = sp015reader.readAllAsHashMap(log);

        final int thirtyPercent = 30;
        GUIUtils.safeUpdatePercentage(
            gui,
            false,
            thirtyPercent,
            GUIConstants.READING(LogConstants.FREITAGSLISTE));

        FreitagslisteReader freitagslisteReader =
            new FreitagslisteReader(pathFreitagsliste, gui, gui.getSemester());
        HashMap<String, StudentFreitagsliste> freitagsliste =
            freitagslisteReader.readAllAsHashMap(log);

        final int sixtyPercent = 60;
        GUIUtils.safeUpdatePercentage(
            gui,
            false,
            sixtyPercent,
            GUIConstants.READING(LogConstants.ALUMNIDATEI));
        CSVReader csvReader = new CSVReader(pathAlumnifile, gui);
        HashMap<String, StudentAlumnifile> alumnifile = csvReader.readAll(log);

        final int eightyPercent = 80;
        GUIUtils.safeUpdatePercentage(
            gui,
            false,
            eightyPercent,
            GUIConstants.COMPARING);
        HashMap<String, StudentControlfile> controlfilePreOutput =
        StudentUtils.compareThreeFiles(
            sp015, freitagsliste,
            alumnifile, log);

        final int ninetyPercent = 90;
        GUIUtils.safeUpdatePercentage(
            gui,
            false,
            ninetyPercent,
            GUIConstants.GROUPING_SORTING);

        var finalOutput =
            StudentUtils.sortAndGroupBeforeWriting(controlfilePreOutput);

        final int ninetyFivePercent = 95;
        GUIUtils.safeUpdatePercentage(
            gui,
            false,
            ninetyFivePercent,
            GUIConstants.WRITING_OUTPUT(false, LogConstants.CONTROL_FILE));
        if (outputPath == null) {
            ControlFileWriter.writer(finalOutput);
        } else {
            ControlFileWriter.writeKontrolldatei(outputPath, finalOutput,
                                                gui, log);
        }

    }
}
