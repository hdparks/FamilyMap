package services;

import database_access.*;
import domain.Generator;
import domain.Person;
import domain.User;
import handlers.AuthUtilities;
import requests.FillRequest;
import responses.FillResponse;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Populates the server's database with generated data for the specified username.
 *
 * The required 'username' parameter must be a registered user.
 * If there is any data in the database already associated with the given username, it is deleted.
 * The optional 'generations' parameter lets the caller specify the number of generations of ancestors to be generated,
 * and must be a non-negative integer, {default = 4}.
 */
public class FillService implements Service<FillRequest, FillResponse> {

    private static Logger logger = Logger.getLogger("FillService");

    /**
     * Handle the incoming FillRequest
     * @param req a requests object representing a requests to the /fill endpoint
     * @return a valid FillResponse if successful, a generic error Response if not
     */
    @Override
    public FillResponse serveResponse(FillRequest req) throws DataAccessException, HttpRequestParseException {
        //  Get username
        String username;
        int generations = 4;

        //  Parse path to get correct username/generations
        if(req.getPath().contains("/")){

            String[] pathSplit = req.getPath().split("/");

            username = pathSplit[0];
            generations = Integer.parseInt(pathSplit[1]);

        } else {

            username = req.getPath();

        }

        //  Parse username
        if (!AuthUtilities.isValidUsername(username)){
            throw new HttpRequestParseException("Invalid parameter: username");
        }

        //  Parse generations
        if (generations < 1){
            throw new HttpRequestParseException("Invalid parameter: generations");
        }

        logger.fine("/fill endpoint with user: "+username+", generations: "+generations);

        Database db = new Database();
        try {
            //  Spin up database connection
            Connection conn = db.openConnection();

            //  Delete user ancestry data
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            UserDao userDao = new UserDao(conn);

            eventDao.deleteByDescendant(username);
            personDao.deletePersonsByDescendant(username);

            //  Generate person from User data
            User user = userDao.getUserByName(username);
            Person person = new Person(user);

            //  Generate lots of things:
            Generator generator = new Generator(conn);
            generator.generateGenerations(person,generations);

            db.closeConnection(true);

            String responseMessage = "Successfully added "+generator.personsAdded+" persons and "+generator.eventsAdded+" events to the database.";
            return new FillResponse(responseMessage, true);

        } catch (FileNotFoundException ex){
            logger.severe(ex.getMessage());
            throw new DataAccessException("Family generator file not found");

        } finally {
            db.closeConnection(false);
        }
    }
}
