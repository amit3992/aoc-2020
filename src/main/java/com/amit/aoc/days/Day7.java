package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.*;

public class Day7 implements Day {

    String [] data;
    Map<String, Map<String, Integer>> bags;
    public Day7() throws IOException {
        this.bags = new HashMap<>();
        this.data =  Arrays.stream(readDay(7).split(System.lineSeparator()))
                .toArray(String[]::new);
        parseBagRules(data);
        // System.out.println("Bag rule count: " + this.bags.size());
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Day7:");
        new Day7().printParts();
    }

    @Override
    public Object part1() throws IOException {

        Queue<String> bagqueue = new LinkedList<>();
        Set<String> result = new HashSet<>();

        for(String key : this.bags.keySet()) {
            if(this.bags.get(key).containsKey("shiny gold")) {
                bagqueue.add(key);
                result.add(key);
            }
        }

        while(!bagqueue.isEmpty()) {
            String currentBag = bagqueue.peek();
            // System.out.println(currentBag);
            for(String key : this.bags.keySet()) {

                if(this.bags.get(key).containsKey(currentBag)) {
                    if(!result.contains(key)) {
                        bagqueue.add(key);
                        result.add(key);
                    }
                }
            }
            bagqueue.remove();
        }

        return result.size();
    }

    @Override
    public Object part2() throws IOException {

        int count = nestedCount(this.bags, "shiny gold") - 1;
        return count;
    }

    private int nestedCount(Map<String, Map<String, Integer>> rules, String bagColor) {
        return 1 + rules.getOrDefault(bagColor, new HashMap<>()).entrySet().stream()
                .mapToInt(e -> e.getValue() * nestedCount(rules, e.getKey()))
                .sum();
    }

    private void parseBagRules(String [] data) {
        for(String rule : data) {
            // System.out.println(rule);
            String [] bg = rule.split("bags contain ");
            String parentBag = bg[0].trim();

            if(bg[1].trim().contains("no other bags")) { /* Process empty bags */
                this.bags.put(bg[0].trim(), new HashMap<>());
            } else {
                String [] bgData = bg[1].trim().split(", ");
                for(String val : bgData) {
                    processBag(parentBag, val);
                }
            }
            // System.out.println("-------------------");
        }
    }

    private void processBag(String parentBag, String data) {
        String [] values = data.trim().split(" ");
        Bag b = new Bag(values[1] + " " + values[2]);

        addBagInMap(parentBag, b, Integer.parseInt(values[0]));
    }

    private void addBagInMap(String parentBag, Bag b, int quantity) {
        if(!this.bags.containsKey(parentBag)) {
            HashMap<String, Integer> data = new HashMap<>();
            data.put(b.getColor(), quantity);
            this.bags.put(parentBag, data);
        } else {
            Map<String, Integer> data = this.bags.get(parentBag);
            if(data.containsKey(b.getColor())) {
                data.put(b.getColor(), data.get(b.getColor()).intValue() + quantity);
            } else {
                data.put(b.getColor(), quantity);
            }
        }
    }

    class Bag {
        String color;
        HashMap<Bag, Integer> bags;

        public Bag(String color) {
            this.color = color;
            this.bags = new HashMap<>();
        }

        public void addBag(Bag b, int quantity) {
            bags.put(b, quantity);
        }

        public String getColor() {
            return color;
        }
    }
}
