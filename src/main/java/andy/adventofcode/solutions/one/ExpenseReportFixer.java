package andy.adventofcode.solutions.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseReportFixer {

    List<Integer> expenses;

    public ExpenseReportFixer(String inputFile) {
        expenses = new ArrayList<>();
        parseInputFile(inputFile);
        Collections.sort(expenses);
    }

    /**
     * Reads input file of one number per line and adds them to the expenses list.
     * @param inputFile Location of input file
     */
    private void parseInputFile(String inputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            while (reader.ready()) {
                expenses.add(Integer.parseInt(reader.readLine()));
            }
        } catch (IOException e) {
            System.out.println("Unfortunately some bad stuff happened");
            e.printStackTrace();
        }
    }

    /**
     * Find the product of the two numbers that sum to 2020 in the sorted expenses list.
     * @return Product of two numbers summing to 2020
     */
    public int productOf2NumbersSummingTo2020() {
        int lowerIndex = 0;
        int upperIndex = expenses.size() - 1;
        boolean upperDescending = true;

        while ((expenses.get(lowerIndex) + expenses.get(upperIndex)) != 2020) {
            boolean sumTooHigh = (expenses.get(lowerIndex) + expenses.get(upperIndex)) > 2020;

            if (upperDescending == sumTooHigh) {
                upperIndex += upperDescending ? -1 : 1;
            } else {
                upperDescending = !upperDescending;
                lowerIndex++;
            }
        }

        return expenses.get(lowerIndex) * expenses.get(upperIndex);
    }

    /**
     * Find the product of the three numbers that sum to 2020 in the sorted expenses list
     * @return Product of three numbers summing to 2020
     */
    public int productOf3NumbersSummingTo2020() {
        int lowerIndex = 0;
        int middleIndex = 1;
        int upperIndex = expenses.size() - 1;
        boolean upperDescending = true;

        int sum = expenses.get(lowerIndex) + expenses.get(middleIndex) + expenses.get(upperIndex);
        while (sum != 2020) {
            boolean sumTooHigh = sum > 2020;

            if (upperDescending == sumTooHigh) {
                upperIndex += upperDescending ? -1 : 1;
                middleIndex = upperDescending ? (lowerIndex + 1) : (upperIndex - 1);
            } else {
                if ((upperDescending && (middleIndex < upperIndex-1))
                        || (!upperDescending && (middleIndex > lowerIndex+1))) {
                    middleIndex += upperDescending ? 1 : -1;
                } else {
                    upperDescending = !upperDescending;
                    lowerIndex++;
                }
            }

            sum = expenses.get(lowerIndex) + expenses.get(middleIndex) + expenses.get(upperIndex);
        }

        return expenses.get(lowerIndex) * expenses.get(middleIndex) * expenses.get(upperIndex);
    }

}
