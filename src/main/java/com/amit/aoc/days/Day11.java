package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Day11 implements Day {

    Seat [][] map;
    public Day11 () throws IOException  {
        String[] mapString =  Arrays.stream(readDay(11).split(System.lineSeparator())).toArray(String[]::new);
        this.map = buildSeatMap(mapString);
    }

    public static void main(String[] args) throws IOException {
        new Day11().printParts();
    }

    @Override
    public Object part1() throws IOException {

        Seat [][] initialState = this.map;
        int rows = initialState.length;
        int cols = initialState[0].length;
        boolean changeState = true;
        LinkedList<Seat[][]> seatStateQ = new LinkedList<>();
        seatStateQ.add(initialState);

        int iteration = 1;
        while(changeState) {
            System.out.println("Iteration: " + iteration);
            Seat[][] newState = new Seat[rows][cols];
            Seat[][] currentState = seatStateQ.peek();

            System.out.println("Current state");
            printSeatMap(currentState);

            changeState = false;
            for(int r = 0; r < currentState.length; r++) {
                for(int c = 0; c < currentState[0].length; c++) {
                    Seat thisSeat = currentState[r][c];


                    switch(thisSeat.getState().getLabel()) {
                        case "L":
                            if(occupiedNeighbors(currentState, thisSeat) == 0) {
                                newState[r][c] = new Seat(r, c);
                                newState[r][c].setState(SeatState.H);
                                changeState = true;
                            } else {
                                newState[r][c] = thisSeat;
                            }
                            break;
                        case "H":
                            if(occupiedNeighbors(currentState, thisSeat) >= 4) {
                                newState[r][c] = new Seat(r, c);
                                newState[r][c].setState(SeatState.L);
                                changeState = true;
                            } else {
                                newState[r][c] = thisSeat;
                            }
                            break;
                        case "X":
                            newState[r][c] = new Seat(r, c);
                            newState[r][c].setState(SeatState.X);
                            break;
                    }
                }
            }

            System.out.println("New state");
            printSeatMap(newState);

            seatStateQ.remove();
            seatStateQ.add(newState);

            if(!changeState) {
                break;
            }

            iteration++;
        }

        return countOccupiedSeats(seatStateQ.poll());
    }

    @Override
    public Object part2() throws IOException {

        Seat [][] initialState = this.map;
        int rows = initialState.length;
        int cols = initialState[0].length;
        boolean changeState = true;
        LinkedList<Seat[][]> seatStateQ = new LinkedList<>();
        seatStateQ.add(initialState);

        int iteration = 1;


        while(changeState) {
            System.out.println("Iteration: " + iteration);

            System.out.println("-----------------------------------------");
//            printSeatMap(initialState);

            Seat[][] newState = solvePart2(initialState);
            changeState = false;

            for(int i = 0; i < newState.length; i++) {

                if(changeState) {
                    break;
                }
                for(int j = 0; j < newState[i].length; j++)
                {
                    String nstate = newState[i][j].getState().getLabel();
                    String istate = initialState[i][j].getState().getLabel();
                    if(!nstate.equals(istate))
                    {
                        changeState = true;
                        break;
                    }
                }
            }
            iteration++;
            initialState = newState;

            System.out.println("New state");
            printSeatMap(newState);
        }

        return countOccupiedSeats(initialState);
    }

    private Seat[][] solvePart2(Seat[][] seats) {
        Seat [][] updated = new Seat[seats.length][seats[0].length];

        for(int i = 0; i < updated.length; i++) {
            for(int j = 0; j < updated[i].length; j++) {

                Seat currentSeat = seats[i][j];

                switch(currentSeat.getState().getLabel()) {
                    case "L":
                        boolean occupied = true;

                        for(int di = -1; di < 2; di++) {
                            if(!occupied) {
                                break;
                            }

                            for(int dj = -1; dj < 2; dj++) {

                                if(di == 0 || dj == 0) {
                                    continue;
                                }

                                if(!occupied)
                                    break;

                                for(int scale = 1; scale < 100; scale++)
                                {
                                    int iOffScale = di * scale;
                                    int jOffScale = dj * scale;

                                    if(i + iOffScale < 0 || j + jOffScale < 0 || i + iOffScale >= updated.length || j + jOffScale >= updated[i].length) {
                                        continue;
                                    }

                                    Seat seatAtOff = seats[i + iOffScale][j + jOffScale];
                                    if(seatAtOff.getState().getLabel().equals(SeatState.H.getLabel()))
                                    {
                                        occupied = false;
                                        break;
                                    }
                                    else if(seatAtOff.getState().getLabel().equals(SeatState.L.getLabel()))
                                    {
                                        break;
                                    }
                                }
                            }
                        }

                        if(occupied) {
                            updated[i][j] = new Seat(i, j);
                            updated[i][j].setState(SeatState.H);
                        } else {
                            updated[i][j] = new Seat(i, j);
                            updated[i][j].setState(SeatState.L);
                        }

                        break;
                    case "H":

                        int occupiedCount = 0;

                        for(int di = -1; di < 2; di++)
                        {
                            for(int dj = -1; dj < 2; dj++)
                            {
                                if(di == 0 && dj == 0)
                                    continue;
                                for(int scale = 1; scale < 100; scale++)
                                {
                                    int iOffScale = di * scale;
                                    int jOffScale = dj * scale;
                                    if(i + iOffScale < 0 || j + jOffScale < 0 || i + iOffScale >= updated.length || j + jOffScale >= updated[i].length)
                                        continue;

                                    Seat seatAtOff = seats[i + iOffScale][j + jOffScale];
                                    if(seatAtOff.getState().getLabel().equals(SeatState.H.getLabel()))
                                    {
                                        occupiedCount++;
                                        break;
                                    }
                                    else if(seatAtOff.getState().getLabel().equals(SeatState.L.getLabel()))
                                    {
                                        break;
                                    }
                                }
                            }
                        }

                        if(occupiedCount >= 5) {
                            updated[i][j] = new Seat(i, j);
                            updated[i][j].setState(SeatState.L);
                        } else {
                            updated[i][j] = new Seat(i, j);
                            updated[i][j].setState(SeatState.H);
                        }

                        break;
                    case "X":
                        updated[i][j] = new Seat(i, j);
                        updated[i][j].setState(SeatState.X);
                        break;
                }

            }
        }

        return updated;
    }

    private int occupiedNeighbors(Seat[][] seatMap, Seat p) {
        ArrayList<Seat> n = new ArrayList<Seat>();

        // System.out.println("Getting neighors for" + p.toString());
        for (int dr = -1; dr <= +1; dr++) {
            for (int dc = -1; dc <= +1; dc++) {
                int r = p.row + dr;
                int c = p.col + dc;

                if ((r >= 0) && (r < seatMap.length) && (c >= 0) && (c < seatMap[0].length)) {
                    // skip p
                    if ((dr != 0) || (dc != 0)) {
                        Seat thisSeat = seatMap[r][c];
                        if(thisSeat.getState().equals(SeatState.H)) {
                            // System.out.println(thisSeat.toString());
                            n.add(thisSeat);
                        }

                    }

                }
            }
        }

        return n.size();
    }

    private int countOccupiedSeats(Seat[][] seatMap) {
        if(seatMap == null) {
            return Integer.MIN_VALUE;
        }

        int seatCount = 0;
        for (int r = 0; r < seatMap.length; r++) {
            for(int c = 0; c < seatMap[0].length; c++) {

                if(seatMap[r][c].getState().equals(SeatState.H)) {
                    seatCount++;
                }
            }
        }

        return seatCount;
    }

    private Seat[][] buildSeatMap(String [] rows) {
        Seat [][] map = new Seat[rows.length][rows[0].length()];

        for(int r = 0; r < rows.length; r++) {
            char [] row = rows[r].toCharArray();
            for(int c = 0; c < row.length; c++) {
                Seat s = new Seat(r, c);

                switch(row[c]) {
                    case 'L':
                        s.setState(SeatState.L);
                        break;
                    case '#':
                        s.setState(SeatState.H);
                        break;
                    case '.':
                        s.setState(SeatState.X);
                        break;
                }

                map[r][c] = s;
            }
        }

        System.out.println("Printing map");

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j].getState() + " ");
            }
            System.out.println();
        }


        return map;
    }

    public void printSeatMap(Seat[][] seats) {
        System.out.println("Printing seat map");

        for(int i = 0; i < seats.length; i++) {
            for(int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j].getState() + " ");
            }
            System.out.println();
        }
    }

    class Seat {
        int row;
        int col;
        SeatState state;

        public Seat(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public SeatState getState() {
            return state;
        }

        public void setState(SeatState state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "Seat{" +
                    "row=" + row +
                    ", col=" + col +
                    ", state=" + state +
                    '}';
        }
    }

    public enum SeatState {
        H("H"),
        L("L"),
        X("X");

        private SeatState (String label) {
            this.label = label;
        }

        public String label;

        public String getLabel() {
            return label;
        }
    }


}
