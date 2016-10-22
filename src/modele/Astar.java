package modele;

import java.util.*;

public class Astar {
	protected ArrayList<BlockWorld> open;
	protected ArrayList<BlockWorld> closed;
	protected BlockWorld first;
	protected BlockWorld last;
	
	/**
	 * Constructeur logique
	 * @param init : l'etat initial
	 * @param term : l'etat final
	 */
	public Astar(BlockWorld init, BlockWorld term) {
		this.first = init;
		this.last = term;
	}
	
	/**
	 * Algorithme de A*
	 */
	public BlockWorld algorithm() {
		// Initialisation
		this.open = new ArrayList<BlockWorld>();
		this.closed = new ArrayList<BlockWorld>();
		this.open.add(this.first);
		int h = h(this.first, this.last);
		BlockWorld current = this.first;
		
		// Traitement
		while(this.open.size()!=0) {
			//current.printTable();
			// x <- arg min(xEO)(f(x))
			for(BlockWorld x : this.open) {
				System.out.println("h to compare : "+h(x, this.last)+" < "+h);
				//System.out.println("g : "+g(this.first, x));
				if(h(x, this.last)/*+g(this.first, x)*/ <= h) {
					x.printTable();
					System.out.println("minimum");
					h = h(x, this.last)/*+g(this.first, x)*/;
					current = x;
				}
				//x.printTable();
			}
			if(current.isEqualTo(this.last)) { continue; }
			// if x n'est pas le noeud final
			this.open.remove(current);
			this.closed.add(current);
			// for all y E successeurs(x)
			for(BlockWorld y : current.next()) {
				// if(y n'appartient pas aux fermes) ET (y n'appartient pas aux ouverts)
				if(this.closed.indexOf(y)==-1 && (this.open.indexOf(y)==-1/* || g(this.first, y) > g(this.first, current)+1*/)) {
					this.open.add(y);
				}
			}
		}
		
		// Sortie
		return current;
	}
	
	/**
	 * Fonction heuristique de l'etat initial pour l'etat final (donnes dans le constructeur logique)
	 */
	public int h(){
		return h(this.first, this.last);
	}
	
	/**
	 * Fonction heuristique qui calcule la valeur d'un etat initial par rapport a un etat final
	 * compte le nombre de blocks mal places
	 * @param bw1 : l'etat initial
	 * @param bw2 : l'etat final
	 */
	protected int h(BlockWorld bw1,BlockWorld bw2){
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
	
	/* Stack Overflow
	public int g() {
		return g(this.first, this.last);
	}
	*/
	
	protected int g(BlockWorld bw1, BlockWorld bw2) {
		if(!bw1.isEqualTo(bw2)) { 		
			for(BlockWorld i : bw1.next()) {
				return g(i,bw2)+1;
			}
		}
		return 0;
	}
}
