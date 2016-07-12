package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable{  
	
	@FXML private TextField mac;  
	@FXML private MenuButton operatetype;
	@FXML private MenuButton serverid;
	
	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage=stage;
	}
    
	@FXML
	public void confirm_press(ActionEvent event){
		CustomDialog.messageDialog(stage, "title", "content");
//		try{
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
//			Parent root = loader.load();
//			Stage tableWindow=new Stage();
//			TableController controller = (TableController)loader.getController();
//			controller.setStage(tableWindow);
//			Scene scene=new Scene(root);
//			tableWindow.setTitle("content");
//			tableWindow.setScene(scene);
//			tableWindow.initModality(Modality.APPLICATION_MODAL);
//			tableWindow.initOwner(stage);
//			tableWindow.getIcons().add(new Image(
//	                getClass().getResourceAsStream("logo.png")));
//			tableWindow.show();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
     
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
    	
    }  
      
}  