package database_access;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import static org.junit.Assert.*;

public class DatabaseTest {

    Database db;

    @Before
    public void setUp(){
        db = new Database();
    }

    @After
    public void tearDown() throws Exception{
        if (db.hasOpenConnection()){
            db.closeConnection(true);
        }
        db = null;
    }

    @Test
    public void openConnection() throws Exception {
        Connection conn = db.openConnection();
        assert(!conn.isClosed());
        db.closeConnection(true);
        conn = null;
    }

    @Test
    public void closeConnection() throws Exception {
        Connection conn = db.openConnection();
        db.closeConnection(false);
        assert(conn.isClosed());
        conn = null;
    }

    @Test
    public void createTables() throws Exception {
        db.createTables();
        Connection conn = db.openConnection();
        ResultSet rs = conn.prepareStatement(
                "SELECT name FROM sqlite_master " +
                "WHERE type = 'table' ").executeQuery();
        System.out.println("Printing table names found within database:");
        rs.next();
        assertEquals(rs.getString(1), "users");
        rs.next();
        assertEquals(rs.getString(1),"persons");
        rs.next();
        assertEquals(rs.getString(1),"events");
        rs.next();
        assertEquals(rs.getString(1),"authTokens");

        assertFalse(rs.next());

        rs.close();
        //  If that didn't crash it, we are good to go
    }

    @Test
    public void clearTables() throws Exception {
        db.createTables();

        //  Fill tables with data
        Connection conn = db.openConnection();
        conn.prepareStatement(
                "INSERT INTO users (userName, password, email, firstName, lastName, gender, personID)" +
                        "VALUES ('user', 'pass', 'email', 'first', 'last', 'f', '001')").executeUpdate();
        conn.prepareStatement(
                "INSERT INTO persons (personID, descendant, firstName, lastName, gender)" +
                "VALUES ('001', 'user', 'first', 'last', 'f')").executeUpdate();
        conn.prepareStatement(
                "INSERT INTO events (eventID, descendant, personID, latitude, longitude, country, city, eventType, year)" +
                        "VALUES ('1','user', '001', 111111,22222, 'USA', 'Provo', 'Birthday', 1994)").executeUpdate();
        conn.prepareStatement(
                "INSERT INTO authTokens (userName, authToken)" +
                        "VALUES ('user', '101010')").executeUpdate();
        db.closeConnection(true);


        db.clearTables();
        conn = db.openConnection();

        ResultSet rs = conn.prepareStatement("SELECT * FROM users").executeQuery();
        assertFalse(rs.next());
        rs = conn.prepareStatement("SELECT * FROM persons").executeQuery();
        assertFalse(rs.next());
        rs = conn.prepareStatement("SELECT * FROM events").executeQuery();
        assertFalse(rs.next());
        rs = conn.prepareStatement("SELECT * FROM authTokens").executeQuery();
        assertFalse(rs.next());

    }
}