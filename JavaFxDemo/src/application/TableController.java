package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TableController implements Initializable{  
	
	@FXML private TableView<Bean> tableView;
	@FXML private MenuButton operatetype;
	@FXML private MenuButton serverid;
	
	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage=stage;
	}
    
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
    	System.out.println("≥ı ºªØtable");
    }  
      
}  


class Bean{
	private int id;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}

