package Gielda;

import java.util.HashMap;

public class Portfel {
    private int zasoby;
    private HashMap<String, Integer> akcjeIlosc;

    public Portfel(int zasoby, HashMap<String, Integer> akcjeIlosc) {
        this.zasoby = zasoby;
        this.akcjeIlosc = new HashMap<String, Integer>(akcjeIlosc);
    }

    public int getZasoby() {
        return zasoby;
    }

    public boolean posiadaAkcje(String akcja, int ilosc) {
        if (!akcjeIlosc.containsKey(akcja)) {
            return false;
        }
        else {
            return akcjeIlosc.get(akcja) >= ilosc;
        }
    }

    public void dodajAkcje(String akcja, int ilosc) {
        if (akcjeIlosc.containsKey(akcja)) {
            int aktualnaIlosc = akcjeIlosc.get(akcja);
            akcjeIlosc.put(akcja, aktualnaIlosc + ilosc);
        }
        else {
            akcjeIlosc.put(akcja, ilosc);
        }
    }

    public void dodajZasoby(int ilosc) {
        zasoby += ilosc;
    }

    public void zabierzAkcje(String akcje, int ilosc) {
        int aktualnaIlosc = akcjeIlosc.get(akcje);
        aktualnaIlosc -= ilosc;
        if (aktualnaIlosc == 0) akcjeIlosc.remove(akcje);
        else akcjeIlosc.put(akcje, aktualnaIlosc);
    }

    public void odejmijZasoby(int ilosc) {
        zasoby -= ilosc;
    }

    public HashMap<String, Integer> getAkcjeIlosc() {
        return akcjeIlosc;
    }

    public void wypisz() {
        System.out.println("Zasoby: " + zasoby);
        System.out.println("Akcje: " + akcjeIlosc);
    }
}
