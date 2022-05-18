package game;
//package intrusion2;

import java.util.*;



public class State{
	// attributs
	protected int n;  // nombre de lignes
	protected int m; //  nombre de colonnes 
	protected  int[][] board;
	private int currentPlayer;

	// création des constantes pour les deux joueurs
	public static int player1 = 1;
	public static int player2 = 2;

	private static List<Position> CLONE;
	private static List<Position> TRIPS;

	/**
	 * initialisation des mouvements prédéfinis
	 */
	static {
		TRIPS = new ArrayList<Position>();
		CLONE = new ArrayList<Position>();

		TRIPS.add(new Position(0,2));
		TRIPS.add(new Position(0,-2));
		TRIPS.add(new Position(2,0));
		TRIPS.add(new Position(-2,0));

		CLONE.add(new Position(0,1));
		CLONE.add(new Position(0,-1));
		CLONE.add(new Position(1,0));
		CLONE.add(new Position(-1,0));

	}



	/**
	 * Constructeur du State
	 * @param n : nombre de lignes dans la grille
	 * @param m : nombre de colonnes dans la grille
	 */
	public State(int n,int m){ 
		this.n = n;
		this.m = m;
		this.board= new int[this.n][this.m];  
		this.currentPlayer = 2;
	}


	/**
	 * Second constructeur du State (pour effectuer une copie de la grille)
	 * @param oldBoard : ancien tableau qui devra etre copié
	 */
	public State(State oldState) {

		this(oldState.n,oldState.m);

		for(int i=0;i<oldState.n;i++) {
			for(int j=0;j<oldState.m;j++) {

				this.board[i][j] = oldState.board[i][j];

			}
		}
    }




	// accesseurs
	public int getCurrentPlayer(){ 
		return this.currentPlayer;
	}

	public void setCurrentPlayer(int newPlayer){
		this.currentPlayer = newPlayer;
	}

	/**
	 * Méthode pour afficher la grille
	 */
	public void displayBoard(){  
		for (int i = 0; i < this.board.length; i++) {
			System.out.println("  ");
			for (int j = 0; j < this.board[i].length ; j++) {
				System.out.print(this.board[i][j] + "  ");
			}
			System.out.println("  ");
		}
		System.out.println();
	}
	


	/**
	 * Méthode qui nous permet de placer les joueurs dans la grille
	 */
	public void putPlayersOnTheBoard() {
		this.board[0][0]               = State.player1;
		this.board[this.n-1][this.m-1] = State.player2;
	}

	/**
	 * Méthode vérifiant l'existence d'une case pour pouvoir effectuer un mouvement 
	 * @param i : indice de la ligne recherchée
	 * @param j : indice de la colonne recherchée
	 * @return un booléen vérifiant si une case existe ou non
	 */
	private boolean isValidIndex(int i, int j) {
		int last_i = this.board.length;
		int last_j = this.board[0].length;

		if(i >= 0 && j >= 0 && i < last_i && j < last_j) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode pour obtenir tous les mouvements possibles des joueurs
	 * @param player : le joueur qui va effectuer le mouvement
	 * @return une liste de mouvement possible pour le joueur
	 */
	public List<Move> getMoves(){
		List<Move> validMoves = new ArrayList<Move>();
		int i2, j2;

		// on va chercher les coups possibles: 

		// pour chaque ligne, puis pour chaque colonnes: 
		for(int i = 0; i < this.board.length; i++){
			for(int j = 0; j < this.board[i].length; j++){
				// si c'est le -player- qui nous interresse: 
				if(board[i][j] == this.currentPlayer) {

					// pour chaque action de type duplication possible: 
					for(Position pos : State.CLONE) {
						i2 = i + pos.getX();
						j2 = j + pos.getY();
						// si la cellule existe et si aucun pion n'est présent : 
						if(this.isValidIndex(i2, j2) && this.board[i2][j2] == 0) {
							// on ajoute un mouvement possible dans la liste des coups possibles: 
							validMoves.add(new Move(this.currentPlayer, true, new Position(i, j), new Position(i2, j2)));
						}
					}

					// la même pour l'action de type déplacement: 
					for(Position pos : State.TRIPS) {
						i2 = i + pos.getX();
						j2 = j + pos.getY();
						if(isValidIndex(i2, j2) && this.board[i2][j2] == 0) {
							validMoves.add(new Move(this.currentPlayer, false, new Position(i, j), new Position(i2, j2)));
						}
					}
				}

			}
		}

		return validMoves;
	}


	/**
	 * Méthode permettant de faire faire des mouvements à chaque joueur dans la grille
	 * @param go : le mouvement qui devra etre effectuer
	 * @return un nouveau State c'est-à-dire une nouvelle grille
	 */
	public State play(Move go){
		int i2, j2;	

		Position depart = go.getDepart();
		Position arrivee = go.getArrivee();

		State stateCopie = new State(this);

		if(go.getAction() == false) {
			stateCopie.board[depart.getX()][depart.getY()] = 0;
			stateCopie.board[arrivee.getX()][arrivee.getY()] = this.currentPlayer;

		}
		else if(go.getAction() == true) {

			stateCopie.board[arrivee.getX()][arrivee.getY()] = this.currentPlayer;
			for(Position pos : State.CLONE) {
				i2 = arrivee.getX() + pos.getX();
				j2 = arrivee.getY() + pos.getY();

				if(isValidIndex(i2, j2)) {
					if(stateCopie.board[i2][j2] == State.player1 || stateCopie.board[i2][j2] == State.player2) {
						stateCopie.board[i2][j2] = this.currentPlayer;
					}
				}
			}
		}
		return stateCopie;
	}


	/**
	 * Méthode pour compter le nombre de pions dans la grille
	 * @param joueur le joueur pour lequel on va compter les pions
	 * @return le nombre total de pion d'un joueur dans la grille
	 */
	public int counter(int joueur) {
		int count = 0;
		for(int i = 0; i < this.n; i++) {
			for(int j = 0; j < this.m; j++) {
				if(this.board[i][j] == joueur) {
					count += 1;
				}
			}
		}
		return count;
	}


	/**
	 * Méthode pour calculer le score de chaque joueur
	 * @param player : le joueur pour lequel on va calculer les points (score)
	 * @return le score d'un joueur 
	 */
	public float getScore(int player) {
		float nbPionJoueur    = 0;
		float nbPionTotal     = 0;
		float scoreTotal      = 0;
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.m ; j++)
			{
				if  (this.board[i][j]!= 0){

					if (this.board[i][j]== player){
						nbPionJoueur ++;
					}
					nbPionTotal++;
				}
				scoreTotal = (nbPionJoueur)/(nbPionTotal);
			}
		}

		return scoreTotal;
	}


	/**
	 * Méthode pour vérifier si la partie est terminée ou pas
	 * @return True ou False si la partie est terminée ou non
	 */
	public boolean isFinished() {
		
		if(counter(1) == 0){
			return true;
		}else if(counter(2) == 0){
			return true;
		}else{
			return false;
		}

	}



	/**
	 * Méthode pour désigner le vainqueur de la partie
	 * @return le joueur qui a remporté la partie
	 */
	public int whoIsTheWinner() {
		// on récupère le nombre de pions de chaque joueur
		int score1 = this.counter(State.player1);
		int score2 = this.counter(State.player2);

		if(score1 > score2) { // si le J1 a plus de pions que le J2 alors le J1 gagne 
			return State.player1;
		}
		else if(score1 < score2) { // sinon c'est le J2 qui gagne
			return State.player2;
		}
		return 0; 
	}



	public String displayTheWinner() {
		String winner = " ";
		if(this.whoIsTheWinner() == State.player1) {
			winner = " Le gagnant de la partie est le joueur " + State.player1;
		}
		else if(this.whoIsTheWinner() == State.player2) {
			winner = " Le gagnant de la partie est le joueur " + State.player2;
		}
		else if(this.whoIsTheWinner() == 0) {
			winner = " Score de parité. Il n'y a pas de vainqueur ";
		}
		return winner;
	}


	@Override
	public String toString() {
		String res = " ";
		if(this.whoIsTheWinner() == State.player1) {
			res = "Le score final de la partie est de : " + this.getScore(State.player1) + " point(s) contre " + this.getScore(State.player2);
		}
		else {
			res = "Le score final de la partie est de : " + this.getScore(State.player2) + " point(s) contre " + this.getScore(State.player1);
		}
		return res;
	}




}