package domain;

import java.util.Objects;


/**
 * Represents a person
 */
public class Person {



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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    /**
     * Since the database automatically generates the personID, this method
     * should only be used when retrieving a person FROM the database.
     * @param personID id
     * @param descendant username
     * @param firstName name
     * @param lastName name
     * @param gender m/f
     * @param father id
     * @param mother id
     * @param spouse id
     */
    public Person(String personID, String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse) throws GenderException {
        if (!gender.matches("m|f")) throw new GenderException();
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * Assigns id at the same time
     * @param user
     */
    public Person(User user){

        this.descendant = user.userName;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.gender = user.gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID='" + personID + '\'' +
                ", descendant='" + descendant + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                ", spouse='" + spouse + '\'' +
                '}';
    }

    /**
     * This one is used when creating new persons
     * @param descendant username
     * @param firstName name
     * @param lastName name
     * @param gender m/f
     * @param father id
     * @param mother id
     * @param spouse id
     */
    public Person(String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse) throws GenderException {
        if (!gender.matches("m|f")) throw new GenderException();
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
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
    public String father;

    /**
     * ID of person’s mother (possibly null)
     */
    public String mother;

    /**
     *  ID of person’s spouse (possibly null)
     */
    public String spouse;

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
                Objects.equals(father, person.father) &&
                Objects.equals(mother, person.mother) &&
                Objects.equals(spouse, person.spouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, descendant, firstName, lastName, gender, father, mother, spouse);
    }
}
