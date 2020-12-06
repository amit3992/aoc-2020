package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.*;

public class Day6 implements Day {

    String [] data;
    ArrayList<Group> groups;
    public Day6() throws IOException {
        this.groups = new ArrayList<>();
        this.data =  Arrays.stream(readDay(6).split(System.lineSeparator()))
                .toArray(String[]::new);
        buildGroups();
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Day6:");
        new Day6().printParts();
    }

    @Override
    public Object part1() throws IOException {
        long count = 0;
        for(Group g : this.groups) {
            // System.out.println(g.toString());
            count += g.getTotalQuestions();
        }
        return count;
    }

    @Override
    public Object part2() throws IOException {
        long count = 0;
        for(Group g : this.groups) {
            // System.out.println(g.toString());
            count += g.getCommonQuestionsAnswered();
        }
        return count;
    }

    private void buildGroups() {
        ArrayList<String> groupData = new ArrayList<>();
        for(String val : this.data) {
            if(val.equals("")) {
                // System.out.println("Group Data: " + groupData.toString());
                this.groups.add(addDataToGroup(groupData));
                groupData.clear();
                continue;
            }
            groupData.add(val);
        }

        if(groupData.size() > 0) {
            this.groups.add(addDataToGroup(groupData));
        }
    }

    private Group addDataToGroup(List<String> groupData) {
        Group g = new Group();
        g.setnPeople(groupData.size());
        StringBuilder sb = new StringBuilder();
        for(String s : groupData) {
            sb.append(s);
        }
        g.setQuestionsForP1(sb.toString());
        g.setQuestionsForP2(groupData);
        return g;
    }



    class Group {
        private int nPeople;
        private Set<Character> p1Questions;
        private int commonQuestionsAnswered;
        private final int MAX_CHAR = 26;

        public Group() {
            this.p1Questions = new HashSet<>();
        }

        public int getCommonQuestionsAnswered() {
            return commonQuestionsAnswered;
        }

        public void setnPeople(int nPeople) {
            this.nPeople = nPeople;
        }

        public int getTotalQuestions() {
            return this.p1Questions.size();
        }

        public void setQuestionsForP2(List<String> data) {
            this.commonQuestionsAnswered = getCommonQuestionsAndSetValues(data, data.size());
        }

        public void setQuestionsForP1(String data) {
            for(Character c : data.toCharArray()) {
                this.p1Questions.add(c);
            }
        }

        private int getCommonQuestionsAndSetValues(List<String> data,
                                            int n)
        {
            Boolean[] prim = new Boolean[MAX_CHAR];
            Arrays.fill(prim, new Boolean(true));

            for(String str : data) {

                Boolean[] sec = new Boolean[MAX_CHAR];
                Arrays.fill(sec, new Boolean(false));

                // for every character of ith string
                for (int j = 0; j < str.length(); j++) {

                    // if character is present in all
                    // strings before, mark it.
                    if (prim[str.charAt(j) - 'a'])
                        sec[str.charAt(j) - 'a'] = true;
                }

                // copy whole secondary array into primary
                System.arraycopy(sec, 0, prim, 0, MAX_CHAR);
            }

            // displaying common characters
            int count = 0;
            for (int i = 0; i < 26; i++)
                if(prim[i]){
                    count +=1;
//                    System.out.print(Character.toChars(i
//                            + 97));
//                    System.out.print(" ");
                }

                return count;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "nPeople=" + nPeople +
                    ", questions=" + this.p1Questions.size() +
                    ", commonQuestionsAnswered= " + this.commonQuestionsAnswered +
                    '}';
        }
    }


}
