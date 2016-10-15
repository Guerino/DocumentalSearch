package PacoSearch.util;

import PacoSearch.common.Post;
import java.util.Comparator;

/**
 *
 * @author
 * Guerino
 */
public class ComparatorTf implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Post p1 = (Post)o1;
        Post p2 = (Post)o2;
        return p2.getFrecuenci_tf() - p1.getFrecuenci_tf();        
    }
    
}
