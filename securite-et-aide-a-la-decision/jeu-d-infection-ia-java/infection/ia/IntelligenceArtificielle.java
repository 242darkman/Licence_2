package ia;

import game.Move;
import game.State;

public class IntelligenceArtificielle{

    private int player;
    private int nombreNoeudVisite;
    private boolean selection;

    
    
    public IntelligenceArtificielle(int player,boolean selectIA){
        this.player    = player;
        this.selection = selectIA;
    }       
    
  


    
    /**
	  * Methode qui permet de choisir un meilleur coup dans une situation de jeu
	  * @param state  La situation courante du jeu
	  * @param La profondeur
	  * @return La meilleure valeur et le meilleur coup
	  */
    public Couple minMax(State state,int profondeur){
        this.nombreNoeudVisite++;
        float b;
        Move mouvement = null;

        if(state.isFinished() || profondeur == 0){
            Couple resultat = new Couple(null, state.getScore(this.player));           
            return resultat;
        }

        if(state.getCurrentPlayer() == this.player){
            b = Float.NEGATIVE_INFINITY;
            
            for(Move move : state.getMoves()){
                State copy = state.play(move);
                Couple resultat = minMax(copy,profondeur - 1);


                if(resultat.getScore() > b){
                    b = resultat.getScore();
                    mouvement = move;
                }
            }

        }else{
            b = Float.POSITIVE_INFINITY;
            
            for(Move move : state.getMoves()){

                State copy = state.play(move);

                Couple resultat = minMax(copy,profondeur - 1);

                if(resultat.getScore() < b){
                   
                    b = resultat.getScore();
                    mouvement = move;
                }
            }
        }
    
        return new Couple(mouvement,b);

    }


    
    /**
	 * Une méthode permettant de réduire le nombre de nœuds évalués par l'algorithme minimax.
	 * @param s La situation courante du jeu
	 * @param profondeur de recherche 
	 * @param alpha la valeur la plus basse que le joueur peut obtenir
	 * @param beta la valeur maximale que le joueur Min autorise a Max
	 * @return La meilleure valeur et le meilleur coup
	 */
    public Couple alphaBeta(State s,int profondeur, float alpha,float beta){
        this.nombreNoeudVisite++;
        Move move = null;

        if(s.isFinished() || profondeur == 0){
            Couple res = new Couple(null, s.getScore(this.player));
            return res;
        }
        
        if(s.getCurrentPlayer() == this.player){

            for(Move mouvement : s.getMoves()){
                State copy = s.play(mouvement);
                Couple resultat = alphaBeta(copy, profondeur-1, alpha, beta);

                if(resultat.getScore() > alpha){
                    alpha = resultat.getScore();
                    move = mouvement;
                }
                
                if(alpha >= beta){
                    return new Couple(move, alpha);
                }
            }
            
            return new Couple(move, alpha);
        }
        
        else{

            for(Move mouvement : s.getMoves()){

                State copy = s.play(mouvement);
                Couple resultat = alphaBeta(copy, profondeur-1, alpha, beta);

                if(resultat.getScore() < beta){

                    beta = resultat.getScore();
                    move = mouvement;

                }if(beta <= alpha){
                    return new Couple(move, beta);
                }
            }

            return new Couple(move, beta);

        }

    }

    
    
    public int getNombreNoeudsVisite(){
        return this.nombreNoeudVisite;
    }
    
    
    
    
	  /**
	    * Methode qui permet de choisir un meilleur coup dans une situation de jeu
	    * @param state La situation courante du jeu
	    * @param profondeur la profondeur d'un joueur
	    * @return Le meilleur coup
	    */
  public Move decide(State state, int profondeur) {

		int vStar = Integer.MIN_VALUE;
		int v = 0;
		Move coup = null;
		State newState = new State(state);

		for (Move mouvement : state.getMoves()) {

			newState = newState.play(mouvement);
			
			if(this.selection){
	            v = (int) minMax(state, profondeur).getScore();
	        }
	        else{
	            v = (int) alphaBeta(state, profondeur, 
	            		Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY).getScore();
	        }
			
			if (v > vStar) {
				vStar = v;
				coup = mouvement;
			}

		}
		return coup;
	}


}


