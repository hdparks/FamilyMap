package domain;

import database_access.Database;
import org.junit.After;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class GeneratorTest {
    Database db = new Database();

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    public void constructor() throws Exception {
        Generator generator = new Generator(db.openConnection());

    }

}