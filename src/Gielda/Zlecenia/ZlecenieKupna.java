package Gielda.Zlecenia;

import Gielda.Inwestorzy.Inwestor;

public abstract class ZlecenieKupna extends Zlecenie{

    public ZlecenieKupna(int limit, String idAkcji, int liczba, int tura, int id, Inwestor inwestor) {
        super(limit, idAkcji, liczba, tura, id, inwestor);
    }
}
