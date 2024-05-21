package tud.bp.group32.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tud.bp.group32.constants.Auszeichnung;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FreitagslisteReaderTest {

    /**
     * This Test method ensures safety upon regular cases.
     *
     * @throws ExecutionException if the file for some reason
     * cannot be opened for reading.
     */
    @Test
    public void regularReadTest() throws ExecutionException {
        var reader = new FreitagslisteReader(
            "./src/test/resources/FreitagslisteRegular.xlsx");

        // Read the File
        var freitagsliste = reader.readAll();

        // Ensure it has read correctly what it needs to read
        Assertions.assertNotNull(freitagsliste);
        Assertions.assertEquals(5, freitagsliste.size());

        // Verify the first Student
        var student0 = freitagsliste.get(0);
        Assertions.assertEquals("1111111", student0.getMatrikelnr());
        Assertions.assertEquals("Tada", student0.getVorname());
        Assertions.assertEquals("Fabian", student0.getNachname());
        Assertions.assertEquals("M.Sc.", student0.getAbschlussAbkuerzung());
        Assertions.assertEquals("Informationssystemtechnik",
                student0.getStudiengang());
        assertFalse(student0.getDoubleDegree());
        Assertions.assertEquals(Auszeichnung.NONE, student0.getAuszeichnung());

        // Verify the second Student
        var student1 = freitagsliste.get(1);
        Assertions.assertEquals("2222222", student1.getMatrikelnr());
        Assertions.assertEquals("Song", student1.getVorname());
        Assertions.assertEquals("Brenda", student1.getNachname());
        Assertions.assertEquals("M.Sc.", student1.getAbschlussAbkuerzung());
        Assertions.assertEquals("Information and Communication Engineering",
                student1.getStudiengang());
        assertFalse(student1.getDoubleDegree());
        Assertions.assertEquals(Auszeichnung.NONE, student1.getAuszeichnung());

        // Verify the third Student
        var student2 = freitagsliste.get(2);
        Assertions.assertEquals("3333333", student2.getMatrikelnr());
        Assertions.assertEquals("Sardine", student2.getVorname());
        Assertions.assertEquals("Chichi", student2.getNachname());
        Assertions.assertEquals("M.Sc.", student2.getAbschlussAbkuerzung());
        Assertions.assertEquals("Mechatronik", student2.getStudiengang());
        assertTrue(student2.getDoubleDegree());
        Assertions.assertEquals(Auszeichnung.GOLD, student2.getAuszeichnung());


        // Verify the fourth Student
        var student3 = freitagsliste.get(3);
        Assertions.assertEquals("4444444", student3.getMatrikelnr());
        Assertions.assertEquals("Steinmeier", student3.getNachname());
        Assertions.assertEquals("Daniela", student3.getVorname());
        Assertions.assertEquals("M.Sc.", student3.getAbschlussAbkuerzung());
        Assertions.assertEquals("Mechatronik", student3.getStudiengang());
        Assertions.assertTrue(student3.getDoubleDegree());
        Assertions.assertEquals(Auszeichnung.NONE, student3.getAuszeichnung());

        // Verify the fifth Student
        var student4 = freitagsliste.get(4);
        Assertions.assertEquals("5555555", student4.getMatrikelnr());
        Assertions.assertEquals("Schema", student4.getNachname());
        Assertions.assertEquals("Konstantino", student4.getVorname());
        Assertions.assertEquals("B.Sc.", student4.getAbschlussAbkuerzung());
        Assertions.assertEquals("Elektrotechnik und Informationstechnik",
            student4.getStudiengang());
        assertFalse(student4.getDoubleDegree());
        Assertions.assertEquals(Auszeichnung.NONE, student4.getAuszeichnung());
    }

    /**
     * This Test method also ensures safety upon coming across empty cells.
     */
    @Test
    public void emptyReadAllTest() throws ExecutionException {
        var emptyReader = new FreitagslisteReader(
            "./src/test/resources/FreitagslisteEmpty.xlsx");

        // Read the File
        var emptyList = emptyReader.readAll();

        // Ensure it has read correctly what it needs to read
        Assertions.assertNotNull(emptyList);
        Assertions.assertEquals(1, emptyList.size());

        // Verify empty student
        var emptyStudent = emptyList.get(0);
        Assertions.assertEquals("N/A", emptyStudent.getMatrikelnr());
        Assertions.assertEquals("N/A", emptyStudent.getVorname());
        Assertions.assertEquals("N/A", emptyStudent.getNachname());
        Assertions.assertEquals("N/A", emptyStudent.getAbschlussAbkuerzung());
        Assertions.assertEquals("N/A", emptyStudent.getStudiengang());
        assertFalse(emptyStudent.getDoubleDegree());
        Assertions.assertEquals(Auszeichnung.NONE,
            emptyStudent.getAuszeichnung());
    }

    /**
     * This Test method ensures safety upon the edge case if the file
     * is a directory rather than a regular file, or for some other reason
     * cannot be opened for reading.
     */
    @Test
    public void faultyFileTest() {
        FreitagslisteReader faultyReader = new
                FreitagslisteReader(
                    "./src/test/resources/FreitagslisteFaulty.xlsx");

        assertThrows(java.util.concurrent.ExecutionException.class,
            faultyReader::readAll);

    }

    /**
     * This Test method ensures safety if the file does not exist.
     */
    @Test
    public void nonExistentFileTest() {
        FreitagslisteReader nonExistentReader = new
                FreitagslisteReader("./src/test/resources/hello.xlsx");


        // Test if an Exception is thrown when attempting to
        // read the non-existent file
        Exception e = assertThrows(
            java.util.concurrent.ExecutionException.class,
                nonExistentReader::readAll);

        assertInstanceOf(FileNotFoundException.class, e.getCause());

    }
}
