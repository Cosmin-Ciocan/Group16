package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    //Get the total population of each continent
    String totPopContinent = "SELECT Continent, SUM(Population) AS TotalPopulation FROM country GROUP BY Continent";

    //Get country population details
    String countryPop = "SELECT country.Name, country.Continent, country.Code, country.Region, country.Population, SUM(city.Population) AS CityPopulation FROM city  INNER JOIN country ON (country.Code = city.CountryCode) GROUP BY city.CountryCode";


    public static void main(String[] args) throws SQLException {
        // Create new Application
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 0);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        a.cityRequest(a.cityContinentPop);

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
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
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

    public void countryRequest(String request) throws SQLException {
        ResultSet resultSet = databaseRequester(request);
        countryPrinter(countryLister(resultSet));
    }

    public void cityRequest(String request) throws SQLException {
        ResultSet resultSet = databaseRequester(request);
        cityPrinter(cityLister(resultSet));
    }
    

    // PRINTERS //
    /**
     * @param cities City list
     */
    public void cityPrinter(ArrayList<City> cities){

        // Check cities is not null
        if (cities == null)
        {
            System.out.println("No cities");
            return;
        }

        //print header
        System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
        // Loop over all countries in the list
        for (City city : cities) {
            if (city == null)
                continue;

            String city_string = String.format("|%-10s|%-10s|", city.name, city.population);
            System.out.println(city_string);
        }
    }

    /**
     * @param countries country list
     */
    public void countryPrinter(ArrayList<Country> countries){

        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }
        //print header
        System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
        // Loop over all countries in the list
        for (Country cty : countries) {
            if(cty == null)
            {
                continue;
            }
            String cty_string = String.format("|%-10s|%-10s|", cty.name, cty.population);
            System.out.println(cty_string);
        }
    }

    public void countryPopulationPrinter(ArrayList<Country> countries) {

        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }
        //print header
        System.out.println(String.format("|%-10s|%-10s|%-10s|%-10s|", "Name", "Total Population","Population living in cities","Population not living in cities"));
        // Loop over all countries in the list
        for (Country cty : countries) {

            if(cty == null)
            {
                continue;
            }
            Float cityPopPerc = (float) ((cty.cityPopulation * 100) / cty.population);
            Float notCityPopPerc = 100 - cityPopPerc;
            String cty_string = String.format("|%-10s|%-10s|%-10s|%-10s|", cty.name, cty.population, cityPopPerc, notCityPopPerc);
            System.out.println(cty_string);

        }
    }

    // Lister //
    /**
     *
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
     *
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


    /**
     *
     * @param resultSet Result of the SQL request
     * @return List of country with details on population
     */
    public ArrayList<Country> PopulationLister(ResultSet resultSet) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        while (resultSet.next()) {
            Country country = new Country();
            country.name = resultSet.getString("Name");
            country.continent = resultSet.getString("Continent");
            country.code = resultSet.getString("Code");
            country.population = resultSet.getInt("Population");
            country.cityPopulation = resultSet.getInt("CityPopulation");
            country.region = resultSet.getString("Region");
            countries.add(country);
        }
        return countries;
    }



    // Basic methods //
    public City getCity(String cityName){
        City city = new City();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, CountryCode, District, Population FROM `city` WHERE Name = '" + cityName + "'";

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


    /**
     * The spoken language in a country where countryCode is provided by the user.
     */
    public void getCountryLanguage(String countryCode)
    {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Language,IsOfficial,Percentage FROM countrylanguage WHERE CountryCode = countryCode LIMIT 3";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            CountryLanguage countryLanguage = new CountryLanguage();

            while (resultSet.next()) {
                countryLanguage.language = resultSet.getString("Language");
                countryLanguage.isOfficial = resultSet.getBoolean("IsOfficial");
                countryLanguage.percentage = resultSet.getFloat("Percentage");
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|%-10s|", "Language", "IsOfficial", "Percentage"));
            // print country language details
            String cty_string = String.format("|%-10s|%-10s|%-10s|", countryLanguage.language, countryLanguage.isOfficial, countryLanguage.percentage);
            System.out.println(cty_string);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countryLanguage");
        }
    }

    /**
     * The population of people, people living in cities, and people not living in cities in each continent.
     */
    public void popInCityAndNot() {

        Map<String, String> map = new HashMap<String, String>();

        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Continent, SUM(Population) AS Population FROM country GROUP BY Continent";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            //Country country = new Country();

            while (resultSet.next()) {
                map.put(resultSet.getString("Continent"), resultSet.getString("Population"));
            }

            System.out.println(map);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get popInCityAndNot");
        }
    }

}