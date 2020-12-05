package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day5 implements Day {

    String [] data;
    char [][] seats = new char[128][8];
    public Day5() throws IOException {
        this.data =  Arrays.stream(readDay(5).split(System.lineSeparator())).toArray(String[]::new);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Day5:");
        new Day5().printParts();
    }

    @Override
    public Object part1() throws IOException {

        initializeSeats();

        long max = 0;
        for( String boardingPass : data ) {
            BoardingPass bp = buildBoardingPass(boardingPass);
            setSeatFromBP(bp);
            if(bp.getSeatId() > max) {
                max = bp.getSeatId();
            }
        }

        return max;
    }

    @Override
    public Object part2() throws IOException {

        BoardingPass myBoardingPass = findMyBoardingPass();
       // printSeats();

        return myBoardingPass != null ? myBoardingPass.getSeatId() : Integer.MAX_VALUE;
    }

    private BoardingPass findMyBoardingPass() {
        for(int r = 0; r < seats.length; r++) {
            for(int c = 0; c < seats[0].length; c++) {
                if(seats[r][c] == 'O') {
                    if(r > 1 && r < 128 && c > 1 && c < 128) {
                        if(seats[r-1][c] == 'X' && seats[r][c - 1] == 'X' && seats[r + 1][c] == 'X' && seats[r][c + 1] == 'X') {
                            // System.out.println("My row: " + r + "| My col: " + c);
                            return new BoardingPass(r, c);
                        }
                    }

                }
            }
        }

        return null;
    }

    public BoardingPass buildBoardingPass(String bpData) {
        // System.out.println("Processing String: " + bpData);
        StringBuilder bpString = new StringBuilder(bpData);
        StringBuilder colData = new StringBuilder();
        StringBuilder rowData = new StringBuilder();

        /* rows */
        for(int i = 0; i < 7; i++) {
            if(bpString.charAt(i) != 'R' && bpString.charAt(i) != 'L') {
                if(bpString.charAt(i) == 'B') {
                    rowData.append(1);
                } else if(bpString.charAt(i) == 'F') {
                    rowData.append(0);
                }
            }
        }

        /* Column */
        for(int i = 7; i < bpString.length(); i++) {
            if(bpString.charAt(i) != 'F' && bpString.charAt(i) != 'B') {
                if(bpString.charAt(i) == 'R') {
                    colData.append(1);
                } else if(bpString.charAt(i) == 'L') {
                    colData.append(0);
                }
            }
        }

        // System.out.println("Binary Row: " + rowData);
        // System.out.println("Binary Col: " + colData);
        int row = Integer.parseInt(rowData.toString(), 2);
        int col = Integer.parseInt(colData.toString(), 2);
        // System.out.println("Row: " + row);
        // System.out.println("Col: " + col);
        return new BoardingPass(row,col);

    }

    class BoardingPass {
        private int row;
        private int col;
        private long seatId;

        public BoardingPass(int row, int col) {
            this.row = row;
            this.col = col;
            this.seatId = this.row*8 + this.col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }


        public long getSeatId() {
            return seatId;
        }

    }

    private void initializeSeats() {
        for(int r = 0; r < seats.length; r++) {
            for(int c = 0; c < seats[0].length; c++) {
                seats[r][c] = 'O';
            }
        }
    }

    private void setSeatFromBP(BoardingPass bp) {
        seats[bp.getRow()][bp.getCol()] = 'X';
    }

    /*
    private void printSeats() {
        for(int r = 0; r < seats.length; r++) {
            for(int c = 0; c < seats[0].length; c++) {
                System.out.print(seats[r][c] + " | ");
            }
            System.out.println();
        }
    }
    */
}
