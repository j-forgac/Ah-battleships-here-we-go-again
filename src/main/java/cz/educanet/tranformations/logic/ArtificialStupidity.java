package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.concurrent.TimeUnit;

public class ArtificialStupidity {

	Battlefield battlefield;

	public ArtificialStupidity(Battlefield battlefield) {
		this.battlefield = battlefield;
	}

	public void play() throws InterruptedException {
		//TimeUnit.SECONDS.sleep((long)0.21);
		System.out.print("");
		if (battlefield.getPlayer().isOnMove()) {
			shoot();
		}
	}

	public void shoot() {
		int x = (int) (Math.random() * battlefield.getHeight());
		int y = (int) (Math.random() * battlefield.getHeight());
		Coordinate c = new Coordinate(y, x);
		if(!battlefield.evaluateAttack(c)){
			MyGame.winner = battlefield.getPlayer();
		}
	}
}
