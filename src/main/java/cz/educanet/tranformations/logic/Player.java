package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.presentation.Window;

public class Player {
	private Battlefield battlefield;
	private ArtificialIntelligence artificialIntelligence;
	public boolean turn = false;

	//*public boolean moved = true;

	public Player(Battlefield battlefield, ArtificialIntelligence artificialIntelligence, boolean aiTest) {
		if(artificialIntelligence == null){
			new Window(800, 800, "Player", battlefield, true);
		}else if(!aiTest){
			new Window(800, 800, "AI", battlefield, false);
		}
		this.battlefield = battlefield;
		this.battlefield.setPlayer(this);
		this.artificialIntelligence = artificialIntelligence;
	}

	public boolean isOnMove() {
		return this == MyGame.onMove;
	}

	public ArtificialIntelligence getArtificialStupidity() {
		return artificialIntelligence;
	}

	public int getScore(){
		return battlefield.score;
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
