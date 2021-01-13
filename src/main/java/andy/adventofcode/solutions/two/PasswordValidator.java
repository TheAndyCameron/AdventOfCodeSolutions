package andy.adventofcode.solutions.two;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {

    private static final String LINE_SPLIT_KEY = ": ";
    private static final String RULE_SPLIT_KEY = " ";
    private static final String RULE_MIN_MAX_SPLIT_KEY = "-";

    List<String> passwords;
    List<PasswordRule> rules;

    public PasswordValidator(String inputFile) {
        passwords = new ArrayList<>();
        rules = new ArrayList<>();
        parseInputFile(inputFile);
    }

    /**
     * Reads input file of one number per line and adds them to the expenses list.
     * @param inputFile Location of input file
     */
    private void parseInputFile(String inputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            while (reader.ready()) {
                parseLine(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("Unfortunately some bad stuff happened");
            e.printStackTrace();
        }
    }

    private void parseLine(String line) {
        String[] splitLine = line.split(LINE_SPLIT_KEY);
        passwords.add(splitLine[1]);

        String[] splitRule = splitLine[0].split(RULE_SPLIT_KEY);
        String[] minMaxes = splitRule[0].split(RULE_MIN_MAX_SPLIT_KEY);
        char requiredChar = splitRule[1].charAt(0);
        int min = Integer.parseInt(minMaxes[0]);
        int max = Integer.parseInt(minMaxes[1]);

        rules.add(new PasswordRule(requiredChar, min, max));
    }

    public int getNumberOfValidPasswords() {
        int count = 0;

        for (int i = 0; i < passwords.size(); i++) {
            if (rules.get(i).isPasswordValid(passwords.get(i))) {
                count++;
            }
        }

        return count;
    }

    public int getNumberOfNewlyValidPasswords() {
        int count = 0;

        for (int i = 0; i < passwords.size(); i++) {
            if (rules.get(i).isPasswordNewlyValid(passwords.get(i))) {
                count++;
            }
        }

        return count;
    }


    protected static class PasswordRule {
        char requiredChar;
        int min;
        int max;

        protected PasswordRule(char requiredChar, int min, int max) {
            this.requiredChar = requiredChar;
            this.min = min;
            this.max = max;
        }

        protected boolean isPasswordValid(String password) {
            long count = password.chars().filter(ch -> ch == requiredChar).count();
            return min <= count && count <= max;
        }

        protected boolean isPasswordNewlyValid(String password) {
            return password.charAt(min-1) == requiredChar ^ password.charAt(max-1) == requiredChar;
        }
    }
}
