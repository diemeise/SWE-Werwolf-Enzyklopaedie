package werwolf.plugins.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class SceneController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	
	public void play(ActionEvent e) throws IOException {
		//TODO Play Button
		System.out.println("Play");
		root = FXMLLoader.load(getClass().getResource("PlayMain.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void enzy(ActionEvent e) {
		//TODO Enzy Button
		System.out.println("Enzy");
	}

}
