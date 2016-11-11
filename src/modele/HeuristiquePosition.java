package modele;

/**
 * 
 * @author Bastien CHAPUSOT, Taric GANDI
 *
 */

public class HeuristiquePosition implements HeuristiqueAbstraite {
	@Override
	public String nom() { return "HeuristiquePosition"; }

	@Override
	public String description() {
		return "L'heuristique position compte le nombre de blocks mal positionnes\n"+
		"de l'etat initial par rapport a l'etat final.\n"+
		"L'ordre des piles n'a pas d'importance.\n"+
		"En utilisant cette heuristique, l'algorithme parcourt :\n"+
		" - 1457 noeuds pour le premier coup;\n"+
		" - 3 noeuds pour le dernier coup;\n"+
		" - 678,6 noeuds en moyenne;\n"+
		" - 9500 noeuds en tout.\n"+
		"Le probleme est resolu en 14 coups.";
	}

	@Override
	public int h(BlockWorld bw1, BlockWorld bw2) {
		Block b1, b2;
		int blocks=bw1.getBlocksCount();	// Initialisé au nombre de blocks de bw1

		for(int i=0; i<bw2.getTable().size(); i++) {	// Pour chaque pile de l'etat final
			// Cas pile vide de l'etat final
			if(bw2.getTable().get(i).size()==0) { continue; }
			b1 = bw2.getTable().get(i).get(0); // Le premier block de la pile de l'etat final
			for(int j=0; j<bw1.getTable().size(); j++) {	// Pour chaque pile de l'etat initial
				// Cas pile vide de l'etat initial
				if(bw1.getTable().get(j).size()==0) { continue; }
				if(bw1.getTable().get(j).get(0).isEqualTo(b1)) { // Si le premier block correspond alors
					blocks--;
 					b2 = bw2.up(b1);
 					while(b1!=null && b2!=null && bw1.on(b2, b1)) {	// Tant que b2 est au-dessus de b1 dans l'etat initial
 						// Le block est bien positionne
 						blocks--;
 						// on monte d'un niveau
 						b1 = b2;
 						b2 = bw2.up(b1);
 					}
					break;
				}
			}
		}
		return blocks;
	}
}