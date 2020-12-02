package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.Arrays;

public class Day2 implements Day {

    PasswordCombo [] passwords;

    public Day2() throws IOException {
        this.passwords = Arrays.stream(readDay(2).split(System.lineSeparator())).map(PasswordCombo::new).toArray(PasswordCombo[]::new);
    }

    public static void main(String[] args) throws IOException
    {
        new Day2().printParts();
    }

    @Override
    public Object part1() throws IOException {
        long validPasswords = 0;
        for(PasswordCombo passwordCombo : passwords) {
            if(isValidPasswordPart1(passwordCombo.getPassword(), passwordCombo.getPolicy())) {
                validPasswords += 1;
            }
        }
        return validPasswords;
    }

    @Override
    public Object part2() throws IOException {

        long validPasswords = 0;
        for(PasswordCombo passwordCombo : passwords) {
            if(isValidPasswordPart2(passwordCombo.getPassword(), passwordCombo.getPolicy())) {
                validPasswords += 1;
            }
        }
        return validPasswords;
    }

    private boolean isValidPasswordPart1(String password, Policy policy) {
        long occurrences = countOccurrencesOfChar(policy.getCh(), password);

        return (occurrences >= policy.getMin()) && (occurrences <= policy.getMax());
    }

    private boolean isValidPasswordPart2(String password, Policy policy) {
        char [] passwordArr = password.toCharArray();
        return (passwordArr[policy.getMin() - 1] == policy.getCh()) ^ (passwordArr[policy.getMax() - 1] == policy.getCh());
    }

    private long countOccurrencesOfChar(char c, String targetString) {
        return targetString.chars().filter(ch -> ch == c).count();
    }
    class PasswordCombo {
        private final Policy policy;
        private final String password;

        public PasswordCombo (String data) {
            String[] passWordSplit = data.split(": ");
            policy = new Policy(passWordSplit[0]);
            password = passWordSplit[1];
        }

        public Policy getPolicy() {
            return policy;
        }

        public String getPassword() {
            return password;
        }
    }

    class Policy {
        private final int min;
        private final int max;
        private final char ch;

        public Policy(String policy) {
            String [] policyVal = policy.split(" ");
            String [] minMax = policyVal[0].split("-");
            ch = policyVal[1].charAt(0);
            min = Integer.parseInt(minMax[0]);
            max = Integer.parseInt(minMax[1]);
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public char getCh() {
            return ch;
        }
    }

}
