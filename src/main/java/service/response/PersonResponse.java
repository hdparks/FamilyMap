package service.response;

import domain.Person;

/**
 * represents a response to a request made to the /person or /person/personID endpoint
 */
public class PersonResponse extends Response{
    public Person person;

    /**
     * Creates a successful PersonResponse object
     * @param person the person object which will be returned
     */
    public PersonResponse(Person person){
        super(true);
        this.person = person;
    }

    /**
     * Creates a failing PersonResponse object with a description of what failed
     * @param errorMessage a message detailing how the Person/ID service failed
     */
    public PersonResponse(String errorMessage){
        super(errorMessage,false);
    }

}
