package werwolf.plugins.gui;



import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class PlayController {
	
	@FXML private TextField name0, name1, name2, name3, name4, name5, name6,
													name7, name8, name9, name10, name11, name12, name13, name14;
	private TextField[] spielerNamenArray = new TextField[15];
	@FXML private TilePane sonderRollenPane;
	@FXML private ChoiceBox<Integer> spielerAnzahl;
	private CheckBox c;
	
	private Integer[] anzahl;
	
	@FXML void initialize() {
		
		baueCheckboxen();
		befuelleSpielerAnzahlArray();
		initTextfelder();
		
		spielerAnzahl.getItems().addAll(anzahl);
		spielerAnzahl.getSelectionModel().select(0);
		
		spielerAnzahl.setOnAction(this::getSpielerAnzahl);
		
		
	}
	
	public void getSpielerAnzahl(ActionEvent e) {
		int teilnehmende = spielerAnzahl.getValue();
		setzeSichtbareTextfelder(teilnehmende);
		
	}
	
	//TODO Mit richtigen Sonderrollen verbinden
	@FXML
	private void baueCheckboxen() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Weißer Werwolf");
		list.add("Super duper Bauer");
		list.add("Hexe");
		
		for(int i = 0; i < list.size(); i++) {
			 c = new CheckBox(list.get(i));
			
			sonderRollenPane.getChildren().add(c);
			c.setIndeterminate(false);
			
		}
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
	
	//TODO schoener umsetzen?
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
}


