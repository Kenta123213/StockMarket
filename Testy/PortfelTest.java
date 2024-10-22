import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Gielda.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PortfelTest {
    private Portfel portfel;

    @BeforeEach
    void setUp() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("TEST", 30);
        portfel = new Portfel(1000, map);
    }

    @Test
    void posiadaAkcje() {
        assertTrue(portfel.posiadaAkcje("TEST", 20));
        assertFalse(portfel.posiadaAkcje("TEST", 31));
        assertFalse(portfel.posiadaAkcje("GOOGL", 30));
    }

    @Test
    void dodajAkcje() {
        portfel.dodajAkcje("TEST", 30);
        assertEquals(portfel.getAkcjeIlosc().get("TEST"), 60);
        portfel.dodajAkcje("GOOGL", 1);
        assertEquals(portfel.getAkcjeIlosc().get("GOOGL"), 1);
    }

    @Test
    void zabierzAkcje() {
        portfel.zabierzAkcje("TEST", 30);
        assertTrue(portfel.getAkcjeIlosc().isEmpty());
        portfel.dodajAkcje("GOOGL", 30);
        portfel.zabierzAkcje("GOOGL", 10);
        assertEquals(portfel.getAkcjeIlosc().get("GOOGL"), 20);
    }
}