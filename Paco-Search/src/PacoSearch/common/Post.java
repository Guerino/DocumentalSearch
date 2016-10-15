package PacoSearch.common;

/**
 *
 * @author
 * Guerino
 */
public class Post {
    private Document doc; 
    private int frecuenci_tf;

    public Post() {
        this.doc = new Document();
        this.frecuenci_tf = 0;
    }

    public Post(Document doc, int frecuenci_tf) {
        this.doc = doc;
        this.frecuenci_tf = frecuenci_tf;
    }

    public Post(int docId, int frecuenci_tf) {
        this.doc = new Document();
        this.doc.setId(docId);
        this.frecuenci_tf = frecuenci_tf;
    }
    
    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public int getFrecuenci_tf() {
        return frecuenci_tf;
    }

    public void setFrecuenci_tf(int frecuenci_tf) {
        this.frecuenci_tf = frecuenci_tf;
    }

//    @Override
//    public String toString() {
//        return "Post{" + "doc=" + doc.toString() + ", frecuenci_tf=" + frecuenci_tf + '}';
//    } 
    

    @Override
    public String toString() {
        return "Post{" + "doc=" + doc.getId() + ", tf=" + frecuenci_tf + '}';
    }   
}