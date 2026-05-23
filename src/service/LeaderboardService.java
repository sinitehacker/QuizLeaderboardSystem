package service;

import model.Participant;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * LeaderboardService - Handles leaderboard, search, score update,
 * participant deletion, and score reports.
 *
 * @author Divyasree
 */
public class LeaderboardService {

    private DatabaseService dbService;
    private Scanner scanner;

    public LeaderboardService() {
        this.dbService = new DatabaseService();
        this.scanner = new Scanner(System.in);
    }

    public void displayLeaderboard() {
        List<Participant> participants = dbService.getAllParticipants();

        if (participants.isEmpty()) {
            System.out.println("\nNo participants found.");
            return;
        }

        participants.sort(Comparator.comparingInt(Participant::getScore).reversed());

        System.out.println("\n=== LEADERBOARD ===");
        System.out.printf("%-5s %-20s %-20s %-10s%n", "ID", "Name", "Department", "Score");
        System.out.println("------------------------------------------------------------");

        for (Participant participant : participants) {
            System.out.printf("%-5d %-20s %-20s %-10d%n",
                    participant.getId(),
                    participant.getName(),
                    participant.getDepartment(),
                    participant.getScore());
        }
    }

    public void searchParticipant() {
        System.out.println("\n=== SEARCH PARTICIPANT ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter participant ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Participant participant = dbService.getParticipantById(id);
            if (participant == null) {
                System.out.println("Participant not found.");
            } else {
                displayParticipant(participant);
            }
        } else if (choice == 2) {
            System.out.print("Enter participant name: ");
            String name = scanner.nextLine();

            List<Participant> participants = dbService.searchParticipantsByName(name);
            if (participants.isEmpty()) {
                System.out.println("No matching participants found.");
            } else {
                for (Participant participant : participants) {
                    displayParticipant(participant);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void updateScore() {
        System.out.println("\n=== UPDATE SCORE ===");
        System.out.print("Enter participant ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter new score: ");
        int newScore = scanner.nextInt();
        scanner.nextLine();

        boolean updated = dbService.updateScore(id, newScore);
        if (updated) {
            System.out.println("Score updated successfully.");
        } else {
            System.out.println("Score update failed.");
        }
    }

    public void deleteParticipant() {
        System.out.println("\n=== DELETE PARTICIPANT ===");
        System.out.print("Enter participant ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean deleted = dbService.deleteParticipant(id);
        if (deleted) {
            System.out.println("Participant deleted successfully.");
        } else {
            System.out.println("Participant deletion failed.");
        }
    }

    public void generateReport() {
        System.out.println("\n=== SCORE REPORT ===");
        System.out.println("Highest Score: " + dbService.getHighestScore());
        System.out.println("Lowest Score: " + dbService.getLowestScore());
        System.out.printf("Average Score: %.2f%n", dbService.getAverageScore());
    }

    private void displayParticipant(Participant participant) {
        System.out.println("\nParticipant Details");
        System.out.println("ID: " + participant.getId());
        System.out.println("Name: " + participant.getName());
        System.out.println("Department: " + participant.getDepartment());
        System.out.println("Score: " + participant.getScore());
    }

    public static void main(String[] args) {
        LeaderboardService leaderboardService = new LeaderboardService();
        Scanner menuScanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== LEADERBOARD MENU ===");
            System.out.println("1. Display Leaderboard");
            System.out.println("2. Search Participant");
            System.out.println("3. Update Score");
            System.out.println("4. Delete Participant");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = menuScanner.nextInt();

            switch (choice) {
                case 1:
                    leaderboardService.displayLeaderboard();
                    break;
                case 2:
                    leaderboardService.searchParticipant();
                    break;
                case 3:
                    leaderboardService.updateScore();
                    break;
                case 4:
                    leaderboardService.deleteParticipant();
                    break;
                case 5:
                    leaderboardService.generateReport();
                    break;
                case 6:
                    System.out.println("Exiting Leaderboard Module.");
                    menuScanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
