package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.*;

public class Battlefield {
	int height;
	Field[][] battlefield;
	public int score = 0;
	boolean debugging = true;


	public Battlefield(int dimensions) {
		height = dimensions;
		battlefield = new Field[height][height];

		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				battlefield[x][y] = Field.createWater();
			}
		}
	}

	public void placeShips() {
		boolean direction;
		int maxBoatSize = Math.min(height, 5);
		ArrayList<Coordinate> possibleCoos;
		for (int boatSize = maxBoatSize; boatSize >= 1; boatSize--) {
			possibleCoos = generateOrder(height);
			direction = new Random().nextBoolean();
			shipBuilt:
			for (int dir = 0; dir < 2; dir++) {
				direction = !direction;
				for (Coordinate possibleCoo : possibleCoos) {
					int cooX = possibleCoo.getX();
					int cooY = possibleCoo.getY();
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
			if (!this.battlefield[y][cooX].getType().equals(Field.tileType.WATER)) {
				return false;
			}
		}
		for (int x = cooX; x < cooX + boatLength && x < height; x++) {
			if (!this.battlefield[cooY][x].getType().equals(Field.tileType.WATER)) {
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

	public boolean evaluateAttack(Coordinate coordinate) {
		//* checks for win + shoots at coordinate
		int attackCooX = coordinate.getY();
		int attackCooY = coordinate.getX();
		switch (battlefield[attackCooX][attackCooY].getType()) {
			case WATER -> {
				battlefield[attackCooX][attackCooY] = Field.createMiss();
				score++;
			}
			case SHIP -> {
				battlefield[attackCooX][attackCooY] = Field.createHit();
				score++;
			}
		}

		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				if (battlefield[x][y].getType() == Field.tileType.SHIP) {
					return true;
				}
			}
		}
		return false;
	}

	public int getHeight(){
		return height;
	}
	public Field.tileType getTileByCoordinate(Coordinate coordinate) {
		return battlefield[coordinate.getX()][coordinate.getY()].getTypeUserSide();
	}

	private ArrayList<Coordinate> generateOrder(int uniqueElements) {
		ArrayList<Coordinate> output = new ArrayList<>();
		for (int num1 = 0; num1 < uniqueElements; num1++) {
			for (int num2 = 0; num2 < uniqueElements; num2++) {
				output.add(new Coordinate(num1, num2));
			}
		}
		Collections.shuffle(output);
		return output;
	}

	public void draw() {
		if (debugging) {
			for (Field[] fields : battlefield) {
				System.out.println();
				for (int y = 0; y < battlefield.length; y++) {
					System.out.print(" " + fields[y].getType() + " ");
				}
			}
		}
	}
}
