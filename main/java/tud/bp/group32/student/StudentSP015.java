package tud.bp.group32.student;


import org.apache.commons.lang3.StringUtils;
import tud.bp.group32.constants.AnredeTyp;
import tud.bp.group32.constants.ArtOfAbschluss;
import tud.bp.group32.constants.Auszeichnung;
import tud.bp.group32.constants.ShortArtOfAbschluss;

/**
 * Represents a student with attributes specific to the SP015 system.
 */
public class StudentSP015 implements Student {
    /**
     * The matriculation number of the student.
     */
    private final String matrikelnr;

    /**
     * The title or salutation of the student (e.g., "Herr" or "Frau").
     * Can take the value of HERR/FRAU Constants defined in the AnredeTyp class.
     */
    private String anrede;

    /**
     * The abbreviation for the type of degree the student is pursuing.
     */
    private String abschlussAbkuerzung;

    /**
     * The type of degree the student is pursuing.
     */
    private String abschlussart;

    /**
     * The first name of the student.
     */
    private String vorname;

    /**
     * The last name of the student.
     */
    private String nachname;

    /**
     * The course of study the student is enrolled in.
     */
    private String studiengang;

    /**
     * The type of degree thesis or final project the student is working on.
     */
    private String abschlussArbeit;

    /**
     * Indicates whether the student is pursuing a double degree.
     */
    private boolean doubleDegree;

    /**
     * The examiner or supervisor of the student's degree thesis.
     */
    private String prueferIn;

    /**
     * The type of  honor received by the student
     * (e.g., GOLD, SILVER).
     */
    private Auszeichnung auszeichnung;

    /**
     * The grade or mark received by the student for their degree.
     */
    private String abschlussNote;

    /**
     * The judgment or verdict associated with the student's
     * degree (e.g., "Mit Auszeichnung").
     */
    private String urteil;

    /**
     * Constructs a StudentSP015 object with the given matriculation number.
     * @param matrikelNr The matriculation number of the student.
     */
    public StudentSP015(final String matrikelNr) {
        this.matrikelnr = matrikelNr;
    }

    /**
     * Constructs a StudentSP015 object with the given parameters.
     * @param matrikelnrParam The matriculation number of the student.
     * @param anredeParam The title or salutation of the student.
     * @param abschlussAbkuerzungParam The abbreviation for the type of degree.
     * @param abschlussartParam The type of degree.
     * @param vornameParam The first name of the student.
     * @param nachnameParam The last name of the student.
     * @param studiengangParam The course of study of the student.
     * @param abschlussArbeitParam The type of degree thesis or final project.
     * @param doubleDegreeParam  if student is pursuing a double degree.
     * @param prueferInParam The examiner or supervisor.
     * @param auszeichnungParam The type of honor received by the student.
     * @param abschlussNoteParam The grade received by the student for degree.
     * @param urteilParam The judgment associated with the student's degree.
     */
    public StudentSP015(
            final String matrikelnrParam,
            final String anredeParam,
            final String abschlussAbkuerzungParam,
            final String abschlussartParam,
            final String vornameParam,
            final String nachnameParam,
            final String studiengangParam,
            final String abschlussArbeitParam,
            final boolean doubleDegreeParam,
            final String prueferInParam,
            final Auszeichnung auszeichnungParam,
            final String abschlussNoteParam,
            final String urteilParam) {
        this.matrikelnr = matrikelnrParam;
        this.anrede = anredeParam;
        this.abschlussAbkuerzung = abschlussAbkuerzungParam;
        this.abschlussart = abschlussartParam;
        this.vorname = vornameParam;
        this.nachname = nachnameParam;
        this.studiengang = studiengangParam;
        this.abschlussArbeit = abschlussArbeitParam;
        this.prueferIn = prueferInParam;
        this.doubleDegree = doubleDegreeParam;
        if (doubleDegree) {
            this.abschlussAbkuerzung = ShortArtOfAbschluss.SHORT_MASTER_SC;
        }
        this.auszeichnung = auszeichnungParam;
        this.abschlussNote = abschlussNoteParam;
        this.urteil = urteilParam;
    }

    /**
     * Constructs a StudentSP015 object with the given parameters.
     * This constructor is intended for the Freitagsliste use.
     * @param matrikelnrParam The matriculation number of the student.
     * @param vornameParam The first name of the student.
     * @param nachnameParam The last name of the student.
     * @param studiengangParam The course of study of the student.
     * @param abschlussAbkuerzungParam The abbreviation for the type of degree.
     * @param doubleDegreeParam  student is pursuing a double degree.
     * @param auszeichnungParam The type of honor received by the student.
     */
    public StudentSP015(final String matrikelnrParam,
                        final String vornameParam,
                        final String nachnameParam,
                        final String studiengangParam,
                        final String abschlussAbkuerzungParam,
                        final boolean doubleDegreeParam,
                        final Auszeichnung auszeichnungParam) {
        this.matrikelnr = matrikelnrParam;
        this.abschlussAbkuerzung = abschlussAbkuerzungParam;
        this.vorname = vornameParam;
        this.nachname = nachnameParam;
        this.studiengang = studiengangParam;
        this.doubleDegree = doubleDegreeParam;
        this.auszeichnung = auszeichnungParam;

        // The following information is either derived or not known
        if (doubleDegree) {
            abschlussart = ArtOfAbschluss.ART_MASTER_DD;
            this.abschlussAbkuerzung = ShortArtOfAbschluss.SHORT_MASTER_SC;
        } else {
            abschlussAbkuerzung =
                    StringUtils.deleteWhitespace(abschlussAbkuerzung);
            if (abschlussAbkuerzung.equalsIgnoreCase(
                    ShortArtOfAbschluss.SHORT_MASTER_SC)) {
                abschlussart = ArtOfAbschluss.ART_MASTER_SC;
            } else if (
                    abschlussAbkuerzung.equalsIgnoreCase(
                            ShortArtOfAbschluss.SHORT_BACHELOR_SC)) {
                abschlussart = ArtOfAbschluss.ART_BACHELOR_SC;
            } else if (
                    abschlussAbkuerzung.equalsIgnoreCase(
                            ShortArtOfAbschluss.SHORT_MASTER_ED)) {
                abschlussart = ArtOfAbschluss.ART_MASTER_ED;
            } else if (
                    abschlussAbkuerzung.equalsIgnoreCase(
                            ShortArtOfAbschluss.SHORT_BACHELOR_ED)) {
                abschlussart = ArtOfAbschluss.ART_BACHELOR_ED;
            } else {
                abschlussart = AnredeTyp.NOT_DEFINED;
            }
        }

        this.anrede = AnredeTyp.NOT_DEFINED;
        this.abschlussArbeit = AnredeTyp.NOT_DEFINED;
        this.prueferIn = AnredeTyp.NOT_DEFINED;
        this.abschlussNote = AnredeTyp.NOT_DEFINED;
        this.urteil = deriveUrteilFromAuszeichnung(auszeichnung);
    }

    /**
     * Derives the judgment or verdict associated with the student's degree
     * from the type of distinction or honor received by the student.
     * @param auszeichnungParam The type of honor received by the student.
     * @return The judgment or verdict associated with the student's degree.
     */
    private String deriveUrteilFromAuszeichnung(
            final Auszeichnung auszeichnungParam) {
        if (auszeichnungParam == Auszeichnung.GOLD) {
            return "Mit Auszeichnung";
        } else if (auszeichnungParam == Auszeichnung.SILVER) {
            return "Sehr gut";
        } else {
            return AnredeTyp.NOT_DEFINED;
        }
    }

    // ++++ GETTERS AND SETTERS ++++
    /**
     * Retrieves the matriculation number of the student.
     * @return The matriculation number.
     */
    public String getMatrikelnr() {
        return this.matrikelnr;
    }

    /**
     * Retrieves the title or salutation of the student.
     * @return The title or salutation.
     */
    public String getAnrede() {
        return this.anrede;
    }

    /**
     * Sets the title or salutation of the student.
     * @param anredeParam The title or salutation to set.
     */
    public void setAnrede(final String anredeParam) {
        this.anrede = anredeParam;
    }

    /**
     * Retrieves the abbreviation for the type of degree of the student.
     * @return The abbreviation for the type of degree.
     */
    public String getAbschlussAbkuerzung() {
        return this.abschlussAbkuerzung;
    }

    /**
     * Sets the abbreviation for the type of degree of the student.
     * @param abschlussAbkuerzungParam type of degree to set.
     */
    public void setAbschlussAbkuerzung(final String abschlussAbkuerzungParam) {
        this.abschlussAbkuerzung = abschlussAbkuerzungParam;
    }

    /**
     * Retrieves the type of degree of the student.
     * @return The type of degree.
     */
    public String getAbschlussart() {
        return abschlussart;
    }

    /**
     * Sets the type of degree of the student.
     * @param abschlussartParam The type of degree to set.
     */
    public void setAbschlussart(final String abschlussartParam) {
        this.abschlussart = abschlussartParam;
    }

    /**
     * Retrieves the first name of the student.
     * @return The first name.
     */
    public String getVorname() {
        return this.vorname;
    }

    /**
     * Sets the first name of the student.
     * @param vornameParam The first name to set.
     */
    public void setVorname(final String vornameParam) {
        this.vorname = vornameParam;
    }

    /**
     * Retrieves the last name of the student.
     * @return The last name.
     */
    public String getNachname() {
        return this.nachname;
    }

    /**
     * Sets the last name of the student.
     * @param nachnameParam The last name to set.
     */
    public void setNachname(final String nachnameParam) {
        this.nachname = nachnameParam;
    }

    /**
     * Retrieves the course of study of the student.
     * @return The course of study.
     */
    public String getStudiengang() {
        return this.studiengang;
    }

    /**
     * Sets the course of study of the student.
     * @param studiengangParam The course of study to set.
     */
    public void setStudiengang(final String studiengangParam) {
        this.studiengang = studiengangParam;
    }

    /**
     * Retrieves the type of degree thesis or final project of the student.
     * @return The type of degree thesis or final project.
     */
    public String getAbschlussarbeit() {
        return this.abschlussArbeit;
    }

    /**
     * Sets the type of degree thesis or final project of the student.
     * @param abschlussArbeitParam
     */
    public void setAbschlussarbeit(final String abschlussArbeitParam) {
        this.abschlussArbeit = abschlussArbeitParam;
    }

    /**
     * Retrieves whether the student is pursuing a double degree.
     * @return True if the student is pursuing a double degree, false otherwise.
     */
    public boolean getDoubleDegree() {
        return doubleDegree;
    }

    /**
     * Sets whether the student is pursuing a double degree.
     * @param doubleDegreeParam
     */
    public void setDoubleDegree(final boolean doubleDegreeParam) {
        this.doubleDegree = doubleDegreeParam;
    }

    /**
     * Retrieves the examiner or supervisor of the student's
     * degree thesis or final project.
     * @return The examiner or supervisor.
     */
    public String getPrueferIn() {
        return this.prueferIn;
    }

    /**
     * Sets the examiner or supervisor of the student's
     * degree thesis or final project.
     * @param prueferInParam The examiner or supervisor to set.
     */
    public void setPrueferIn(final String prueferInParam) {
        this.prueferIn = prueferInParam;
    }

    /**
     * Retrieves the type of distinction or honor received by the student.
     * @return The type of distinction or honor.
     */
    public Auszeichnung getAuszeichnung() {
        return this.auszeichnung;
    }

    /**
     * Sets the type of distinction or honor received by the student.
     * @param auszeichnungParam The type of distinction or honor to set.
     */
    public void setAuszeichnung(final Auszeichnung auszeichnungParam) {
        this.auszeichnung = auszeichnungParam;
    }

    /**
     * Retrieves the grade or mark received by the student for their degree.
     * @return The grade or mark.
     */
    public String getAbschlussNote() {
        return this.abschlussNote;
    }

    /**
     * Sets the grade or mark received by the student for their degree.


     * @param abschlussNoteParam The grade or mark to set.
     */
    public void setAbschlussNote(final String abschlussNoteParam) {
        this.abschlussNote = abschlussNoteParam;
    }

    /**
     * Retrieves the judgment or verdict associated with the student's degree.
     * @return The judgment or verdict.
     */
    public String getUrteil() {
        return this.urteil;
    }

    /**
     * Sets the judgment or verdict associated with the student's degree.
     * @param urteilParam The judgment or verdict to set.
     */
    public void setUrteil(final String urteilParam) {
        this.urteil = urteilParam;
    }
    /**
     *  Creates a StudentControlfile object based on the attributes of
     *  this student.
     * @return The StudentControlfile object created.
     */
    public StudentControlfile asStudentControlfile() {
        return new StudentControlfile(
                matrikelnr,
                anrede,
                abschlussAbkuerzung,
                abschlussart,
                vorname,
                nachname,
                studiengang,
                abschlussArbeit,
                doubleDegree,
                prueferIn,
                auszeichnung,
                false,
                abschlussNote,
                urteil);
    }


    /**
     * takes an object and checks if param is equal to it.
     * @param obj
     * @return obj
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof StudentSP015)) {
            return false;
        }
        StudentSP015 student = (StudentSP015) obj;
        return
            matrikelnr.equals(student.getMatrikelnr())
            && anrede.equals(student.getAnrede())
            && abschlussAbkuerzung.equals(student.getAbschlussAbkuerzung())
            && abschlussart.equals(student.getAbschlussart())
            && vorname.equals(student.getVorname())
            && nachname.equals(student.getNachname())
            && studiengang.equals(student.getStudiengang())
            && abschlussArbeit.equals(student.getAbschlussarbeit())
            && doubleDegree == student.getDoubleDegree()
            && prueferIn.equals(student.getPrueferIn())
            && auszeichnung.equals(student.getAuszeichnung())
            && abschlussNote.equals(student.getAbschlussNote())
            && urteil.equals(student.getUrteil());
    }

    /**
     * Returns the hash code of the object.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
