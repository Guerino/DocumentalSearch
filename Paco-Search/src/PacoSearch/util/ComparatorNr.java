package PacoSearch.util;

import PacoSearch.common.Vocabulary;
import java.util.Comparator;

/**
 *
 * @author
 * Guerino
 */
public class ComparatorNr implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Vocabulary v1 = (Vocabulary)o1;
        Vocabulary v2 = (Vocabulary)o2;
        return v1.getCant_doc_aparece_nr() - v2.getCant_doc_aparece_nr();  //de menor a mayor      
    }
}
