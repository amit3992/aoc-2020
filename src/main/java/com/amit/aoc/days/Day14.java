package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 implements Day {

    private String[] data;
    Map<String, Long> memory;
    public Day14() throws IOException {
        this.data =  Arrays.stream(readDay(14).split(System.lineSeparator())).toArray(String[]::new);
        this.memory = new HashMap<>();
    }

    public static void main(String[] args) throws IOException  {
        new Day14().printParts();
    }

    @Override
    public Object part1() throws IOException {

        String [] programData = this.data;
        char [] bitMask = new char[36];

        for(String program : programData) {
            String[] parts = program.split("=");

            if(parts[0].trim().equals("mask")) {
                bitMask = parts[1].trim().toCharArray();
            } else {
                long result = 0;
                int index = parseIndex(parts[0].substring(4, parts[0].length() - 2));
                String number = String.format("%36s", Integer.toBinaryString(Integer.parseInt(parts[1].trim()))).replace(" ", "0");

                for(int i = 0; i < bitMask.length; i++)
                {
                    if(bitMask[i] != 'X')
                        result = (result << 1) | Integer.parseInt(String.valueOf(bitMask[i]));
                    else
                        result = (result << 1) | Integer.parseInt(String.valueOf(number.charAt(i)));
                }

                memory.put(String.valueOf(index), result);
            }
        }

        long sum = 0;
        for(Long value : memory.values())
        {
            sum += value;
        }

        return sum;
    }

    @Override
    public Object part2() throws IOException {

        String [] programData = this.data;
        memory.clear();
        char [] bitMask = new char[36];

        for(String program : programData) {
            String[] parts = program.split("=");

            if(parts[0].trim().equals("mask")) {
                bitMask = parts[1].trim().toCharArray();
            } else {
                long result = 0;
                List<char[]> address = new ArrayList<>();
                int index = parseIndex(parts[0].substring(4, parts[0].length() - 2));
                address.add(String.format("%36s", Integer.toBinaryString(index)).replace(" ", "0").toCharArray());
                long number = Integer.parseInt(parts[1].trim());
                for(int i = 0; i < bitMask.length; i++)
                {
                    for(int j = address.size() - 1; j >= 0; j--)
                    {
                        char[] addressEdit = address.remove(j);
                        if(bitMask[i] == 'X')
                        {
                            addressEdit[i] = '0';
                            address.add(addressEdit);
                            char[] clone = addressEdit.clone();
                            clone[i] = '1';
                            address.add(clone);
                        }
                        else if(bitMask[i] == '1')
                        {
                            addressEdit[i] = '1';
                            address.add(addressEdit);
                        }
                        else
                        {
                            address.add(addressEdit);
                        }
                    }
                }

                for(char[] addr : address)
                    memory.put(new String(addr), number);
            }
        }

        long sum = 0;
        for(Long value : memory.values())
        {
            sum += value;
        }

        return sum;
    }

    private int parseIndex(String mem) {
        int result = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(mem);
        if(m.matches()) {
            result = Integer.parseInt(m.group());
        }
        return result;
    }


}
