package locations;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LocationTest {
    LocationParser locationParser;

    @BeforeEach
    void setUp() {
        locationParser = new LocationParser();
    }

    @Test
    void testParse() {
        Location location = locationParser.Parse("Budapest,47.497912,19.040235");

        assertEquals("Budapest",location.getName());
        assertEquals(47.497912,location.getLat());
        assertEquals(19.040235,location.getLon());
    }

}