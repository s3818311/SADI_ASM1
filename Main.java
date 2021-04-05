import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static EnrolmentManager manager = EnrolmentManager.getInstance();
    private static InputValidator validator = InputValidator.getInstance();

    public static void printMenu() {
        System.out.println("\n------MAIN MENU------");
        System.out.println("1 List enrolments");
        System.out.println("2 Add enrolment");
        System.out.println("3 Update enrolment");
        System.out.println("4 Delete enrolment");
        System.out.println("5 Get info");
        System.out.println("6 Print all information");
        System.out.println("7 Quit");
        System.out.print("> ");
    };

    public static void main(String[] args) {
        boolean run = true;
        if (args.length == 0)
            run = manager.populateFromFile("sampleEnrolments.csv");
        else
            run = manager.populateFromFile(args[0]);

        while (run) {
            printMenu();

            int inp = 0;

            try {
                inp = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a number between 1 and 3");
                scanner.next();
                continue;
            }

            if (inp < 1 || inp > 7) {
                System.out.println("Please enter a number between 1 and 7");
                continue;
            }

            switch (inp) {
            case 1:
                listEnrolments();
                break;
            case 2:
                addEnrolment();
                break;

            case 4:
                deleteEnrolment();
                break;
            case 5:
                getInfo();
                break;
            case 6:
                printAllInfo();
                break;
            case 7:
                run = false;
            }
        }

        manager.finalize();
        scanner.close();

        System.out.println("Ending program...");
    }

    public static void listEnrolments() {
        System.out.println();
        List<StudentEnrolment> enrolments = manager.getEnrolments();
        for (StudentEnrolment studentEnrolment : enrolments) {
            System.out.println(studentEnrolment);
        }
    }

    public static void addEnrolment() {
        System.out.println();
        String studentId = validator.getValidatedStudentId("Student Id: ");
        String courseId = validator.getValidatedCourseId("Course Id: ");
        String semester = validator.getValidatedSemester("Semester: ");

        if (manager.add(new StudentEnrolment(studentId, courseId, semester)))
            System.out.println(" - Enrolment added successfully");
        else
            System.out.println(" - Enrolment of the same info already exists. Enrolment not added");
    }

    public static void deleteEnrolment() {
        System.out.println();
        List<StudentEnrolment> enrolments = manager.getEnrolments();
        int sz = enrolments.size();
        int inp = -1;

        for (int i = 0; i < sz; i++)
            System.out.println((i + 1) + "/\n" + enrolments.get(i).toString());

        do {
            System.out.print("Please enter the index of the enrolment you want to delete (0 to return): ");
            try {
                inp = scanner.nextInt();
                if (inp == 0)
                    return;
            } catch (Exception ignored) {
                continue;
            }
        } while (inp < 0 || inp > sz);

        manager.delete(inp - 1);
        System.out.println(" - Enrolment successfully removed");
    }

    public static void printGetOneMenu() {
        System.out.println("\n-------GET INFO-------");
        System.out.println("1 Get all courses from a student in a semester");
        System.out.println("2 Get all students from a course in a semester");
        System.out.println("3 Get all courses offered in a semester");
        System.out.println("4 Back");
        System.out.print("> ");
    }

    public static void getInfo() {
        boolean run = true;

        while (run) {
            printGetOneMenu();

            int inp = 0;
            try {
                inp = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a number between 1 and 4");
                scanner.next();
                continue;
            }

            if (inp < 1 || inp > 4) {
                System.out.println("Please enter a number between 1 and 4");
                continue;
            }

            String semester = validator.getValidatedSemester("Please enter the semester: ");

            switch (inp) {
            case 1:
                String sid = validator.getValidatedStudentId("Please enter the student id: ");
                manager.getOne(inp, sid, semester);
                break;
            case 2:
                String cid = validator.getValidatedCourseId("Please enter the course id: ");
                manager.getOne(inp, cid, semester);
                break;
            case 3:
                manager.getOne(inp, null, semester);
            case 4:
                run = false;
            }
        }
    }

    public static void printAllInfo() {
        manager.getAll();
    }
}