import java.awt.Color;
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
        this.pieceMoved(p.getStructure());
    }
    
    public void pieceMoved(int [][] pieceStructure) {
        boolean removed = false;
        int rotateDegree = 0;
        while (!removed && rotateDegree <= 360) {
            removed = pieces.remove(new Piece(pieceStructure, color));
            pieceStructure = State.ccRotation(pieceStructure);
            rotateDegree+=90;
        }
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
    
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Player) {
            Player u = (Player) o;
            result = this.number == u.getNumber() && this.name.equals(u.getName()) 
                    && this.color.equals(u.getColor()) && this.pieces.equals(u.getPieces());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.number;
    }

}