package cz.educanet.tranformations.logic;



import java.util.ArrayList;
import java.util.HashMap;

public class AiBenchmark {

	public static int samples = 10000;

	public static int size = 10;

	public static HashMap<Integer, Integer> output = new HashMap<>();

	public static ArrayList<Integer> output2 = new ArrayList<>();


	public static void main(String[] Args) throws InterruptedException {
		int totalMoves = 0;
		for (int x = 17; x <= 100; x++) {
			output.put(x, 0);
		}

		for (int x = 0; x < samples; x++) {
			Battlefield pole1 = new Battlefield(size);
			pole1.placeShips();
			ArtificialIntelligence ai = new ArtificialIntelligence(pole1);
			Player player = new Player(pole1, ai, true);
			while (MyGame.winner == null) {
				MyGame.onMove = player;
				player.getArtificialStupidity().play();
			}
			//*System.out.println("Vsechny lode zniceny, vas pocet tahu: " + MyGame.winner.getScore());
			output.put(MyGame.winner.getScore(), output.get(MyGame.winner.getScore()) + 1);
			MyGame.winner = null;
		}
		for (int x = 17; x <= 100; x++) {
			totalMoves += output.get(x) * x;
			for(int y = 0; y < output.get(x);y++){
				System.out.println(x);
				output2.add(x);
			}
		}

		System.out.println("Average moves to win: " + totalMoves / samples);
		if (samples % 2 != 0) {
			System.out.println("Median = " + output2.get(samples / 2));
		} else {
			System.out.println("Median = " + ((output2.get(samples / 2) + output2.get(samples / 2)) / 2));
		}





	}
}
