package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ImageDisplay extends JFrame {

    ImageDisplay() {
        setLayout(new FlowLayout());

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("image/image.png")));
        add(new JLabel(image));
    }

    public void display() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }
}
