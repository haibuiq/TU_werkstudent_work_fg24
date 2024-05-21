package tud.bp.group32.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import tud.bp.group32.constants.Auszeichnung;

public class SP015ReaderTest {

    /**
     * This Test method ensures safety upon regular cases.
     * @throws ExecutionException if the file for some reason cannot
     * be opened for reading.
     */
    @Test
    public void regularReadAllTest() throws ExecutionException {
        var reader1 = new SP015Reader(
            "./src/test/resources/SP015RegularOther.xlsx");

        // Read the File
        var regularList = reader1.readAll();

        // Ensure it has read correctly what it needs to read
        assertNotNull(regularList);
        assertEquals(4, regularList.size());

        // Verify the first Student
        var student0 = regularList.get(0);
        assertEquals("1111111", student0.getMatrikelnr());
        assertEquals("Herr", student0.getAnrede());
        assertEquals("B.Sc.", student0.getAbschlussAbkuerzung());
        assertEquals("Bachelor of Science", student0.getAbschlussart());
        assertEquals("FirstName01", student0.getVorname());
        assertEquals("LastName01", student0.getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
            student0.getStudiengang());
        assertEquals("Thesis1", student0.getAbschlussarbeit());
        assertEquals(false, student0.getDoubleDegree());
        assertEquals("Prof. Dr.-Ing. Test1", student0.getPrueferIn());
        assertEquals(Auszeichnung.NONE, student0.getAuszeichnung());
        assertEquals("2.93", student0.getAbschlussNote());
        assertEquals("Befriedigend", student0.getUrteil());

        // Verify the second Student
        var student1 = regularList.get(1);
        assertEquals("2222222", student1.getMatrikelnr());
        assertEquals("Frau", student1.getAnrede());
        assertEquals("M.Sc.", student1.getAbschlussAbkuerzung());
        assertEquals("Master of Science", student1.getAbschlussart());
        assertEquals("FirstName02", student1.getVorname());
        assertEquals("LastName02", student1.getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
            student1.getStudiengang());
        assertEquals("Thesis2", student1.getAbschlussarbeit());
        assertEquals(false, student1.getDoubleDegree());
        assertEquals("Prof. Dr.-Ing. Test2", student1.getPrueferIn());
        assertEquals(Auszeichnung.GOLD, student1.getAuszeichnung());
        assertEquals("1.3", student1.getAbschlussNote());
        assertEquals("Mit Auszeichnung", student1.getUrteil());

        // Verify the third Student
        var student2 = regularList.get(2);
        assertEquals("3333333", student2.getMatrikelnr());
        assertEquals("Herr", student2.getAnrede());
        assertEquals("M.Sc.", student2.getAbschlussAbkuerzung());
        assertEquals("Master of Science", student2.getAbschlussart());
        assertEquals("FirstName03", student2.getVorname());
        assertEquals("LastName03", student2.getNachname());
        assertEquals("Informations und Kommunikationstechnik",
            student2.getStudiengang());
        assertEquals("Thesis3", student2.getAbschlussarbeit());
        assertEquals(true, student2.getDoubleDegree());
        assertEquals("Prof. Dr.-Ing. Test3", student2.getPrueferIn());
        assertEquals(Auszeichnung.SILVER, student2.getAuszeichnung());
        assertEquals("1.5", student2.getAbschlussNote());
        assertEquals("Sehr Gut", student2.getUrteil());

        // Verify the fourth Student
        var student3 = regularList.get(3);
        assertEquals("4444444", student3.getMatrikelnr());
        assertEquals("Frau", student3.getAnrede());
        assertEquals("B.Sc.", student3.getAbschlussAbkuerzung());
        assertEquals("Bachelor of Science", student3.getAbschlussart());
        assertEquals("FirstName04", student3.getVorname());
        assertEquals("LastName04", student3.getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
            student3.getStudiengang());
        assertEquals("Thesis4", student3.getAbschlussarbeit());
        assertEquals(false, student3.getDoubleDegree());
        assertEquals("Prof. Dr.-Ing. Test4", student3.getPrueferIn());
        assertEquals(Auszeichnung.NONE, student3.getAuszeichnung());
        assertEquals("1.94", student3.getAbschlussNote());
        assertEquals("Gut", student3.getUrteil());

    }

    /**
     * This Test method also ensures safety upon coming across empty cells.
     * @throws ExecutionException if the file for some reason cannot
     * be opened for reading.
     */
    @Test
    public void emptyReadAllTest() throws ExecutionException {
        var emptyReader = new SP015Reader(
            "./src/test/resources/SP015Empty.xlsx");

        // Read the File
        var emptyList = emptyReader.readAll();

        // Ensure it has read correctly what it needs to read
        assertNotNull(emptyList);
        assertEquals(1, emptyList.size());

        var emptyStudent = emptyList.get(0);
        assertEquals("N/A", emptyStudent.getMatrikelnr());
        assertEquals("N/A", emptyStudent.getAnrede());
        assertEquals("N/A", emptyStudent.getAbschlussAbkuerzung());
        assertEquals("N/A", emptyStudent.getAbschlussart());
        assertEquals("N/A", emptyStudent.getVorname());
        assertEquals("N/A", emptyStudent.getNachname());
        assertEquals("N/A", emptyStudent.getStudiengang());
        assertEquals("N/A", emptyStudent.getAbschlussarbeit());
        assertEquals(false, emptyStudent.getDoubleDegree());
        assertEquals("N/A", emptyStudent.getPrueferIn());
        assertEquals(Auszeichnung.NONE, emptyStudent.getAuszeichnung());
        assertEquals("N/A", emptyStudent.getAbschlussNote());
        assertEquals("N/A", emptyStudent.getUrteil());
    }

    /**
     * This Test method ensures safety upon the edge case if the file
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    @Test
    public void faultyReadAllTest() {
        var faultyReader = new SP015Reader(
            "./src/test/resources/SP015Faulty.xlsx");

        assertThrows(java.util.concurrent.ExecutionException.class, () -> {
            faultyReader.readAll();
        });
    }

    /**
     * This Test method ensures safety if the file does not exist.
     */
    @Test
    public void nonExistentReadAllTest() {
        var faultyReader = new SP015Reader(
            "./src/test/resources/SP015NonExistent.xlsx");

        Exception e = assertThrows(
            java.util.concurrent.ExecutionException.class, () -> {
            faultyReader.readAll();
        });

        assertTrue(e.getCause() instanceof java.io.FileNotFoundException);
    }
}
