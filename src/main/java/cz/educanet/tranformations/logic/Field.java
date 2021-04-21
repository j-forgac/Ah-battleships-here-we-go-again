package cz.educanet.tranformations.logic;

public class Field {
    tileType type;
    int dimensions;
    String id;
    public enum tileType {WATER, MISS, HIT, SHIP, UNKNOWN, SUNK}

    public Field (tileType type, int dimensions, String id){
        this.type = type;
        this.dimensions = dimensions;
        this.id = id;
    }
    public Field (tileType type){
        this.type = type;
    }

    public static Field createWater(){
        return new Field(tileType.WATER,1,"0");
    }

    public static Field createMiss(){
        return new Field(tileType.MISS,1,"0");
    }

    public static Field createHit(String id){
        return new Field(tileType.HIT, 0, id);
    }

    public static Field createShip(int dimensions, String id){
        return new Field(tileType.SHIP, dimensions, id);
    }

    public static Field createSunk(){
        return new Field(tileType.SUNK,1,"0");
    }



    public tileType getType(){
        return this.type;
    }

    public String getId(){
        return this.id;
    }
    public int getDimensions(){
        return this.dimensions;
    }


    public tileType getTypeUserSide(){
        if(this.type == tileType.SHIP || this.type == tileType.WATER){ // Battleships 3
            return tileType.UNKNOWN;
        } else {
            return this.type;
        }
    }
}
