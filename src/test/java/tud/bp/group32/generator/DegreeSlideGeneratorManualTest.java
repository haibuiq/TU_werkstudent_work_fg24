package tud.bp.group32.generator;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for the functionality of the DegreeSlideGenerator
 */
public class DegreeSlideGeneratorManualTest extends SlideGeneratorTest{
    /**
     * Test ensure that the given string parameters will be taken accordingly.
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void regularTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/DegreeSlideRegular1.pptx");

            var test1 = new String[]{"Bachelor of Science", "Master of Science", "international Abschlüsse", "Doppeldiplom"};

            for (String s : test1) {
                DegreeSlide.generate(this.ppt, s);
            }
        } catch (IOException e) {
            throw new ExecutionException(e.getCause());
        }
    }

    /**
     * Test ensure that the program in the current state doesn't check for the content of the parameters
     * which means everything will be taken, even an empty string
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void regularTest2() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/DegreeSlideRegular2.pptx");

            var test2 = new String[]{"Happy Birthday", "", "1234 56 7+890"};

            for (String s : test2) {
                DegreeSlide.generate(this.ppt, s);
            }
        } catch (IOException e) {
            throw new ExecutionException(e.getCause());
        }
    }

    @Test
    public void regularWithStudentsTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/DegreeSlideRegular3.pptx");

            var test3 = new String[]{"Bachelor of Science", "Master of Science", "international Abschlüsse", "Doppeldiplom"};
            var sampleList = randomRegularStudents();

            for (String s : test3) {
                DegreeSlide.generateWithStudents(this.ppt, s, sampleList);
            }
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }

    //What could be done to avoid this/prevention of program crash ?
    /**
     * Test makes visible that the program doesn't check when parameter is null.
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void nullTest() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/DegreeSlideNullParam.pptx");

            /*
            Not NullPointer because when trying to access textRun/paragraph object, since the given String
            was 'null', the textRun/paragraph object wasn't created and therefore IndexOutOfBound (line 53)
             */
            assertThrows(
                    IndexOutOfBoundsException.class,
                    () -> DegreeSlide.generate(ppt, null),
                    "It should throw Error for null parameter!");
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }
}
