import Gielda.*;
import Gielda.Inwestorzy.InwestorRand;
import Gielda.Zlecenia.*;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class ZlecenieKupnaWATest {
    private ZlecenieKupnaWA zlecenie;
    private Symulacja symulacja;

    @Test
    void testCzyMozeBycZrealizowaneTrue() {
        PriorityQueue<ZlecenieKupna> zleceniaKupna = new PriorityQueue<>(new ZlecenieKupnaComparator());
        PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy = new PriorityQueue<>(new ZlecenieSprzedazyComparator());
        zlecenie = new ZlecenieKupnaWA(125, "TEST", 100, 1, 1, new InwestorRand(symulacja, 1));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 71, 1, 2, new InwestorRand(symulacja, 2)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 20, 1, 3, new InwestorRand(symulacja, 3)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 30, 1, 4, new InwestorRand(symulacja, 4)));
        assertTrue(zlecenie.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenie));
    }

    @Test
    void testCzyMozeBycZrealizowaneFalse() {
        PriorityQueue<ZlecenieKupna> zleceniaKupna = new PriorityQueue<>(new ZlecenieKupnaComparator());
        PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy = new PriorityQueue<>(new ZlecenieSprzedazyComparator());
        zlecenie = new ZlecenieKupnaWA(125, "TEST", 100, 1, 1, new InwestorRand(symulacja, 1));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 60, 1, 2, new InwestorRand(symulacja, 2)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 20, 1, 3, new InwestorRand(symulacja, 3)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 10, 1, 4, new InwestorRand(symulacja, 4)));
        assertFalse(zlecenie.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenie));
    }

    @Test
    void testCzyMozeBycZrealizowaneZInnymWATrue() {
        PriorityQueue<ZlecenieKupna> zleceniaKupna = new PriorityQueue<>(new ZlecenieKupnaComparator());
        PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy = new PriorityQueue<>(new ZlecenieSprzedazyComparator());
        zlecenie = new ZlecenieKupnaWA(125, "TEST", 100, 1, 1, new InwestorRand(symulacja, 1));
        zleceniaKupna.add(new ZlecenieKupnaBT(124, "TEST", 21, 1, 21, new InwestorRand(symulacja, 21)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 60, 1, 2, new InwestorRand(symulacja, 2)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyWA(124, "TEST", 20, 1, 3, new InwestorRand(symulacja, 3)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 20, 1, 4, new InwestorRand(symulacja, 4)));
        assertTrue(zlecenie.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenie));
    }

    @Test
    void testCzyMozeBycZrealizowaneZInnymWAFalse() {
        PriorityQueue<ZlecenieKupna> zleceniaKupna = new PriorityQueue<>(new ZlecenieKupnaComparator());
        PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy = new PriorityQueue<>(new ZlecenieSprzedazyComparator());
        zlecenie = new ZlecenieKupnaWA(125, "TEST", 100, 1, 1, new InwestorRand(symulacja, 1));
        zleceniaKupna.add(new ZlecenieKupnaBT(124, "TEST", 19, 1, 21, new InwestorRand(symulacja, 21)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 60, 1, 2, new InwestorRand(symulacja, 2)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyWA(124, "TEST", 20, 1, 3, new InwestorRand(symulacja, 3)));
        zleceniaSprzedazy.add(new ZlecenieSprzedazyBT(124, "TEST", 20, 1, 4, new InwestorRand(symulacja, 4)));
        assertFalse(zlecenie.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenie));
    }
}