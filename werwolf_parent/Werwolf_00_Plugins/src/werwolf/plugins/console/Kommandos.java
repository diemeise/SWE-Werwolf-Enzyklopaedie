package werwolf.plugins.console;

import java.util.HashMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import werwolf.adapter.sql.OutputAdapter;
import werwolf.adapter.sql.SQLKartenRepository;




public enum Kommandos {
	
	//TODO: Liste alle Spezialrollen auf
	//TODO: Suche nach Name
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
		throw new Exception("Ungültiges Kommando!");
    }

	public boolean isRunning() {
        return isRunning;
    }
}
