package cz.educanet.tranformations.logic;

public class Field {
    tileType type;
    int dimensions;
    public enum tileType {WATER, MISS, HIT, SHIP, UNKNOWN}

    public Field (tileType type, int dimensions){
        this.type = type;
        this.dimensions = dimensions;
    }
    public Field (tileType type){
        this.type = type;
    }

    public static Field createWater(){
        return new Field(tileType.WATER);
    }

    public static Field createMiss(){
        return new Field(tileType.MISS);
    }

    public static Field createHit(){
        return new Field(tileType.HIT);
    }

    public static Field createShip(int dimensions){
        return new Field(tileType.SHIP, dimensions);
    }


    public tileType getType(){
        return this.type;
    }

    public tileType getTypeUserSide(){
        if(this.type == tileType.SHIP || this.type == tileType.WATER){ // Battleships 3
            return tileType.UNKNOWN;
        } else {
            return this.type;
        }
    }
}
