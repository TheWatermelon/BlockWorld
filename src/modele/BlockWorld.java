package modele;

import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

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

	/**
	 * Constructeur logique a partir d'un fichier.
	 * Le fichier doit etre du format suivant : chaque ligne correspond a un emplacement sur la table et les blocs sont indiques par des caracteres minuscules de 'a' a 'z'
	 * @param filename : le nom du fichier d'entree
	 */
	public BlockWorld(String filename) {
		table=new ArrayList<>();
		parent=null;
		change="do nothing";
		hauteur=0;

		BufferedReader lecteur=null;
		try {
			lecteur = new BufferedReader(new FileReader(filename));
			try {
				String ligne;
				while((ligne=lecteur.readLine())!=null) {
					Stack<Block> s = new Stack<Block>();
					for(int i=0; i<ligne.length(); i++) {
						s.push(new Block(ligne.charAt(i)));
					}
					table.add(s);
				}
			} catch(IOException e) {
				System.err.println("Erreur de lecture du fichier '"+filename+"'");
			}
		} catch(FileNotFoundException e) {
			System.err.println("Le fichier '"+filename+"' n'existe pas.");
		}
	}

	/** 
	 * getChange : connaitre le passe de l'etat
	 * @return le changement de l'etat par rapport a un parent
	 */
	public String getChange() { return change; }

	/** 
	 * getHauteur : accesseur de la hauteur
	 * @return la hauteur
	 */
	public int getHauteur() { return hauteur; }

	/** 
	 * setHauteur : mutateur de la hauteur
	 * @param h : la nouvelle hauteur
	 * @return void
	 */
	public void setHauteur(int h) { this.hauteur = h; }

	/**
	 * getParent : renvoie le parent de cet etat. La racine n'a pas de parent (parent=null)
	 * @return le parent
	 */
	public BlockWorld getParent() { return parent; }

	/**
	 * setParent : mutateur de l'attribut parent
	 * @param bw : le nouveau parent
	 */
	public void setParent(BlockWorld bw) { this.parent = bw; }

	/**
	 * searchParent : renvoie le parent a la hauteur donnee. Si la hauteur est superieure ou egale a la hauteur de l'etat, renvoie l'etat
	 * @param h : la hauteur du parent recherche
	 * @return le parent recherche
	 */
	public BlockWorld searchParent(int h) {
		if(h>=this.hauteur) { return this; }
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
	 * purgeTable : retire de la table toutes les piles vides
	 */
	protected void purgeTable() {
		for(int i=0; i<this.table.size(); i++) {
			for(Stack<Block> stack : this.table) {
				if(stack.isEmpty()) {this.table.remove(stack); break; }
			}
		}
	}
	
	/**
	 * getTable : accesseur de la table
	 * @return table : la table
	 */
	public ArrayList<Stack<Block>> getTable(){
		return table;
	}

	/**
	 * getBlockCount : renvoie le nombre de blocks sur la table
	 * @return le nombre le blocks sur la table
	 */
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
			for(Block b : table.get(i)) { 
				if(b.isEqualTo(block)) { 
					block=b;
					onTable=true; 
					break; 
				}
			}
			if(onTable) { break; }
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
		for(int i=0;i<table.size();i++) {	// pour chaque pile
			boolean onStack=false;
			for(Block b : table.get(i)) {	// recupere le block equivalent a b1
				if(b.isEqualTo(b1)) {
					b1=b;
					break;
				}
			}
			for(Block b : table.get(i)) { // recupere le block equivalent a b2
				if(b.isEqualTo(b2)) { 
					b2=b;
					onStack=true; 
					break; 
				}
			}
			if(onStack) {	// cette pile contient le bloc b2
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
	 * isEqualTo : fonction d'egalite entre BlockWorld.
	 * @param bw : le BlockWorld a comparer
	 * @return vrai si bw est egal au BlockWorld appelant, faux sinon
	 */
	public boolean isEqualTo(BlockWorld bw) {
		HeuristiqueAbstraite heuristique = new HeuristiquePosition();
		if(heuristique.h(this, bw)==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * next : genere tous les etats successeurs du BlockWorld appelant
	 * @return un tableau (BlockWorld[]) des successeurs du BlockWorld appelant
	 */
	public BlockWorld[] next() {
		// Preparation de l'etat
		purgeTable();
		// Calcul du nombre d'etats successeurs et stockage des piles sources d'etats successeurs
		int nbSuccesseurs=0;
		int[] pilesSource = new int[this.table.size()];
		for(int i=0; i<this.table.size(); i++) {
			if(!this.table.get(i).isEmpty()) {
				nbSuccesseurs+=this.table.size();
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
				for(int j=0; j<=this.table.size(); j++) {	// Pour chaque pile de destination
					if(i==j) { continue; }	// On passe le deplacement d'un block de sa pile vers sa pile
					copie=copy();
					copie.setHauteur(this.hauteur+1);
					copie.setParent(this);
					sOrig = copie.getTable().get(i);	// On prend la pile source
					if(j==this.table.size()) { // Cas de la creation d'une pile de destination
						sDest = new Stack<Block>();
						copie.addStack(sDest);
					} else {
						sDest = copie.getTable().get(j);	// On prend la pile destination
					}
					successeurs[etatSuccesseur++]=copie.put(sOrig, sDest);	// Ajout de l'etat successeur au tableau
					copie.purgeTable();
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
