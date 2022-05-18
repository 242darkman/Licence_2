package views;

import views.GUI.PuzzleGUI;
import models.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WelcomeFrame extends JFrame {

    /** Insets une représentation des bordures d'un conteneur.
     */
    private static final Insets insets = new Insets(10, 20, 0, 20);
    /** sizeGrid : la taille de notre grille de jeu
     */
    private int sizeGrid;
    /** les dimensions de la fenetre de bienVenu ou le joueur choisi la taille pour commencer
     */
    private final int height = 330;
    private final int width = 530;

    public WelcomeFrame() {
        super("WelcomeWindow ");
        setResizable(true);
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
        getContentPane().setLayout(boxLayout);
        // on initialise notre taille de grille au cas ou le joueur ne choisit pas une taille
        // on lance la partie avec une taille standard qui egale a 3
        this.sizeGrid=3;
        initComponents();
        //
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        
    }

    /**
     * initComponenets
     * initialise le contenu de la fenetre en mettant les differenet composants dans la fenetre
     * label "Jeu De Puzzle" tout au centre
     * label "Choisir La Taille Du Puzzle" tout au centre
     * un JComboBox afin de donner la possibilite au joueure de choisir la taille du grille de jeu
     * Deux Boutton 'Jouer' afin de commencer la partie.
     */

    public void initComponents() {
        JLabel labelJeuPuzzle = new JLabel("Jeu De Puzzle");
        JLabel chooseSizeGrid = new JLabel("Choisir La Taille Du Puzzle");
        JButton playButton = new JButton("Jouer");
        JComboBox listSizes;
        JLabel licenceNames = new JLabel("ACHCHAB-VOUVOU-AIT_HAMMOUDA-DEFOURNEAUX   2020-2021/UniCAEN");
        Object[] elements = new Object[]{3, 4, 5, 6, 7, 8, 9, 10};

        listSizes = new JComboBox(elements);
        JPanel panel = new JPanel(new GridBagLayout());

        labelJeuPuzzle.setFont(labelJeuPuzzle.getFont().deriveFont(40f).deriveFont(Font.BOLD));
        addComponent(panel, labelJeuPuzzle, 0, 0, GridBagConstraints.CENTER, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL);
        chooseSizeGrid.setFont(chooseSizeGrid.getFont().deriveFont(15f));
        addComponent(panel, chooseSizeGrid, 2, 1, 1, 1, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.CENTER);
        addComponent(panel, listSizes, 2, 2, 1, 1, 4, 8, GridBagConstraints.NORTH, GridBagConstraints.CENTER);
        addComponent(panel, playButton, 1, 3, 0, 1, 20, 8, GridBagConstraints.NORTH, GridBagConstraints.CENTER);

        licenceNames.setFont(licenceNames.getFont().deriveFont(11f).deriveFont(Font.BOLD));
        addComponent(panel, licenceNames, 1, 3, 0, 1, 0, 0, GridBagConstraints.PAGE_END, GridBagConstraints.CENTER);
        add(panel);

        // mettre un ActionListener sur le JComboBox afin de reccuperer la taille de grille choisi par l'utilisateur
        listSizes.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        sizeGrid= (int) listSizes.getSelectedItem();
                    }
                }
        );
        // mettre un ActionListener sur la boutton Jouer afin de lancer une partie
        // sert a lancer une instance la classe PuzzleGUI
        playButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            dispose();
                            new PuzzleGUI(new Board(sizeGrid,sizeGrid));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    /**
     * addComponent ajoute des composants dans une grille de lignes et de colonnes,
     * permettant aux composants spécifiés de s'étendre sur plusieurs lignes ou colonnes.
     * @param container le conteneur sur lequel on va travailler
     * @param component l'element qu'on va ajouter soit un label ou ...
     * @param gridx la position x dans le conteneur
     * @param gridy la position x dans le conteneur
     * @param gridwidth combien de case va t-il occuper en longueur
     * @param gridheight combien de case va t-il occuper en hauteur
     * @param ipadx sa taille suivant l'axe des x
     * @param ipady sa taille suivant l'axe des y
     * @param anchor déterminer où placer le composant (dans la zone).
     * @param fill déterminer si et comment redimensionner le composant.
     */
    private static void addComponent(Container container, Component component, int gridx, int gridy,
                                     int gridwidth, int gridheight, int ipadx, int ipady, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        gbc.ipady = ipady;
        gbc.ipadx = ipadx;
        container.add(component, gbc);
    }

}
