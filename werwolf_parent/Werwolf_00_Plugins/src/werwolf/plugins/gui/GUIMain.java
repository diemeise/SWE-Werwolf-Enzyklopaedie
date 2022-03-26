package werwolf.plugins.gui;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUIMain extends Application {
	
	//Umlaute
	private static final char ue = '\u00FC';
	private static final char oe = '\u00F6';
	
	//FINALS
	private static final String STAGE_TITLE = "Werw" + oe + "lfe vom D" + ue + "sterwald!";
	private static final int STAGE_WIDTH = 1300;
	private static final int STAGE_HEIGHT = 760;
	
	//GUI Komponenten
	Parent root;
	Scene scene;
	Image icon;
	
	String css;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		root = FXMLLoader.load(getClass().getResource("MainFX.fxml"));
		css = getClass().getResource("application.css").toExternalForm();
		
		scene = new Scene(root);
		scene.getStylesheets().add(css);
		
		
		definiereStage(stage);
		
		stage.show();
	}
	
	/**
	 * Layout für die Stage der GUI
	 * @param stage
	 */
	public void definiereStage(Stage stage) {
		icon = new Image("pfote.png");
		
		stage.setTitle(STAGE_TITLE);
		stage.getIcons().add(icon);
		
		stage.setHeight(STAGE_HEIGHT);
		stage.setWidth(STAGE_WIDTH);
		
		stage.setScene(scene);
	}

}
