
import models.Board;
import models.Move;
import models.Piece;
import views.WelcomeFrame;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        System.out.println("Usage : Saisie <gui> Pour lancer Une INTERFACE GRAPHIQUE \n"
                + "        Saisie <terminal> Pour lancer le Jeu en Terminal \n");

        Scanner sc = new Scanner(System.in);
        // On lit l'identifiant saisi par l'utilisateur afin de voir si on lance le jeu en Puzzle.GUI ou en ligne de commande
        String key = sc.nextLine();

        if (key.equals("gui")) {
            new WelcomeFrame();
        } else {
            if (key.equals("terminal")) {

                try {
                    // On demande a l'utilisateur de saisir la taille du plateau de jeu
                    System.out.println("Saisie la <taille> du plateau de Jeu :  \n"
                            + "<NOTE : Taille du plateau est un entier>");
                    int sizeGride = sc.nextInt();

                    Board grid = new Board(sizeGride, sizeGride);
                    boolean isFinish = grid.isFinished();
                    System.out.println("************************");
                    grid.displayBoard();
                    System.out.println("************************");
                    while (!isFinish) {

                        try {
                            // On demande a l'utilisateur de saisir l'element a echanger avec la piece vide
                            System.out.println("Veuillez Saisir l'element Que Vous Voulez Echanger Avec La Piece Vide '0' ");
                            Scanner sc1 = new Scanner(System.in);
                            int elmnt = sc1.nextInt();
                            if (grid.getPieceElmnt(elmnt) == null) {
                                System.out.println("ERROR : Element Incorrecte -- <Element n'existe pas dans le Plateau de Jeu> --");
                            } else {
                                Piece piece = grid.getPieceElmnt(elmnt);
                                Move move = new Move(piece, grid.getEmptyPiece());
                                if (grid.possibleSwaps().contains(move)) {
                                    grid.swap2Pieces(piece);
                                    System.out.println("************************************************");
                                    grid.displayBoard();
                                    System.out.println("************************************************");
                                } else {
                                    System.out.println("Alert : Element Inéchangeable Avec La Piece Vide ");
                                }

                            }
                            isFinish = grid.isFinished(); // on regarde si on l'utilisateur a resolue le Puzzle
                        } catch (InputMismatchException exp) {
                            System.out.println("************************");
                            grid.displayBoard();
                            System.out.println("************************");
                            System.out.println("Error : Element Saisie Incorrecte\n"
                                    + "Element à Saisir est Un Chiffre (entier) Existant Dans le Plateau De Jeu.");
                        }
                    }
                    System.out.println("Felicitation , Vous Avez Arrivé à Resoudre Le Puzzle");

                } catch (Exception exp) {
                    System.out.println("Error : Element Saisie Incorrecte\n"
                            + "NOTE : Taille du plateau est un entier");
                }
            } else {
                System.out.println("Error : Usage Invalide\n"
                        + "Usage : Saisie <gui> Pour lancer Une INTERFACE GRAPHIQUE \n"
                        + "        Saisie <terminal> Pour lancer le Jeu en Terminal \n");
            }
        }
    }

}
