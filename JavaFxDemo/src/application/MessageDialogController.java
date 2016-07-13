package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MessageDialogController implements Initializable{  
	
	@FXML private Label content;  
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setContent(String content){
		this.content.setText(content);
	}
	
	@FXML
	public void press_button(){
		stage.close();
	}
    
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
    	
    }  
      
}  