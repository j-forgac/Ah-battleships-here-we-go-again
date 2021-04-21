package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.ArrayList;
import java.util.Random;

public class ArtificialIntelligence {

	Battlefield battlefield;
	int[][] heatMap;
	boolean mode = true; // true = hunt mode false = destroy mode

	public ArtificialIntelligence(Battlefield battlefield) {
		this.battlefield = battlefield;
		heatMap = new int[battlefield.getHeight()][battlefield.getHeight()];

	}

	public void play() throws InterruptedException {
		//TimeUnit.SECONDS.sleep((long)0.21);
		System.out.print("");
		if (battlefield.getPlayer().isOnMove()) {
			shoot();
		}
	}

	public void shoot() {
		clearHeatMap();
		generateHeatMap();
		Coordinate c = chooseTile();
		//print();
		if (!battlefield.evaluateAttack(c)) {
			MyGame.winner = battlefield.getPlayer();
		}
		if(battlefield.getTileByCoordinate(c) == Field.tileType.HIT){
			mode = false;
		} else if(battlefield.getTileByCoordinate(c) == Field.tileType.SUNK && !contains(Field.tileType.HIT)){
			mode = true;
		}
	}

	public Coordinate chooseTile(){
		ArrayList<Integer[]> mostLikelyTilesToBeShip = new ArrayList<>();
		int topValue = 0;
		for(int x=0;x<heatMap.length;x++){
			for(int y=0;y<heatMap.length;y++){
				if(heatMap[y][x] > topValue){
					topValue = heatMap[y][x];
				}
			}
		}
		for(int x=0;x<heatMap.length;x++){
			for(int y=0;y<heatMap.length;y++){
				if(heatMap[y][x] == topValue){
					mostLikelyTilesToBeShip.add(new Integer[]{y,x});
				}
			}
		}
		int pos = (int) (Math.random() * mostLikelyTilesToBeShip.size());
		return new Coordinate(mostLikelyTilesToBeShip.get(pos)[0],mostLikelyTilesToBeShip.get(pos)[1]);
	}

	public void generateHeatMap() {
		for (int size : battlefield.customBoats.values()) {
			boolean direction = new Random().nextBoolean();
			for (int dir = 0; dir <= 1; dir++) {
				direction = !direction;
				for (int y = 0; y < battlefield.getHeight(); y++) {
					for (int x = 0; x < battlefield.getHeight(); x++) {
						boolean modeCondition;
						if(mode){
							modeCondition = canBeBuiltOnHeatMap(direction, x, y, size);
						}else{
							modeCondition = overlayingHit(direction, x, y, size);
						}
						if (modeCondition) {
							if (direction) {
								buildShipOnHeatMapH(x, y, size);
							} else {
								buildShipOnHeatMapV(x, y, size);
							}
						}

					}
				}
			}
		}
		if(!mode){
			setHitsAndSunksForDestroyMode();
		}
	}

	public void clearHeatMap() {
		for (int y = 0; y < battlefield.getHeight(); y++) {
			for (int x = 0; x < battlefield.getHeight(); x++) {
				heatMap[y][x] = 0;
			}
		}
	}

	public void setHitsAndSunksForDestroyMode(){
		for (int y = 0; y < battlefield.getHeight(); y++) {
			for (int x = 0; x < battlefield.getHeight(); x++) {
				if(battlefield.battlefield[y][x].getTypeUserSide() == Field.tileType.HIT || battlefield.battlefield[y][x].getTypeUserSide() == Field.tileType.SUNK){
					heatMap[y][x] = 0;
				}
			}
		}
	}

	public void buildShipOnHeatMapH(int cooX, int cooY, int boatLength) {
		for (int x = cooX; x < cooX + boatLength; x++) {
			heatMap[cooY][x] += 1;
		}
	}

	public void buildShipOnHeatMapV(int cooX, int cooY, int boatLength) {
		for (int y = cooY; y < cooY + boatLength; y++) {
			heatMap[y][cooX] += 1;
		}
	}

	public void print() {
		for (int x = 0; x < battlefield.getHeight(); x++) {
			System.out.println("");
			for (int y = 0; y < battlefield.getHeight(); y++) {
				if(heatMap[y][x] < 10){
					System.out.print(heatMap[y][x] + "  ");
				} else {
					System.out.print(heatMap[y][x] + " ");

				}
			}
		}
	}

	public boolean canBeBuiltOnHeatMap(boolean axis, int cooX, int cooY, int boatLength) { //* true = X false = Y
		if (axis) {
			try { //* check if colliding with other ships
				for (int x = cooX; x < cooX + boatLength; x++) {
					if (battlefield.battlefield[cooY][x].getTypeUserSide() != Field.tileType.UNKNOWN) {
						return false;
					}
				}
			} catch (Exception e) { //* doesn't fit in map
				return false;
			}
		} else {
			try {
				for (int y = cooY; y < cooY + boatLength; y++) {
					if (battlefield.battlefield[y][cooX].getTypeUserSide() != Field.tileType.UNKNOWN) {
						return false;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public boolean overlayingHit(boolean axis, int cooX, int cooY, int boatLength){
		boolean overlay = false;
		if (axis) {
			try { //* check if colliding with other ships
				for (int x = cooX; x < cooX + boatLength; x++) {
					switch (battlefield.battlefield[cooY][x].getTypeUserSide()){
						case HIT:
							overlay = true;
							break;
						case MISS:
						case SUNK:
							return false;
					}
				}
			} catch (Exception e) { //* doesn't fit in map
				return false;
			}
		} else {
			try {
				for (int y = cooY; y < cooY + boatLength; y++) {
					switch (battlefield.battlefield[y][cooX].getTypeUserSide()) {
						case HIT:
							overlay = true;
							break;
						case MISS:
						case SUNK:
							return false;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return overlay;
	}

	public boolean contains(Field.tileType tile){
		for(Field[] row:battlefield.battlefield){
			for (Field element:row){
				if(element.getType() == tile){
					return true;
				}
			}
		}
		return false;
	}
}
