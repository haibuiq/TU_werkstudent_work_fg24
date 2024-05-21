package tud.bp.group32.reader;

import org.junit.jupiter.api.Test;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.student.StudentControlfile;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ControlFileReaderTest {


    /**
     * This Test method ensures safety upon regular cases.
     * @throws ExecutionException if the file for some reason cannot
     * be opened for reading.
     */
    @Test
    public void regularReadAllTest() throws ExecutionException {
        ControlFileReader regularReader = new ControlFileReader(
            "./src/test/resources/ControlFileRegular.xlsx");

        // Read the File
        List<StudentControlfile> regularList = regularReader.readAll();

        // Ensure the reading was successful
        assertNotNull(regularList);

        // Ensure the right amount of elements was read
        assertEquals(4, regularList.size());

        // Verify the first Student
        StudentControlfile student0 = regularList.get(0);
        assertEquals("1111111", student0.getMatrikelnr());
        assertEquals("Herr", student0.getAnrede());
        assertEquals("M.Sc.", student0.getAbschlussAbkuerzung());
        assertEquals("Master of Science", student0.getAbschlussart());
        assertEquals("FirstName1", student0.getVorname());
        assertEquals("LastName1", student0.getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
            student0.getStudiengang());
        assertEquals("Thesis1", student0.getAbschlussarbeit());
        assertFalse(student0.getDoubleDegree());
        assertEquals("Prof. Dr. rer. nat. Prof1",
            student0.getPrueferIn());
        assertEquals(Auszeichnung.NONE,
            student0.getAuszeichnung());
        assertFalse(student0.getWillHaveSlide());
        assertEquals("2,31",
            student0.getAbschlussNote());
        assertEquals("Gut",
            student0.getUrteil());

        // Verify the second Student
        StudentControlfile student1 = regularList.get(1);
        assertEquals("2222222",
            student1.getMatrikelnr());
        assertEquals("Frau",
            student1.getAnrede());
        assertEquals("B.Sc.",
            student1.getAbschlussAbkuerzung());
        assertEquals("Bachelor of Science",
            student1.getAbschlussart());
        assertEquals("FirstName2",
            student1.getVorname());
        assertEquals("LastName2",
            student1.getNachname());
        assertEquals("Energy Science and Engineering",
            student1.getStudiengang());
        assertEquals("Thesis2",
            student1.getAbschlussarbeit());
        assertFalse(student1.getDoubleDegree());
        assertEquals("Prof. Dr. Prof2",
            student1.getPrueferIn());
        assertEquals(Auszeichnung.SILVER,
            student1.getAuszeichnung());
        assertTrue(student1.getWillHaveSlide());
        assertEquals("1,57",
            student1.getAbschlussNote());
        assertEquals("Sehr gut",
            student1.getUrteil());

        // Verify the third Student
        StudentControlfile student2 = regularList.get(2);
        assertEquals("3333333",
            student2.getMatrikelnr());
        assertEquals("N/A",
            student2.getAnrede());
        assertEquals("B.Ed.",
            student2.getAbschlussAbkuerzung());
        assertEquals("Bachelor of Education",
            student2.getAbschlussart());
        assertEquals("FirstName3",
            student2.getVorname());
        assertEquals("LastName3",
            student2.getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
            student2.getStudiengang());
        assertEquals("N/A",
            student2.getAbschlussarbeit());
        assertFalse(student2.getDoubleDegree());
        assertEquals("N/A",
            student2.getPrueferIn());
        assertEquals(Auszeichnung.NONE,
            student2.getAuszeichnung());
        assertFalse(student2.getWillHaveSlide());
        assertEquals("N/A",
            student2.getAbschlussNote());
        assertEquals("N/A",
            student2.getUrteil());

        // Verify the fourth Student
        StudentControlfile student3 = regularList.get(3);
        assertEquals("4444444",
            student3.getMatrikelnr());
        assertEquals("N/A",
            student3.getAnrede());
        assertEquals("M.Sc.",
            student3.getAbschlussAbkuerzung());
        assertEquals("Double Degree Master",
            student3.getAbschlussart());
        assertEquals("FirstName4",
            student3.getVorname());
        assertEquals("LastName4",
            student3.getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
            student3.getStudiengang());
        assertEquals("N/A",
            student3.getAbschlussarbeit());
        assertTrue(student3.getDoubleDegree());
        assertEquals("N/A",
            student3.getPrueferIn());
        assertEquals(Auszeichnung.GOLD,
            student3.getAuszeichnung());
        assertFalse(student3.getWillHaveSlide());
        assertEquals("N/A",
            student3.getAbschlussNote());
        assertEquals("Mit Auszeichnung",
            student3.getUrteil());
    }

    /**
     * This Test method ensures safety upon the edge case of coming across
     * cells with value null(empty but not BLANK).
     * @throws ExecutionException if the file for some reason cannot
     * be opened for reading.
     */
    @Test
    public void nullReadTest() throws ExecutionException {
        ControlFileReader nullReader = new ControlFileReader(
            "./src/test/resources/ControlFileEmpty.xlsx");

        // Read the File
        List<StudentControlfile> nullList = nullReader.readAll();

        // Ensure the reading was successful
        assertNotNull(nullList);

        assertEquals(2, nullList.size());

        // Verify the Student with empty cells as null.
        StudentControlfile student0 = nullList.get(0);
        assertEquals("N/A", student0.getMatrikelnr());
        assertEquals("N/A", student0.getAnrede());
        assertEquals("N/A", student0.getAbschlussAbkuerzung());
        assertEquals("N/A", student0.getAbschlussart());
        assertEquals("N/A", student0.getVorname());
        assertEquals("N/A", student0.getNachname());
        assertEquals("N/A", student0.getStudiengang());
        assertEquals("N/A", student0.getAbschlussarbeit());
        assertFalse(student0.getDoubleDegree());
        assertEquals("N/A", student0.getPrueferIn());
        assertEquals(Auszeichnung.NONE, student0.getAuszeichnung());
        assertFalse(student0.getWillHaveSlide());
        assertEquals("N/A", student0.getAbschlussNote());
        assertEquals("N/A", student0.getUrteil());

        // Verify the regular Student
        StudentControlfile student1 = nullList.get(1);
        assertEquals("2222222", student1.getMatrikelnr());
        assertEquals("Frau", student1.getAnrede());
        assertEquals("B.Sc.", student1.getAbschlussAbkuerzung());
        assertEquals("Bachelor of Science", student1.getAbschlussart());
        assertEquals("FirstName2", student1.getVorname());
        assertEquals("LastName2", student1.getNachname());
        assertEquals("Energy Science and Engineering",
            student1.getStudiengang());
        assertEquals("Thesis2", student1.getAbschlussarbeit());
        assertFalse(student1.getDoubleDegree());
        assertEquals("Prof. Dr. Prof2", student1.getPrueferIn());
        assertEquals(Auszeichnung.SILVER, student1.getAuszeichnung());
        assertTrue(student1.getWillHaveSlide());
        assertEquals("1,57", student1.getAbschlussNote());
        assertEquals("Sehr gut", student1.getUrteil());
    }

    /**
     * This Test method ensures safety upon the edge case if the file
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    @Test
    public void faultyFileTest() {
        ControlFileReader faultyReader = new ControlFileReader(
            "./src/test/resources/ControlFileFaulty.xlsx");

        assertThrows(ExecutionException.class, () -> faultyReader.readAll());
    }

    /**
     * This Test method ensures safety if the file does not exist.
     */
    @Test
    public void nonExistentFileTest() {
        ControlFileReader nonExistentReader = new ControlFileReader(
            "./src/test/resources/ControlFileNonExistent.xlsx");

        Exception e = assertThrows(
            java.util.concurrent.ExecutionException.class,
            () -> nonExistentReader.readAll());

        assertTrue(e.getCause() instanceof java.io.FileNotFoundException);

    }



}
