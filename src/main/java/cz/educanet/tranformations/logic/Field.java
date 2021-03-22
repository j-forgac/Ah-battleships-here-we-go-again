package cz.educanet.tranformations.logic;

public class Field {
    String type;
    int dimensions;

    public Field (String type, int dimensions){
        this.type = type;
        this.dimensions = dimensions;
    }
    public Field (String type){
        this.type = type;
    }

    public static Field createWater(){
        Field water = new Field("WATER");
        return water;
    }

    public static Field createMiss(){
        Field miss = new Field("MISS");
        return miss;
    }

    public static Field createHit(){
        Field hit = new Field("HIT");
        return hit;
    }

    public static Field createShip(int dimensions){
        Field ship = new Field("SHIP", dimensions);
        return ship;
    }

    public String getType(){
        return this.type;
    }

    public int getDimensions(){
        return this.dimensions;
    }
}
