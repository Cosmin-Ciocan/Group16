package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

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

        //request 1
        a.printCountries(a.countriesWorldPop());

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


    // Display methods

    /**
     * Display a country name
     */
    public void displayCountry(Country country)
    {
        if (country != null)
        {
            System.out.println(country.name + "\n");
        }
    }


    /**
     * Display a list of countries
     */
    public void printCountries(ArrayList<Country> countries)
    {
        //print header
        System.out.println(String.format("%-10s", "Name"));
        // Loop over all countries in the list
        for (Country cty  : countries)
        {
            String cty_string = String.format("%-10s",cty.name);
            System.out.println(cty_string);
        }
    }


    // WORLD //

    /**
     * All the countries in the world organised by largest population to smallest.
     */
    public ArrayList<Country> countriesWorldPop() {
        try
        {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name FROM `country` ORDER BY Population DESC";

            ResultSet resultSet = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<>();
            while (resultSet.next())
            {
                Country cty = new Country();
                cty.name = resultSet.getString("Name");
                countries.add(cty);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries list");
            return null;
        }
    }

}