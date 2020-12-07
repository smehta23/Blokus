import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StateLabel extends JComponent {
    

    JLabel playerNameLabel;
    JLabel pieceSetSizeLabel;
    JLabel turnNumberLabel;
    JLabel pieceSelectedLabel;
    JLabel gameStatusLabel;
    
    JLabel player1ScoreLabel;
    JLabel player2ScoreLabel;
    JLabel player3ScoreLabel;
    JLabel player4ScoreLabel;
    


    public StateLabel() {
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(8, 1, 1, 1));
        player1ScoreLabel = new JLabel("Player 1 Score: " + State.players[0].getScore());
        this.add(player1ScoreLabel);
        player2ScoreLabel = new JLabel("Player 2 Score: " + State.players[1].getScore());
        this.add(player2ScoreLabel);
        player3ScoreLabel = new JLabel("Player 3 Score: " + State.players[2].getScore());
        this.add(player3ScoreLabel);
        player4ScoreLabel = new JLabel("Player 4 Score: " + State.players[3].getScore());
        this.add(player4ScoreLabel);
        playerNameLabel = new JLabel("Current Player: " + State.getCurrentPlayer().getName());
        this.add(playerNameLabel);
        turnNumberLabel = new JLabel("Turn Number: " + State.turnNumber+"");
        this.add(turnNumberLabel);
        pieceSetSizeLabel = new JLabel("Player Piece Set Size: " + State.currentPlayer.getPiecesSize() + "");
        this.add(pieceSetSizeLabel);
        gameStatusLabel = new JLabel(State.gameStatus);
        this.add(gameStatusLabel);
        
        
//        if (State.pieceToMove!=null) {
//            pieceSelectedLabel = new JLabel("Piece Selected is " + State.pieceToMove.getName());
//        } else {
//            pieceSelectedLabel = new JLabel("Piece Selected: null");
//        }
//        this.add(pieceSelectedLabel);
        
    }
    
    @Override
    public void paintComponent(Graphics gc) {

        turnNumberLabel.setText("Turn Number: " + State.turnNumber);
        pieceSetSizeLabel.setText("Player Piece Set Size: " + State.currentPlayer.getPieces().size());
        playerNameLabel.setText("Current Player: " + State.getCurrentPlayer().getName());
        player1ScoreLabel.setText("Player 1 Score: " + State.players[0].getScore());
        player2ScoreLabel.setText("Player 2 Score: " + State.players[1].getScore());
        player3ScoreLabel.setText("Player 3 Score: " + State.players[2].getScore());
        player4ScoreLabel.setText("Player 4 Score: " + State.players[3].getScore());
        gameStatusLabel.setText(State.gameStatus);
        //        if (State.pieceToMove!=null) {
//            pieceSelectedLabel.setText("Piece Selected is " + State.pieceToMove.getName());
//        } else {
//            pieceSelectedLabel.setText("Piece Selected: null");
//        }

    }

}
