package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    LocationParser locationParser;

    @BeforeEach
    void setUp() {
        locationParser = new LocationParser();
    }

    @Test
    void testParse() {
        Location location = locationParser.Parse("Budapest, 47.497912, 19.040235");

        assertEquals("Budapest", location.getName());
        assertEquals(47.497912, location.getLat());
        assertEquals(19.040235, location.getLon());
    }

    @Test
    void testIsOnEquator() {
        assertTrue(locationParser.Parse("City,0.0,1.0").isOnEquator());
        assertFalse(locationParser.Parse("City,1.0,0.0").isOnEquator());
    }

    @Test
    void testIsOnPrimeMeridian() {
        assertTrue(locationParser.Parse("City,1.0,0.0").isOnPrimeMeridian());
        assertFalse(locationParser.Parse("City,0.0,1.0").isOnPrimeMeridian());
    }

    @Test
    void testDifferentObject() {
        Location location1 = locationParser.Parse("Budapest,47.497912,19.040235");
        Location location2 = locationParser.Parse("Budapest,47.497912,19.040235");
        assertNotSame(location1, location2);

        assertEquals(location1.getName(), location2.getName());
        assertEquals(location1.getLon(), location2.getLon());
        assertEquals(location1.getLat(), location2.getLat());
    }


    @Test
    void testDistanceFrom() {
        Location budapest = locationParser.Parse("Budapest,47.497912,19.040235");
        Location kiskunhalas = locationParser.Parse("Kiskunhalas,46.430840, 19.463683");
        assertEquals(122920, budapest.distanceFrom(kiskunhalas), 10);
    }

    @Test
    void testLatitudeException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> locationParser.Parse("Any,100,0"));
        assertEquals("Illegal latitude", ex.getMessage());


    }

    @Test
    void testLongitudeException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> locationParser.Parse("Any,0,200"));
        assertEquals("Illegal longitude", ex.getMessage());

    }
}