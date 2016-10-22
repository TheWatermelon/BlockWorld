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
		Block i=new Block('i');
		Block j=new Block('j');
		Stack<Block> s0 = new Stack<>();
		Stack<Block> s1 = new Stack<>();
		Stack<Block> s= new Stack<>();
		s.push(b);
		Stack<Block> si= new Stack<>();
		si.push(d);si.push(c);si.push(a);
		Stack<Block> sh= new Stack<>();
		sh.push(f);sh.push(e);
		Stack<Block> sj= new Stack<>();
		sj.push(j);sj.push(i);sj.push(g);sj.push(h);
		BlockWorld bo=new BlockWorld();
		bo.addStack(s);
		bo.addStack(si);
		bo.addStack(sh);
		bo.addStack(sj);
		
		/////
		BlockWorld box=new BlockWorld();
		box.addStack(s1);
		box.getTable().get(0).push(j);
		box.getTable().get(0).push(i);
		box.getTable().get(0).push(h);
		box.getTable().get(0).push(g);
		box.getTable().get(0).push(f);
		box.getTable().get(0).push(e);
		box.getTable().get(0).push(d);
		box.getTable().get(0).push(c);
		box.getTable().get(0).push(b);
		box.getTable().get(0).push(a);
		box.addStack(s0);
		box.addStack(s0);
		box.addStack(s0);
		Astar su=new Astar(bo, box);
	
		bo.printTable();
		System.out.println("Etat initial");
		box.printTable();
		System.out.println("Etat final");
		
		/*
		{
			int cpt=1;
			for(BlockWorld i : bo.next()) {
				System.out.println();
				i.printTable();
				System.out.println("Etat successeur "+(cpt++));
			}
		}
		*/
		
		su.algorithm().printTable();
		System.out.println("Etat conseil");
		
	}
}
