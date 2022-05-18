package ia;
// package intrusion2;

import game.Move;
import game.State;

public interface GameRepresentation {
	
	/**
	 * Méthode permettant aux joueur de choisir un coup 
	 * @param state : grille dans laquelle le joueur choisit son coup
	 * @return le coup qui sera choisit
	 */
	public abstract Move decide(State state);
	
	/**
	 * Méthode permettant de retourner le nom du joueur
	 * @return le nom du joueur
	 */
	//public int getPlayerName();
}
