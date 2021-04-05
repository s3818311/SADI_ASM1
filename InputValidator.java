import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private Scanner scanner = Main.scanner;
    private Pattern studentIdPattern = Pattern.compile("^\\d{7}$");
    private Pattern courseIdPattern = Pattern.compile("^[A-Z]{4}\\d{4}$");
    private Pattern semesterPattern = Pattern.compile("^\\d{4}[ABC]$");

    private InputValidator() {
    };

    private static InputValidator INSTANCE = null;

    public static InputValidator getInstance() {
        if (INSTANCE == null)
            INSTANCE = new InputValidator();

        return INSTANCE;
    }

    public String getValidatedStudentId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String sid = scanner.next();
            if (sid.length() == 8 && sid.startsWith("s") && studentIdPattern.matcher(sid.substring(1)).matches()) {
                return sid;
            } else {
                System.out.println("Please enter the student ID in the correct format.");
            }
        }
    }

    public String getValidatedCourseId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String cid = scanner.next();
            if (cid.length() == 8 && courseIdPattern.matcher(cid).matches()) {
                return cid;
            } else {
                System.out.println("Please enter the course ID in the correct format.");
            }
        }
    }

    public String getValidatedSemester(String prompt) {
        while (true) {
            System.out.print(prompt);
            String semester = scanner.next();
            if (semester.length() == 5 && semesterPattern.matcher(semester).matches()) {
                return semester;
            } else {
                System.out.println("Please enter the semester in the correct format.");
            }
        }
    }

    public int getValidatedIntChoice(int max) {
        int inp = 0;
        try {
            inp = scanner.nextInt();
            if (inp < 1 || inp > max)
                throw new InputMismatchException();
        } catch (InputMismatchException e) {
            System.out.printf("Please enter a number between 1 and %d\n", max);
            scanner.next();
            return 0;
        }

        return inp;
    }

}
