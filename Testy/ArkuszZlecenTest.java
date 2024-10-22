import Gielda.*;
import Gielda.Inwestorzy.InwestorRand;
import Gielda.Inwestorzy.InwestorSma;
import Gielda.Zlecenia.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ArkuszZlecenTest {
    private ArkuszZlecen arkuszZlecen;
    private Symulacja symulacja;

    @BeforeEach
    void setUp() {
        symulacja = new Symulacja("input.txt", 0);
        arkuszZlecen = new ArkuszZlecen("TEST", 100, symulacja);
    }

    @Test
    void testWyznaczSmaN() {
        arkuszZlecen.dodajCene(110);
        arkuszZlecen.dodajCene(120);
        arkuszZlecen.dodajCene(130);
        assertEquals(125, arkuszZlecen.wyznaczSmaN(2));
        assertEquals(120, arkuszZlecen.wyznaczSmaN(3));
    }

    @Test
    void testDodajZlecenie() {
        ZlecenieKupna zlecenieKupna = new ZlecenieKupnaN(124, "TEST", 15, 2, 3, new InwestorRand(symulacja, 3));
        ZlecenieSprzedazy zlecenieSprzedazy = new ZlecenieSprzedazyN(126, "TEST", 12, 2, 5, new InwestorSma(symulacja, 2));
        arkuszZlecen.dodajZlecenie(zlecenieKupna);
        arkuszZlecen.dodajZlecenie(zlecenieSprzedazy);
        assertEquals(zlecenieKupna, arkuszZlecen.getZleceniaKupna().peek());
        assertEquals(zlecenieSprzedazy, arkuszZlecen.getZleceniaSprzedazy().peek());
    }

    @Test
    void testTura1() {
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        akcjeIlosc.put("TEST", 30);
        InwestorRand inw1 = new InwestorRand(symulacja, 3);
        inw1.utworzPortfel(10000, akcjeIlosc);
        InwestorRand inw2 = new InwestorRand(symulacja, 4);
        inw2.utworzPortfel(10000, akcjeIlosc);

        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaBT(126, "TEST", 15, 2, 3, inw1));
        arkuszZlecen.dodajZlecenie(new ZlecenieSprzedazyBT(124, "TEST", 7, 2, 7, inw2));

        arkuszZlecen.Tura();

        assertEquals(0, arkuszZlecen.getZleceniaSprzedazy().size());
        assertEquals(1, arkuszZlecen.getZleceniaKupna().size());
    }

    @Test
    void testTura2() {
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        akcjeIlosc.put("TEST", 30);
        InwestorRand inw1 = new InwestorRand(symulacja, 3);
        inw1.utworzPortfel(10000, akcjeIlosc);
        InwestorRand inw2 = new InwestorRand(symulacja, 4);
        inw2.utworzPortfel(10000, akcjeIlosc);

        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaBT(126, "TEST", 7, 2, 3, inw1));
        arkuszZlecen.dodajZlecenie(new ZlecenieSprzedazyBT(124, "TEST", 15, 2, 7, inw2));

        arkuszZlecen.Tura();

        assertEquals(1, arkuszZlecen.getZleceniaSprzedazy().size());
        assertEquals(0, arkuszZlecen.getZleceniaKupna().size());
    }

    @Test
    void testTura3() {
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        akcjeIlosc.put("TEST", 30);
        InwestorRand inw1 = new InwestorRand(symulacja, 3);
        inw1.utworzPortfel(10000, akcjeIlosc);
        InwestorRand inw2 = new InwestorRand(symulacja, 4);
        inw2.utworzPortfel(10000, akcjeIlosc);

        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaBT(124, "TEST", 7, 2, 3, inw1));
        arkuszZlecen.dodajZlecenie(new ZlecenieSprzedazyBT(126, "TEST", 7, 2, 7, inw2));

        arkuszZlecen.Tura();

        assertEquals(1, arkuszZlecen.getZleceniaSprzedazy().size());
        assertEquals(1, arkuszZlecen.getZleceniaKupna().size());
    }

    @Test
    void testTura4() {
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        akcjeIlosc.put("TEST", 30);
        InwestorRand inw1 = new InwestorRand(symulacja, 3);
        inw1.utworzPortfel(10000, akcjeIlosc);
        InwestorRand inw2 = new InwestorRand(symulacja, 4);
        inw2.utworzPortfel(10000, akcjeIlosc);

        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaBT(127, "TEST", 7, 2, 3, inw1));
        arkuszZlecen.dodajZlecenie(new ZlecenieSprzedazyBT(126, "TEST", 7, 2, 7, inw2));

        arkuszZlecen.Tura();

        assertEquals(0, arkuszZlecen.getZleceniaSprzedazy().size());
        assertEquals(0, arkuszZlecen.getZleceniaKupna().size());
    }

    @Test
    void koniecTury() {
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        akcjeIlosc.put("TEST", 30);
        InwestorRand inw1 = new InwestorRand(symulacja, 3);
        inw1.utworzPortfel(10000, akcjeIlosc);
        InwestorRand inw2 = new InwestorRand(symulacja, 4);
        inw2.utworzPortfel(10000, akcjeIlosc);
        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaN(124, "TEST", 15, 2, 3, inw1));
        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaN(124, "TEST", 15, 2, 4, inw2));
        arkuszZlecen.turaKoniec();

        assertEquals(0, arkuszZlecen.getZleceniaKupna().size());

        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaBT(124, "TEST", 15, 2, 5, inw1));
        arkuszZlecen.dodajZlecenie(new ZlecenieKupnaBT(124, "TEST", 15, 2, 6, inw2));
        arkuszZlecen.turaKoniec();

        assertEquals(2, arkuszZlecen.getZleceniaKupna().size());
    }

    @Test
    void testWykonajZlecenie() {
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        akcjeIlosc.put("TEST", 30);
        InwestorRand inw1 = new InwestorRand(symulacja, 3);
        inw1.utworzPortfel(10000, akcjeIlosc);
        InwestorRand inw2 = new InwestorRand(symulacja, 4);
        inw2.utworzPortfel(10000, akcjeIlosc);

        ZlecenieKupnaBT zlecenie1 = new ZlecenieKupnaBT(126, "TEST", 15, 2, 3, inw1);
        ZlecenieSprzedazyBT zlecenie2 = new ZlecenieSprzedazyBT(127, "TEST", 7, 2, 6, inw2);

        arkuszZlecen.dodajZlecenie(zlecenie1);
        arkuszZlecen.dodajZlecenie(zlecenie2);

        arkuszZlecen.wykonajZlecenie(zlecenie1, zlecenie2);

        assertEquals(8, zlecenie1.getLiczba());
        assertEquals(10000 - 7*126, inw1.getZasoby());
        assertEquals(10000 + 7*126, inw2.getZasoby());
        assertTrue(inw1.posiadaAkcje("TEST", 37));
        assertTrue(inw2.posiadaAkcje("TEST", 23));
    }

    @Test
    void testUstalCene1() {
        ZlecenieKupna zlecenie1 = new ZlecenieKupnaBT(124, "TEST", 15, 2, 3, new InwestorRand(symulacja, 3));
        ZlecenieSprzedazy zlecenie2 = new ZlecenieSprzedazyBT(127, "TEST", 15, 2, 4, new InwestorRand(symulacja, 5));

        assertEquals(124, arkuszZlecen.ustalCene(zlecenie1, zlecenie2));
    }

    @Test
    void testUstalCene2() {
        ZlecenieKupna zlecenie1 = new ZlecenieKupnaBT(124, "TEST", 15, 2, 4, new InwestorRand(symulacja, 3));
        ZlecenieSprzedazy zlecenie2 = new ZlecenieSprzedazyBT(127, "TEST", 15, 2, 3, new InwestorRand(symulacja, 5));

        assertEquals(127, arkuszZlecen.ustalCene(zlecenie1, zlecenie2));
    }

    @Test
    void testMoznaWykonac() {
        ZlecenieKupna zlecenieKupna = new ZlecenieKupnaN(124, "TEST", 15, 2, 3, new InwestorRand(symulacja, 3)) {
            public int getLimit() { return 200; }
        };
        ZlecenieSprzedazy zlecenieSprzedazy = new ZlecenieSprzedazyN(124, "TEST", 15, 2, 3, new InwestorRand(symulacja, 4)) {
            public int getLimit() { return 150; }
        };
        assertTrue(arkuszZlecen.moznaWykonac(zlecenieKupna, zlecenieSprzedazy));
    }
}
