package cz.educanet.tranformations.logic;

public class Field {
    tileType type;
    int dimensions;
    public enum tileType {WATER, MISS, HIT, SHIP}

    public Field (tileType type, int dimensions){
        this.type = type;
        this.dimensions = dimensions;
    }
    public Field (tileType type){
        this.type = type;
    }

    public static Field createWater(){
        Field water = new Field(tileType.WATER);
        return water;
    }

    public static Field createMiss(){
        Field miss = new Field(tileType.MISS);
        return miss;
    }

    public static Field createHit(){
        Field hit = new Field(tileType.HIT);
        return hit;
    }

    public static Field createShip(int dimensions){
        Field ship = new Field(tileType.SHIP, dimensions);
        return ship;
    }

    public tileType getType(){
        return this.type;
    }

    public int getDimensions(){
        return this.dimensions;
    }
}
