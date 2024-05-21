package tud.bp.group32.generator;

import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFGroupShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.LogConstants;
import tud.bp.group32.utilities.DynamicProperties;
import tud.bp.group32.utilities.LoggingUtils;

import java.awt.geom.Rectangle2D;
import java.util.function.Predicate;
import java.util.logging.Level;

public class OtherSlideSetting {
    protected OtherSlideSetting() {
        throw new UnsupportedOperationException();
    }
    /**
     * Use to find the place-holder text box for the slide-number from layout
     * "Title Content".
     */
    private static final int PH_SLIDENUMBER_SLIDEID = 8;
    /**
     * Use to find the place-holder text box for the date from layout
     * "Title Content".
     */
    private static final int PH_DATETIME_SLIDEID    = 7;
    /**
     * Name of the title shape in the master slide.
     */
    private static final String TITLE_SHAPE_NAME        = "Titel";
    /**
     * Index for the master slide.
     */
    private static final int MASTER_ID              = 0;

    /**
     * Spacing between the title and the content.
     */
    private static final double TITLE_SPACING              = 10.;
    /**
     * Font Size Property of the title.
     */
    private static final double TITLE_FONT_SIZE            = 34.;

    /**
     * Index for the first item.
     */
    private static final int FIRST_ITEM = 0;
    /**
     * Index for the second item.
     */
    private static final int NEXT_ITEM = 1;
    /**
     * Name of the layout for the slide with the table.
     */
    private static final String LAYOUT_NAME_BOX_FRAME   = "Title Content";
    /**
     * Name of the layout for the slide with the table.
     */
    private static final String LAYOUT_NAME_CREATE   = "Blank";

    /**
     * Title text box name in the Blank-Layout.
     */
    private static final String TITLE_TABLE = "Absolventen des";
    /**
     * Font Size Property of the text.
     */
    private static final String TITLE_NORMAL =
                            DynamicProperties.SLIDES_MAIN_TITLE;

    /**
     * Title for students with no badge.
     */
    private static final String TITLE_NONE =
                            DynamicProperties.SLIDES_TITLE_NO;
    /**
     * Title for students with silver badge.
     */
    private static final String TITLE_SILVER =
                            DynamicProperties.SLIDES_TITLE_SG;
    /**
     * Title for students with gold badge.
     */
    private static final String TITLE_GOLD =
                            DynamicProperties.SLIDES_TITLE_MA;

    /**
     * WiSe.
     */
    private static final String WINTER_SEMESTER = " zweiten Halbjahres ";
    /**
     * SoSe.
     */
    private static final String SUMMER_SEMESTER = " ersten Halbjahres ";

    /**
     * Font Size Property of the text.
     */
    private static final double FOOTER_FONT_SIZE = 8.;
    /**
     * Name of the layout for the slide with the table.
     */
    @SuppressWarnings("unused")
    private static final int FOOTER_MIDDLE_SHAPE_INDEX_FROM_MASTER = 6;



    //text for the middle part in the footer ( Faculty | Institute | Person )
    /**
     * Text for the middle part in the footer.
     */
    @SuppressWarnings("unused")
    private static final String FOOTER_MIDDLE_TEXT =
            "Fachbereich 18 (etit) | Dekan | Absolventenfeier";
    //date of the graduation (better being a parameter)
    /**
     * Date of the graduation.
     */
    private static final String DATE_OF_PARTY = "02.02.2024";
    /**
     * Method to choose a shape by its name.
     * @param name Name of the shape
     * @return The shape with the given name
     */
    @SuppressWarnings("rawtypes") //If there are better solution, pls tell me
    public static Predicate<Shape> chooseShapeByName(final String name) {
        return sh -> sh.getShapeName().contains(name);
    }

    /**
     * Setting the title for table slide.
     * @param slide             The slide needed to set the title
     * @param isWinterSemester  True, if the party is for the second half of
     *                          the year
     * @param year              Year of date when the party happens
     */
    public static void settingTitle(
            final XSLFSlide slide,
            final boolean isWinterSemester,
            final int year) {
        //should be only one
        XSLFGroupShape group = slide.createGroup();
        Rectangle2D anchor = slide.getSlideShow().getSlideMasters()
                .get(0).getLayout(LAYOUT_NAME_BOX_FRAME)
                .getShapes().stream()
                .filter(chooseShapeByName(TITLE_SHAPE_NAME))
                .map(Shape::getAnchor)
                .toList().get(0);
        group.setAnchor(anchor);
        XSLFTextShape tb1 = group.createAutoShape();
        tb1.setAnchor(
                new Rectangle2D.Double(
                    1.,
                    1.,
                    anchor.getWidth() - 2,
                    TITLE_FONT_SIZE));
        tb1.setText(TITLE_TABLE.concat(isWinterSemester
                ? WINTER_SEMESTER
                : SUMMER_SEMESTER).concat(String.valueOf(year)));
        group.getShapes().stream()
                .map(shape -> (XSLFTextShape) shape)
                .forEach(ts -> {
                    ts.setTextDirection(TextShape.TextDirection.HORIZONTAL);
                    ts.getTextParagraphs().get(FIRST_ITEM)
                            .setTextAlign(TextParagraph.TextAlign.RIGHT);
                    XSLFTextRun tr = ts.getTextParagraphs()
                            .get(FIRST_ITEM).getTextRuns().get(FIRST_ITEM);
                    tr.setBold(true);
                    tr.setFontSize(TITLE_FONT_SIZE);
                });
    }

    /**
     * Setting the title for most of the slide. For student-slide,
     * it is based on the acquired badge of that specific student.
     * @param slide The slide needed to set the title
     * @param badge Acquired badge by the student
     */
    public static void settingTitle(final XSLFSlide slide,
                                    final Auszeichnung badge) {
        //should be only one
        XSLFGroupShape group = slide.createGroup();
        Rectangle2D anchor = slide.getSlideShow()
                .getSlideMasters()
                .get(MASTER_ID).getLayout(LAYOUT_NAME_BOX_FRAME)
                .getShapes().stream()
                .filter(chooseShapeByName(TITLE_SHAPE_NAME))
                .map(Shape::getAnchor)
                .toList().get(FIRST_ITEM);
        group.setAnchor(anchor);
        XSLFTextShape tb1 = group.createAutoShape();
        XSLFTextShape tb2 = group.createAutoShape();
        tb1.setAnchor(new Rectangle2D.Double(
                1.,
                1.,
                anchor.getWidth() - 2,
                TITLE_FONT_SIZE));
        tb2.setAnchor(new Rectangle2D.Double(1.,
                tb1.getAnchor().getMaxY() + TITLE_SPACING,
                anchor.getWidth() - 2,
                TITLE_FONT_SIZE));
        tb1.setText(TITLE_NORMAL);
        String s = switch (badge) {
            case NONE   -> TITLE_NONE;
            case SILVER -> TITLE_SILVER;
            case GOLD   -> TITLE_GOLD;
        };
        tb2.setText(s);
        group.getShapes().stream().map(shape -> (XSLFTextShape) shape)
                .forEach(ts -> {
                    ts.setTextDirection(TextShape.TextDirection.HORIZONTAL);
                    ts.getTextParagraphs()
                            .get(FIRST_ITEM)
                            .setTextAlign(TextParagraph.TextAlign.CENTER);
                    XSLFTextRun tr = ts.getTextParagraphs().get(FIRST_ITEM)
                            .getTextRuns().get(FIRST_ITEM);
                    tr.setBold(true);
                    tr.setFontSize(TITLE_FONT_SIZE);
                });
    }

    /**
     * Setting the slide number and date of the party into the slide.
     * @param slide The slide that needed to be setting up
     */
    public static void settingDateAndSlideNumber(final XSLFSlide slide) {
        //set slide number @ the bottom
        slide.getSlideLayout().getShapes().stream()
                .filter(sh -> sh.isPlaceholder()
                        && (sh.getPlaceholder()
                        .nativeSlideId == PH_DATETIME_SLIDEID
                        || sh.getPlaceholder()
                        .nativeSlideId == PH_SLIDENUMBER_SLIDEID))
                .forEach(sh -> {

                    XSLFTextShape tsh = slide.createAutoShape();
                    XSLFTextShape oldTsh = (XSLFTextShape) sh;
                    tsh.setAnchor(sh.getAnchor());
                    XSLFTextParagraph tp = tsh.addNewTextParagraph();
                    XSLFTextRun tr = tp.addNewTextRun();

                    switch (sh.getPlaceholder().nativeSlideId) {
                        //setting date
                        case PH_DATETIME_SLIDEID    -> {
                            tp.setTextAlign(TextParagraph.TextAlign.LEFT);
                            tr.setFontSize(FOOTER_FONT_SIZE);
                            tr.setFontColor(oldTsh.getTextParagraphs()
                                    .get(FIRST_ITEM)
                                    .getTextRuns().get(FIRST_ITEM)
                                    .getFontColor());
                            tr.setText(DATE_OF_PARTY);
                        }
                        //setting slide number
                        case PH_SLIDENUMBER_SLIDEID -> {
                            tp.setTextAlign(TextParagraph.TextAlign.RIGHT);
                            tr.setFontSize(FOOTER_FONT_SIZE);
                            tr.setFontColor(oldTsh.getTextParagraphs()
                                    .get(FIRST_ITEM).getTextRuns()
                                    .get(FIRST_ITEM).getFontColor());
                            tr.setText(String.valueOf(slide.getSlideNumber()));
                        }
                        default ->
//this section is to highlight a case when future implementation is different
                            System.out.println("Something went wrong while"
                                    + "changing the date and slideNr");
                    }
                });
    }

    /**
     * Simple Algorithm the get the anchor from the layout of the master slide
     * from a given template.
     * @param shapeName     Name of the shape
     * @param layoutName    Name of the layout
     * @param slideShow     The presentation which was generated based on a
     *                      template
     * @return              The anchor of the shape
     */
    public static Rectangle2D getAnchorWithString(final String shapeName,
            final String layoutName,
            final XMLSlideShow slideShow) {
        return slideShow.getSlideMasters().get(MASTER_ID)
                .getLayout(layoutName).getShapes().stream()
                .filter(chooseShapeByName(shapeName))
                .map(Shape::getAnchor)
                .toList().get(FIRST_ITEM);
    }

    /**
     * Create a slide with the "Blank"-layout from the template.
     * @param slideShow The presentation which was generated based on a template
     * @return          The generated slide with "Blank"-layout
     */
    public static XSLFSlide createSlideAsBlank(final XMLSlideShow slideShow) {
        return slideShow.createSlide(slideShow.findLayout(LAYOUT_NAME_CREATE));
    }

    /**
     * Remove existing slides from the given presentation.
     * @param slideShow The presentation which was generated based on a template
     * @param log       The logger to write the log
     */
    public static void removeExistingSlides(final XMLSlideShow slideShow,
                                            final LoggingUtils log) {
        if (log != null && !slideShow.getSlides().isEmpty()) {
            log.writeInLog(Level.INFO, LogConstants.TEMPLATE_NOT_EMPTY);
        }
        while (!slideShow.getSlides().isEmpty()) {
            slideShow.removeSlide(FIRST_ITEM);
        }
    }

    //Maybe it's better to just return 0/1 w/o defining the variable
    // in regard to cs (magic number error will take places)
    /**
     * Index for the first item.
     * @return  Return 0
     */
    public static int getFirstItem() {
        return FIRST_ITEM;
    }

    /**
     * Index for the second item.
     * @return  Return 1
     */
    public static int getNextItem() {
        return NEXT_ITEM;
    }
}
