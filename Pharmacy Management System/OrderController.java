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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shahab
 */
public class OrderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public void Home(ActionEvent event) throws IOException{
        System.out.println("You clicked me!");
        Parent Home = FXMLLoader.load(getClass().getResource("FXML_Page4.fxml"));
        Scene Home_scene = new Scene(Home);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(Home_scene);
        app_stage.show();
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
