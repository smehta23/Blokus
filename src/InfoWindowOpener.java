import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class InfoWindowOpener implements ActionListener {

    Frame frame;
    String windowTitle;
    String text;

    public InfoWindowOpener(Frame frame, AboutExcerpts windowTitle) {
        this.windowTitle = windowTitle.name();
        this.text = windowTitle.getExcerpt();
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog dialog = new JDialog(frame, windowTitle);
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        dialog.add(textArea);
        dialog.setVisible(true);
        dialog.setSize(400, 400);
        dialog.setResizable(false);

    }

}
