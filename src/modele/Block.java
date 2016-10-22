package modele;

public class Block {
	
     protected char value;
     
     public Block(char value) {
    	 this.value=value;
     }

     public char getValue(){
    	 return value;
     }
     
     public boolean isEqualTo(Block b) {
    	 return this.value==b.getValue();
     }
}
