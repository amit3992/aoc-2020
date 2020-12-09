package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class Day9 implements Day {

    long [] data;
    long weakness;
    public Day9() throws IOException {
        this.data =  Arrays.stream(readDay(9).split(System.lineSeparator()))
                .mapToLong(Long::parseLong)
                .toArray();
        weakness = 0;
    }

    public static void main(String [] args) throws IOException {
        System.out.println("Day9:");
        new Day9().printParts();
    }


    @Override
    public Object part1() throws IOException {
        int preamble = 25;
        return findWeakness(preamble).getValue();
    }

    @Override
    public Object part2() throws IOException {
        return findEncryptionWeakness().getValue();
    }

    private Result findEncryptionWeakness() {

        Result result2 = new Result();
        result2.setValue(Integer.MAX_VALUE);
        for(int i = 0; i < this.data.length; i++) {
            long rollingSum = this.data[i];
            long high = this.data[i];
            long low = this.data[i];

            for(int p = 1; p < this.data.length; p++) {
                long current = this.data[i + p];
                rollingSum += current;

                if(high < current) {
                    high = current;
                }

                if(low > current) {
                    low = current;
                }

                if(rollingSum == weakness) {
                    // System.out.println("result=" + (high + low));
                    result2.setValue(high + low);
                    return result2;
                }
                if(rollingSum > weakness) {
                    break;
                }

            }
        }

        return result2;
    }

    private Result findWeakness(int preamble) {

        Result result = new Result();
        int low = 0;
        int high = 0;
        long val = 0;
        for(int ptr = 0; ptr < this.data.length; ptr++) {

            if(ptr < preamble) {
                // preamble
                continue;
            }

            high = ptr - 1;
            low = ptr - preamble;
            val = this.data[ptr];
            // System.out.println("ptr=" + ptr + "| value="+ this.data[ptr] +  "| low=" + low + "| high= " + high);
            long [] arr = buildArr(low, high);

            if(!isValidNumber(arr, val)) {
                // System.out.println("Found. Val= " + val);
                result.setValue(val);
                weakness = val;
                break;
            }
        }

        return result;

    }

    private long [] buildArr(int low, int high) {
        long[] arr = new long[high - low + 1];
        int index = 0;
        for(int i = low; i <= high; i++) {
            arr[index] = this.data[i];
            index++;
        }

        return arr;
    }

    private boolean isValidNumber(long arr[], long sum)
    {
        HashSet<Long> s = new HashSet<Long>();
        boolean found = false;
        for (int i = 0; i < arr.length; ++i)
        {
            long temp = sum - arr[i];

            // checking for condition
            if (s.contains(temp)) {
//                System.out.println(
//                        "Pair with given sum "
//                                + sum + " is (" + arr[i]
//                                + ", " + temp + ")");
                found = true;
            }
            s.add(arr[i]);
        }

        return found;
    }

    class Result {
        long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
}
