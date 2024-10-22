package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

import java.util.PriorityQueue;

public class ZlecenieKupnaBT extends ZlecenieKupna{

    public ZlecenieKupnaBT(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor) {
        super(limit, idAkcji, liczba, tura, id, inwestor);
    }

    public boolean czyUsunac() {
        return false;
    }

    public boolean czyMozeBycZrealizowane(PriorityQueue<ZlecenieKupna> zleceniaKupnaTemp,
                                                   PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazyTemp,
                                                   Zlecenie zlecenie) {
        return true;
    }

}
