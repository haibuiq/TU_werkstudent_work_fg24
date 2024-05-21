package tud.bp.group32.generator;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.Studiengang;
import tud.bp.group32.constants.ShortArtOfAbschluss;
import tud.bp.group32.constants.ArtOfAbschluss;
import tud.bp.group32.constants.AnredeTyp;
import tud.bp.group32.student.StudentControlfile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SlideGeneratorTest {
    /**
     * Path to the template inside the project
     */
    protected final String FIS = "src/test/resources/generator/TUDa_PowerPoint_Sublogo_2023_etitlang.pptx";
    /**
     * FileOutputStream object used in the test
     */
    protected FileOutputStream fos;
    /**
     * SlideShow object used in the test
     */
    protected XMLSlideShow ppt;

    /**
     * Quick setup before every test for DegreeSlide
     * @throws ExecutionException    Should only happen when problem with file opening/closing occurs.
     */
    @BeforeEach
    public void setup() throws ExecutionException {
        try {
            FileInputStream fis = new FileInputStream(FIS);
            this.ppt = new XMLSlideShow(fis);
            while (!this.ppt.getSlides().isEmpty()) {
                this.ppt.removeSlide(0);
            }
//            fis.close();
        } catch (IOException e) {                       //FileNotFoundException is subclass of IOException
            throw new ExecutionException(e);
            //or from tearDown()? -> throw new ExecutionException(new RuntimeException(e));
        }
    }

    /**
     * This method writes the test output in the output file and closes the output stream afterwards.
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @AfterEach
    public void tearDown() throws ExecutionException {
        try {
            this.ppt.write(this.fos);

            this.fos.close();
            this.ppt.close();
        } catch (IOException e) {
            throw new ExecutionException(new RuntimeException(e));
        }
    }

    /**
     * Just a helper method to create 5 random students with regular/in the norm parameters.
     * @return  Array of 5 StudentControlfile objects
     */
    protected StudentControlfile[] randomRegularStudents() {
        StudentControlfile s0 = new StudentControlfile(
                "0", AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_ED,
                ArtOfAbschluss.ART_BACHELOR_SC, "Max", "Mustermann", Studiengang.ETIT_SHORT,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                false, "Lorem Ipsum passage",
                Auszeichnung.SILVER, false, "1.7", "");
        StudentControlfile s1 = new StudentControlfile(
                "1", AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_ED,
                ArtOfAbschluss.ART_BACHELOR_SC, "Jacob", "Mustermann",Studiengang.ETIT_SHORT,
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
                false, "Lorem Ipsum passage",
                Auszeichnung.NONE, false, "2.3", "");
        StudentControlfile s2 = new StudentControlfile(
                "2", AnredeTyp.FRAU, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
                ArtOfAbschluss.ART_BACHELOR_SC, "Eva", "Mustermann",Studiengang.ETIT_SHORT,
                "Thesis 2", false, "Pruefer 2",
                Auszeichnung.GOLD, true, "1.0", "");
        StudentControlfile s3 = new StudentControlfile(
                "3", AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
                ArtOfAbschluss.ART_MASTER_SC, "Max", "Müller",Studiengang.MEC_FULL,
                "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus",
                false, "de Finibus Bonorum et Malorum",
                Auszeichnung.SILVER, true, "1.4", "");
        StudentControlfile s4 = new StudentControlfile(
                "4", AnredeTyp.NOT_DEFINED, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
                ArtOfAbschluss.ART_BACHELOR_SC, "Erika", "Musterfrau",Studiengang.ETIT_FULL,
                "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue",
                false, "1914 translation by H. Rackham",
                Auszeichnung.NONE, true, "2.3", "");
        return new StudentControlfile[] {s0, s1, s2, s3, s4};
    }

    /**
     * Just a helper method to create 5 random students with extreme parameters
     * @return  Array of 5 StudentControlfile objects
     */
    protected StudentControlfile[] randomExtremeStudents() {
        StudentControlfile s0 = new StudentControlfile(
                "0", AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_ED,
                ArtOfAbschluss.ART_BACHELOR_SC, "Maximilian David", "Mustermann Hausberger", Studiengang.ETIT_SHORT,
                "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness",
                false, "1914 translation by H. Rackham",
                Auszeichnung.NONE, true, "2.3", "");
        StudentControlfile s1 = new StudentControlfile(
                "1", AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_ED,
                ArtOfAbschluss.ART_BACHELOR_SC, "Jacob Steven Jason", "Mustermann Müller",Studiengang.ETIT_SHORT,
                "Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
                false, "de Finibus Bonorum et Malorum",
                Auszeichnung.NONE, false, "2.3", "");
        StudentControlfile s2 = new StudentControlfile(
                "2", AnredeTyp.FRAU, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
                ArtOfAbschluss.ART_BACHELOR_SC, "Eva Sarah", "Mustermann Müller",Studiengang.ETIT_SHORT,
                "But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?",
                false, "1914 translation by H. Rackham",
                Auszeichnung.GOLD, true, "1.0", "");
        StudentControlfile s3 = new StudentControlfile(
                "3", AnredeTyp.HERR, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
                ArtOfAbschluss.ART_MASTER_SC, "Jonas Tim", "Müller Geiger",Studiengang.MEC_FULL,
                "These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided",
                false, "1914 translation by H. Rackham",
                Auszeichnung.SILVER, true, "1.4", "");
        StudentControlfile s4 = new StudentControlfile(
                "4", AnredeTyp.NOT_DEFINED, ShortArtOfAbschluss.SHORT_BACHELOR_SC,
                ArtOfAbschluss.ART_BACHELOR_SC, "Erika Silvia Josephine", "Musterfrau Müller",Studiengang.ETIT_FULL,
                "Thesis 4", false, "Pruefer 1",
                Auszeichnung.NONE, true, "2.3", "");
        return new StudentControlfile[] {s0, s1, s2, s3, s4};
    }
}