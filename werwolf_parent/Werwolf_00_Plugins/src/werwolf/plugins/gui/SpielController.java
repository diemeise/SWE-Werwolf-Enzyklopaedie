package werwolf.plugins.gui;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import werwolf.plugins.gui.model.SpielerModel;

public class SpielController {

	//FXML Elemente
	@FXML private TableView spielerTabelle;
	@FXML private TableColumn colSpielername, colSpielerRolle, colSpielerLeben, colRollenFunktion;
	@FXML private Pane toeteSpielerPane;
	@FXML private Label spielTextLabel;
	private ObservableList<SpielerModel> spielerListe;
	private ArrayList<String> lebendigeSpieler;
	private Button btn;
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	String css;
	
	@FXML void initialize() {
		
		//Reihen vorbereiten für Tabelleneintraege
		colSpielername.setCellValueFactory(new PropertyValueFactory<>("name"));
		colSpielerRolle.setCellValueFactory(new PropertyValueFactory<>("rollenName"));
		colSpielerLeben.setCellValueFactory(new PropertyValueFactory<>("status"));
		colRollenFunktion.setCellValueFactory(new PropertyValueFactory<>("rollenFunktion"));
		//spielerTabelle.getItems().add();
		
		//TODO in eigene Methode
		//Spielerobjekte für die Tabelle vorbereiten
		spielerListe = FXCollections.observableArrayList();
		List<Map<String,String>> spielerStrings = GUIMain.outputAdapter.listeAlleSpieler();
		for (Map<String, String> map : spielerStrings) {
			String name = map.get("Spielername");
			String rollenName = map.get("Rollenname");
			String rollenFunktion = map.get("Rollenfunktion");
			String status =map.get("Status");
			SpielerModel s = new SpielerModel(name, rollenName, rollenFunktion, status);
			spielerListe.add(s);
		}
		spielerTabelle.setItems(spielerListe);
		 baueToeteButtons();
		
	}
	
	//TODO
	public void toeteSpieler(ActionEvent e) {
	
		String spielerName = ((Button) e.getSource()).getText();
		System.out.println(GUIMain.outputAdapter.eliminereSpieler(spielerName));
		
		for (SpielerModel spieler: spielerListe) {
			if(spieler.getName() == spielerName) {
				spieler.toeteSpieler();
			}
		}		
		spielerTabelle.refresh();
		
	}
	
	public void neachsterSpielSchritt(ActionEvent e) {
		String spielText= GUIMain.outputAdapter.neachsterSpielSchritt();
		Map<String,String> spielerMap = GUIMain.outputAdapter.getAktiverSpieler();
		System.out.println(spielText);
		spielTextLabel.setText("Aktiver Spieler:" + spielerMap.get("Spielername") + " (" + spielerMap.get("Rollenname") + ")");		
	}
	
	/**
	 * Beendet das Spiel über einen Button, User muss Beenden bestaetigen
	 * @param e
	 * @throws IOException
	 */
	public void beendeSpiel(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Sicher, dass du das Spiel abbrechen möchtest?");
		alert.setHeaderText("Spielabbruch");
		Optional<ButtonType> option = alert.showAndWait();
		
		if(option.get() == ButtonType.OK) {
			//Wieder auf den Startbildschirm
			root = FXMLLoader.load(getClass().getResource("fxml\\MainFX.fxml"));
			css = getClass().getResource("fxml\\application.css").toExternalForm();
			
			
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			
			scene = new Scene(root);
			
			scene.getStylesheets().add(css);
			
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	//TODO evtl. etwas komplexer aufbauen :P 
	public void baueToeteButtons() {
		
		int positionY = 10;
		lebendigeSpieler = new ArrayList<>(0);
		
		for (SpielerModel spieler : spielerListe) {
			String status = spieler.getStatus();
			if(status == "lebendig") {
				String name = spieler.getName();
				lebendigeSpieler.add(name);
			}
		}
		
		for(int i = 0; i < lebendigeSpieler.size(); i++) {
			btn = new Button(lebendigeSpieler.get(i));
			toeteSpielerPane.getChildren().add(btn);
			btn.setLayoutY(positionY);
			btn.setOnAction(this::toeteSpieler);
			positionY = (int) (positionY + 20);
		}
		
		
	}
	

}
