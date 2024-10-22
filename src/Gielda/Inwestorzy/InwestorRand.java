package Gielda.Inwestorzy;

import Gielda.Symulacja;
import Gielda.Zlecenia.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InwestorRand extends Inwestor{

    public InwestorRand(Symulacja symulacja, int id){
        super(symulacja, id);
    }

    public void utworzZlecenie(HashMap<String, Integer> akcjeCena) {
        if (czyMozeZakupic(akcjeCena) && czyMozeSprzedac()) {
            Random rand = new Random();
            if (rand.nextBoolean()) utworzZlecenieKupna(akcjeCena);
            else utworzZlecenieSprzedazy(akcjeCena);
        }
        else if (czyMozeSprzedac()) utworzZlecenieSprzedazy(akcjeCena);
        else if (czyMozeZakupic(akcjeCena)) utworzZlecenieKupna(akcjeCena);
    }

    public void utworzZlecenieSprzedazy(HashMap<String, Integer> akcjeCena) {
        HashMap<String, Integer> akcjeIlosc = portfel.getAkcjeIlosc();
        ArrayList<String> akcje = new ArrayList<>(akcjeIlosc.keySet());
        Random rand = new Random();
        //wybranie losowej akcji z portfela do sprzedazy
        String akcja = akcje.get(rand.nextInt(akcje.size()));
        int ilosc = rand.nextInt(akcjeIlosc.get(akcja)) + 1;
        //wczytanie ceny z ostatniej transakcji
        int cena = akcjeCena.get(akcja);
        int wachanie;
        do {
            wachanie = rand.nextInt(21) - 10;
        }
        while (cena + wachanie <= 0);
        //ustalenie limitu ceny
        cena += wachanie;
        //losowy wybor typu zlecenia
        int typZlecenia = rand.nextInt(4);
        int waznoscTura = rand.nextInt(symulacja.getTura(), symulacja.getLiczbaTur() + 1);
        ZlecenieSprzedazy zlecenie;
        switch (typZlecenia) {
            case 0:
                zlecenie = new ZlecenieSprzedazyN(cena, akcja, ilosc, symulacja.getTura(),
                        symulacja.getGlobalId(), this);
                break;
            case 1:
                zlecenie = new ZlecenieSprzedazyBT(cena, akcja, ilosc, symulacja.getTura(),
                        symulacja.getGlobalId(), this);
                break;
            case 2:
                zlecenie = new ZlecenieSprzedazyWA(cena, akcja, ilosc, symulacja.getTura(),
                        symulacja.getGlobalId(), this);
                break;
            case 3:
                zlecenie = new ZlecenieSprzedazyWT(cena, akcja, ilosc, symulacja.getTura(),
                        symulacja.getGlobalId(), this, waznoscTura);
                break;
            default:
                return;
        }
        symulacja.idPlus();
        symulacja.wprowadzZlecenie(zlecenie);
    }

    public void utworzZlecenieKupna(HashMap<String, Integer> akcjeCena) {
        ArrayList<String> akcje = new ArrayList<>(akcjeCena.keySet());
        Random rand = new Random();
        int cena;
        String akcja;
        //wybranie losowej akcji na ktora inwestor ma pieniadze
        do {
            akcja = akcje.get(rand.nextInt(akcje.size()));
            cena = akcjeCena.get(akcja);
        }
        while (cena > portfel.getZasoby());
        int wachanie;
        //losowanie wachania ceny
        do {
            wachanie = rand.nextInt(21) - 10;
        }
        while (cena + wachanie <= 0 || cena + wachanie > portfel.getZasoby());
        //ustalenie nowej ceny
        cena += wachanie;
        //ustalenie liczby akcji do zakupy
        //ilosc2 jest losowana w zakrasie ilosci akcji dostepnych na rynku / 2 w celu zbalansowania
        int ilosc = rand.nextInt( portfel.getZasoby() / cena) + 1;
        int ilosc2 = rand.nextInt(symulacja.iloscAkcji(akcja)/2) + 1;
        if (ilosc2 < ilosc) ilosc = ilosc2;
        ZlecenieKupna zlecenie;
        //losowanie typu zlecenia
        int typZlecenia = rand.nextInt(4);
        int waznoscTura = rand.nextInt(symulacja.getTura(), symulacja.getLiczbaTur() + 1);
        switch (typZlecenia) {
            case 0:
                zlecenie = new ZlecenieKupnaN(cena, akcja, ilosc, symulacja.getTura(), symulacja.getGlobalId(),
                        this);
                break;
            case 1:
                zlecenie = new ZlecenieKupnaBT(cena, akcja, ilosc, symulacja.getTura(), symulacja.getGlobalId(),
                        this);
                break;
            case 2:
                zlecenie = new ZlecenieKupnaWA(cena, akcja, ilosc, symulacja.getTura(), symulacja.getGlobalId(),
                        this);
                break;
            case 3:
                zlecenie = new ZlecenieKupnaWT(cena, akcja, ilosc, symulacja.getTura(), symulacja.getGlobalId(),
                        this, waznoscTura);
                break;
            default:
                return;
        }
        symulacja.idPlus();
        symulacja.wprowadzZlecenie(zlecenie);
    }

    //sprawdza czy istnieje akcja na ktora stac inwestora
    public boolean czyMozeZakupic(HashMap<String, Integer> akcjeCena) {
        for (String akcja : akcjeCena.keySet()) {
            if (portfel.getZasoby() >= akcjeCena.get(akcja)) return true;
        }
        return false;
    }

    //sprawdza czy inwestor ma jakakolwiek akcje na sprzedaz
    public boolean czyMozeSprzedac() {
        return !portfel.getAkcjeIlosc().isEmpty();
    }
}
