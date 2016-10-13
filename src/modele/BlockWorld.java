package modele;

import java.util.ArrayList;
import java.util.Stack;

import modele.Block;

public class BlockWorld {

	ArrayList<Stack<Block>> liststackblock;

	public BlockWorld() {
		liststackblock=new ArrayList<>();
	}
	public void addStack( Stack<Block> s){
		liststackblock.add(s);
	}
	public ArrayList<Stack<Block>> getListStack(){
		return liststackblock;
	}

	public boolean clear(Block block){
		boolean b=false;
		for(int i=0;i<4;i++){
			for(int j=0;j<liststackblock.get(i).size();j++){
				if(liststackblock.get(i).get(0)==block){
					b=true;
				}
			}
		}
		return b;	 
	}

	public boolean on(Block b1,Block b2){

		boolean b=false;
		for(int i=0;i<4;i++){
			for(int j=0;j<liststackblock.get(i).size();j++){
				if(liststackblock.get(i).get(j)==b2){
					if(j>0){
						if(liststackblock.get(i).get(j-1)==b1){
							b=true;
						} 
					}
				}
			}
		}
		return b;	
	}

	public BlockWorld put(Stack<Block> x,Stack<Block> y){
		//boolean insurance=false;
		liststackblock.get(liststackblock.indexOf(y)).push(liststackblock.get(liststackblock.indexOf(x)).pop());
		return this;
	}
	
	public BlockWorld copy() {
		BlockWorld copy = new BlockWorld();
		for(int i=0; i<4; i++) {
			Stack<Block> s = new Stack<Block>();
			for(int j=0; j<this.liststackblock.get(i).size(); j++) {
				s.push(this.liststackblock.get(i).get(j));
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
			if(this.liststackblock.get(i).size()!=0) {
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
					Stack<Block> sOrig = this.liststackblock.get(i);	// On prend la pile source
					Stack<Block> sDest = this.liststackblock.get(j);	// On prend la pile destination
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
			for(int t=0;t<liststackblock.get(l).size();t++){
				System.out.print("|");
				System.out.print(liststackblock.get(l).get(t).getValue());
				System.out.print("|");
			}
			System.out.print("__");
		}
	}
	
	public void printTable() {
		// Recuperation de la hauteur maximum
		int maxHeight=0;
		for(int i=0; i<4; i++) {
			if(liststackblock.get(i).size()>maxHeight) {
				maxHeight=liststackblock.get(i).size();
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
				if(i/2<liststackblock.get(j).size()) {
					blocks[i+1]+="+-+";
					blocks[i]+="|"+liststackblock.get(j).get(i/2).getValue()+"|";
				} else {
					blocks[i]+="   ";
					blocks[i+1]+="   ";
				}
				// Espace entre les blocks
				blocks[i]+="   ";
				blocks[i+1]+="   ";
			}
		}
		for(int i=maxHeight-1; i>=0; i--) {
			System.out.println(blocks[i]);
		}
		String lastLine="";
		lastLine+="___";
		if(this.liststackblock.get(0).size()==0) { lastLine+="___"; }
		else {	lastLine+="+-+"; }
		lastLine+="___";
		if(this.liststackblock.get(1).size()==0) { lastLine+="___"; }
		else {	lastLine+="+-+"; }
		lastLine+="___";
		if(this.liststackblock.get(2).size()==0) { lastLine+="___"; }
		else {	lastLine+="+-+"; }
		lastLine+="___";
		if(this.liststackblock.get(3).size()==0) { lastLine+="___"; }
		else {	lastLine+="+-+"; }
		lastLine+="___";
		System.out.println(lastLine);
	}
}
