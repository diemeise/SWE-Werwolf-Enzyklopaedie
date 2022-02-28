package werwolf.plugins.console;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public enum Kommandos {
	
    LIST_KARTEN("list-karten") {
    	@Override
        public void execute(MatchResult matcher) {
            System.out.println(":(");
        }
    },
    	
   QUIT("quit") {
        @Override
        public void execute(MatchResult matcher) {
            isRunning = false;
        }
    };


	private static boolean isRunning = true;
	private Pattern pattern;
	
    Kommandos(String pattern) {
    	this.pattern = Pattern.compile(pattern);
	}
    
    public abstract void execute(MatchResult matcher);
    
    public static Kommandos executeMatching(String input) throws Exception {
        for (Kommandos command : Kommandos.values()) {
            Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                command.execute(matcher);
                return command;
            }
        }
		throw new Exception("Ungültiges Kommando!");
    }

	public boolean isRunning() {
        return isRunning;
    }
}
