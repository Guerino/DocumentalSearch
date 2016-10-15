package component;

import PacoSearch.common.Document;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author
 * Guerino
 */
public class ResultsPagination {
    //atributos para el paginador de resultados
    private final int NUMBER_ITEMS_PAGINATOR = 10;
    private boolean bfirtsPage;
    private boolean bpreviusPage;
    private boolean bnextPage;
    private boolean blastPage;
    private int currentPageIndex; //pagina actual que esta viendo el usuario 
    private int itemsPerPage;// Numero de resultados a mostrar por pagina
    private int totalRows;
    private int totalPages;
    List<Document> listDoc;

    public ResultsPagination(int itemsPerPage, List<Document> listDoc) {
        this.currentPageIndex = 1;
        this.itemsPerPage = itemsPerPage;
        this.listDoc = listDoc;
        this.totalRows = listDoc.size()-1;
        this.totalPages = totalRows/itemsPerPage + (totalRows%itemsPerPage==0?0:1);
        this.bfirtsPage = this.bpreviusPage = false;        
    }
    
    public List<Document> getListDocument(int currentPage){
        this.currentPageIndex = currentPage;
        List<Document> list = new LinkedList<Document>();
        int start = (currentPageIndex-1)*itemsPerPage+1;
        int end = currentPageIndex*itemsPerPage+1;
        
        for (int i = start; i < end; i++){
            if(i <= totalRows)
                list.add(listDoc.get(i));        
        }      
        
        return list;
    }

    public List<Integer> getListPages(){
        List<Integer> listPages = new LinkedList<Integer>();       
        //generamos las lista de numeros de pagina
        int startIndexPage, endIndexPage;
        if(currentPageIndex > 6){//de 10!!!!!!!!
            startIndexPage = currentPageIndex-5;
            endIndexPage = currentPageIndex + 5;      
        }else{
            startIndexPage = (currentPageIndex-NUMBER_ITEMS_PAGINATOR) <=0 ? 1: (currentPageIndex);
            endIndexPage = startIndexPage + NUMBER_ITEMS_PAGINATOR;                  
        }
        
        if(endIndexPage>totalPages) endIndexPage = totalPages;
//        String str="";
         //Si es una sola fila no se muestra la paginacion
        if(totalRows>itemsPerPage){
//            str = "Pagina " + currentPageIndex + " de " + totalPages + " ";//un detalle, que adorna             
            if(currentPageIndex>1){ //str += "<< Primera < Anterior ";
                this.bfirtsPage = this.bpreviusPage = true; //lo ponemos a true para mostrarlos, si no estan a false                
            }else
                this.bfirtsPage = this.bpreviusPage = false;
            
            for (int i = startIndexPage; i < endIndexPage; i++) 
                listPages.add(new Integer(i));
            
            if(currentPageIndex<totalPages) {// str += "Siguiente >";
                this.bnextPage = this.blastPage = true;
            }else
                this.bnextPage = this.blastPage = false;
//            str += " Ultima >> ";
//            System.out.println("Lista de paginas: ");
//            System.out.println(str.trim());
        }
        
        return listPages;
    }

    public boolean isFirtsPage() {
        return bfirtsPage;
    }

    public boolean isPreviusPage() {
        return bpreviusPage;
    }

    public boolean isNextPage() {
        return bnextPage;
    }

    public boolean isLastPage() {
        return blastPage;
    }

    public int getFirtsPage() {
        return 1;
    }

    public int getPreviusPage() {
        return this.currentPageIndex-1;
    }

    public int getNextPage() {
        return this.currentPageIndex+1;
    }

    public int getLastPage() {
        return this.totalPages;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }
        
}
