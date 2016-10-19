package vue;

import java.util.Stack;

import modele.Astar;
import modele.Block;
import modele.BlockWorld;

public class Main {
	public static void main(String[] args) {
		
		//Donnees de test
		Block a=new Block('a');
		Block b=new Block('b');
		Block c=new Block('c');
		Block d=new Block('d');
		Block e=new Block('e');
		Block f=new Block('f');
		Block g=new Block('g');
		Block h=new Block('h');
		Stack<Block> s= new Stack<>();
		s.add(a);s.add(b);
		Stack<Block> si= new Stack<>();
		si.add(c);si.add(d);
		Stack<Block> sh= new Stack<>();
		sh.add(e);sh.add(f);
		Stack<Block> sj= new Stack<>();
		sj.add(g);sj.add(h);
		BlockWorld bo=new BlockWorld();
		bo.addStack(sj);
		bo.addStack(sh);
		bo.addStack(si);
		bo.addStack(s);
		
		/////
		BlockWorld box=new BlockWorld();
		box.addStack(sh);
		 
		box.addStack(s);
		box.addStack(si);
		box.addStack(sj);
		Astar su=new Astar();
		
		System.out.println(su.h(bo, box));
		
		//bo.put(sj,si);
		
		//BlockWorld[] successeurs = bo.next();
	
		bo.printTable();
		box.printTable();
		//System.out.println("Etat initial");
		/*
		for(int i=0; i<successeurs.length; i++) {
			System.out.println();
			successeurs[i].printTable();
			System.out.println("Etat successeur "+(i+1));
		}
		*/
		
	}
}
