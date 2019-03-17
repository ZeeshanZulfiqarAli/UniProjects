package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    public TextField uName,pass;
    
    @FXML
    private void handleSignin(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
    	boolean flag=false;
    	String query="select p_id from pharmacy.dbo.pharmacist where username='"+uName.getText()+"' and pass_hash='"+pass.getText().hashCode()+"'";
    	
    	
		try(PreparedStatement pstmt = Main.con.prepareStatement(query);) {
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            //System.out.println(rs.getString("p_id"));
	            Main.p_id=rs.getString("p_id");
	            if(Main.p_id==null) {
	            	//do something
	            }else { flag=true;}
	        }		
	    }
	    // Handle any errors that may have occurred.
	    catch (SQLException e) {
	        e.printStackTrace();
	    }

    	if(flag) {
	        Parent Home = FXMLLoader.load(getClass().getResource("FXML_Page4.fxml"));
	        Scene Home_scene = new Scene(Home);
	        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        app_stage.setScene(Home_scene);
	        app_stage.show();
	   	}
    }
    
    public void handleSignup(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }

    

    public void initialize(URL url, ResourceBundle rb) {
        // TODO check if logged in
    	//Main.loggedIn()
    	Main.p_id=null;
    } 
}
