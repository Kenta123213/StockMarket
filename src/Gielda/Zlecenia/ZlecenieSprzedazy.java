package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

import java.util.Comparator;

public abstract class ZlecenieSprzedazy extends Zlecenie{

    public ZlecenieSprzedazy(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor) {
        super(limit, idAkcji, liczba, tura, id, inwestor);
    }

    public Comparator<Zlecenie> cenaWaznosc = new Comparator<Zlecenie>() {

        @Override
        public int compare(Zlecenie o1, Zlecenie o2) {
            int porownanieCeny = Integer.compare(o1.getLimit(), o2.getLimit());
            if (porownanieCeny != 0) {
                return porownanieCeny;
            }
            return compare(o1, o2);
        }
    };
}
