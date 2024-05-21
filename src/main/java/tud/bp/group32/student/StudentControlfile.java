package tud.bp.group32.student;

import tud.bp.group32.constants.Auszeichnung;

public final class StudentControlfile extends StudentSP015 {
  /**
   * String for Student missing in alumni list.
   */
  public static final String FEHLT_ALUMNILIST
          = "Student fehlt in Alumniliste";
  /**
   * String for Student missing in sp015 list.
   */
  public static final String FEHLT_SP015
          = "Student fehlt in SP015";
  /**
   * String for Student missing in alumni and sp015 list.
   */
  public static final String FEHLT_SP015_ALUMNILIST
          = "Student fehlt in SP015 UND Alumniliste";

  /**
   * boolean true if student will have slide. False otherwise.
   */
  private boolean willHaveSlide;

  /**
   * String comment can have one of the constant strings above as value.
   */
  private String comment;

  /**
   * boolean true if student is in sp015. False otherwise.
   */
  private boolean isInSP015;

  /**
   * boolean true if student is in alumni. False otherwise.
   */
  private boolean isInAlumniFile;


  /**
   * public constructor which calls parent constructor.
   * @param matrikelnr of student.
   */
  public StudentControlfile(final String matrikelnr) {
    super(matrikelnr);
  }


  /**
   * public constructor that builds object with following information.
   * @param matrikelnr of student.
   * @param anrede of student.
   * @param abschlussAbkuerzung of student.
   * @param abschlussart of student.
   * @param vorname of student.
   * @param nachname of student.
   * @param studiengang of student.
   * @param abschlussArbeit of student.
   * @param doubleDegree of student.
   * @param prueferIn of student.
   * @param auszeichnung of student.
   * @param willHaveslide of student.
   * @param abschlussNote of student.
   * @param urteil of student.
   */
  public StudentControlfile(final String matrikelnr, final String anrede,
                            final String abschlussAbkuerzung,
                            final String abschlussart, final String vorname,
                            final String nachname,
                            final String studiengang,
                            final String abschlussArbeit,
                            final boolean doubleDegree,
                            final String prueferIn,
                            final Auszeichnung auszeichnung,
                            final boolean willHaveslide,
                            final String abschlussNote,
                            final String urteil) {

    super(matrikelnr, anrede, abschlussAbkuerzung,
            abschlussart, vorname, nachname,
            studiengang, abschlussArbeit,
            doubleDegree, prueferIn,
            auszeichnung, abschlussNote,
            urteil);
    this.willHaveSlide = willHaveslide;
    this.setComment("");
  }

  /**
   * public constructor that takes following student information.
   * @param matrikelnr
   * @param vorname
   * @param nachname
   * @param studiengang
   * @param abschlussAbkuerzung
   * @param doubleDegree
   * @param auszeichnung
   */
  public StudentControlfile(final String matrikelnr, final String vorname,
                            final String nachname, final String studiengang,
                            final String abschlussAbkuerzung,
                            final boolean doubleDegree,
                            final Auszeichnung auszeichnung) {

    super(matrikelnr, vorname, nachname, studiengang,
            abschlussAbkuerzung, doubleDegree, auszeichnung);
    this.setComment("");
  }


  /**
   * getter for willHaveSlide.
   * @return true/false if student will have a slide.
   */
  public boolean getWillHaveSlide() {
    return willHaveSlide;
  }

  /**
   * setter for willHaveSlide.
   * @param willHaveslide
   */
  public void setWillHaveSlide(final boolean willHaveslide) {
    this.willHaveSlide = willHaveslide;
    if (willHaveSlide) {
      setIsInSP015(true);
      setIsInAlumnifile(true);
    }
  }

  /**
   * getter for comment.
   * @return comment.
   */
  public String getComment() {
    return comment;
  }

  /**
   * setter for comment.
   * @param comment1
   */
  public void setComment(final String comment1) {
    this.comment = comment1;
  }

  /**
   * getter for isInSp015.
   * @return isInSP015
   */
  public boolean getIsInSP015() {
    return isInSP015;
  }

  /**
   * getter for isInAlumni.
   * @return isInAlumniFile
   */
  public boolean getIsInAlumnifile() {
    return isInAlumniFile;
  }

  /**
   * setter for isInSp015.
   * @param newIsInSP015
   */
  public void setIsInSP015(final boolean newIsInSP015) {
    this.isInSP015 = newIsInSP015;
  }

  /**
   * setter for isInAlumni.
   * @param newIsInAlumniFile
   */
  public void setIsInAlumnifile(final boolean newIsInAlumniFile) {
    this.isInAlumniFile = newIsInAlumniFile;
  }

  /**
   * converts student information to string.
   * @return string student information.
   */
  public String toString() {
    return "StudentControleFile{ "
            + "Matrikelnummer= " + getMatrikelnr()
            + ", Anrede= " + getAnrede()
            + ", Nachname= " + getNachname()
            + ", Vorname= " + getVorname()
            + ", Abschlussart= " + getAbschlussart()
            + ", Double Degree= " + getDoubleDegree()
            + ", Abschluss Abk.= " + getAbschlussAbkuerzung()
            + ", Studiengang= " + getStudiengang()
            + ", Auszeichnung= " + getAuszeichnung()
            + ", Pruefer/In= " + getPrueferIn()
            + ", AbschlussNote= " + getAbschlussNote()
            + ", Urteil= " + getUrteil()
            + ", Abschlussarbeit= " + getAbschlussarbeit()
            + ", WillHaveSlide= " + getWillHaveSlide() + '}';
  }


  @Override
  public boolean equals(final Object obj) {
    if (obj == null
        || !(obj instanceof StudentControlfile)
        || !super.equals(obj)) {
      return false;
    }

    StudentControlfile student = (StudentControlfile) obj;
    return
       willHaveSlide == student.getWillHaveSlide()
       && comment.equals(student.getComment())
       && isInSP015 == student.getIsInSP015()
       && isInAlumniFile == student.getIsInAlumnifile();
  }
  @Override
  public int hashCode() {
      return super.hashCode();
  }

}
