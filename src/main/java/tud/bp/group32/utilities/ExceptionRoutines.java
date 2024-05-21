package tud.bp.group32.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import tud.bp.group32.Main;
import tud.bp.group32.UI.GUIExceptionHandler;
import tud.bp.group32.constants.GUIConstants;
import tud.bp.group32.constants.LogConstants;

/**
 * Class to tell the program how to react in case of an error.
 * This is a utility class, hence it should not have a public constructor (in
 * compliance with checkstyle).
 */

public class ExceptionRoutines {
    protected ExceptionRoutines() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Method to describe how the program would react
     * in case of an exception caught.
     * @param e parameter to help checking for exceptions.
     * @param gui a GUI hook for showing a Dialog box with an error message.
     */
    public static void exceptionRoutine(final Exception e,
                                        final GUIExceptionHandler gui)
            throws ExecutionException {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        if (e instanceof java.io.FileNotFoundException) {
            String errorMessage = LogConstants.FILE_NOT_FOUND(
                    e.getLocalizedMessage());
            if (Main.isCliMode()) {
                endProgram(errorMessage, 1);
            } else {
                if (LoggingUtils.instanceExists()) {
                    LoggingUtils.getInstance()
                            .writeInLog(LoggingUtils.ERROR, errorMessage);
                }
                if (gui != null) {
                    gui.catchException(GUIConstants.ERROR, errorMessage);
                }
                throw new ExecutionException(e);
            }
        } else if (e instanceof java.io.IOException) {
            String errorMessage = LogConstants.UNEXPECTED_IO_ERROR(null)
                + "\n" + GUIConstants.CHECK_LOG;

            if (Main.isCliMode()) {
                endProgram(LogConstants.UNEXPECTED_IO_ERROR(exceptionAsString),
                        1);
            } else {
                if (LoggingUtils.instanceExists()) {
                    LoggingUtils.getInstance().writeInLog(
                        LoggingUtils.ERROR,
                        LogConstants.UNEXPECTED_IO_ERROR(exceptionAsString));
                }
                if (gui != null) {
                    gui.catchException(GUIConstants.ERROR, errorMessage);
                }
                throw new ExecutionException(e);
            }
        } else {
            String errorMessage = LogConstants.UNEXPECTED_ERROR(null)
                + "\n" + GUIConstants.CHECK_LOG;
            if (Main.isCliMode()) {
                endProgram(LogConstants.UNEXPECTED_ERROR(exceptionAsString), 1);
            } else {
                if (LoggingUtils.instanceExists()) {
                    LoggingUtils.getInstance().writeInLog(
                        LoggingUtils.ERROR,
                        LogConstants.UNEXPECTED_ERROR(exceptionAsString));
                }
                if (gui != null) {
                    gui.catchException(GUIConstants.ERROR, errorMessage);
                }
                throw new ExecutionException(e);
            }
        }
    }

    /**
     * Method to make sure that the program works safely.
     * @param e potential Exception
     * @param gui a GUI hook for showing a Dialog box with an error message.
     */
    public static void exceptionRoutineSafe(final Exception e,
                                            final GUIExceptionHandler gui) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        if (e instanceof java.io.FileNotFoundException) {
            String errorMessage = LogConstants
                    .FILE_NOT_FOUND(e.getLocalizedMessage());
            if (Main.isCliMode()) {
                endProgram(errorMessage, 1);
            } else {
                LoggingUtils.getInstance()
                        .writeInLog(LoggingUtils.ERROR, errorMessage);
                if (gui != null) {
                    gui.catchException(GUIConstants.ERROR, errorMessage);
                }
            }
        } else if (e instanceof java.io.IOException) {
            String errorMessage = LogConstants.UNEXPECTED_IO_ERROR(null)
                + "\n" + GUIConstants.CHECK_LOG;

            if (Main.isCliMode()) {
                endProgram(LogConstants.UNEXPECTED_IO_ERROR(exceptionAsString),
                        1);
            } else {
                LoggingUtils.getInstance().writeInLog(
                    LoggingUtils.ERROR,
                    LogConstants.UNEXPECTED_IO_ERROR(exceptionAsString));
                if (gui != null) {
                    gui.catchException(GUIConstants.ERROR, errorMessage);
                }
            }
        } else {
            String errorMessage = LogConstants.UNEXPECTED_ERROR(null)
                + "\n" + GUIConstants.CHECK_LOG;
            if (Main.isCliMode()) {
                endProgram(LogConstants.UNEXPECTED_ERROR(exceptionAsString), 1);
            } else {
                LoggingUtils.getInstance().writeInLog(
                    LoggingUtils.ERROR,
                    LogConstants.UNEXPECTED_ERROR(exceptionAsString));
                if (gui != null) {
                    gui.catchException(GUIConstants.ERROR, errorMessage);
                }
            }
        }
    }

    /**
     * Method to end the program.
     * @param error the caught error
     * @param status exit status
     */
    public static void endProgram(final String error, final int status) {
        if (LoggingUtils.instanceExists()) {
            LoggingUtils.getInstance().writeInLog(LoggingUtils.ERROR,
                    error + LogConstants.CONTACT_US);
        }
        System.out.println(LogConstants.EXIT_ERROR + "\n"
                + LogConstants.PRESS_ENTER);
        var scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
        System.exit(status);
    }
}
