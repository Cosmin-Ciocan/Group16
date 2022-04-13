package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    //Tests for City Printer
    @Test
    void cityPrinterNull(){
        app.cityPrinter(null);
    }
    @Test
    void printCitiesTestEmpty()
    {
        ArrayList<City> cities = new ArrayList<>();
        app.cityPrinter(cities);
    }

    @Test
    void printCitiesTestContainsNull()
    {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.cityPrinter(cities);
    }

    //Tests for Country Printer
    @Test
    void countryPrinterNull(){
        app.countryPrinter(null);
    }

    @Test
    void printCountriesTestEmpty()
    {
        ArrayList<Country> countries = new ArrayList<>();
        app.countryPrinter(countries);
    }

    @Test
    void printCountriesTestContainsNull()
    {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.countryPrinter(countries);
    }

    //Tests for city lister


}
