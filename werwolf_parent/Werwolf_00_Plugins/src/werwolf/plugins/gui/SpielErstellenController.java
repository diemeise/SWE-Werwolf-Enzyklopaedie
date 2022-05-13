package werwolf.plugins.gui;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SpielErstellenController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	String css;
	
	@FXML private TextField name0, name1, name2, name3, name4, name5, name6,
													name7, name8, name9, name10, name11, name12, name13, name14;
	private TextField[] spielerNamenArray = new TextField[15];
	@FXML private VBox cBox;
	@FXML private ChoiceBox<Integer> spielerAnzahlBox;
	private CheckBox c;
	
	private Integer[] anzahl;
	
	@FXML void initialize() {
		
		baueCheckboxen();
		befuelleSpielerAnzahlArray();
		initTextfelder();
		
		spielerAnzahlBox.getItems().addAll(anzahl);
		spielerAnzahlBox.getSelectionModel().select(0);
		
		spielerAnzahlBox.setOnAction(this::getSpielerAnzahl);
		
		
	}
	
	public void getSpielerAnzahl(ActionEvent e) {
		int teilnehmende = spielerAnzahlBox.getValue();
		setzeSichtbareTextfelder(teilnehmende);
		//NUR FUER TESTMODUS, SONST AUSKOMMENTIEREN
		testNamenEintragen(teilnehmende);		
	}
	
	public void starteSpiel(ActionEvent e) throws IOException {
		List<String> spielerNamen =  getSpielerNamen();
		List<String> rollen = getRollen(); 
		if(!(pruefeSpielerUndRollen(spielerNamen.size(), rollen.size()))) {
			Alert alert = new Alert(AlertType.INFORMATION, "Spieleranzahl oder Rollenanzahl nicht korrekt!");
			alert.showAndWait();
			return;
		}
		GUIMain.outputAdapter.starteSpiel(spielerNamen, rollen);
		
		//naechster Screeen
		root = FXMLLoader.load(getClass().getResource("fxml\\SpielScene.fxml"));
		css = getClass().getResource("fxml\\application.css").toExternalForm();
		
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		
		scene.getStylesheets().add(css);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private List<String> getRollen() {
		List<String> rollenNamen = new ArrayList<String>(0);
		rollenNamen.add("werwolf");
		rollenNamen.add("werwolf");
		//alle Checkboxen durchgehen (Panel hat nur checkboxen )
		ObservableList<Node> checkboxen = cBox.getChildren();
		for (Node node : checkboxen) {
			CheckBox checkbox = (CheckBox) node;
			if(checkbox.isSelected()) {
				rollenNamen.add(checkbox.getText());
			}
		}
		return rollenNamen;
	}

	//Spielernamen aus den sichtbaren Textfeldern speichern
	private List<String> getSpielerNamen() {
		List<String> spielerNamen = new ArrayList<String>(0);
		for (TextField t: spielerNamenArray) {
			if(t.isVisible()) {
				if(!(t.getText().isBlank())) {
					spielerNamen.add(t.getText());

				}
			}
		}
		return spielerNamen;
	}
	
	private boolean pruefeSpielerUndRollen(int spielerNamenAnzahl, int rollenAnzahl) {
		int spielerAnzahl = spielerAnzahlBox.getValue();
		if ((spielerAnzahl > spielerNamenAnzahl) || (spielerAnzahl <= rollenAnzahl)) {
			return false;
		}
		
		return true;
	}

	@FXML
	private void baueCheckboxen() {
		//Sonderrollen finden (Key: Name, Value: Funktion)
		Map<String,String> spezialKarten = GUIMain.outputAdapter.getAlleSpezialKarten();
		ArrayList<String> listeDerKarten = new ArrayList<String>( spezialKarten.keySet());
		
		
		for(int i = 0; i < listeDerKarten.size(); i++) {
			 c = new CheckBox(listeDerKarten.get(i));	
			 
			 
			cBox.getChildren().add(c);
			c.setIndeterminate(false);
			
			
		}
		
		cBox.setSpacing(9);
		
		
	}

	/**
	 * Befuellt das Array mit moeglicher Spieleranzahl
	 */
	private void befuelleSpielerAnzahlArray() {
		anzahl = new Integer[10];
		int spieler = 6;
		
		for(int i = 0; i< Array.getLength(anzahl) ; i++) {
			anzahl[i] = spieler;
			spieler++;
		}
			
	}
	
	private void initTextfelder() {
		//Arrayerzeugung mit = {e0, e1, e2} funktioniert nicht :(. liegt vmtl an FXML elementen
		spielerNamenArray[0] = name0;
		spielerNamenArray[1] = name1;
		spielerNamenArray[2] = name2;
		spielerNamenArray[3] = name3;
		spielerNamenArray[4] = name4;
		spielerNamenArray[5] = name5;
		spielerNamenArray[6] = name6;
		spielerNamenArray[7] = name7;
		spielerNamenArray[8] = name8;
		spielerNamenArray[9] = name9;
		spielerNamenArray[10] = name10;
		spielerNamenArray[11] = name11;
		spielerNamenArray[12] = name12;
		spielerNamenArray[13] = name13;
		spielerNamenArray[14] = name14;
		for (int i = 6; i < spielerNamenArray.length; i++) {
			spielerNamenArray[i].setVisible(false);
		}
	}

	private void setzeSichtbareTextfelder(int spieleranzahl) {		
		for(int feld = spieleranzahl-1; feld >= 0; feld-- ) {
			spielerNamenArray[feld].setVisible(true);
		}
		for(int feld = spieleranzahl; feld <spielerNamenArray.length; feld++ ) {
			spielerNamenArray[feld].setVisible(false);
		}
		
	}
	
	//%%%%%%TEST METHODE UM NICHT ALLE NAMEN TIPPEN ZU MUESSEN %%%%%%%
	public void testNamenEintragen(int spieleranzahl){
		for(int feld = spieleranzahl-1; feld >= 0; feld-- ) {
			spielerNamenArray[feld].setText("Testname" + feld);
		}
	}
}


