package actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
/**
 *
 * @author
 * Guerino
 */

public class DownloadAction  extends ActionSupport  {
    private String pathFile = "";
    private String filename = "";
    private InputStream fileInputStream = null;

    public void setFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getFilename() {
        return filename;
    }
    
    public InputStream getFileInputStream() {	 
            return fileInputStream;	   
    }

    @Override
    public String execute() throws Exception {
        filename = pathFile.substring(pathFile.lastIndexOf("\\")+1).trim();
        fileInputStream = new FileInputStream(new File(pathFile));

        return SUCCESS;	  
    }
}
