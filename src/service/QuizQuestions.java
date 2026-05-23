package service;

public class QuizQuestions {
    // Array of questions
    public static final String[] QUESTIONS = {
        "What is the capital of France?",
        "Which programming language is used for Android development?",
        "What is the largest ocean on Earth?",
        "Who wrote 'Romeo and Juliet'?",
        "What is the chemical symbol for Gold?"
    };
    
    // Array of options for each question (each is an array of 4 strings)
    public static final String[][] OPTIONS = {
        {"A. London", "B. Berlin", "C. Paris", "D. Madrid"},
        {"A. Python", "B. Java", "C. C++", "D. JavaScript"},
        {"A. Atlantic Ocean", "B. Indian Ocean", "C. Arctic Ocean", "D. Pacific Ocean"},
        {"A. Charles Dickens", "B. Jane Austen", "C. William Shakespeare", "D. Mark Twain"},
        {"A. Go", "B. Au", "C. Ag", "D. Fe"}
    };
    
    // Correct answers (A, B, C, or D)
    public static final char[] ANSWERS = {'C', 'B', 'D', 'C', 'B'};
    
    // Points per correct answer
    public static final int POINTS_PER_QUESTION = 10;
}