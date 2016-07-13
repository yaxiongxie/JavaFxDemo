package application;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TableController implements Initializable{  
	
	@FXML private TableView<Bean> tableView;
	
	private Stage stage;
	public Collection<String> set;
	
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSet(Collection<String> set) {
		this.set = set;
		
		TableColumn idColumn = new TableColumn("id");
		idColumn.setMinWidth(55);
		idColumn.setCellValueFactory(
                new PropertyValueFactory<Bean, String>("id"));
 
        TableColumn contentCol = new TableColumn("content");
        contentCol.setMinWidth(544);
        contentCol.setCellValueFactory(
                new PropertyValueFactory<Bean, String>("content"));
        tableView.getColumns().addAll(idColumn,contentCol);
		
		ObservableList<TableColumn<Bean, ?>> list=tableView.getColumns();
		for(TableColumn<Bean, ?> tableColumn:list){
			System.out.println(tableColumn.getText());
		}
		Collection<Bean> beans=new ArrayList<>();
		int i=1;
		for(String serializable:set){
			System.out.println(serializable);
			Bean bean=new Bean();
			bean.setId(i);
			bean.setContent(serializable.toString());
			beans.add(bean);
			i++;
		}
		ObservableList<Bean> data=FXCollections.observableArrayList(beans);
		tableView.setItems(data);
	}
    
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
    }  
      
}  



