package Gielda;

import Gielda.Inwestorzy.Inwestor;
import Gielda.Inwestorzy.InwestorRand;
import Gielda.Inwestorzy.InwestorSma;
import Gielda.Wyjatki.WlasnyWyjatek;
import Gielda.Zlecenia.Zlecenie;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Symulacja {
    private int liczbaTur;
    private int tura;
    private ArrayList<Inwestor> inwestorzy;
    private HashMap<String, Integer> akcjeCena;
    private ArrayList<ArkuszZlecen> arkusze;
    private String nazwaPliku;
    private int globalId;

    public Symulacja(String nazwaPliku, int liczbaTur) {
        this.nazwaPliku = nazwaPliku;
        this.liczbaTur = liczbaTur;
        this.tura = 0;
        this.inwestorzy = new ArrayList<>();
        this.akcjeCena = new HashMap<>();
        this.arkusze = new ArrayList<>();
        this.globalId = 0;
        wczytajDaneZPliku();
    }

    public void start() {
        for (int i = 0; i < liczbaTur; i++) {
            turaStart();
        }
        wypiszInwestorow();
    }

    public int getCenaAkcji(String akcja) {
        return akcjeCena.get(akcja);
    }

    public int getTura() {
        return tura;
    }

    public int getLiczbaTur() {
        return liczbaTur;
    }

    public int getGlobalId() {
        return globalId;
    }

    public ArrayList<ArkuszZlecen> getArkusze() {
        return arkusze;
    }

    public void ustawCene (String akcja, int cena) {
        akcjeCena.put(akcja, cena);
    }

    public void wprowadzZlecenie(Zlecenie zlecenie){
        for (ArkuszZlecen arkusz : arkusze){
            if (arkusz.getIdSpolki().equals(zlecenie.getIdAkcji())){
                arkusz.dodajZlecenie(zlecenie);
            }
        }
    }

    public void wypiszInwestorow() {
        for (Inwestor inwestor : inwestorzy){
            System.out.println("Portfel inwestora " + inwestor.getId() + " : ");
            inwestor.wypiszPortfel();
        }
    }

    public void idPlus() {
        globalId++;
    }

    public void turaStart() {
        Collections.shuffle(inwestorzy);
        for (Inwestor inwestor : inwestorzy) {
            inwestor.utworzZlecenie(akcjeCena);
        }
        for (ArkuszZlecen arkusz : arkusze) {
            arkusz.Tura();
        }
        tura++;
    }

    public void wczytajDaneZPliku() {
        try {
            Scanner scanner = new Scanner(new File(nazwaPliku));
            String linia = scanner.nextLine();
            while (linia.charAt(0) == '#' && scanner.hasNextLine()) {
                linia = scanner.nextLine();
            }
            wczytajInwestorow(linia);
            linia = scanner.nextLine();
            while (linia.charAt(0) == '#' && scanner.hasNextLine()) {
                linia = scanner.nextLine();
            }
            wczytajAkcje(linia);
            linia = scanner.nextLine();
            while (linia.charAt(0) == '#' && scanner.hasNextLine()) {
                linia = scanner.nextLine();
            }
            wczytajPortfele(linia);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku o nazwie: " + nazwaPliku);
        }
    }

    public void wczytajInwestorow(String linia) {
        int i = 0;
        Scanner scanner = new Scanner(linia);
        while (scanner.hasNextLine()) {
            String c = scanner.next();
            try {
                sprawdzPoprawnoscTypuInwestora(c);
                if (c.equals("R")) {
                    inwestorzy.add(new InwestorRand(this, i));
                }
                else {
                    inwestorzy.add(new InwestorSma(this, i));
                }
                i++;
            }
            catch (WlasnyWyjatek e) {
                System.err.println("Błąd: " + e.getMessage());
                System.exit(1);
            }
        }
        scanner.close();
    }

    public void sprawdzPoprawnoscTypuInwestora(String c) throws WlasnyWyjatek {
        if (!c.equals("R") && !c.equals("S")){
            throw new WlasnyWyjatek("Złe dane typu inwestorów");
        }
    }

    public void wczytajAkcje(String linia) {
        String[] pary = linia.split(" ");
        for (String para : pary) {
            String[] x = para.split(":");
            akcjeCena.put(x[0], Integer.parseInt(x[1]));
            arkusze.add(new ArkuszZlecen(x[0], Integer.parseInt(x[1]), this));
        }
    }

    public int iloscAkcji (String akcja) {
        for (ArkuszZlecen arkusz : arkusze) {
            if (arkusz.getIdSpolki().equals(akcja)) return arkusz.getIloscAkcji();
        }
        return 0;
    }

    public void wczytajPortfele(String linia) {
        Scanner scanner = new Scanner(linia);
        HashMap<String, Integer> akcjeIlosc = new HashMap<>();
        int zasoby = Integer.parseInt(scanner.next());
        String x;
        do {
            x = scanner.next();
            String[] split = x.split(":");
            akcjeIlosc.put(split[0], Integer.parseInt(split[1]));
            for (ArkuszZlecen arkusz : arkusze) {
                if (arkusz.getIdSpolki().equals(split[0])) {
                    arkusz.setIloscAkcji(Integer.parseInt(split[1]) * inwestorzy.size());
                }
            }
        }
        while (scanner.hasNext());
        scanner.close();
        for (Inwestor inwestor : inwestorzy) {
            inwestor.utworzPortfel(zasoby, akcjeIlosc);
        }
    }
}
