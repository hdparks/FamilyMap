package responses;

import domain.Person;

import java.util.List;

/**
 * represents a responses to a requests made to the /person endpoint
 */
public class PersonResponse extends Response{

    /**
     * A list of Person objects
     */
    public Person[] persons;

    /**
     * Creates a successful PersonResponse object
     * @param persons the Person objects which will be returned
     */
    public PersonResponse(Person[] persons){
        super(true);
        this.persons = persons;
    }

    /**
     * Creates a failing PersonResponse object with a description of what failed
     * @param errorMessage a message detailing how the Person services failed
     */
    public PersonResponse(String errorMessage){
        super(errorMessage,false);
    }

}
