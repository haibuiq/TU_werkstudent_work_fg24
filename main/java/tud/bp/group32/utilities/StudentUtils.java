package tud.bp.group32.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.student.Student;
import tud.bp.group32.student.StudentAlumnifile;
import tud.bp.group32.student.StudentFreitagsliste;
import tud.bp.group32.student.StudentSP015;
import tud.bp.group32.constants.AnredeTyp;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.LogConstants;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class StudentUtils {
    private StudentUtils() {
        // Private constructor to prevent instantiation.
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Method to sort and group the students after comparison.
     * Sorts students on last name, then groups them by Abschlussart.
     * @param input Final list of students after they've been compared.
     * @return Sorted and grouped list of Students.
     */
    public static List<StudentControlfile> sortAndGroupBeforeWriting(
        final HashMap<String, StudentControlfile> input) {
        return input.values().stream()
        // Sort students by last name
                .sorted(Comparator.comparing(StudentControlfile::getNachname))
                .collect(Collectors.collectingAndThen(
                        // Group the Students based on Abschlussart
                        Collectors.groupingBy(
                            student -> student.getAbschlussart() != null
                                ? student.getAbschlussart()
                                : AnredeTyp.NOT_DEFINED,
                            // Put them in a Linked HashMap to retain sort.
                            LinkedHashMap::new,
                            Collectors.toList()),
                        map -> map.values().stream()
                                .flatMap(List::stream)
                                .collect(Collectors.toList())));
    }

    /**
     * A method to check whether a string is a valid number.
     * @param strNum the String to be checked
     * @return boolean is the string numeric
     */
    public static boolean isNumeric(final String strNum) {
        if (strNum == null) {
            return false;
        }
        if (strNum.equals(AnredeTyp.NOT_DEFINED)) {
            return true;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Transforms an Excel column name to a numerical index.
     * The column name is usually a single letter.
     * NOTE: Can only take strings up to length 2.
     * @param column Column name to be transformed
     * @return int The index
     */
    public static int columnNameToIndex(final String column) {
        if (column.length() > 2) {
            return -1;
        }
        char[] ch = column.toLowerCase().toCharArray();
        final int lowercaseCharConstant = 97;
        final int alphabetLength = 26;
        if (ch.length == 2) {
            return (alphabetLength * (ch[0] - lowercaseCharConstant + 1))
                + (ch[1] - lowercaseCharConstant);
        } else {
            return ch[0] - lowercaseCharConstant;
        }
    }

    /**
     * Reads an Excel cell as a String if it's of type:
     *   - BOOLEAN
     *   - STRING
     *   - NUMERIC
     * Returns NOT_DEFINED if unexpected type.
     * @param cell The cell to be read
     * @return String The value or N/A
     */
    public static String readCellAsString(final Cell cell) {
        if (cell != null && cell.getCellType() != CellType.BLANK) {
            if (cell.getCellType() == CellType.BOOLEAN) {
                return Boolean.toString(cell.getBooleanCellValue());
            }
            if (cell.getCellType() == CellType.STRING
                && !cell.getStringCellValue().isBlank()) {
                return cell.getStringCellValue();
            }
            if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return AnredeTyp.NOT_DEFINED;
    }

    /**
     * Derives Auszeichnung value from different string constants.
     * These constants are found in different places throughout the lists.
     * @param string The string to be checked for Auszeichnung
     * @return Auszeichnung The Auszeichnung it signifies
     */
    public static Auszeichnung deriveAuszeichnung(final String string) {
        switch (string.toLowerCase()) {
            case "mit auszeichnung":
                return Auszeichnung.GOLD;
            case "ma":
                return Auszeichnung.GOLD;
            case "sehr gut":
                return Auszeichnung.SILVER;
            default:
                return Auszeichnung.NONE;
        }

    }

    /**
     * Method that compares the three Lists and decides which student
     * will be taken into the Control File.
     * @param sp015 Students from the SP015 List
     * @param freitagsliste Students from the Freitagsliste
     * @param alumnifile Students from the Alumnilist
     * @param log The logger instance
     * @return A HashMap containing all students which meet the conditions
     *         to be in the control file. That is:
     *              - If he is included in the Freitagsliste
     *                   - If he is also included in SP015 and Alumnilist,
     *                   he is marked with a true in willHaveSlide attribute.
     */
    public static HashMap<String, StudentControlfile> compareThreeFiles(
            final HashMap<String, StudentSP015> sp015,
            final HashMap<String, StudentFreitagsliste> freitagsliste,
            final HashMap<String, StudentAlumnifile> alumnifile,
            final LoggingUtils log) {
        HashMap<String, StudentControlfile> controlfileList = new HashMap<>();
        final int studentsWithSlide = 0;
        final int studentsWithoutSlide = 1;
        int[] studentCounts = new int[2];
        // Iterate through Freitagsliste
        freitagsliste.forEach(
                (matrikelnr, studentFreitagsliste) -> {
                    StudentControlfile current;

                    // Handle every case we care for.
                    // REMINDER: We don't care abt students not in Freitagsliste
                    if (sp015.containsKey(matrikelnr)) {
                        current = sp015.get(matrikelnr).asStudentControlfile();
                        current.setIsInSP015(true);
                        current.setDoubleDegree(
                            studentFreitagsliste.getDoubleDegree()
                            || current.getDoubleDegree());
                        // GOOD - is in folien
                        if (alumnifile.containsKey(matrikelnr)) {
                            current.setWillHaveSlide(true);
                            ++studentCounts[studentsWithSlide];
                        } else {
                            // BAD - is in SP015, not in Alumni
                            current.setWillHaveSlide(false);
                            current.setIsInAlumnifile(false);
                            ++studentCounts[studentsWithoutSlide];
                        }
                    } else {
                        // BAD - is not in SP015, determine if in alumni
                        current = studentFreitagsliste.asStudentControlfile();
                        current.setIsInSP015(false);
                        current.setIsInAlumnifile(alumnifile.containsKey(
                            matrikelnr));
                        ++studentCounts[studentsWithoutSlide];
                        current.setWillHaveSlide(false);
                    }
                    controlfileList.put(matrikelnr, current);
                });
        if (log != null) {
            log.writeInLog(Level.INFO,
                    LogConstants.COUNTED(
                        studentCounts[studentsWithSlide],
                        studentCounts[studentsWithoutSlide]));
        }
        return controlfileList;
    }

    /**
     * Generates an array of integers representing a
     * balanced distribution of elements.
     * The method ensures that the sum of elements is evenly distributed across
     * groups based on the specified group size.
     *
     * @param numOfElements The total number of elements to be distributed.
     * @return An array of integers representing the balanced distribution.
     * @throws IllegalArgumentException if
     *  {@code numOfElements} is less than or equal to 0.
     */
    public static int[] getBalancedSumList(final int numOfElements) {
        int numElements = (int) Math.ceil(
            (double) numOfElements / DynamicProperties.GROUP_SIZE);
        int[] array = new int[numElements];
        for (int i = 0; i < numOfElements; i++) {
            array[i % numElements] = array[i % numElements] + 1;
        }
        return array;
    }

    /**
     * Sorts a list of students based on their last- and then their first name.
     * The sorting is performed in ascending order.
     *
     * @param studentsList The list of students to be sorted.
     * @throws IllegalArgumentException if {@code studentsList} is null.
     */
    public static void sortByName(final List<? extends Student> studentsList) {
        if (studentsList == null) {
            return;
        }
        Collections.sort(studentsList,
                Comparator.comparing(Student::getNachname)
                      .thenComparing(Student::getVorname));
    }

    /**
     * Converts a list of StudentControlfile objects to
     * an array of StudentControlfile.
     * If the input list is null, an empty array is returned.
     *
     * @param list The list of StudentControlfile objects
     *             to be converted to an array.
     * @return An array containing the elements of the input
     *         list in the same order,
     *         or an empty array if the input list is null.
     */
    public static StudentControlfile[] listToArray(
        final List<StudentControlfile> list) {
        if (list != null) {
            return list.toArray(new StudentControlfile[list.size()]);
        } else {
            return new StudentControlfile[0];
        }
    }

    /**
     * To be used to truncate after-comma-values from the Matrikelnr.
     * @param matrikelnr The matrikelnr to be checked.
     * @return The cleaned Matrikelnr or the original.
     */
    public static String cleanMatrikelnr(final String matrikelnr) {
        if (matrikelnr.length() <= Student.MATRIKELNR_LENGTH) {
                return matrikelnr;
        } else {
                var split = matrikelnr.split("\\.");
                if (split.length > 1
                        && split[0].length() == Student.MATRIKELNR_LENGTH) {
                    return split[0];
                } else {
                    return matrikelnr;
                }
        }
    }
}
