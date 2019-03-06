package responses;

import domain.Person;

/**
 * Represents a responses to a requests to the /person/[personID] endpoint
 */
public class PersonIDResponse extends Response {

    public String descendant;
    public String personID;
    public String firstName;
    public String lastName;
    public String gender;
    public String father;
    public String mother;
    public String spouse;

    /**
     * Create a valid responses object with the valid person data
     * @param person the Person object with the given ID
     */
    public PersonIDResponse(Person person){
        super(true);
        this.descendant = person.descendant;
        this.personID = person.personID;
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.gender = person.gender;
        this.father = person.fatherID;
        this.mother = person.motherID;
        this.spouse = person.spouseID;
    }

    /**
     * Create a failing responses with error message
     * @param message string detailing the error in the services
     */
    PersonIDResponse(String message){
        super(message,false);
    }
}
