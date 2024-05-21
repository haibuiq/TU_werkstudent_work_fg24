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

public final class DegreeSlide {
    /**
     * Private constructor to hide the implicit public one.
     */
    protected DegreeSlide() {
        throw new UnsupportedOperationException();
    }

    /**
     * Title text box name in the Blank-Layout.
     */
    private static final String LAYOUT_NAME_BOX_FRAME   = "Title Content";
    /**
     * Content text box name in the Blank-Layout.
     */
    private static final String CONTENT_SHAPE_NAME      = "Inhaltsplatzhalter";
    /**
     * Font Size Property of the text.
     */
    private static final double FONTSIZE_CONTENT =
            DynamicProperties.SLIDES_DEGREE_CONTENT_FONT_SIZE;

    /**
     * Font Color Property of the text.
     */
    private static final Color FONT_COLOR = Color.black;

    /**
     * Create a new slide as standard Overlay for the upcoming slides with the
     * Blank-Layout as foundation from the given pptx-template.
     * @param slideShow The Slideshow object which the new slide would belong to
     * @param degree    The degree-string
     */
    public static void generate(
            final XMLSlideShow slideShow, final String degree) {
        XSLFSlide slide =  OtherSlideSetting.createSlideAsBlank(slideShow);

        OtherSlideSetting.settingTitle(slide, Auszeichnung.NONE);
        Rectangle2D anchor = OtherSlideSetting.getAnchorWithString(
                CONTENT_SHAPE_NAME,
                LAYOUT_NAME_BOX_FRAME,
                slide.getSlideShow());
        settingContent(slide, degree, anchor);
        //might not need this anymore
        OtherSlideSetting.settingDateAndSlideNumber(slide);
    }

    /**
     * Create a new slide as Overlay, with a list of students included on the
     * right side(the appearance is the same as with the GroupfotoSlide format).
     * @param slideShow The Slideshow object which the new slide would belong to
     * @param degree The degree-string
     * @param next  List of the upcoming students
     */
    public static void generateWithStudents(
            final XMLSlideShow slideShow,
            final String degree,
            final StudentControlfile[] next) {
        XSLFSlide slide = OtherSlideSetting.createSlideAsBlank(slideShow);
        OtherSlideSetting.settingTitle(slide, Auszeichnung.NONE);
        XSLFTextBox box = GroupfotoSlideGenerator
                .settingNextStudents(slide, next);
        Rectangle2D prevAnchor = OtherSlideSetting.getAnchorWithString(
                CONTENT_SHAPE_NAME,
                LAYOUT_NAME_BOX_FRAME,
                slide.getSlideShow());
        Rectangle2D newAnchor  = new Rectangle2D.Double(
                prevAnchor.getX(),
                prevAnchor.getY(),
                prevAnchor.getWidth() - box.getAnchor().getWidth(),
                prevAnchor.getHeight());
        settingContent(slide, degree, newAnchor);
    }

    /**
     * Set the text in the middle of the given box.
     * @param slide     The overlay slide
     * @param degree    The degree-string
     * @param anchor    The anchor for the degree-string
     */
    private static void settingContent(
            final XSLFSlide slide,
            final String degree,
            final Rectangle2D anchor) {
        XSLFTextBox tsh = slide.createTextBox();

        tsh.setAnchor(anchor);
        tsh.setText(degree);
        tsh.setTextDirection(TextShape.TextDirection.HORIZONTAL);
        tsh.setVerticalAlignment(VerticalAlignment.MIDDLE);
        tsh.setHorizontalCentered(true);

        XSLFTextParagraph tp = tsh.getTextParagraphs()
                .get(OtherSlideSetting.getFirstItem());
        XSLFTextRun tr = tp.getTextRuns().get(OtherSlideSetting.getFirstItem());

        tp.setTextAlign(TextParagraph.TextAlign.CENTER);
        tr.setFontSize(FONTSIZE_CONTENT);
        tr.setBold(true);

        tr.setFontColor(FONT_COLOR);
    }
}
