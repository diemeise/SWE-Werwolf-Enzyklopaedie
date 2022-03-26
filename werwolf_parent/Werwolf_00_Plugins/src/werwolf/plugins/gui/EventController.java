package werwolf.plugins.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Arc;

public class EventController {
	
	@FXML
	private Arc pacman;
	private double x;
	
	
	public void right(ActionEvent e) {
		System.out.println("RIGHT");
		x = pacman.getCenterX();
		pacman.setCenterX(x+=10);
	}
	
	public void left(ActionEvent e) {
		System.out.println("LEFT");
		x = pacman.getCenterX();
		pacman.setCenterX(x-=10);
	}

}
