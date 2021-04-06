import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static EnrolmentManager manager = EnrolmentManager.getInstance();
    private static InputValidator validator = InputValidator.getInstance();

    public static void printFileMenu() {
        System.out.println("\n------DATABASE INPUT------");
        System.out.println("1 Import custom database");
        System.out.println("2 Use default sample database");
    }

    public static void printMainMenu() {
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

    public static void main() {
        boolean run = true;
        int inp = validator.getValidatedIntChoice(2);

        if (inp == 1) {
            System.out.print("> Enrolment file name: ");
            String enrolmentFileName = scanner.nextLine();
            System.out.print("> Course file name: ");
            String courseFileName = scanner.nextLine();
            System.out.print("> Student file name: ");
            String studentFileName = scanner.nextLine();
            run = manager.populateFromFiles(enrolmentFileName, courseFileName, studentFileName);
        } else {
            run = manager.populateFromFiles("sampleEnrolments.csv", "sampleCourses.csv", "sampleStudents.csv");
        }

        while (run) {
            printMainMenu();

            int inp2 = validator.getValidatedIntChoice(7);

            switch (inp2) {
            case 1:
                listEnrolments();
                break;
            case 2:
                addEnrolment();
                break;
            case 3:
                updateEnrolment();
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
        System.out.println("\n------ADD ENROLMENT------");
        String sid = validator.getValidatedStudentId("Student Id: ");
        String cid = validator.getValidatedCourseId("Course Id: ");
        String semester = validator.getValidatedSemester("Semester: ");

        if (manager.add(new StudentEnrolment(sid, cid, semester)))
            System.out.println(" - Enrolment added successfully");
        else
            System.out.println(" - Enrolment of the same info already exists. Enrolment not added");
    }

    public static void deleteEnrolment() {
        System.out.println("\n------DELETE ENROLMENT------");
        List<StudentEnrolment> enrolments = manager.getEnrolments();
        int sz = enrolments.size();

        for (int i = 0; i < sz; i++)
            System.out.println((i + 1) + "/\n" + enrolments.get(i).toString());

        while (true) {
            System.out.print("Please enter the index of the enrolment you want to delete (0 to return): ");
            int inp = validator.getValidatedIntChoice(0, sz);
            if (inp == 0)
                return;

            manager.delete(inp - 1);
            System.out.println(" - Enrolment successfully removed");
        }

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

            int inp = validator.getValidatedIntChoice(4);

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
        System.out.println("\n------ALL INFO------");
        manager.getAll();
    }
}