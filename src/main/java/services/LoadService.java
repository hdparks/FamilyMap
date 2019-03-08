package services;

import database_access.*;
import domain.Event;
import domain.Person;
import domain.User;
import requests.LoadRequest;
import responses.LoadResponse;

import java.sql.Connection;
import java.util.logging.Logger;


/**
 * Clears all data from database, just like clear, then loads the posted data.
 */
public class LoadService implements Service<LoadRequest, LoadResponse>{

    private static Logger logger = Logger.getLogger("LoadService");

    /**
     * Clears the database, loads in data from req
     * @param req LoadRequest object
     * @return res successful Response success, failing Response object on failure
     */
    @Override
    public LoadResponse serveResponse(LoadRequest req) throws DataAccessException, HttpRequestParseException {
        //  Parse request
        if (    req.getEvents()  == null ||
                req.getPersons() == null ||
                req.getUsers()   == null){
            throw new HttpRequestParseException("Invalid parameters: missing data");
        }


        Database db = new Database();
        try {
            //  Spin up database connection
            Connection conn = db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            PersonDao personDao = new PersonDao(conn);
            UserDao userDao = new UserDao(conn);
            EventDao eventDao = new EventDao(conn);

            //  Clear from the database
            authTokenDao.clear();
            personDao.clear();
            userDao.clear();
            eventDao.clear();

            //  Add Users
            int usersAdded = 0;
            for (User user : req.getUsers()){
                userDao.add(user);
                usersAdded++;
            }

            //  Add Persons
            int personsAdded = 0;
            for (Person person : req.getPersons()){
                personDao.add(person);
                personsAdded++;
            }

            //  Add Events
            int eventsAdded = 0;
            for (Event event : req.getEvents()){
                eventDao.add(event);
                eventsAdded++;
            }

            db.closeConnection(true);

            String resMessage = "Successfully added "+usersAdded+" users, "
                    + personsAdded + " persons, and " + eventsAdded + " events to the database.";

            logger.fine(resMessage);

            return new LoadResponse(resMessage, true);

        } catch (DataAccessException ex){
            db.closeConnection(false);
            logger.severe(ex.getMessage());
            throw ex;

        } finally {
            //  Close connection just to be sure
            db.closeConnection(false);
        }
    }
}
