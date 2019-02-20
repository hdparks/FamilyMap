package service.response;

import domain.Person;

/**
 * Represents a response to a request to the /person/[personID] endpoint
 */
public class PersonIDResponse extends Response {
    /**
     * Person data object
     */
    Person person;

    /**
     * Create a valid response object with the valid person data
     * @param person the Person object with the given ID
     */
    PersonIDResponse(Person person){
        super(true);
        this.person = person;
    }

    /**
     * Create a failing response with error message
     * @param message string detailing the error in the service
     */
    PersonIDResponse(String message){
        super(message,false);
    }
}
