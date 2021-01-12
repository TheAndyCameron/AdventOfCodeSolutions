package andy.adventofcode.solutions.seven;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BagTracker {

    private static final String SHINY_GOLD_BAG = "shiny gold";

    private static final String CONTAINS_SPLIT_KEY = " contain ";
    private static final String BAG_LIST_SPLIT_KEY = ", ";
    private static final String BAG = " bag";
    private static final String BAGS = " bags";
    private static final String FULL_STOP = ".";

    Map<String, List<String>> containedInMapping;
    Set<String> bagsContainingGold;

    public BagTracker(String inputFile) {
        containedInMapping = new HashMap<>();
        bagsContainingGold = new TreeSet<>();
        getInputLines(inputFile);
    }

    /**
     * Reads the file located at the given path to populate the "containedInMapping" for use later.
     * @param inputFile Input file location
     */
    private void getInputLines(String inputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            while (reader.ready()) {
                parseLineAndAddToMap(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("Unfortunately some bad stuff happened");
            e.printStackTrace();
        }
    }

    /**
     * Takes a string in the format of a complete line from the input file.
     * @param line Line from input file
     */
    private void parseLineAndAddToMap(String line) {
        if (line == null || line.equals("")) {
            return;
        }

        line = line.replace(BAGS, "");
        line = line.replace(BAG, "");
        line = line.replace(FULL_STOP, "");

        String[] containsPair = line.split(CONTAINS_SPLIT_KEY);
        String parentBag = containsPair[0];

        String[] containedItems = containsPair[1].split(BAG_LIST_SPLIT_KEY);

        for (String containedItem : containedItems) {
            String[] itemSplit = containedItem.split(" ", 2);
            String childBag = itemSplit[1];
            System.out.println(childBag);

            if (!containedInMapping.containsKey(childBag)) {
                containedInMapping.put(childBag, new ArrayList<>());
            }

            containedInMapping.get(childBag).add(parentBag);
        }
    }

    /**
     * Evaluate how many bags can at some point contain a shiny gold bag.
     * @return Number of bags
     */
    public int howManyBagsEventuallyContainAShinyGoldBag() {
        bagsContainingGold.clear();

        expandBagSet(SHINY_GOLD_BAG, bagsContainingGold);

        return bagsContainingGold.size();
    }

    /**
     * Adds all possible parent bags to the set and then recurs on each parent, until all reachable bags are found.
     * @param containedBag Bag being considered
     * @param parentBags Aggregated set of reachable bags
     */
    private void expandBagSet(String containedBag, Set<String> parentBags) {
        List<String> parents = containedInMapping.get(containedBag);

        if (parents == null) {
            return;
        }

        for (String bag : parents) {
            if (parentBags.contains(bag)) {
                // Bag already considered
                continue;
            }
            parentBags.add(bag);
            expandBagSet(bag, parentBags);
        }

    }

}
