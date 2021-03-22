package cz.educanet.tranformations.logic;

import java.util.*;

public class Battlefield {
	static int height;
	public Field[][] battlefield;


	public Battlefield(int dimensions) {
		height = dimensions;
		battlefield = new Field[height][height];

		myFor(new CallBack() {
			public void call(int x, int y) {
				battlefield[x][y] = Field.createWater();
			}
		});
	}

	public void placeShips() {
		boolean direction;
		int maxBoatSize = Math.min(height, 5);
		ArrayList<Integer[]> possibleCoos;
		for (int boatSize = maxBoatSize; boatSize >= 1; boatSize--) {
			possibleCoos = generateOrder(height);
			direction = new Random().nextBoolean();
			shipBuilt:
			for (int dir = 0; dir < 2; dir++) {
				direction = !direction;
				for (Integer[] possibleCoo : possibleCoos) {
					int cooX = possibleCoo[0];
					int cooY = possibleCoo[1];
					if (fitsInMap(cooX, boatSize) && notCollidingWithOtherShips(cooX, cooY, boatSize)) {
						if (fitsInMap(cooY, boatSize) && notCollidingWithOtherShips(cooX, cooY, boatSize)) {
							if (direction) {
								buildShipV(cooX, cooY, boatSize);
							} else {
								buildShipH(cooX, cooY, boatSize);
							}
						} else {
							buildShipH(cooX, cooY, boatSize);
						}
						break shipBuilt;
					} else {
						if (fitsInMap(cooY, boatSize) && notCollidingWithOtherShips(cooX, cooY, boatSize)) {
							buildShipV(cooX, cooY, boatSize);
							break shipBuilt;
						}
					}
				}
			}
		}
	}

	public boolean fitsInMap(int coos, int boatLength) {
		int boundaries = this.battlefield.length;
		return coos + boatLength <= boundaries;
	}

	public boolean notCollidingWithOtherShips(int cooX, int cooY, int boatLength) {
		for (int y = cooY; y < cooY + boatLength && y < height; y++) {
			if (this.battlefield[y][cooX].getType() != "WATER") {
				return false;
			}
		}
		for (int x = cooX; x < cooX + boatLength && x < height; x++) {
			if (this.battlefield[cooY][x].getType() != "WATER") {
				return false;
			}
		}
		return true;
	}

	public void buildShipH(int cooX, int cooY, int boatLength) {
		for (int x = cooX; x < cooX + boatLength; x++) {
			this.battlefield[cooY][x] = Field.createShip(boatLength);
		}
	}

	public void buildShipV(int cooX, int cooY, int boatLength) {
		for (int y = cooY; y < cooY + boatLength; y++) {
			this.battlefield[y][cooX] = Field.createShip(boatLength);
		}
	}

	public boolean evaluateAttack(int tile1, int tile2) {
		int attackCooX = tile1;
		int attackCooY = tile2;
		switch (battlefield[attackCooY][attackCooX].getType()) {
			case "WATER" -> battlefield[attackCooY][attackCooX] = Field.createMiss();
			case "SHIP" -> battlefield[attackCooY][attackCooX] = Field.createHit();
		}


		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				if (battlefield[x][y].getType() == "SHIP") {
					return true;
				}
			}
		}
		return false;
	}

	private ArrayList<Integer[]> generateOrder(int uniqueElements) {
		ArrayList<Integer[]> output = new ArrayList<>();
		for (int num1 = 0; num1 < uniqueElements; num1++) {
			for (int num2 = 0; num2 < uniqueElements; num2++) {
				output.add(new Integer[]{num1, num2});
			}
		}
		Collections.shuffle(output);
		return output;
	}

	public static void myFor(CallBack callBack) {
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				callBack.call(x, y);
			}
		}
	}

	public interface CallBack {
		public void call(int x, int y);
	}
}
