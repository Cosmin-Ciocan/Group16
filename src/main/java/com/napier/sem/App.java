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

        //a.countryRequest(a.countriesWorldPop, "countriesWorldPop");
        //a.cityRequest(a.cityWorldPop, "cityWorldPop");
        //a.outputContinentPopulation(a.getCountryList(), "continentPopulation.md");
        a.outputLanguagePopulation();

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

    /**
     *
     * @param request Sql Request
     * @param fileName Name of the markdown output file
     * @throws SQLException
     */
    public void countryRequest(String request, String fileName) throws SQLException {
        ResultSet resultSet = databaseRequester(request);
        outputCountry(countryLister(resultSet), fileName + ".md");
    }

    /**
     *
     * @param request Sql Request
     * @param fileName Name of the markdown output file
     * @throws SQLException
     */
    public void cityRequest(String request, String fileName) throws SQLException {
        ResultSet resultSet = databaseRequester(request);
        outputCity(cityLister(resultSet), fileName + ".md");
    }

    /**
     *
     * @param request Sql Request
     * @param fileName Name of the markdown output file
     * @throws SQLException
     */
    public void capitalCityRequest(String request, String fileName) throws SQLException {
        ResultSet resultSet = databaseRequester(request);
        outputCapitalCity(cityLister(resultSet), fileName + ".md");
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

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Code | Name | Continent | Region | Population | Capital |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- |\r\n");
        // Loop over all countries in the list
        for (Country cty : countries) {
            if (cty == null) continue;
            City capital = getCity(cty.capital);
            sb.append("| " + cty.code + " | " +
                    cty.name + " | " + cty.continent + " | " +
                    cty.region + " | " + cty.population + " | "
                    + capital.name + " |\r\n");
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

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Name | Country | District | Population |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");
        // Loop over all cities in the list
        for (City city : cities) {
            if (city == null) continue;
            sb.append("| " + city.name + " | " +
                    city.countryCode + " | " + city.district + " | " +
                    city.population + " |\r\n");
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

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Name | Country | Population |\r\n");
        sb.append("| --- | --- | --- |\r\n");
        // Loop over all cities in the list
        for (City city : cities) {
            if (city == null) continue;
            sb.append("| " + city.name + " | " +
                    city.countryCode + " | " +
                    city.population + " |\r\n");
        }
        filePrinter(filename, sb);
    }

    public void outputCountryPopulation(ArrayList<Country> countries, String filename) {
        if(countries == null)
        {
            System.out.println("No countries");
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
            Integer cityPopPerc = (int) round(cityPopulation / Population * 100);
            if (cityPopPerc > 100) {
                cityPopPerc = 100;
            }
            Integer notCityPopPerc = 100 - cityPopPerc;

            sb.append("| " + cty.name + " | " +
                    cty.population + " | " +
                    cityPopPerc + " % | " + notCityPopPerc + " % |\r\n");
        }
        filePrinter(filename, sb);
    }

    public void outputRegionPopulation(ArrayList<Country> countries, String filename){
        if(countries == null)
        {
            System.out.println("No countries");
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
            Integer cityPopPerc = (int) (cityPopulation / population * 100);
            Integer notCityPopPerc = 100 - cityPopPerc;
            Integer finalPopulation = population.intValue() ;
            sb.append("| " + region + " | " +
                    finalPopulation + " | " +
                    cityPopPerc + " % | " + notCityPopPerc + " % |\r\n");
        }
        filePrinter(filename, sb);
    }

    public void outputContinentPopulation(ArrayList<Country> countries, String filename){
        if(countries == null)
        {
            System.out.println("No countries");
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
            Integer cityPopPerc = (int) round(cityPopulation / population * 100);
            Integer notCityPopPerc = 100 - cityPopPerc;
            Integer finalPopulation = population.intValue() ;
            sb.append("| " + continent + " | " +
                    finalPopulation + " | " +
                    cityPopPerc + " % | " + notCityPopPerc + " % |\r\n");
        }
        filePrinter(filename, sb);
    }

    public void outputLanguagePopulation(){
        ArrayList<String> languages = new ArrayList<>();
        languages.add("Chinese");
        languages.add("Hindi");
        languages.add("Spanish");
        languages.add("English");
        languages.add("Arabic");

        StringBuilder sb = new StringBuilder();
        Long worldPop = getWorldPopulation();

        //print header
        sb.append("| Name | Speaker Population | World Percentage |\r\n");
        sb.append("| --- | --- | --- |\r\n");

        for(String language : languages){
            Integer pop = getLanguagePopulation(language);
            Float worldPerc = (((float) pop / (float) worldPop) * 100);
            sb.append("| " + language + " | " +
                    pop + " | " +
                    worldPerc + " % |\r\n");
        }
        filePrinter("LanguagePop.md",sb);
    }

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
            Float cityPopulation = (float) cty.cityPopulation;
            Float Population = (float) cty.population;
            Integer cityPopPerc = round(cityPopulation / Population*100);
            if (cityPopPerc > 100){
                cityPopPerc = 100;
            }
            Integer notCityPopPerc = 100 - cityPopPerc;
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

    // Population methods

    public Integer getCityPopulation(String cityName){
        Integer population = 0;
        ArrayList<City> cityList = getCityList();
        for(City city : cityList){
            if((city.name).equals(cityName))
            population = city.population;
        }
        return population;
    }

    public Integer getDistrictPopulation(String districtName){
        Integer population = 0;
        ArrayList<City> cityList = getCityList();
        for(City city : cityList){
            if((city.district).equals(districtName))
                population += city.population;
        }
        return population;
    }

    public Integer getCountryPopulation(String countryCode){
        Integer population = 0;
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
            if((country.code).equals(countryCode))
                population = country.population;
        }
        return population;
    }

    public Integer getRegionPopulation(String regionName){
        Integer population = 0;
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
            if((country.region).equals(regionName))
                population += country.population;
        }
        return population;
    }

    public Long getContinentPopulation(String continentName){
        Long population = Long.valueOf(0);
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
            if((country.continent).equals(continentName))
                population += country.population;
        }
        return population;
    }

    public Long getWorldPopulation(){
        Long population = Long.valueOf(0);
        ArrayList<Country> countryList = getCountryList();
        for(Country country : countryList){
                population += country.population;
        }
        return population;
    }

    public Integer getLanguagePopulation(String language){
        Float population = Float.valueOf(0);
        ArrayList<CountryLanguage> countryLanguageList = getCountryLanguageList();

        for(CountryLanguage cl : countryLanguageList){
            if(cl.language.equals(language)){
                population += (getCountryPopulation(cl.countryCode) * cl.percentage)/100;
            }
        }
        Integer roundedPop = round(population);
        return round(population);
    }



    // Basic methods //

    public City getCity(Integer cityCode){
        City city = new City();
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT Name, CountryCode, District, Population FROM `city` WHERE ID = '" + cityCode + "'";

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

    public Integer getCountryCityPopulation(String countryCode){

        Integer cityPop = 0;
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT SUM(Population) AS CityPopulation FROM city  WHERE CountryCode = '"+countryCode+"'";

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
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}