import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JLabel {

    public static final int SQUARE_LENGTH = 25;
    private Color c;
    private Dimension d;

    public Square() {
        this.c = State.START_COLOR;
        d = new Dimension(SQUARE_LENGTH, SQUARE_LENGTH);
    }

    public Square(Dimension d) {
        this.c = State.START_COLOR;
        this.d = d;
    }

    public Square(Color c) {
        this.c = c;
        d = new Dimension(SQUARE_LENGTH, SQUARE_LENGTH);
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
        return SQUARE_LENGTH;
    }

    public void setColor(Color c) {
        this.c = c;
    }

}
