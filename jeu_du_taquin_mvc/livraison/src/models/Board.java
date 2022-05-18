package models;

import util.AbstractModeleEcoutable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Nous permet de créer et gérer le plateau 
 */
public class Board extends AbstractModeleEcoutable {

    /** 
     * Board : represente notre plateau de jeu
     * Une Matrice de Piece
     */
    protected Piece[][] board;
      
    /**
     *  TrueBoard : represente notre plateau qui contient la ditribution initiale des pieces
     *  Une Matrice de Piece
     */
    protected Piece[][] trueBoard;

    /**
     *  nbLine : nombre de ligne de notre matrice
     */
    protected int nbLine;

    /**
     *  nbColumn : nombre de ligne de notre matrice
     */
    protected int nbColumn;
    
    /**
     *  emptyPiece : Notre piece vide dans le plateau de jeu
     *  son contenue est initialisé par 0.
     */
    protected Piece emptyPiece;

    /** 
     * Constructeur :
     *  On initialise notre plateau de jeu et on fait une copie dans le TrueBoard
     *  comme cela on s'assure de garder la version initiale correcte.
     *  On initialise ainsi note piece vide.
     *  Apres on  mélange les piece de notre plateau. (SHUFFLE)
     *  @param nbLine : nombre des lignes
     *  @param nbColumn : nombres des colonnes
     */
    public Board(int nbLine, int nbColumn) {
        super();
        this.nbLine = nbLine;
        this.nbColumn = nbColumn;
        this.board = new Piece[nbLine][nbColumn];
        this.trueBoard = new Piece[nbLine][nbColumn];
        int pieceContent = 1;
        for (int i = 0; i < nbLine; i++) {
            for (int j = 0; j < nbColumn; j++) {
                Piece elementBoard = new Piece(i, j, pieceContent);
                Piece elementTrueBoard = new Piece(i, j, pieceContent);
                board[i][j] = elementBoard;
                trueBoard[i][j] = elementTrueBoard;
                pieceContent++;
            }
        }
        board[nbLine - 1][nbColumn - 1].setElement(0);
        trueBoard[nbLine - 1][nbColumn - 1].setElement(0);
        emptyPiece = board[nbLine - 1][nbColumn - 1];
        shuffle();
    }

    /**
     *  displayBoard : on affiche notre plateau sous forme d'une matrice
     */
    public void displayBoard() {
        for (Piece[] line : board) {
            for (Piece column : line) {
                System.out.printf("%4s", column.getElement());
            }
            System.out.println();
        }
    }

    /**
     * isSwapPossible : ON regarde si le mouvement à effectuer est possible
     * On borne ainsi notre plateau pour ne pas sortir des bornes de notres plateau
     * On teste suivant la direction du deplacement {N:Nord - S:Sud - E:Est -O:Ouest}
     * @param start : Notre piece du depart.
     * @param direction : une direction de type STRING parmis les 4 direction possible {N-S-E-O}.
     * @return boolean : Est ce possible d'effectuer le mouvement ou Non.
     */
    public boolean isSwapPossible(Piece start, String direction) {
        switch (direction) {
            case "N":
                if (start.getI() == 0) {
                    return false;
                } else {
                    return true;
                }
            case "S":
                if (start.getI() == getNbLine() - 1) {
                    return false;
                } else {
                    return true;
                }
            case "E":
                if (start.getJ() == getNbColumn() - 1) {
                    return false;
                } else {
                    return true;
                }
            case "O":
                if (start.getJ() == 0) {
                    return false;
                } else {
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * possibleSwaps : regarde tous les case qui entoure la piece vide (dans les quatres direction {N-S-E-O}
     * et teste si ils peuvent etre echanger avec la piece vide et on les collectes dans une liste
     * @return une liste des mouvements possibles.
     */
    public ArrayList<Move> possibleSwaps() {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        String[] direction = {"N", "S", "E", "O"};

        for (String str : direction) {
            if (str.equals("N")) {
                if (isSwapPossible(getEmptyPiece(), "N")) {
                    possibleMoves.add(new Move(board[getEmptyPiece().getI() - 1][getEmptyPiece().getJ()], getEmptyPiece()));
                }
            }
            if (str.equals("S")) {
                if (isSwapPossible(getEmptyPiece(), "S")) {
                    possibleMoves.add(new Move(board[getEmptyPiece().getI() + 1][getEmptyPiece().getJ()], getEmptyPiece()));
                }
            }
            if (str.equals("E")) {
                if (isSwapPossible(getEmptyPiece(), "E")) {
                    possibleMoves.add(new Move(board[getEmptyPiece().getI()][getEmptyPiece().getJ() + 1], getEmptyPiece()));
                }
            }
            if (str.equals("O")) {
                if (isSwapPossible(getEmptyPiece(), "O")) {
                    possibleMoves.add(new Move(board[getEmptyPiece().getI()][getEmptyPiece().getJ() - 1], getEmptyPiece()));
                }
            }
        }
        return possibleMoves;
    }


    /**
     * swap2Pieces : échanger une piece données en parametre avec la piece vide
     * @param  piece : la piece à echanger
     */
    public void swap2Pieces(Piece piece) {
        Move move = new Move(piece, getEmptyPiece());
        if (this.possibleSwaps().contains(move)) {
            emptyPiece.setElement(piece.getElement());
            board[emptyPiece.getI()][emptyPiece.getJ()].setElement(piece.getElement());
            board[piece.getI()][piece.getJ()].setElement(0);
            setEmptyPiece(piece);
            notifierLesEcouteurs(); // On notifie les ecouteurs pour qu'ils effectue la misAJour de vue
        }
    }

    /**
     * shuffle : demare depuis un plateau initialement correcte.
     * et effectuer des échanges possibles tirés aleatoirement depuis la liste des echanges possible pour ne pas trouve des configurations impossible à la fin.
     * cette fonction joue un nombre precis des mouvements qui depends de la taille du notre plateau
     * nbMoves : nombres de nouvements qui egales à  Math.pow(getNbLine(),3) ;
     * de plus  la taille du plateau est grande de plus on va augmenter le nombres des echanges a effectuer
     * afin de bien mélanger notre plateau de jeu.
     */
    public void shuffle() {
        int nbMoves = (int) Math.pow(getNbLine(),3); // nombres des echanges a jouer pour melanger le puzzle
        while (nbMoves > 0) {
            ArrayList<Move> moves = possibleSwaps();
            Move randomSwap = moves.get(new Random().nextInt(moves.size()));
            swap2Pieces(randomSwap.start);
            nbMoves--;
        }
    }

    /**
     * isFinished : compare élement par élement du plateau de jeu avec le plateau Correcte
     * @return si les deux plateau on les meme elemnts ou Non
     *          Si Oui la partie est fini.
     */
    public boolean isFinished() {
        for (int i = 0; i < nbLine; i++) {
            for (int j = 0; j < nbColumn; j++) {
                if (!(board[i][j].getElement() == trueBoard[i][j].getElement())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * getPieceElmnt
     * @return la piece qui contient l'element donnée en parametre
     */
    public Piece getPieceElmnt(int elmnt){
        for (int i = 0; i < nbLine; i++) {
            for (int j = 0; j < nbColumn; j++) {
                if (board[i][j].getElement() == elmnt) {
                    return board[i][j];
                }
            }
        }
        return null;
    }


    /**
     * Accesseurs
     * @return le nombre des lignes
     */
    public int getNbLine() { return nbLine; }
    /**
     * Accesseurs
     * @return le nombre des colonnes
     */
    public int getNbColumn() { return nbColumn; }
    /**
     * Accesseurs
     * @return le plateau de jeu
     */
    public Piece[][] getBoard() { return board; }
    /**
     * Accesseurs
     * @return la piece vide
     */
    public Piece getEmptyPiece() { return emptyPiece; }
    /**
     * Mutateurs
     * Permet d'affecter une piece a la piece vide
     */
    public void setEmptyPiece(Piece piece) { this.emptyPiece = piece; }


}



