package tud.bp.group32.utilities;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import tud.bp.group32.constants.DefaultProperties;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DynamicPropertiesTest {
    private final String BACK_UP = "src/test/resources/ultilities/DefaultBackup.properties";

    /**
     * Initialize the DefaultProperties-file as backup and also the DynamicProperties-Object
     * @throws ExecutionException Should only happen with open/close the IO.
     */
    @BeforeAll
    public void createSetback() throws ExecutionException {
        try {
            Properties backup = DefaultProperties.getDefaults();
            backup.store(
                    new FileOutputStream(BACK_UP), "From backup");
            DynamicProperties.initProperties(null);
            // Read the backup file and remove the second line
            List<String> lines = Files.readAllLines(Paths.get(BACK_UP));
            lines.remove(1); // Remove the second line to keep it consistent between versions
            Files.write(Paths.get(BACK_UP), lines);
        } catch (Exception e) {
            System.out.println("Error when storing the file during test: DefaultBackup.properties");
            throw new ExecutionException(e);
        }
    }

    /**
     * Method used to clean up the values in DynamicProperties-Object
     * by loading the default values back.
     */
    @AfterEach
    public void clean() {
        DynamicProperties.loadNewProperties(BACK_UP, null);
    }


    /**
     * Test for comparing the default values and actual parsed values
     */
    @Test
    public void propertiesTest() {
        //general
        Assertions.assertEquals(
                DefaultProperties.GROUP_SIZE,
                DynamicProperties.GROUP_SIZE);
        Assertions.assertEquals(
                DefaultProperties.SHOW_BOX_RIGHT_SIDE,
                DynamicProperties.SHOW_BOX_RIGHT_SIDE);
        Assertions.assertEquals(
                DefaultProperties.JA,
                DynamicProperties.JA);
        Assertions.assertEquals(
                DefaultProperties.NEIN,
                DynamicProperties.NEIN);

        //Alumni
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.ALUMNI_MATRIKELNR),
                DynamicProperties.ALUMNI_MATRIKELNR);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.ALUMNI_VORNAME),
                DynamicProperties.ALUMNI_VORNAME);
        Assertions.assertEquals(StudentUtils.columnNameToIndex(
                DefaultProperties.ALUMNI_NACHNAME),
                DynamicProperties.ALUMNI_NACHNAME);
        Assertions.assertEquals(
                DefaultProperties.ALUMNI_CSV_SEPARATOR,
                DynamicProperties.ALUMNI_CSV_SEPARATOR);

        //SP015
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_MATRIKELNR),
                DynamicProperties.SP015_MATRIKELNR);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_ABSCHLUSSABKUERZUNG),
                DynamicProperties.SP015_ABSCHLUSSABKUERZUNG);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_ABSCHLUSSART),
                DynamicProperties.SP015_ABSCHLUSSART);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_ANREDE),
                DynamicProperties.SP015_ANREDE);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_NACHNAME),
                DynamicProperties.SP015_NACHNAME);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_VORNAME),
                DynamicProperties.SP015_VORNAME);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_STUDIENGANG),
                DynamicProperties.SP015_STUDIENGANG);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_URTEIL),
                DynamicProperties.SP015_URTEIL);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_PRUEFERIN),
                DynamicProperties.SP015_PRUEFERIN);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_ABSCHLUSSARBEIT),
                DynamicProperties.SP015_ABSCHLUSSARBEIT);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_DOUBLEDEGREE),
                DynamicProperties.SP015_DOUBLEDEGREE);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.SP015_ABSCHLUSSNOTE),
                DynamicProperties.SP015_ABSCHLUSSNOTE);

        //Freitagsliste
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_NACHNAME),
                DynamicProperties.FREITAG_NACHNAME);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_VORNAME),
                DynamicProperties.FREITAG_VORNAME);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_MATRIKELNR),
                DynamicProperties.FREITAG_MATRIKELNR);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_ABSCHLUSSABKUERZUNG),
                DynamicProperties.FREITAG_ABSCHLUSSABKUERZUNG);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_STUDIENGANG),
                DynamicProperties.FREITAG_STUDIENGANG);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_DOUBLEDEGREE),
                DynamicProperties.FREITAG_DOUBLEDEGREE);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.FREITAG_AUSZEICHNUNG),
                DynamicProperties.FREITAG_AUSZEICHNUNG);

        //Control file column indexes
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_MATRIKELNR),
                DynamicProperties.CONTROL_MATRIKELNR);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_ANREDE),
                DynamicProperties.CONTROL_ANREDE);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_NACHNAME),
                DynamicProperties.CONTROL_NACHNAME);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_VORNAME),
                DynamicProperties.CONTROL_VORNAME);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_ABSCHLUSS_ART),
                DynamicProperties.CONTROL_ABSCHLUSS_ART);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_DD),
                DynamicProperties.CONTROL_DD);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_ABSCHLUSS_ABK),
                DynamicProperties.CONTROL_ABSCHLUSS_ABK);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_STUDIENGANG),
                DynamicProperties.CONTROL_STUDIENGANG);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_ABSCHLUSS_NOTE),
                DynamicProperties.CONTROL_ABSCHLUSS_NOTE);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_URTEIL),
                DynamicProperties.CONTROL_URTEIL);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_PRUEFERIN),
                DynamicProperties.CONTROL_PRUEFERIN);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_ABSCH_ARBEIT),
                DynamicProperties.CONTROL_ABSCH_ARBEIT);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_WILL_HAVE_SLIDE),
                DynamicProperties.CONTROL_WILL_HAVE_SLIDE);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_IS_IN_SP015),
                DynamicProperties.CONTROL_IS_IN_SP015);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_IS_IN_ALUMNI),
                DynamicProperties.CONTROL_IS_IN_ALUMNI);
        Assertions.assertEquals(
                StudentUtils.columnNameToIndex(DefaultProperties.CONTROL_COMMENT),
                DynamicProperties.CONTROL_COMMENT);

        //Control file header names
        Assertions.assertEquals(
                DefaultProperties.HEADER_MATRIKELNR,
                DynamicProperties.HEADER_MATRIKELNR);
        Assertions.assertEquals(
                DefaultProperties.HEADER_ANREDE,
                DynamicProperties.HEADER_ANREDE);
        Assertions.assertEquals(
                DefaultProperties.HEADER_NACHNAME,
                DynamicProperties.HEADER_NACHNAME);
        Assertions.assertEquals(
                DefaultProperties.HEADER_VORNAME,
                DynamicProperties.HEADER_VORNAME);
        Assertions.assertEquals(
                DefaultProperties.HEADER_ABSCHLUSS,
                DynamicProperties.HEADER_ABSCHLUSS);
        Assertions.assertEquals(
                DefaultProperties.HEADER_DD,
                DynamicProperties.HEADER_DD);
        Assertions.assertEquals(
                DefaultProperties.HEADER_ABSCHLUSS_ABK,
                DynamicProperties.HEADER_ABSCHLUSS_ABK);
        Assertions.assertEquals(
                DefaultProperties.HEADER_STUDIENGANG,
                DynamicProperties.HEADER_STUDIENGANG);
        Assertions.assertEquals(
                DefaultProperties.HEADER_ABSCHLUSSNOTE,
                DynamicProperties.HEADER_ABSCHLUSSNOTE);
        Assertions.assertEquals(
                DefaultProperties.HEADER_URTEIL,
                DynamicProperties.HEADER_URTEIL);
        Assertions.assertEquals(
                DefaultProperties.HEADER_THESIS_VORSITZ,
                DynamicProperties.HEADER_THESIS_VORSITZ);
        Assertions.assertEquals(
                DefaultProperties.HEADER_THESIS,
                DynamicProperties.HEADER_THESIS);
        Assertions.assertEquals(
                DefaultProperties.HEADER_WILL_HAVE_SLIDE,
                DynamicProperties.HEADER_WILL_HAVE_SLIDE);
        Assertions.assertEquals(
                DefaultProperties.HEADER_SP015_PRESENT,
                DynamicProperties.HEADER_SP015_PRESENT);
        Assertions.assertEquals(
                DefaultProperties.HEADER_ALUMNI_PRESENT,
                DynamicProperties.HEADER_ALUMNI_PRESENT);
        Assertions.assertEquals(
                DefaultProperties.HEADER_COMMENT,
                DynamicProperties.HEADER_COMMENT);
        /*
        Missing some attributes
        SLIDES_STUDENT_FONT_SIZE_LG, SLIDES_STUDENT_FONT_SIZE_SM,
        SLIDES_GROUPFOTO_FONT_SIZE_LG, SLIDES_GROUPFOTO_FONT_SIZE_SM,
        SLIDES_DEGREE_CONTENT_FONT_SIZE

        And titles as well
         */
    }

    /**
     * Test case checks when a value of a property is correctly changed
     * If this test fails at the line checking whether the groupsize read
     * from the file is correct, then that means that the file was not read
     * due to missing properties that were not added to the test file.
     * @throws ExecutionException Should only happen with open/close the IO.
     */
    @Test
    public void regularTest() throws ExecutionException {
        try {
            propertiesTest();
            String newFile  = "src/test/resources/ultilities/PropertiesRegularFile.properties";
            DynamicProperties.loadNewProperties(newFile, null);
            Assertions.assertEquals(6, DynamicProperties.GROUP_SIZE);
            Assertions.assertEquals(StudentUtils.columnNameToIndex("B"), DynamicProperties.SP015_MATRIKELNR);
        } catch (Exception e) {
            System.out.println("Error when storing the file during test: PropertiesRegularFile.properties");
            throw new ExecutionException(e);
        }
    }

    /**
     * Test cases for ensuring the safety of DynamicProperties
     * when it opens a faulty file.
     */
    @Test
    public void faultyTest() {
        DynamicProperties.loadNewProperties("NotExistingFile.properties", null);
        //to highlight that the older value(s) is used when the new properties-file is faulty
        Assertions.assertEquals(
                DefaultProperties.GROUP_SIZE,
                DynamicProperties.GROUP_SIZE);

        DynamicProperties.loadNewProperties(null, null);
        Assertions.assertEquals(
                DefaultProperties.GROUP_SIZE,
                DynamicProperties.GROUP_SIZE);

        DynamicProperties.loadNewProperties("src/test/resources/ultilities/PropertiesFaultyFile.properties", null);
        Assertions.assertEquals(
                DefaultProperties.GROUP_SIZE,
                DynamicProperties.GROUP_SIZE);
    }
}
