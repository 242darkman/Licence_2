package game;
//package intrusion2;

public class Position {
	protected int x;
	protected int y;

	/**
	 * Constructeur de Position
	 * @param x indice de la ligne 
	 * @param y indice de la colone 
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 2ème constructeur de Position
	 * @param pos une position
	 */
	public Position(Position pos) {
		this(pos.getX(), pos.getY());
	}

	/**
	 * Change la position d'un pion en faisant la somme des valeurs x et y 
	 * indiquées en paramètres
	 * @param x indice de la ligne 
	 * @param y indice de la colone 
	 */

	public void movePos(int x, int y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Redéfinition de la méthode equals pour comparer les valeurs x et y 
	 * avec une autre Position
	 * @param pos une position
	 * @return booléen
	 */
	public boolean equals(Position pos) {
		return pos.getX() == this.getX() && pos.getY() == this.getY();
	}



	// accesseurs

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
