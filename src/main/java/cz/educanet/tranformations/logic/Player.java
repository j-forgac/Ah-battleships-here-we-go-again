package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.presentation.Window;

import java.util.concurrent.TimeUnit;

public class Player {
	private Battlefield battlefield;
	private ArtificialStupidity artificialStupidity;
	public boolean turn = false;

	//*public boolean moved = true;

	public Player(Battlefield battlefield, ArtificialStupidity artificialStupidity) {
		if(artificialStupidity == null){
			new Window(800, 800, "Player", battlefield, true);
		}else{
			new Window(800, 800, "AI", battlefield, false);
		}
		this.battlefield = battlefield;
		this.battlefield.setPlayer(this);
		this.artificialStupidity = artificialStupidity;
	}

	public boolean isOnMove() {
		return this == MyGame.onMove;
	}

	public ArtificialStupidity getArtificialStupidity() {
		return artificialStupidity;
	}

	public void move() throws InterruptedException {
		/* if (artificialStupidity == null) {
			moved = false;
			while (!moved) {
				TimeUnit.SECONDS.sleep((long)0.5);
			}
		} */
	}
}
