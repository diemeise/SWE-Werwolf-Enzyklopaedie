package werwolf.plugins.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class StartseiteController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	@FXML private TilePane sonderRollenPane;
	@FXML private CheckBox c;

	
	String css;
	
	
	public void play(ActionEvent e) throws IOException {
		System.out.println("Play");
		root = FXMLLoader.load(getClass().getResource("fxml\\SpielErstellen.fxml"));
		css = getClass().getResource("fxml\\application.css").toExternalForm();
		
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		
		//baueCheckboxen();
		
		scene = new Scene(root);
		
		scene.getStylesheets().add(css);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void enzy(ActionEvent e) {
		System.out.println("Enzy");
	}
	

}
