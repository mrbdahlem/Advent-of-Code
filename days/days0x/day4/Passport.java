package days.days0x.day4;

import java.util.*;

public class Passport {
    private String id;
    private String countryId;
    private int birthYear = -1;
    private int issueYear = -1;
    private int expirationYear = -1;
    private String height;
    private String hairColor;
    private String eyeColor;

    public Passport (String data) {
        String[] tokens = data.split("\\s");
        //System.out.println(Arrays.toString(tokens));

        for (String token : tokens) {
            String[] kv = token.split(":");
            
            switch (kv[0]) {
                case "byr":
                    birthYear = Integer.parseInt(kv[1]);
                    break;
                case "iyr":
                    issueYear = Integer.parseInt(kv[1]);
                    break;
                case "eyr":
                    expirationYear = Integer.parseInt(kv[1]);
                    break;
                case "hgt":
                    height = kv[1];
                    break;
                case "hcl":
                    hairColor = kv[1];
                    break;
                case "ecl":
                    eyeColor = kv[1];
                    break;
                case "pid":
                    id = kv[1];
                    break;
                case "cid":
                    countryId = kv[1];
                    break;
            }
        }
    }

    public boolean isComplete(boolean hacked) {
        return  id != null &&
                (hacked || countryId != null) &&
                hairColor != null &&
                height != null &&
                eyeColor != null &&
                birthYear != -1 &&
                issueYear != -1 &&
                expirationYear != -1;
    }

    public boolean isValid(boolean hacked) {
        return  validId() &&
                validCountry(hacked) &&
                validHair() &&
                validEye() &&
                validHeight() &&
                validBirth() &&
                validIssue() &&
                validExpiration();
    }

    private boolean validId() {
        if (id == null) 
            return false;

        return id.matches("^[0-9]{9}$");
    }

    private boolean validCountry(boolean hacked) {
        return (hacked || countryId != null);
    }

    private boolean validHair() {
        if (hairColor == null) 
            return false;

        return hairColor.matches("^#[0-9a-f]{6}$");
    }

    private boolean validEye() {
        return "amb".equals(eyeColor) ||
                "blu".equals(eyeColor) ||
                "brn".equals(eyeColor) ||
                "gry".equals(eyeColor) ||
                "grn".equals(eyeColor) ||
                "hzl".equals(eyeColor) ||
                "oth".equals(eyeColor);
    }

    private boolean validHeight() {
        if (height == null)
            return false;

        if (height.endsWith("cm")) {
            int val = Integer.parseInt(height.substring(0, height.length() - 2));
            return val >= 150 && val <= 193;
        }
        else if (height.endsWith("in")) {
            int val = Integer.parseInt(height.substring(0, height.length() - 2));
            return val >= 59 && val <= 76;
        }

        return false;
    }

    private boolean validBirth() {
        return birthYear >= 1920 && birthYear <= 2002;
    }

    private boolean validIssue() {
        return issueYear >= 2010 && issueYear <= 2020;
    }

    private boolean validExpiration() {
        return expirationYear >= 2020 && expirationYear <= 2030;
    }

    public String toString() {
        String s = "Passport: " + id + ":" + 
                    " cid: " + countryId +
                    " issued: " + issueYear +
                    " expires: " + expirationYear +
                    " born: " + birthYear +
                    " height: " + height +
                    " hair: " + hairColor +
                    " eyes: " + eyeColor;

        return s;
    }
}