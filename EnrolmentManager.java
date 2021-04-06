import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnrolmentManager implements StudentEnrolmentManager {
    private List<StudentEnrolment> studentEnrolments = new ArrayList<>();
    private List<Course> coursesList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();

    private EnrolmentManager() {
    };

    private static EnrolmentManager INSTANCE = null;

    public static EnrolmentManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new EnrolmentManager();

        return INSTANCE;
    }

    public boolean populateFromFiles(String enrolmentFileName, String courseFileName, String studentFileName) {
        Scanner enrolmentScanner;
        Scanner courseScanner;
        Scanner studentScanner;
        try {
            enrolmentScanner = new Scanner(new File(enrolmentFileName));
            courseScanner = new Scanner(new File(courseFileName));
            studentScanner = new Scanner(new File(studentFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Database file does not exist or cannot be found.");
            return false;
        }

        populateEnrolment(enrolmentScanner);
        populateCourse(courseScanner);
        try {
            populateStudent(studentScanner);
        } catch (ParseException ex) {
            System.out.println("Error while parsing student database.");
            return false;
        }

        enrolmentScanner.close();
        courseScanner.close();
        studentScanner.close();

        System.out.println("Database was successfully imported.");

        return true;
    }

    public void populateEnrolment(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            StudentEnrolment enrolment = StudentEnrolment.parseCsvStr(fileScanner.nextLine());
            studentEnrolments.add(enrolment);
        }
    }

    public void populateCourse(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            Course course = Course.parseCsvStr(fileScanner.nextLine());
            coursesList.add(course);
        }
    }

    public void populateStudent(Scanner fileScanner) throws ParseException {
        while (fileScanner.hasNextLine()) {
            Student enrolment = Student.parseCsvStr(fileScanner.nextLine());
            studentList.add(enrolment);
        }
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
