package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BlockWorld {

	ArrayList<Stack<Block>> liststackblock;

	public BlockWorld() {
		// TODO Auto-generated constructor stub
		liststackblock=new ArrayList<>();	 
	}
	public void addStack( Stack s){

		liststackblock.add(s);

	}
	public List getListStack(){

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

	public BlockWorld put(Stack x,Stack y){
		boolean insurance=false;

		for(Stack r: liststackblock)
		{
			if(r.equals(x)){

				for(Stack ru: liststackblock){
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



}
