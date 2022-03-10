package werwolf.plugins.console;

import java.util.HashMap;
import java.util.Scanner;

import werwolf.adapter.sql.OutputAdapter;
import werwolf.adapter.sql.SQLKartenRepository;

public class KonsolenMain {
	
	public static void exeKonsole(OutputAdapter outputAdapter) {
		Scanner sc = new Scanner(System.in);
        Kommandos comm = null;
      
        
        do {
            try {
				comm = Kommandos.executeMatching(sc.nextLine(), outputAdapter);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } while (comm == null || comm.isRunning());
        sc.close();
	}
}
