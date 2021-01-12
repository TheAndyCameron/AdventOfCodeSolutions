package andy.adventofcode.solutions.seven;

public class Main {

    public static void main(String[] args) {

        BagTracker bagTracker = new BagTracker("./resources/input_files/input7.txt");
        int bagsContainingGold = bagTracker.howManyBagsEventuallyContainAShinyGoldBag();
        System.out.println("The number of bags eventually containing a gold bag is " + bagsContainingGold);

        int bagsGoldContains = bagTracker.howManyBagsInsideAShinyGoldBag();
        System.out.println("The number of bags contained inside a shiny gold bag is " + bagsGoldContains);

    }

}
