package views;

import models.Board;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;


public class CutImage {

    /** grid : une instance du plateau de jeu
     * Une Matrice de Piece
     */
    protected Board grid;
    /**iconHeight : la longueur de chaque icone du puzzle
     */
    protected int iconHeight;
    /**iconWidth : la longueur de chaque icone du puzzle
     */
    protected int iconWidth;
    /**imageToBuffer : Une image a composer, le contenu du puzzle
     */
    protected BufferedImage imageToBuffer;
    /**imagePart : un tableau des portions d'image decoupé
     */
    protected BufferedImage imagePart[];

    /**Constructeur
     * @param grid le plateau du jeu pour obtenir le caracteristiques du plateau
     * @throws IOException si il n'arrive pas a lire l'image.
     */
    public CutImage(Board grid) throws IOException {
        this.grid=grid;
        File imageFile = new File("website.jpg");
        imageToBuffer = ImageIO.read(imageFile);
        this.iconHeight = imageToBuffer.getHeight() / this.grid.getNbColumn();
        this.iconWidth = imageToBuffer.getWidth() / this.grid.getNbLine();
        imagePart = new BufferedImage[this.grid.getNbColumn() * this.grid.getNbLine()];
        this.imagePart=cut();
    }

    /** cut
     *  on découpe une image a nbLine (nombre des ligne de notre plateau de jeu) ligne et nbColumn colonne (nombre de colonnes de notre plateau de jeu)
     * @return un tableau des images decoupé
     */
    public BufferedImage[] cut() {


/**
 * 
 * méthode provenant de : 
 * https://kalanir.blogspot.com/2010/02/how-to-split-image-into-chunks-java.html?fbclid=IwAR1o6QO9EVY8hs2tFPoZmkUYlI28GLdt82YaJqYs1HQCNGgxlgU8bnsGROw
 * 
 */

        int count = 0;
        for (int i = 0; i < this.grid.getNbLine(); i++) {
            for (int j = 0; j < this.grid.getNbColumn(); j++) {
                imagePart[count] = new BufferedImage(this.getIconWidth(), this.getIconHeight(), imageToBuffer.getType());
                Graphics2D gr = imagePart[count++].createGraphics();
                gr.drawImage(imageToBuffer, 0, 0, this.getIconWidth(),
                        this.getIconHeight(), this.getIconWidth() * j,
                        this.getIconHeight() * i, this.getIconWidth() * j + this.getIconWidth(),
                        this.getIconHeight() * i + this.getIconHeight(), null);
                gr.dispose();
            }
        }
        return imagePart;
    }

    /**
     * Accesseurs
     * @return le longueur de l'icone decoupé
     */
    public int getIconWidth() {
        return iconWidth;
    }
    /**
     * Accesseurs
     * @return le hauteur de l'icone decoupé
     */
    public int getIconHeight() {
        return iconHeight;
    }
    /**
     * Accesseurs
     * @return un tableau des portions d'image decoupé
     */
    public BufferedImage[] getImagePart() {
        return imagePart;
    }
    /**
     * Accesseurs
     * @return le plateau du jeu de puzzle
     */
    public Board getGrid() {
        return grid;
    }
    /**
     * Accesseurs
     * @return l'image du jeu
     */
    public BufferedImage getImageToBuffer() {
        return imageToBuffer;
    }

}


