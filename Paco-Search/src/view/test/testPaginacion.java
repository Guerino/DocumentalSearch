package view.test;

/**
 *
 * @author
 * Guerino
 */
public class testPaginacion {

    public static void main(String[] args) {     
        int currentPageIndex = 1;
         int totalFilas = 16;
        System.out.println("currentPageIndex: " +currentPageIndex);
        ///////////////////////////////////////////////
        int itemsPerPage = 10;
        int start = (currentPageIndex-1)*itemsPerPage+1;
        int end = currentPageIndex*itemsPerPage+1;
        String str="";
        for (int i = start; i < end; i++){
            if(i <= totalFilas)
                str += i + " ";          
        }        
        System.out.println("Lista resultados: " + str.trim());
        
        //ahora hay que generar las lista de numeros de pagina
        str="";
        int totalPages = totalFilas/itemsPerPage + (totalFilas%itemsPerPage==0?0:1);
        System.out.println("totalPages: " + totalPages);          
        
        int startIndexPage, endIndexPage;
        if(currentPageIndex > 6){//de 10!!!!!!!!!!!!!!!!!!!!!
            startIndexPage = currentPageIndex-5;
            endIndexPage = currentPageIndex + 5;      
        }else{
            startIndexPage = (currentPageIndex-itemsPerPage) <=0 ? 1: (currentPageIndex);
            endIndexPage = startIndexPage + itemsPerPage;                  
        }
        
        if(endIndexPage>totalPages) endIndexPage = totalPages;
        
        //Si es una sola fila no se muestra la paginacion
        if(totalFilas>itemsPerPage){
            str = "Pagina " + currentPageIndex + " de " + totalPages + " | ";//un detalle, que adorna
            
            
            if(currentPageIndex>1) str += "<< Primera < Anterior ";            
            for (int i = startIndexPage; i < endIndexPage; i++) str += i + " ";
            if(currentPageIndex<totalPages) str += "Siguiente >";
            str += " Ultima >> ";
            System.out.println("Lista de paginas: ");
            System.out.println(str.trim());
        }
    }
}
