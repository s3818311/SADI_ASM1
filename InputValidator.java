import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private Scanner scanner = Main.scanner;
    private EnrolmentManager manager = EnrolmentManager.getInstance();
    private Pattern semesterPattern = Pattern.compile("^\\d{4}[ABC]$");

    private InputValidator() {
    };

    private static InputValidator INSTANCE = null;

    public static InputValidator getInstance() {
        if (INSTANCE == null)
            INSTANCE = new InputValidator();

        return INSTANCE;
    }

    public String getValidatedStudentName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine();
            boolean exist = false;
            for (Student student : manager.getStudents()) {
                if (student.getName().equals(name)) {
                    exist = true;
                    break;
                }
            }

            if (exist)
                return name;
            else
                System.out.printf("No student with the name '%s' found in the database.\n", name);
        }
    }

    public String getValidatedCourseName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine();
            boolean exist = false;
            for (Course course : manager.getCourses()) {
                if (course.getName().equals(name)) {
                    exist = true;
                    break;
                }
            }

            if (exist)
                return name;
            else
                System.out.printf("No course with the name '%s' found in the database.\n", name);
        }

    }

    public String getValidatedSemester(String prompt) {
        while (true) {
            System.out.print(prompt);
            String semester = scanner.nextLine();
            if (semester.length() == 5 && semesterPattern.matcher(semester).matches()) {
                return semester;
            } else {
                System.out.println("Please enter the semester in the correct format.");
            }
        }
    }

    public int getValidatedIntChoice(String prompt, int min, int max) {
        int inp = min - 1;
        while (inp < min || inp > max) {
            System.out.printf(prompt);
            try {
                inp = scanner.nextInt();
                if (inp < min || inp > max)
                    throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.printf("Please enter a number between 1 and %d\n", max);
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        return inp;
    }

    public int getValidatedIntChoice(String prompt, int max) {
        return getValidatedIntChoice(prompt, 1, max);
    }

}
