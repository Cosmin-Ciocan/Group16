package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static java.lang.Math.round;

/**
 * main app
 */
public class App {

    // All the countries in the world organised by largest population to smallest.
    String countriesWorldPop = "SELECT * FROM `country` ORDER BY Population DESC";

    // The top N populated countries in the world where N is provided by the user
    String topCountriesWorldPop = "SELECT * FROM country ORDER BY Population DESC LIMIT 10";

    // All the cities in the world organised by largest population to smallest
    String cityWorldPop = "SELECT * FROM city ORDER BY Population DESC ";

    // The top N populated cities in the world where N is provided by the user.
    String topCitiesWorldPop = "SELECT * FROM city ORDER BY Population DESC LIMIT 10";

    // All the capital cities in the world organised by largest population to smallest
    String capitalCitiesWorldPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID)  ORDER BY city.Population DESC ";

    // The top N populated capital cities in the world where N is provided by the users.
    String topCapitalCitiesWorldPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID)  ORDER BY city.Population DESC LIMIT 10";

    // All the countries in a continent organised by largest population to smallest
    String countriesContinentPop = "SELECT * FROM country WHERE Continent = 'North America' ORDER BY Population DESC";

    // The top N populated countries in a continent where N is provided by the user
    String topCountriesContinentPop = "SELECT * FROM country WHERE Continent = 'North America' ORDER BY Population DESC LIMIT 10";

    // All the cities in a continent organised by largest population to smallest.
    String cityContinentPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE Continent = 'North America' ORDER BY city.Population DESC";

    // The top N populated cities in a continent where N is provided by the user.
    String topCitiesContinentPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE Continent = 'North America' ORDER BY city.Population DESC LIMIT 3";

    // All the capital cities in a continent organised by largest population to smallest.
    String capitalCitiesContinentPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Continent = 'North America')  ORDER BY city.Population DESC";

    // The top N populated capital cities in a continent where N is provided by the user.
    String topCapitalCitiesContinentPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Continent = 'North America')  ORDER BY city.Population DESC LIMIT 10";

    // All the countries in a region organised by largest population to smallest
    String countriesRegionPop = "SELECT * FROM country WHERE Region = 'Southern Europe' ORDER BY Population DESC";

    // The top N populated countries in a region where N is provided by the user
    String topCountriesRegionPop = "SELECT * FROM country WHERE Region = 'Southern Europe' ORDER BY Population DESC LIMIT 10";

    // All the cities in a region organised by largest population to smallest
    String cityRegionPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE country.Region = 'Caribbean' ORDER BY city.Population DESC";

    // The top N populated cities in a region where N is provided by the user.
    String topCitiesRegionPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE country.Region = 'Caribbean' ORDER BY city.Population DESC LIMIT 3";

    // All the capital cities in a region organised by largest to smallest.
    String capitalCitiesRegionPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Region = 'Caribbean') ORDER BY city.Population DESC";

    // The top N populated capital cities in a region where N is provided by the user.
    String topCapitalCitiesRegionPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Region = 'Caribbean') ORDER BY city.Population DESC LIMIT 3";

    // All the cities in a country organised by largest population to smallest
    String cityCountryPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE country.Name = 'France' ORDER BY city.Population DESC";

    // The top N populated cities in a country where N is provided by the user.
    String topCitiesCountryPop = "SELECT * FROM city INNER JOIN country ON (country.Code = city.CountryCode) WHERE country.Name = 'France' ORDER BY city.Population DESC LIMIT 3";

    // All the cities in a district organised by largest population to smallest
    String cityDistrictPop = "SELECT * FROM city WHERE District = 'Adana'";

    // The top N populated cities in a district where N is provided by the user.
    String topCitiesDistrictPop = "SELECT * FROM city WHERE District = 'Adana' LIMIT 3";


    public static void main(String[] args) throws SQLException {
        // Create new Application
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 0);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        a.countryRequest(a.countriesWorldPop, "WorldCountriesPopulation.md");
        a.countryRequest(a.countriesContinentPop, "ContinentCountriesPopulation.md");
        a.countryRequest(a.countriesRegionPop, "RegionCountriesPopulation.md");
        a.countryRequest(a.topCountriesWorldPop, "TopWorldCountriesPopulation.md");
        a.countryRequest(a.topCountriesContinentPop, "TopContinentCountriesPopulation.md");
        a.countryRequest(a.topCountriesRegionPop, "TopRegionCountriesPopulation.md");
        a.cityRequest(a.cityWorldPop, "WorldCitiesPopulation.md");
        a.cityRequest(a.cityContinentPop, "ContinentCitiesPopulation.md");
        a.cityRequest(a.cityRegionPop, "RegionCitiesPopulation.md");
        a.cityRequest(a.cityCountryPop, "CountryCitiesPopulation.md");
        a.cityRequest(a.cityDistrictPop, "DistrictCitiesPopulation.md");
        a.cityRequest(a.topCitiesWorldPop, "WorldTopCitiesPopulation.md");
        a.cityRequest(a.topCitiesContinentPop, "ContinentTopCitiesPopulation.md");
        a.cityRequest(a.topCitiesRegionPop, "RegionTopCitiesPopulation.md");
        a.cityRequest(a.topCitiesCountryPop, "CountryTopCitiesPopulation.md");
        a.cityRequest(a.topCitiesDistrictPop, "DistrictTopCitiesPopulation.md");
        a.capitalCityRequest(a.capitalCitiesWorldPop, "WorldCapitalCitiesPop.md");
        a.capitalCityRequest(a.capitalCitiesContinentPop, "ContinentCapitalCitiesPop.md");
        a.capitalCityRequest(a.capitalCitiesRegionPop, "RegionCapitalCitiesPop.md");
        a.capitalCityRequest(a.topCapitalCitiesWorldPop, "WorldTopCapitalCitiesPop.md");
        a.capitalCityRequest(a.topCapitalCitiesContinentPop, "ContinentTopCapitalCitiesPop.md");
        a.capitalCityRequest(a.topCapitalCitiesRegionPop, "RegionTopCapitalCitiesPop.md");
        a.outputContinentPopulation("DetailedPopulationContinent.md");
        a.outputRegionPopulation("DetailedPopulationRegion.md");
        a.outputCountryPopulation("DetailedPopulationCountry.md");

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connect to the MySQL database.
     */
    private Connection con = null;

    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://"+ location + "/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqlE) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqlE.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    // Main functions //

    /**
     *
     * @param request Sql Request
     * @param fileName Name of the output file
     * @throws SQLException Sql exception
     */
    public void countryRequest(String request, String fileName) throws SQLException {
        if(request == null || request.isEmpty()) {
            System.out.println("No request");
            return;
        }
        if(fileName == null || fileName.isEmpty()){
            System.out.println("Filename Missing");
            return;
        }
        ResultSet resultSet = databaseRequester(request);
        outputCountry(countryLister(resultSet), fileName);
    }

    /**
     *
     * @param request Sql Request
     * @param fileName Name of the output file
     * @throws SQLException Sql exception
     */
    public void cityRequest(String request, String fileName) throws SQLException {
        if(request == null || request.isEmpty()) {
            System.out.println("No request");
            return;
        }
        if(fileName == null || fileName.isEmpty()){
            System.out.println("Filename Missing");
            return;
        }
        ResultSet resultSet = databaseRequester(request);
        outputCity(cityLister(resultSet), fileName);
    }

    /**
     *
     * @param request Sql Request
     * @param fileName Name of the output file
     * @throws SQLException Sql exception
     */
    public void capitalCityRequest(String request, String fileName) throws SQLException {

        if(request == null || request.isEmpty()) {
            System.out.println("No request");
            return;
        }

        if(fileName == null || fileName.isEmpty()){
            System.out.println("Filename Missing");
            return;
        }

        ResultSet resultSet = databaseRequester(request);
        outputCapitalCity(cityLister(resultSet), fileName);
    }


    // PRINTERS //

    /**
     * Output country list to markdown file
     * @param countries list of country
     * @param filename name of the destination file
     */
    public void outputCountry(ArrayList<Country> countries, String filename) {
        // Check employees is not null
        if (countries == null) {
            System.out.println("No countries");
            return;
        }

        if(filename == null){
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty()){
            System.out.println("No filemame");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Code | Name | Continent | Region | Population | Capital |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- |\r\n");
        // Loop over all countries in the list
        for (Country cty : countries) {
            if (cty == null) continue;
            City capital = getCity(cty.capital);
            sb.append("| ").append(cty.code).append(" | ").append(cty.name).append(" | ").append(cty.continent).append(" | ").append(cty.region).append(" | ").append(cty.population).append(" | ").append(capital.name).append(" |\r\n");
        }
        filePrinter(filename, sb);
    }

    /**
     * Output country list to markdown file
     * @param cities list of city
     * @param filename name of the destination file
     */
    public void outputCity(ArrayList<City> cities, String filename) {
        // Check employees is not null
        if (cities == null) {
            System.out.println("No cities");
            return;
        }

        if(filename == null)
        {
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty())
        {
            System.out.println("No filename");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Name | Country | District | Population |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");
        // Loop over all cities in the list
        for (City city : cities) {
            if (city == null) continue;
            sb.append("| ").append(city.name).append(" | ").append(city.countryCode).append(" | ").append(city.district).append(" | ").append(city.population).append(" |\r\n");
        }
        filePrinter(filename, sb);
    }

    /**
     * Output country list to markdown file
     * @param cities list of city
     * @param filename name of the destination file
     */
    public void outputCapitalCity(ArrayList<City> cities, String filename) {
        if (cities == null) {
            System.out.println("No cities");
            return;
        }

        if(filename == null)
        {
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty())
        {
            System.out.println("No filename");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Name | Country | Population |\r\n");
        sb.append("| --- | --- | --- |\r\n");
        // Loop over all cities in the list
        for (City city : cities) {
            if (city == null) continue;
            sb.append("| ").append(city.name).append(" | ").append(city.countryCode).append(" | ").append(city.population).append(" |\r\n");
        }
        filePrinter(filename, sb);
    }


    /**
     * Output country population details to markdown file
     * @param filename name of the destination file
     */
    public void outputCountryPopulation(String filename) {
        ArrayList<Country> countries = getCountryList();
        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }
        if(filename == null)
        {
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty())
        {
            System.out.println("No filename");
            return;
        }

        StringBuilder sb = new StringBuilder();
        //print header
        sb.append("| Name | Total Population | Population living in cities | Population not living in cities |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");
        // Loop over all countries in the list
        for (Country cty : countries) {
            if (cty == null) continue;
            Double cityPopulation = (double) cty.cityPopulation;
            Double Population = (double) cty.population;
            int cityPopPerc = (int) round(cityPopulation / Population * 100);
            if (cityPopPerc > 100) {
                cityPopPerc = 100;
            }
            int notCityPopPerc = 100 - cityPopPerc;

            sb.append("| ").append(cty.name).append(" | ").append(cty.population).append(" | ").append(cityPopPerc).append(" % | ").append(notCityPopPerc).append(" % |\r\n");
        }
        filePrinter(filename, sb);
    }

    /**
     * Output region population details to markdown file
     * @param filename name of the destination file
     */
    public void outputRegionPopulation(String filename){
        ArrayList<Country> countries = getCountryList();
        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }

        if(filename ==  null){
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty()){
            System.out.println("No filename");
            return;
        }
        ArrayList<String> regionList = getRegionList();
        StringBuilder sb = new StringBuilder();

        //print header
        sb.append("| Name | Total Population | Population living in cities | Population not living in cities |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        Double population;
        Double cityPopulation;

        for (String region : regionList){
            population = 0.0;
            cityPopulation = 0.0;
            for (Country cty : countries){
                if (cty.region.equals(region)){
                    population += cty.population;
                    cityPopulation += cty.cityPopulation;
                }
            }
            int cityPopPerc = (int) (cityPopulation / population * 100);
            int notCityPopPerc = 100 - cityPopPerc;
            int finalPopulation = population.intValue() ;
            sb.append("| ").append(region).append(" | ").append(finalPopulation).append(" | ").append(cityPopPerc).append(" % | ").append(notCityPopPerc).append(" % |\r\n");
        }
        filePrinter(filename, sb);
    }

    /**
     * Output continent population details to markdown file
     * @param filename name of the destination file
     */
    public void outputContinentPopulation(String filename){
        ArrayList<Country> countries = getCountryList();
        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }

        if(filename == null)
        {
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty())
        {
            System.out.println("No filename");
            return;
        }
        ArrayList<String> continentList = getContinentList();
        StringBuilder sb = new StringBuilder();

        //print header
        sb.append("| Name | Total Population | Population living in cities | Population not living in cities |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        Double population;
        Double cityPopulation;

        for (String continent : continentList){
            population = 0.0;
            cityPopulation = 0.0;
            for (Country cty : countries){
                if (cty.continent.equals(continent)){
                    population += cty.population;
                    cityPopulation += cty.cityPopulation;
                }
            }
            int cityPopPerc = (int) round(cityPopulation / population * 100);
            int notCityPopPerc = 100 - cityPopPerc;
            int finalPopulation = population.intValue() ;
            sb.append("| ").append(continent).append(" | ").append(finalPopulation).append(" | ").append(cityPopPerc).append(" % | ").append(notCityPopPerc).append(" % |\r\n");
        }
        filePrinter(filename, sb);
    }

    /**
     * Output language details to markdown file
     * @param filename name of the destination file
     */
    public void outputLanguagePopulation(String filename){
        ArrayList<String> languages = new ArrayList<>();
        languages.add("Chinese");
        languages.add("Hindi");
        languages.add("Spanish");
        languages.add("English");
        languages.add("Arabic");

        StringBuilder sb = new StringBuilder();
        Long worldPop = getWorldPopulation();

        if(filename ==  null){
            System.out.println("No filename");
            return;
        }

        if(filename.isEmpty()){
            System.out.println("No filename");
            return;
        }

        //print header
        sb.append("| Name | Speaker Population | World Percentage |\r\n");
        sb.append("| --- | --- | --- |\r\n");

        for(String language : languages){
            Integer pop = getLanguagePopulation(language);
            float worldPerc = (((float) pop / (float) worldPop) * 100);
            sb.append("| ").append(language).append(" | ").append(pop).append(" | ").append(worldPerc).append(" % |\r\n");
        }
        filePrinter(filename,sb);
    }


    // Lister //
    /**
     * Convert a result set of database request into a list of city
     * @param resultSet Result of the SQL request
     * @return List of city
     */
     public ArrayList<City> cityLister(ResultSet resultSet) throws SQLException {

         ArrayList<City> cities = new ArrayList<>();
         while (resultSet.next()) {
             City city = new City();
             city.name = resultSet.getString("Name");
             city.population = resultSet.getInt("Population");
             city.countryCode = resultSet.getString("CountryCode");
             city.district = resultSet.getString("District");
             cities.add(city);
         }
         return cities;
     }

    /**
     * Convert a result set of database request into a list of country
     * @param resultSet Result of the SQL request
     * @return List of country
     */
    public ArrayList<Country> countryLister(ResultSet resultSet) throws SQLException {

        ArrayList<Country> countries = new ArrayList<>();
        while (resultSet.next()) {
            Country country = new Country();
            country.name = resultSet.getString("Name");
            country.population = resultSet.getInt("Population");
            country.code = resultSet.getString("Code");
            country.continent = resultSet.getString("Continent");
            country.region = resultSet.getString("Region");
            country.governmentForm = resultSet.getString("GovernmentForm");
            country.headOfState= resultSet.getString("HeadOfState");
            country.localName = resultSet.getString("LocalName");
            country.code2 = resultSet.getString("Code2");
            country.surfaceArea = resultSet.getFloat("SurfaceArea");
            country.indepYear = resultSet.getInt("IndepYear");
            country.capital = resultSet.getInt("Capital");
            country.lifeExpectancy = resultSet.getFloat("LifeExpectancy");
            country.GNP = resultSet.getFloat("GNP");
            country.GNPOld = resultSet.getFloat("GNPOld");
            countries.add(country);
        }
        return countries;
    }

    // Population methods

    /**
     * Get the population of a city
     * @param cityName name of the city
     * @return city population
     */
    public Integer getCityPopulation(String cityName){
        Integer population = 0;
        ArrayList<City> cityList = getCityList();

        if(cityName == null){
            System.out.println("No city name");
            return null;
        }

        if(cityName.isEmpty()){
            System.out.println("No city name");
            return null;
        }
        for(City city : cityList){
            if((city.name).equals(cityName))
            population = city.population;
        }
        return population;
    }


    /**
     * Get the population of a District
     * @param districtName name of the district
     * @return district population
     */
    public Integer getDistrictPopulation(String districtName){
        Integer population = 0;
        ArrayList<City> cityList = getCityList();
        for(City city : cityList){
            if((city.district).equals(districtName))
                population += city.population;
        }
        return population;
    }


    /**
     * Get the population of a country
     * @param countryCode code of the country
     * @return city population
     */
    public Integer getCountryPopulation(String countryCode){
        Integer population = 0;
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
            if((country.code).equals(countryCode))
                population = country.population;
        }
        return population;
    }

    /**
     * Get the population of a region
     * @param regionName name of the region
     * @return region population
     */
    public Integer getRegionPopulation(String regionName){
        Integer population = 0;
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
            if((country.region).equals(regionName))
                population += country.population;
        }
        return population;
    }

    /**
     * Get the population of a continent
     * @param continentName name of the continent
     * @return continent population
     */
    public Long getContinentPopulation(String continentName){
        Long population = 0L;
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
            if((country.continent).equals(continentName))
                population += country.population;
        }
        return population;
    }

    /**
     * Get the population of the world
     * @return world population
     */
    public Long getWorldPopulation(){
        Long population = 0L;
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
                population += country.population;
        }
        return population;
    }

    /**
     * Get the number of people speaking a language in the world
     * @param language name of the language
     * @return Population
     */
    public Integer getLanguagePopulation(String language){
        float population = (float) 0;
        ArrayList<CountryLanguage> countryLanguageList = getCountryLanguageList();

        for(CountryLanguage cl : countryLanguageList){
            if(cl.language.equals(language)){
                population += (getCountryPopulation(cl.countryCode) * cl.percentage)/100;
            }
        }
        return round(population);
    }

    // Database methods //

    /**
     * Get city data from database
     * @param cityCode Code of the city
     * @return City data
     */
    public City getCity(Integer cityCode){
        City city = new City();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, CountryCode, District, Population FROM city WHERE ID = '" + cityCode + "'";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next()) {
                city.name = resultSet.getString("Name");
                city.countryCode = resultSet.getString("CountryCode");
                city.district = resultSet.getString("District");
                city.population = resultSet.getInt("Population");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return city;
    }

    /**
     * Get the list of all the city in the database
     * @return City list
     */
    public ArrayList<City> getCityList(){
        ArrayList<City> cities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT * FROM city ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                city.district = resultSet.getString("District");
                city.countryCode = resultSet.getString("CountryCode");
                cities.add(city);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return cities;
    }

    /**
     * Get the list of all the country in the database
     * @return Country list
     */
    public ArrayList<Country> getCountryList(){
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT * FROM country ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next()) {
                Country country = new Country();
                country.name = resultSet.getString("Name");
                country.population = resultSet.getInt("Population");
                country.code = resultSet.getString("Code");
                country.continent = resultSet.getString("Continent");
                country.region = resultSet.getString("Region");
                country.governmentForm = resultSet.getString("GovernmentForm");
                country.headOfState= resultSet.getString("HeadOfState");
                country.localName = resultSet.getString("LocalName");
                country.code2 = resultSet.getString("Code2");
                country.surfaceArea = resultSet.getFloat("SurfaceArea");
                country.indepYear = resultSet.getInt("IndepYear");
                country.capital = resultSet.getInt("Capital");
                country.lifeExpectancy = resultSet.getFloat("LifeExpectancy");
                country.GNP = resultSet.getFloat("GNP");
                country.GNPOld = resultSet.getFloat("GNPOld");
                country.cityPopulation = getCountryCityPopulation(resultSet.getString("Code"));
                countries.add(country);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return countries;
    }

    /**
     * Get the list of all the language in all the countries
     * @return Country Language list
     */
    public ArrayList<CountryLanguage> getCountryLanguageList(){
        ArrayList<CountryLanguage> countriesLanguage = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT * FROM countrylanguage";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next()) {
                CountryLanguage countryLanguage = new CountryLanguage();
                countryLanguage.countryCode = resultSet.getString("CountryCode");
                countryLanguage.language = resultSet.getString("Language");
                countryLanguage.percentage =  resultSet.getFloat("Percentage");
                countriesLanguage.add(countryLanguage);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return countriesLanguage;
    }

    /**
     * Get the list of all the region in the world
     * @return Region name list
     */
    public ArrayList<String> getRegionList(){
        ArrayList<String> regionList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Region from country group by Region";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next()) {
                regionList.add(resultSet.getString("Region"));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return regionList;
    }

    /**
     * Get the list of all the continent in the world
     * @return Continent name list
     */
    public ArrayList<String> getContinentList(){
        ArrayList<String> continentList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Continent from country group by Continent";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next()) {
                continentList.add(resultSet.getString("Continent"));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return continentList;
    }

    /**
     * Get the number of people living in cities in a country
     * @param countryCode Code of the country
     * @return Population
     */
    public Integer getCountryCityPopulation(String countryCode){

        int cityPop = 0;
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT SUM(Population) AS CityPopulation FROM city WHERE CountryCode = '"+countryCode+"'";

            ResultSet resultSet = stmt.executeQuery(strSelect);
            if (resultSet.next()) {
                cityPop = resultSet.getInt("CityPopulation");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get city");
        }
        return cityPop;
    }


    public ResultSet databaseRequester (String Query)
    {
        ResultSet resultSet;

        try {
            Statement stmt = con.createStatement();

            resultSet = stmt.executeQuery(Query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country list");
            return null;
        }
        return resultSet;
    }

    public void filePrinter (String filename, StringBuilder sb ){
        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}