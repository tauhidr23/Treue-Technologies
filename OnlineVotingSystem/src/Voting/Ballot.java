package Voting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Ballot {
    private String title;
    private List<String> options;
    private Map<String, Integer> voteCounts;

    public Ballot(String title, List<String> options) {
        this.title = title;
        this.options = options;
        this.voteCounts = new HashMap<>();
        for (String option : options) {
            voteCounts.put(option, 0);
        }
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void castVote(String option) {
        if (voteCounts.containsKey(option)) {
            voteCounts.put(option, voteCounts.get(option) + 1);
            System.out.println("Vote for '" + option + "' successfully cast.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    public void displayResults() {
        System.out.println("Results for '" + title + "':");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
}
