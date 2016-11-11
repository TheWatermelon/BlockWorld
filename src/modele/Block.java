package modele;

public class Block {
     protected char value;
     
     /**
      * Constructeur logique
      * @param value : la valeur du block
      */
     public Block(char value) {
    	 this.value=value;
     }

     /**
      * getValue : accesseur de l'attribut value
      * @return La valeur du block
      */
     public char getValue(){
    	 return value;
     }
     
     /**
      * isEqualTo : fonction d'egalite entre blocks (au niveau de la valeur)
      * @param b : le block a comparer
      * @return Vrai si les valeurs des blocks sont egales, Faux sinon
      */
     public boolean isEqualTo(Block b) {
    	 return this.value==b.getValue();
     }
}
