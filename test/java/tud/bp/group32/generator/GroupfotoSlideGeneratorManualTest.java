package tud.bp.group32.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tud.bp.group32.student.StudentControlfile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GroupfotoSlideGeneratorManualTest extends SlideGeneratorTest{
    /**
     * Test case for regular names.
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void regularTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/GroupfotoSlideRegular1.pptx");

            var testObj5 = randomRegularStudents();
            var testObj4 = new StudentControlfile[]
                    {testObj5[4], testObj5[2], testObj5[3], testObj5[0]};
            var testObj3 = new StudentControlfile[]
                    {testObj5[1], testObj5[3], testObj5[4]};
            var testObj2 = new StudentControlfile[]{testObj5[2], testObj5[0]};
            var testObj1 = new StudentControlfile[]{testObj5[3]};

            GroupfotoSlideGenerator.generate(ppt, testObj1, testObj1);
            GroupfotoSlideGenerator.generate(ppt, testObj2, testObj2);
            GroupfotoSlideGenerator.generate(ppt, testObj3, testObj3);
            GroupfotoSlideGenerator.generate(ppt, testObj4, testObj4);
            GroupfotoSlideGenerator.generate(ppt, testObj5, testObj5);
            GroupfotoSlideGenerator.generate(ppt, testObj2, null);

        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }

    /**
     * Test case for extreme names (e.g. too long, too many, ...)
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void extremeTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/GroupfotoSlideExtreme1.pptx");

            StudentControlfile[] testObj = randomExtremeStudents();

            GroupfotoSlideGenerator.generate(this.ppt, testObj, testObj);
            GroupfotoSlideGenerator.generate(this.ppt, testObj, null);
        } catch (FileNotFoundException e) {
            throw new ExecutionException(e);
        }
    }

    /**
     * The generator for Groupfoto slide is not responsible for input.
     * This test case checks the case when prev-array is null or has the size 0
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void nullPrevTest() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/GroupfotoSlideNullParam.pptx");

            Assertions.assertThrows(
                    NullPointerException.class,
                    () -> GroupfotoSlideGenerator.generate(this.ppt,null, null));
            GroupfotoSlideGenerator.generate(this.ppt, new StudentControlfile[0], null);
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }
}
