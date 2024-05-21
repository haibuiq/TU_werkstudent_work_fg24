package tud.bp.group32.generator;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;



public abstract class TableSlideGenerator {
    //border widths setting for different purposes
    /**
     * Border stroke weight for a cell when the border in question
     * is between 2 cells.
     */
    private static final double BORDER_INNER_WIDTH      = 0.5;
    /**
     * Border stroke weight for a cell when the border is also the border
     * of the table chart.
     */
    private static final double BORDER_OUTER_WIDTH      = 2.5;
    /**
     * Border stroke weight to separate/highlight the row of the table chart.
     */
    private static final double BORDER_SEPARATOR_WIDTH  = 1.;

    /**
     * Row is better being dynamic, while column could stay static.
     */
    private static int numOfRows = 6;
    /**
     * Number of columns of the table chart.
     */
    private static final int NUM_OF_COLUMNS = 3;
    /**
     * Border color of a cell object.
     */
    private static final Color BORDER_COLOR = new Color(0, 0, 0);
    /**
     * Predefined table chart anchor.
     */
    private static final Rectangle2D TABLE_ANCHOR =
        new Rectangle2D.Double(960. / 11 * 3, 160., 960. / 11 * 5, 320.);

    /**
     * Predefined cell width for those that contains text.
     */
    private static final double CELL_WIDTH_TEXT =
        TABLE_ANCHOR.getWidth() / 5 * 3;
    /**
     * Predefined cell width for those that contains number.
     */
    private static final double CELL_WIDTH_NUMBER = TABLE_ANCHOR.getWidth() / 5;
    /**
     * Predefined font color for the last row of the table chart.
     */
    private static final Color FONT_COLOR_LAST_ROW = Color.black;

/**
 * Generate the slide with a table chart which contains the amount of
 * graduated students.
 * @param ppt The presentation object
 * @param numbers The list of graduated students for various degrees
 */
    public static void generateSlide(
            final XMLSlideShow ppt,
            final java.util.List<Integer> numbers) {
        if (numbers.size() < 5) {
            return;
        }
        generateSlide(ppt, numbers.get(0), numbers.get(1),
            numbers.get(2), numbers.get(3), numbers.get(4));
    }

    /**
     * Generate the slide with a table chart which contains
     * the amount of graduated students for various degrees.
     * @param ppt       The presentation object
     * @param studentB  Number of bachelor students
     * @param studentMG Number of german master students
     * @param studentMI Number of international master students
     * @param dd        Number of graduated students with double degrees
     * @param badges    Number of graduated students with excellent grade
     */
    public static void generateSlide(
            final XMLSlideShow ppt,
            final int studentB,
            final int studentMG,
            final int studentMI,
            final int dd,
            final int badges) {
        XSLFSlide slide = OtherSlideSetting.createSlideAsBlank(ppt);
        int year = 2024;
        boolean isWinter = false;

        //need to take from arrays.length instead
        XSLFTable table = slide.createTable(numOfRows, NUM_OF_COLUMNS);

        table.setAnchor(TABLE_ANCHOR);

        OtherSlideSetting.settingTitle(slide, isWinter, year);
        OtherSlideSetting.settingDateAndSlideNumber(slide);

        //these two variables should be provided as parameters
        int[] numbers = new int[]
                {studentB, studentMG, studentMI, dd, badges,
                        studentB + studentMG + studentMI + dd + badges};
        String[] headers = new String[]{
                "Bachelor-Absolventen",
                "Master-Absolventen (deutschsprachige Studiengänge)",
                "Master-Absolventen (internationale Studiengänge)",
                "Doppelabschlüsse",
                "Sehr gute Abschlüsse",
                "Insgesamt"};

        //setting up each row
        for (int i = 0; i < numOfRows; i++) {
            //trying to equally set up the row height
            table.setRowHeight(i, TABLE_ANCHOR.getHeight() / numbers.length);

            // Setting the width of each column
            // (with the first column being 3/5 of the total
            // and the other two each 1/5).
            if (i < NUM_OF_COLUMNS) {
                table.setColumnWidth(i, i == 0
                                            ? CELL_WIDTH_TEXT
                                            : CELL_WIDTH_NUMBER);
            }

            //setting up each cell in a row
            for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                String s =
                switch (j) {
                    case 0 : yield headers[i];
                    case 1 : yield String.valueOf(numbers[i]);
                    //rounding errors could occur
                    case 2 : yield Math.round(
                        ((float) numbers[i]) / numbers[5] * 100) + " %";
                    default: yield ""; //should not happen
                };
                XSLFTableCell cell = table.getCell(i, j);
                cell.setText(s);
                XSLFTextParagraph tp = cell.getTextParagraphs()
                        .get(OtherSlideSetting.getFirstItem());

                //text alignment based on column
                if (j == 0) {
                    tp.setTextAlign(TextParagraph.TextAlign.LEFT);
                } else {
                    tp.setTextAlign(TextParagraph.TextAlign.CENTER);
                }

                //changing the font color into blue for the last one
                if (i == numOfRows - 1) {
                    XSLFTextRun tr = tp.getTextRuns().get(
                        OtherSlideSetting.getFirstItem());
                    tr.setBold(true);
                    tr.setFontColor(FONT_COLOR_LAST_ROW);
                }

                //setting the borders of each cell
                setborder(table, i, j);
                //text run should be horrizontal
                cell.setTextDirection(TextShape.TextDirection.HORIZONTAL);
                //text would be placed in the middle of the cell
                cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            }
        }
    }

    /**
     * Method changes the border stroke weight of cell from the given
     * table chart parameter.
     * The stroke weight for that cell change according to where
     * the cell is located.
     * @param table     The table chart
     * @param row       Row index of a specific cell from the table
     * @param column    Column index of a specific cell from the table
     */
    private static void setborder(
            final XSLFTable table,
            final int row,
            final int column) {
        XSLFTableCell cell = table.getCell(row, column);
        //set vertical borders
        if (column == 0) {
            setBorderHelper(cell, TableCell.BorderEdge.left,
                        BORDER_OUTER_WIDTH);
            setBorderHelper(cell, TableCell.BorderEdge.right,
                        BORDER_INNER_WIDTH);
        } else if (column == table.getNumberOfColumns() - 1) {
            setBorderHelper(cell, TableCell.BorderEdge.left,
                        BORDER_INNER_WIDTH);
            setBorderHelper(cell, TableCell.BorderEdge.right,
                        BORDER_OUTER_WIDTH);
        } else {
            setBorderHelper(cell, TableCell.BorderEdge.left,
                        BORDER_INNER_WIDTH);
            setBorderHelper(cell, TableCell.BorderEdge.right,
                        BORDER_INNER_WIDTH);
        }

        //set horizontal borders
        if (row == 0) {
            setBorderHelper(cell, TableCell.BorderEdge.top,
                        BORDER_OUTER_WIDTH);
            setBorderHelper(cell, TableCell.BorderEdge.bottom,
                        BORDER_INNER_WIDTH);
        } else if (row == table.getNumberOfRows() - 1) {
            setBorderHelper(cell, TableCell.BorderEdge.top,
                        BORDER_SEPARATOR_WIDTH);
            setBorderHelper(cell, TableCell.BorderEdge.bottom,
                        BORDER_OUTER_WIDTH);
        } else {
            setBorderHelper(cell, TableCell.BorderEdge.top,
                        BORDER_INNER_WIDTH);
            setBorderHelper(cell, TableCell.BorderEdge.bottom,
                        BORDER_INNER_WIDTH);
        }
    }

    /**
     * Helper method to set the border stroke weight for certain edge of a cell.
     * @param cell  The cell object which needs to change one border stroke
     *              weight for an edge
     * @param edge  The specific edge from the given cell parameter
     * @param width The stroke weight
     */
    private static void setBorderHelper(final XSLFTableCell cell,
            final TableCell.BorderEdge edge, final double width) {
        cell.setBorderColor(edge, BORDER_COLOR);
        cell.setBorderWidth(edge, width);
    }
}
