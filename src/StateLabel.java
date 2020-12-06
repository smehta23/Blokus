import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StateLabel extends JComponent {
    
//    StringBuffer curPlayer;
//    StringBuffer pieceSetSize;
//    StringBuffer curTurnNumber;
//    JLabel curPlayerLabel;
//    JLabel pieceSetSizeLabel;
    JLabel curTurnNumberLabel;

    public StateLabel() {
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(2, 1, 1, 1));
        curTurnNumberLabel = new JLabel(State.turnNumber+"");
        this.add(curTurnNumberLabel);
//        curPlayerLabel = new JLabel("Current Player: " + State.getCurrentPlayer().getName());
//        this.add(curPlayerLabel);
//        pieceSetSizeLabel = new JLabel("PieceSet Size: " + State.getCurrentPlayer().getPieces().size());
//        this.add(pieceSetSizeLabel);
//        curTurnNumber = new StringBuffer("CurrentTurn#: " + State.turnNumber);
//        curTurnNumberLabel = new JLabel(curTurnNumber.toString());
//        this.add(curTurnNumberLabel);
//        if (State.pieceToMove!=null) {
//            this.add(new JLabel("PieceToMove: " + State.pieceToMove.getName()));
//        }
//        if (State.piecePrevMoved!=null) {
//            this.add(new JLabel("PiecePrevMoved: " + State.piecePrevMoved.getName()));
//        }
    }
    
    @Override
    public void paintComponent(Graphics gc) {
//        super.paintComponent(gc);
        this.setBackground(Color.black);
        //this.setLayout(new GridLayout(2, 1, 1, 1));
        //this.remove(curTurnNumberLabel);
        //this.removeAll();
//        this.setLayout(new GridLayout(2, 1, 1, 1));
//        curPlayerLabel = new StringBuffer("Current Player: " + State.getCurrentPlayer().getName());
//        this.add(new JLabel(curPlayerLabel.toString()));
//        pieceSetSizeLabel = new StringBuffer("PieceSet Size: " + State.getCurrentPlayer().getPieces().size());
//        this.add(new JLabel(pieceSetSizeLabel.toString()));
//        curTurnNumberLabel = new StringBuffer("CurrentTurn#: " + State.turnNumber);
//        this.add(new JLabel(curTurnNumberLabel.toString()));
        curTurnNumberLabel.setText(State.turnNumber+ "");
        
        //System.out.println("Repaint StateLabel" + State.turnNumber);
//        curPlayerLabel.replace(
//                0, 
//                curPlayerLabel.toString().length()-1, 
//                "Current Player: " + State.getCurrentPlayer().getName());
//        pieceSetSizeLabel.replace(
//                0, 
//                pieceSetSizeLabel.length()-1, 
//                "PieceSet Size: " + State.getCurrentPlayer().getPieces().size());
//        curTurnNumber.replace(
//                0,
//                curTurnNumber.length()-1, 
//                "CurrentTurn#: " + State.turnNumber);
        
        
        //        if (State.pieceToMove!=null) {
//            this.add(new JLabel("PieceToMove: " + State.pieceToMove.getName()));
//        }
//        if (State.piecePrevMoved!=null) {
//            this.add(new JLabel("PiecePrevMoved: " + State.piecePrevMoved.getName()));
//        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(30, 100);
    }

}
