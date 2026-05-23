package service;

import model.Participant;
import java.util.Scanner;

public class QuizService {

    private DatabaseService dbService;
    private Scanner scanner;

    public QuizService() {
        this.dbService = new DatabaseService();
        this.scanner = new Scanner(System.in);
    }

    public void startQuiz() {
        System.out.println("\n=== QUIZ LEADERBOARD SYSTEM ===");
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your department: ");
        String department = scanner.nextLine();
        
        System.out.println("\n=== QUIZ START ===\n");
        
        int score = askQuestions();
        
        Participant participant = new Participant(name, department, score);
        boolean saved = dbService.addParticipant(participant);
        
        System.out.println("\n=== QUIZ COMPLETED ===");
        System.out.println("Your score: " + score + "/50");
        
        if (saved) {
            System.out.println("✅ Participant saved successfully!");
        } else {
            System.out.println("❌ Failed to save participant.");
        }
    }
    
    private int askQuestions() {
        int totalScore = 0;
        
        for (int i = 0; i < QuizQuestions.QUESTIONS.length; i++) {
            System.out.println("Question " + (i+1) + ": " + QuizQuestions.QUESTIONS[i]);
            
            String[] options = QuizQuestions.OPTIONS[i];
            for (String option : options) {
                System.out.println(option);
            }
            
            System.out.print("Your answer (A, B, C, or D): ");
            String answerStr = scanner.nextLine().trim().toUpperCase();
            char userAnswer = answerStr.length() > 0 ? answerStr.charAt(0) : ' ';
            
            if (userAnswer == QuizQuestions.ANSWERS[i]) {
                totalScore += QuizQuestions.POINTS_PER_QUESTION;
                System.out.println("✅ Correct!\n");
            } else {
                System.out.println("❌ Wrong! The correct answer was " + QuizQuestions.ANSWERS[i] + "\n");
            }
        }
        
        return totalScore;
    }

    public static void main(String[] args) {
        QuizService qs = new QuizService();
        qs.startQuiz();
    }
}