package com.amit.aoc.days;

import com.amit.aoc.common.Day;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 implements Day {

    private String[] data;
    public Day4() throws IOException {
        this.data =  Arrays.stream(readDay(4).split(System.lineSeparator())).toArray(String[]::new);
    }

    public static void main(String[] args) throws IOException
    {
        new Day4().printParts();
    }

    @Override
    public Object part1() throws IOException {

        List<Passport> passports = buildPassports(this.data);

        int validPassports = 0;

        for(Passport p : passports) {
            if(p.isValid()) {
                validPassports +=1;
            }
        }

        return validPassports;
    }

    @Override
    public Object part2() throws IOException {

        /*
        * Remove validation for isByr etc in Passport class
        * */
        System.out.println("Check Part 1");
        return Integer.MIN_VALUE;
    }

    public List<Passport> buildPassports(String [] data) {
        List<Passport> passports = new ArrayList<>();
        ArrayList<String> singlePassportData = new ArrayList<>();
        for(String s : data) {
            if(!s.equals("")) {
                singlePassportData.add(s);
            } else {
                passports.add(new Passport().buildPassportFromStrings(singlePassportData));
                // System.out.println("--------- passportData processed ------------");
                // System.out.println(singlePassportData);
                singlePassportData.clear();
            }
        }

        if(!singlePassportData.isEmpty()) {
            passports.add(new Passport().buildPassportFromStrings(singlePassportData));
            // System.out.println("--------- passportData ------------");
            // System.out.println(singlePassportData);
        }

        return passports;
    }

    class Passport {


        private int byr;
        private int iyr;
        private int eyr;
        private Height hgt;
        private String ecl;
        private String hcl;
        private String pid;
        private long cid;
        private boolean isValid;

        /* For validity check */
        private boolean isByr;
        private boolean isIyr;
        private boolean isEyr;
        private boolean isHgt;
        private boolean isEcl;
        private boolean isHcl;
        private boolean isPid;
        private boolean isCid;
        private boolean isStrictValidation;

        private Set<String> eclVals;

        @Override
        public String toString() {
            return "Passport{" +
                    "byr=" + byr +
                    ", iyr=" + iyr +
                    ", eyr=" + eyr +
                    ", hgt=" + hgt +
                    ", ecl='" + ecl + '\'' +
                    ", hcl='" + hcl + '\'' +
                    ", pid=" + pid +
                    ", cid=" + cid +
                    ", isValid=" + isValid +
                    '}';
        }

        public Passport() {
            this.eclVals = Stream.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    .collect(Collectors.toCollection(HashSet::new));
        }

        public Passport buildPassportFromStrings(List<String> data) {
            Passport p = new Passport();
            for(String dataLine : data) {
                String [] passportData = dataLine.split(" ");
                // System.out.println(passportData);

                if(passportData.length > 0) {
                    for(String pData : passportData) {
                        String [] pVals = pData.split(":");

                        switch (pVals[0]) {
                            case "ecl":
                                p.setEcl(pVals[1]);
                                break;
                            case "pid":
                                p.setPid(pVals[1]);
                                break;
                            case "byr":
                                p.setByr(Integer.parseInt(pVals[1]));
                                break;
                            case "eyr":
                                p.setEyr(Integer.parseInt(pVals[1]));
                                break;
                            case "hgt":
                                p.setHgt(new Height(pVals[1]));
                                break;
                            case "cid":
                                p.setCid(Long.parseLong(pVals[1]));
                                break;
                            case "iyr":
                                p.setIyr(Integer.parseInt(pVals[1]));
                                break;
                            case "hcl":
                                p.setHcl(pVals[1]);
                                break;

                                default:
                        }
                    }
                }
            }

            p.checkValidity();
            System.out.println(p.toString());
            return p;
        }

        private void checkValidity() {
            this.isValid = this.isPid && this.isEcl && this.isHgt && this.isEyr && this.isByr && this.isIyr && this.isHcl;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getHcl() {
            return hcl;
        }

        public void setHcl(String hcl) {
            this.hcl = hcl;

            if(hcl.charAt(0) != '#') {
                this.isHcl = false;
                return;
            }

            StringBuilder sb = new StringBuilder(hcl);
            if(sb.charAt(0) == '#') {
                sb.deleteCharAt(0);
            }
            Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,}$");
            boolean value = p.matcher(sb.toString()).matches();
            this.isHcl = value;
        }

        public int getByr() {
            return byr;
        }

        public void setByr(int byr) {
            this.byr = byr;

            if( (String.valueOf(byr).length() == 4) && (1920 <= byr && byr <= 2002) ) {
                this.isByr = true;
            }
        }

        public int getIyr() {
            return iyr;
        }

        public void setIyr(int iyr) {
            this.iyr = iyr;

            if( (String.valueOf(iyr).length() == 4) && (2010 <= iyr && iyr <= 2020) ) {
                this.isIyr = true;
            }
        }

        public int getEyr() {
            return eyr;
        }

        public void setEyr(int eyr) {
            this.eyr = eyr;
            if( (String.valueOf(eyr).length() == 4) && (2020 <= eyr && eyr <= 2030) ) {
                this.isEyr = true;
            }
        }

        public Height getHgt() {
            return hgt;
        }

        public void setHgt(Height hgt) {
            this.hgt = hgt;
            this.isHgt = hgt.isValid();
        }

        public String getEcl() {
            return ecl;
        }

        public void setEcl(String ecl) {
            this.ecl = ecl;

            if(eclVals.contains(ecl)) {
                this.isEcl = true;
            }
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;

            boolean isNumeric = pid.chars().allMatch( Character::isDigit );

            this.isPid = isNumeric && (pid.toCharArray().length == 9);
        }

        public long getCid() {
            return cid;
        }

        public void setCid(long cid) {
            this.cid = cid;
            this.isCid = true;
        }
    }
    enum HeightUnit {
        cm("CM"),
        in("IN");

        public HeightUnit fromString(String s) {
            if(s.equals("cm")) {
                return cm;
            }
            if(s.equals("in")) {
                return in;
            }
            return null;
        }

        public final String label;

        private HeightUnit(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    class Height {
        private int val;
        private HeightUnit heightUnit;
        private boolean isValid;

        public boolean isValid() {
            return isValid;
        }

        public Height(String ht) {

            char [] hVals = ht.toCharArray();
            StringBuilder val = new StringBuilder();

            for(int i = 0; i < hVals.length - 1; i++) {
                if(Character.isDigit(hVals[i])) {
                    val.append(hVals[i]);
                }
            }

            this.val = Integer.parseInt(val.toString());

            if(hVals[hVals.length - 1] == 'm') {
                this.heightUnit = HeightUnit.cm;

                if(150 <= this.val && this.val <= 193) {
                    this.isValid = true;
                }

            } else if(hVals[hVals.length - 1] == 'n') {
                this.heightUnit = HeightUnit.in;

                if(59 <= this.val && this.val <= 76) {
                    this.isValid = true;
                }
            }

            if(this.heightUnit == null) {
                this.isValid = false;
            }

        }

        @Override
        public String toString() {
            return "Height{" +
                    "val=" + val +
                    ", heightUnit=" + heightUnit +
                    '}';
        }
    }

}
