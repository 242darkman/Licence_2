package views;

import views.GUI.PuzzleGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Victory extends JFrame {

    /** Insets une représentation des bordures d'un conteneur.
     */
    private static final Insets insets = new Insets(10, 20, 0, 20);
    /** les dimensions de la fenetre Victory
     */
    private final int height = 300;
    private final int width = 500;

    /**
     * iniComponent : initialise la dimension de notre fenetre de jeu
     * en mettant un MouseListener pour ecouter sur la souris et reccuperer la position ou le joueure a cliqué
     */
    public Victory(){
        super("Victory Window");
        setResizable(true);
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
        getContentPane().setLayout(boxLayout);

        initComponents();

        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
    }
    /**
     * initComponenets
     * initialise le contenu de la fenetre en mettant les differenet elements
     * label "CONGATULATION " tout au centre
     * Deux Bouttons une pour lancer une autre patie l'autre pour quitter le jeu.
     */
    public void initComponents() {
        JLabel labelJeuPuzzle = new JLabel("Congratulation");
        JButton nouvellePartie = new JButton("Nouvelle Partie");
        JButton exit = new JButton("Quitter");
        // On utilise un GridBagLayout pour placer les composants dans une grille de lignes et de colonnes
        JPanel panel = new JPanel(new GridBagLayout());

        labelJeuPuzzle.setFont(labelJeuPuzzle.getFont().deriveFont(40f).deriveFont(Font.BOLD)); // on change le font de l'ecriture
        addComponent(panel, labelJeuPuzzle, 0, 0, GridBagConstraints.CENTER, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        addComponent(panel, exit, 1, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.CENTER);
        addComponent(panel, nouvellePartie, 2, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.CENTER);
        panel.setBackground(new Color(146, 171, 161, 238));
        add(panel); // on ajoute le panel

        // mettre un ActionListener sur la boutton quitter qui permer de faire un Exit System afin d'arreter le processus
        exit.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        System.exit(0);
                    }
                }
        );
        // mettre un ActionListener sur la boutton nouvelle partie qui lance une instance la classe PuzzleGui afin de lancer une partie
        nouvellePartie.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        try {
                            dispose();
                            new PuzzleGUI();
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
