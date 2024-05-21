package tud.bp.group32.generator;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class TableSlideGeneratorManualTest extends SlideGeneratorTest {
    /**
     * Test ensure that the given string parameters will be taken accordingly.
     * @throws ExecutionException   Should only happen when problem with file opening/closing occurs.
     */
    @Test
    public void regularTest1() throws ExecutionException {
        try {
            this.fos = new FileOutputStream("src/test/resources/generator/TableSlideRegular1.pptx");

            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i : new int[]{1, 2, 3, 4, 5}) {
              numbers.add(i);
            }
            TableSlideGenerator.generateSlide(ppt, numbers);
        } catch (IOException e) {
            throw new ExecutionException(e.getCause());
        }
    }

}
