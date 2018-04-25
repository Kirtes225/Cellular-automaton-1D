package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";


    private static final int height = 50, length = 2 * height+1;


    public static void main(String[] args) {
        int[][] table = new int[height][length];
        table[0][length / 2] = 1;

        int rule = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter rule number: ");
            rule = scanner.nextInt();
            printCellularAutomaton(table, rule);
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.err.println("Input is not INTEGER");
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("30: " + decToBinary(30));
        System.out.println("60: " + decToBinary(60));
        System.out.println("90: " + decToBinary(90));
        System.out.println("102: " + decToBinary(102));
        System.out.println("150: " + decToBinary(150));
        System.out.println("250: " + decToBinary(250));
    }


    private static List<Integer> decToBinary(int number) {
        if (number <= 0) {
            System.err.println(number + " is invalid input");
            return null;
        }

        int[] bitsFulfilled = Stream.of(Integer.toBinaryString(number).split("")).mapToInt(Integer::parseInt).toArray();
        int bits = 0;
        double counter = Math.pow(2, bits);
        while (counter < bitsFulfilled.length) {
            counter = Math.pow(2, bits++);
        }

        List<Integer> digits = new ArrayList<>();

        for (int i = 0; i < 8 - bitsFulfilled.length; i++) //because there are only 2^8 variations
            digits.add(0);

        for (int digit : bitsFulfilled) {
            digits.add(digit);
        }

        return digits;
    }


    private static void printCellularAutomaton(int[][] table, int number) {
        StringBuilder sb = new StringBuilder();

        List<Integer> bits = decToBinary(number);
        if (bits == null || bits.isEmpty()) {
            System.err.println("Failure");
            return;
        } else {
            for (int i = 1; i < table.length; i++) {
                for (int j = 1; j < table[0].length - 1; j++) {
                    if (table[i - 1][j - 1] == 1 && table[i - 1][j] == 1 && table[i - 1][j + 1] == 1) {
                        table[i][j] = bits.get(0);
                    } else if (table[i - 1][j - 1] == 1 && table[i - 1][j] == 1 && table[i - 1][j + 1] == 0) {
                        table[i][j] = bits.get(1);
                    } else if (table[i - 1][j - 1] == 1 && table[i - 1][j] == 0 && table[i - 1][j + 1] == 1) {
                        table[i][j] = bits.get(2);
                    } else if (table[i - 1][j - 1] == 1 && table[i - 1][j] == 0 && table[i - 1][j + 1] == 0) {
                        table[i][j] = bits.get(3);
                    } else if (table[i - 1][j - 1] == 0 && table[i - 1][j] == 1 && table[i - 1][j + 1] == 1) {
                        table[i][j] = bits.get(4);
                    } else if (table[i - 1][j - 1] == 0 && table[i - 1][j] == 1 && table[i - 1][j + 1] == 0) {
                        table[i][j] = bits.get(5);
                    } else if (table[i - 1][j - 1] == 0 && table[i - 1][j] == 0 && table[i - 1][j + 1] == 1) {
                        table[i][j] = bits.get(6);
                    } else if (table[i - 1][j - 1] == 0 && table[i - 1][j] == 0 && table[i - 1][j + 1] == 0) {
                        table[i][j] = bits.get(7);
                    }
                }
            }
        }

        for (int i = 0; i < table.length; i++) {
            for (int j = 1; j < table[0].length - 1; j++) {
                if (table[i][j] == 1)
                    sb.append(ANSI_BLUE_BACKGROUND).append(" ");
                else
                    sb.append(ANSI_YELLOW_BACKGROUND).append(" ");
            }
            sb.append(ANSI_RESET).append("\n");
        }
        System.out.println(sb.toString());
    }
}
