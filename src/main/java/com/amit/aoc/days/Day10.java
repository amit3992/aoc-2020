package com.amit.aoc.days;

import com.amit.aoc.common.Day;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Day10 implements Day {

    int [] data;
    HashMap<Integer, Integer> seen;
    public Day10() throws IOException {
        this.data =  Arrays.stream(readDay(10).split(System.lineSeparator()))
                .mapToInt(Integer::parseInt)
                .toArray();
        seen = new HashMap<>();
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Day10:");
        new Day10().printParts();
    }

    @Override
    public Object part1() throws IOException {
        int [] voltages = this.data;
        Arrays.sort(voltages);

        int ones = 0;
        int threes = 0;
        int prev = 0;

        for(int i = 0; i < voltages.length; i++) {
            seen.put(voltages[i], i);
            int diff = voltages[i] - prev;

            if(diff == 1) {
                ones++;
            } else if(diff ==  3) {
                threes++;
            }
            prev = voltages[i];
        }
        threes++;
        return ones * threes;
    }



    @Override
    public Object part2() throws IOException {
        int[] voltages = this.data;
        long [] ways = new long[voltages.length];

        ways[voltages.length - 1] = 1;
        for(int i = voltages.length - 2; i >= 0; i--) {
            long sum = 0;
            for (int diff = 1 ; diff <= 3; diff++) {
                if(seen.containsKey(voltages[i] + diff)) {
                    sum += ways[seen.get(voltages[i] + diff)];
                }
            }
            ways[i] = sum;
        }

        long result = 0;
        for(int j = 1; j <= 3; j++) {
            if(seen.containsKey(j)) {
                result += ways[seen.get(j)];
            }
        }

        return result;

    }
}
