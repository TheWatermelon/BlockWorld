package vue;

import modele.*;

public class Main {
	public static void main(String[] args) {
		BlockWorld bw1 = new BlockWorld("etatInitial.txt");
		BlockWorld bw2 = new BlockWorld("etatFinal.txt");
	
		bw1.printTable();
		System.out.println("Initial state\n");
		bw2.printTable();
		System.out.println("Final state\n");

		Astar su = new Astar(bw1, bw2, new HeuristiquePositionPlus());
		
		su.run();
		
	}
}