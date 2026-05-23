package main;
import database.DBConnection;

import model.Participant;
import service.DatabaseService;
import java.util.List;
import java.util.Scanner;

public class TestCRUD {
    
    private static DatabaseService dbService = new DatabaseService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("===== Testing Database CRUD Operations =====\n");
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- CRUD Test Menu ---");
            System.out.println("1. Add a test participant");
            System.out.println("2. View all participants");
            System.out.println("3. Search participant by ID");
            System.out.println("4. Update participant score");
            System.out.println("5. Delete participant");
            System.out.println("6. View statistics (highest/lowest/average)");
            System.out.println("7. Exit test");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    testAddParticipant();
                    break;
                case 2:
                    testViewAll();
                    break;
                case 3:
                    testSearchById();
                    break;
                case 4:
                    testUpdateScore();
                    break;
                case 5:
                    testDeleteParticipant();
                    break;
                case 6:
                    testStatistics();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting test...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
        
        DBConnection.closeConnection();
        scanner.close();
    }
    
    private static void testAddParticipant() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter score: ");
        int score = scanner.nextInt();
        scanner.nextLine();
        
        Participant p = new Participant(name, dept, score);
        dbService.addParticipant(p);
    }
    
    private static void testViewAll() {
        List<Participant> participants = dbService.getAllParticipants();
        if (participants.isEmpty()) {
            System.out.println("No participants found!");
        } else {
            System.out.println("\n===== All Participants =====");
            for (Participant p : participants) {
                System.out.println("ID: " + p.getId() + " | Name: " + p.getName() + 
                                   " | Dept: " + p.getDepartment() + " | Score: " + p.getScore());
            }
        }
    }
    
    private static void testSearchById() {
        System.out.print("Enter participant ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Participant p = dbService.getParticipantById(id);
        if (p != null) {
            System.out.println("Found: " + p);
        } else {
            System.out.println("No participant found with ID: " + id);
        }
    }
    
    private static void testUpdateScore() {
        System.out.print("Enter participant ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new score: ");
        int newScore = scanner.nextInt();
        scanner.nextLine();
        
        dbService.updateScore(id, newScore);
    }
    
    private static void testDeleteParticipant() {
        System.out.print("Enter participant ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            dbService.deleteParticipant(id);
        }
    }
    
    private static void testStatistics() {
        int count = dbService.getParticipantCount();
        int highest = dbService.getHighestScore();
        int lowest = dbService.getLowestScore();
        double average = dbService.getAverageScore();
        
        System.out.println("\n===== Quiz Statistics =====");
        System.out.println("Total Participants: " + count);
        System.out.println("Highest Score: " + highest);
        System.out.println("Lowest Score: " + lowest);
        System.out.printf("Average Score: %.2f\n", average);
    }
}
