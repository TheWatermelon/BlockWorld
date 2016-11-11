package vue;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.io.*;

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
		Stack<Block> s2 = new Stack<>();
		Stack<Block> s= new Stack<>();
		s.push(b);s2.add(b);s2.add(a);
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
		s1.add(j);s1.add(i);s1.add(h);
		s1.add(g);s1.add(f);s1.add(e);
		s1.add(d);s1.add(c);s1.add(b);s1.add(a);
		s0.add(j);s0.add(i);s0.add(h);
		s0.add(g);s0.add(f);s0.add(e);
		s0.add(d);s0.add(c);//s1.add(b);s1.add(a);
		BlockWorld box=new BlockWorld();
		BlockWorld boi=new BlockWorld();
		boi.addStack(s0);
		
		box.addStack(s1);
		s1=new Stack<>();
		boi.addStack(s2);
		boi.addStack(s1);
		boi.addStack(s1);
        box.addStack(s1);
		box.addStack(s1);
		box.addStack(s1);
		//box.addStack(s0);
		Astar su=new Astar(bo,box);
		//BlockWorld current;
	
		bo.printTable();
		box.printTable();boi.printTable();
	//su.algogbegbe(bo, box).printTable();
		//box.printTable();
//System.out.print(su.heuristics(box, box));
		
	 su.run();
			
			
			//pox.poll().printTable();
	/*	 
		PriorityQueue<BlockWorld> po=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> pox=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> poxi=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> poxie=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> poxij=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> ph=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> phe=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> phi=new PriorityQueue<>(); 
			for(BlockWorld bj: boi.next()){
				bj.incost+=su.heuristics(bj, box);
				po.add(bj);
			}
			
			
			  
			// po.peek().printTable();
		 
			 for(BlockWorld bji: po.peek().next()){
					bji.incost+=su.heuristics(bji, box);
					pox.add(bji);
				}
			 
	
			 for(BlockWorld bjib: pox.peek().next()){
					bjib.incost+=su.heuristics(bjib, box);
					poxi.add(bjib);
				}
			 
			 for(BlockWorld bjib: poxi.peek().next()){
					bjib.incost+=su.heuristics(bjib, box);
					poxie.add(bjib);
				}
			 for(BlockWorld bjib: poxie.peek().next()){
					bjib.incost+=su.heuristics(bjib, box);
					poxij.add(bjib);
				}
			 for(BlockWorld bjib: poxij.peek().next()){
					bjib.incost+=su.heuristics(bjib, box);
					ph.add(bjib);
				}
			 
			 for(BlockWorld bjib: ph.peek().next()){
					bjib.incost+=su.heuristics(bjib, box);
					phe.add(bjib);
				}
			 for(BlockWorld bjib: phe.peek().next()){
					bjib.incost+=su.heuristics(bjib, box);
					phi.add(bjib);
				}
			 
			 for(BlockWorld bij: phi){
					bij.printTable();
					
					System.out.print(bij.incost);
				}
				
			 phi.peek().printTable();	
		//System.out.println(phi.peek().incost);
		//	pox.poll().printTable();
			*/
		//	System.out.print(su.hu(poxi.poll(), box));
		//su.run();
		
		/*PriorityQueue<BlockWorld> po=new PriorityQueue<>(); 
		
		 
		PriorityQueue<BlockWorld> poxie=new PriorityQueue<>();
		PriorityQueue<BlockWorld> poxise=new PriorityQueue<>();
		PriorityQueue<BlockWorld> poi=new PriorityQueue<>(); 
		PriorityQueue<BlockWorld> poit=new PriorityQueue<>();
		
		ArrayList<BlockWorld> t=new ArrayList<>();
        for(BlockWorld bj: bo.next()){
			bj.incost=su.h(bj, box);
			t.add(bj);
		 }
    
		for(BlockWorld bj: bo.next()){
			bj.incost=su.h(bj, box);
			po.add(bj);
		}
		
		
		
        for(BlockWorld bj: pox.poll().next()){
        	bj.incost=su.h(bj, box);
        	poxi.add(bj);
			
		}
		
      /*  for(BlockWorld bj: poxi){
        	bj.incost=su.h(bj, box);
			bj.printTable();
			//System.out.println(su.h(bj,box));
			
		}*/
      /*  for(BlockWorld bj: poxi.poll().next()){
        	bj.incost=su.h(bj, box);
        	 poxie.add(bj);
			//System.out.print(su.h(bj,box));
			
		}
        for(BlockWorld bj: poxie.poll().next()){
        	bj.incost=su.h(bj, box);
       	 poxise.add(bj);
			//System.out.print(su.h(bj,box));
			
		}
        
        for(BlockWorld bj: poxise.poll().next()){
        	bj.incost=su.h(bj, box);	
       	 poi.add(bj);
			//System.out.print(su.h(bj,box));
			
		}
        for(BlockWorld bj: poi){
        	bj.incost=su.h(bj, box);
          	 poit.add(bj);
   			//System.out.print(su.h(bj,box));
   			
   		}
        for(BlockWorld bj: poi){
			
         bj.printTable();
  		System.out.print(su.h(bj,box));
  			
  		}
        
        
      poi.poll().printTable();*/
   
    }
}
