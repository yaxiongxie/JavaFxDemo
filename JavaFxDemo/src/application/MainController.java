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
import javafx.scene.control.TextField;

public class MainController implements Initializable{  
	
	@FXML private TextField mac;  
	@FXML private MenuButton operatetype;
	@FXML private MenuButton serverid;
    
	@FXML
	public void confirm_press(ActionEvent event){
		Dialog<Object> dialog=new Dialog<>();
		Optional<Object> result = dialog.showAndWait();
		 if (result.isPresent() && result.get() == ButtonType.OK) {
		     dialog.close();
		 }
	}
     
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
  
    }  
      
}  