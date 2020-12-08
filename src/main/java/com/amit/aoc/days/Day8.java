package com.amit.aoc.days;

import com.amit.aoc.common.Day;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Day8 implements Day {


    String [] data;
    public Day8() throws IOException {
        this.data =  Arrays.stream(readDay(8).split(System.lineSeparator()))
                .toArray(String[]::new);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Day8:");
        new Day8().printParts();
    }

    @Override
    public Object part1() throws IOException {
        StopWatch watch = new StopWatch();
        watch.start();
        int accVal = runGame(this.data).getGlobalAcc();
        watch.stop();
        System.out.println("Time Elapsed: " + watch.getTime(TimeUnit.MILLISECONDS));
        return accVal;
    }


    @Override
    public Object part2() throws IOException {
        StopWatch watch = new StopWatch();
        watch.start();

        String [] instructions = this.data;
        int pointer = 0;
        String oldInstruction = "";
        boolean run = true;
        Result result = new Result();
        do {
            boolean done = false;

            while(!done && pointer < instructions.length) {
                String op = instructions[pointer].split(" ")[0];
                String val = instructions[pointer].split(" ")[1];

                if(op.equalsIgnoreCase("nop")) {

                    done = true;
                    oldInstruction = instructions[pointer];
                    String newInstruction = oldInstruction.replace("nop", "jmp");
                    instructions[pointer] = newInstruction;


                } else if(op.equalsIgnoreCase("jmp")) {

                    done = true;
                    oldInstruction = instructions[pointer];
                    String newInstruction = oldInstruction.replace("jmp", "nop");
                    instructions[pointer] = newInstruction;
                } else {
                    pointer++;
                }
            }
            result = runGame(instructions);

            if(!result.isInfinite()) {
                run = false;
            } else {
                instructions[pointer] = oldInstruction;
            }
            pointer++;
        } while (run && pointer < this.data.length);

        watch.stop();
        System.out.println("Time Elapsed: " + watch.getTime(TimeUnit.MILLISECONDS));
        return result.getGlobalAcc();

    }

    private Result runGame(String [] instructions) {

        Result result = new Result();
        int globalAcc = 0;
        int pc;

        List<Integer> visited = new ArrayList<>();
        pc = 0;
        boolean flag = true;

        while(flag) {
            String op = this.data[pc].split(" ")[0];
            String val = this.data[pc].split(" ")[1];
            visited.add(pc);
            switch(op) {
                case "nop":
                    pc++;
                    break;
                case "acc":
                    pc++;
                    globalAcc += Integer.parseInt(val);
                    break;
                case "jmp":
                    pc += Integer.parseInt(val);
                    break;
            }

            if(visited.contains(pc)) {
                // System.out.println("PC=" + pc + " | globalAcc=" + globalAcc);
                flag = false;
                result.setInfinite(true);
                result.setGlobalAcc(globalAcc);
            } else if(pc >= instructions.length) {
                flag = false;
                result.setInfinite(false);
                result.setGlobalAcc(globalAcc);
            }
        }

        return result;
    }

    class Result {
        boolean isInfinite;
        int globalAcc;

        public boolean isInfinite() {
            return isInfinite;
        }

        public void setInfinite(boolean infinite) {
            isInfinite = infinite;
        }

        public int getGlobalAcc() {
            return globalAcc;
        }

        public void setGlobalAcc(int globalAcc) {
            this.globalAcc = globalAcc;
        }
    }


}
