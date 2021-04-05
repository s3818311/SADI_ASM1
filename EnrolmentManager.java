import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnrolmentManager implements StudentEnrolmentManager {
    List<StudentEnrolment> studentEnrolments = new ArrayList<>();
    List<String> studentIdsList = new ArrayList<>();
    List<String> coursesIdsList = new ArrayList<>();

    private EnrolmentManager() {
    };

    private static EnrolmentManager INSTANCE = null;

    public static EnrolmentManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new EnrolmentManager();

        return INSTANCE;
    }

    public boolean populateFromFile(String fileName) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist or cannot be found.");
            return false;
        }
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            StudentEnrolment enrolment = StudentEnrolment.parseCsvStr(scanner.nextLine());
            studentEnrolments.add(enrolment);
            studentIdsList.add(enrolment.getStudentId());
            coursesIdsList.add(enrolment.getCourseId());
        }

        scanner.close();

        System.out.println("Database was successfully imported.");

        return true;
    }

    @Override
    public boolean add(StudentEnrolment studentEnrolment) {
        if (studentEnrolments.contains(studentEnrolment))
            return false;

        studentEnrolments.add(studentEnrolment);
        return true;

    }

    @Override
    public boolean update(StudentEnrolment studentEnrolment) {

        return false;
    }

    @Override
    public void delete(int index) {
        studentEnrolments.remove(index);
    }

    @Override
    public void getOne(int opt, String id, String semester) {
        if (opt == 1) {
            System.out.format("%s's courses in semester %s:\n", id, semester);
            for (StudentEnrolment enrolment : studentEnrolments)
                if (enrolment.getStudentId().equals(id) && enrolment.getSemester().equals(semester))
                    System.out.printf(" |- %s\n", enrolment.getCourseId());
        } else if (opt == 2) {
            System.out.format("Students enrolling in %s for semester %s:\n", id, semester);
            for (StudentEnrolment enrolment : studentEnrolments)
                if (enrolment.getCourseId().equals(id) && enrolment.getSemester().equals(semester))
                    System.out.printf(" |- %s\n", enrolment.getStudentId());
        } else {
            System.out.printf("Courses offered in semester %s:\n", semester);
            for (StudentEnrolment enrolment : studentEnrolments) {
                if (enrolment.getSemester().equals(semester))
                    System.out.printf(" |- %s\n", enrolment.getCourseId());
            }
        }

    }

    @Override
    public void getAll() {
        System.out.println("Student enrolments:");
        for (StudentEnrolment enrolment : studentEnrolments) {
            System.out.println("--------------------");
            System.out.println(enrolment);
        }

        System.out.println("\nStudents: ");
        for (String sid : studentIdsList) {
            System.out.println("|-" + sid);
        }

        System.out.println("\nCourses: ");
        for (String cid : coursesIdsList) {
            System.out.println("|-" + cid);
        }
    }

    protected void finalize() {

    }

    protected List<StudentEnrolment> getEnrolments() {
        List<StudentEnrolment> copy = new ArrayList<>();
        for (StudentEnrolment enrolment : studentEnrolments)
            copy.add(enrolment.clone());

        return copy;
    }

}
