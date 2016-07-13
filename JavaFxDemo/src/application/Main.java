package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root = (Parent)loader.load();
			MainController controller = (MainController)loader.getController();
			controller.setStage(primaryStage); 
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("main");
			primaryStage.getIcons().add(new Image(
	                getClass().getResourceAsStream("logo.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//28-d9-8a-02-f0-41
	public static void main(String[] args) {
		launch(args);
	}
}
