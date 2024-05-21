package tud.bp.group32.student;

import tud.bp.group32.constants.Auszeichnung;

public class StudentFreitagsliste implements Student {
    /**
     * Matrikelnummer.
     */
    private final String matrikelnr;
    /**
     * Vorname.
     */
    private String vorname;
    /**
     * Nachname.
     */
    private String nachname;
    /**
     * Studiengang.
     */
    private String studiengang;
    /**
     * Abschlussabkürzung.
     */
    private String abschlussAbkuerzung;
    /**
     * Double Degree boolean value.
     */
    private boolean doubleDegree;
    /**
     * Auszeichnung.
     */
    private Auszeichnung auszeichnung;

    /**
     * Constructor of the StudentFreitagsliste class with only the matrikelnr.
     * @param matrikelnrParam parameter of type String.
     */
    public StudentFreitagsliste(final String matrikelnrParam) {
        this.matrikelnr = matrikelnrParam;
    }

    /**
     * Constructor of the StudentFreitagsliste class with all the attributes.
     * @param matrikelnrParam parameter of type String.
     * @param vornameParam parameter of type String.
     * @param nachnameParam parameter of type String.
     * @param studiengangParam parameter of type String.
     * @param abschlussAbkuerzungParam parameter of type String.
     * @param doubleDegreeParam parameter of type boolean.
     * @param auszeichnungParam parameter of type Auszeichnung.
     */
    public StudentFreitagsliste(final String matrikelnrParam,
                                final String vornameParam,
                                final String nachnameParam,
                                final String studiengangParam,
                                final String abschlussAbkuerzungParam,
                                final boolean doubleDegreeParam,
                                final Auszeichnung auszeichnungParam) {
        this.matrikelnr = matrikelnrParam;
        this.vorname = vornameParam;
        this.nachname = nachnameParam;
        this.studiengang = studiengangParam;
        this.abschlussAbkuerzung = abschlussAbkuerzungParam;
        this.doubleDegree = doubleDegreeParam;
        this.auszeichnung = auszeichnungParam;
    }

    /**
     * Getter for attribute matrikelnr.
     * @return attribute matrikelnr.
     */
    @Override
    public String getMatrikelnr() {
        return matrikelnr;
    }

    /**
     * Getter for attribute vorname.
     * @return attribute vorname.
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setter for attribute vorname.
     * @param vornameParam parameter of type String.
     */
    public void setVorname(final String vornameParam) {
        this.vorname = vornameParam;
    }

    /**
     * Getter for attribute nachname.
     * @return attribute nachname.
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setter for attribute nachname.
     * @param nachnameParam parameter of type String.
     */
    public void setNachname(final String nachnameParam) {
        this.nachname = nachnameParam;
    }

    /**
     * Getter for attribute studiengang.
     * @return attribute studiengang.
     */
    public String getStudiengang() {
        return studiengang;
    }

    /**
     * Setter for attribute studiengang.
     * @param studiengangParam parameter of type String.
     */
    public void setStudiengang(final String studiengangParam) {
        this.studiengang = studiengangParam;
    }

    /**
     * Getter for attribute abschlussAbkuerzung.
     * @return attribute abschlussAbkuerzung.
     */
    public String getAbschlussAbkuerzung() {
        return abschlussAbkuerzung;
    }

    /**
     * Setter for attribute abschlussAbkuerzung.
     * @param abschlussAbkuerzungParam parameter of type String.
     */
    public void setAbschlussAbkuerzung(final String abschlussAbkuerzungParam) {
        this.abschlussAbkuerzung = abschlussAbkuerzungParam;
    }

    /**
     * Getter for attribute doubleDegree.
     * @return attribute doubleDegree.
     */
    public boolean getDoubleDegree() {
        return doubleDegree;
    }

    /**
     * Setter for attribute doubleDegree.
     * @param doubleDegreeParam parameter of type boolean.
     */
    public void setDoubleDegree(final boolean doubleDegreeParam) {
        this.doubleDegree = doubleDegreeParam;
    }

    /**
     * Getter for attribute auszeichnung.
     * @return attribute auszeichnung.
     */
    public Auszeichnung getAuszeichnung() {
        return auszeichnung;
    }

    /**
     * Setter for attribute auszeichnung.
     * @param auszeichnungParam parameter of type Auszeichnung.
     */
    public void setAuszeichnung(final Auszeichnung auszeichnungParam) {
        this.auszeichnung = auszeichnungParam;
    }

    /**
     * Changed into "final" as checkstyle suggests. This class is designed
     * to be extended, but this method did not have a comment explaining
     * how the extension could be safely done. So if the class is not
     * intended to be extended, then this method should either be "final",
     * or marked as "static/final/abstract/empty", or an annotation that
     * allows extension should be added.
     * @return a Student from the Control File.
     */
    public final StudentControlfile asStudentControlfile() {
        return new StudentControlfile(matrikelnr, vorname, nachname,
                studiengang, abschlussAbkuerzung, doubleDegree, auszeichnung);
    }

    /**
     * Changed into "final" as checkstyle suggests. This class is designed
     * to be extended, but this method did not have a comment explaining
     * how the extension could be safely done. So if the class is not
     * intended to be extended, then this method should either be "final",
     * or marked as "static/final/abstract/empty", or an annotation that
     * allows extension should be added.
     * @return a String of a student from the Freitagsliste.
     */
    @Override
    public final String toString() {
        return "StudentFreitagsliste{"
                + "matrikelnr='" + matrikelnr + '\''
                + ", vorname='" + vorname + '\''
                + ", nachname='" + nachname + '\''
                + ", studiengang='" + studiengang + '\''
                + ", abschlussAbkuerzung='" + abschlussAbkuerzung + '\''
                + ", doubleDegree=" + doubleDegree
                + ", auszeichnung=" + auszeichnung
                + '}';
    }
}
