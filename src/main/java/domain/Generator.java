package domain;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import database_access.DataAccessException;
import database_access.EventDao;
import database_access.PersonDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Random;
import java.util.logging.Logger;

public class Generator {

    private static Logger logger = Logger.getLogger("Generator");

    private static final int CURRENT_YEAR = 2019;
    public int eventsAdded;
    public int personsAdded;

    private Gson gson;
    @SerializedName("data")
    private String[] fnames;

    @SerializedName("data")
    private String[] mnames;

    @SerializedName("data")
    private String[] snames;

    @SerializedName("data")
    private Location[] locations;

    private Connection conn;


    private class StringWrapper{
        String[] data;
    }

    private class LocationWrapper{
        Location[] data;
    }

    /**
     * Generic constructor
     * @throws FileNotFoundException if the operation fails
     */
    public Generator(Connection conn) throws FileNotFoundException {
        logger.info("Creating Generator object...");
        this.gson = new Gson();

        try {

            StringWrapper f = gson.fromJson(new FileReader(new File("familymapserver"+File.separator+"json"+File.separator+"fnames.json")), StringWrapper.class);
            this.fnames = f.data;
            logger.info("fnames loaded");

            StringWrapper m = gson.fromJson(new FileReader(new File("familymapserver"+File.separator+"json"+File.separator+"mnames.json")), StringWrapper.class);
            this.mnames = m.data;
            logger.info("mnames loaded");

            StringWrapper s = gson.fromJson(new FileReader(new File("familymapserver"+File.separator+"json"+File.separator+"snames.json")), StringWrapper.class);
            this.snames = s.data;
            logger.info("snmaes loaded");

            LocationWrapper l = gson.fromJson(new FileReader(new File("familymapserver"+File.separator+"json"+File.separator+"locations.json")), LocationWrapper.class);
            this.locations = l.data;
            logger.info("locations loaded");

        } catch (Exception ex){
            logger.severe(ex.getMessage());
            throw ex;
        }


        this.conn = conn;

        this.eventsAdded = 0;
        this.personsAdded = 0;

        logger.info("Generator object created");
    }

    /**
     * Get a random female first name
     * @return a random female first name
     */
    private String getfName(){
        Random random = new Random();
        return fnames[random.nextInt(fnames.length)];
    }

    /**
     * Get a random male first name
     * @return a random male first name
     */
    private String getmName(){
        Random random = new Random();
        return mnames[random.nextInt(mnames.length)];
    }

    /**
     * Get a random last name
     * @return a random last name
     */
    private String getsName(){
        Random random = new Random();
        return snames[random.nextInt(snames.length)];
    }

    /**
     * Get a random location
     * @return a random location object
     */
    private Location getLocation(){
        Random random = new Random();
        return this.locations[random.nextInt(this.locations.length)];
    }


    /**
     * Load an event with the random location
     * @param event the Event to be loaded
     */
    private void loadLocation(Event event){
        Location location = getLocation();
        event.city = location.city;
        event.country = location.country;
        event.latitude = location.latitude;
        event.longitude = location.longitude;
    }

    /**
     * Load an event with a specific location
     * @param event the Event to be loaded
     * @param location the Location to load
     */
    private void loadLocation(Event event, Location location){
        event.city = location.city;
        event.country = location.country;
        event.latitude = location.latitude;
        event.longitude = location.longitude;
    }



    /**
     * A helper class to pull JSON data for event locations
     */
    private class Location {
        String country;
        String city;
        String latitude;
        String longitude;
    }

    /**
     * Generates a mother
     * @param person the child
     * @return a female Person object with matching descendant to person
     */
    private Person generateParent(Person person, String lastName,String gender) throws DataAccessException{
        String firstName = (gender.equals("m")) ?
                getmName():
                getfName();

        return new Person(
                person.descendant,
                firstName,
                lastName,
                gender,
                null,
                null,
                null
        );
    }

    /**
     * Generates an event of a given type, year, for a given person type
     * @param person the Person object
     * @param year the year
     * @param eventType the event type
     * @return the new Event object
     */
    private Event generateEvent(Person person,int year,String eventType){
        Event event = new Event(
                person.descendant,
                person.personID,
                null,
                null,
                null,
                null,
                eventType,
                year);

        //  Load in random location
        loadLocation(event);

        return event;
    }

    /**
     * Generates the given number of generations, updating the database
     * @param userPerson the User object of the descendant
     * @param generations the number of generations to generate
     */
    public void generateGenerations(Person userPerson, int generations) throws DataAccessException{
        logger.info("Generating generations...");
        try{
            //  Spin up Database driver, PersonDao and EventDao
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);

            //  Add the user to the database
            personDao.add(userPerson);
            this.personsAdded++;

            //  Make user Birthday event, save it
            int userBirthYear = 2000;
            Event userBirthday = generateEvent(userPerson, userBirthYear,"Birth" );
            eventDao.add(userBirthday);
            this.eventsAdded++;
            //  generate specified number of generations
            generateNextGeneration(userPerson,userBirthYear,personDao,eventDao,generations);

        } catch (DataAccessException ex){
            logger.severe(ex.getMessage());

            //  Close and roll back
            throw ex;
        }

    }

    /**
     * Recursive function to build the next generation
     * @param child child Person object
     * @param childBirthYear child's birth year
     * @param personDao PersonDao instance
     * @param eventDao EventDao instance
     * @param generations number of recursions
     * @throws DataAccessException should operation fail
     */
    private void generateNextGeneration(Person child, int childBirthYear, PersonDao personDao, EventDao eventDao, int generations) throws DataAccessException {
        logger.entering("Generator","NextGeneration");
        if ( generations <= 0 ){
            //  We are done
            return;
        }


        //  If child is female and married, choose random last name for parents
        String lastName = (child.gender.equals("f") && child.spouseID != null)
                ? getsName()
                : child.lastName;

        //  Generate mother and father Person objects
        Person mother = generateParent(child, lastName,"f");
        personDao.add(mother);
        this.personsAdded++;

        Person father = generateParent(child, lastName, "m");
        personDao.add(father);
        this.personsAdded++;

        //  Set everyone's id's to one another
        personDao.updateRelationships(child, mother, father);


        //  Generate events for parents

        //  Marriage:
        int parentMarriageYear = getParentMarriageYear(childBirthYear);
        Location parentMarriageLocation = getLocation();

        Event motherMarriage = new Event( mother.descendant,mother.personID,null,null,null,null,"Marriage", parentMarriageYear);
        Event fatherMarriage = new Event( father.descendant,father.personID,null,null,null,null,"Marriage", parentMarriageYear);

        loadLocation(motherMarriage,parentMarriageLocation);
        loadLocation(fatherMarriage,parentMarriageLocation);

        eventDao.add(motherMarriage);
        eventDao.add(fatherMarriage);
        this.eventsAdded += 2;

        //  Birth:
        int fatherBirthYear = getBirthYearFromMarriageYear(parentMarriageYear);
        Event fatherBirth = generateEvent(father,fatherBirthYear, "Birth");

        int motherBirthYear = getBirthYearFromMarriageYear(parentMarriageYear);
        Event motherBirth = generateEvent(mother, motherBirthYear, "Birth");

        eventDao.add(motherBirth);
        eventDao.add(fatherBirth);
        this.eventsAdded += 2;

        //  Death:
        int fatherDeathYear = getDeathYearFromBirthYear(fatherBirthYear);

        Event fatherDeath;
        if(fatherDeathYear > CURRENT_YEAR){
            fatherDeath = generateEvent(father, CURRENT_YEAR, "Subscribed to PewDiePie");
        } else {
            fatherDeath = generateEvent(father, fatherDeathYear, "Death");
        }

        int motherDeathYear = getDeathYearFromBirthYear(motherBirthYear);
        Event motherDeath;
        if(motherDeathYear > CURRENT_YEAR){
            motherDeath = generateEvent(mother, CURRENT_YEAR,"Subscribed to PewDiePie");
        } else {
            motherDeath = generateEvent(mother, CURRENT_YEAR, "Death");
        }

        eventDao.add(fatherDeath);
        eventDao.add(motherDeath);
        this.eventsAdded += 2;

        //  Recurse on both parents
        generateNextGeneration(mother, motherBirthYear, personDao, eventDao,generations - 1);
        generateNextGeneration(father, fatherBirthYear, personDao, eventDao, generations - 1);


    }

    //  QUICK NOTES ON EVENT GENERATION
    //  - A person will only ever get married between ages 20 and 30
    //  - A child will only ever be born within 10 years of parent's marriage
    //  - A person will only ever die after 60 years, and will die before 90
    //  This ensures


    /**
     * Get the parent's marriage year based on the child's birth year
     * @param birthYear birth year of the child
     * @return randomized marriage year of the parents (0 - 10 years before birth)
     */
    public int getParentMarriageYear(int birthYear){
        Random random = new Random();
        return birthYear - random.nextInt(10);
    }

    /**
     * Get a person's birth year based on their marriage year
     * @return randomized birth year (20 - 30 years before marriage)
     */
    public int getBirthYearFromMarriageYear(int marriageYear){
        Random random = new Random();
        return marriageYear - 20 - random.nextInt(10);
    }

    /**
     * Get a person's death year based on their birth year.
     * People won't die until sixty,
     * @param birthYear birth year of
     * @return randomized death year (60-90 years old)
     */
    public int getDeathYearFromBirthYear(int birthYear){
       Random random = new Random();
       return birthYear + 60 + random.nextInt(30);
    }

}
