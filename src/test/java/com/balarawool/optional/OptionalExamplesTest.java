package com.balarawool.optional;

import com.balarawool.optional.OptionalExamples.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.balarawool.optional.OptionalExamples.Country.IN;
import static com.balarawool.optional.OptionalExamples.Country.NL;
import static com.balarawool.optional.OptionalExamples.searchFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionalExamplesTest {

    @Test
    public void testOptionalParamVsMethodOverloading() {
        // Example - method overloading is better than Optional parameter
        List<Player> playersList1 = searchFor(NL, Optional.empty());
        assertEquals(2, playersList1.size());
        assertEquals(NL, playersList1.get(0).getCountry());
        assertEquals(NL, playersList1.get(1).getCountry());

        List<Player> playersList2 = searchFor(IN, Optional.of(1000));
        assertEquals(1, playersList2.size());
        assertEquals(IN, playersList2.get(0).getCountry());
        assertTrue(playersList2.get(0).getRating() >= 1000);

        List<Player> playersList3 = searchFor(NL);
        assertEquals(2, playersList3.size());
        assertEquals(NL, playersList3.get(0).getCountry());
        assertEquals(NL, playersList3.get(1).getCountry());

        List<Player> playersList4 = searchFor(IN, 1000);
        assertEquals(1, playersList4.size());
        assertEquals(IN, playersList4.get(0).getCountry());
        assertTrue(playersList4.get(0).getRating() >= 1000);
    }
}
