import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EnrolmentManager implements StudentEnrolmentManager {
    private Set<StudentEnrolment> studentEnrolments = new LinkedHashSet<>();
    private Set<Course> coursesList = new LinkedHashSet<>();
    private Set<Student> studentsList = new LinkedHashSet<>();

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
            Student student = Student.parseCsvStr(fileScanner.nextLine());
            studentsList.add(student);
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
    public void update(int opt, String sid, String cid, String semester) {
        StudentEnrolment temp = new StudentEnrolment(sid, cid, semester);
        if (opt == 1) {
            if (!studentEnrolments.add(temp)) {
                System.out.println("Course already exist in the list.");
            }
        } else {
            for (StudentEnrolment enrolment : studentEnrolments)
                if (enrolment.equals(temp)) {
                    studentEnrolments.remove(enrolment);
                    return;
                }
            System.out.println("Course does not exist in the list.");
        }
    }

    @Override
    public void delete(int index) {
        studentEnrolments.remove(index);
    }

    @Override
    public StudentEnrolment getOne() {
        return null;
    }

    @Override
    public List<StudentEnrolment> getAll() {
        List<StudentEnrolment> copy = new ArrayList<>();
        for (StudentEnrolment enrolment : studentEnrolments)
            copy.add(enrolment.clone());

        return copy;
    }

    protected void finalize() {

    }

    protected void printCoursesPerStudentPerSemester(String name, String semester) {
        boolean empty = true;
        System.out.format("%s's courses in semester %s:\n", name, semester);
        for (StudentEnrolment enrolment : studentEnrolments)
            if (enrolment.getStudentName().equals(name) && enrolment.getSemester().equals(semester)) {
                System.out.printf(" |- %s\n", enrolment.getCourseName());
                empty = false;
            }

        if (empty) {
            System.out.printf("No courses for %s in semester %s found.\n", name, semester);
        }
    }

    protected void printStudentsPerCoursePerSemester(String name, String semester) {
        boolean empty = true;
        System.out.format("Students enrolling in %s for semester %s:\n", name, semester);
        for (StudentEnrolment enrolment : studentEnrolments)
            if (enrolment.getCourseName().equals(name) && enrolment.getSemester().equals(semester)) {
                System.out.printf(" |- %s\n", enrolment.getStudentName());
                empty = false;
            }

        if (empty) {
            System.out.printf("No students enrolled in %s for semester %s found.\n", name, semester);
        }
    }

    protected void printCoursesOfferedPerSemester(String semester) {
        Set<String> temp = new HashSet<>();
        System.out.printf("Courses offered in semester %s:\n", semester);
        for (StudentEnrolment enrolment : studentEnrolments) {
            String cname = enrolment.getCourseName();
            if (enrolment.getSemester().equals(semester) && !temp.contains(cname)) {
                System.out.printf(" |- %s\n", cname);
                temp.add(cname);
            }
        }

        if (temp.isEmpty()) {
            System.out.printf("No courses currently offered for semester %s\n", semester);
        }
    }

    protected List<Student> getStudents() {
        List<Student> copy = new ArrayList<>();
        for (Student student : studentsList)
            copy.add(student.clone());

        return copy;
    }

    protected List<Course> getCourses() {
        List<Course> copy = new ArrayList<>();
        for (Course course : coursesList)
            copy.add(course.clone());

        return copy;
    }

}
