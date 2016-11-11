package vue;

/**
 * 
 * @author Bastien CHAPUSOT, Taric GANDI
 *
 */

import modele.*;

public class Main {
	/**
	 * Point d'entree de la JVM
	 * @param args : la liste des arguments de la ligne de commandes
	 */
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