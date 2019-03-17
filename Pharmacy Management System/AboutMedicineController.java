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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class AboutMedicineController implements Initializable {

	public TableView<medicine> tbl;
	public TableColumn m_id,gname,bname,comp,exp;
	
     public void Home(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("FXML_Page4.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
     
     
     public void back(ActionEvent event) throws IOException{
         //System.out.println("You clicked me!");
         Parent Home = FXMLLoader.load(getClass().getResource("Medicine.fxml"));
         Scene Home_scene = new Scene(Home);
         Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         app_stage.setScene(Home_scene);
         app_stage.show();
     }

     
    public void initialize(URL url, ResourceBundle rb) {
        String query = MedicineController.query;
        System.out.println(query);
        
        m_id.setCellValueFactory(new PropertyValueFactory<>("m_id"));
        gname.setCellValueFactory(new PropertyValueFactory<>("Generic_name"));
        bname.setCellValueFactory(new PropertyValueFactory<>("Brand_Name"));
        comp.setCellValueFactory(new PropertyValueFactory<>("comp"));
        exp.setCellValueFactory(new PropertyValueFactory<>("exp"));
        
        
        try(PreparedStatement pstmt = Main.con.prepareStatement(query);) {
	        ResultSet rs = pstmt.executeQuery();
	        if(!rs.isBeforeFirst()) {
	            //System.out.println(rs.getString("p_id"));
	            //System.out.println("no data");
	            //warningLbl.setText("No such medicine found!");
	            //warningLbl.setVisible(true);
	            //Main.p_id=rs.getString("p_id");
	        }		
	        else {
	        	//show data in table
	        	
	        	while(rs.next()){
	        		tbl.getItems().add(new medicine(rs.getInt("m_id"),rs.getString("generic_name"),rs.getString("brand_names"),rs.getString("companies"),rs.getString("last_exp_date")));
	        		//System.out.println(rs.getString("generic_name"));
	        	}
	        }
	    }
	    // Handle any errors that may have occurred.
	    catch (SQLException e) {
	        e.printStackTrace();
	    }

    }    
    
}
