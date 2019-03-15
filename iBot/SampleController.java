package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.DepthTest;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SampleController implements Initializable{
	private BorderPane lay;
	public TextField tField;
	public TextArea tArea;
	public ImageView iv;
	public Button Sbutton;
	public Button btn,helpBtn;
	ArrayList<String> ans;
	int a;
	static int b=1;
	String str;

	public void initialize(URL location, ResourceBundle resources) {		//keep focus on tField as the program starts by default
	    Platform.runLater( () -> tField.requestFocus() );
	}

	public void textAction() throws Exception{
		b=1;
		if(tField.getText()!=null) {
		tArea.appendText("You: "+tField.getText()+"\n\r");
		Ibot ib=new Ibot();
		ans=ib.Handler(tField.getText());
		Timeline tl=new Timeline();
		System.out.println("    "+ans.get(0));
		KeyFrame[] kf=new KeyFrame[ans.size()+1];
		kf[0]=new KeyFrame(Duration.seconds(0.5),new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
		    	tArea.appendText("Ibot: "+ans.get(0)+"\n\r");
			} 
		});
		tl.getKeyFrames().add(kf[0]);

		for(a=1;a<ans.size();a++) {
			
			str=ans.get(a);
			
			kf[a]=new KeyFrame(Duration.seconds(1.5*a),new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					int j=b;
			    	tArea.appendText("        "+ans.get(j)+"\n\r");
					SampleController.b++;
				}
			}); 
			tl.getKeyFrames().add(kf[a]);
		}
		
		tField.setText(null);
		if(ans.get(0)=="ok bye") {
			tField.setDisable(true);
			kf[a]=new KeyFrame(Duration.seconds(2),new EventHandler<ActionEvent>() {			//wait 2 seconds before exiting the program
			public void handle(ActionEvent t) {
				Platform.exit();
			} 
		});
		tl.getKeyFrames().add(kf[a]);
		}
		tl.play();																		//playing timeline transition after all the keyframes are added

		}
	}
	public void scrollDown() {
		tArea.setScrollTop(Double.MAX_VALUE);
	}
	
	public void scrl() {												//Active the scroll down button, called on mouse moved in the Stage
		double hold=0,scrlMax=0;
		Image img=new Image(getClass().getResourceAsStream("ScrolldownFinal.png"));
		ImageView iv=new ImageView(img);
		iv.setFitWidth(20);
		iv.setFitHeight(20);
		iv.setDepthTest(DepthTest.ENABLE);
		btn.setPadding(Insets.EMPTY);
		btn.setGraphic(iv);
		hold=tArea.getScrollTop();										//this method returns the pixels it has been scrolled by from the bottom. it returns Double.MAX_VALUE if it is at the bottom and other value if it isn't  
		tArea.setScrollTop(Double.MAX_VALUE);							//setting it to Double.MAX_VALUE means it is set to the maximum possible i.e to the very bottom
		scrlMax=tArea.getScrollTop();									
/*
 * so we compare scrlMax with Double.MAX_VALUE which is true if the scroll bar was at the bottom so the condition in the following code
 * keep the button hidden. else if the scroll bar is above than it returns other value (after setting to Double.MAX_VALUE value already)
 * which tells us that it was not at the bottom so it is not equal and the button is shown
 */
		if(scrlMax==Double.MAX_VALUE) {									//basically setting the value to Double max value, the same is returned so the hold would be equal. 
			scrlMax=hold;												
		}																 
		tArea.setScrollTop(hold);										 
		try {																			
		if(hold!=scrlMax) {
			btn.setDisable(false);
			btn.setOpacity(1);
			btn.setVisible(true);
		}
		else {
			btn.setDisable(true);
			btn.setOpacity(0);
			btn.setVisible(false);
		}} catch (Exception e){

		    e.getCause().printStackTrace();
		}  
	}
	

	public void HelpPopUp() {											//Manages the help popup. called on help button
		File helpFile=new File("help.txt");
	Scanner input = null;
		try {
		input=new Scanner(helpFile);
	}
	catch(Exception e) {
		System.out.println("help.txt not found");
	}
		Stage pWindow=new Stage();
		TextArea pTextArea=new TextArea();
		for(;input.hasNext();) {
			pTextArea.appendText(input.nextLine()+"\n\r");
		}
		Scene pScene=new Scene(pTextArea,200,200);
		pTextArea.setEditable(false);
		pWindow.setScene(pScene);
		pWindow.initModality(Modality.APPLICATION_MODAL);
		pWindow.show();
		pWindow.setMaximized(false);
		pWindow.setResizable(false);
		pTextArea.setScrollTop(0);
	}
}
