package domain;

/**
 * Represents a person
 */
public class Person {
    /**
     * Unique identifier for this person (non-empty string)
     */
    String personID;

    /**
     *  User (Username) to which this person belongs
     */
    String descendant;

    /**
     * Person’s first name (non-empty string)
     */
    String firstName;

    /**
     * Person’s last name (non-empty string)
     */
    String lastName;

    /**
     * Person’s gender (string: "f" or "m")
     */
    String gender;

    /**
     * ID of person’s father (possibly null)
     */
    String fatherID;

    /**
     * ID of person’s mother (possibly null)
     */
    String motherID;

    /**
     *  ID of person’s spouse (possibly null)
     */
    String spouseID;
}
