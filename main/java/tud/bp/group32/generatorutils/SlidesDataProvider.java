package tud.bp.group32.generatorutils;

import org.apache.commons.collections.ListUtils;
import tud.bp.group32.constants.ArtOfAbschluss;
import tud.bp.group32.constants.Studiengang;
import tud.bp.group32.constants.TableTitles;
import tud.bp.group32.student.StudentControlfile;
import tud.bp.group32.constants.Auszeichnung;

import static tud.bp.group32.utilities.StudentUtils.getBalancedSumList;
import static tud.bp.group32.utilities.StudentUtils.sortByName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SlidesDataProvider {

    /**
     * Students that will Occur in Slides
     * and sorted by name.
     */
    private List<StudentControlfile> studentsWillBeInSlides;
    /**
     * Students that will Occur in Bachelor Slides
     * and sorted by name.
     */
    private List<StudentControlfile> bachelorSlides;
    /**
     * Students that will Occur in German Master Slides
     * and sorted by name.
     */
    private List<StudentControlfile> cleanMasterSlides;
    /**
     * Students that will Occur in International Master Slides
     * and sorted by name.
     */
    private List<StudentControlfile> internationalMasterSlides;
    /**
     * Students that will Occur in Double Degree Slides
     * and sorted by name.
     */
    private List<StudentControlfile> doubleDegreeSlides;


    /**
     * Students for Statistics table generation
     * includes Studis that Will and Not be in Slides.
     */
    private List<StudentControlfile> allStudents;
    /**
     * Students for "Bachelor-Absolventen"
     * row in Statistics table.
     */
    private List<StudentControlfile> studisOfBachelorWithoutGoldOrSilver;
    /**
     * Students for "Master-in-German-Absolventen"
     * row in Statistics table.
     */
    private List<StudentControlfile> studisOfMasterInGermanWithoutGoldOrSilver;
    /**
     * Students for "Master-International-Absolventen"
     * row in Statistics table.
     */
    private List<StudentControlfile> studisOfMaterInternaWithoutGoldOrSilver;
    /**
     * Students for "Double-Degree-Absolventen"
     * row in Statistics table.
     */
    private List<StudentControlfile> studisWithDoubleDegreeWithoutGoldOrSilver;
    /**
     * Number Students for "Sehr-gute-Abschlüsse"
     * row in Statistics table.
     */
    private int numOfStudentsWithGoldAndSilver;


    /**
     * Constructor for creating an instance of the SlidesDataProvider.
     * @param allStudentsFromControlFile the read students from Control file.
     */
    public SlidesDataProvider(
            final List<StudentControlfile> allStudentsFromControlFile) {
        initialiseAttributsForStatisticsTable(allStudentsFromControlFile);
        initialiseAttributsForAllSlides(allStudentsFromControlFile);
    }

    /**
     * This method creates an Iterator through the Students
     * of Bachelor Slides as Balanced groups.
     * @return StudentControlFileIterator.
     */
    public StudentControlFileIterator getBachelorsIterator() {
        var countList =
                Arrays.stream(
                        getBalancedSumList(bachelorSlides.size())
                ).boxed().toList();
        return new StudentControlFileIterator(
                bachelorSlides, countList);
    }

    /**
     * This method creates an Iterator through the Students
     * of Master Slides as Balanced groups.
     * @return StudentControlFileIterator.
     */
    public StudentControlFileIterator getMastersIterator() {
        var countList =
                Arrays.stream(
                        getBalancedSumList(cleanMasterSlides.size())
                ).boxed().toList();
        return new StudentControlFileIterator(
                cleanMasterSlides, countList);
    }

    /**
     * This method creates an Iterator through the Students
     * of Master International Slides as Balanced groups.
     * @return StudentControlFileIterator.
     */
    public StudentControlFileIterator getInternationalIterator() {
        var countList =
                Arrays.stream(
                        getBalancedSumList(internationalMasterSlides.size())
                ).boxed().toList();
        return new StudentControlFileIterator(
                internationalMasterSlides, countList);
    }

    /**
     * This method creates an Iterator through the Students
     * of Double Degree Slides as Balanced groups.
     * @return StudentControlFileIterator.
     */
    public StudentControlFileIterator getDDIterator() {
        var countList =
                Arrays.stream(
                        getBalancedSumList(doubleDegreeSlides.size())
                ).boxed().toList();
        return new StudentControlFileIterator(
                doubleDegreeSlides, countList);
    }


    /**
     * This method sets the data of the statistics Table Slide
     * statistics include all Students not only the ones willBeInSlides
     * and exclues Silver/Gold from Groups.
     *
     * @param allStudentsFromControlFile the read students from Control file.
     */
    private void initialiseAttributsForStatisticsTable(
            final List<StudentControlfile> allStudentsFromControlFile) {
        // Statistics-Table Data.
        allStudents = allStudentsFromControlFile;

        studisOfBachelorWithoutGoldOrSilver = allStudents.stream()
                .filter(studentControlfile ->
                        (studentControlfile.getAbschlussart()
                        .equalsIgnoreCase(ArtOfAbschluss.ART_BACHELOR_SC)
                        || studentControlfile.getAbschlussart()
                           .equalsIgnoreCase(ArtOfAbschluss.ART_BACHELOR_ED))
                        && studentControlfile.getAuszeichnung()
                                == Auszeichnung.NONE)
                .toList();

        studisOfMasterInGermanWithoutGoldOrSilver = allStudents.stream()
                .filter(studentControlfile ->
                        (studentControlfile.getAbschlussart()
                        .equalsIgnoreCase(ArtOfAbschluss.ART_MASTER_SC)
                        || studentControlfile.getAbschlussart()
                           .equalsIgnoreCase(ArtOfAbschluss.ART_MASTER_ED))
                        && !studentControlfile.getDoubleDegree()
                        && !studentControlfile.getStudiengang()
                             .equalsIgnoreCase(Studiengang.ICE_FULL)
                        && studentControlfile.getAuszeichnung()
                                == Auszeichnung.NONE)
                .toList();

        studisOfMaterInternaWithoutGoldOrSilver = allStudents.stream()
                .filter(studentControlfile ->
                        studentControlfile.getStudiengang()
                        .equalsIgnoreCase(Studiengang.ICE_FULL)
                        && !studentControlfile.getDoubleDegree()
                        && studentControlfile.getAuszeichnung()
                                == Auszeichnung.NONE)
                .toList();

        studisWithDoubleDegreeWithoutGoldOrSilver = allStudents.stream()
                .filter(studentControlfile ->
                        studentControlfile.getDoubleDegree()
                        && studentControlfile.getAuszeichnung()
                                == Auszeichnung.NONE)
                .toList();

        numOfStudentsWithGoldAndSilver = (int) allStudents.stream()
                .filter(studentControlfile ->
                        studentControlfile.getAuszeichnung()
                                != Auszeichnung.NONE
                       ).count();
    }

    /**
     * This method sets all subgroups for the slides Alphabetic sorted.
     * @param allStudentsFromControlFile students read from Control File.
     */
    private void initialiseAttributsForAllSlides(
            final List<StudentControlfile> allStudentsFromControlFile) {
        // other Folien Data
        studentsWillBeInSlides = allStudentsFromControlFile.stream()
                .filter(studentControlfile ->
                        studentControlfile.getWillHaveSlide()
                ).collect(Collectors.toList());

        sortByName(studentsWillBeInSlides);

        // inkl. Silver/Gold
        bachelorSlides = studentsWillBeInSlides.stream()
                .filter(studentControlfile ->
                        studentControlfile.getAbschlussart()
                        .equalsIgnoreCase(ArtOfAbschluss.ART_BACHELOR_SC)
                        || studentControlfile.getAbschlussart()
                            .equalsIgnoreCase(ArtOfAbschluss.ART_BACHELOR_ED))
                .toList();


        // without DoubleDegree and without International-Stg,
        // inkl.Gold and Silver.
        cleanMasterSlides = studentsWillBeInSlides.stream()
                .filter(studentControlfile ->
                        (studentControlfile.getAbschlussart()
                        .equalsIgnoreCase(ArtOfAbschluss.ART_MASTER_SC)
                        || studentControlfile.getAbschlussart()
                        .equalsIgnoreCase(ArtOfAbschluss.ART_MASTER_ED))
                        && !studentControlfile.getDoubleDegree()
                        && !studentControlfile.getStudiengang()
                                .equalsIgnoreCase(Studiengang.ICE_FULL))
                .toList();

        // without DoubleDegree, inkl. Gold and Silver.
        internationalMasterSlides = studentsWillBeInSlides.stream()
                .filter(studentControlfile ->
                        studentControlfile.getStudiengang()
                        .equalsIgnoreCase(Studiengang.ICE_FULL)
                        && !studentControlfile.getDoubleDegree())
                .toList();

        // DoubleDegree, can be Master International or Master German,
        // inkl. Gold and Silver.
        doubleDegreeSlides = studentsWillBeInSlides.stream()
                .filter(studentControlfile ->
                        studentControlfile.getDoubleDegree())
                .toList();
    }

    /**
     * This method gives back the students of the Bachelor Slides.
     * @return list of Students of the Bachelor Slides .
     */
    public List<StudentControlfile> getBachelorSlides() {
        return bachelorSlides;
    }

    /**
     * This method gives back the number of students for the Bachelor Slides.
     * @return number of Students of the Bachelor Slides .
     */
    public int getNumStudentsOfBachelorSlides() {
        return bachelorSlides.size();
    }

    /**
     * This method gives back the students of the Clean Master Slides.
     * @return list of Students of the Master Slides .
     */
    public List<StudentControlfile> getCleanMasterSlides() {
        return cleanMasterSlides;
    }

    /**
     * This method gives back the number of students for
     * the Clean Master Slides.
     * @return number of Students of the Master Slides .
     */
    public int getNumStudentsOfCleanMasterSlides() {
        return cleanMasterSlides.size();
    }

    /**
     * This method gives back the students of
     * the International Master Slides.
     * @return list of Students of the International Master Slides .
     */
    public List<StudentControlfile> getInternationalMasterSlides() {
        return internationalMasterSlides;
    }

    /**
     * This method gives back the number of students
     * for the International Master Slides.
     * @return number of Students of the International Master Slides .
     */
    public int getNumStudentsOfInternationalMasterSlides() {
        return internationalMasterSlides.size();
    }

    /**
     * This method gives back the students
     * of the Double Degree Slides.
     * @return list of Students of the Double Degree Slides .
     */
    public List<StudentControlfile> getDoubleDegreeSlides() {
        return doubleDegreeSlides;
    }

    /**
     * This method gives back the number of students
     * for the Double Degree Slides.
     * @return number of Students of the Double Degree Slides .
     */
    public int getNumStudentsOfDoubleDegreeSlides() {
        return doubleDegreeSlides.size();
    }

    /**
     * This method gives back the students of the slides in the right
     * order of the groups in slides.
     * @return list of Students in the order of the groups in slides.
     */
    @SuppressWarnings("unchecked")
public List<StudentControlfile> getStudentsForSlides() {
        return ListUtils.union(
                ListUtils.union(bachelorSlides, cleanMasterSlides),
                ListUtils.union(doubleDegreeSlides, internationalMasterSlides));
    }


    /**
     * This method returns a list of the Titles of the first Column of the
     * Statistics Table.
     * if a group-Title have 0 students, then it will be decided
     * by INCLUDE_EMPTY_CATEGORIES if it's in the table.
     *
     * @return List of Strings as the first Column of
     *         the statistics Table from top until bottom.
     */
    @SuppressWarnings("unused") // Code is togglable.
public List<String> getFirstColumnOfStatisticsTable() {
        List<String> titels = new ArrayList<>();
        // Toggle inclusion of empty categories
        final boolean includeEmptyCategories = true;
        if (includeEmptyCategories
                || !studisOfBachelorWithoutGoldOrSilver.isEmpty()) {
            titels.add(TableTitles.BACHELOR);
        }
        if (includeEmptyCategories
                || !studisOfMasterInGermanWithoutGoldOrSilver.isEmpty()) {
            titels.add(TableTitles.MASTER_GERMAN);
        }
        if (includeEmptyCategories
                || !studisOfMaterInternaWithoutGoldOrSilver.isEmpty()) {
            titels.add(TableTitles.MASTER_INTERNATIONAL);
        }
        if (includeEmptyCategories
                || !studisWithDoubleDegreeWithoutGoldOrSilver.isEmpty()) {
            titels.add(TableTitles.DOUBLE_DEGREE);
        }
        if (includeEmptyCategories
                || numOfStudentsWithGoldAndSilver > 0) {
            titels.add(TableTitles.WITH_BADGES);
        }
        return titels;
    }

    /**
     * this method returns a list of the number of Students of every row of
     * (the second Column) of the Statistics Table.
     * if a Title/Absolventen-Group have 0 students, then it will be decided
     * by INCLUDE_EMPTY_CATEGORIES if it's in the table.
     *
     * @return List of Integers as the Second Column
     *         of the statistics Table from top until bottom.
     */
    @SuppressWarnings("unused")
    public List<Integer> getSecondColumnOfStatisticsTable() {
        List<Integer> amounts = new ArrayList<>();
        // Toggle inclusion of empty categories
        final boolean includeEmptyCategories = true;
        if (includeEmptyCategories
                || !studisOfBachelorWithoutGoldOrSilver.isEmpty()) {
            amounts.add(studisOfBachelorWithoutGoldOrSilver.size());
        }
        if (includeEmptyCategories
                || !studisOfMasterInGermanWithoutGoldOrSilver.isEmpty()) {
            amounts.add(studisOfMasterInGermanWithoutGoldOrSilver.size());
        }
        if (includeEmptyCategories
                || !studisOfMaterInternaWithoutGoldOrSilver.isEmpty()) {
            amounts.add(studisOfMaterInternaWithoutGoldOrSilver.size());
        }
        if (includeEmptyCategories
                || !studisWithDoubleDegreeWithoutGoldOrSilver.isEmpty()) {
            amounts.add(studisWithDoubleDegreeWithoutGoldOrSilver.size());
        }
        if (includeEmptyCategories
                || numOfStudentsWithGoldAndSilver > 0) {
            amounts.add(numOfStudentsWithGoldAndSilver);
        }
        return amounts;
    }

}
