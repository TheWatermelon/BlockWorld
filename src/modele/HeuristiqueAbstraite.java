package modele;

public interface HeuristiqueAbstraite {
	/**
	 * nom : Le nom de la strategie
	 * @return Le nom de la strategie
	 */
	public String nom();

	/**
	 * description : La description de la strategie
	 * @return La description de la strategie
	 */
	public String description();

	/**
	 * h : La strategie
	 * @param bw1 : l'etat initial
	 * @param bw2 : l'etat final
	 * @return La valeur heuristique de l'etat initial par rapport a l'etat final
	 */
	public int h(BlockWorld bw1, BlockWorld bw2);
}