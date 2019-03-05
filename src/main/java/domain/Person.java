package domain;

import java.util.Objects;

/**
 * Represents a person
 */
public class Person {

    /**
     * A counter that allows for unique PersonID's
     */
    private static int counterID = 0;

    /**
     * Assigns a unique PersonID value each time it is called.
     * @return a unique PersonID
     */
    public static String getNewPersonID(){
        counterID += 1;
        return Integer.toString(counterID);
    }


    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public Person(String personID, String descendant, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Unique identifier for this person (non-empty string)
     */
    public String personID;

    /**
     *  User (userName) to which this person belongs
     */
    public String descendant;

    /**
     * Person’s first name (non-empty string)
     */
    public String firstName;

    /**
     * Person’s last name (non-empty string)
     */
    public String lastName;

    /**
     * Person’s gender (string: "f" or "m")
     */
    public String gender;

    /**
     * ID of person’s father (possibly null)
     */
    public String fatherID;

    /**
     * ID of person’s mother (possibly null)
     */
    public String motherID;

    /**
     *  ID of person’s spouse (possibly null)
     */
    public String spouseID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) &&
                descendant.equals(person.descendant) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                gender.equals(person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID);
    }
}
