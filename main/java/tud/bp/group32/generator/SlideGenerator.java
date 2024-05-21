package tud.bp.group32.generator;

import tud.bp.group32.generatorutils.StudentControlFileIterator;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.utilities.StudentUtils;

import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * @author haibuiq
 * This class was created with the purpose of containing the configuration for
 * the creation of a slide with just one student.
 */
public class SlideGenerator {
    protected SlideGenerator() {
        throw new UnsupportedOperationException();
    }
    /**
     * Loop for generating slides from a passed iterator.
     * @param ppt The PowerPoint object to add the slides to.
     * @param iterator The iterator to generate the slides from.
     * @return The last group of students, to be used as prev in the next call.
     */
    public static List<StudentControlfile> generateSlidesLoop(
            final XMLSlideShow ppt, final StudentControlFileIterator iterator) {
        List<StudentControlfile> prev = null;
        List<StudentControlfile> next = null;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (prev != null) {
                GroupfotoSlideGenerator.generate(
                        ppt,
                        StudentUtils.listToArray(prev),
                        StudentUtils.listToArray(next));
            }
            for (StudentControlfile student : next) {
                StudentSlideGenerator.generateSlideAsGroup(ppt, student);
            }
            prev = next;
        }
        return next;
    }

}
