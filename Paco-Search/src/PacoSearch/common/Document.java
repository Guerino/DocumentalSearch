package PacoSearch.common;

/**
 * Clase que representa un documento con sus palabras mas frecuentes
 * @author
 * Guerino
 */
public class Document {
    private int id; // 4 bytes
    private String path; // 2 byte inicial + (1 byte * caracter) 
    private long size; //tamaño total del archivo en bytes        
    private long ptrId; // direccion de comienzo del document id
    
    private String name;
        
    public Document() {
        this.id = 1;
        this.path = "";
        this.size = 0;
        this.ptrId = 0;
    }

    public Document(int id, String path, long size) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.ptrId = 0;
    }

    public Document(int id, long startIndex) {
        this.id = id;
        this.ptrId = startIndex;
        this.path = "";
    }    

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        if(id == 0) id = 1;
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getPtrId() {
        return ptrId;
    }

    public void setPtrId(long ptrId) {
        this.ptrId = ptrId;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return path.substring(this.path.lastIndexOf("\\")+1, path.length());
    }

    
    //Funciona de 10!
    public String getSizeToString() {
        String str = new String ();      
        if(size > 1048576){
                long MB = size/1048576;
                str = MB + " MB";
        }else
            if(size > 1024){
                long KB = size/1024;
                str = KB + " KB";
        }else
             str = size + " bytes";     
            
        return str;
    }
        
    public void setSize(long size) {
        this.size = size;
    }

    //Redefinido para poder ser usado en el docRank
    @Override
    public boolean equals(Object obj) {
        Document doc = (Document)obj;
        return (this.getId() == doc.getId()) ? true : false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        return hash;
    } 

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", path=" + path + ", size = " + size + " bytes}";
    }
    
}
