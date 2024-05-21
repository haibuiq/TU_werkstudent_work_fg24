package tud.bp.group32.student;

public class StudentAlumnifile implements Student {
  /**
   * attribute matrikelnummer of student.
   */
  private final String matrikelnr;
  /**
   * attribute vorname of student.
   */
  private String vorname;
  /**
   * attribute nachname of student.
   */
  private String nachname;
  /**
   * attribute studiengang of student.
   */
  private String studiengang;

  /**
   * public constructor that takes the student matriculation
   * number and sets it to the class attribute.
   * @param matrikelNr matriculation number of student.
   */
  public StudentAlumnifile(final String matrikelNr) {
    this.matrikelnr = matrikelNr;
  }

  /**
   * public constructor.
   * @param matrikelNr matriculation number of student.
   * @param vorName first name of student.
   * @param nachName last name of student.
   * @param studienGang studiengang of student.
   */
  public StudentAlumnifile(final String matrikelNr, final String vorName,
                           final String nachName, final String studienGang) {
    this.matrikelnr = matrikelNr;
    this.vorname = vorName;
    this.nachname = nachName;
    this.studiengang = studienGang;
  }

  /**
   * getter for matriculation number.
   * @return matriculation number of student.
   */
  @Override
  public String getMatrikelnr() {
    return matrikelnr;
  }

  /**
   * getter for first name.
   * @return first name of student.
   */
  public String getVorname() {
    return vorname;
  }

  /**
   * setter for first name of student.
   * @param vorName of student.
   */
  public void setVorname(final String vorName) {
    this.vorname = vorName;
  }

  /**
   * getter for last name.
   * @return last name of student.
   */
  public String getNachname() {
    return nachname;
  }

  /**
   * setter for last name.
   * @param nachName of student.
   */
  public void setNachname(final String nachName) {
    this.nachname = nachName;
  }

  /**
   * getter for studiengang.
   * @return studiengang of student.
   */
  public String getStudiengang() {
    return studiengang;
  }

  /**
   * setter for studiengang.
   * @param studienGang of student.
   */
  public void setStudiengang(final String studienGang) {
    this.studiengang = studienGang;
  }

  /**
   * presents student information as a string.
   * @return String information of student.
   */
  @Override
  public String toString() {
    return "StudentAlumnifile{"
            +
            "matrikelnr='" + matrikelnr + '\''
            +
            ", vorname='" + vorname + '\''
            +
            ", nachname='" + nachname + '\''
            +
            ", studiengang='" + studiengang + '\''
            +
            '}';
  }
}
