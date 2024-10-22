package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

import java.util.PriorityQueue;

public class ZlecenieKupnaWA extends ZlecenieKupna{

    public ZlecenieKupnaWA(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor) {
        super(limit, idAkcji, liczba, tura, id, inwestor);
    }

    public boolean czyUsunac() {
        return true;
    }

    //rekurencyjne sprawdzanie czy zlecenie WA moze byc zrealizowane
    public boolean czyMozeBycZrealizowane(PriorityQueue<ZlecenieKupna> zleceniaKupnaTemp,
                                          PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazyTemp,
                                          Zlecenie zlecenie) {
        PriorityQueue<ZlecenieKupna> zleceniaKupna = new PriorityQueue<>(zleceniaKupnaTemp);
        PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy = new PriorityQueue<>(zleceniaSprzedazyTemp);
        ZlecenieSprzedazy zlecenieS;
        int iloscAkcji = zlecenie.getLiczba();
        while (!zleceniaSprzedazy.isEmpty() && iloscAkcji > 0) {
            zlecenieS = zleceniaSprzedazy.poll();
            if (zlecenieS instanceof ZlecenieSprzedazyWA) {
                if (!zlecenieS.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenieS)) return false;
            }
            if (zlecenieS.getLimit() > zlecenie.getLimit()) return false;
            iloscAkcji -= Math.min(iloscAkcji, zlecenieS.getLiczba());
        }
        return iloscAkcji <= 0;
    }

}
