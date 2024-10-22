package Gielda.Zlecenia;

import java.util.Comparator;

public class ZlecenieSprzedazyComparator implements Comparator<ZlecenieSprzedazy> {
    @Override
    public int compare(ZlecenieSprzedazy o1, ZlecenieSprzedazy o2) {
        int porownanieCeny = Integer.compare(o1.getLimit(), o2.getLimit());
        if (porownanieCeny != 0) {
            return porownanieCeny;
        }
        return o1.getId() - o2.getId();
    };
}

