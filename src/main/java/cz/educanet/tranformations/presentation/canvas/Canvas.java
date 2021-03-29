package cz.educanet.tranformations.presentation.canvas;

import cz.educanet.tranformations.logic.Battlefield;
import cz.educanet.tranformations.logic.models.Coordinate;
import cz.educanet.tranformations.logic.Field.*;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private Battlefield battlefield;

	private int cellWidth;
	private int cellHeight;
	private boolean notWon = true;
	boolean dontStop = true;

	public Canvas(Battlefield battlefield, int size) {
		this.battlefield = battlefield;
		SCREEN_WIDTH = size;
		SCREEN_HEIGHT = size;
		setBackground(Color.BLACK);

		addMouseListener(new CanvasMouseListener((x, y, button) -> {
			battlefield.draw();
			if (notWon) {
				Coordinate c = new Coordinate(x / cellWidth, y / cellHeight);
				notWon = battlefield.evaluateAttack(c);
			} else {
				System.out.println("Vsechny lode zniceny, vas pocet tahu: " + battlefield.score);
			}
		}));
	}

	@Override
	public void paint(Graphics g) {
		if (!notWon && dontStop) {
			dontStop = false;
			System.out.println("Vsechny lode zniceny, vas pocet tahu: " + battlefield.score);
		}
		super.paint(g);
		cellWidth = (getSize().width - 1) / SCREEN_WIDTH;
		cellHeight = getSize().height / SCREEN_HEIGHT;
		for (int i = 0; i < SCREEN_WIDTH; i++) {
			for (int j = 0; j < SCREEN_HEIGHT; j++) {
				Coordinate cell = new Coordinate(j, i);
				switch (battlefield.getTileByCoordinate(cell)) {
					case MISS -> {
						if ((notWon)) {
							g.setColor(new Color(255, 0, 0));
						} else {
							g.setColor(new Color(0, 102, 255));
						}
					}
					case HIT -> {
						if ((notWon)) {
							g.setColor(new Color(255, 213, 0));
						} else {
							g.setColor(new Color(8, 255, 0));
						}
					}
					default -> g.setColor(new Color(0, 102, 255));
				}
				g.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);


				g.setColor(Color.darkGray);
				g.drawRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);

			}
		}
	}
}

