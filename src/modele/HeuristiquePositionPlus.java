package modele;

/**
 * 
 * @author Bastien CHAPUSOT, Taric GANDI
 *
 */

public class HeuristiquePositionPlus implements HeuristiqueAbstraite {
	@Override
	public String nom() { return "HeuristiquePositionPlus"; }

	@Override
	public String description() {
		return "L'heuristique position plus compte le nombre de blocks mal positionnes\n"+
		"et retire un coefficient si un block bien positionne fait partie d'une suite.\n"+
		"Autrement dit, plus il y a de blocks bien positionnes au sein d'une pile,\n"+
		"plus la valeur de l'heuristique sera faible.\n"+
		"Cette heuristique represente une heuristique mieux informee qu'HeuristiquePosition.\n"+
		"En utilisant cette heuristique, l'algorithme parcourt :\n"+
		" - 919 noeuds pour le premier coup;\n"+
		" - 3 noeuds pour le dernier coup;\n"+
		" - 120,8 noeuds en moyenne;\n"+
		" - 1691 noeuds en tout.\n"+
		"Le probleme est resolu en 14 coups.";
	}

	@Override
	public int h(BlockWorld bw1, BlockWorld bw2) {
		Block b1, b2;
		int coef=1, blocks=bw1.getBlocksCount();	// Initialisé au nombre de blocks de bw1

		for(int i=0; i<bw2.getTable().size(); i++) {	// Pour chaque pile de l'etat final
			// Cas pile vide de l'etat final
			if(bw2.getTable().get(i).size()==0) { continue; }
			b1 = bw2.getTable().get(i).get(0); // Le premier block de la pile de l'etat final
			for(int j=0; j<bw1.getTable().size(); j++) {	// Pour chaque pile de l'etat initial
				// Cas pile vide de l'etat initial
				if(bw1.getTable().get(j).size()==0) { continue; }
				if(bw1.getTable().get(j).get(0).isEqualTo(b1)) { // Si le premier block correspond alors
					coef=1;
					blocks-=coef;
 					b2 = bw2.up(b1);
 					while(b1!=null && b2!=null && bw1.on(b2, b1)) {	// Tant que b2 est au-dessus de b1 dans l'etat initial
 						// Le block est bien positionne
 						coef++;
						blocks-=coef;
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