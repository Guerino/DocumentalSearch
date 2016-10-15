package PacoSearch.util;

import java.util.Comparator;

/**
 *
 * @author
 * Guerino
 */
public class ComparatorWords implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
             Tuple t1 = (Tuple)o1;
             Tuple t2 = (Tuple)o2;             
             return t1.getWord().compareTo(t2.getWord());
        }
    
}
