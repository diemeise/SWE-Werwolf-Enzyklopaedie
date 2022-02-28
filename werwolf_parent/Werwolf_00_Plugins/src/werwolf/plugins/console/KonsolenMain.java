package werwolf.plugins.console;

import java.util.Scanner;

public class KonsolenMain {
	public static void main(String[] args) {     
		
		Scanner sc = new Scanner(System.in);
        Kommandos comm = null;
        do {
            try {
				comm = Kommandos.executeMatching(sc.nextLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
        } while (comm == null || comm.isRunning());
        sc.close();
    }
}
