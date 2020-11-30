import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class Square extends JComponent {
    
    private Color c;
    private Dimension d;

    public Square(Dimension d) {
        this.c = Color.GRAY;
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
    
    
    public void setColor(Color c) {
        this.c = c;
    }

}
