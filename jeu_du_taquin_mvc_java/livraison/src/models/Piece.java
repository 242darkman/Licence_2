package models;

public class Piece {

    /** i : coordonnées des lignes dans le plateau du jeu
     */
    private int i;
    /** j : coordonnées des lignes dans le plateau du jeu
     */
    private int j;
    /** element : le contenu de la piece
     */
    protected int element;

    /**Constructeur
     * @param i coordonnées des lignes
     * @param j coordonnées des lignes
     * @param elemnt le contenu de la piece
     */
    public Piece(int i, int j, int elemnt) {
        this.i = i;
        this.j = j;
        this.element = elemnt;
    }


    /**
     * Deux pieces sont egales si ils ont les memes coordonneés ainsi le meme element.
     */
    @Override
    public boolean equals(Object obj) {
        return (this.getI() == ((Piece) obj).getI()
                && this.getJ() == ((Piece) obj).getJ()
                && this.element == ((Piece) obj).element);
    }

    /**
     * @return String Un affichage des elements de la piece.
     */
    @Override
    public String toString() {
        return "element : " + getElement() + "{" + getI() + " , " + getJ() + "}";
    }



    /**
     * Accesseurs
     * @return la coordonneé i.
     */
    public int getI() {
        return i;
    }
    /**
     * Accesseurs
     * @return la coordonneé j.
     */
    public int getJ() {
        return j;
    }
    /**
     * Accesseurs
     * @return le contenu de la piece.
     */
    public int getElement() {
        return element;
    }
    /**
     * Mutateurs
     * Permet d'initialiser la coordonnées i.
     */
    public void setI(int i) {
        this.i = i;
    }
    /**
     * Mutateurs
     * Permet d'initialiser la coordonnées j
     */
    public void setJ(int j) {
        this.j = j;
    }
    /**
     * Mutateurs
     * Permet d'affecter une element de type int au contenu de la piece
     */
    public void setElement(int element) {
        this.element = element;
    }

}
