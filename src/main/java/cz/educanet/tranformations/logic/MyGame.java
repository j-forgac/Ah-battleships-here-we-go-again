package cz.educanet.tranformations.logic;

import java.util.Scanner;

public class MyGame {

	public static Player onMove;

	public static Player[] players = new Player[2];

	public static Player winner = null;


	public static void main(String[] Args) throws InterruptedException {

		//-----------------------GETTING-USER-INPUT-(SIZE-OF-FIELD)--------------------------//

		Scanner mySc = new Scanner(System.in);
		int size = 0;
		String playerType;
		while (size < 5 || size > 301) {
			System.out.println("Zadejte vysku pole");
			size = Integer.parseInt(mySc.nextLine());
		}

		//-----------------------GETTING-USER-INPUT-(PLAYER-TYPES)-------------------------//

		for (int x = 1; x < 3; x++) {
			boolean nonValid = true;
			while (nonValid) {
				System.out.println("Zadejte typ hrace " + x + " (h = human, a = AI): ");
				playerType = mySc.nextLine();
				if(playerType.equals("h")){
					nonValid = false;
					Battlefield pole1 = new Battlefield(size);
					pole1.placeShips();
					players[x-1] = new Player(pole1, null, false);
				} else if(playerType.equals("a")){
					nonValid = false;
					Battlefield pole2 = new Battlefield(size);
					pole2.placeShips();
					players[x-1] = new Player(pole2, new ArtificialIntelligence(pole2), false);
				}
			}
		}


		//---------------------GAME-CYCLE--------------------------//

		onMove = players[0];
		while (MyGame.winner == null) {
			for (Player player : players) {
				if (player.getArtificialStupidity() != null && MyGame.winner == null) {
					player.getArtificialStupidity().play();
				}
			}
		}
		System.out.println("Vsechny lode zniceny, vas pocet tahu: " + winner.getScore());
	}
}

