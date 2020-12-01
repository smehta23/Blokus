import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JComponent {
    
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private Square [][] squares;

    public Board() {
        this.setBackground(Color.black);
        State.setBoardToDefault();
        GridLayout layout = new GridLayout(BOARD_WIDTH, BOARD_HEIGHT, 1, 1);
        this.setLayout(layout);
        squares = new Square [BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                squares[i][j] = new Square(State.getBoardColors()[i][j]);
                this.add(squares[i][j]);
            }
        }
        
        DragListener dragListener = new DragListener();
        ClickListener clickListener = new ClickListener();
        this.addMouseMotionListener(dragListener);
        this.addMouseListener(clickListener);
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        this.setBackground(Color.black);
        GridLayout layout = new GridLayout(BOARD_WIDTH, BOARD_HEIGHT, 1, 1);
        this.setLayout(layout);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                this.add(squares[i][j]);
            }
        }
    }
    
    
    
    private class DragListener extends MouseMotionAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            Point pos = e.getPoint();
            //+1 because each square has an adjacent 1 px border
            int x = pos.x/(Square.getDefaultSize() + 1);
            int y = pos.y/(Square.getDefaultSize() + 1);
            if (x < BOARD_WIDTH && y < BOARD_HEIGHT && x >= 0 && y >= 0) {
                for (int i = 0; i < squares.length; i++) {
                    for (int j = 0; j < squares[i].length; j++) {
                        squares[i][j].setColor(State.getBoardColors()[i][j]);
                    }
                }
                squares[y][x].setColor(Color.RED);
                repaint();
            }
        }
        
    }
    
    private class ClickListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Point pos = e.getPoint();
            //+1 because each square has an adjacent 1 px border
            int x = pos.x/(Square.getDefaultSize() + 1);
            int y = pos.y/(Square.getDefaultSize() + 1);
            if (x < BOARD_WIDTH && y < BOARD_HEIGHT && x >= 0 && y >= 0) {
                for (int i = 0; i < squares.length; i++) {
                    for (int j = 0; j < squares[i].length; j++) {
                        squares[i][j].setColor(State.getBoardColors()[i][j]);
                    }
                }
                squares[y][x].setColor(Color.RED);
                repaint();
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            Point pos = e.getPoint();
            //+1 because each square has an adjacent 1 px border
            int x = pos.x/(Square.getDefaultSize() + 1);
            int y = pos.y/(Square.getDefaultSize() + 1);
            if (x < BOARD_WIDTH && y < BOARD_HEIGHT && x >= 0 && y >= 0) {
                State.setBoardColors(y, x, Color.RED);
                for (int i = 0; i < squares.length; i++) {
                    for (int j = 0; j < squares[i].length; j++) {
                        squares[i][j].setColor(State.getBoardColors()[i][j]);
                    }
                }
                repaint();
            }
        }
        
    }
    
    
    

}
