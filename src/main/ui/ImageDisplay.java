package ui;

import model.logging.Event;
import model.logging.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Objects;

public class ImageDisplay extends JFrame implements WindowListener {

    ImageDisplay() {
        setLayout(new FlowLayout());

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("image/image.png")));
        add(new JLabel(image));

        addWindowListener(this);
    }

    public void display() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        printEventsToConsole();
        System.exit(0);
    }

    //EFFECTS: prints all logged events to the console.
    private void printEventsToConsole() {
        EventLog log = EventLog.getInstance();
        for (Event event : log) {
            System.out.printf("%s| %s%n", event.getDate(), event.getDescription());
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
