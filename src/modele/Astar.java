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
		this.closed = new ArrayList<BlockWorld>();
	}
	
	/**
	 * Algorithme de A*
	 */
	public BlockWorld algorithm() {
		// Initialisation
		ArrayList<Integer> distance = new ArrayList<Integer>();
		int currentDistance;
		this.open = new ArrayList<BlockWorld>();
		this.open.add(this.first);
		currentDistance = 0;
		distance.add(new Integer(currentDistance));
		int h = h(this.first, this.last);
		BlockWorld current = this.first;
		BlockWorld conseil = current;
		
		// Traitement
		while(this.open.size()!=0) {
			//current.printTable();
			// x <- arg min(xEO)(f(x))
			h = h(this.first, this.last)+currentDistance;
			current = this.open.get(0);
			for(BlockWorld x : this.open) {
				currentDistance = distance.get(this.open.indexOf(x));
				//System.out.println(" h to compare : "+(h(x, this.last)+currentDistance)+" <= "+h);
				//System.out.println("g : "+g(this.first, x));
				if(h(x, this.last)+currentDistance <= h) {
					//x.printTable();
					//System.out.println("minimum");
					//System.out.println(" h to compare : "+h(x, this.last)+"+"+currentDistance+" <= "+h);
					current = x;
					if(currentDistance==1) {
						conseil = current;
					}
					h = h(x, this.last)+currentDistance;
				}
				//x.printTable();
			}
			if(current.isEqualTo(this.last)) { this.first=conseil; return conseil; }
			// if x n'est pas le noeud final
			currentDistance = this.open.indexOf(current)==-1?0:distance.get(this.open.indexOf(current));
			distance.remove(new Integer(currentDistance));
			this.open.remove(current);
			this.closed.add(current);
			// for all y E successeurs(x)
			for(BlockWorld y : current.next()) {
				// if(y n'appartient pas aux fermes) ET (y n'appartient pas aux ouverts)
				boolean isClosed=false;
				for(int i=0; i<this.closed.size(); i++) {
					if(y.isEqualTo(this.closed.get(i))) { isClosed = true; break; }
				}
				if(!isClosed && (this.open.indexOf(y)==-1)) {
					this.open.add(y);
					distance.add(new Integer(currentDistance+1));
				}
			}
		}
		
		// Sortie
		this.first = conseil;
		return conseil;
	}
	
	/**
	 * Fonction heuristique de l'etat initial pour l'etat final (donnes dans le constructeur logique)
	 */
	public int h(){
		return h(this.first, this.last);
	}

	/**
	 * hZero : heuristique aveugle
	 * @return 0
	 */
	public int hZero() {
		return 0;
	}
	
	/**
	 * Fonction heuristique qui calcule la valeur d'un etat initial par rapport a un etat final
	 * compte le nombre de blocks mal places
	 * @param bw1 : l'etat initial
	 * @param bw2 : l'etat final
	 * @return la valeur de l'etat
	 */
	protected int hSymetrique(BlockWorld bw1,BlockWorld bw2){
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

	/**
	 * Fonction heuristique qui calcule le nombre de blocs mal positionnes.
	 * Les blocks sont invalides s'il n'y a pas la meme suite dans une des piles de l'autre BlockWorld.
	 * @param bw1 : l'etat initial
	 * @param bw2 : l'etat final
	 * @return la valeur de l'etat
	 */
	protected int h(BlockWorld bw1, BlockWorld bw2) {
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
				}
			}
		}
		return blocks;
	}

	/**
	 * g : fonction de calcul de distance (hauteur de noeud) entre deux BlockWorld
	 * genere un stack overflow
	 * @param bw1 : l'etat initial
	 * @param bw2 : l'etat final
	 * @return le 'coût' entre les deux etats
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
