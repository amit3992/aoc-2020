package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day1 implements Day {
    public static void main(String[] args) throws IOException
    {
        new Day1().printParts();
    }

    @Override
    public Object part1() throws IOException {
        int[] report = createNumberStream().toArray();

        for(int i = 0; i < report.length; i++) {
            for (int j = i + 1; j < report.length; j++) {
                if(report[i] + report[j] == 2020) {
                    return report[i] * report[j];
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public Object part2() throws IOException {
        int[] report = createNumberStream().toArray();

        for(int i = 0; i < report.length; i++) {
            for (int j = i + 1; j < report.length; j++) {
                for(int k = j + 1; k < report.length; k++) {
                    if(report[i] + report[j] + report[k] == 2020) {
                        return report[i] * report[j] * report[k];
                    }
                }

            }
        }

        return Integer.MIN_VALUE;
    }

    private IntStream createNumberStream() throws IOException {
        return Arrays.stream(readDay(1).split(System.lineSeparator())).mapToInt(Integer::parseInt);
    }

}
