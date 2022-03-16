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
	//TODO Liste alle bösen Rollen auf 
	
    LIST_KARTEN("list-karten") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
            //System.out.println(":(");
    		HashMap<String, String> karten = new HashMap<>();
    		karten = out.getAlleKartenByFunktion();
    		karten.forEach((k,v) -> System.out.println(k+": "+v));
        }
    },
    
    LIST_SPEZIAL("list-spezial") {
    	@Override
        public void execute(MatchResult matcher, OutputAdapter out) {
    		HashMap<String, String> karten = new HashMap<>();
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
			//karten.forEach((k,v) -> System.out.println(k+": "+v)); //Sad das ist natürlich dann nicht sortiert :c
			
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
    
    
    LIST_ALLE_SPIELER("zeige-alle-spieler"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		System.out.println("Vorhandene Spieler:");
    		List<Map<String,String>> spielerList = out.listeAlleSpieler();
    		for (Map<String, String> spielerMap : spielerList) {
				String str = "Spieler "
						+ spielerMap.get("Spielername")
						+ "(" + spielerMap.get("Rollenname") + "): "
						+ spielerMap.get("Rollenfunktion");
				System.out.println(str);
			}
		}
    	
    },
    
    ZEIGE_SPIELER("zeige-spieler ([^\s]*)"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String,String> spielerMap = out.getDetailsOfSpieler(matcher.group(1));
    		if(spielerMap.containsKey("Error")) {
    			System.out.println(spielerMap.get("Error"));
    			return;
    		}
    		String str = "Spieler "
					+ spielerMap.get("Spielername")
					+ "(" + spielerMap.get("Rollenname") + "): "
					+ spielerMap.get("Rollenfunktion");
			System.out.println(str);
		}
    	
    },
    
    ZEIGE_AKTIVEN_SPIELER("zeige-aktiver-spieler"){
		
    	@Override
		public void execute(MatchResult matcher, OutputAdapter out) {
    		Map<String,String> spielerMap = out.getAktivenSpieler();
    		if(spielerMap.containsKey("Error")) {
    			System.out.println(spielerMap.get("Error"));
    			return;
    		}
    		String str = "Spieler "
					+ spielerMap.get("Spielername")
					+ "(" + spielerMap.get("Rollenname") + "): "
					+ spielerMap.get("Rollenfunktion");
			System.out.println(str);
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
		throw new Exception("Ungültiges Kommando!");
    }

	public boolean isRunning() {
        return isRunning;
    }
}
