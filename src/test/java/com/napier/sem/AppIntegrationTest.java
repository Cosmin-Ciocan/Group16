package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

    public class AppIntegrationTest
    {
        static App app;

        @BeforeAll
        static void init()
        {
            app = new App();
            app.connect("localhost:33060", 0);

        }

        @Test
        void testGetCity()
        {
            City city = app.getCity("Kabul");
            assertEquals(city.countryCode, "AFG");
            assertEquals(city.district, "Kabol");
        }
}
