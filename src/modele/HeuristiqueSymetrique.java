package modele;

/**
 * 
 * @author Bastien CHAPUSOT, Taric GANDI
 *
 */

import java.util.*;

public class HeuristiqueSymetrique implements HeuristiqueAbstraite {
	@Override
	public String nom() { return "HeuristiqueSymetrique"; }

	@Override
	public String description() {
		return "L'heuristique symetrique compare les piles des deux tables\n"+
		"comme si elles etaient calquees les unes sur les autres.\n"+
		"Les piles doivent donc etre identiques et a la meme position\n"+
		"pour que deux etats soient egaux";
	}

	@Override
	public int h(BlockWorld bw1, BlockWorld bw2) {
		int blocks=0;
		for(int i=0; i<bw1.getTable().size(); i++){	// Pour chaque pile de la table
			Stack<Block> s=bw1.getTable().get(i);	// On recupere la pile de l'etat initial
			Stack<Block> ss=bw2.getTable().get(i);	// On recupere la pile de l'etat recherche
			for(int j=0; j<Math.max(s.size(),ss.size()); j++){	// Pour chaque block de la pile la plus haute
				if(Math.max(s.size(),ss.size())==s.size()){	// Si la pile la plus haute est la pile de l'etat initial
					if(j<ss.size()){	// Si on peut comparer avec un block de l'autre pile (meme hauteur de pile)
						if(!s.get(j).isEqualTo(ss.get(j))){
							blocks++;
						}
					}else{	
						// Puisqu'on est sur la pile de l'etat initial, 
						// s'il reste des blocks sur cette pile est qu'il n'y a plus de blocks sur la pile de l'etat recherche,
						// on considere que tous les blocks restants sur cette pile sont mal places
						blocks++;
					}
				}else{	// Si la pile la plus haute est la pile de l'etat recherche
					if(j<s.size()){	// Si on peut comparer avec un block de l'autre pile
						if(!s.get(j).isEqualTo(ss.get(j))){
							blocks++;
						}
					}
					// S'il n'y a plus de blocks sur la pile initiale, on ne fait rien
				}
			}
		}
		return blocks;
	}
}