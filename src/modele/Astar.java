package modele;

import java.util.*;

/**
 * 
 * @author Bastien CHAPUSOT, Taric GANDI
 *
 */

public class Astar {
	protected ArrayList<BlockWorld> open;
	protected ArrayList<BlockWorld> closed;
	protected BlockWorld first;
	protected BlockWorld last;
	protected HeuristiqueAbstraite heuristique;
	
	/**
	 * Constructeur logique
	 * @param init : l'etat initial
	 * @param term : l'etat final
	 * @param h : l'heuristique a utiliser
	 */
	public Astar(BlockWorld init, BlockWorld term, HeuristiqueAbstraite h) {
		this.first = init;
		this.last = term;
		this.heuristique = h;
	}

	/**
	 * setFirst : mutateur de l'etat initial
	 * @param bw : le nouvel etat initial
	 */
	public void setFirst(BlockWorld bw) {
		this.first = bw;
	}

	/**
	 * setLast : mutateur de l'etat final
	 * @param bw : le nouvel etat final
	 */
	public void setLast(BlockWorld bw) {
		this.last = bw;
	}
	
	/**
	 * algorithm : Algorithme de A*
	 * @return Le meilleur successeur de l'etat initial
	 */
	public BlockWorld algorithm() {
		// Initialisation
		this.open = new ArrayList<BlockWorld>();
		this.open.add(this.first);
		this.closed = new ArrayList<BlockWorld>();
		ArrayList<Integer> distance = new ArrayList<Integer>();
		int currentDistance=0;
		distance.add(new Integer(currentDistance));
		int f = heuristique.h(this.first, this.last);
		BlockWorld current = this.first;
		BlockWorld conseil = current;
		
		// Traitement
		while(this.open.size()!=0) {
			// x <- arg min(xEO)(f(x))
			f = 99;
			current = this.open.get(0);
			for(BlockWorld x : this.open) {
				currentDistance = distance.get(this.open.indexOf(x));
				if(heuristique.h(x, this.last)+currentDistance <= f) {
					current = x;
					f = heuristique.h(x, this.last)+currentDistance;
					conseil = current.getHauteur()<2?current:current.searchParent(this.first.getHauteur()+1);
				}
			}
			// if x est le noeud final
			if(current.isEqualTo(this.last)) { break; }
			// Sinon
			currentDistance = this.open.indexOf(current)==-1?0:distance.get(this.open.indexOf(current));
			distance.remove(new Integer(currentDistance));
			this.open.remove(current);
			this.closed.add(current);
			// for all y E successeurs(x)
			for(BlockWorld y : current.next()) {
				// if(y n'appartient pas aux fermes) ET (y n'appartient pas aux ouverts)
				boolean isOpen=false, isClosed=false;
				for(int i=0; i<this.open.size(); i++) {
					if(y.isEqualTo(this.open.get(i))) { isOpen = true; break; }
				}
				for(int i=0; i<this.closed.size(); i++) {
					if(y.isEqualTo(this.closed.get(i))) { isClosed = true; break; }
				}
				if(!isClosed && !isOpen) {
					this.open.add(y);
					distance.add(new Integer(currentDistance+1));
				}
			}
		}
		
		// Sortie
		return conseil;
	}

	/**
	 * run : la boucle d'execution de l'algorithme
	 */
	public void run() {
		BlockWorld current;
		System.out.println("Algorithm started using "+heuristique.nom()+"\n");
		System.out.println(heuristique.description()+"\n");
		do {
			current=algorithm();
			current.printTable();
			System.out.println(current.getChange()+"\n");
			setFirst(current);
			/*
			try {
				System.out.println("Press enter");
				System.in.read();
			} catch(IOException exception) {}
			*/
		} while(!current.isEqualTo(this.last));
		System.out.println("Algorithm ended.");
	}
}
