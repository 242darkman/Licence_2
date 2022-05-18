package models;



public class Move {

    /** start : la piece du depart.
     */
    public Piece start;
    /**start : la piece d'arriveé.
     * cette piece va presque toujours correspondre à la piece vide
     */
    protected Piece end;



    /** Constructeur
     * @param start : piece du depart
     * @param end : piece d'arriveé
     */
    public Move(Piece start, Piece end) {
        this.start = start;
        this.end = end;
    }
    /**
     * deux piece dans mouvement sont egales si ils ont la meme piece du depart et d'arriveé.
     */
    @Override
    public boolean equals(Object obj) {
        return (this.start.equals(((Move) obj).start)
                && this.end.equals(((Move) obj).end));
    }

    /**
     * @return String Un affichage des elements du mouvement.
     */
    @Override
    public String toString() {
        return ("Start {" + start.getI() + "  ;  " + start.getJ() + "}  " +
                "End {" + end.getI() + "  ;  " + end.getJ() + "}");
    }

    /**
     * Accesseurs
     * @return la piece du depart.
     */
    public Piece getStart() {
        return start;
    }
    /**
     * Accesseurs
     * @return la piece d'arriveé.
     */
    public Piece getEnd() {
        return end;
    }



}
