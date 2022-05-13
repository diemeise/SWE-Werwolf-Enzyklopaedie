package werwolf.plugins.console;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import werwolf.adapter.output.OutputAdapter;




public enum Kommandos {
	
	
    LIST_KARTEN("list-karten", "list-karten", "Gibt eine Liste aller Karten aus.") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String, String> karten = new HashMap<>();
    		karten = out.getAlleKartenByFunktion();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_SPEZIAL("list-spezial", "list-spezial", "Gibt eine Liste aller speziellen Karten aus") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String, String> karten = new HashMap<>();
    		karten = out.getAlleSpezialKarten();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_BOESE("list-boese", "list-boese","Gibt eine Liste aller boesen Karten aus") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String, String> karten = new HashMap<>();
    		karten = out.getAlleBoesenKarten();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_GUT("list-gut", "list-gut", "Gibt eine Liste aller guten Karten aus") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String, String> karten = new HashMap<>();
    		karten = out.getAlleGutenKarten();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    SUCHE_NAMEN("suche ([a-zA-Z]+)", "suche <Kartenname>", "gibt Karten aus, deren Name dem <Kartenname> entspricht") {

		@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
			String name = matcher.group(1);
			
			HashMap<String, String> karten = new HashMap<>();
			karten = out.getKartenDetails(name);
			
			String n = karten.get("Name");
			String f = karten.get("Funktion");
			String b = karten.get("Beschreibung");
			String g = karten.get("Gesinnung");
			String s = karten.get("Spezialrolle");
			
			System.out.println("Name: " + n);
			System.out.println("Funktion: " + f);
			System.out.println("Beschreibung: " + b);
			System.out.println("Gesinnung: " + g);
			System.out.println("Spezialrolle: " + s);
			
		}
    	
    },
    	
   // ###################Spiel-Funktionen###################
    GAME_START("starte-spiel ([^\s]*,?) ([^\\s]*,?)", "starte-spiel <spieler1>,<spieler2,.. <rolle1>,<rolle2>,..", "startet ein Spiel mit den angegebenen Spielernamen und den übergebenen Rollen. "
    					+ "	Sind mehr Spieler als Rollen angegeben, wird der Rest als Dorfbewohner deklariert."){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		String errorString = "Syntax: starte-spiel [spielername],[spielername2],[...] [rollenName],[rollenname2],[...]";
			
    		//Namen und Rollen in Listen isolieren
    		try {
    			List<String> spielerNamen = Arrays.asList(matcher.group(1).split(","));
    			List<String> rollenNamen = Arrays.asList(matcher.group(2).split(","));
    			System.out.println(out.starteSpiel(spielerNamen, rollenNamen));    			
    		}catch (Exception e) {
				e.printStackTrace();
				System.out.println(errorString);
			}
		}
    },
    
    
    LIST_ALLE_SPIELER("alle-spieler", "alle-Spieler", "Gibt eine Uebersicht aller Spieler und ihrer Rollen aus"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println("Vorhandene Spieler:");
    		List<Map<String,String>> spielerList = out.listeAlleSpieler();
    		for (Map<String, String> spielerMap : spielerList) {
				String str = "Spieler "
						+ spielerMap.get("Spielername")
						+ "(" + spielerMap.get("Rollenname") + "):\t"
						+ spielerMap.get("Rollenfunktion")  + "\t"
						+ "Status : " + spielerMap.get("Status");
				System.out.println(str);
			}
		}
    	
    },
    
    LIST_GEWINNER("zeige-gewinner", "zeige-gewinner", "zeigt die Gewinner des letzten Spiels an"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println("Gewinner:");
    		List<Map<String,String>> spielerList = out.listGewinner();
    		for (Map<String, String> spielerMap : spielerList) {
				String str = "Spieler "
						+ spielerMap.get("Spielername")
						+ "(" + spielerMap.get("Rollenname") + "):\t"
						+ spielerMap.get("Rollenfunktion")  + "\t"
						+ "Status : " + spielerMap.get("Status");
				System.out.println(str);
			}
		}
    	
    },
    
    LIST_SPIELER("zeige-spieler ([^\s]*)", "zeige-spieler <spielername>", "zeigt Details zu einem Spielernamen an"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String,String> spielerMap = out.getDetailsOfSpieler(matcher.group(1));
    		if(spielerMap.containsKey("Error")) {
    			System.out.println(spielerMap.get("Error"));
    			return;
    		}
    		String str = "Spieler "
					+ spielerMap.get("Spielername")
					+ "(" + spielerMap.get("Rollenname") + "):\t"
					+ spielerMap.get("Rollenfunktion") + "\t"
					+ "\tStatus : " + spielerMap.get("Status");
			System.out.println(str);
		}
    	
    },
    
    
    LIST_AKTIVER_SPIELER("zeige-aktiver-spieler", "zeige-aktiver-spieler", "Zeigt den aktuelle Spieler und seine Rolle an"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String,String> spielerMap = out.getAktiverSpieler();
    		if(spielerMap.containsKey("Error")) {
    			System.out.println(spielerMap.get("Error"));
    			return;
    		}
    		String str = "Spieler "
					+ spielerMap.get("Spielername")
					+ "(" + spielerMap.get("Rollenname") + "): "
					+ spielerMap.get("Rollenfunktion")
					+ "Status : " + spielerMap.get("Status");
			System.out.println(str);
		}    	
    },
    

    LIST_PHASE("zeige-spielphase", "zeige-spielphase", "gibt Informationen zur aktuellen Spielphase an"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String,String> map = out.listSpielphase();
    		String str = "Nacht Nummer: " + map.get("Anzahl")  + "\t"
    						+ "Aktiver Spieler: " + map.get("Aktiver_Spieler") + "\t"
    						+"Phase abgeschlossen: " + map.get("Abgeschlossen");
    		System.out.println(str);
    	}    	
    },
    
    LIST_BUERGERMEISTER("buergermeister", "buergermeister", "zeigt den aktuellen Buergermeister an"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.getBuergermeister());
    	}    	
    },
    
    /**
     * zeige:
     * alle Spieler
     * aktuelle Phase
     * aktueller Spieler
     * 
     */
    GAME_STATS("spiel-uebersicht", "spiel-uebersicht", "zeigt eine Uebersicht des Spiels mit aktueller Phase, allen Spielern und weiteren Infos"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println("--Alle Spieler--");
    		LIST_ALLE_SPIELER.execute(null, out);
    		System.out.println("--Aktuelle Phase--");
    		LIST_PHASE.execute(null, out);
    		System.out.println("--Aktueller Spieler--");
    		LIST_AKTIVER_SPIELER.execute(null, out);
    		System.out.println("--Aktueller Buergermeister--");
    		LIST_BUERGERMEISTER.execute(null, out);
    		
    	}    	
    },
    
 GAME_STATUS("spiel-status","spiel-status", "Zeigt den aktuellen Status des Spiels"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.getSpielStatus());
    	}    	
    },
    
    GAME_END("stopp-spiel","stopp-spiel", "stoppt ein laufendes Spiel"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.beendeSpiel());
    	}    	
    },
  
  GAME_CONTINUE("spielschritt", "spielschritt", "fuert ein Spielschritt aus"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.neachsterSpielSchritt());
    	}    	
    },

  ELIMINIERE_SPIELER("kill ([^\s]*)", "kill <spielername>", "eliminiert den angegebenen Spieler"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.eliminereSpieler(matcher.group(1)));
    	}    	
    },
 
  NEUER_BUERGERMEISTER("waehle ([^\s]*)", "waehle <spielername>", "waehlt einen neuen Buergermeister"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.setBuergermeister(matcher.group(1)));
    	}    	
    },
 
    QUIT("quit", "quit", "beendet das Programm") {
        @Override
        public void execute(MatchResult matcher, OutputAdapter out) {
            isRunning = false;
        }
    },
	
	 
    HILFE("hilfe", "hilfe", "zeigt diese Uebersicht") {
        @Override
        public void execute(MatchResult matcher, OutputAdapter out) {
            for (Kommandos k : Kommandos.values()) {
				System.out.println(k.getKommando()
						+ "\t" + "|" + "\t"
						+ k.getBeschreibung()
						+ System.lineSeparator());
			}
        }
    };


	private static boolean isRunning = true;
	
	
	private Pattern pattern;
	private String kommando;
	private String beschreibung;
	
	Kommandos(String pattern, String kommando, String beschreibung) {
    	this.pattern = Pattern.compile(pattern);
    	this.kommando = kommando;
    	this.beschreibung = beschreibung;
	}
	
	
    
   
    
    public abstract void execute(MatchResult matcher, OutputAdapter out);
    
    public static Kommandos executeMatching(String input, OutputAdapter out) throws Exception {
    	
    	
    	for (Kommandos command : Kommandos.values()) {
            Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                command.execute(matcher, out);
                return command;
            }
        }
    	System.out.println("Kommando Unbekannt!");
    	System.out.println("Fuer eine Uebersicht aller Kommandos <hilfe> eingeben");
    	return null;
    }

	public boolean isRunning() {
        return isRunning;
    }





	public Pattern getPattern() {
		return pattern;
	}





	public String getKommando() {
		return kommando;
	}





	public String getBeschreibung() {
		return beschreibung;
	}
}
