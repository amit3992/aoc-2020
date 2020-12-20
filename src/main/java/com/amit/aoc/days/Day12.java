package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.Arrays;

public class Day12 implements Day {

    private String[] data;
    public Day12() throws IOException {
        this.data =  Arrays.stream(readDay(12).split(System.lineSeparator())).toArray(String[]::new);
    }

    public static void main(String[] args) throws IOException  {
        new Day12().printParts();
    }

    @Override
    public Object part1() throws IOException {
        int posX = 0;
        int posY = 0;
        int facing = 0;

        return Integer.MIN_VALUE;
    }

    @Override
    public Object part2() throws IOException {
        return Integer.MAX_VALUE;
    }


}
