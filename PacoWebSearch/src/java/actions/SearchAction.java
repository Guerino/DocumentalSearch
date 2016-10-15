package actions;

import PacoSearch.Search;
import PacoSearch.SearchIndexFile;
import PacoSearch.common.Document;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import component.ResultsPagination;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 * Guerino
 */
public class SearchAction extends ActionSupport {
    private List<Document> listDoc;    
    private String query;
    private String time;
    private int totalDoc;
    //atributos para el paginador de resultados
    private List<Integer> listPage;
    private int currentPage; //pagina actual que esta viendo el usuario  
    private int numberPage; //numero de pagina a mostrar pasada por parametro    
    
    private int firtsPage;
    private int previusPage;
    private int nextPage;
    private int lastPage;
    
    private boolean bfirtsPage;
    private boolean bpreviusPage;
    private boolean bnextPage;
    private boolean blastPage;
    
    private List<Integer> listItemsPerPage;
    private int itemSelected;//numero de resultados a mostrar por pagina
    
    public SearchAction() {
        this.listDoc = new LinkedList<Document>();
        this.listPage = new LinkedList<Integer>();
        this.listItemsPerPage =  new LinkedList<Integer>();
        this.query = "";
        this.totalDoc = 0;
        this.currentPage = 0;
        this.bfirtsPage = this.bpreviusPage = false;
        this.bnextPage = this.blastPage = false;
        this.lastPage = 0;
        this.itemSelected = 10; // 10 items inicialmente
        
        listItemsPerPage.add(new Integer(5));
        listItemsPerPage.add(new Integer(10));
        listItemsPerPage.add(new Integer(25));
        listItemsPerPage.add(new Integer(50));
        listItemsPerPage.add(new Integer(100));
    }

    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();       
        String qu = (String)session.get("query");
        //si es la primera vez
        if(!getQuery().equals(qu) || qu == null)
        {
            if(!getQuery().isEmpty()){
                //Realizamos la busqueda en el indice
                List<Document> list = searchQuery();            
                //guardamos la palabra de busqueda para compararla y en caso de que sea
                //la misma no volvera realizar de nuevo la busqueda
                session.put("query", query);
                session.put("time", time);
                session.put("totalDoc", totalDoc);
                session.put("list", list);
               
                if(list.size() > 1){
                    //Paginador, por defecto, 10 resultados por pagina comenzando en la 1
                    ResultsPagination rp = new ResultsPagination(getItemsPerPage(), list);

                    this.listDoc = rp.getListDocument(1);
                    this.listPage = rp.getListPages(); 

                    this.bfirtsPage = rp.isFirtsPage();
                    this.bpreviusPage = rp.isPreviusPage();
                    this.bnextPage = rp.isNextPage();
                    this.blastPage = rp.isLastPage();

                    this.currentPage = rp.getCurrentPageIndex();
                    this.firtsPage = rp.getFirtsPage();
                    this.previusPage = rp.getPreviusPage();
                    this.nextPage = rp.getNextPage();
                    this.lastPage = rp.getLastPage();
                    
                    session.put("itemSelected", getItemsPerPage());
                }
            }else
                return NONE;          
        }else{
            //si es la misma palabra de busqueda, aumentar o retroceder 10 registros segun donde el usuario haga clic
            totalDoc = Integer.valueOf(String.valueOf(session.get("totalDoc")));
            time = String.valueOf(session.get("time"));      
            
            List<Document> list = (List<Document>)session.get("list");
            if(list.size() > 1){                
                if(numberPage<=0) numberPage = 1;
                //Paginador, 10 resultados por pagina comenzando en la numberPage
                ResultsPagination rp = new ResultsPagination(getItemsPerPage(), list);
                listDoc = rp.getListDocument(numberPage);
                listPage = rp.getListPages();

                bfirtsPage = rp.isFirtsPage();
                bpreviusPage = rp.isPreviusPage();
                bnextPage = rp.isNextPage();            
                blastPage = rp.isLastPage();

                currentPage = rp.getCurrentPageIndex();
                firtsPage = rp.getFirtsPage();
                previusPage = rp.getPreviusPage();
                nextPage = rp.getNextPage();
                lastPage = rp.getLastPage();                
            }
        }
        
        return SUCCESS;
    }
    
    private List<Document> searchQuery(){
        long startTime = System.currentTimeMillis();
        File pathIndex = new File("index").getAbsoluteFile();
        List<Document> list = new LinkedList<Document>();
        if(pathIndex.exists()){     
            Search search = new PacoSearch.Search(pathIndex.getAbsolutePath(), SearchIndexFile.BINARY_SEARCH);            
            list = search.search(this.query);
        }

        long endTime = System.currentTimeMillis();
        double totalTime = (double)(endTime - startTime)/(double)1000;            
        totalDoc = (list == null) ? 0: list.size();        
        time = String.valueOf(totalTime);
        
        return list;
    }
    
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    public String getQ() { // de 10!
        String q = "";
        String[] words = this.query.toLowerCase().split(" ");
        for (int i = 0; i < words.length; i++) {
               q += words[i] + '+';
        }        
        q = q.substring(0, q.lastIndexOf('+')).trim();        
        return q;
    }

    public void setQ(String query) {
        this.query = query;
    }

    public int getItemsPerPage() {        
        return itemSelected;
    }

    
    public String getTime() {
        return time;
    }

    public int getTotalDoc() {
        return totalDoc;
    }
    
    public List<Document> getListDoc() {
        return listDoc;
    }

    public List<Integer> getListPages() {
        return this.listPage;
    }
    
    public int getNumberPage() {
        return numberPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
  
    public boolean getIsFirtsPage() {
        return bfirtsPage;
    }

    public boolean getIsPreviusPage() {
        return bpreviusPage;
    }

    public boolean getIsLastPage() {
        return blastPage;
    }

    public boolean getIsNextPage() {
        return bnextPage;
    }

    public int getFirtsPage() {
        return firtsPage;
    }

    public int getPreviusPage() {
        return previusPage;
    }

    public int getNextPage() {
        return nextPage;
    }
    
    public int getLastPage() {
        return lastPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(String itemSelected) {
        if(itemSelected.isEmpty()) itemSelected ="10";
        this.itemSelected = Integer.parseInt(itemSelected);
    }

    public List<Integer> getListItemsPerPage() {
        return listItemsPerPage;
    }
    
    
    
}