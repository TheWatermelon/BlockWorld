package modele;

import java.util.ArrayList;
import java.util.Stack;

import modele.Block;

public class BlockWorld {
	ArrayList<Stack<Block>> table;

	public BlockWorld() {
		table=new ArrayList<>();
	}

	public void addStack( Stack<Block> s){
		table.add(s);
	}
	
	public ArrayList<Stack<Block>> getTable(){
		return table;
	}

	public boolean clear(Block block){
		boolean b=false;
		for(int i=0;i<4;i++){
			if(table.get(i).get(0)==block){
				b=true;
			}
		}
		return b;
	}

	public boolean on(Block b1,Block b2){
		for(int i=0;i<4;i++){
			for(int j=0;j<table.get(i).size();j++){
				if(table.get(i).get(j)==b2){
					if(j>0){
						if(table.get(i).get(j-1)==b1){
							return true;
						} 
					}
				}
			}
		}
		return false;
	}

	public BlockWorld put(Stack<Block> x,Stack<Block> y){
		table.get(table.indexOf(y)).push(table.get(table.indexOf(x)).pop());
		return this;
	}
	
	public BlockWorld copy() {
		BlockWorld copy = new BlockWorld();
		for(int i=0; i<4; i++) {
			Stack<Block> s = new Stack<Block>();
			for(int j=0; j<this.table.get(i).size(); j++) {
				s.push(this.table.get(i).get(j));
			}
			copy.addStack(s);
		}
		return copy;
	}
	
	public BlockWorld[] next() {
		// Calcul du nombre d'etats successeurs et stockage des piles sources d'etats successeurs
		int nbSuccesseurs=0;
		int[] pilesSource = new int[4];
		for(int i=0; i<4; i++) {
			if(this.table.get(i).size()!=0) {
				nbSuccesseurs+=3;
				pilesSource[i]=1;
			}
		}
		// Copie de l'etat actuel
		BlockWorld copie = this;
		// Creation du tableau des etats successeurs
		BlockWorld[] successeurs = new BlockWorld[nbSuccesseurs];
		for(int i=0; i<nbSuccesseurs; i++) {
			successeurs[i] = new BlockWorld();
		}
		// Remplissage du tableau des etats successeurs
		int etatSuccesseur=0;
		for(int i=0; i<4; i++) {	// Pour chaque pile d'origine
			if(pilesSource[i]==1) {	// Si la pile d'origine est une pile source d'etats successeurs
				for(int j=0; j<4; j++) {	// Pour chaque pile de destination
					if(i==j) { continue; }	// On passe le deplacement d'un block de sa pile vers sa pile
					Stack<Block> sOrig = this.table.get(i);	// On prend la pile source
					Stack<Block> sDest = this.table.get(j);	// On prend la pile destination
					successeurs[etatSuccesseur++]=(put(sOrig, sDest)).copy();	// Ajout de l'etat successeur au tableau
					put(sDest, sOrig);	// Retour a l'etat initial
				}
			}
		}
		return successeurs;
	}

	public void printInLineTable() {
		for(int l=0;l<4;l++){
			System.out.print("__");
			for(int t=0;t<table.get(l).size();t++){
				System.out.print("|");
				System.out.print(table.get(l).get(t).getValue());
				System.out.print("|");
			}
			System.out.print("__");
		}
	}
	
	public void printTable() {
		// Recuperation de la hauteur maximum
		int maxHeight=0;
		for(int i=0; i<4; i++) {
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
			for(int j=0; j<4; j++) {
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
		for(int i=0; i<4; i++) {
			if(this.table.get(i).size()==0) { lastLine+="___"; }
			else {	lastLine+="+-+"; }
			lastLine+="___";
		}
		System.out.println(lastLine);
	}
}
