package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MedicineController implements Initializable {
	
	public static String query;
	public TextField gName,m_id,bName,comp,exp;
	
  
     public void Home(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("FXML_Page4.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
      public void Search(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
    	boolean isFirst=true;
    	boolean showAll=true;
    	query = "select * from pharmacy.dbo.medicine";
    	if (!(gName.getText().isEmpty() || gName.getText()==null)) {
    		if(isFirst) {
    			query+=" where";
    			isFirst=false;
    		}
    		query+=" generic_name='"+gName.getText()+"' and";
    		showAll=false;
    	}
    	if (!(m_id.getText().isEmpty() || m_id.getText()==null)) {
    		if(isFirst) {
    			query+=" where";
    			isFirst=false;
    		}
    		query+=" m_id="+Integer.parseInt(m_id.getText())+" and";
    		showAll=false;
    	}
    	if (!(bName.getText().isEmpty() || bName.getText()==null)) {
    		if(isFirst) {
    			query+=" where";
    			isFirst=false;
    		}
    		query+=" brand_names='"+bName.getText()+"' and";
    		showAll=false;
    	}
    	if (!(comp.getText().isEmpty() || comp.getText()==null)) {
    		if(isFirst) {
    			query+=" where";
    			isFirst=false;
    		}
    		query+=" companies='"+comp.getText()+"' and";
    		showAll=false;
    	}
    	if (!(exp.getText().isEmpty() || exp.getText()==null)) {
    		if(isFirst) {
    			query+=" where";
    			isFirst=false;
    		}
    		query+=" last_exp_date='"+exp.getText()+"' and";
    		showAll=false;
    	}
    	if(!showAll) {
    	query = query.substring(0, query.length()-4)+";";
    	}
    	//System.out.println(query);
    	
        Parent Home = FXMLLoader.load(getClass().getResource("AboutMedicine.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
