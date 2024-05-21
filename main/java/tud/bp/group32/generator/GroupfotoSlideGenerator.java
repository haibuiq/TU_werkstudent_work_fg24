package tud.bp.group32.generator;

import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.utilities.DynamicProperties;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class GroupfotoSlideGenerator {
    protected GroupfotoSlideGenerator() {
        throw new UnsupportedOperationException();
    }
    /**
     * Title box name in the Blank-Layout.
     */
    private static final String LAYOUT_NAME_BOX_FRAME = "Title Content";
    /**
     * Content box name in the Blank-Layout.
     */
    private static final String CONTENT_SHAPE_NAME = "Inhaltsplatzhalter";

    /**
     * Text for the next students.
     */
    private static final String CONTENT_NEXT_STUDENTS = "nächste Studierende";


    /**
     * Font Size Property of the text.
     */
    private static final double CONTENT_BIG_FONT_SIZE =
            DynamicProperties.SLIDES_GROUPFOTO_FONT_SIZE_LG;
    /**
     * Font Size Property of the text.
     */
    private static final double CONTENT_SMALL_FONT_SIZE =
            DynamicProperties.SLIDES_GROUPFOTO_FONT_SIZE_SM;
    /**
     * Approximation of the height of hte small textbox.
     */
    private static final double CONTENT_SMALL_TEXT_HEIGHT =
            CONTENT_SMALL_FONT_SIZE * 2.5;


    /**
     * Color Property of the text.
     * NOTE: These could be changed to be dynamic in the future.
     */
    private static final Color BOX_COLOR = Color.white;
    /**
     * Color Property of the text.
     */
    private static final Color LINE_COLOR = Color.black;
    /**
     * Width of the line of the box.
     */
    private static final double LINE_WIDTH = 1.5;

    /**
     * The generated slide contains possibly 2 lists of names on the left and
     * right sides in the content box.
     * @param ppt   The presentation
     * @param prev  List of students that was previously shown on StudentSlide
     * @param next  List of (potential) next group of students
     */
    public static void generate(final XMLSlideShow ppt,
                                final StudentControlfile[] prev,
                                final StudentControlfile[] next) {
        XSLFSlide slide = OtherSlideSetting.createSlideAsBlank(ppt);

        OtherSlideSetting.settingTitle(slide, Auszeichnung.NONE);
        settingPrevStudents(slide, prev);
        if (next != null) {
            settingNextStudents(slide, next);
        }
        OtherSlideSetting.settingDateAndSlideNumber(slide);
    }

    /**
     * Prepare from the list of StudentControlFiles
     * the names of each student and write them out
     * on the left side of the slide.
     * @param slide The group photo slide
     * @param prev  List of previous students
     */
    private static void settingPrevStudents(final XSLFSlide slide,
                                            final StudentControlfile[] prev) {
        List<String> prevNames = Arrays.stream(prev)
                .map(student -> ""
                        .concat(student.getAnrede().contains("Herr")
                                ? "Hr. "
                                : student.getAnrede().contains("Frau")
                                ? "Fr. "
                                : "")
                        .concat(String.format("%s %s",
                                student.getVorname(),
                                student.getNachname())))
                .toList();
        createTextBoxHelper(slide, prevNames, true);
    }

    /**
     * Prepare from the list of StudentControlFiles the names of each student
     * and write them out on the right side of the slide.
     *
     * @param slide The group photo slide
     * @param next  List of upcoming students
     * @return  The Text box object with the names of students.
     */
    public static XSLFTextBox settingNextStudents(
            final XSLFSlide slide,
            final StudentControlfile[] next) {
        List<String> nextNames = Arrays.stream(next)
                .map(student -> "".concat(
                        String.format("%c. %s",
                                student.getVorname().charAt(0),
                                student.getNachname())))
                .toList();
        return createTextBoxHelper(slide, nextNames, false);
    }

    /**
     * Create an invisible box with the list of student.
     *
     * @param slide  The group photo slide
     * @param names  List of name of Students
     * @param isPrev True, if the names need to be on the left side,
     *               otherwise false.
     * @return       The Text box object with the names of students.
     */
    private static XSLFTextBox createTextBoxHelper(final XSLFSlide slide,
                                                   final List<String> names,
                                                   final boolean isPrev) {
        // anchor is the reference taken from slide master,
        // anchorParam is the actual anchor of the text boxes for the names
        Rectangle2D anchor = OtherSlideSetting.getAnchorWithString(
                CONTENT_SHAPE_NAME,
                LAYOUT_NAME_BOX_FRAME,
                slide.getSlideShow());
        Rectangle2D anchorParam;
        XSLFTextBox tb;
        // the text boxes on both side are variable based on amount of students
        if (isPrev) {
            anchorParam = new Rectangle2D.Double(
                    anchor.getX() + 10,
                    anchor.getY() + CONTENT_SMALL_TEXT_HEIGHT,
                    anchor.getWidth() / 2,
                    CONTENT_BIG_FONT_SIZE  * (names.size() + 1));
            tb = slide.createTextBox();
            tb.setAnchor(anchorParam);
        } else {
            anchorParam = new Rectangle2D.Double(
                        anchor.getX() + anchor.getWidth() / 2 - 10,
                        anchor.getY() + CONTENT_SMALL_TEXT_HEIGHT,
                        anchor.getWidth() / 2,
                        CONTENT_BIG_FONT_SIZE * (names.size() + 1));
            tb = createBoxRightSide(slide, anchorParam);
            createTextNextStudent(slide, anchorParam);
        }

//        XSLFTextBox
        tb.setTextDirection(TextShape.TextDirection.HORIZONTAL);
        tb.setTextAutofit(TextShape.TextAutofit.SHAPE);
        tb.setVerticalAlignment(VerticalAlignment.TOP);

        names.forEach(name -> {
            XSLFTextParagraph tp = tb.addNewTextParagraph();
            tp.setTextAlign(isPrev
                    ? TextParagraph.TextAlign.LEFT
                    : TextParagraph.TextAlign.RIGHT);
            XSLFTextRun tr = tp.addNewTextRun();
            tr.setText(name);
            tr.setBold(true);
            tr.setFontSize(CONTENT_BIG_FONT_SIZE);
        });

        anchorParam = tb.resizeToFitText();
        tb.setAnchor(anchorParam);
        return tb;
    }

    /**
     * Create a box which outlines the upcoming students.
     *
     * @param slide     The group photo slide
     * @param anchor    The anchor of the invisible text box on the right side
     * @return         The created box
     */
    private static XSLFTextBox createBoxRightSide(final XSLFSlide slide,
                                                final Rectangle2D anchor) {
        XSLFTextBox shape = slide.createTextBox();
        double width = anchor.getWidth();
        shape.setAnchor(new Rectangle2D.Double(
                anchor.getX() + width / 3,
                anchor.getY(),
                width / 3 * 2, anchor.getHeight()));

        //As AG wanted which is to only draw the outer line
        if (DynamicProperties.SHOW_BOX_RIGHT_SIDE) {
            shape.setLineColor(LINE_COLOR);
            shape.setLineWidth(LINE_WIDTH);
        }

        //Actual implementation later on( filled out box)
        shape.setFillColor(BOX_COLOR);
        return shape;
    }

    /**
     * Write the short text 'naechste Studierende' above the box
     * on the right side.
     * @param slide     The group photo slide
     * @param anchor    The anchor of the invisible text box on the right side
     */
    private static void createTextNextStudent(final XSLFSlide slide,
                                            final Rectangle2D anchor) {
        XSLFTextBox tb = slide.createTextBox();

        //config for text shape
        tb.setTextDirection(TextShape.TextDirection.HORIZONTAL);
        tb.setHorizontalCentered(true);
        double width = anchor.getWidth();
        //the text hast the same x-offset and width as the colored box
        tb.setAnchor(new Rectangle2D.Double(
                anchor.getX() + width / 3,
                anchor.getY() - CONTENT_SMALL_TEXT_HEIGHT,
                width / 3 * 2,
                CONTENT_SMALL_TEXT_HEIGHT));

        //config for text paragraph
        XSLFTextParagraph tp = tb.addNewTextParagraph();
        tp.setTextAlign(TextParagraph.TextAlign.CENTER);

        //text for run config
        XSLFTextRun tr = tp.addNewTextRun();
        tr.setText(CONTENT_NEXT_STUDENTS);
        tr.setBold(true);
        tr.setFontSize(CONTENT_SMALL_FONT_SIZE);
    }
}
