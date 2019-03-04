package responses;

import domain.Person;

/**
 * Represents a responses to a requests to the /person/[personID] endpoint
 */
public class PersonIDResponse extends Response {
    /**
     * Person data object
     */
    Person person;

    /**
     * Create a valid responses object with the valid person data
     * @param person the Person object with the given ID
     */
    PersonIDResponse(Person person){
        super(true);
        this.person = person;
    }

    /**
     * Create a failing responses with error message
     * @param message string detailing the error in the services
     */
    PersonIDResponse(String message){
        super(message,false);
    }
}
