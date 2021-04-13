package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import enrolmentManager.EnrolmentManager;
import fileManager.ReportFileManager;
import helper.InputValidator;
import object.Course;
import object.Student;
import object.StudentEnrolment;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static EnrolmentManager manager = EnrolmentManager.getInstance();
    private static InputValidator validator = InputValidator.getInstance();

    public static void main(String[] args) throws IOException {
        boolean run = dbSelect();

        String mainMenu = "\n------MAIN MENU------" + "\n1 List enrolments" + "\n2 Add enrolment"
                + "\n3 Update enrolment" + "\n4 Delete enrolment" + "\n5 Get all courses from a student in a semester"
                + "\n6 Get all students from a course in a semester" + "\n7 Get all courses offered in a semester"
                + "\n8 Print all students information" + "\n9 Print all courses information" + "\n10 Quit" + "\n> ";

        while (run) {
            int inp = validator.getValidatedIntChoice(mainMenu, 10);

            switch (inp) {
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
                coursesPerStudentPerSemester();
                break;
            case 6:
                studentsPerCoursePerSemester();
                break;
            case 7:
                coursesOfferedPerSemester();
                break;
            case 8:
                printAllStudents();
                break;
            case 9:
                printAllCourses();
                break;
            case 10:
                run = false;
            }
        }

        try {
            manager.finalize();
        } catch (IOException ex) {
            System.out.println("An exception occurred: " + ex.getMessage());
            System.out.println("Changes made have not been saved!");
        }
        scanner.close();

        System.out.println("Ending program...");
    }

    public static boolean dbSelect() {
        boolean run = false;
        String fileMenu = "\n------DATABASE INPUT------" + "\n1 Import custom database"
                + "\n2 Use default sample database" + "\n> ";

        int inp = validator.getValidatedIntChoice(fileMenu, 2);

        if (inp == 1) {
            System.out.print("> Enrolment file name: ");
            String enrolmentFileName = scanner.nextLine();
            System.out.print("> Course file name: ");
            String courseFileName = scanner.nextLine();
            System.out.print("> Student file name: ");
            String studentFileName = scanner.nextLine();
            run = manager.populateFromFiles(enrolmentFileName, courseFileName, studentFileName);
        } else if (inp == 2) {
            run = manager.populateFromFiles("sampleEnrolments.csv", "sampleCourses.csv", "sampleStudents.csv");
        }

        return run;
    }

    public static void listEnrolments() {
        System.out.println();
        List<StudentEnrolment> enrolments = manager.getAll();
        for (StudentEnrolment studentEnrolment : enrolments) {
            System.out.println("------------------------------");
            System.out.println(studentEnrolment);
        }
        System.out.println("------------------------------");
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

            int opt = validator.getValidatedIntChoice(
                    "\n1 Add a course to this list\n2 Delete a course from this list\n3 Return\n> ", 3);

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
        String menu = "Please enter the index of the enrolment you want to delete (0 to return): ";

        for (int i = 0; i < sz; i++)
            System.out.println((i + 1) + "/\n" + enrolments.get(i));

        while (true) {
            int inp = validator.getValidatedIntChoice(menu, 0, sz);
            if (inp == 0)
                return;

            manager.delete(inp - 1);
            System.out.println(" - Enrolment successfully removed");
        }
    }

    public static void coursesPerStudentPerSemester() throws IOException {
        String name = validator.getValidatedStudentName("Please enter the student name: ");
        String semester = validator.getValidatedSemester("Please enter the semester: ");

        manager.printCoursesPerStudentPerSemester(name, semester);

        if (validator.getValidateYesNo("Do you want to save this report? (Y/N) > ")) {
            ReportFileManager fileManager = null;
            try {
                fileManager = new ReportFileManager(String.format("Courses for %s in semester %s.csv", name, semester));
                fileManager.setList(manager.getCoursesPerStudentPerSemester(name, semester));
                fileManager.dumpToFile();
            } catch (FileNotFoundException e) {
                fileManager.createFile();
            } catch (IOException e) {
                System.out.println("An error occurred while saving report. Report not saved!");
            }
        }

    }

    public static void studentsPerCoursePerSemester() throws IOException {
        String name = validator.getValidatedCourseName("Please enter the course name: ");
        String semester = validator.getValidatedSemester("Please enter the semester: ");

        manager.printStudentsPerCoursePerSemester(name, semester);

        if (validator.getValidateYesNo("Do you want to save this report? (Y/N) > ")) {
            ReportFileManager fileManager = null;
            try {
                fileManager = new ReportFileManager(String.format("Students in %s in semester %s.csv", name, semester));
                fileManager.setList(manager.getStudentsPerCoursePerSemester(name, semester));
                fileManager.dumpToFile();
            } catch (FileNotFoundException e) {
                fileManager.createFile();
            } catch (IOException e) {
                System.out.println("An error occurred while saving report. Report not saved!");
            }
        }
    }

    public static void coursesOfferedPerSemester() throws IOException {
        String semester = validator.getValidatedSemester("Please enter the semester: ");

        manager.printCoursesOfferedPerSemester(semester);

        if (validator.getValidateYesNo("Do you want to save this report? (Y/N) > ")) {
            ReportFileManager fileManager = null;
            try {
                fileManager = new ReportFileManager(String.format("Courses offered in semester %s.csv", semester));
                fileManager.setList(manager.getCoursesOfferedPerSemester(semester));
                fileManager.dumpToFile();
            } catch (FileNotFoundException e) {
                fileManager.createFile();
            } catch (IOException e) {
                System.out.println("An error occurred while saving report. Report not saved!");
            }
        }

    }

    public static void printAllStudents() {
        System.out.println("\n------STUDENTS INFO------");
        List<Student> students = manager.getStudents();

        for (Student student : students) {
            System.out.println("\n------------------------------");
            System.out.print(student);
            System.out.println("------------------------------");
        }
    }

    public static void printAllCourses() {
        System.out.println("\n------COURSES INFO------");
        List<Course> courses = manager.getCourses();

        for (Course course : courses) {
            System.out.println("\n------------------------------");
            System.out.print(course);
            System.out.println("------------------------------");
        }

    }
}