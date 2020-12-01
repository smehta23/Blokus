import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class Square extends JLabel {
    
    private Color c;
    private Dimension d;
    
    public Square() {
        this.c = Color.GRAY;
        d = new Dimension (30, 30);
    }

    public Square(Dimension d) {
        this.c = Color.GRAY;
        this.d = d;
    }
    
    public Square(Color c) {
        this.c = c;
    }
    
    public Square(Color c, Dimension d) {
        this.c = c;
        this.d = d;
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
        gc.fillRect(0, 0, d.width, d.height);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return d;
    }
    
    public static int getDefaultSize() {
        return 30;
    }
    
    public void setColor(Color c) {
        this.c = c;
    }

}
