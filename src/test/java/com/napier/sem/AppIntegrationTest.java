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

        @Test
        void testGetCity()
        {
            City city = app.getCity(1);
            assertEquals("The city code is not AFG",city.countryCode, "AFG");
        }

}
