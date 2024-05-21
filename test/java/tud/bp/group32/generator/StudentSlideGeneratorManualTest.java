package tud.bp.group32.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tud.bp.group32.student.StudentControlfile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StudentSlideGeneratorManualTest extends SlideGeneratorTest {
    /**
     * Test case for regular parameters.
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void regularTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/StudentSlideRegular1.pptx");

            var testObj = randomRegularStudents();

            for(StudentControlfile st : testObj) {
                StudentSlideGenerator.generateSlideAsGroup(this.ppt, st);
            }
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }

    /**
     * Test case for extreme parameters (e.g. too long, too many, ...)
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void extremeTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/StudentSlideExtreme1.pptx");

            var testObj = randomExtremeStudents();

            for(StudentControlfile st : testObj) {
                StudentSlideGenerator.generateSlideAsGroup(this.ppt, st);
            }
        } catch (FileNotFoundException e) {
            throw new ExecutionException(e);
        }
    }

    /**
     * Test case when the student parameter is null, a NullPointerException should be thrown
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void nullParamTest() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/StudentSlideNullParam.pptx");

            Assertions.assertThrows(
                    NullPointerException.class,
                    () -> StudentSlideGenerator.generateSlideAsGroup(this.ppt, null));
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }
}
