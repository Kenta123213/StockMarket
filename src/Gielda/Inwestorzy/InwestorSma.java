package Gielda.Inwestorzy;

import Gielda.ArkuszZlecen;
import Gielda.Symulacja;
import Gielda.Zlecenia.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class InwestorSma extends Inwestor{

    public InwestorSma(Symulacja symulacja, int id) {
        super(symulacja, id);
    }


    //sprawdza na podstawie przeciec sma czy inwestor bedzie chcial sprzedac czy kupic
    public void utworzZlecenie(HashMap<String, Integer> akcjeCena) {
        if (symulacja.getTura() < 10) return;
        ArrayList<String> doKupna = mozeZakupic(przeciecieKupna(), akcjeCena);
        ArrayList<String> doSprzedazy = mozeSprzedac(przeciecieSprzedazy());
        if (!doKupna.isEmpty() && !doSprzedazy.isEmpty()) {
            Random rand = new Random();
            if (rand.nextBoolean()) utworzZlecenieSprzedazy(doSprzedazy, akcjeCena);
            else utworzZlecenieKupna(doKupna, akcjeCena);
        }
        else if (!doKupna.isEmpty()) {
            utworzZlecenieKupna(doKupna, akcjeCena);
        }
        else if (!doSprzedazy.isEmpty()) {
            utworzZlecenieSprzedazy(doSprzedazy, akcjeCena);
        }
    }

    public void utworzZlecenieKupna(ArrayList<String> doKupna, HashMap<String, Integer> akcjeCena) {
        HashMap<String, Integer> akcjeIlosc = portfel.getAkcjeIlosc();
        //wybieranie losowej akcji
        Collections.shuffle(doKupna);
        String akcja = doKupna.getFirst();
        int cena = akcjeCena.get(akcja);
        Random rand = new Random();
        int wachanie;
        do {
            wachanie = rand.nextInt(21) - 10;
        }
        while (cena + wachanie <= 0 || cena + wachanie > portfel.getZasoby());
        //ustalenie  nowej ceny
        cena += wachanie;
        //losowanie ilosci akcji do kupienia
        int ilosc = rand.nextInt( portfel.getZasoby() / cena) + 1;
        int ilosc2 = rand.nextInt(symulacja.iloscAkcji(akcja)/2) + 1;
        if (ilosc2 < ilosc) ilosc = ilosc2;
        ZlecenieKupna zlecenie;
        //losowanie ilosci akcji do kupienia
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

    public void utworzZlecenieSprzedazy(ArrayList<String> doSprzedazy, HashMap<String, Integer> akcjeCena) {
        //losowanie akcji do sprzedazy
        Collections.shuffle(doSprzedazy);
        String akcja = doSprzedazy.getFirst();
        Random rand = new Random();
        //losowanie ilosci do sprzedazy
        int ilosc = rand.nextInt(portfel.getAkcjeIlosc().get(akcja)) + 1;
        int cena = akcjeCena.get(akcja);
        int wachanie;
        do {
            wachanie = rand.nextInt(21) - 10;
        }
        while (cena + wachanie <= 0);
        //ustalenie ceny
        cena += wachanie;
        int typZlecenia = rand.nextInt(4);
        int waznoscTura = rand.nextInt(symulacja.getTura(), symulacja.getLiczbaTur() + 1);
        //losowanie typu zlecenia
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

    //zwraca liste z akcjami na ktore stac inwestora i ktore mialy przeciecie na kupno
    public ArrayList<String> mozeZakupic(ArrayList<ArkuszZlecen> doKupna, HashMap<String, Integer> akcjeCena) {
        ArrayList<String> arkusze = new ArrayList<>();
        for (ArkuszZlecen arkusz : doKupna) {
            if (akcjeCena.get(arkusz.getIdSpolki()) <= getZasoby()) {
                arkusze.add(arkusz.getIdSpolki());
            }
        }
        return arkusze;
    }

    //zwraca liste z akcjami ktore inwestor ma w portfelu i ktore mialy przeciecie na sprzedaz
    public ArrayList<String> mozeSprzedac(ArrayList<ArkuszZlecen> doSprzedazy) {
        ArrayList<String> arkusze = new ArrayList<>();
        for (ArkuszZlecen arkusz : doSprzedazy) {
            if (portfel.posiadaAkcje(arkusz.getIdSpolki(), 1)) {
                arkusze.add(arkusz.getIdSpolki());
            }
        }
        return arkusze;
    }

    //zwraca liste z akcjami ktore mialy przeciecie sma wskazujace na kupno
    public ArrayList<ArkuszZlecen> przeciecieKupna () {
        ArrayList<ArkuszZlecen> przeciecie = new ArrayList<>();
        for (ArkuszZlecen arkusz : symulacja.getArkusze()) {
            if (arkusz.getPrzeciecieKupno()) {
                przeciecie.add(arkusz);
            }
        }
        return przeciecie;
    }
    //zwraca liste z akcjami ktore mialy przeciecie sma wskazujace na sprzedaz
    public ArrayList<ArkuszZlecen> przeciecieSprzedazy() {
        ArrayList<ArkuszZlecen> przeciecie = new ArrayList<>();
        for (ArkuszZlecen arkusz : symulacja.getArkusze()) {
            if (arkusz.getPrzeciecieSprzedaz()) {
                przeciecie.add(arkusz);
            }
        }
        return przeciecie;
    }
}
