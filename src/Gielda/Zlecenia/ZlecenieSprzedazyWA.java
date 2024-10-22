package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

import java.util.PriorityQueue;

public class ZlecenieSprzedazyWA extends ZlecenieSprzedazy{

    public ZlecenieSprzedazyWA(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor) {
        super(limit, idAkcji, liczba, tura, id, inwestor);
    }

    public boolean czyUsunac() {
        return true;
    }

    public boolean czyMozeBycZrealizowane(PriorityQueue<ZlecenieKupna> zleceniaKupnaTemp,
                                          PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazyTemp,
                                          Zlecenie zlecenie) {
        PriorityQueue<ZlecenieKupna> zleceniaKupna = new PriorityQueue<>(zleceniaKupnaTemp);
        PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy = new PriorityQueue<>(zleceniaSprzedazyTemp);
        ZlecenieKupna zlecenieK;
        int iloscAkcji = zlecenie.getLiczba();
        while (!zleceniaKupna.isEmpty() && iloscAkcji > 0) {
            zlecenieK = zleceniaKupna.poll();
            if (zlecenieK instanceof ZlecenieKupnaWA) {
                if (!zlecenieK.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenieK)) return false;
            }
            if (zlecenieK.getLimit() < zlecenie.getLimit()) return false;
            iloscAkcji -= Math.min(iloscAkcji, zlecenieK.getLiczba());
        }
        return iloscAkcji <= 0;
    }
}
