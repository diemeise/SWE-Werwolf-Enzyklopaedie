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
	
	//TODO Liste alle guten Rollen auf
	//TODO Liste alle boesen Rollen auf 
	
    LIST_KARTEN("list-karten") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
            //System.out.println(":(");
    		Map<String, String> karten = new HashMap<>();
    		karten = out.getAlleKartenByFunktion();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_SPEZIAL("list-spezial") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String, String> karten = new HashMap<>();
    		karten = out.getAlleSpezialKarten();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_BOESE("list-boese") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		HashMap<String, String> karten = new HashMap<>();
    		karten = out.getAlleBoesenKarten();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_GUT("list-gut") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		HashMap<String, String> karten = new HashMap<>();
    		karten = out.getAlleGutenKarten();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    SUCHE_NAMEN("suche ([a-zA-Z]+)") {

		@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
			// TODO Auto-generated method stub
			String name = matcher.group(1);
			//System.out.println(name);
			
			HashMap<String, String> karten = new HashMap<>();
			karten = out.getKartenDetails(name);
			//karten.forEach((k,v) -> System.out.println(k+": "+v)); //Sad das ist natuerlich dann nicht sortiert :c
			
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
    GAME_START("starte-spiel ([^\s]*,?) ([^\\s]*,?)"){
		
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
    
    
    LIST_ALLE_SPIELER("alle-spieler"){
		
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
    
    LIST_GEWINNER("zeige-gewinner"){
		
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
    
    LIST_SPIELER("zeige-spieler ([^\s]*)"){
		
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
    
    LIST_AKTIVER_SPIELER("zeige-aktiver-spieler"){
		
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
    
    /**
     * gibt info über die aktuelle Phase
     * lebende Spieler am Start
     * aktueller Spieler
     * bereits eliminierte Spieler
     *TODO
     */
    LIST_PHASE("zeige-spielphase"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String,String> map = out.listSpielphase();
    		String str = "Nacht Nummer: " + map.get("Anzahl")  + "\t"
    						+ "Aktiver Spieler: " + map.get("Aktiver_Spieler") + "\t"
    						+"Phase abgeschlossen: " + map.get("Abgeschlossen");
    		System.out.println(str);
    	}    	
    },
    
    /**
     * zeige:
     * alle Spieler
     * aktuelle Phase
     * aktueller Spieler
     * 
     */
    GAME_STATS("spiel-uebersicht"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println("--Alle Spieler--");
    		LIST_ALLE_SPIELER.execute(null, out);
    		System.out.println("--Aktuelle Phase--");
    		LIST_PHASE.execute(null, out);
    		System.out.println("--Aktueller Spieler--");
    		LIST_AKTIVER_SPIELER.execute(null, out);
    		
    	}    	
    },
    
    GAME_END("stopp-spiel"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.beendeSpiel());
    	}    	
    },
  
  GAME_CONTINUE("spielschritt"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.neachsterSpielSchritt());
    	}    	
    },

 ELIMINIERE_SPIELER("kill ([^\s]*)"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println(out.eliminereSpieler(matcher.group(1)));
    	}    	
    },
 
    QUIT("quit") {
        @Override
        public void execute(MatchResult matcher, OutputAdapter out) {
            isRunning = false;
        }
    };


	private static boolean isRunning = true;
	
	
	private Pattern pattern;
	
	Kommandos(String pattern) {
    	this.pattern = Pattern.compile(pattern);
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
		throw new Exception("Ungueltiges Kommando!");
    }

	public boolean isRunning() {
        return isRunning;
    }
}
