package werwolf.plugins.gui;

import java.awt.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SceneController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	@FXML private TilePane sonderRollenPane;
	@FXML private CheckBox c;

	
	String css;
	
	
	public void play(ActionEvent e) throws IOException {
		//TODO Play Button
		System.out.println("Play");
		root = FXMLLoader.load(getClass().getResource("fxml\\PlayMain.fxml"));
		css = getClass().getResource("fxml\\application.css").toExternalForm();
		
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		
		//baueCheckboxen();
		
		scene = new Scene(root);
		
		scene.getStylesheets().add(css);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void enzy(ActionEvent e) {
		//TODO Enzy Button
		System.out.println("Enzy");
	}
	

}
