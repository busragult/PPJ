package Players;

import java.util.Scanner;

public class Traveler extends Players{
    private int health;
    private int mana;
    private int money;
    private boolean shield;
    private boolean full_mana;

    public Traveler(int health, int mana, int money, int xPosition, int yPosition) {
        super(xPosition, yPosition);
        this.health = health;
        this.mana = mana;
        this.money = money;
        this.shield = false;
        this.full_mana = false;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean isFull_mana() {
        return full_mana;
    }

    public void setFull_mana(boolean full_mana) {
        this.full_mana = full_mana;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (this.health > 100) {
            this.health = 100;
        }
    }

    public int getMana() {
        return mana;
    }

    public void setMana() {
        this.mana--;
    }

    public void addMana() {
        this.mana = this.mana + 5;
        if (this.mana > 50) {
            this.mana = 50;
        }
    }

    public void action(char c) {
        switch (c) {
            case 1758:
                if (this.shield) {
                    System.out.println("One time protected!");
                    this.setShield(false);
                } else {
                    this.setHealth(this.health - 10);
                }
                break;
            case 36:
                this.money = this.money + 100;
                break;
            case 1769:
                this.setHealth(this.health + 10);
                this.addMana();
                break;
            case 1128:
                this.upgrade();
                break;
            default:
                break;
        }
        if (!this.isFull_mana()) {
            this.setMana();
        }
    }

    private void upgrade() {
        Scanner s = new Scanner(System.in);
        System.out.println("1 - One time shield (500)");
        System.out.println("2 - Full mana (1200)");
        System.out.print("Others - Exit\n=> ");
        int val = s.nextInt();
        switch (val) {
            case 1:
                if (this.money >= 500){
                    this.setMoney(this.money - 500);
                    this.setShield(true);
                } else {
                    System.out.println("You don't have enough money!");
                }
                break;
            case 2:
                if (this.money >= 1200){
                    this.setMoney(this.money - 1200);
                    this.mana = 50;
                    this.setFull_mana(true);
                } else {
                    System.out.println("You don't have enough money!");
                }
                break;
            default:
                break;
        }
    }
}
