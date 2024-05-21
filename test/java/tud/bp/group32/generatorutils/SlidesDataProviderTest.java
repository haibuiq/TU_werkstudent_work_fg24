package tud.bp.group32.generatorutils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tud.bp.group32.reader.ControlFileReader;
import tud.bp.group32.student.StudentControlfile;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SlidesDataProviderTest {

  /**
   * Regular SlidesDataProvider for regular test cases.
   */
  private static SlidesDataProvider regularSlidesDataProvider;

  /**
   * List of StudentControlfile objects for regular test cases with
   * unsorted control file.
   */
  private static List<StudentControlfile>  regularUnsortedControlFile;

  /**
   * SlidesDataProvider for cases when data is not available.
   */
  private static SlidesDataProvider notAvailableSlidesDataProvider;

  /**
   * List of StudentControlfile objects for cases when data is not available.
   */
  private static List<StudentControlfile> notAvailableControlFile;

  /**
   * SlidesDataProvider for cases when control file is empty.
   */
  @SuppressWarnings("unused")
  private static SlidesDataProvider emptySlidesDataProvider;

  /**
   * List of StudentControlfile objects for cases when control file is empty.
   */
  private static List<StudentControlfile>  emptyControlFile;


  /**
   * This Method sets up the Expected data for different Test Cases:
   * Regular and Edge cases, assuming ControlFileReader works as intended.
   * @throws ExecutionException if the file for some reason cannot
   * be opened for reading.
   */
  @BeforeAll
  public static void setup() throws ExecutionException {
    //Regular Case: Regular Control File but Unsorted.
    ControlFileReader regularReader = new ControlFileReader(
        "./src/test/resources/ControlFileRegularUnsorted.xlsx");
    regularUnsortedControlFile = regularReader.readAll();
    regularSlidesDataProvider = new SlidesDataProvider(
        regularUnsortedControlFile);

    //Edge Case: Control File cells are N/A.
    ControlFileReader notAvailableReader = new ControlFileReader(
        "./src/test/resources/notAvailableControlFile.xlsx");
    notAvailableControlFile = notAvailableReader.readAll();
    notAvailableSlidesDataProvider = new SlidesDataProvider(
        notAvailableControlFile);

    //Edge Case: Control File empty.
    ControlFileReader emptyReader = new ControlFileReader(
        "./src/test/resources/EmptyControlFile.xlsx");
    emptyControlFile = emptyReader.readAll();
    emptySlidesDataProvider = new SlidesDataProvider(emptyControlFile);
  }

  /**
   * This Test method ensures safety of BachelorSlides data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedBachelorSlidesTest() {
    List<StudentControlfile> regularBachelorSlides = regularSlidesDataProvider
        .getBachelorSlides();

    // Ensure the Providing was successful.
    assertNotNull(regularBachelorSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(6, regularBachelorSlides.size());

    // Ensure the right Students are provided in a Sorted manner.
    assertEquals(regularUnsortedControlFile.get(4),
        regularBachelorSlides.get(0));
    assertEquals(regularUnsortedControlFile.get(3),
        regularBachelorSlides.get(1));
    assertEquals(regularUnsortedControlFile.get(10),
        regularBachelorSlides.get(2));
    assertEquals(regularUnsortedControlFile.get(11),
        regularBachelorSlides.get(3));
    assertEquals(regularUnsortedControlFile.get(12),
        regularBachelorSlides.get(4));
    assertEquals(regularUnsortedControlFile.get(13),
        regularBachelorSlides.get(5));

  }

  /**
   * This Test method ensures safety of CleanMasterSlides data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedCleanMasterSlidesTest() {
    List<StudentControlfile> regularCleanMasterSlides =
        regularSlidesDataProvider.getCleanMasterSlides();

    // Ensure the Providing was successful.
    assertNotNull(regularCleanMasterSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(2, regularCleanMasterSlides.size());

    // Ensure the right Students are provided in a Sorted manner.
    assertEquals(regularUnsortedControlFile.get(6),
        regularCleanMasterSlides.get(0));
    assertEquals(regularUnsortedControlFile.get(8),
        regularCleanMasterSlides.get(1));
  }

  /**
   * This Test method ensures safety of InternationalMasterSlides data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedInternationalMasterSlidesTest() {
    List<StudentControlfile> regularInternationalMasterSlides =
        regularSlidesDataProvider.getInternationalMasterSlides();

    // Ensure the Providing was successful.
    assertNotNull(regularInternationalMasterSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(1, regularInternationalMasterSlides.size());

    // Ensure the right Students are provided in a Sorted manner.
    assertEquals(regularUnsortedControlFile.get(5),
        regularInternationalMasterSlides.get(0));
  }

  /**
   * This Test method ensures safety of DoubleDegreeSlides data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedDoubleDegreeSlidesTest() {
    List<StudentControlfile> regularDoubleDegreeSlides =
        regularSlidesDataProvider.getDoubleDegreeSlides();

    // Ensure the Providing was successful.
    assertNotNull(regularDoubleDegreeSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(2, regularDoubleDegreeSlides.size());

    // Ensure the right Students are provided in a Sorted manner.
    assertEquals(regularUnsortedControlFile.get(9),
        regularDoubleDegreeSlides.get(0));
    assertEquals(regularUnsortedControlFile.get(0),
        regularDoubleDegreeSlides.get(1));
  }

  /**
   * This Test method ensures safety of getStudentsForSlides data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetStudentsForSlidesTest() {
    List<StudentControlfile> regularStudentsForSlides =
        regularSlidesDataProvider.getStudentsForSlides();

    // Ensure the Providing was successful.
    assertNotNull(regularStudentsForSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(11, regularStudentsForSlides.size());

    // Ensure the right Students are provided in a Sorted manner.
    //Sorted Bachelors.
    assertEquals(regularUnsortedControlFile.get(4),
        regularStudentsForSlides.get(0));
    assertEquals(regularUnsortedControlFile.get(3),
        regularStudentsForSlides.get(1));
    assertEquals(regularUnsortedControlFile.get(10),
        regularStudentsForSlides.get(2));
    assertEquals(regularUnsortedControlFile.get(11),
        regularStudentsForSlides.get(3));
    assertEquals(regularUnsortedControlFile.get(12),
        regularStudentsForSlides.get(4));
    assertEquals(regularUnsortedControlFile.get(13),
        regularStudentsForSlides.get(5));
    //Sorted Clean Master.
    assertEquals(regularUnsortedControlFile.get(6),
        regularStudentsForSlides.get(6));
    assertEquals(regularUnsortedControlFile.get(8),
        regularStudentsForSlides.get(7));
    //Sorted DD.
    assertEquals(regularUnsortedControlFile.get(9),
        regularStudentsForSlides.get(8));
    assertEquals(regularUnsortedControlFile.get(0),
        regularStudentsForSlides.get(9));
    //Sorted International.
    assertEquals(regularUnsortedControlFile.get(5),
        regularStudentsForSlides.get(10));
  }

  /**
   * This Test method ensures safety of getBachelorsIterator data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetBachelorsIteratorTest() {
    StudentControlFileIterator regularBachelorsIterator =
        regularSlidesDataProvider.getBachelorsIterator();

    // Ensure the Providing was successful.
    assertNotNull(regularBachelorsIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    sizes.add(3);
    sizes.add(3);
    assertEquals(sizes, regularBachelorsIterator.getSizes());

    // Ensure the right Students are provided in a Sorted manner
    // in Balanced groups.
    List<StudentControlfile> group1 = new LinkedList<>();
    group1.add(regularUnsortedControlFile.get(4));
    group1.add(regularUnsortedControlFile.get(3));
    group1.add(regularUnsortedControlFile.get(10));

    assertEquals(group1, regularBachelorsIterator.next());

    List<StudentControlfile> group2 = new LinkedList<>();
    group2.add(regularUnsortedControlFile.get(11));
    group2.add(regularUnsortedControlFile.get(12));
    group2.add(regularUnsortedControlFile.get(13));

    assertEquals(group2, regularBachelorsIterator.next());

    //Ensure there is no next Iteration
    assertFalse(regularBachelorsIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getMastersIterator data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetMastersIteratorTest() {
    StudentControlFileIterator regularMastersIterator =
        regularSlidesDataProvider.getMastersIterator();

    // Ensure the Providing was successful.
    assertNotNull(regularMastersIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    sizes.add(2);
    assertEquals(sizes, regularMastersIterator.getSizes());

    // Ensure the right Students are provided in a Sorted manner
    // in Balanced groups.
    List<StudentControlfile> group1 = new LinkedList<>();
    group1.add(regularUnsortedControlFile.get(6));
    group1.add(regularUnsortedControlFile.get(8));

    assertEquals(group1, regularMastersIterator.next());

    //Ensure there is no next Iteration
    assertFalse(regularMastersIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getDDIterator data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetDDIteratorTest() {
    StudentControlFileIterator regularDDIterator =
        regularSlidesDataProvider.getDDIterator();

    // Ensure the Providing was successful.
    assertNotNull(regularDDIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    sizes.add(2);
    assertEquals(sizes, regularDDIterator.getSizes());

    // Ensure the right Students are provided in a Sorted manner
    // in Balanced groups.
    List<StudentControlfile> group1 = new LinkedList<>();
    group1.add(regularUnsortedControlFile.get(9));
    group1.add(regularUnsortedControlFile.get(0));

    assertEquals(group1, regularDDIterator.next());

    //Ensure there is no next Iteration
    assertFalse(regularDDIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getInternationalIterator data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetInternationalIteratorTest() {
    StudentControlFileIterator regularInternationalIterator =
        regularSlidesDataProvider.getInternationalIterator();

    // Ensure the Providing was successful.
    assertNotNull(regularInternationalIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    sizes.add(1);
    assertEquals(sizes, regularInternationalIterator.getSizes());

    // Ensure the right Students are provided in a Sorted manner
    // in Balanced groups.
    List<StudentControlfile> group1 = new LinkedList<>();
    group1.add(regularUnsortedControlFile.get(5));

    assertEquals(group1, regularInternationalIterator.next());

    //Ensure there is no next Iteration
    assertFalse(regularInternationalIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getFirstColumnOfStatisticsTable data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetFirstColumnOfStatisticsTableTest() {
    List<String> regularFirstColumn =
        regularSlidesDataProvider.getFirstColumnOfStatisticsTable();

    // Ensure the Providing was successful.
    assertNotNull(regularFirstColumn);

    // Ensure the right amount of Rows is provided.
    assertEquals(5, regularFirstColumn.size());

    // Ensure the right Groups are provided in the right order.
    assertEquals("Bachelor-Absolventen", regularFirstColumn.get(0));
    assertEquals("Master-Absolventen\n"
            + " (deutschsprachige Studiengänge)", regularFirstColumn.get(1));
    assertEquals("Master-Absolventen\n"
            + "(internationale Studiengänge)", regularFirstColumn.get(2));
    assertEquals("Doppelabschlüsse", regularFirstColumn.get(3));
    assertEquals("Sehr gute Abschlüsse", regularFirstColumn.get(4));
  }

  /**
   * This Test method ensures safety of getSecondColumnOfStatisticsTable data
   * upon regular cases and Unsorted data input.
   */
  @Test
  public void regularUnsortedGetSecondColumnOfStatisticsTableTest() {
    List<Integer> regularSecondColumn =
        regularSlidesDataProvider.getSecondColumnOfStatisticsTable();

    // Ensure the Providing was successful.
    assertNotNull(regularSecondColumn);

    // Ensure the right amount of Rows is provided.
    assertEquals(5, regularSecondColumn.size());

    // Ensure the right size of groups are provided in the right order.
    assertEquals(3, regularSecondColumn.get(0));
    assertEquals(1, regularSecondColumn.get(1));
    assertEquals(1, regularSecondColumn.get(2));
    assertEquals(2, regularSecondColumn.get(3));
    assertEquals(7, regularSecondColumn.get(4));
  }



  /**
   * This Test method ensures safety of BachelorSlides data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableBachelorSlidesTest() {
    List<StudentControlfile> notAvailableBachelorSlides =
        notAvailableSlidesDataProvider.getBachelorSlides();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableBachelorSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, notAvailableBachelorSlides.size());
  }

  /**
   * This Test method ensures safety of CleanMasterSlides data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableCleanMasterSlidesTest() {
    List<StudentControlfile> notAvailableCleanMasterSlides =
        notAvailableSlidesDataProvider.getCleanMasterSlides();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableCleanMasterSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, notAvailableCleanMasterSlides.size());
  }

  /**
   * This Test method ensures safety of InternationalMasterSlides data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableInternationalMasterSlidesTest() {
    List<StudentControlfile> notAvailableInternationalMasterSlides =
        notAvailableSlidesDataProvider.getInternationalMasterSlides();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableInternationalMasterSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, notAvailableInternationalMasterSlides.size());
  }

  /**
   * This Test method ensures safety of DoubleDegreeSlides data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableDoubleDegreeSlidesTest() {
    List<StudentControlfile> notAvailableDoubleDegreeSlides =
        notAvailableSlidesDataProvider.getDoubleDegreeSlides();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableDoubleDegreeSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, notAvailableDoubleDegreeSlides.size());
  }

  /**
   * This Test method ensures safety of getStudentsForSlides data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetStudentsForSlidesTest() {
    List<StudentControlfile> notAvailableStudentsForSlides =
        notAvailableSlidesDataProvider.getStudentsForSlides();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableStudentsForSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, notAvailableStudentsForSlides.size());
  }

  /**
   * This Test method ensures safety of getBachelorsIterator data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetBachelorsIteratorTest() {
    StudentControlFileIterator notAvailableBachelorsIterator =
        notAvailableSlidesDataProvider.getBachelorsIterator();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableBachelorsIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, notAvailableBachelorsIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(notAvailableBachelorsIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getMastersIterator data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetMastersIteratorTest() {
    StudentControlFileIterator notAvailableMastersIterator =
        notAvailableSlidesDataProvider.getMastersIterator();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableMastersIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, notAvailableMastersIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(notAvailableMastersIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getDDIterator data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetDDIteratorTest() {
    StudentControlFileIterator notAvailableDDIterator =
        notAvailableSlidesDataProvider.getDDIterator();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableDDIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, notAvailableDDIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(notAvailableDDIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getInternationalIterator data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetInternationalIteratorTest() {
    StudentControlFileIterator notAvailableInternationalIterator =
        notAvailableSlidesDataProvider.getInternationalIterator();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableInternationalIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, notAvailableInternationalIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(notAvailableInternationalIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getFirstColumnOfStatisticsTable data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetFirstColumnOfStatisticsTableTest() {
    List<String> notAvailableFirstColumn =
        notAvailableSlidesDataProvider.getFirstColumnOfStatisticsTable();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableFirstColumn);

    // Ensure the right amount of Rows is provided.
    assertEquals(5, notAvailableFirstColumn.size());

    // Ensure the right Groups are provided in the right order.
    assertEquals("Bachelor-Absolventen", notAvailableFirstColumn.get(0));
    assertEquals("Master-Absolventen\n"
            + " (deutschsprachige Studiengänge)",
                notAvailableFirstColumn.get(1));
    assertEquals("Master-Absolventen\n"
            + "(internationale Studiengänge)", notAvailableFirstColumn.get(2));
    assertEquals("Doppelabschlüsse", notAvailableFirstColumn.get(3));
    assertEquals("Sehr gute Abschlüsse", notAvailableFirstColumn.get(4));
  }

  /**
   * This Test method ensures safety of getSecondColumnOfStatisticsTable data
   * upon the edge case of N/A cells as data input.
   */
  @Test
  public void notAvailableGetSecondColumnOfStatisticsTableTest() {
    List<Integer> notAvailableSecondColumn =
        notAvailableSlidesDataProvider.getSecondColumnOfStatisticsTable();

    // Ensure the Providing was successful.
    assertNotNull(notAvailableSecondColumn);

    // Ensure the right amount of Rows is provided.
    assertEquals(5, notAvailableSecondColumn.size());

    // Ensure the right size of groups are provided in the right order.
    assertEquals(0, notAvailableSecondColumn.get(0));
    assertEquals(0, notAvailableSecondColumn.get(1));
    assertEquals(0, notAvailableSecondColumn.get(2));
    assertEquals(0, notAvailableSecondColumn.get(3));
    assertEquals(0, notAvailableSecondColumn.get(4));
  }



  /**
   * This Test method ensures safety of BachelorSlides data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyBachelorSlidesTest() {
    List<StudentControlfile> emptyBachelorSlides =
        notAvailableSlidesDataProvider.getBachelorSlides();

    // Ensure the Providing was successful.
    assertNotNull(emptyBachelorSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, emptyBachelorSlides.size());
  }

  /**
   * This Test method ensures safety of CleanMasterSlides data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyCleanMasterSlidesTest() {
    List<StudentControlfile> emptyCleanMasterSlides =
        notAvailableSlidesDataProvider.getCleanMasterSlides();

    // Ensure the Providing was successful.
    assertNotNull(emptyCleanMasterSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, emptyCleanMasterSlides.size());
  }

  /**
   * This Test method ensures safety of InternationalMasterSlides data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyInternationalMasterSlidesTest() {
    List<StudentControlfile> emptyInternationalMasterSlides =
        notAvailableSlidesDataProvider.getInternationalMasterSlides();

    // Ensure the Providing was successful.
    assertNotNull(emptyInternationalMasterSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, emptyInternationalMasterSlides.size());
  }

  /**
   * This Test method ensures safety of DoubleDegreeSlides data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyDoubleDegreeSlidesTest() {
    List<StudentControlfile> emptyDoubleDegreeSlides =
        notAvailableSlidesDataProvider.getDoubleDegreeSlides();

    // Ensure the Providing was successful.
    assertNotNull(emptyDoubleDegreeSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, emptyDoubleDegreeSlides.size());
  }

  /**
   * This Test method ensures safety of GetStudentsForSlides data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetStudentsForSlidesTest() {
    List<StudentControlfile> emptyStudentsForSlides =
        notAvailableSlidesDataProvider.getStudentsForSlides();

    // Ensure the Providing was successful.
    assertNotNull(emptyStudentsForSlides);

    // Ensure the right amount of Students is provided.
    assertEquals(0, emptyStudentsForSlides.size());
  }

  /**
   * This Test method ensures safety of getBachelorsIterator data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetBachelorsIteratorTest() {
    StudentControlFileIterator emptyBachelorsIterator =
        notAvailableSlidesDataProvider.getBachelorsIterator();

    // Ensure the Providing was successful.
    assertNotNull(emptyBachelorsIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, emptyBachelorsIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(emptyBachelorsIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getMastersIterator data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetMastersIteratorTest() {
    StudentControlFileIterator emptyMastersIterator =
        notAvailableSlidesDataProvider.getMastersIterator();

    // Ensure the Providing was successful.
    assertNotNull(emptyMastersIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, emptyMastersIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(emptyMastersIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getDDIterator data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetDDIteratorTest() {
    StudentControlFileIterator emptyDDIterator =
        notAvailableSlidesDataProvider.getDDIterator();

    // Ensure the Providing was successful.
    assertNotNull(emptyDDIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, emptyDDIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(emptyDDIterator.hasNext());
  }

  /**
   * This Test method ensures safety of getInternationalIterator data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetInternationalIteratorTest() {
    StudentControlFileIterator emptyInternationalIterator =
        notAvailableSlidesDataProvider.getInternationalIterator();

    // Ensure the Providing was successful.
    assertNotNull(emptyInternationalIterator);

    // Ensure the right amount of Students is provided in a Balanced manner.
    LinkedList<Integer> sizes = new LinkedList<>();
    assertEquals(sizes, emptyInternationalIterator.getSizes());

    //Ensure there is no next Iteration
    assertFalse(emptyInternationalIterator.hasNext());
  }

  /**
   * This Test method ensures safety of GetFirstColumnOfStatisticsTable data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetFirstColumnOfStatisticsTableTest() {
    List<String> emptyFirstColumn = notAvailableSlidesDataProvider
        .getFirstColumnOfStatisticsTable();

    // Ensure the Providing was successful.
    assertNotNull(emptyFirstColumn);

    // Ensure the right amount of Rows is provided.
    assertEquals(5, emptyFirstColumn.size());

    // Ensure the right Groups are provided in the right order.
    assertEquals("Bachelor-Absolventen", emptyFirstColumn.get(0));
    assertEquals("Master-Absolventen\n"
            + " (deutschsprachige Studiengänge)", emptyFirstColumn.get(1));
    assertEquals("Master-Absolventen\n"
            + "(internationale Studiengänge)", emptyFirstColumn.get(2));
    assertEquals("Doppelabschlüsse", emptyFirstColumn.get(3));
    assertEquals("Sehr gute Abschlüsse", emptyFirstColumn.get(4));
  }

  /**
   * This Test method ensures safety of getSecondColumnOfStatisticsTable data
   * upon the edge case of empty data input.
   */
  @Test
  public void emptyGetSecondColumnOfStatisticsTableTest() {
    List<Integer> emptySecondColumn =
        notAvailableSlidesDataProvider.getSecondColumnOfStatisticsTable();

    // Ensure the Providing was successful.
    assertNotNull(emptySecondColumn);

    // Ensure the right amount of Rows is provided.
    assertEquals(5, emptySecondColumn.size());

    // Ensure the right size of groups are provided in the right order.
    assertEquals(0, emptySecondColumn.get(0));
    assertEquals(0, emptySecondColumn.get(1));
    assertEquals(0, emptySecondColumn.get(2));
    assertEquals(0, emptySecondColumn.get(3));
    assertEquals(0, emptySecondColumn.get(4));

  }

}
