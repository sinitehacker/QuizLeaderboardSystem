package model;

/**
 * Participant model class - Represents a quiz participant
 * This is a Plain Old Java Object (POJO)
 * 
 * @author Sindhuja
 */
public class Participant {
    
    // Private fields (encapsulation)
    private int id;
    private String name;
    private String department;
    private int score;
    
    // Default constructor (no parameters)
    public Participant() {
    }
    
    // Parameterized constructor (with all fields except id)
    public Participant(String name, String department, int score) {
        this.name = name;
        this.department = department;
        this.score = score;
    }
    
    // Parameterized constructor (with all fields)
    public Participant(int id, String name, String department, int score) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.score = score;
    }
    
    // Getters (to access private fields)
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public int getScore() {
        return score;
    }
    
    // Setters (to modify private fields)
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    // toString method - displays participant information
    @Override
    public String toString() {
        return "Participant [id=" + id + ", name=" + name + 
               ", department=" + department + ", score=" + score + "]";
    }
}
