package modele;

import java.util.Stack;

public class Astar {

	
	
	public int h(BlockWorld bw1,BlockWorld bw2){
		int blocks=0;
		for(int i=0;i<4;i++){
			Stack<Block> s=bw1.getListStack().get(i);	
			Stack<Block> ss=bw2.getListStack().get(i);
			for(int j=0;j<Math.max(s.size(),ss.size());j++){
				if(Math.max(s.size(),ss.size())==s.size()){
					if(j<ss.size()){
						if(s.get(j)!=ss.get(j)){
							blocks++;
						}
					}else{
						blocks++;
					}
					
				}else{
					if(j<s.size()){
						
						if(s.get(j)!=ss.get(j)){
							blocks++;
						}
					}
					
				}
			}
		}
	
		
		
		return blocks;
	}
}
