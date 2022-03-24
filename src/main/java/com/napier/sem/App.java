package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * main app
 */
public class App {
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 0);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        a.countriesWorldPop();

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

    // PRINTERS //

    /**
     *
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

    public void countryPrinter(ArrayList<Country> countries){

        //print header
        System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
        // Loop over all countries in the list
        for (Country cty : countries) {
            String cty_string = String.format("|%-10s|%-10s|", cty.name, cty.population);
            System.out.println(cty_string);
        }
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


    // WORLD //

    /**
     * All the countries in the world organised by largest population to smallest.
     */
    public void countriesWorldPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, Population FROM `country` ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }
            countryPrinter(countries);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * The top N populated countries in the world where N is provided by the user
     */
    public void topCountriesWorldPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population FROM country ORDER BY Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }
            countryPrinter(countries);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * All the cities in the world organised by largest population to smallest
     */
    public void cityWorldPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population FROM city ORDER BY Population DESC ";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * The top N populated cities in the world where N is provided by the user.
     */
    public void topCitiesWorldPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population FROM city ORDER BY Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * All the capital cities in the world organised by largest population to smallest
     */
    public void capitalCitiesWorldPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID)  ORDER BY city.Population DESC ";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * The top N populated capital cities in the world where N is provided by the users.
     */
    public void topCapitalCitiesWorldPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID)  ORDER BY city.Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    //CONTINENT//

    /**
     * All the countries in a continent organised by largest population to smallest
     */
    public void countriesContinentPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, Population FROM country WHERE Continent = 'North America' ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }
            countryPrinter(countries);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated countries in a continent where N is provided by the user
     */
    public void topCountriesContinentPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Continent,Population FROM country WHERE Continent = 'North America' ORDER BY Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                Country cty = new Country();
                cty.continent = resultSet.getString("Continent");
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }
            countryPrinter(countries);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }


    /**
     * All the cities in a continent organised by largest population to smallest.
     */
    public void cityContinentPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE Continent = 'North America' ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated cities in a continent where N is provided by the user.
     */
    public void tpoCitiesContinentPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = " SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE Continent = 'North America' ORDER BY city.Population DESC LIMIT 3";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * All the capital cities in a continent organised by largest population to smallest.
     */
    public void capitalCitiesContinentPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Continent = 'North America')  ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated capital cities in a continent where N is provided by the user.
     */
    public void topCapitalCitiesContinentPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Continent = 'North America')  ORDER BY city.Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    //REGION//

    /**
     * All the countries in a region organised by largest population to smallest
     */
    public void countriesRegionPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population FROM country WHERE Region = 'Southern Europe' ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }
            countryPrinter(countries);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }


    /**
     * The top N populated countries in a region where N is provided by the user
     */
    public void topCountriesRegionPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Region,Population FROM country WHERE Region = 'Southern Europe' ORDER BY Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                Country cty = new Country();
                cty.region = resultSet.getString("Region");
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|%-10s|", "Region", "Name", "Population"));
            // Loop over all countries in the list
            for (Country cty : countries) {
                String cty_string = String.format("|%-10s|%-10s|%-10s|", cty.region, cty.name, cty.population);
                System.out.println(cty_string);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * All the cities in a region organised by largest population to smallest
     */
    public void cityRegionPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE country.Region = 'Caribbean' ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated cities in a region where N is provided by the user.
     */
    public void topCitiesRegionPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE country.Region = 'Caribbean' ORDER BY city.Population DESC LIMIT 3";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * All the capital cities in a region organised by largest to smallest.
     */
    public void capitalCitiesRegionPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Region = 'Caribbean') ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated capital cities in a region where N is provided by the user.
     */
    public void topCapitalCitiesRegionPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE (country.Capital = city.ID) AND (country.Region = 'Caribbean') ORDER BY city.Population DESC LIMIT 3";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    //COUNTRY

    /**
     * All the cities in a country organised by largest population to smallest
     */
    public void cityCountryPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE country.Name = 'France' ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated cities in a country where N is provided by the user.
     */
    public void topCitiesCountryPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE country.Name = 'France' ORDER BY city.Population DESC LIMIT 3";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    //DISTRICT

    /**
     * All the cities in a district organised by largest population to smallest
     */
    public void cityDistrictPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT District,Name,Population FROM city WHERE District = 'Adana'";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.district = resultSet.getString("District");
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated cities in a district where N is provided by the user.
     */
    public void topCitiesDistrictPop() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population,District FROM city WHERE District = 'Adana' LIMIT 3";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.district = resultSet.getString("District");
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }
            cityPrinter(cities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }
    /**
     * The spoken language in a country where countryCode is provided by the user.
     */
    public void getCountryLanguage(String countryCode) {
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

}