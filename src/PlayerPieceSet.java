import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class PlayerPieceSet extends JComponent{
    
    private final int width = 2;
    private final int height = 11;
    private Color c;

    public PlayerPieceSet(Color c) {
        this.c = c;
        this.setBackground(Color.black);
        GridLayout layout = new GridLayout(width, height, 1, 1);
        this.setLayout(layout);
        Pieces [] pieceStructures = Pieces.class.getEnumConstants();
        for (Pieces structure : pieceStructures) {
            int initX, initY;
            initX = initY = 0;
            for (int i = 0; i < p.length; i++) {
                for (int j = 0; j < p[0].length; j++) {
                    gc.fillRect(initX, initY, 20, 20);
                    initX += 20;
                }
                initY+=20;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
    }
    
    

}
