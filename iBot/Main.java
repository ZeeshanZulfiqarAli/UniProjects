package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;


public class Main extends Application {

	Scene scene1,scene2;
	static public void main(String args[]) {
		launch(args);	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{

		Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		primaryStage.setTitle("Ibot");
		BorderPane layout=new BorderPane();
		BorderPane bp=new BorderPane();
		ImageView pic=new ImageView();
		pic.setImage(new Image(Main.class.getResourceAsStream("logo3.png")));
		Label l=new Label("Click here to continue");
		l.setOpacity(0.5);
		pic.setPreserveRatio(true);
		pic.setFitWidth(380);
		layout.setPadding(new Insets(10,5,100,5));
		layout.setCenter(pic);
		bp.setCenter(l);
		layout.setBottom(bp);
		scene1=new Scene(layout, 400, 600);
		primaryStage.setScene(scene1);								//intro scene
	    scene2=new Scene(root, 400, 600);							//Chat layout
	    l.setOnMouseClicked(e -> primaryStage.setScene(scene2));
		primaryStage.show();
	}
}
