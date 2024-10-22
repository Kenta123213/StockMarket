package Gielda.Zlecenia;

import java.util.Comparator;

public class ZlecenieKupnaComparator implements Comparator<ZlecenieKupna> {
    @Override
    public int compare(ZlecenieKupna o1, ZlecenieKupna o2) {
        int porownanieCeny = Integer.compare(o2.getLimit(), o1.getLimit());
        if (porownanieCeny != 0) {
            return porownanieCeny;
        }
        return o1.getId() - o2.getId();
    };
}

