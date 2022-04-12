package werwolf.plugins.gui;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import werwolf.plugins.gui.model.SpielerModel;

public class SpielController {

	//FXML Elemente
	@FXML private TableView spielerTabelle;
	@FXML private TableColumn colSpielername, colSpielerRolle, colSpielerLeben, colRollenFunktion;
	private ObservableList<SpielerModel> spielerListe;
	
	@FXML void initialize() {
		
		//Reihen vorbereiten für Tabelleneintraege
		colSpielername.setCellValueFactory(new PropertyValueFactory<>("name"));
		colSpielerRolle.setCellValueFactory(new PropertyValueFactory<>("rollenName"));
		colSpielerLeben.setCellValueFactory(new PropertyValueFactory<>("status"));
		colRollenFunktion.setCellValueFactory(new PropertyValueFactory<>("rollenFunktion"));
		//spielerTabelle.getItems().add();
		
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
		
	}
	
	
	public void spielertothihi(ActionListener e) {
		//TODO 
	}
	

}
