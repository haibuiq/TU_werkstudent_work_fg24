package tud.bp.group32.writer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.reader.ControlFileReader;
import tud.bp.group32.student.StudentControlfile;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ControlFileWriterTest {

        /**
         * File path of the test control file.
         */
        private String filePath = "./src/test/resources/ControllFileTest.xlsx";

        /**
         * File path of the test non existent file.
         */
        private String nonExistentFile =
                "./src/test/resources/NonExistentDirectory/"
                + "NonExistentControllFileTest.xlsx";

        /**
         * File path of the test empty file.
         */
        private String emptyFile =
                "./src/test/resources/EmptyControlFileTest.xlsx";
    /**
     * tests method writeKontrolldatei() from class KontrollDatei_Writer Reader
     * by using three normal Student objects (StudentAlumnifile objects).
     * @throws ExecutionException exception type.
     */
    @Test
    public void writeKontrollDateiTest() throws ExecutionException {
        List<StudentControlfile> list = new LinkedList<>();
        StudentControlfile studentControlfile1 = new StudentControlfile(
                "2925341", "Herr", "M.Sc.",
                "Master of Science", "Daniel", "Malcolm", "Informatik",
                "Forschung in der KI",
                false, "Professor Hans Gutenberg", Auszeichnung.NONE, true,
                "1,7", "Gut");

        StudentControlfile studentControlfile2 = new StudentControlfile(
                "272341", "Frau", "B.Sc.",
                "Bachelor of Science", "Lukas", "Gutenberg", "Mechatronik",
                "Autoindustrie",
                true, "Professor Lenard Reicher", Auszeichnung.SILVER, false,
                "1,0", "Sehr Gut");

        StudentControlfile studentControlfile3 = new StudentControlfile(
                "N/A", "N/A", "N/A",
                "N/A", "N/A", "N/A", "N/A",
                "N/A",
                false, "N/A", Auszeichnung.NONE, false,
                "N/A", "N/A");

        list.add(studentControlfile1);
        list.add(studentControlfile2);
        list.add(studentControlfile3);

        ControlFileReader excelFile = new ControlFileReader(filePath);

        ControlFileWriter.writeKontrolldatei(filePath, list, null);

        List<StudentControlfile> excel = excelFile.readAll();
        assertEquals(studentControlfile1.getMatrikelnr(),
                excel.get(0).getMatrikelnr());
        assertEquals(studentControlfile1.getAnrede(),
                excel.get(0).getAnrede());
        assertEquals(studentControlfile1.getAbschlussAbkuerzung(),
                excel.get(0).getAbschlussAbkuerzung());
        assertEquals(studentControlfile1.getAbschlussart(),
                excel.get(0).getAbschlussart());
        assertEquals(studentControlfile1.getVorname(),
                excel.get(0).getVorname());
        assertEquals(studentControlfile1.getNachname(),
                excel.get(0).getNachname());
        assertEquals(studentControlfile1.getStudiengang(),
                excel.get(0).getStudiengang());
        assertEquals(studentControlfile1.getAbschlussarbeit(),
                excel.get(0).getAbschlussarbeit());
        assertEquals(studentControlfile1.getDoubleDegree(),
                excel.get(0).getDoubleDegree());
        assertEquals(studentControlfile1.getPrueferIn(),
                excel.get(0).getPrueferIn());
        assertEquals(studentControlfile1.getAuszeichnung(),
                excel.get(0).getAuszeichnung());
        assertEquals(studentControlfile1.getWillHaveSlide(),
                excel.get(0).getWillHaveSlide());
        assertEquals(studentControlfile1.getAbschlussNote(),
                excel.get(0).getAbschlussNote());
        assertEquals(studentControlfile1.getUrteil(),
                excel.get(0).getUrteil());


        assertEquals(studentControlfile2.getMatrikelnr(),
                excel.get(1).getMatrikelnr());
        assertEquals(studentControlfile2.getAnrede(),
                excel.get(1).getAnrede());
        assertEquals(studentControlfile2.getAbschlussAbkuerzung(),
                excel.get(1).getAbschlussAbkuerzung());
        assertEquals(studentControlfile2.getAbschlussart(),
                excel.get(1).getAbschlussart());
        assertEquals(studentControlfile2.getVorname(),
                excel.get(1).getVorname());
        assertEquals(studentControlfile2.getNachname(),
                excel.get(1).getNachname());
        assertEquals(studentControlfile2.getStudiengang(),
                excel.get(1).getStudiengang());
        assertEquals(studentControlfile2.getAbschlussarbeit(),
                excel.get(1).getAbschlussarbeit());
        assertEquals(studentControlfile2.getDoubleDegree(),
                excel.get(1).getDoubleDegree());
        assertEquals(studentControlfile2.getPrueferIn(),
                excel.get(1).getPrueferIn());
        assertEquals(studentControlfile2.getAuszeichnung(),
                excel.get(1).getAuszeichnung());
        assertEquals(studentControlfile2.getWillHaveSlide(),
                excel.get(1).getWillHaveSlide());
        assertEquals(studentControlfile2.getAbschlussNote(),
                excel.get(1).getAbschlussNote());
        assertEquals(studentControlfile2.getUrteil(),
                excel.get(1).getUrteil());

        assertEquals(studentControlfile3.getMatrikelnr(),
                excel.get(2).getMatrikelnr());
        assertEquals(studentControlfile3.getAnrede(),
                excel.get(2).getAnrede());
        assertEquals(studentControlfile3.getAbschlussAbkuerzung(),
                excel.get(2).getAbschlussAbkuerzung());
        assertEquals(studentControlfile3.getAbschlussart(),
                excel.get(2).getAbschlussart());
        assertEquals(studentControlfile3.getVorname(),
                excel.get(2).getVorname());
        assertEquals(studentControlfile3.getNachname(),
                excel.get(2).getNachname());
        assertEquals(studentControlfile3.getStudiengang(),
                excel.get(2).getStudiengang());
        assertEquals(studentControlfile3.getAbschlussarbeit(),
                excel.get(2).getAbschlussarbeit());
        assertEquals(studentControlfile3.getDoubleDegree(),
                excel.get(2).getDoubleDegree());
        assertEquals(studentControlfile3.getPrueferIn(),
                excel.get(2).getPrueferIn());
        assertEquals(studentControlfile3.getAuszeichnung(),
                excel.get(2).getAuszeichnung());
        assertEquals(studentControlfile3.getWillHaveSlide(),
                excel.get(2).getWillHaveSlide());
        assertEquals(studentControlfile3.getAbschlussNote(),
                excel.get(2).getAbschlussNote());
        assertEquals(studentControlfile3.getUrteil(),
                excel.get(2).getUrteil());

    }

    /**
     * tests method writeKontrolldatei() from class KontrollDatei_Writer Reader
     * by using a Filepath to a non-existent xlsx-file
     * and checks that the FilNotFoundException is thrown.
     * @throws ExecutionException exception type.
     */
    @Test
    public void nonExistentWriteKontrollDateiTest() throws
            ExecutionException {
        StudentControlfile studentControlfile = new StudentControlfile("");
        List<StudentControlfile> list = new LinkedList<>();
        list.add(studentControlfile);

        Exception exception = assertThrows(ExecutionException.class, () -> {
            ControlFileWriter.writeKontrolldatei(nonExistentFile, list, null);
        });

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof FileNotFoundException);
    }


    /**
     * tests method writeKontrolldatei() from class KontrollDatei_Writer Reader
     * by using an empty xlsx-File and checks that all fields
     * are not assigned/false.
     * @throws ExecutionException exception type.
     */
    @Test
    public void writeEmptyKontrollDateiTest() throws ExecutionException {

        List<StudentControlfile> emptyList = new LinkedList<>();
        StudentControlfile studentControlfile = new StudentControlfile(
                "N/A", "N/A", "N/A",
                "N/A", "N/A", "N/A", "N/A",
                "N/A", false, "N/A",
                Auszeichnung.NONE, false, "N/A", "N/A"
        );
                emptyList.add(studentControlfile);

        ControlFileReader excelFile = new ControlFileReader(emptyFile);
        ControlFileWriter.writeKontrolldatei(
                emptyFile, emptyList, null);
        List<StudentControlfile> excel = excelFile.readAll();
        assertEquals(studentControlfile.getMatrikelnr(),
                excel.get(0).getMatrikelnr());
        assertEquals(studentControlfile.getAnrede(),
                excel.get(0).getAnrede());
        assertEquals(studentControlfile.getAbschlussAbkuerzung(),
                excel.get(0).getAbschlussAbkuerzung());
        assertEquals(studentControlfile.getAbschlussart(),
                excel.get(0).getAbschlussart());
        assertEquals(studentControlfile.getVorname(),
                excel.get(0).getVorname());
        assertEquals(studentControlfile.getNachname(),
                excel.get(0).getNachname());
        assertEquals(studentControlfile.getStudiengang(),
                excel.get(0).getStudiengang());
        assertEquals(studentControlfile.getAbschlussarbeit(),
                excel.get(0).getAbschlussarbeit());
        assertEquals(studentControlfile.getDoubleDegree(),
                excel.get(0).getDoubleDegree());
        assertEquals(studentControlfile.getPrueferIn(),
                excel.get(0).getPrueferIn());
        assertEquals(studentControlfile.getAuszeichnung(),
                excel.get(0).getAuszeichnung());
        assertEquals(studentControlfile.getWillHaveSlide(),
                excel.get(0).getWillHaveSlide());
        assertEquals(studentControlfile.getAbschlussNote(),
                excel.get(0).getAbschlussNote());
        assertEquals(studentControlfile.getUrteil(),
                excel.get(0).getUrteil());
    }

       /**
        * This method is executed after each test method in the test class.
        * It cleans up any temporary files created during testing.
        * It deletes the files specified by their file paths.
        * If the file does not exist, it will not throw an exception.
        */
        @AfterEach
        public void cleanUp() {
                try {
                        Files.deleteIfExists(Paths.get(filePath));
                        Files.deleteIfExists(Paths.get(emptyFile));
                        assertFalse(Files.deleteIfExists(
                                Paths.get(nonExistentFile)));
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

}




