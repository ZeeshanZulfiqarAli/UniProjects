package application;

/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class FXML_Page4Controller implements Initializable {

	public Button sellBtn,searchBtn;
	public TextField med,quantity;
	public Label warningLbl;
	public TableView<med_unit> tbl;
	public TableColumn id,name,comp,exp,up,quan;
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	if (Main.p_id==null) {
    		//SignOut();
    	}
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	comp.setCellValueFactory(new PropertyValueFactory<>("comp"));
    	exp.setCellValueFactory(new PropertyValueFactory<>("exp"));
    	up.setCellValueFactory(new PropertyValueFactory<>("up"));
    	quan.setCellValueFactory(new PropertyValueFactory<>("quan"));
    }
    public void getTbl(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
    	String query="select * from pharmacy.dbo.medicine_unit where name='"+med.getText()+"'";
    	
    	
		try(PreparedStatement pstmt = Main.con.prepareStatement(query);) {
	        ResultSet rs = pstmt.executeQuery();
	        if(!rs.isBeforeFirst()) {
	            //System.out.println(rs.getString("p_id"));
	            //System.out.println("no data");
	            warningLbl.setText("No such medicine found!");
	            warningLbl.setVisible(true);
	            //Main.p_id=rs.getString("p_id");
	        }		
	        else {
	        	//show data in table
	        	
	        	while(rs.next()){
	        		tbl.getItems().add(new med_unit(rs.getInt("U_id"),rs.getString("name"),rs.getString("company"),rs.getString("exp_date"),rs.getInt("quantity"),rs.getFloat("u_price")));
	        	}
	        }
	    }
	    // Handle any errors that may have occurred.
	    catch (SQLException e) {
	        e.printStackTrace();
	    }

    }
    
    public void remove(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
    	int requestedQuantity;
    	String query;
        ObservableList<med_unit> unitSelected, allUnits;
        allUnits = tbl.getItems();
        unitSelected = tbl.getSelectionModel().getSelectedItems();
        if(unitSelected.isEmpty()) {	//if no row selected
        	warningLbl.setText("Please select the row");
        	warningLbl.setVisible(true);
        }
        else if(quantity.getText().isEmpty() || quantity.getText()==null) {  //if no quantity provided
        	warningLbl.setText("Please enter the quantity");
        	warningLbl.setVisible(true);
	        }
        else {
        	//System.out.println("xyz");
	        requestedQuantity=Integer.parseInt(quantity.getText());
	        //System.out.println(requestedQuantity);
	        int s=unitSelected.size();
	        med_unit u;
	        for(int count=0;count<s;count++ ) {
	        	u=unitSelected.get(count);
	        	System.out.println(u.getQuan()-requestedQuantity);
	        	if(requestedQuantity==u.getQuan()) {	//delete whole record if selling all the stock
	        		
		        	query ="delete from pharmacy.dbo.medicine_unit where U_id="+u.getId()+"";
	
					try{
						PreparedStatement pstmt = Main.con.prepareStatement(query);
						pstmt.execute();
					}
				    // Handle any errors that may have occurred.
				    catch (SQLException e) {
				    	
				        e.printStackTrace();
				    }
	
		       	} else if(requestedQuantity<u.getQuan()) {
		       		//System.out.println("id: "+u.getId());
	        		query ="update pharmacy.dbo.medicine_unit set quantity="+(u.getQuan()-requestedQuantity)+" where U_id="+u.getId()+";";
	        		//System.out.println(u.getQuan()-requestedQuantity);
	    			try{
	    				PreparedStatement pstmt = Main.con.prepareStatement(query);
	    				pstmt.execute();
	    			}
	    			
	    		    // Handle any errors that may have occurred.
	    		    catch (SQLException e) {
	    		    	
	    		        e.printStackTrace();
	    		    }
	
		       	} else {
	        		warningLbl.setText("Requested quantity larger than stock!");
	        		warningLbl.setVisible(true);
	        	}
	        	unitSelected.forEach(allUnits::remove);
	        }
	        }
    }
    
     public void SignOut(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
    	Main.p_id=null;
        Parent Home = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
        
    }
      public void Medicne(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("Medicine.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
       public void AddProduct(ActionEvent event) throws IOException{
        //System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
}
