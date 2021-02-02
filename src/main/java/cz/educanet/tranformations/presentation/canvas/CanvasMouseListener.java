package cz.educanet.tranformations.presentation.canvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasMouseListener implements MouseListener {

    private MouseClickListener listener = null;

    public CanvasMouseListener(MouseClickListener mouseClickListener) {
        this.listener = mouseClickListener;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        listener.click(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
