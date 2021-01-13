package andy.adventofcode.solutions;

import andy.adventofcode.solutions.one.ExpenseReportFixer;
import andy.adventofcode.solutions.seven.BagTracker;

public class Main {

    private static final String PATH_TO_INPUTS = "./resources/input_files/";
    private static final String INPUT_FILE_1 = PATH_TO_INPUTS + "input1.txt";
    private static final String INPUT_FILE_7 = PATH_TO_INPUTS + "input7.txt";


    public static void main(String[] args) {

        // Problem 1
        System.out.println("Problem 1");
        ExpenseReportFixer expenseReportFixer = new ExpenseReportFixer(INPUT_FILE_1);
        int productOfTwo = expenseReportFixer.productOf2NumbersSummingTo2020();
        System.out.println("The product of the 2 numbers that sum to 2020 is " + productOfTwo);
        int productOfThree = expenseReportFixer.productOf3NumbersSummingTo2020();
        System.out.println("The product of the 3 numbers that sum to 2020 is " + productOfThree);




        // Problem 7
        System.out.println("\nProblem 7");
        BagTracker bagTracker = new BagTracker(INPUT_FILE_7);
        int bagsContainingGold = bagTracker.howManyBagsEventuallyContainAShinyGoldBag();
        System.out.println("The number of bags eventually containing a gold bag is " + bagsContainingGold);

        int bagsGoldContains = bagTracker.howManyBagsInsideAShinyGoldBag();
        System.out.println("The number of bags contained inside a shiny gold bag is " + bagsGoldContains);

    }

}
