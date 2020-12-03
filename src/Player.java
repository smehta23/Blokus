import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player{

    private static int playerNumber = 1;
    private Set<Piece> pieces;
    private String name;
    private Color color;
    private int number;
    
    
    public Player() {
        this.name = "Player" + playerNumber;
        this.number = playerNumber;
        this.color = State.GAME_COLORS[playerNumber - 1];
        this.pieces = new HashSet<Piece>();
        GamePieces [] gamePieces = GamePieces.class.getEnumConstants();
        for (GamePieces piece : gamePieces) {
            Piece p = new Piece(piece.getStructure(), color);
            this.pieces.add(p);
        }

        playerNumber++;
    }
    
    public Player(Set<Piece> pieces, String name, Color color, int number) {
        this.pieces = pieces;
        this.name = name;
        this.color = color;
        this.number = number;
    }
    
    public void pieceMoved(Piece p) {
        pieces.remove(p);
    }
    
    public void pieceMoved(int [][] pieceStructure) {
        pieces.remove(new Piece(pieceStructure, color));
    }
    
    public String getName() {
        return name;
    }
    
    public Color getColor() {
        return new Color(color.getRGB());
    }
    
    public int getNumber() {
        return number;
    }
    
    public Set<Piece> getPieces(){
        Set<Piece> copyOfPieces = new HashSet<Piece>(this.pieces); 
        return copyOfPieces;
    }

}
