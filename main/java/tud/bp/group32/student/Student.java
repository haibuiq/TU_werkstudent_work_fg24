package tud.bp.group32.student;

public interface Student {
    // This boolean determines whether the data for a student whose matrikelnr.
    // shows up more than once should be taken by the first or last entry.
    /**
     * takes the data from the first enrty if set to false.
     */
    boolean TAKE_DATA_FROM_FIRST_ENTRY = false;

    /**
     * Length of MatrikelNr.
     */
    int MATRIKELNR_LENGTH = 7;

    /**
     * getter for matriculation number.
     * @return matrikelnummer.
     */
    String getMatrikelnr();

    /**
     * getter for first name.
     * @return vorname.
     */
    String getVorname();
    /**
     * getter for last name.
     * @return nachname.
     */
    String getNachname();
    /**
     * getter for studiengang.
     * @return studiengang.
     */
    String getStudiengang();

}
