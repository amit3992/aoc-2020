package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.*;

public class Day15 implements Day {

    private String [] data;
    private List<Integer> startingNumbers;
    Map<Integer, Integer> seen;
    public Day15() throws IOException {
        this.data =  Arrays.stream(readDay(15).split(System.lineSeparator())).toArray(String[]::new);
        startingNumbers = parseData(data[0]);
        this.seen = new HashMap<>();
    }

    public static void main(String[] args) throws IOException  {
        new Day15().printParts();
    }

    @Override
    public Object part1() throws IOException {
        int turn = 0;
        int prev = -1;

        /* Add starting numbers */
        for(Integer num : startingNumbers) {
            seen.put(num, turn);
            turn++;
            prev = num;
        }

        int max = 2020;

        for(int i = startingNumbers.size(); i < max; i++) {

            int val = 0;
            int prevPos = 0;

            if(seen.containsKey(prev)) {
                prevPos = seen.get(prev);
                val = (i - 1) - prevPos;
            } else {
                val = 0;
            }

            if(prev >= 0) {
                seen.put(prev, i - 1);
            }

            prev = val;

        }

        return prev;
    }

    @Override
    public Object part2() throws IOException {
        seen.clear();

        int turn = 0;
        int prev = -1;

        /* Add starting numbers */
        for(Integer num : startingNumbers) {
            seen.put(num, turn);
            turn++;
            prev = num;
        }

        int max = 30000000;

        for(int i = startingNumbers.size(); i < max; i++) {

            int val = 0;
            int prevPos = 0;

            if(seen.containsKey(prev)) {
                prevPos = seen.get(prev);
                val = (i - 1) - prevPos;
            } else {
                val = 0;
            }

            if(prev >= 0) {
                seen.put(prev, i - 1);
            }

            prev = val;

        }

        return prev;
    }

    private List<Integer> parseData(String data) {
        List<Integer> result = new ArrayList<>();
        for(String ch : data.trim().split(",")) {
            result.add(Integer.parseInt(ch));
        }

        return result;
    }
}
