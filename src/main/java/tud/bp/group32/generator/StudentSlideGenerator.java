package tud.bp.group32.generator;

import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.ImageLoader;
import tud.bp.group32.constants.Auszeichnung;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFGroupShape;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.Insets2D;



/**
 * The class is used to generate a slide for a student.
*/
public class StudentSlideGenerator {
    protected StudentSlideGenerator() {
        throw new UnsupportedOperationException();
    }
    /**
     * Constants for the slide layout and the shape name.
     */
    private static final String LAYOUT_NAME_FRAMEWORK = "Title Content";
    /**
     * The name of the shape that holds the content of the student.
     */
    private static final String SHAPE_CONTENT_NAME = "Inhaltsplatzhalter";
    /**
     * The text that will be shown before the thesis title.
     */
    private static final String PRE_THESIS = "Titel der Abschlussarbeit:";

    /**
     * Path to the gold badge relative to the resources folder.
     */
    private static final String GOLD_BADGE_PATH = "images/gold-badge.png";
    /**
     * Path to the silver badge relative to the resources folder.
     */
    private static final String SILVER_BADGE_PATH = "images/silver-badge.png";


    /**
     * Constants for the font size and the spacing of the content.
     */
    private static final double FONT_SIZE_CONTENT_SMALL =
            DynamicProperties.SLIDES_STUDENT_FONT_SIZE_SM;
    /**
     * The font size for the big content.
     */
    private static final double FONT_SIZE_CONTENT_BIG =
            DynamicProperties.SLIDES_STUDENT_FONT_SIZE_LG;
    /**
     * The spacing for the small content.
     */
    private static final double SPACING_CONTENT_SMALL = 2.;
    /**
     * The spacing for the big content.
     */
    private static final double SPACING_CONTENT_BIG = 4.;
    /**
     * The bottom offset for the content.
     */
    private static final double BOTTOM_OFFSET = 500;

    /**
     * The maximum lines for the thesis text.
     */
    private static final double THESIS_MAX_LINES = 3;


    /**
     * Generate a Student-slide with the idea that the content (shapes)
     * of the student are holding on another anchor,
     * (which has the slide as anchor) and not the slide itself.
     * @param ppt       The presentation that need the slide
     * @param student   A Student object that have all the necessary information
     */
    public static void generateSlideAsGroup(final XMLSlideShow ppt,
                        final StudentControlfile student) {
        XSLFSlide slide = OtherSlideSetting.createSlideAsBlank(ppt);

        OtherSlideSetting.settingTitle(slide, student.getAuszeichnung());
        settingBadge(slide, student.getAuszeichnung());
        settingContent(slide, student);

        OtherSlideSetting.settingDateAndSlideNumber(slide);
    }

    /**
     * Method places information of the given student object
     * as predefined in the slide.
     * @param slide     The slide for the student information
     * @param student   The StudentControlFile object
     */
    private static void settingContent(
            final XSLFSlide slide,
            final StudentControlfile student) {
        XSLFGroupShape group = slide.createGroup();

        Rectangle2D anchorContent =
                OtherSlideSetting.getAnchorWithString(
                        SHAPE_CONTENT_NAME,
                        LAYOUT_NAME_FRAMEWORK,
                        slide.getSlideShow());
        group.setAnchor(new Rectangle2D.Double(
                slide.getSlideShow().getPageSize().getWidth() / 6,
                anchorContent.getY(),
                slide.getSlideShow().getPageSize().getWidth() / 3 * 2,
                anchorContent.getHeight()));
        OtherSlideSetting.settingDateAndSlideNumber(slide);

        //1 bc I want to make sure that all boxes are inside the group shape
        double nextOffsetY = 1;
        //setting honorifics
        nextOffsetY = configContent(
                group,
                student.getAnrede(),
                nextOffsetY, FONT_SIZE_CONTENT_BIG, SPACING_CONTENT_BIG);
        //setting degree
        nextOffsetY = configContent(
                group,
                student.getAbschlussAbkuerzung(),
                nextOffsetY, FONT_SIZE_CONTENT_BIG, SPACING_CONTENT_BIG);
        //setting name
        nextOffsetY = configContent(
                group,
                String.format("%s %s", student.getVorname(),
                                student.getNachname()),
                nextOffsetY, FONT_SIZE_CONTENT_BIG, SPACING_CONTENT_BIG);
        //setting major
        nextOffsetY = configContent(
                group,
                student.getStudiengang(),
                nextOffsetY, FONT_SIZE_CONTENT_SMALL, SPACING_CONTENT_SMALL);
        //setting pre-thesis
        nextOffsetY = configContent(
                group,
                PRE_THESIS,
                nextOffsetY, FONT_SIZE_CONTENT_SMALL, SPACING_CONTENT_SMALL);
        //setting thesis
        nextOffsetY = configContent(
                group,
                student.getAbschlussarbeit(),
                nextOffsetY, FONT_SIZE_CONTENT_SMALL, SPACING_CONTENT_SMALL);
        //setting examiner
        nextOffsetY = configContent(
                group,
                student.getPrueferIn(),
                nextOffsetY, FONT_SIZE_CONTENT_SMALL, 0.);
        //fix box if necessary
        // (nextOffsetY becomes the surplus offset from over the 500px mark)
        nextOffsetY += group.getAnchor().getY() - BOTTOM_OFFSET;
        if (nextOffsetY > 0) {
            fixBox(group, slide.getSlideShow().getPageSize().getWidth());
        }
    }

    /**
     * Setting the text as content of the student with the given parameters
     * as configurations of the text.
     * @param group     The anchor of all the texts
     * @param txt       Text that need to be set
     * @param offsetY   Off-set in y-axis from the top of the anchor
     * @param fontSize  Font size for the text
     * @param spacing   Space between this box and the next one
     * @return          The next Y - offset
     */
    private static double configContent(
            final XSLFGroupShape group,
            final String txt,
            final double offsetY,
            final double fontSize,
            final double spacing) {
        Rectangle2D anchor = group.getAnchor();
        XSLFTextBox tb = group.createTextBox();
        tb.setAnchor(new Rectangle2D.Double(1, offsetY,
                                        anchor.getWidth(), fontSize));
        tb.setText(txt);

        XSLFTextParagraph tp = tb.getTextParagraphs().get(
                                OtherSlideSetting.getFirstItem());
        XSLFTextRun tr = tp.getTextRuns().get(
                                OtherSlideSetting.getFirstItem());

        //config for text paragraph
        tp.setTextAlign(TextParagraph.TextAlign.CENTER);

        //config for text run
        tr.setFontSize(fontSize);
        tr.setBold(true);


        //config for text shape/box
        tb.setAnchor(tb.resizeToFitText());
        tb.setTextDirection(TextShape.TextDirection.HORIZONTAL);
        tb.setTextAutofit(TextShape.TextAutofit.SHAPE);
        tb.setVerticalAlignment(VerticalAlignment.MIDDLE);

        return spacing + tb.getAnchor().getMaxY();
    }

    private static void settingBadge(
            final XSLFSlide slide,
            final Auszeichnung auszeichnung) {
        if (auszeichnung != Auszeichnung.NONE) {
            try {
                InputStream fis = new ImageLoader().getResourceAsStream(
                        auszeichnung == Auszeichnung.GOLD
                                ? GOLD_BADGE_PATH
                                : SILVER_BADGE_PATH);
                XSLFPictureData picData = slide.getSlideShow().addPicture(
                                fis, PictureData.PictureType.PNG);
                XSLFPictureShape ps = slide.createPicture(picData);
                fis.close();

                double x = slide.getSlideShow().getPageSize()
                                .getWidth() / 6 * 4 + 25;
                ps.setAnchor(new Rectangle2D.Double(x, 155., 80, 108));
            } catch (IOException e) {
                //Logging for badge wasn't found
                throw new RuntimeException("Badge picture wasn't found.");
            }
        }
    }

    /**
     * This method will expand the box of thesis text to the width of the slide,
     * the height would contain 3 lines of text in normal case.
     * @param group     The Group-shape that hold all the contents together
     * @param newWidth  The
     */
    private static void fixBox(
            final XSLFGroupShape group,
            final double newWidth) {
        //index of thesis shape inside the group
        int idThesis = 5;

        XSLFTextBox shape = (XSLFTextBox) group.getShapes().get(idThesis);
        Rectangle2D anchor = shape.getAnchor();
        Insets2D insets = shape.getInsets();
        XSLFTextParagraph tp = shape.getTextParagraphs().get(0);
        XSLFTextRun tr = tp.getTextRuns().get(0);

        double newHeight = tr.getFontSize() * THESIS_MAX_LINES
                        + insets.bottom + insets.top;
        shape.setAnchor(new Rectangle2D.Double(
                (anchor.getWidth() - newWidth) / 2,
                anchor.getY(),
                newWidth,
                newHeight));

        double amount = shape.getAnchor().getMaxY() + SPACING_CONTENT_SMALL;

        shape = (XSLFTextBox) group.getShapes()
                        .get(idThesis + OtherSlideSetting.getNextItem());
        anchor = shape.getAnchor();
        shape.setAnchor(new Rectangle2D.Double(
                anchor.getX(), amount, anchor.getWidth(), anchor.getHeight()));
    }
}
