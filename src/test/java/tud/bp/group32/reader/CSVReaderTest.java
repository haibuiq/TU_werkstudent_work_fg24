package tud.bp.group32.reader;

import org.junit.jupiter.api.Test;
import tud.bp.group32.student.StudentAlumnifile;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class CSVReaderTest {

    /**
     * tests method readAll() from class CSVReader by using three
     * normal Student objects (StudentAlumnifile objects).
     * @throws ExecutionException
     */
    @Test
    public void readAllTest() throws ExecutionException {
        CSVReader reader = new CSVReader(
                "./src/test/resources/regularCsv.csv");

        // Read the File
        HashMap<String, StudentAlumnifile> map = reader.readAll();
        final int four = 4;

        assertNotNull(map);
        assertEquals(four, map.size());

        StudentAlumnifile student1 = map.get("1111111");
        assertEquals("FirstName1", student1.getVorname());
        assertEquals("LastName1", student1.getNachname());
        assertEquals("1111111", student1.getMatrikelnr());

        StudentAlumnifile student2 = map.get("2222222");
        assertEquals("FirstName2", student2.getVorname());
        assertEquals("LastName2", student2.getNachname());
        assertEquals("2222222", student2.getMatrikelnr());

        StudentAlumnifile student3 = map.get("3333333");
        assertEquals("FirstName3", student3.getVorname());
        assertEquals("LastName3", student3.getNachname());
        assertEquals("3333333", student3.getMatrikelnr());
    }
    /** tests method readAll() from class CSVReader by using
     * a non-existent Filepath to a Csv-File.
     * and checks that the FilNotFoundException is thrown.
     * @throws ExecutionException
     */
    @Test
    public void nonExistentReadAllTest() {
        var faultyReader = new CSVReader(
                "./src/test/resources/CsvNonExistent.csv");

        Exception e = assertThrows(
                java.util.concurrent.ExecutionException.class, () -> {
            faultyReader.readAll();
        });

        assertTrue(e.getCause() instanceof java.io.FileNotFoundException);
    }

    /**
     * tests method readAll() from class CSVReader by using an empty Csv-File
     * and checks that all fields are not assigned/false.
     * @throws ExecutionException
     */
    @Test
    public void emptyCsvFileReadAllTest() throws ExecutionException {
        var emptyReader = new CSVReader("./src/test/resources/emptyCsv.csv");
        HashMap<String, StudentAlumnifile> emptyList = emptyReader.readAll();
        StudentAlumnifile emptyStudent = emptyList.get("someKey");
        assertEquals(0, emptyList.size());
        assertEquals(null, emptyStudent);
    }

}

