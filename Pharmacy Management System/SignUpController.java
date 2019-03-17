package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SignUpController implements Initializable {

	public TextField pName,uName,pass;
	public Button addBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	
    	
    }
    
    public void add(ActionEvent event) throws IOException{
    	
    	String query="insert into pharmacy.dbo.pharmacist(p_name,username,pass_hash) values('"+pName.getText()+"','"+uName.getText()+"','"+pass.getText().hashCode()+"')";
    	//ResultSet rs = connectivity.executeStatement(Main.con, query);
		try(PreparedStatement pstmt = Main.con.prepareStatement(query);) {
	        pstmt.execute();
	        Parent Home = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
	        Scene Home_scene = new Scene(Home);
	        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        app_stage.setScene(Home_scene);
	        app_stage.show();

	    }
	    // Handle any errors that may have occurred.
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
    	//raise error
    }
    
    public void back(ActionEvent event) throws IOException{

        Parent Home = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
}
