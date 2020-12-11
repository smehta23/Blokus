import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable {

    public Game() {
    }

    public void run() {

        JFrame frame = new JFrame("Blokus");

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        StateLabel stateLabel = new StateLabel();
        panel.add(stateLabel);

        Board board = new Board();
        board.setMaximumSize(board.getPreferredSize());
        board.setMinimumSize(board.getPreferredSize());
        panel.add(board);

        PlayerPieceSet pset = new PlayerPieceSet();
        panel.add(pset);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton rotPiece = new JButton("Rotate Piece");
        rotPiece.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                State.rotatePlacedPiece();
                board.repaint();
            }
        });
        buttons.add(rotPiece);

        JButton nextTurn = new JButton("Next Turn");
        nextTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                State.nextTurn();
                board.repaint();
                pset.repaint();
                pset.deselectAll();
                stateLabel.repaint();
            }
        });
        buttons.add(nextTurn);

        JButton undo = new JButton("Undo Move");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                State.undo();
                board.repaint();
                pset.repaint();
                stateLabel.repaint();

            }
        });
        buttons.add(undo);

        panel.add(buttons);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem openItem = new JMenuItem("Open");
        saveAsItem.addActionListener(new SaveGame(frame));
        openItem.addActionListener(new OpenGame(frame, board, pset, stateLabel));

        fileMenu.add(saveAsItem);
        fileMenu.add(openItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutGame = new JMenuItem("About Game");
        JMenuItem gameInstructions = new JMenuItem("Game Instructions");
        JMenuItem gameFeatures = new JMenuItem("Game Features");
        aboutGame.addActionListener(new InfoWindowOpener(frame, AboutExcerpts.ABOUT_GAME));
        gameInstructions.addActionListener(new InfoWindowOpener(
                frame, AboutExcerpts.GAME_INSTRUCTIONS));
        gameFeatures.addActionListener(new InfoWindowOpener(frame, AboutExcerpts.GAME_FEATURES));

        helpMenu.add(aboutGame);
        helpMenu.add(gameInstructions);
        helpMenu.add(gameFeatures);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setSize(1280, 800);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}