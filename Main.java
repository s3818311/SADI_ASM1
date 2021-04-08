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
        System.out.print("> ");
    }

    public static void printMainMenu() {
        System.out.println("\n------MAIN MENU------");
        System.out.println("1 List enrolments");
        System.out.println("2 Add enrolment");
        System.out.println("3 Update enrolment");
        System.out.println("4 Delete enrolment");
        System.out.println("5 Get all courses from a student in a semester");
        System.out.println("6 Get all students from a course in a semester");
        System.out.println("7 Get all courses offered in a semester");
        System.out.println("8 Quit");
        System.out.print("> ");
    };

    public static void main(String[] args) {
        printFileMenu();
        int inp = validator.getValidatedIntChoice(2);

        boolean run = true;

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

            int inp2 = validator.getValidatedIntChoice(8);
            String sid, cid, semester;

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
                sid = validator.getValidatedStudentName("Please enter the student name: ");
                semester = validator.getValidatedSemester("Please enter the semester: ");
                manager.printCoursesPerStudentPerSemester(sid, semester);
                break;
            case 6:
                cid = validator.getValidatedCourseName("Please enter the course name: ");
                semester = validator.getValidatedSemester("Please enter the semester: ");
                manager.printStudentsPerCoursePerSemester(cid, semester);
                break;
            case 7:
                semester = validator.getValidatedSemester("Please enter the semester: ");
                manager.printCoursesOfferedPerSemester(semester);
                break;
            case 8:
                run = false;
            }
        }

        manager.finalize();
        scanner.close();

        System.out.println("Ending program...");
    }

    public static void listEnrolments() {
        System.out.println();
        List<StudentEnrolment> enrolments = manager.getAll();
        for (StudentEnrolment studentEnrolment : enrolments) {
            System.out.println("------------------------------");
            System.out.println(studentEnrolment);
        }
    }

    public static void addEnrolment() {
        System.out.println("\n------ADD ENROLMENT------");
        String sname = validator.getValidatedStudentName("Student name: ");
        String cname = validator.getValidatedCourseName("Course name: ");
        String semester = validator.getValidatedSemester("Semester: ");

        if (manager.add(new StudentEnrolment(sname, cname, semester)))
            System.out.println(" - Enrolment added successfully");
        else
            System.out.println(" - Enrolment of the same info already exists. Enrolment not added.");
    }

    public static void updateEnrolment() {
        System.out.println("\n------UPDATE ENROLMENT------");
        String sid = validator.getValidatedStudentName("Please enter the student name you want to update: ");
        String semester = validator.getValidatedSemester("Please enter the semester you want to update: ");

        while (true) {
            manager.printCoursesPerStudentPerSemester(sid, semester);

            System.out.printf("1 Add a course to this list\n2 Delete a course from this list\n3 Return\n> ");

            int opt = validator.getValidatedIntChoice(3);

            if (opt == 3)
                return;

            String cid = validator.getValidatedCourseName("Course name: ");

            manager.update(opt, sid, cid, semester);
        }
    }

    public static void deleteEnrolment() {
        System.out.println("\n------DELETE ENROLMENT------");
        List<StudentEnrolment> enrolments = manager.getAll();
        int sz = enrolments.size();

        for (int i = 0; i < sz; i++)
            System.out.println((i + 1) + "/\n" + enrolments.get(i));

        while (true) {
            System.out.print("Please enter the index of the enrolment you want to delete (0 to return): ");
            int inp = validator.getValidatedIntChoice(0, sz);
            if (inp == 0)
                return;

            manager.delete(inp - 1);
            System.out.println(" - Enrolment successfully removed");
        }

    }

    public static void printAllInfo() {
        System.out.println("\n------ALL INFO------\n");
        manager.getAll();
    }
}