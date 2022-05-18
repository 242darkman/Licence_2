package main;

import game.Move;
import game.State;
import ia.IntelligenceArtificielle;

public class Main{
	public static void main(String[] args) throws InterruptedException {
		
		if (!validArgs(args)) {
			System.out.println("Entrer le bon nombre d'argument afin d'executer le programme");
			System.exit(0);
		}
		
		
		/**
		 * Initialisation des paramètres de jeu
		 */
		int lignes   = Integer.parseInt(args[0]);
		int colonnes = Integer.parseInt(args[1]);
		int coupAvance     = Integer.parseInt(args[2]);
		int profondeurJoueur1 = Integer.parseInt(args[3]);
		int profondeurJoueur2 = Integer.parseInt(args[4]);
		boolean selectAlgo = Boolean.parseBoolean(args[5]);
		
		game(lignes, colonnes, coupAvance, 
				profondeurJoueur1, profondeurJoueur2, selectAlgo);
	}

	public static void game(int n, int m, int coupAvance, int profondeurJ1,
			int profondeurJ2, boolean select){

		State board = new State(n,m);  

		Move mouvement;
		board.putPlayersOnTheBoard();

		board.displayBoard();
		IntelligenceArtificielle myAI = new IntelligenceArtificielle(1,select);
		int compteur = 0;
		while (!board.isFinished()){ 
			System.out.println("Joueur : " + board.getCurrentPlayer());

			if(compteur%2 == 0){
				board.setCurrentPlayer(State.player1);
			}else{
				board.setCurrentPlayer(State.player2);
			}

			if(coupAvance != 0){
				board.setCurrentPlayer(State.player1);
				coupAvance -= 1;
			}

			
			
			
			try {
				Thread.sleep(0);

				if(board.getCurrentPlayer() == State.player1){
					mouvement = myAI.decide(board,profondeurJ1);
				}else{
					mouvement = myAI.decide(board,profondeurJ2);
				}
				
				System.out.println("Mouvement choisie : " + mouvement);
				
				board = board.play(mouvement);
			}
			catch(Exception e) {
				break;
			}
			
			// on affiche la nouvelle grille modifiée
			board.displayBoard();
			compteur++;
		}
		// on affiche le score de chaque joueur
		System.out.println(board.toString());
		System.out.println(" Pion du J1 : " + board.counter(State.player1));
		System.out.println(" Pion du J2 : " + board.counter(State.player2));
		System.out.println("Nombre noeuds " + myAI.getNombreNoeudsVisite());
		System.out.println("-------------------------------------");
		System.out.println("||" + board.displayTheWinner() + " ||");
		System.out.println("-------------------------------------");
	}
	
	
	
	/**
	 * Méthode qui essaie de valider les arguments passer en ligne de commande
	 * @param args : tableau où sera contenu les arguments entrés en ligne de commande
	 * @return retourne true si les arguments sont valides et false sinon.
	 */
	public static boolean validArgs(String [] args) {
		if (Integer.parseInt(args[0]) * Integer.parseInt(args[1]) < 3) {
			System.out.println("⚠️ Les dimensions sont trop petites pour la grille ❗");
			return false;
		}
		if (Integer.parseInt(args[2]) < 0 || Integer.parseInt(args[3]) < 0 || Integer.parseInt(args[4]) < 0) {
			System.out.println("⚠️ Le nombre entré pour la (les) profindeur(s) et/ou le coup d'avance ne peut etre négatif ⛔");
			return false;
		}
		
		return true;
	}

}