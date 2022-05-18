package game;
//package instrusion2;



public class Move{
	protected int player;
	protected boolean action; // dupliquer ou deplacer
	protected Position depart, arrivee;


	/**
	 * Constructeur de la classe Move
	 * @param player le pion qui réalise un mouvement
	 * @param action le type d'action qu'il réalise (true pour une duplication 
	 * / false pour un déplacement)
	 * @param depart la position du pion qui va réaliser une action
	 * @param arrivee la position où le pion va réaliser son action
	 */
	public Move(int player, boolean action, Position depart, Position arrivee) {
		this.player = player;
		this.action = action;
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
	
	public Move() {}

	// Accesseurs: 

	public int getPlayer() {
		return this.player;
	}

	public boolean getAction() {
		return this.action;
	}

	public Position getDepart() {
		return this.depart;
	}

	public Position getArrivee() {
		return this.arrivee;
	}

	@Override
	public String toString(){
		return "( Départ = " + this.depart.toString() + " , Arrivée = " + this.arrivee.toString() + " , Action = " + this.action + ")";
	}


}
