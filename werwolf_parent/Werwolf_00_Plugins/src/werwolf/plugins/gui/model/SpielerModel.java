package werwolf.plugins.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SpielerModel {

	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty rollenName = new SimpleStringProperty();
	private StringProperty rollenFunktion = new SimpleStringProperty();
	private StringProperty status = new SimpleStringProperty();
	
	public SpielerModel(String name, String rollenName, String rollenFunktion, String status) {
		super();
		this.name.set(name);
		this.rollenName.set(rollenName);
		this.rollenFunktion.set(rollenFunktion);
		this.status.set(status);
	}

	public String getName() {
		return name.get();
	}

	public String getRollenName() {
		return rollenName.get();
	}

	public String getRollenFunktion() {
		return rollenFunktion.get();
	}

	public String getStatus() {
		return status.get();
	}
	
	public void toeteSpieler() {
		this.status.set("verstorben");
	}
	
	public void belebeSpieler() {
		this.status.set("lebendig");
	}
	
	
	
}
