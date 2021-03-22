package cz.educanet.tranformations.presentation.canvas;

import cz.educanet.tranformations.logic.Battlefield;
import cz.educanet.tranformations.logic.models.Coordinate;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private Battlefield battlefield;

	private int cellWidth;
	private int cellHeight;
	private boolean notWon = true;

	public Canvas(Battlefield battlefield, int size) {
		this.battlefield = battlefield;
		SCREEN_WIDTH = size;
		SCREEN_HEIGHT = size;
		setBackground(Color.BLACK);

		addMouseListener(new CanvasMouseListener((x, y, button) -> {
			if(notWon) {
				Coordinate c = new Coordinate(x / cellWidth, y / cellHeight);
				notWon = battlefield.evaluateAttack(c.getY(), c.getX());
				System.out.println(notWon);
			}
		}));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		cellWidth = (getSize().width - 1) / SCREEN_WIDTH;
		cellHeight = getSize().height / SCREEN_HEIGHT;

		for (int i = 0; i < SCREEN_WIDTH; i++) {
			for (int j = 0; j < SCREEN_HEIGHT; j++) {
				Coordinate cell = new Coordinate(j, i);
				switch (battlefield.battlefield[cell.getY()][cell.getX()].getType()) {
					case "MISS" -> g.setColor(new Color(255, 0, 0));
					case "HIT" -> g.setColor(new Color(8, 255, 0));
					default -> g.setColor(new Color(0, 102, 255));
				}
				g.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);


				g.setColor(Color.darkGray);
				g.drawRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);

			}
		}
	}
}

