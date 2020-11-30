import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JComponent {
    
    private final int width = 20;
    private final int height = 20;
    private Square [][] squares;

    public Board() {
        this.setBackground(Color.black);
        GridLayout layout = new GridLayout(width, height, 1, 1);
        this.setLayout(layout);
        squares = new Square [width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                squares[i][j] = new Square(new Dimension(30, 30));
                this.add(squares[i][j]);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        this.setBackground(Color.black);
        GridLayout layout = new GridLayout(width, height, 1, 1);
        this.setLayout(layout);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.add(squares[i][j]);
            }
        }
    }
    

}
