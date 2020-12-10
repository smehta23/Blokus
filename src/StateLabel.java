import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StateLabel extends JComponent {
    

    private JLabel playerNameLabel;
    private JLabel pieceSetSizeLabel;
    private JLabel turnNumberLabel;
    private JLabel gameStatusLabel;
    
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel player3ScoreLabel;
    private JLabel player4ScoreLabel;
    
    //private JComponent playerInfo = new JComponent();
    private class PlayerInfo extends JComponent {
        public PlayerInfo() {
            this.setLayout(new GridLayout(4, 1, 1, 1));
        }
        
    }
    
    private class GameInfo extends JComponent {
        public GameInfo() {
            this.setLayout(new GridLayout(4, 1, 1, 1));
        }
    }
    


    public StateLabel() {
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(2, 1, 1, 1));
        PlayerInfo playerInfo = new PlayerInfo();
        GameInfo gameInfo = new GameInfo();
        
        player1ScoreLabel = new JLabel(State.getPlayer(0).getName() + " Score: " + State.getPlayer(0).getScore());
        playerInfo.add(player1ScoreLabel);
        player1ScoreLabel.setForeground(State.getPlayer(0).getColor().darker());
        player2ScoreLabel = new JLabel(State.getPlayer(1).getName() + " Score: " + State.getPlayer(1).getScore());
        playerInfo.add(player2ScoreLabel);
        player2ScoreLabel.setForeground(State.getPlayer(1).getColor().darker());
        player3ScoreLabel = new JLabel(State.getPlayer(2).getName() + " Score: " + State.getPlayer(2).getScore());
        playerInfo.add(player3ScoreLabel);
        player3ScoreLabel.setForeground(State.getPlayer(2).getColor().darker());
        player4ScoreLabel = new JLabel(State.getPlayer(3).getName() + " Score: " + State.getPlayer(3).getScore());
        playerInfo.add(player4ScoreLabel);
        player4ScoreLabel.setForeground(State.getPlayer(3).getColor().darker());
        playerNameLabel = new JLabel("Current Player: " + State.getCurrentPlayer().getName());
        
        
        gameInfo.add(playerNameLabel);
        turnNumberLabel = new JLabel("Turn Number: " + State.getTurnNumber());
        gameInfo.add(turnNumberLabel);
        pieceSetSizeLabel = new JLabel("Player Piece Set Size: " + State.getCurrentPlayer().getPiecesSize() + "");
        gameInfo.add(pieceSetSizeLabel);
        gameStatusLabel = new JLabel(State.getStatus());
        gameInfo.add(gameStatusLabel);
        
        
        playerInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        gameInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(playerInfo);
        this.add(gameInfo);
        
        
//        if (State.pieceToMove!=null) {
//            pieceSelectedLabel = new JLabel("Piece Selected is " + State.pieceToMove.getName());
//        } else {
//            pieceSelectedLabel = new JLabel("Piece Selected: null");
//        }
//        this.add(pieceSelectedLabel);
        
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        turnNumberLabel.setText("Turn Number: " + State.getTurnNumber());
        pieceSetSizeLabel.setText("Player Piece Set Size: " + State.getCurrentPlayer().getPieces().size());
        playerNameLabel.setText("Current Player: " + State.getCurrentPlayer().getName());
        player1ScoreLabel.setText(State.getPlayer(0).getName() + " Score: " + State.getPlayer(0).getScore());
        player2ScoreLabel.setText(State.getPlayer(1).getName() + " Score: " + State.getPlayer(1).getScore());
        player3ScoreLabel.setText(State.getPlayer(2).getName() + " Score: " + State.getPlayer(2).getScore());
        player4ScoreLabel.setText(State.getPlayer(3).getName() + " Score: " + State.getPlayer(3).getScore());
        gameStatusLabel.setText("<html>" + "<B>" + State.getStatus() + "</B>" + "</html>");
        gameStatusLabel.setFont(new Font("Serif", Font.PLAIN, 20));

    }
    
//    @Override 
//    public Dimension getPreferredSize() {
//        return new Dimension (150, 150);
//    }

}
