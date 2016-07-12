package application;

import application.SBTFxDialog.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomDialog {
	
	public static void messageDialog(Stage stage,String title,String content){
		try{
			FXMLLoader loader = new FXMLLoader(CustomDialog.class.getResource("messageDialog.fxml"));
			Parent root = loader.load();
			Stage dialogStage=new Stage();
			MessageDialogController controller = (MessageDialogController)loader.getController();
			Scene scene=new Scene(root);
			dialogStage.setTitle("title");
			dialogStage.setScene(scene);
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(stage);
			dialogStage.getIcons().add(new Image(
					CustomDialog.class.getResourceAsStream("logo.png")));
			controller.setStage(dialogStage);
			controller.setContent("content");
			dialogStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
