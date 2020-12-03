package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 implements Day {

    char [][] map;
    public Day3() throws IOException {
        String[] mapString =  Arrays.stream(readDay(3).split(System.lineSeparator())).toArray(String[]::new);
        this.map = buildMap(mapString);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Day3:");
        new Day3().printParts();
    }

    @Override
    public Object part1() throws IOException {
        return treesEncountered(3, 1);
    }

    @Override
    public Object part2() throws IOException {
        return treesEncountered(1, 1)
                * treesEncountered(3, 1)
                * treesEncountered(5, 1)
                * treesEncountered(7, 1)
                * treesEncountered(1, 2);
    }

    public long treesEncountered(int across, int down) {

        int r = 0;
        int c = 0;
        int hits = 0;

        while (c < this.map.length) {

            // Arrange
            r = moveRight(r, across ,this.map[0].length);
            c = moveDown(c, down);

            // breaking condition
            if(c >= this.map.length) {
                return hits;
            }

            // System.out.println("POSITION; r=" + r + "; c=" + c);
            // Act
            if(this.map[c][r] == '#') {
                // System.out.println("Action=HIT; r=" + r + "; c=" + c + "; char=" + this.map[c][r]);
                hits+=1;
            } else {
                // System.out.println("Action=MISS; r=" + r + "; c=" + c + "; char=" + this.map[c][r]);
            }
        }

        return hits;

    }

    private int moveRight(int pos, int increment ,int maxLength) {
        pos += increment;
        if(pos >= maxLength) {
            pos = pos % maxLength;
        }
        return pos;
    }

    private int moveDown(int pos, int increment) {
        return pos + increment;
    }

    private char[][] buildMap(String [] rows) {
        char[][] map = new char[rows.length][rows[0].length()];
        for(int i = 0; i < rows.length; i++) {
            map[i] = rows[i].toCharArray();
        }

        /*
        System.out.println("Printing map");

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        */

        return map;
    }
}
