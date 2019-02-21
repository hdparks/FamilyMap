package domain;

/**
 * Represents a person
 */
public class Person {

    /**
     * Allows for generation of unique Person ID's from 1 to MAXINTEGER.
     */
    private static int idTracker = 0;

    /**
     * Returns a unique PersonID (String)
     * @return a unique PersonID
     */
    static String assignID(){
        Person.idTracker += 1;
        return String.valueOf(Person.idTracker);
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

}
