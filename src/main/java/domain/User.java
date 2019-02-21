package domain;

/**
 *  Represents a user
 */
public class User {

    /**
     * A unique user name, (non-empty string)
     */
    public String userName;

    /**
     * User's password, (non-empty string)
     */
    public String password;

    /**
     * User's email address, (non-empty string)
     */
    public String email;

    /**
     * User's first name, (non-empty string)
     */
    public String firstName;

    /**
     * User's last name, (non-empty string)
     */
    public String lastName;

    /**
     * User's gender, ("m" or "f")
     */
    public String gender;

    /**
     * Unique personID assigned to this User's generated Person object
     */
    public String personID;

    User(String userName, String password, String email, String firstName, String lastName, String gender, String personID){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }
}
