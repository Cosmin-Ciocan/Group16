package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    /**
     * Testing countryRequest() with null request
     * @throws SQLException -
     */
    @Test
    void countryRequestTestRequestNull() throws SQLException {
        app.countryRequest(null,"test.md");
    }

    /**
     * Testing countryRequest() with empty request
     * @throws SQLException -
     */
    @Test
    void countryRequestTestRequestEmpty() throws SQLException {
        app.countryRequest("","test.md");
    }

    /**
     * Testing countryRequest() with null filename
     * @throws SQLException -
     */
    @Test
    void countryRequestTestFilenameNull() throws SQLException {
        app.countryRequest("request",null);
    }

    /**
     * Testing countryRequest() with empty filename
     * @throws SQLException -
     */
    @Test
    void countryRequestTestFilenameEmpty() throws SQLException {
        app.countryRequest("request","");
    }

    /**
     * Testing cityRequest() with null request
     * @throws SQLException -
     */
    @Test
    void cityRequestTestRequestNull() throws SQLException {
        app.cityRequest(null,"test.md");
    }

    /**
     * Testing cityRequest() with empty request
     * @throws SQLException -
     */
    @Test
    void cityRequestTestRequestEmpty() throws SQLException {
        app.cityRequest("","test.md");
    }

    /**
     * Testing cityRequest() with null filename
     * @throws SQLException -
     */
    @Test
    void cityRequestTestFilenameNull() throws SQLException {
        app.cityRequest("request",null);
    }

    /**
     * Testing cityRequest() with empty filename
     * @throws SQLException -
     */
    @Test
    void cityRequestTestFilenameEmpty() throws SQLException {
        app.cityRequest("request","");
    }

    /**
     * Testing capitalCityRequest() with null request
     * @throws SQLException -
     */
    @Test
    void capitalCityRequestTestRequestNull() throws SQLException {
        app.capitalCityRequest(null, "test.md");
    }

    /**
     * Testing capitalCityRequest() with empty request
     * @throws SQLException -
     */
    @Test
    void capitalCityRequestTestRequestEmpty() throws SQLException {
        app.capitalCityRequest("", "test.md");
    }

    /**
     * Testing capitalCityRequest() with null filename
     * @throws SQLException -
     */
    @Test
    void capitalCityRequestTestFilenameNull() throws SQLException {
        app.capitalCityRequest("request", null);
    }

    /**
     * Testing capitalCityRequest() with empty filename
     * @throws SQLException -
     */
    @Test
    void capitalCityRequestTestFilenameEmpty() throws SQLException {
        app.capitalCityRequest("request", "");
    }

    /**
     * Testing outputCountry() with null filename
     */
    @Test
    void outputCountryTestFilenameNull() {
        ArrayList<Country> countries =  new ArrayList<>();
        app.outputCountry(countries, null);
    }

    /**
     * Testing outputCountry() with null filename
     */
    @Test
    void outputCountryTestCountryNull(){
        app.outputCountry(null, "test.md");
    }

    /**
     * Testing outputCountry() with empty filename
     */
    @Test
    void outputCountryTestFilenameEmpty(){
        ArrayList<Country> countries =  new ArrayList<>();
        app.outputCountry(countries, "");
    }

    /**
     * Testing outputCity() with null filename
     */
    @Test
    void outputCityTestFilenameNull(){
        ArrayList<City> cities = new ArrayList<>();
        app.outputCity(cities, null);
    }

    /**
     * Testing outputCity() with empty filename
     */
    @Test
    void outputCityTestFilenameEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.outputCity(cities, "");
    }

    /**
     * Testing outputCity() with null city list
     */
    @Test
    void outputCityTestCityNull(){
        app.outputCity(null, "test.md");
    }

    /**
     * Testing outputCapitalCity() with null city list
     */
    @Test
    void outputCapitalCityTestCapitalCityNull(){
        app.outputCapitalCity(null, "test.md");
    }

    /**
     * Testing outputCapitalCity() with null filename
     */
    @Test
    void outputCapitalCityTestFilenameNull(){
        ArrayList<City> cities = new ArrayList<>();
        app.outputCapitalCity(cities, null);
    }

    /**
     * Testing outputCapitalCity() with empty filename
     */
    @Test
    void outputCapitalCityTestFilenameEmpty(){
        ArrayList<City> cities = new ArrayList<>();
        app.outputCapitalCity(cities, "");
    }

    /**
     * Testing outputCountryPopulation() with null filename
     */
    @Test
    void outputCountryPopulationTestFilenameNull(){
        app.outputCountryPopulation(null);
    }

    /**
     * Testing outputCountryPopulation() with empty filename
     */
    @Test
    void outputCountryPopulationTestFilenameEmpty(){
        app.outputCountryPopulation("");
    }

    /**
     * Testing outputRegionPopulation() with null filename
     */
    @Test
    void outputRegionPopulationTestFilenameNull(){
        app.outputRegionPopulation(null);
    }

    /**
     * Testing outputRegionPopulation() with empty filename
     */
    @Test
    void outputRegionPopulationTestFilenameEmpty(){
        app.outputRegionPopulation("");
    }

    /**
     * Testing outputContinentPopulation() with null filename
     */
    @Test
    void outputContinentPopulationTestFilenameNull(){
        app.outputContinentPopulation(null);
    }

    /**
     * Testing outputContinentPopulation() with empty filename
     */
    @Test
    void outputContinentPopulationTestFilenameEmpty(){
        app.outputContinentPopulation("");
    }

    /**
     * Testing outputLanguagePopulation() with null filename
     */
    @Test
    void outputLanguagePopulationTestFilenameNull(){
        app.outputLanguagePopulation(null);
    }

    /**
     * Testing outputLanguagePopulation() with empty filename
     */
    @Test
    void outputLanguagePopulationTestFilenameEmpty(){
        app.outputLanguagePopulation("");
    }

    /**
     * Testing getCityPopulation() with null city name
     */
    @Test
    void getCityPopulationTestCityNameNull(){
        app.getCityPopulation(null);
    }

    /**
     * Testing getCityPopulation() with empty city name
     */
    @Test
    void getCityPopulationTestCityNameEmpty(){
        app.getCityPopulation("");
    }

    /**
     * Testing getDistrictPopulation() with null district name
     */
    @Test
    void getDistrictPopulationTestDistrictNameNull(){
        app.getDistrictPopulation( null);
    }

    /**
     * Testing getDistrictPopulation() with empty district name
     */
    @Test
    void getDistrictPopulationTestDistrictNameEmpty(){
        app.getDistrictPopulation( "");
    }

    /**
     * Testing getCountryPopulation() with null country code
     */
    @Test
    void getCountryPopulationTestCountryCodeNull(){
        ArrayList<Country> countryList = new ArrayList<>();
        app.getCountryPopulation(null, countryList );
    }

    /**
     * Testing getCountryPopulation() with empty country code
     */
    @Test
    void getCountryPopulationTestCountryCodeEmpty(){
        ArrayList<Country> countryList = new ArrayList<>();
        app.getCountryPopulation("", countryList );
    }

    /**
     * Testing getRegionPopulation() with null region name
     */
    @Test
    void getRegionPopulationTestRegionNameNull(){
        app.getRegionPopulation(null);
    }

    /**
     * Testing getRegionPopulation() with empty region name
     */
    @Test
    void getRegionPopulationTestRegionNameEmpty(){
        app.getRegionPopulation("");
    }

    /**
     * Testing getContinentPopulation() with null continent name
     */
    @Test
    void getContinentPopulationTestContinentNameNull(){
        app.getContinentPopulation(null);
    }

    /**
     * Testing getContinentPopulation() with empty continent name
     */
    @Test
    void getContinentPopulationTestContinentNameEmpty(){
        app.getContinentPopulation("");
    }

    /**
     * Testing getLanguagePopulation() with null language name
     */
    @Test
    void getLanguagePopulationTestLanguageNull(){
        app.getLanguagePopulation(null);
    }

    /**
     * Testing getLanguagePopulation() with empty language name
     */
    @Test
    void getLanguagePopulationTestLanguageEmpty(){
        app.getLanguagePopulation("");
    }

    /**
     * Testing getCity() with null city code
     */
    @Test
    void getCityTestCityCodeNull(){
        app.getCity(null);
    }

    /**
     * Testing getCountryCityPopulation() with null country code
     */
    @Test
    void getCountryCityPopulationTestCountryCodeNull(){
        app.getCountryCityPopulation(null);
    }

    /**
     * Testing getCountryCityPopulation() with empty country code
     */
    @Test
    void getCountryCityPopulationTestCountryCodeEmpty(){
        app.getCountryCityPopulation("");
    }

    /**
     * Testing databaseRequester() with null query
     */
    @Test
    void databaseRequesterTestQueryNull(){
        app.databaseRequester(null);
    }

    /**
     * Testing databaseRequester() with empty query
     */
    @Test
    void databaseRequesterTestQueryEmpty(){
        app.databaseRequester("");
    }

    /**
     * Testing filePrinter() with null filename
     */
    @Test
    void filePrinterTestFilenameNull(){
        StringBuilder test = new StringBuilder();
        app.filePrinter(null, test);
    }

    /**
     * Testing filePrinter() with empty filename
     */
    @Test
    void filePrinterTestFilenameEmpty(){
        StringBuilder test = new StringBuilder();
        app.filePrinter("", test);
    }

}
