package views;

import models.Board;
import models.Piece;
import util.EcouteurModel;
import util.ModeleEcoutable;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Nous permettant de créer notre grille dans notre interface
 */
public class Grid extends JPanel implements EcouteurModel {

    /**
     * grid : une instance du plateau de jeu
     * Une Matrice de Piece
     */
    private Board grid;
    /**
     * heigth : la hauteur de notre puzzle qui va dependre de l'image données
     */
    private int heigth;
    /**
     * width : la longueur de notre puzzle qui va dependre de l'image données
     */
    private int width;
    /**
     * tileWidth : la longueurs de chaque case du puzzle
     */
    private int tileWidth;
    /**
     * tileHeigth : la longueurs de chaque case du puzzle
     */
    private int tileHeigth;
    /**
     * AllImgPart : on reccupere tout les images resultant de l'image decoupe
     */
    private BufferedImage[] allImgPart;

    /**
     * Constructeur
     * pemet de mettre un ecouteur sur notre graphique
     * on découpe une image a nbLine (nombre des ligne de notre plateau de jeu) ligne et nbColumn colonne (nombre de colonnes de notre plateau de jeu)
     * et initialise la hauteur et la longueur des case de puzzle
     * ainsi il lance la methode initComponenet qui dessine les differents elements graphiques.
     * @throws IOException
     */
    public Grid(Board grid) throws IOException {
        this.grid = grid;
        this.grid.ajoutEcouteur(this); // On ajpoute un ecouteur sur notre graphique
        // on découpe une image a n ligne et c colonne .
        // n = nombre des ligne de notre plateau de jeu
        // m = nombre de colonnes de notre plateau de jeu
        CutImage cutImage = new CutImage(this.grid);
        allImgPart = cutImage.getImagePart();
        this.heigth = cutImage.getImageToBuffer().getHeight();
        this.width = cutImage.getImageToBuffer().getWidth();
        //On initialise la hauteur et la longeure des cases du puzzles par leurs valeurs.
        tileWidth = cutImage.getIconWidth();
        tileHeigth = cutImage.getIconHeight();
        // on dessine les case graphique de notre puzzle et on les remplis par les portions des images decoupées.
        initComponent();
    }

    /**
     * iniComponent : initialise la dimension de notre fenetre de jeu
     * en mettant un MouseListener pour ecouter sur la souris et reccuperer la position ou le joueure a cliqué
     */
    public void initComponent() {
        setPreferredSize(new Dimension(this.width, this.heigth));
        setBackground(Color.WHITE);
        // Cree un MouseListener pour ecouter sur la souris et reccuperer la position ou le joueure a cliqué
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int ex = e.getX(); // On retourne la position X du cliquée par la souris
                int ey = e.getY(); // On retourne la position Y du cliquée par la souris
                // On convertit de la position cliquée par la souris a une position dans notre plateau
                // En divisant sur la hauteur pour obtenir la coordonnée i
                // Et En divisant sur la hauteur pour obtenir la coordonnée j
                int i = ey / tileHeigth;
                int j = ex / tileWidth;
                // On initialise la piece cliquée par la souris afin de faire le swap si il est possible
                Piece clickPiece = new Piece(i, j, grid.getBoard()[i][j].getElement());
                grid.swap2Pieces(clickPiece);
            }
        });

    }

    /**
     * drawGrid : initialise les dimensions de notre fenetre de jeu
     * Dessine des rectangle et reccupere l'image qui corresponde a l'element de la piece dans le board
     * ainsi il dessine l'icone ou la portion de l'image reccuperé dans le graphe. (rectangle).
     */
    public void drawGrid(Graphics2D g) {
        for (int i = 0; i < grid.getNbLine(); i++) {
            for (int j = 0; j < grid.getNbColumn(); j++) {
                // On convertit de la position [i][j] du plateau a une position dans notre du graphique
                // En la multulpiant par la longeurs et la hauteurs de nos case du puzzle
                int x = j * tileWidth;
                int y = i * tileHeigth;

                if (grid.getBoard()[i][j].getElement() == 0) { // Si on roncontre la piece vide
                    g.fillRect(x, y, tileWidth, tileHeigth);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, tileWidth, tileHeigth);
                    g.setColor(new Color(0, 0, 0, 255)); // On colore le contenue de la pice par le noir
                    if (grid.isFinished()) {
                        g.setColor(Color.BLACK);
                        new Victory();
                    }
                } else {
                    // On reccupere l'image qui corresponde a l'element de la piece dans le board
                    Icon icone = new ImageIcon(allImgPart[grid.getBoard()[i][j].getElement() - 1]);
                    g.setColor(getForeground());
                    g.fillRect(x, y, tileWidth, tileHeigth);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, tileWidth, tileHeigth);
                    icone.paintIcon(this, g, x, y); // on dessine l'icone (portion de l'image dans le rectangle)
                }
            }
        }
    }

    /**
     * paintCommponent : appelle la methode drawGrid pour dessiner le plateau finale du puzzle
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2D);

    }

    /**
     * modelMiseAJour : methode implementée afin d'effectuer le miseAjour
     * redessiner le contenue du fenetre si un changement est notifié par les ecouteurs.
     */
    @Override
    public void modelMiseAJour(ModeleEcoutable source) {
        this.repaint();
    }

}
