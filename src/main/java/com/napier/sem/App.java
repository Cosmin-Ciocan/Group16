package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * main app
 */
public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        a.cityContinentPop();

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connect to the MySQL database.
     */
    private Connection con = null;
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(8000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    // WORLD //

    /**
     * All the countries in the world organised by largest population to smallest.
     */
    public void countriesWorldPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, Population FROM `country` ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next())
            {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
            // Loop over all countries in the list
            for (Country cty  : countries)
            {
                String cty_string = String.format("|%-10s|%-10s|",cty.name, cty.population);
                System.out.println(cty_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * The top N populated countries in the world where N is provided by the user
     */
    public void topCountriesWorldPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population FROM country ORDER BY Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next())
            {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
            // Loop over all countries in the list
            for (Country cty  : countries)
            {
                String cty_string = String.format("|%-10s|%-10s|",cty.name, cty.population);
                System.out.println(cty_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }

    /**
     * All the cities in the world organised by largest population to smallest
     */
    public void cityWorldPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Population FROM city ORDER BY Population DESC ";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next())
            {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
            // Loop over all countries in the list
            for (City city : cities)
            {
                String city_string = String.format("|%-10s|%-10s|",city.name, city.population);
                System.out.println(city_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
        }
    }



    //CONTINENT//
    /**
     * All the countries in a continent organised by largest population to smallest
     */
    public void countriesContinentPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, Population FROM country WHERE Continent = 'North America' ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next())
            {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
            // Loop over all countries in the list
            for (Country cty  : countries)
            {
                String cty_string = String.format("|%-10s|%-10s|",cty.name, cty.population);
                System.out.println(cty_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    /**
     * The top N populated countries in a continent where N is provided by the user
     */
    public void topCountriesContinentPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Continent,Population FROM country WHERE Continent = 'North America' ORDER BY Population DESC LIMIT 10";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next())
            {
                Country cty = new Country();
                cty.continent = resultSet.getString("Continent");
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|%-10s|", "Continent", "Name", "Population"));
            // Loop over all countries in the list
            for (Country cty  : countries)
            {
                String cty_string = String.format("|%-10s|%-10s|%-10s|",cty.continent, cty.name, cty.population);
                System.out.println(cty_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }


    /**
     * All the cities in a continent organised by largest population to smallest.
     */
    public void cityContinentPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE Continent = 'North America' ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next())
            {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
            // Loop over all countries in the list
            for (City city : cities)
            {
                String city_string = String.format("|%-10s|%-10s|", city.name, city.population);
                System.out.println(city_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

    //REGION//
    /**
     * All the countries in a region organised by largest population to smallest
     */
    public void countriesRegionPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name,Region,Population FROM country WHERE Region = 'Southern Europe' ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next())
            {
                Country cty = new Country();
                cty.region = resultSet.getString("Region");
                cty.name = resultSet.getString("Name");
                cty.population = resultSet.getInt("Population");
                countries.add(cty);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|%-10s|", "Region", "Name", "Population"));
            // Loop over all countries in the list
            for (Country cty  : countries)
            {
                String cty_string = String.format("|%-10s|%-10s|%-10s|",cty.region, cty.name, cty.population);
                System.out.println(cty_string);
            }
        }
        catch (Exception e)
        {
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
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name, city.Population FROM country INNER JOIN city ON (country.Code = city.CountryCode) WHERE country.Region = 'Caribbean' ORDER BY city.Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<>();
            while (resultSet.next())
            {
                City city = new City();
                city.name = resultSet.getString("Name");
                city.population = resultSet.getInt("Population");
                cities.add(city);
            }

            //print header
            System.out.println(String.format("|%-10s|%-10s|", "Name", "Population"));
            // Loop over all countries in the list
            for (City city :  cities)
            {
                String cty_string = String.format("|%-10s|%-10s|", city.name, city.population);
                System.out.println(cty_string);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");

        }
    }

}