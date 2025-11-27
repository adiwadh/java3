import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    private Integer rollNumber;
    private String studentName;
    private Integer[] marks;

    public Student(Integer rollNumber, String studentName, Integer[] marks) throws InvalidMarksException {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
        validateMarks();
    }

    public void validateMarks() throws InvalidMarksException {
        if (marks == null || marks.length != 3) {
            throw new InvalidMarksException("Missing data: Marks array is invalid.");
        }
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] == null) {
                throw new InvalidMarksException("Null value: Mark for subject " + (i + 1) + " is null.");
            }
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    public double calculateAverage() {
        if (marks == null || marks.length == 0) {
            return 0.0;
        }
        int sum = 0;
        for (Integer mark : marks) {
            sum += mark;
        }
        return (double) sum / marks.length;
    }

    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (Integer mark : marks) {
            System.out.print(mark + " ");
        }
        System.out.println();
        System.out.println("Average: " + calculateAverage());
        System.out.println("Result: " + (calculateAverage() >= 40 ? "Pass" : "Fail"));
    }

    public Integer getRollNumber() {
        return rollNumber;
    }
}

class ResultManager {
    private List<Student> students = new ArrayList<>();
    private Scanner scanner;

    public ResultManager() {
        this.scanner = new Scanner(System.in);
        // Pre-populate with 10 sample students
        try {
            students.add(new Student(101, "Alice", new Integer[]{85, 92, 88}));
            students.add(new Student(102, "Bob", new Integer[]{70, 65, 80}));
            students.add(new Student(103, "Charlie", new Integer[]{45, 50, 35})); // Fail
            students.add(new Student(104, "David", new Integer[]{90, 89, 91}));
            students.add(new Student(105, "Eve", new Integer[]{60, 70, 65}));
            students.add(new Student(106, "Frank", new Integer[]{75, 78, 72}));
            students.add(new Student(107, "Grace", new Integer[]{95, 98, 93}));
            students.add(new Student(108, "Henry", new Integer[]{30, 40, 42})); // Fail
            students.add(new Student(109, "Ivy", new Integer[]{82, 85, 80}));
            students.add(new Student(110, "Jack", new Integer[]{55, 60, 58}));
            System.out.println("10 sample students added for demonstration.");
        } catch (InvalidMarksException e) {
            System.out.println("Pre-population error: " + e.getMessage());
        }
    }

    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            Integer rollNumber = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Student Name: ");
            String studentName = scanner.nextLine();
            Integer[] marks = new Integer[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = Integer.parseInt(scanner.nextLine());
            }

            Student newStudent = new Student(rollNumber, studentName, marks);
            students.add(newStudent);
            System.out.println("Student added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter valid numbers for Roll Number and marks.");
        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            System.out.println("Returning to main menu...");
        }
    }

    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            Integer searchRollNumber = Integer.parseInt(scanner.nextLine());
            Student foundStudent = null;
            for (Student student : students) {
                if (student.getRollNumber().equals(searchRollNumber)) {
                    foundStudent = student;
                    break;
                }
            }

            if (foundStudent != null) {
                foundStudent.displayResult();
            } else {
                System.out.println("Error: Student with Roll Number " + searchRollNumber + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a valid number for the Roll Number.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            System.out.println("Search completed.");
        }
    }

    public void mainMenu() {
        int choice = 0;
        while (choice != 3) {
            System.out.println("===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        showStudentDetails();
                        break;
                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        break;
                    default:
                        System.out.println("Error: Invalid choice. Please select 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid menu number.");
                choice = 0; // Reset choice to stay in the loop
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing scanner: " + e.getMessage());
        }
    }
}

public class StudentResultManagementSystem {
    public static void main(String[] args) {
        ResultManager manager = new ResultManager();
        manager.mainMenu();
    }
}
