package controllers;

import java.awt.event.*;

import javax.swing.*;
import models.Board;


/**
 * Classe qui permet de gérer les touches du clavier et leur actions
 */
public class KeyBoard extends JFrame implements KeyListener{

    public boolean keyActive ;
    private Board board;

    /**
     * Nous permet de ramener notre fenetre et notre board
     * @param frame
     * @param board
     */
    public KeyBoard(JFrame frame,Board board){
        
        this.board = board;
        frame.addKeyListener(this);
        
    }


    /**
     * Nous utilisons un boolean KeyActive pour empecher
     * a la touche de repeter l'apelle de la méthode pour
     * bouger la case
     */
    @Override
    public void keyPressed(KeyEvent arg0) {

        int x = this.board.getEmptyPiece().getI();
        int y = this.board.getEmptyPiece().getJ();

        if(!this.keyActive){
        switch(arg0.getKeyCode()){

                case KeyEvent.VK_DOWN :
                        if(x - 1 >= 0){
                            this.board.swap2Pieces(this.board.getBoard()[x - 1][y]);
                            this.keyActive = true;
                        }
                  
                    break;
            
                case KeyEvent.VK_UP:
                        if ( x < this.board.getNbLine() - 1){
                            this.board.swap2Pieces(this.board.getBoard()[x + 1][y]);
                            this.keyActive = true;
                        }
                    break;

                case KeyEvent.VK_LEFT:

                        if ( y < this.board.getNbColumn() - 1){
                            this.board.swap2Pieces(this.board.getBoard()[x][y + 1 ]);
                            this.keyActive = true;
                        }
                  
                    break;

                case KeyEvent.VK_RIGHT:
                        if(y > 0 ){
                            this.board.swap2Pieces(this.board.getBoard()[x][y - 1]);
                            this.keyActive = true;
                        }
                   
                    break;

                }
            }
        }

    /**
     * Permet de gérer ce qui ce passe quand on relache une touche
     */
    @Override
    public void keyReleased(KeyEvent arg0) {

        this.keyActive = false;

    }
    
    @Override
    public void keyTyped(KeyEvent arg0) {

    }

}
