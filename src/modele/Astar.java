package modele;

import java.util.*;

public class Astar {
	protected ArrayList<BlockWorld> open;
	protected ArrayList<BlockWorld> closed;
	protected ArrayList<BlockWorld> advices;
	protected BlockWorld first;
	protected BlockWorld last;
	public PriorityQueue<BlockWorld> openlist;
	//PriorityQueue<BlockWorld> listouverts; 
	    
	/**
	 * Constructeur logique
	 * @param init : l'etat initial
	 * @param term : l'etat final
	 */
 
	public Astar(BlockWorld init, BlockWorld term) {
		this.first = init;
		this.last = term;
		this.advices = new ArrayList<BlockWorld>();
		archiveAdvice(this.first);
		
	}

	protected void archiveAdvice(BlockWorld advice) {
		this.advices.add(advice);
	}

	public void setFirst(BlockWorld bw) {
		this.first = bw;
		archiveAdvice(this.first);
	}

	public void setLast(BlockWorld bw) {
		this.last = bw;
	}
	
	/**
	 * Algorithme de A*
	 */
	
	public int heuristics(BlockWorld initial,BlockWorld finale){
		int finali = -1,compteur=0;
		Block moi = null ;
		
			 for(int i=0;i<finale.getTable().size();i++){// Parcours du tableau des différentes piles du tableau final
				 if(!finale.getTable().get(i).isEmpty()){
				  for(int j=0;j<finale.getTable().size();j++){
				       if(!initial.getTable().get(j).isEmpty()){
					        if(finale.getTable().get(i).get(0)==initial.getTable().get(j).get(0)){
					        	finali=0;
						 for(int g=0;g<Math.max(finale.getTable().get(i).size(),initial.getTable().get(j).size());g++){
							 if(g<Math.min(finale.getTable().get(i).size(),initial.getTable().get(j).size())){
								if(finale.getTable().get(i).get(g)==initial.getTable().get(j).get(g)) {
									compteur++;
									if(compteur==Math.min(finale.getTable().get(i).size(),initial.getTable().get(j).size())){
										
										//finali+=initial.getTable().get(j).size()-(initial.getTable().get(j).indexOf(initial.getTable().get(j).get(g))+1);
										if(compteur==initial.getTable().get(j).size()&&(initial.getTable().get(j).size()!=finale.getTable().get(i).size())){
											if(initial.getTable().get(j).size()+1<finale.getTable().get(i).size()){
											moi=finale.getTable().get(i).get(initial.getTable().get(j).size()+1);
											finali+=finale.getTable().get(i).size()%initial.getTable().get(j).size();
											System.out.print("l  "+moi.value);
											}
										}
										
									}	
							 
									
								}else{
									
									//On s'assure qu'il reste des elements dans la pile finale
								        if(g<finale.getTable().get(i).size()){
								        	if(initial.getTable().get(j).get(initial.getTable().get(j).size()-1).equals(finale.getTable().get(i).get(g))){
								        	finali+=5;	
								        	}else{
								        		
								        	}
								 moi=finale.getTable().get(i).get(g);
											//System.out.print(initial.getTable().get(j).get(initial.getTable().get(j).size()-1).equals(finale.getTable().get(i).get(g)));
								        }
								 finali+=finale.getTable().get(i).size()%compteur;   
						         finali+=initial.getTable().get(j).size()-(initial.getTable().get(j).indexOf(initial.getTable().get(j).get(g-1))+1);
									
										//	moi=initial.getTable().get(j).get(g-1);
										//System.out.print(moi.value);
										break;
									 
									
									
								}
							 }
						 }
					 }
				 }
			   }
			    
			 }
		  }
			 if(compteur==0){
				 for(Stack<Block> s:finale.getTable()){
					 if(!s.isEmpty()){
						 moi=s.get(0);
						 break; 
					 }
				 }
				
			 } 
			 
           if(moi!=null){
        	 finali+=initial.indextotop(moi);
           }
			return finali;
			
		}
	public BlockWorld algogbegbe(BlockWorld initial,BlockWorld finale){
	
	//listouverts.add(initial);
		openlist=new PriorityQueue<>();
		PriorityQueue<BlockWorld> fo ;
		
		
		for(BlockWorld bi:initial.next()){
	       bi.incost+=this.heuristics(bi, finale);
	       openlist.add(bi);
		} 
		
		BlockWorld b=openlist.poll();
		while(!b.isEqualTo(finale)){
			fo=new PriorityQueue<>();
			for(BlockWorld bl:b.next()){
				bl.incost+=this.heuristics(bl, finale);
				fo.add(bl);
			}
			//b=new BlockWorld();
			b=fo.poll();
			System.out.print(b.incost);
		}
		 
       
		return b;
		
	}
	public BlockWorld algorithm() {
		// Initialisation
		this.open = new ArrayList<BlockWorld>();
		this.open.add(this.first);
		this.closed = new ArrayList<BlockWorld>();
		ArrayList<Integer> distance = new ArrayList<Integer>();
		int currentDistance=0;
		distance.add(new Integer(currentDistance));
		int f = h(this.first, this.last);
		BlockWorld current = this.first;
		BlockWorld conseil = current;
		
		// Traitement
		while(this.open.size()!=0) {
			// x <- arg min(xEO)(f(x))
			f = 99;
			current = this.open.get(0);
			for(BlockWorld x : this.open) {
				currentDistance = distance.get(this.open.indexOf(x));
				if(h(x, this.last)+currentDistance <= f) {
					current = x;
					f = h(x, this.last)+currentDistance;
					//System.out.println("Comparaison currentDistance : "+currentDistance+" avec hauteur : "+x.getHauteur());
					//conseil = current.getHauteur()<2?current:current.searchParent(1);
					if(currentDistance<=2) {
						conseil = current;
					}
				}
			}
			// if x est le noeud final
			if(current.isEqualTo(this.last)) { return conseil; }
			currentDistance = this.open.indexOf(current)==-1?0:distance.get(this.open.indexOf(current));
			distance.remove(new Integer(currentDistance));
			this.open.remove(current);
			this.closed.add(current);
			// for all y E successeurs(x)
			for(BlockWorld y : current.next()) {
				// if(y n'appartient pas aux fermes) ET (y n'appartient pas aux ouverts)
				boolean isOpen=false, isClosed=false, isAdvice=false;
				for(int i=0; i<this.open.size(); i++) {
					if(y.isEqualTo(this.open.get(i))) { isOpen = true; break; }
				}
				for(int i=0; i<this.closed.size(); i++) {
					if(y.isEqualTo(this.closed.get(i))) { isClosed = true; break; }
				}
				/*
				for(int i=0; i<this.advices.size(); i++) {
					if(y.isEqualTo(this.advices.get(i))) { isAdvice = true; break; }
				}
				*/
				if(!isClosed && !isOpen) {
					this.open.add(y);
					//System.out.println("Deuxieme comparaison currentDistance : "+currentDistance+" avec hauteur : "+y.getHauteur());
					distance.add(new Integer(currentDistance+1));
				}
			}
		}
		
		// Sortie
		return conseil;
	}
	
	public BlockWorld algorithm1() {
		// Initialisation
		this.open = new ArrayList<BlockWorld>();
		this.open.add(this.first);
		this.closed = new ArrayList<BlockWorld>();
		ArrayList<Integer> distance = new ArrayList<Integer>();
		int currentDistance=0;
		distance.add(new Integer(currentDistance));
		int f = heuristics(this.first, this.last);
		BlockWorld current = this.first;
		BlockWorld conseil = current;
		
		// Traitement
		while(this.open.size()!=0) {
			// x <- arg min(xEO)(f(x))
			f = 99;
			current = this.open.get(0);
			for(BlockWorld x : this.open) {
				currentDistance = distance.get(this.open.indexOf(x));
				if(heuristics(x, this.last)+currentDistance <= f) {
					current = x;
					f = heuristics(x, this.last)+currentDistance;
					//System.out.println("Comparaison currentDistance : "+currentDistance+" avec hauteur : "+x.getHauteur());
					//conseil = current.getHauteur()<2?current:current.searchParent(1);
					if(currentDistance<=2) {
						conseil = current;
					}
				}
			}
			// if x est le noeud final
			if(current.isEqualTo(this.last)) { return conseil; }
			currentDistance = this.open.indexOf(current)==-1?0:distance.get(this.open.indexOf(current));
			distance.remove(new Integer(currentDistance));
			this.open.remove(current);
			this.closed.add(current);
			// for all y E successeurs(x)
			for(BlockWorld y : current.next()) {
				// if(y n'appartient pas aux fermes) ET (y n'appartient pas aux ouverts)
				boolean isOpen=false, isClosed=false, isAdvice=false;
				for(int i=0; i<this.open.size(); i++) {
					if(y.isEqualTo(this.open.get(i))) { isOpen = true; break; }
				}
				for(int i=0; i<this.closed.size(); i++) {
					if(y.isEqualTo(this.closed.get(i))) { isClosed = true; break; }
				}
				/*
				for(int i=0; i<this.advices.size(); i++) {
					if(y.isEqualTo(this.advices.get(i))) { isAdvice = true; break; }
				}
				*/
				if(!isClosed && !isOpen) {
					this.open.add(y);
					//System.out.println("Deuxieme comparaison currentDistance : "+currentDistance+" avec hauteur : "+y.getHauteur());
					distance.add(new Integer(currentDistance+1));
				}
			}
		}
		
		// Sortie
		return conseil;
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
	protected void ajoutOuvert(BlockWorld b){
		
		
		
	}
	
	
	
	
	
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
	public int h(BlockWorld bw1, BlockWorld bw2) {
		Block b1, b2;
		int blocks=bw1.getBlocksCount();	// InitialisÃ© au nombre de blocks de bw1

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
	
	// Methode devant me permettre de calculer le nommbre de block à partir du nième bloc différent de celui du blockwordfinal+
	 
	public int hu(BlockWorld bw1, BlockWorld bw2) {
		Block b1, b2; int deff=0; Block n = null;
		int blocks=bw1.getBlocksCount();	// InitialisÃ© au nombre de blocks de bw1

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
 						//System.out.print(b1.getValue()+" et "+b2.getValue());
 						b1 = b2;
 						b2 = bw2.up(b1);
 						
 					}
 					//System.out.print(b1.getValue()+" et "+b2.getValue()); 
 					deff=bw1.relatedtoTop(b2);
 					
					break;
				}
			}
		}
		return deff;
	}
	
	
	
	
	
	
	
	
	

	public void run() {
		BlockWorld current;
		do {
			current=algorithm1();
			current.printTable();
			System.out.println(current.getHauteur()+" "+current.getChange());
			setFirst(current);
			/*
			try {
				System.out.println("Press enter");
				System.in.read();
			} catch(IOException exception) {}
			*/
		} while(!current.isEqualTo(this.last));
	}
}
