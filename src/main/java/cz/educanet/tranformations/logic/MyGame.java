package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.presentation.Window;

import java.util.Scanner;

public class MyGame{
    public static void main(String[] Args) {
        Scanner mySc = new Scanner(System.in);
        int size = 0;
        while(size < 1 || size > 26) {
            System.out.println("Zadejte vysku pole");
            size = mySc.nextInt();
        }

        Battlefield pole1 = new Battlefield(size);

        pole1.placeShips();
        new Window(800, 800, "Battleships", pole1);
    }
}
