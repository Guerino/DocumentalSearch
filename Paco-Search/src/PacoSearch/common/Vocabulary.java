package PacoSearch.common;

/**
 * Clase que representa una palabra con sus atributos asociados
 * @author
 * Guerino
 */
public class Vocabulary {
    private int id; // 4 bytes
    private String word; // 2 byte inicial + (1 byte * caracter)
    private int cant_doc_aparece_nr; // 4 bytes
    private int max_veces_tf; // 4 bytes
    
    private long ptrId; // direccion de comienzo del vocabulario id 

    public Vocabulary() {
        this.id = 0;
        this.word = "";
        this.cant_doc_aparece_nr = 0;
        this.max_veces_tf = 0;
        this.ptrId = 0;
    }

    public Vocabulary(int id, String word, int cant_doc_aparece_nr, int max_veces_tf) {
        this.id = id;
        this.word = word;
        this.cant_doc_aparece_nr = cant_doc_aparece_nr;
        this.max_veces_tf = max_veces_tf;
    }

    public Vocabulary(int id, long ptrId) {
        this.id = id;
        this.ptrId = ptrId;
    }

    public Vocabulary(String word, long ptrId) {
        this.word = word;
        this.ptrId = ptrId;
    }  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCant_doc_aparece_nr() {
        return cant_doc_aparece_nr;
    }

    public void setCant_doc_aparece_nr(int cant_doc_aparece_nr) {
        this.cant_doc_aparece_nr = cant_doc_aparece_nr;
    }

    public int getMax_veces_tf() {
        return max_veces_tf;
    }

    public void setMax_veces_tf(int max_veces_tf) {
        this.max_veces_tf = max_veces_tf;
    }

    public long getPtrId() {
        return ptrId;
    }

    public void setPtrId(long ptrId) {
        this.ptrId = ptrId;
    }

   

    @Override
    public String toString() {
        return "Vocabulary{" + "id=" + id + ", word=" + word + ", nr=" + cant_doc_aparece_nr + ", max_tf=" + max_veces_tf + '}';
    }
    
          
}