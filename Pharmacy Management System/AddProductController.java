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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController implements Initializable {

	public TextField name,gname,up,comp,exp,quantity;

     public void Home(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("FXML_Page4.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
     
     public void add(ActionEvent event) throws IOException{
         //System.out.println("You clicked me!");
    	 int m_id=0;
    	 String query="insert into pharmacy.dbo.medicine(generic_name,brand_names,companies,quantity,last_exp_date) OUTPUT INSERTED.m_id values('"+gname.getText()+"','"+name.getText()+"','"+comp.getText()+"',"+quantity.getText()+",'"+exp.getText()+"')";
    	 
     	
    	 try(PreparedStatement pstmt = Main.con.prepareStatement(query);){
     		ResultSet rs =pstmt.executeQuery();
     		rs.next();
     		m_id = rs.getInt("m_id");
     	}
  	    catch (SQLException e) {
 	        e.printStackTrace();
 	    }

     	//query
    	query="insert into pharmacy.dbo.medicine_unit(name,company,exp_date,u_price,quantity,m_id) values('"+name.getText()+"','"+comp.getText()+"','"+exp.getText()+"','"+up.getText()+"','"+quantity.getText()+"','"+m_id+"')";
     	//ResultSet rs = connectivity.executeStatement(Main.con, query);
 		try(PreparedStatement pstmt = Main.con.prepareStatement(query);) {
 	        pstmt.execute();
 	        Parent Home = FXMLLoader.load(getClass().getResource("FXML_Page4.fxml"));
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

    	 //successfully added
     //}
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
