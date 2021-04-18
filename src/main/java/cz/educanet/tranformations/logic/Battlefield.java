package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.*;

public class Battlefield {
	Player player;
	int height;
	Field[][] battlefield;
	public int score = 0;
	boolean debugging = true;
	HashMap<String, Integer> customBoats = new HashMap<>();
	HashMap<String, Integer> notSunkShips = new HashMap<>();


	public Battlefield(int dimensions) {
		customBoats.put("5a",5);
		customBoats.put("4a",4);
		customBoats.put("3a",3);
		customBoats.put("3b",3);
		customBoats.put("2a",2); //*only in descending order
		notSunkShips = customBoats;
		height = dimensions;
		battlefield = new Field[height][height];

		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				battlefield[x][y] = Field.createWater();
			}
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer(){
		return this.player;
	}

	public void placeShips() {
		boolean direction;
		int maxBoatSize = Math.min(height, 5);
		int boatSize;
		ArrayList<Coordinate> possibleCoos;
		for (String boatId : customBoats.keySet()) {
			boatSize = customBoats.get(boatId);
			possibleCoos = generateOrder(height);
			direction = new Random().nextBoolean();
			shipBuilt:
			for (int dir = 0; dir < 2; dir++) {
				direction = !direction;
				for (Coordinate possibleCoo : possibleCoos) {
					int cooX = possibleCoo.getX();
					int cooY = possibleCoo.getY();
					boolean canBeBuiltY = canBeBuilt(true, cooX, cooY, boatSize);
					boolean canBeBuiltX = canBeBuilt(false, cooX, cooY, boatSize);
					if (canBeBuiltX || canBeBuiltY) {
						if (canBeBuiltX && canBeBuiltY){
							if (direction) {
								buildShipV(cooX, cooY, boatSize, boatId);
							} else {
								buildShipH(cooX, cooY, boatSize, boatId);
							}
						} else if (canBeBuiltX){
							buildShipV(cooX, cooY, boatSize, boatId);
						} else {
							buildShipH(cooX, cooY, boatSize, boatId);
						}
						break shipBuilt;
					}
				}
			}
		}
	}

	public boolean canBeBuilt(boolean axis, int cooX, int cooY, int boatLength) { //* true = X false = Y
		if (axis) {
			try { //* check if colliding with other ships
				for (int x = cooX; x < cooX + boatLength; x++) {
					if (this.battlefield[cooY][x].getType() != Field.tileType.WATER) {
						return false;
					}
				}
			} catch (Exception e) { //* doesn't fit in map
				return false;
			}
		} else {
			try {
				for (int y = cooY; y < cooY + boatLength; y++) {
					if (this.battlefield[y][cooX].getType() != Field.tileType.WATER) {
						return false;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public void buildShipH(int cooX, int cooY, int boatLength, String id) { //horizontal ship
		for (int x = cooX; x < cooX + boatLength; x++) {
			this.battlefield[cooY][x] = Field.createShip(boatLength, id);
		}
	}

	public void buildShipV(int cooX, int cooY, int boatLength, String id) { //vertical ship
		for (int y = cooY; y < cooY + boatLength; y++) {
			this.battlefield[y][cooX] = Field.createShip(boatLength, id);
		}
	}

	public boolean evaluateAttack(Coordinate coordinate) {
		//* shoots at coordinate
		int attackCooX = coordinate.getX();
		int attackCooY = coordinate.getY();
		String possibleSunkShipId;
		boolean sunk = true;
		switch (battlefield[attackCooY][attackCooX].getType()) {
			case WATER -> {
				battlefield[attackCooY][attackCooX] = Field.createMiss();
				score++;
			}
			case SHIP -> {
				possibleSunkShipId = battlefield[attackCooY][attackCooX].getId();
				battlefield[attackCooY][attackCooX] = Field.createHit(possibleSunkShipId);
				score++;
				//* checks if ship's been sunk
				loop:
				for (int x = 0; x < height; x++) {
					for (int y = 0; y < height; y++) {
						if (battlefield[x][y].getId().equals(possibleSunkShipId) && battlefield[x][y].getType() == Field.tileType.SHIP) {
							sunk = false;
							break loop;
						}
					}
				}
				if(sunk){
					for (int x = 0; x < height; x++) {
						for (int y = 0; y < height; y++) {
							if (battlefield[x][y].getId().equals(possibleSunkShipId) && battlefield[x][y].getType() == Field.tileType.HIT) {
								battlefield[x][y] = Field.createSunk();
								notSunkShips.remove(battlefield[x][y].getId());
							}
						}
					}
				}
			}
		}
		//*check win
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				if (battlefield[x][y].getType() == Field.tileType.SHIP) {
					switchPlayer();
					return true;
				}
			}
		}
		switchPlayer();
		return false;
	}

	public int getHeight(){
		return height;
	}

	public Field.tileType getTileByCoordinate(Coordinate coordinate) {
		return battlefield[coordinate.getY()][coordinate.getX()].getTypeUserSide();
	}

	public Field.tileType getRawTileByCoordinate(Coordinate coordinate) {
		return battlefield[coordinate.getY()][coordinate.getX()].getType();
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
					System.out.print(" " + fields[y].getType() + " " + fields[y].getDimensions());
				}
			}
		}
	}

	private void switchPlayer() {
		for (Player player: MyGame.players){
			if(player != this.player){
				MyGame.onMove = player;
			}
		}
	}
}
