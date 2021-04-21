package cz.educanet.tranformations.presentation.canvas;

import cz.educanet.tranformations.logic.ArtificialIntelligence;
import cz.educanet.tranformations.logic.Battlefield;
import cz.educanet.tranformations.logic.Field;
import cz.educanet.tranformations.logic.MyGame;
import cz.educanet.tranformations.logic.models.Coordinate;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private Battlefield battlefield;
	private ArtificialIntelligence artificialIntelligence;

	private int cellWidth;
	private int cellHeight;

	public Canvas(Battlefield battlefield, boolean interact) {
		this.battlefield = battlefield;
		SCREEN_WIDTH = battlefield.getHeight();
		SCREEN_HEIGHT = battlefield.getHeight();
		setBackground(Color.BLACK);
		if (interact) {
			addListener();
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		cellWidth = (getSize().width - 1) / SCREEN_WIDTH;
		cellHeight = getSize().height / SCREEN_HEIGHT;
		for (int i = 0; i < SCREEN_WIDTH; i++) {
			for (int j = 0; j < SCREEN_HEIGHT; j++) {
				Coordinate cell = new Coordinate(i, j);
				switch (battlefield.getTileByCoordinate(cell)) {
					case MISS -> {
						if ((MyGame.winner == null)) {
							g.setColor(new Color(255, 0, 0));
						} else {
							g.setColor(new Color(0, 102, 255));
						}
					}
					case HIT -> g.setColor(new Color(255, 213, 0));
					case UNKNOWN -> g.setColor(new Color(0, 102, 255));
					case SUNK -> {
						if ((MyGame.winner == null)) {
							g.setColor(new Color(133, 233, 0));
						} else {
							g.setColor(new Color(0, 255, 0));
						}
					}
				}
				g.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);


				g.setColor(Color.darkGray);
				g.drawRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);

			}
		}
	}

	public void addListener() {
		addMouseListener(new CanvasMouseListener((x, y, button) -> {
			Coordinate c = new Coordinate(x / cellWidth, y / cellHeight);
			if (MyGame.winner == null && battlefield.getPlayer().isOnMove() && battlefield.getTileByCoordinate(c) == Field.tileType.UNKNOWN) {
				if (!battlefield.evaluateAttack(c)) {
					MyGame.winner = this.battlefield.getPlayer();
				}
			}
		}));
	}
}

