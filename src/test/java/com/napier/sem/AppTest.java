package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void countryRequestTestRequestNull() throws SQLException {
        app.countryRequest(null,"test.md");
    }

    @Test
    void countryRequestTestRequestEmpty() throws SQLException {
        app.countryRequest("","test.md");
    }

    @Test
    void countryRequestTestFilenameNull() throws SQLException {
        app.countryRequest("request",null);
    }

    @Test
    void countryRequestTestFilenameEmpty() throws SQLException {
        app.countryRequest("request","");
    }
}
