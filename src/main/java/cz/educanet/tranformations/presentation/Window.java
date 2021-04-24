package cz.educanet.tranformations.presentation;

import cz.educanet.tranformations.logic.Battlefield;
import cz.educanet.tranformations.presentation.canvas.Canvas;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends JFrame {

    public Window(int width, int height, String title, Battlefield battlefield, boolean interact) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        root.setSize(width, height);

        JPanel canvas = new Canvas(battlefield, interact);
        root.add(canvas);

        add(root);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.repaint();
            }
        }, 0, 200);
    }

}
