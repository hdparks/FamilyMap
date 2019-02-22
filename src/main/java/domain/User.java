package domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                gender.equals(user.gender) &&
                personID.equals(user.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, email, firstName, lastName, gender, personID);
    }

    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }
}
