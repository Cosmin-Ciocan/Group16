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

    @Test
    void cityRequestTestRequestNull() throws SQLException {
        app.cityRequest(null,"test.md");
    }

    @Test
    void cityRequestTestRequestEmpty() throws SQLException {
        app.cityRequest("","test.md");
    }

    @Test
    void cityRequestTestFilenameNull() throws SQLException {
        app.cityRequest("request",null);
    }

    @Test
    void cityRequestTestFilenameEmpty() throws SQLException {
        app.cityRequest("request","");
    }

    @Test
    void capitalCityRequestTestRequestNull() throws SQLException {
        app.capitalCityRequest(null, "test.md");
    }

    @Test
    void capitalCityRequestTestRequestEmpty() throws SQLException {
        app.capitalCityRequest("", "test.md");
    }

    @Test
    void capitalCityRequestTestFilenameNull() throws SQLException {
        app.capitalCityRequest("request", null);
    }

    @Test
    void capitalCityRequestTestFilenameEmpty() throws SQLException {
        app.capitalCityRequest("request", "");
    }

    @Test
    void outputCountryTestFilenameNull() {
        ArrayList<Country> countries =  new ArrayList<>();
        app.outputCountry(countries, null);
    }

    @Test
    void outputCountryTestCountryNull(){
        app.outputCountry(null, "test.md");
    }

    @Test
    void outputCountryTestFilenameEmpty(){
        ArrayList<Country> countries =  new ArrayList<>();
        app.outputCountry(countries, "");
    }

    @Test
    void outputCityTestFilenameNull(){
        ArrayList<City> cities = new ArrayList<>();
        app.outputCity(cities, null);
    }

    @Test
    void outputCityTestFilenameEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.outputCity(cities, "");
    }

    @Test
    void outputCityTestCityNull(){
        app.outputCity(null, "test.md");
    }
}
