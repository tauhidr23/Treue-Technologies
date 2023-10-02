package Voting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Create Ballot");
            System.out.println("4. Display Ballots");
            System.out.println("5. Cast Vote");
            System.out.println("6. Display Results");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUsername = sc.next();
                    System.out.print("Enter password: ");
                    String regPassword = sc.next();
                    votingSystem.registerUser(regUsername, regPassword);
                    System.out.println("Registration successful.");
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String username = sc.next();
                    System.out.print("Enter password: ");
                    String password = sc.next();
                    if (votingSystem.authenticateUser(username, password)) {
                        System.out.println("Login successful.");
                        menu(username, votingSystem, sc);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3:
                    System.out.print("Enter ballot title: ");
                    String ballotTitle = sc.next();
                    List<String> options = new ArrayList<>();
                    while (true) {
                        System.out.print("Enter option (or 'done' to finish): ");
                        String option = sc.next();
                        if (option.equals("done")) {
                            break;
                        }
                        options.add(option);
                    }
                    votingSystem.createBallot(ballotTitle, options);
                    System.out.println("Ballot creation successful.");
                    break;

                case 4:
                    votingSystem.displayBallots();
                    break;

                case 5:
                    System.out.print("Enter your username: ");
                    String voteUsername = sc.next();
                    System.out.print("Enter the index of the ballot: ");
                    int ballotIndex = sc.nextInt();
                    
                    if (ballotIndex >= 0 && ballotIndex < votingSystem.ballots.size()) {
                        Ballot selectedBallot = votingSystem.ballots.get(ballotIndex);
                        System.out.println("Available options:");
                        for (int i = 0; i < selectedBallot.getOptions().size(); i++) {
                            System.out.println((i + 1) + ". " + selectedBallot.getOptions().get(i));
                        }
                        System.out.print("Select an option: ");
                        int optionIndex = sc.nextInt();
                        
                        if (optionIndex >= 1 && optionIndex <= selectedBallot.getOptions().size()) {
                            votingSystem.castVote(voteUsername, ballotIndex, selectedBallot.getOptions().get(optionIndex - 1));
                        } else {
                            System.out.println("Invalid option index.");
                        }
                    } else {
                        System.out.println("Invalid ballot index.");
                    }
                    break;

                case 6:
                    System.out.print("Enter the index of the ballot: ");
                    int resultsIndex = sc.nextInt();
                    votingSystem.displayResults(resultsIndex);
                    break;

                case 7:
                    System.out.println("Exiting the application.");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private static void menu(String username, VotingSystem votingSystem, Scanner sc) {
        System.out.println("Welcome, " + username + "!");
        while (true) {
            System.out.println("1. Display Ballots");
            System.out.println("2. Cast Vote");
            System.out.println("3. Display Results");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            

            switch (choice) {
                case 1:
                    votingSystem.displayBallots();
                    break;

                case 2:
                    System.out.print("Enter the index of the ballot: ");
                    int ballotIndex = sc.nextInt();
                    if (ballotIndex >= 0 && ballotIndex < votingSystem.ballots.size()) {
                        Ballot selectedBallot = votingSystem.ballots.get(ballotIndex);
                        System.out.println("Available options:");
                        for (int i = 0; i < selectedBallot.getOptions().size(); i++) {
                            System.out.println((i + 1) + ". " + selectedBallot.getOptions().get(i));
                        }
                        System.out.print("Select an option: ");
                        int optionIndex = sc.nextInt();
                        
                        if (optionIndex >= 1 && optionIndex <= selectedBallot.getOptions().size()) {
                            votingSystem.castVote(username, ballotIndex, selectedBallot.getOptions().get(optionIndex - 1));
                        } else {
                            System.out.println("Invalid option index.");
                        }
                    } else {
                        System.out.println("Invalid ballot index.");
                    }
                    break;

                case 3:
                    System.out.print("Enter the index of the ballot: ");
                    int resultsIndex = sc.nextInt();
                    
                    votingSystem.displayResults(resultsIndex);
                    break;

                case 4:
                    System.out.println("Logging out.");
                    return;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}