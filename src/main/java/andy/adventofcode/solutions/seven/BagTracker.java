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
    private static final String NO_OTHER_BAGS = "no other bags";

    Map<String, List<String>> containedInMapping;
    Map<String, List<String>> containsMapping;
    Map<String, List<Integer>> containsCountMapping;

    public BagTracker(String inputFile) {
        containedInMapping = new HashMap<>();
        containsMapping = new HashMap<>();
        containsCountMapping = new HashMap<>();
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
        if (line == null || line.equals("") || line.contains(NO_OTHER_BAGS)) {
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
            int childBagCount = Integer.parseInt(itemSplit[0]);
            String childBag = itemSplit[1];

            if (!containedInMapping.containsKey(childBag)) {
                containedInMapping.put(childBag, new ArrayList<>());
            }
            if (!containsMapping.containsKey(parentBag)) {
                containsMapping.put(parentBag, new ArrayList<>());
                containsCountMapping.put(parentBag, new ArrayList<>());
            }

            containedInMapping.get(childBag).add(parentBag);
            containsMapping.get(parentBag).add(childBag);
            containsCountMapping.get(parentBag).add(childBagCount);
        }
    }

    /**
     * Evaluate how many bags can at some point contain a shiny gold bag.
     * @return Number of bags
     */
    public int howManyBagsEventuallyContainAShinyGoldBag() {
        Set<String> bagsContainingGold = new TreeSet<>();

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

    /**
     * Evaluates how many bags are contained inside a shiny gold bag, not including the shiny gold bag itself.
     * @return Number of bags inside a shiny gold bag.
     */
    public int howManyBagsInsideAShinyGoldBag() {
        return expandBagCount(SHINY_GOLD_BAG, new HashMap<>()) - 1;
    }

    /**
     * Evaluates how many bags are contained in a given bag, including the given one.
     * @param parentBag The bag to evaluate.
     * @param evaluatedBags A running size map to avoid excessive recursion
     * @return The number of bags
     */
    private int expandBagCount(String parentBag, HashMap<String, Integer> evaluatedBags) {
        List<String> children = containsMapping.get(parentBag);
        List<Integer> childCounts = containsCountMapping.get(parentBag);

        if (children == null) {
            return 1;
        } else if (evaluatedBags.containsKey(parentBag)) {
            return evaluatedBags.get(parentBag);
        } else {
            int parentBagSize = 1;

            for (int i = 0; i < children.size(); i++) {
                parentBagSize += childCounts.get(i) * expandBagCount(children.get(i), evaluatedBags);
            }

            evaluatedBags.put(parentBag, parentBagSize);

            return parentBagSize;
        }
    }

}
