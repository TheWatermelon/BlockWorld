package modele;

import java.util.ArrayList;
import java.util.Stack;

import modele.Block;

public class BlockWorld {
	protected ArrayList<Stack<Block>> table;
	protected String change;
	protected int hauteur;
	protected BlockWorld parent;

	/**
	 * Constructeur par defaut
	 */
	public BlockWorld() {
		table=new ArrayList<>();
		parent=null;
		change="do nothing";
		hauteur=0;
	}

	/** getChange : connaitre le passe de l'etat
	 * @return le changement de l'etat par rapport a un parent
	 */
	public String getChange() { return change; }

	/** getHauteur : accesseur de la hauteur
	 * @return hauteur
	 */
	public int getHauteur() { return hauteur; }

	/** setHauteur : mutateur de la hauteur
	 * @return void
	 */
	public void setHauteur(int h) { this.hauteur = h; }

	public BlockWorld getParent() { return parent; }

	public void setParent(BlockWorld bw) { this.parent = bw; }

	public BlockWorld searchParent(int h) {
		if(h>=this.hauteur) { return null; }
		BlockWorld p=this;
		for(int i=0; i!=this.hauteur-h; i++) {
			p = p.getParent();
		}
		return p;
	}

	/**
	 * addStack : ajoute une pile s a la table
	 * @param s : la pile a ajouter a la fin de la table
	 */
	public void addStack( Stack<Block> s){
		table.add(s);
	}
	
	/**
	 * getTable : accesseur de la table
	 * @return table : la table
	 */
	public ArrayList<Stack<Block>> getTable(){
		return table;
	}

	public int getBlocksCount() {
		int blocks=0;

		for(int i=0; i<table.size(); i++) {
			blocks+=table.get(i).size();
		}

		return blocks;
	}

	/**
	 * clear : verifie que block est le block au dessus de la pile
	 * @param block : le block a verifier
	 * @return vrai si le block est au dessus d'une pile, faux sinon
	 */
	public boolean clear(Block block){
		for(int i=0;i<table.size();i++){
			if(table.get(i).get(table.get(i).size()-1).isEqualTo(block)){
				return true;
			}
		}
		return false;
	}

	/**
	 * up : renvoie le block au-deesus de celui passé en argument
	 * @param block : le block au-dessous
	 * @return le block au-dessus de celui passé en paramètre
	 */
	public Block up(Block block) {
		// Verifie que le block est sur la table
		int i;
		boolean onTable=false;
		for(i=0; i<table.size(); i++) {
			if(table.get(i).indexOf(block)!=-1) { onTable = true; break; }
		}
		if(!onTable) { return null; }
		// Verifie que le block n'est pas le dernier
		if(table.get(i).indexOf(block)==table.get(i).size()-1) { return null; }

		return table.get(i).get(table.get(i).indexOf(block)+1);
	}

	/**
	 * on : verifie que b1 est au-dessus de b2
	 * @param b1 : le premier block
	 * @param b2 : le deuxieme block
	 * @return vrai si b1 est au-dessus de b2, faux sinon
	 */
	public boolean on(Block b1,Block b2){
		for(int i=0;i<table.size();i++){	// pour chaque pile
			if(table.get(i).indexOf(b2)!=-1) {	// cette pile contient le bloc b2
				if(table.get(i).indexOf(b1)==(table.get(i).indexOf(b2)+1)) {	// si b1 est au-dessus de b2
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * put : pose le block du dessus de la pile x sur le dessus de la pile y
	 * @param x : la pile source
	 * @param y : la pile destination
	 * @return le nouveau BlockWorld
	 */
	public BlockWorld put(Stack<Block> x,Stack<Block> y){
		if(y.size()==0) { change="put "+x.get(x.size()-1).getValue()+" on table"; }
		else { change="put "+x.get(x.size()-1).getValue()+" on "+y.get(y.size()-1).getValue(); }
		y.push(x.pop());
		return this;
	}
	
	/**
	 * copy : cree un copie du BlockWorld appelant
	 * @return la copie du BlockWorld appelant
	 */
	public BlockWorld copy() {
		BlockWorld copy = new BlockWorld();
		for(int i=0; i<table.size(); i++) {
			Stack<Block> s = new Stack<Block>();
			for(int j=0; j<this.table.get(i).size(); j++) {
				s.push(this.table.get(i).get(j));
			}
			copy.addStack(s);
		}
		return copy;
	}
	
	/**
	 * isEqualTo : fonction d'egalite entre BlockWorld. INVALIDE
	 * @param bw : le BlockWorld a comparer
	 * @return vrai si bw est egal au BlockWorld appelant, faux sinon
	 */
	public boolean isEqualTo(BlockWorld bw) {
		Block b1, b2;
		int blocks=this.getBlocksCount();	// Initialisé au nombre de blocks du comparé

		for(int i=0; i<this.table.size(); i++) {	// Pour chaque pile du comparant
			// Cas pile vide du comparant
			if(this.table.get(i).size()==0) { continue; }
			b1 = this.table.get(i).get(0); // Le premier block de la pile du comparant
			for(int j=0; j<bw.getTable().size(); j++) {	// Pour chaque pile du comparé
			// Cas pile vide du comparé
			if(bw.getTable().get(j).size()==0) { continue; }
				if(bw.getTable().get(j).get(0).isEqualTo(b1)) { // Si le premier block correspond alors
					blocks--;
 					b2 = this.up(b1);
 					while(b1!=null && b2!=null && bw.on(b2, b1)) {	// Tant que b2 est au-dessus de b1 dans le comparé
 						// Le block est bien positionné
 						blocks--;
 						// on monte d'un niveau
 						b1 = b2;
 						b2 = this.up(b1);
 					}
				}
			}
		}

		if(blocks==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * next : genere tous les etats successeurs du BlockWorld appelant
	 * @return un tableau (BlockWorld[]) des successeurs du BlockWorld appelant
	 */
	public BlockWorld[] next() {
		// Calcul du nombre d'etats successeurs et stockage des piles sources d'etats successeurs
		int nbSuccesseurs=0;
		int[] pilesSource = new int[this.table.size()];
		for(int i=0; i<this.table.size(); i++) {
			if(!this.table.get(i).isEmpty()) {
				nbSuccesseurs+=this.table.size()-1;
				pilesSource[i]=1;
			}
		}
		// Creation du tableau des etats successeurs
		BlockWorld[] successeurs = new BlockWorld[nbSuccesseurs];
		for(int i=0; i<nbSuccesseurs; i++) {
			successeurs[i] = new BlockWorld();
		}
		// Remplissage du tableau des etats successeurs
		int etatSuccesseur=0;
		BlockWorld copie;
		Stack<Block> sOrig, sDest;
		for(int i=0; i<this.table.size(); i++) {	// Pour chaque pile d'origine
			if(pilesSource[i]==1) {	// Si la pile d'origine est une pile source d'etats successeurs
				for(int j=0; j<this.table.size(); j++) {	// Pour chaque pile de destination
					if(i==j) { continue; }	// On passe le deplacement d'un block de sa pile vers sa pile
					copie=copy();
					copie.setHauteur(this.hauteur+1);
					copie.setParent(this);
					sOrig = copie.getTable().get(i);	// On prend la pile source
					sDest = copie.getTable().get(j);	// On prend la pile destination
					successeurs[etatSuccesseur++]=copie.put(sOrig, sDest);	// Ajout de l'etat successeur au tableau
				}
			}
		}
		return successeurs;
	}

	@Override
	public String toString() {
		String res="";
		for(int l=0;l<table.size();l++){
			res+="__";
			for(int t=0;t<table.get(l).size();t++){
				res+="|"+table.get(l).get(t).getValue()+"|";
			}
			res+="__";
		}
		res+="\n";
		
		return res;
	}
	
	/**
	 * printTable : affiche la table dans la sortie standard
	 */
	public void printTable() {
		// Recuperation de la hauteur maximum
		int maxHeight=0;
		for(int i=0; i<table.size(); i++) {
			if(table.get(i).size()>maxHeight) {
				maxHeight=table.get(i).size();
			}
		}
		maxHeight*=2;
		String[] blocks = new String[maxHeight];
		// Premier sol
		for(int i=0; i<maxHeight; i++) {
			blocks[i]="   ";
		}
		// Stockage des blocks dans le tableau
		for(int i=0; i<maxHeight; i=i+2) {
			for(int j=0; j<table.size(); j++) {
				if(i/2<table.get(j).size()) {
					blocks[i+1]+="+-+";
					blocks[i]+="|"+table.get(j).get(i/2).getValue()+"|";
				} else {
					blocks[i+1]+="   ";
					blocks[i]+="   ";
				}
				// Espace entre les blocks
				blocks[i]+="   ";
				blocks[i+1]+="   ";
			}
		}
		for(int i=maxHeight-1; i>=0; i--) {
			System.out.println(blocks[i]);
		}
		String lastLine="___";
		for(int i=0; i<table.size(); i++) {
			if(this.table.get(i).size()==0) { lastLine+="___"; }
			else {	lastLine+="+-+"; }
			lastLine+="___";
		}
		System.out.println(lastLine+"\n");
	}
}
