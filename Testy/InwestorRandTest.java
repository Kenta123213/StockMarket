import Gielda.Inwestorzy.InwestorRand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Gielda.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class InwestorRandTest {
    private InwestorRand inwestor;
    private Symulacja symulacja;

    @BeforeEach
    void setUp() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("TEST", 30);
        inwestor = new InwestorRand(symulacja, 1);
        inwestor.utworzPortfel(1000, map);
    }

    @Test
    void testCzyMozeZakupic() {
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        map1.put("TEST", 1001);
        map2.put("TEST", 999);

        assertFalse(inwestor.czyMozeZakupic(map1));
        assertTrue(inwestor.czyMozeZakupic(map2));
    }

    @Test
    void czyMozeSprzedac() {
        assertTrue(inwestor.czyMozeSprzedac());
        inwestor.zabierzAkcje("TEST", 30);
        assertFalse(inwestor.czyMozeSprzedac());
    }
}