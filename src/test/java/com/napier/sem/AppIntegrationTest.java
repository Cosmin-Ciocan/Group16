package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

    class AppIntegrationTest
    {
        static App app;

        @BeforeAll
        static void init()
        {
            app = new App();
            app.connect("localhost:33060", 3000);

        }

        /**
         * Testing getCity()
         */
        @Test
        void getCityTest() {
            City city = app.getCity(1);
            assertEquals("AFG",city.countryCode, "The city country code is wrong");
        }

        /**
         * Testing getCityPopulation()
         */
        @Test
        void getCityPopulationTest(){
            int population = app.getCityPopulation("Paris");
            assertEquals( 2125246, population, "The city population is false" );
        }

        /**
         * Testing getCityPopulation()
         */
        @Test
        void getDistrictPopulationTest(){
            int population = app.getDistrictPopulation("Herat");
            assertEquals( 186800, population, "The district population is false" );
        }

        /**
         * Testing getCountryPopulation()
         */
        @Test
        void getCountryPopulationTest(){
            ArrayList<Country> countryList = app.getCountryList();
            int population = app.getCountryPopulation("BEL", countryList);
            assertEquals( 10239000, population, "The country population is false" );
        }

        /**
         * Testing getRegionPopulation()
         */
        @Test
        void getRegionPopulationTest(){
            int population = app.getRegionPopulation("Melanesia");
            assertEquals( 6472000, population, "The region population is false" );
        }

        /**
         * Testing getContinentPopulation()
         */
        @Test
        void getContinentPopulationTest(){
            long population = app.getContinentPopulation("Asia");
            assertEquals( 3705025700L, population, "The continent population is false" );
        }

        /**
         * Testing getWorldPopulation()
         */
        @Test
        void getWorldPopulationTest(){
            long population = app.getWorldPopulation();
            assertEquals( 6078749450L, population, "The world population is false" );
        }

        /**
         * Testing getLanguagePopulation()
         */
        @Test
        void getLanguagePopulationTest(){
            int population = app.getLanguagePopulation("English");
            assertEquals( 347077824, population, "The language population is false" );
        }

        /**
         * Testing getCityList()
         */
        @Test
        void getCityListTest(){
            ArrayList<City> cities = app.getCityList();
            City first = cities.get(0);
            assertEquals(Optional.of(10500000), Optional.ofNullable(first.population), "The first city list element is false");
        }

        /**
         * Testing getRegionList()
         */
        @Test
        void getRegionListTest(){
            ArrayList<String> regionList = app.getRegionList();
            String first = regionList.get(0);
            assertEquals("Caribbean", first, "The first city list element is false");
        }

}

