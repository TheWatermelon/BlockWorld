package modele;

import java.util.ArrayList;
import java.util.Stack;

import modele.Block;

public class BlockWorld {

	ArrayList<Stack<Block>> liststackblock;

	public BlockWorld() {
		// TODO Auto-generated constructor stub
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
		for(Stack<Block> r: liststackblock)
		{
			if(r.equals(x)){
				for(Stack<Block> ru: liststackblock){
					if(ru.equals(y)){
						for(int i=0;i<r.size();i++){
							liststackblock.get(liststackblock.indexOf(y)).add(liststackblock.get(liststackblock.indexOf(x)).get(i));
						}
					}
				}
			}
		}
		return this;
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
		System.out.println("___+-+___+-+___+-+___+-+___");
	}
}
