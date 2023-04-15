package Players;

import java.util.Scanner;

public class NPC extends Players{
    private String name;

    public NPC(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    public NPC(String name, int xPosition, int yPosition) {
        super(xPosition, yPosition);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int endGame() {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\n" + this.getName() + " speaks: You found me again Traveler!");
        System.out.println("I have only one question for you!");
        System.out.println("When you answer that truly, your soul will be free!");
        System.out.println("What's the main purpose of life?");
        System.out.println("1 - ME!");
        System.out.println("2 - OTHERS!");
        System.out.print("????????????\n=>");
        /*
         * System.out.println("2 - Family!");
         * System.out.println("3 - Fun & Insensibility");
         * System.out.println("4 - Others");
         */
        int val = s.nextInt();

        switch (val) {
            case 1:
            case 2:
                System.out.println("See you soon again!");
                break;
            default:
                System.out.println("Well done! You won!");
                System.out.println("Life can not be that simple!");
                System.out.println("See you soon!");
                return 1;
        }

        return 0;
    }
}
