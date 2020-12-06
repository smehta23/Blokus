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
    


    public StateLabel() {
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(4, 1, 1, 1));
        playerNameLabel = new JLabel("Current Player: " + State.getCurrentPlayer().getName());
        this.add(playerNameLabel);
        turnNumberLabel = new JLabel("Turn Number: " + State.turnNumber+"");
        this.add(turnNumberLabel);
        pieceSetSizeLabel = new JLabel("Player Piece Set Size: " + State.currentPlayer.getPiecesSize() + "");
        this.add(pieceSetSizeLabel);
        if (State.pieceToMove!=null) {
            pieceSelectedLabel = new JLabel("Piece Selected is " + State.pieceToMove.getName());
        } else {
            pieceSelectedLabel = new JLabel("Piece Selected: null");
        }
        this.add(pieceSelectedLabel);
        
    }
    
    @Override
    public void paintComponent(Graphics gc) {

        turnNumberLabel.setText("Turn Number: " + State.turnNumber);
        pieceSetSizeLabel.setText("Player Piece Set Size: " + State.currentPlayer.getPieces().size());
        playerNameLabel.setText("Current Player: " + State.getCurrentPlayer().getName());
        if (State.pieceToMove!=null) {
            pieceSelectedLabel.setText("Piece Selected is " + State.pieceToMove.getName());
        } else {
            pieceSelectedLabel.setText("Piece Selected: null");
        }

    }

}
