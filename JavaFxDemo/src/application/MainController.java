package application;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
		int serverFlag=1;
		if(serverid.getText().equals("测试服务器")){
			serverFlag=2;
		}
		String operateString=operatetype.getText();
		if(operateString.equals("查询主帐号")){
			String mainbind=OperateService.getMainBind(mac.getText(), serverFlag);
			CustomDialog.messageDialog(stage, "title", mainbind);
		}else if(operateString.equals("查询次帐号")){
			Set<Serializable> set=OperateService.getSubBind(mac.getText(), serverFlag);
			Set<String> sSet=new HashSet<>();
			for(Serializable serializable:set){
				sSet.add(serializable.toString());
			}
			showTable(sSet);
		}else if(operateString.equals("注册mac")){
			OperateService.saveMac(mac.getText(), serverFlag);
			CustomDialog.messageDialog(stage, "title", "注册完成");
		}else if(operateString.equals("查询kit所有子结点")){
			List<String> list=OperateService.getKitSubNodes(mac.getText(), serverFlag);
			showTable(list);
		}else if(operateString.equals("查询门磁历史记录")){
			List<String> list=OperateService.getMcHistoryList(mac.getText(), serverFlag);
			showTable(list);
		}else if(operateString.equals("查询人体历史记录")){
			List<String> list=OperateService.getRtHistoryList(mac.getText(), serverFlag);
			showTable(list);
		}
//		CustomDialog.messageDialog(stage, "title", operatetype.getText());
//		
	}
	
	public void showTable(Collection<String> set){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
			Parent root = loader.load();
			Stage tableWindow=new Stage();
			TableController controller = (TableController)loader.getController();
			controller.setStage(tableWindow);
			controller.setSet(set);
			Scene scene=new Scene(root);
			tableWindow.setTitle("table");
			tableWindow.setScene(scene);
			tableWindow.initModality(Modality.APPLICATION_MODAL);
			tableWindow.initOwner(stage);
			tableWindow.getIcons().add(new Image(
	                getClass().getResourceAsStream("logo.png")));
			tableWindow.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
     
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
    	ObservableList<MenuItem> list=operatetype.getItems();
    	for(MenuItem menuItem:list){
    		menuItem.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    				operatetype.setText(menuItem.getText());
    			}
    		});
    	}
    	
    	ObservableList<MenuItem> serverlist=serverid.getItems();
    	for(MenuItem menuItem:serverlist){
    		menuItem.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    				serverid.setText(menuItem.getText());
    			}
    		});
    	}
    	
    }  
      
}  