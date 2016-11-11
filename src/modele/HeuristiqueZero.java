package modele;

/**
 * 
 * @author Bastien CHAPUSOT, Taric GANDI
 *
 */

public class HeuristiqueZero implements HeuristiqueAbstraite {
	@Override
	public String nom() { return "HeuristiqueZero"; }

	@Override
	public String description() { 
		return "L'heuristique zero renvoie 0 quel que soit l'etat initial.\n"+
		"Elle represente l'heuristique aveugle.";
	}

	@Override
	public int h(BlockWorld bw1, BlockWorld bw2) {
		return 0;
	}
}