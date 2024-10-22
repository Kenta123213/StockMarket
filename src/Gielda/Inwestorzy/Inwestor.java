package Gielda.Inwestorzy;

import Gielda.Portfel;
import Gielda.Symulacja;

import java.util.HashMap;

public abstract class Inwestor {
    protected Portfel portfel;
    protected Symulacja symulacja;
    protected int id;

    public Inwestor(Symulacja symulacja, int id) {
        this.symulacja = symulacja;
        this.id = id;
    }

    public int getZasoby() {
        return portfel.getZasoby();
    }

    public boolean posiadaZasoby (int ilosc) {
        return portfel.getZasoby() >= ilosc;
    }

    public Symulacja getSymulacja() {
        return symulacja;
    }

    public boolean posiadaAkcje (String akcje, int ilosc) {
        return portfel.posiadaAkcje(akcje, ilosc);
    }

    public abstract void utworzZlecenie(HashMap<String, Integer> akcjeCena);

    public int getId() {
        return id;
    }

    public void utworzPortfel(int zasoby, HashMap<String, Integer> akcjeIlosc) {
        this.portfel = new Portfel(zasoby, akcjeIlosc);
    }

    public void dodajZasoby(int ilosc) {
        portfel.dodajZasoby(ilosc);
    }

    public void dodajAkcje(String akcje, int ilosc) {
        portfel.dodajAkcje(akcje, ilosc);
    }

    public void odejmijZasoby(int ilosc) {
        portfel.odejmijZasoby(ilosc);
    }

    public void zabierzAkcje(String akcja, int ilosc) {
        portfel.zabierzAkcje(akcja, ilosc);
    }

    public void wypiszPortfel() {
        portfel.wypisz();
    }
}
