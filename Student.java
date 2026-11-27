// Represents a registered student.
// Validates its own data so an invalid Student can never be created.

public class Student {

    public static final int MIN_AGE = 1;
    public static final int MAX_AGE = 120;
    public static final double MIN_GRADE = 0.0;
    public static final double MAX_GRADE = 100.0;
    public static final int MAX_NAME_LENGTH = 50;

    private final String name;
    private final int age;
    private final double grade;

    // Throws IllegalArgumentException if the name, age or grade is invalid

    public Student(String name, int age, double grade) {
        this.name = validateName(name);
        this.age = validateAge(age);
        this.grade = validateGrade(grade);
    }

    private static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty.");
        }
        String trimmed = name.trim();
        if (trimmed.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Name must not exceed " + MAX_NAME_LENGTH + " characters.");
        }
        if (!trimmed.matches("[A-Za-z][A-Za-z .'-]*")) {
            throw new IllegalArgumentException(
                    "Name may only contain letters, spaces, periods, hyphens and apostrophes.");
        }
        return trimmed;
    }

    private static int validateAge(int age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException(
                    "Age must be between " + MIN_AGE + " and " + MAX_AGE + ".");
        }
        return age;
    }

    private static double validateGrade(double grade) {
        if (Double.isNaN(grade) || grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new IllegalArgumentException(
                    "Grade must be between " + MIN_GRADE + " and " + MAX_GRADE + ".");
        }
        return grade;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGrade() {
        return grade;
    }

    // Two students are duplicates if the name (ignoring case) and age match
    
    public boolean isSameStudentAs(Student other) {
        return other != null
                && name.equalsIgnoreCase(other.name)
                && age == other.age;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}