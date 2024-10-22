package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

import java.util.PriorityQueue;

public class ZlecenieSprzedazyWT extends ZlecenieSprzedazy{
    private int waznoscTura;

    public ZlecenieSprzedazyWT(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor, int waznoscTura) {
        super(limit, idAkcji, liczba, tura, id, inwestor);
        this.waznoscTura = waznoscTura;
    }

    public boolean czyUsunac() {
        return inwestor.getSymulacja().getTura() >= waznoscTura;
    }

    public boolean czyMozeBycZrealizowane(PriorityQueue<ZlecenieKupna> zleceniaKupnaTemp,
                                                   PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazyTemp,
                                                   Zlecenie zlecenie) {
        return true;
    }
}
