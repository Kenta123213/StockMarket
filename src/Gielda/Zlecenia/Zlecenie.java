package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

import java.util.PriorityQueue;

public abstract class Zlecenie {
    protected int limit;
    protected String idAkcji;
    protected int liczba;
    protected int tura;
    protected int id;
    protected Inwestor inwestor;

    public Zlecenie(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor) {
        this.limit = limit;
        this.idAkcji = idAkcji;
        this.liczba = liczba;
        this.id = id;
        this.inwestor = inwestor;
        this.tura = tura;
    }

    public int getId() {
        return id;
    }

    public abstract boolean czyMozeBycZrealizowane(PriorityQueue<ZlecenieKupna> zleceniaKupnaTemp,
                                          PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazyTemp,
                                          Zlecenie zlecenie);

    abstract public boolean czyUsunac ();

    public Inwestor getInwestor() {
        return inwestor;
    }

    public int getLiczba() {
        return liczba;
    }

    public void zmniejszLiczba(int ile) {
        liczba -= ile;
    }

    public String getIdAkcji() {
        return idAkcji;
    }

    public int getLimit() {
        return limit;
    }

    public void wypisz() {
        System.out.println(idAkcji + " limit: " + limit + " id: " + id + " liczba: " + liczba);
    }

}
