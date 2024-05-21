package tud.bp.group32.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import tud.bp.group32.reader.CSVReader;
import tud.bp.group32.reader.FreitagslisteReader;
import tud.bp.group32.reader.SP015Reader;
import tud.bp.group32.student.StudentAlumnifile;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.student.StudentFreitagsliste;
import tud.bp.group32.student.StudentSP015;

public class ListsComparisonTest {

        /**
         * Test to compare the 3 input files and verify that all the information
         * from those 3 files is correctly read.
         * @throws ExecutionException
         */
        @Test
    public void compareUsingStudentUtils() throws ExecutionException {
        // Initialize readers for each type of input file
        var sp015Reader = new SP015Reader(
                "./src/test/resources/SP015Regular (6 students).xlsx");
        var freitagslisteReader = new FreitagslisteReader(
                "./src/test/resources/FreitagslisteRegular.xlsx");
        var alumnifileReader = new CSVReader(
                "./src/test/resources/AlumnilisteRegular (6 students).csv");

        // Read all files
        HashMap<String, StudentSP015> sp015 =
                sp015Reader.readAllAsHashMap();
        HashMap<String, StudentFreitagsliste> freitagsliste =
                freitagslisteReader.readAllAsHashMap();
        HashMap<String, StudentAlumnifile> alumnifile =
                alumnifileReader.readAll();

        // Ensure all maps are not null
        assertNotNull(sp015);
        assertNotNull(freitagsliste);
        assertNotNull(alumnifile);

        //compare the 3 files
        HashMap<String, StudentControlfile> controlfileList
                = StudentUtils.compareThreeFiles(sp015, freitagsliste,
                alumnifile, null);

        // Compare 1st student
        assertNotNull(controlfileList);
        assertEquals("1111111", controlfileList.get("1111111").getMatrikelnr());
        assertEquals("Tada", controlfileList.get("1111111").getVorname());
        assertEquals("Fabian", controlfileList.get("1111111").getNachname());
        assertEquals("Informationssystemtechnik",
        controlfileList.get("1111111").getStudiengang());
        assertEquals(true, controlfileList.get("1111111").getWillHaveSlide());
        assertEquals(true, controlfileList.get("1111111").getIsInAlumnifile());
        assertEquals(true, controlfileList.get("1111111").getIsInSP015());

        // Compare 2nd student
        assertNotNull(controlfileList);
        assertEquals("2222222", controlfileList.get("2222222").getMatrikelnr());
        assertEquals("Song", controlfileList.get("2222222").getVorname());
        assertEquals("Brenda", controlfileList.get("2222222").getNachname());
        assertEquals("Information and Communication Engineering",
        controlfileList.get("2222222").getStudiengang());
        assertEquals(true, controlfileList.get("2222222").getWillHaveSlide());
        assertEquals(true, controlfileList.get("2222222").getIsInAlumnifile());
        assertEquals(true, controlfileList.get("2222222").getIsInSP015());

        // Compare 3rd student
        assertNotNull(controlfileList);
        assertEquals("3333333", controlfileList.get("3333333").getMatrikelnr());
        assertEquals("Sardine", controlfileList.get("3333333").getVorname());
        assertEquals("Chichi", controlfileList.get("3333333").getNachname());
        assertEquals("Mechatronik", controlfileList
                .get("3333333").getStudiengang());
        assertEquals(true, controlfileList.get("3333333").getWillHaveSlide());
        assertEquals(true, controlfileList.get("3333333").getIsInAlumnifile());
        assertEquals(true, controlfileList.get("3333333").getIsInSP015());

        // Compare 4th student
        assertNotNull(controlfileList);
        assertEquals("4444444", controlfileList.get("4444444").getMatrikelnr());
        assertEquals("Daniela", controlfileList.get("4444444").getVorname());
        assertEquals("Steinmeier",
        controlfileList.get("4444444").getNachname());
        assertEquals("Mechatronik",
        controlfileList.get("4444444").getStudiengang());
        assertEquals(true, controlfileList.get("4444444").getWillHaveSlide());
        assertEquals(true, controlfileList.get("4444444").getIsInAlumnifile());
        assertEquals(true, controlfileList.get("4444444").getIsInSP015());

        // Compare 5th student
        assertNotNull(controlfileList);
        assertEquals("5555555", controlfileList.get("5555555").getMatrikelnr());
        assertEquals("Konstantino",
        controlfileList.get("5555555").getVorname());
        assertEquals("Schema", controlfileList.get("5555555").getNachname());
        assertEquals("Elektrotechnik und Informationstechnik",
        controlfileList.get("5555555").getStudiengang());
        assertEquals(true, controlfileList.get("5555555").getWillHaveSlide());
        assertEquals(true, controlfileList.get("5555555").getIsInAlumnifile());
        assertEquals(true, controlfileList.get("5555555").getIsInSP015());

        // 6th student in CSV file, see if the test recognizes the change
        assertNull(controlfileList.get("6666666"));

        // 6th student in SP015, see if the test recognizes the change
        assertNull(controlfileList.get("7777777"));
    }

         /**
        * This is also for testing the 3 input files,
        but this time with empty files.
        * @throws ExecutionException
        */
        @Test
    public void emptyComparisonTest() throws ExecutionException {
        // Initialize readers for each type of input file
        var sp015Reader = new SP015Reader(
                "./src/test/resources/SP015Empty.xlsx");
        var freitagslisteReader = new FreitagslisteReader(
                "./src/test/resources/FreitagslisteEmpty.xlsx");
        var alumnifileReader = new CSVReader(
                "./src/test/resources/AlumnilisteEmpty.csv");

        // Read all files
        HashMap<String, StudentSP015> sp015 =
                sp015Reader.readAllAsHashMap();
        HashMap<String, StudentFreitagsliste> freitagsliste =
                freitagslisteReader.readAllAsHashMap();
        HashMap<String, StudentAlumnifile> alumnifile =
                alumnifileReader.readAll();

        // Ensure all maps are not null
        assertNotNull(sp015);
        assertNotNull(freitagsliste);
        assertNotNull(alumnifile);

        //compare the 3 files
        HashMap<String, StudentControlfile> controlfileList
                = StudentUtils.compareThreeFiles(sp015, freitagsliste,
                alumnifile, null);

        // Compare empty student
        assertNotNull(controlfileList);
        assertEquals("N/A", controlfileList.get("N/A").getMatrikelnr());
        assertEquals("N/A", controlfileList.get("N/A").getVorname());
        assertEquals("N/A", controlfileList.get("N/A").getNachname());
        assertEquals("N/A", controlfileList.get("N/A").getStudiengang());

        assertEquals(false, controlfileList.get("N/A").getWillHaveSlide());
        assertEquals(false, controlfileList.get("N/A").getIsInAlumnifile());
        // still counts as 1 student
        assertEquals(true, controlfileList.get("N/A").getIsInSP015());

    }
}
