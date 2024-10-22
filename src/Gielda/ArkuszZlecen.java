package Gielda;

import Gielda.Zlecenia.*;

import java.util.*;

public class ArkuszZlecen {
    private PriorityQueue<ZlecenieKupna> zleceniaKupna;
    private ZlecenieKupnaComparator zlecenieKupnaComparator;
    private PriorityQueue<ZlecenieSprzedazy> zleceniaSprzedazy;
    private ZlecenieSprzedazyComparator zlecenieSprzedazyComparator;
    private Comparator<ZlecenieSprzedazy> comparatorSprzedaz;
    private String idSpolki;
    private int iloscAkcji;
    private Symulacja symulacja;
    private ArrayList<Integer> ceny;      //10 cen z poprzednich tur

    private float sma5;
    private float sma10;

    private boolean sma5_10;            //zmienna pokazujaca czy 5 jest nad 10 czy pod

    private boolean przeciecieKupno;
    private boolean przeciecieSprzedaz;

    public float wyznaczSmaN(int n) {
        int suma = 0;
        if (ceny.size() >= n) {
            for (int i = 0; i < n; i++) {
                suma += ceny.get(ceny.size() - 1 - i);
            }
        }
        return (float) suma / n;
    }

    public ArkuszZlecen(String idSpolki, int cenaPoczatkowa, Symulacja symulacja) {
        this.idSpolki = idSpolki;
        this.zlecenieKupnaComparator = new ZlecenieKupnaComparator();
        this.symulacja = symulacja;
        this.zlecenieSprzedazyComparator = new ZlecenieSprzedazyComparator();
        this.zleceniaKupna = new PriorityQueue<>(zlecenieKupnaComparator);
        this.zleceniaSprzedazy = new PriorityQueue<>(zlecenieSprzedazyComparator);
        this.ceny = new ArrayList<>();
        this.ceny.add(cenaPoczatkowa);
    }

    public PriorityQueue<ZlecenieKupna> getZleceniaKupna() {
        return zleceniaKupna;
    }

    public PriorityQueue<ZlecenieSprzedazy> getZleceniaSprzedazy() {
        return zleceniaSprzedazy;
    }

    public int getIloscAkcji() {
        return iloscAkcji;
    }

    public boolean getPrzeciecieKupno() {
        return przeciecieKupno;
    }

    public boolean getPrzeciecieSprzedaz() {
        return przeciecieSprzedaz;
    }

    public String getIdSpolki() {
        return idSpolki;
    }

    public void setIloscAkcji(int iloscAkcji) {
        this.iloscAkcji = iloscAkcji;
    }

    //funkcja przy dodawaniu ceny do listy dba zeby zapisane bylo max 100 ostatnich cen
    public void dodajCene(int cena) {
        if (ceny.size() < 10) ceny.add(cena);
        else {
            ceny.removeFirst();
            ceny.add(cena);
        }
    }

    //dodaje zlecenie na odpowiednia kolejke priortytetowa
    public void dodajZlecenie(Zlecenie zlecenie) {
        if (zlecenie instanceof ZlecenieKupna) {
            zleceniaKupna.add((ZlecenieKupna) zlecenie);
        } else {
            zleceniaSprzedazy.add((ZlecenieSprzedazy) zlecenie);
        }
    }

    //sprawdza czy limity umozliwiaja dokonanie transakcji
    public boolean moznaWykonac(ZlecenieKupna zlecenieK, ZlecenieSprzedazy zlecenieS) {
        return zlecenieK.getLimit() >= zlecenieS.getLimit();
    }

    //symulacja jedenej tury
    public void Tura() {
        //zmienna pokazaujaca czy w danej turze zostala wykonana jakakolwiek transakcja
        boolean czyZmiana = false;
        //zmienna pokazujaca czy w danej turze nastapilo przeciecie sygnalizujace kupno
        przeciecieKupno = false;
        //zmienna pokazujaca czy w danej turze nastapilo przeciecie sygnalizujace sprzedaz
        przeciecieSprzedaz = false;
        boolean czyWykonac;

        //wczytanie pierwszych w kolejce zlecen kupna i sprzedazy
        ZlecenieKupna zlecenieK = zleceniaKupna.peek();
        ZlecenieSprzedazy zlecenieS = zleceniaSprzedazy.peek();
        while (!zleceniaKupna.isEmpty() && !zleceniaSprzedazy.isEmpty() && moznaWykonac(zlecenieK, zlecenieS)) {
            czyWykonac = true;
            //obsluga zlecen WA
            if (zlecenieK instanceof ZlecenieKupnaWA) {
                if (!zlecenieK.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenieK)) {
                    zleceniaKupna.remove(zlecenieK);
                    czyWykonac = false;
                }
            } else if (zlecenieS instanceof ZlecenieSprzedazyWA) {
                if (!zlecenieS.czyMozeBycZrealizowane(zleceniaKupna, zleceniaSprzedazy, zlecenieS)) {
                    zleceniaSprzedazy.remove(zlecenieS);
                    czyWykonac = false;
                }
            }
            //sprawdzenie czy inwestorzy dalej maja odpowiednie srodki i zasoby
            if (!zlecenieK.getInwestor().posiadaZasoby(ustalCene(zlecenieK, zlecenieS) *
                    Math.min(zlecenieS.getLiczba(), zlecenieK.getLiczba())) && czyWykonac) {
                zleceniaKupna.remove(zlecenieK);
                czyWykonac = false;
            }
            if (!zlecenieS.getInwestor().posiadaAkcje(zlecenieS.getIdAkcji(), zlecenieS.getLiczba()) && czyWykonac) {
                zleceniaSprzedazy.remove(zlecenieS);
                czyWykonac = false;
            }
            //zlecenie wykonania zlecenia
            if (czyWykonac) {
                wykonajZlecenie(zlecenieK, zlecenieS);
                czyZmiana = true;
            }
            zlecenieK = zleceniaKupna.peek();
            zlecenieS = zleceniaSprzedazy.peek();
        }
        if (czyZmiana) {
            dodajCene(symulacja.getCenaAkcji(idSpolki));
            //sprawdzanie czy nastapilo przeciecie
            sma5 = wyznaczSmaN(5);
            sma10 = wyznaczSmaN(10);
            if (!sma5_10 && sma5 >= sma10) {
                przeciecieKupno = true;
                sma5_10 = true;
            }
            if (sma5_10 && sma5 <= sma10) {
                przeciecieSprzedaz = true;
            }
            if (sma5 < sma10) sma5_10 = false;
        }
        turaKoniec();
    }

    //usuniecie odpowiednich zlecen
    public void turaKoniec() {
        zleceniaKupna.removeIf(Zlecenie::czyUsunac);
        zleceniaSprzedazy.removeIf(Zlecenie::czyUsunac);
    }

    public void wykonajZlecenie(ZlecenieKupna zlecenieK, ZlecenieSprzedazy zlecenieS) {
        //ustawienie nowej ceny
        symulacja.ustawCene(zlecenieK.getIdAkcji(), ustalCene(zlecenieK, zlecenieS));
        if (zlecenieK.getLiczba() > zlecenieS.getLiczba()) {
            //zmniejszenie ilsoci akcji w zleceniu inwestora K
            zlecenieK.zmniejszLiczba(zlecenieS.getLiczba());
            //danie pieniedzy inwestorowi S
            zlecenieS.getInwestor().dodajZasoby(ustalCene(zlecenieK, zlecenieS) * zlecenieS.getLiczba());
            //zabranie akcji inwestorowi S
            zlecenieS.getInwestor().zabierzAkcje(zlecenieS.getIdAkcji(), zlecenieS.getLiczba());
            //zabranie pieniedzy inwestorowi K
            zlecenieK.getInwestor().odejmijZasoby(ustalCene(zlecenieK, zlecenieS) * zlecenieS.getLiczba());
            //danie akcji inwestorowi K
            zlecenieK.getInwestor().dodajAkcje(zlecenieK.getIdAkcji(), zlecenieS.getLiczba());
            //usuniecie zlecenia inwestora S
            zleceniaSprzedazy.remove();
        } else if (zlecenieK.getLiczba() == zlecenieS.getLiczba()) {
            //danie pieniędzy inwestorowi S
            zlecenieS.getInwestor().dodajZasoby(ustalCene(zlecenieK, zlecenieS) * zlecenieS.getLiczba());
            //zabranie akcji inwestorowi S
            zlecenieS.getInwestor().zabierzAkcje(zlecenieS.getIdAkcji(), zlecenieK.getLiczba());
            //zabranie pieniedzy inwestorowi K
            zlecenieK.getInwestor().odejmijZasoby(ustalCene(zlecenieK, zlecenieS) * zlecenieS.getLiczba());
            //danie akcji inwestorowi K
            zlecenieK.getInwestor().dodajAkcje(zlecenieK.getIdAkcji(), zlecenieK.getLiczba());
            //usuniecie obu zleceń
            zleceniaSprzedazy.remove();
            zleceniaKupna.remove();
        } else {
            //zmniejszenie ilosci akcji w zleceniu inwestora S
            zlecenieS.zmniejszLiczba(zlecenieK.getLiczba());
            //danie pieniędzy inwestorowi S
            zlecenieS.getInwestor().dodajZasoby(ustalCene(zlecenieK, zlecenieS) * zlecenieK.getLiczba());
            //zabranie akcji inwestorowi S
            zlecenieS.getInwestor().zabierzAkcje(zlecenieS.getIdAkcji(), zlecenieK.getLiczba());
            //zabranie pieniedzy inwestorowi K
            zlecenieK.getInwestor().odejmijZasoby(ustalCene(zlecenieK, zlecenieS) * zlecenieK.getLiczba());
            //danie akcji inwestorowi K
            zlecenieK.getInwestor().dodajAkcje(zlecenieK.getIdAkcji(), zlecenieK.getLiczba());
            //usuniecie zlecenia kupna
            zleceniaKupna.remove();
        }
    }

    //zwraca cene transakcji na podstawie ktore zlecenie jest starsze
    public int ustalCene(ZlecenieKupna zlecenieK, ZlecenieSprzedazy zlecenieS) {
        if (zlecenieK.getId() > zlecenieS.getId()) {
            return zlecenieS.getLimit();
        }
        return zlecenieK.getLimit();
    }
}