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
			controller.setStage(primaryStage); // or what you want to do
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("main");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(
	                getClass().getResourceAsStream("logo.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
