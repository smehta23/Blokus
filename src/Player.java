import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Player {

    private static int playerNumber = 0;
    private Set<Piece> pieces;
    private String name;
    private Color color;
    private int number;
    private int score = 0;

    public Player() {
        this.name = "Player" + (playerNumber + 1);
        this.number = playerNumber;
        System.out.println("Player " + playerNumber + " created.");
        this.color = State.GAME_COLORS[number];
        this.pieces = new HashSet<Piece>();
        GamePieces[] gamePieces = GamePieces.class.getEnumConstants();
        for (GamePieces piece : gamePieces) {
            Piece p = new Piece(piece.getStructure(), color);
            this.pieces.add(p);
        }
        for (Piece p : pieces) {
            score += p.getNumOfSquares();
        }

        playerNumber++;
    }

    public Player(Set<Piece> pieces, String name, Color color, int number) {
        this.pieces = pieces;
        this.name = name;
        this.color = color;
        this.number = number;
    }

    public boolean pieceMoved(Piece p) {
        return pieces.remove(p);
    }

    public void pieceMoved(int[][] pieceStructure) {
        pieceMoved(new Piece(pieceStructure, color));
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

    public int getScore() {
        score = 0;
        for (Piece p : pieces) {
            score += p.getNumOfSquares();
        }
        return score;
    }

    public Set<Piece> getPieces() {
        Set<Piece> copyOfPieces = new HashSet<Piece>(this.pieces);
        return copyOfPieces;
    }

    public List<Piece> getPiecesReverse() {
        List<Piece> copyOfPieces = new LinkedList<Piece>(this.pieces);
        Collections.reverse(copyOfPieces);
        return copyOfPieces;
    }

    public void setPieces(Set<Piece> pieceSet) {
        this.pieces = new HashSet<Piece>(pieceSet);
    }

    public void addPiece(Piece p) {
        this.pieces.add(p);
    }

    public int getPiecesSize() {
        return pieces.size();
    }
    
    static void resetPlayerCount() {
        playerNumber = 0;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Player) {
            Player u = (Player) o;
            result = this.number == u.getNumber() && 
                    this.name.equals(u.getName()) && 
                    this.color.equals(u.getColor());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.number;
    }

}
