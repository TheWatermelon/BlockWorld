package vue;

import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		
	//Données de test
		Block a=new Block('a');
		Block b=new Block('b');
		Block c=new Block('c');
		Block d=new Block('d');
		Block e=new Block('e');
		Block f=new Block('f');
		Block g=new Block('g');
		Block h=new Block('h');
		Stack s= new Stack<>();
		s.add(a);s.add(b);
		Stack si= new Stack<>();
		si.add(c);si.add(d);
		Stack sh= new Stack<>();
		sh.add(e);sh.add(f);
		Stack sj= new Stack<>();
		sj.add(g);sj.add(h);
		BlockWorld bo=new BlockWorld();
		bo.addStack(sj);
		bo.addStack(sh);
		bo.addStack(si);
		bo.addStack(s);
			
		 bo.put(sj,si);
		
		for(int l=0;l<4;l++){
			System.out.print("__");
			for(int t=0;t<bo.liststackblock.get(l).size();t++){
				
				System.out.print("|");
				System.out.print(bo.liststackblock.get(l).get(t).getValue());
				System.out.print("|");
				
			}
			System.out.print("__");
			//System.out.println("__");
		}
		
		//System.out.print(bo.clear(e));
	}
}
