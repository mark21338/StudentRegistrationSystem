import java.util.ArrayList;
import java.util.List;

// Simple student registration system.
// Stores full student records, validates input and rejects duplicates.

public class StudentRegistration {

    private static final List<Student> students = new ArrayList<>();

    // Registers a new student.
    // Returns true if added, false if it is a duplicate.
    // Throws IllegalArgumentException if the name, age or grade is invalid.

    public static boolean addStudent(String name, int age, double grade) {
        Student newStudent = new Student(name, age, grade); // validates input

        for (Student existing : students) {
            if (existing.isSameStudentAs(newStudent)) {
                return false;
            }
        }

        students.add(newStudent);
        return true;
    }

    // Tries to add a student and reports the result to the user

    private static void tryAddStudent(String name, int age, double grade) {
        try {
            if (addStudent(name, age, grade)) {
                System.out.println("Student Added!");
                System.out.println("Name: " + name.trim());
                System.out.println("Age: " + age);
                System.out.println("Grade: " + grade);
            } else {
                System.out.println("Duplicate student, not added: " + name.trim());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add student: " + e.getMessage());
        }
        System.out.println();
    }

    // Prints all registered students
    
    public static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        System.out.println("Registered Students:");
        int number = 1;
        for (Student student : students) {
            System.out.println(number + ". " + student);
            number++;
        }
    }

    public static void main(String[] args) {
        tryAddStudent("", -5, 150);    // invalid: rejected
        tryAddStudent("John", 20, 85); // valid: added
        tryAddStudent("John", 20, 85); // duplicate: rejected

        displayStudents();
    }
}