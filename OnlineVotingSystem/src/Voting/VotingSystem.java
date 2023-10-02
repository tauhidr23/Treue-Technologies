package Voting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VotingSystem {
    private Map<String, User> users;
    List<Ballot> ballots;

    public VotingSystem() {
        users = new HashMap<>();
        ballots = new ArrayList<>();
    }

    public void registerUser(String username, String password) {
        User newUser = new User(username, password);
        users.put(username, newUser);
    }

    public void createBallot(String title, List<String> options) {
        Ballot newBallot = new Ballot(title, options);
        ballots.add(newBallot);
    }

    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            return user.authenticate(password);
        }
        return false;
    }

    public void displayBallots() {
        System.out.println("Available ballots:");
        for (int i = 0; i < ballots.size(); i++) {
            System.out.println((i + 1) + ". " + ballots.get(i).getTitle());
        }
    }

    public void castVote(String username, int ballotIndex, String option) {
        if (ballotIndex >= 0 && ballotIndex < ballots.size()) {
            ballots.get(ballotIndex).castVote(option);
        } else {
            System.out.println("Invalid ballot index.");
        }
    }

    public void displayResults(int ballotIndex) {
        if (ballotIndex >= 0 && ballotIndex < ballots.size()) {
            ballots.get(ballotIndex).displayResults();
        } else {
            System.out.println("Invalid ballot index.");
        }
    }
}
