package services;

import database_access.*;
import domain.Generator;
import domain.Person;
import domain.User;
import handlers.AuthUtilities;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.FillRequest;
import responses.FillResponse;

import javax.xml.crypto.Data;
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
    public FillResponse serveResponse(FillRequest req) throws HttpInternalServerError, HttpBadRequestException {


        String username;
        int generations = 4;
        String uri = req.getPath().substring("/fill/".length());

        //  Parse path to get correct username/generations
        if(uri.contains("/")){

            String[] pathSplit = uri.split("/");
            username = pathSplit[0];
            generations = Integer.parseInt(pathSplit[1]);

        } else { username = uri; }


        Database db = new Database();
        try {
            //  Spin up database connection
            Connection conn = db.openConnection();

            //  Parse username
            if (!AuthUtilities.isValidUsername(username, conn)){
                throw new HttpBadRequestException("Invalid parameter: username");
            }

            //  Parse generations
            if (generations < 1){
                throw new HttpBadRequestException("Invalid parameter: generations");
            }



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
            generator.generateGenerations(person, generations);

            db.closeConnection(true);

            String responseMessage = "Successfully added " + generator.personsAdded + " persons and " + generator.eventsAdded + " events to the database.";
            return new FillResponse(responseMessage, true);

        } catch (DataAccessException | FileNotFoundException ex){

            throw new HttpInternalServerError(ex.getMessage());

        }catch (NumberFormatException ex){

            throw new HttpBadRequestException(ex.getMessage());

        } finally {
            db.hardClose();
        }
    }
}
