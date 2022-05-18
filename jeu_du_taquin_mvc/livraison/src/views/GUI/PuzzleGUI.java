package views.GUI;

import controllers.KeyBoard;
import models.Board;
import views.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Nous permet de créer la version interface de notre jeux
 */
public class PuzzleGUI extends JFrame {

    /**
     * On créer un plateau
     * @throws IOException
     */
    public PuzzleGUI() throws IOException {
        this(new Board(3, 3));
    }

    /**
     * On créer notre grille d'image, et on ajoute les touche du clavier
     * @param plateau
     * @throws IOException
     */
    public PuzzleGUI(Board plateau) throws IOException {
        super("JEU De PUZZLE");
        Grid vue = new Grid(plateau);

        JFrame frame = new JFrame();
        frame.add(vue, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        KeyBoard keyBoard = new KeyBoard(frame, plateau);
    }
}