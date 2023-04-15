package com.company;

import Players.NPC;
import Players.Traveler;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void showDetails(Traveler p) {
        System.out.print("\n\nHealth   : " + p.getHealth());
        if (p.getHealth() == 100) {
            System.out.print("(max)");
        }
        System.out.print("\nMana     : " + p.getMana());
        if (p.getMana() == 50) {
            System.out.print("(max)");
        }
        System.out.print("\nMoney    : " + p.getMoney());
        System.out.print("\nShield   : " + p.isShield());
        System.out.print("\nFull Mana: " + p.isFull_mana());
    }

    public static void reLocateWise(NPC w, char[][] map, int row, int col) {
        map[w.getyPosition()][w.getxPosition()] = ' ';
        do {
            if (w.getxPosition() == 0) {
                if (w.getyPosition() == 0) {
                    w.setyPosition(row - 1);
                } else {
                    w.setxPosition(col - 1);
                }
            } else {
                if (w.getyPosition() == 0) {
                    w.setxPosition(0);
                } else {
                    w.setyPosition(0);
                }
            }
        } while (map[w.getyPosition()][w.getxPosition()] == 'P');

        map[w.getyPosition()][w.getxPosition()] = 992;
    }

    public static void drawMap(char[][] map, int row, int col) {
        System.out.print(" ");
        for (int i = 0; i < col * 2; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < row; i++) {
            System.out.print("|");
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println("|");
        }

        System.out.print(" ");
        for (int i = 0; i < col * 2; i++) {
            System.out.print("-");
        }
    }

    public static int checkEndGame(Traveler p, NPC w, char[][] map) {
        int flag = 0;
        if (p.getxPosition() == w.getxPosition() && p.getyPosition() == w.getyPosition()) {
            flag = w.endGame();
            if (flag == 0) {
                p.setxPosition((map.length - 1) / 2);
                p.setyPosition((map[0].length - 1) / 2);
            }
        }
        return flag;
    }

    // LIST OF IMPLEMENTED FEATURES
    // Store information about player
    // Place player on the map
    // Allow player to move on the board
    // Add randomness
    // Add 4 types of elements
    // Add NPCs
    // Allow to lose/win
    // Add option to upgrade

    public static void main(String[] args) {
        int row = 25, col = 25;
        char[][] map = new char[row][col];
        Traveler player = new Traveler(100, 50, 0, (map.length - 1) / 2, (map[0].length - 1) / 2);
        NPC wise = new NPC("Old Wise Man", 0, 0);
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int val, flag = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = ' ';
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                val = rand.nextInt(100);
                if (val > 10 && val <= 20) { // ۞ trap
                    map[i][j] = 1758;
                } else if (val > 60 && val <= 65) { // $ money
                    map[i][j] = 36;
                } else if (val > 50 && val <= 56) { // ۩ meditation
                    map[i][j] = 1769;
                } else if (val > 65 && val <= 70) { // Ѩ upgrade
                    map[i][j] = 1128;
                }
            }
        }

        val = 0;
        map[player.getyPosition()][player.getxPosition()] = 'P';
        map[wise.getyPosition()][wise.getxPosition()] = 992;

        while (flag == 0) {
            drawMap(map, row, col);
            String move;
            showDetails(player);

            if (player.getHealth() <= 0 || player.getMana() <= 0) {
                System.out.println("\n\nYou Died!");
                return;
            }

            System.out.print("\nWhere do you want to move: ");
            move = sc.nextLine();
            move = move.toUpperCase();
            switch (move) {
                case "NORTH": // up
                    if (player.getyPosition() == 0) {
                        System.out.println("Invalid area!");
                        break;
                    }

                    player.action(map[player.getyPosition() - 1][player.getxPosition()]);
                    player.setyPosition(player.getyPosition() - 1);

                    if (val != 1) {
                        map[player.getyPosition() + 1][player.getxPosition()] = ' ';
                    } else {
                        map[player.getyPosition() + 1][player.getxPosition()] = 1128;
                        val = 0;
                    }

                    if (map[player.getyPosition()][player.getxPosition()] == 1128) {
                        val = 1;
                    }

                    flag = checkEndGame(player, wise, map);

                    map[player.getyPosition()][player.getxPosition()] = 'P';
                    reLocateWise(wise, map, row, col);
                    break;
                case "SOUTH": // down
                    if (player.getyPosition() == map.length - 1) {
                        System.out.println("Invalid area!");
                        break;
                    }

                    player.action(map[player.getyPosition() + 1][player.getxPosition()]);
                    player.setyPosition(player.getyPosition() + 1);

                    if (val != 1) {
                        map[player.getyPosition() - 1][player.getxPosition()] = ' ';
                    } else {
                        map[player.getyPosition() - 1][player.getxPosition()] = 1128;
                        val = 0;
                    }

                    if (map[player.getyPosition()][player.getxPosition()] == 1128) {
                        val = 1;
                    }

                    flag = checkEndGame(player, wise, map);

                    map[player.getyPosition()][player.getxPosition()] = 'P';
                    reLocateWise(wise, map, row, col);
                    break;
                case "EAST": // right
                    if (player.getxPosition() == map[0].length - 1) {
                        System.out.println("Invalid area!");
                        break;
                    }

                    player.action(map[player.getyPosition()][player.getxPosition() + 1]);
                    player.setxPosition(player.getxPosition() + 1);

                    if (val != 1) {
                        map[player.getyPosition()][player.getxPosition() - 1] = ' ';
                    } else {
                        map[player.getyPosition()][player.getxPosition() - 1] = 1128;
                        val = 0;
                    }

                    if (map[player.getyPosition()][player.getxPosition()] == 1128) {
                        val = 1;
                    }

                    flag = checkEndGame(player, wise, map);

                    map[player.getyPosition()][player.getxPosition()] = 'P';
                    reLocateWise(wise, map, row, col);
                    break;
                case "WEST": // left
                    if (player.getxPosition() == 0) {
                        System.out.println("Invalid area!");
                        break;
                    }

                    player.action(map[player.getyPosition()][player.getxPosition() - 1]);
                    player.setxPosition(player.getxPosition() - 1);

                    if (val != 1) {
                        map[player.getyPosition()][player.getxPosition() + 1] = ' ';
                    } else {
                        map[player.getyPosition()][player.getxPosition() + 1] = 1128;
                        val = 0;
                    }

                    if (map[player.getyPosition()][player.getxPosition()] == 1128) {
                        val = 1;
                    }

                    flag = checkEndGame(player, wise, map);

                    map[player.getyPosition()][player.getxPosition()] = 'P';
                    reLocateWise(wise, map, row, col);
                    break;
                default:
                    break;
            }
        }
    }
}
