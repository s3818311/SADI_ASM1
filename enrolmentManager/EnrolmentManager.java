package enrolmentManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import fileManager.CourseFileManager;
import fileManager.EnrolmentFileManager;
import fileManager.StudentFileManager;
import object.Course;
import object.Student;
import object.StudentEnrolment;

public class EnrolmentManager implements StudentEnrolmentManager {
    private Set<StudentEnrolment> studentEnrolments = new LinkedHashSet<>();
    private Set<Course> coursesList = new LinkedHashSet<>();
    private Set<Student> studentsList = new LinkedHashSet<>();

    EnrolmentFileManager enrolmentFileManager;
    StudentFileManager studentFileManager;
    CourseFileManager courseFileManager;

    private EnrolmentManager() {
    };

    private static EnrolmentManager INSTANCE = null;

    public static EnrolmentManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new EnrolmentManager();

        return INSTANCE;
    }

    public static EnrolmentManager getTestInstance() {
        return new EnrolmentManager();
    }

    @Override
    public boolean add(StudentEnrolment studentEnrolment) {
        return studentEnrolments.add(studentEnrolment);
    }

    @Override
    public void update(int opt, String sname, String cname, String semester) {
        StudentEnrolment enrolment = getOne(sname, cname, semester);
        if (opt == 1) {
            if (enrolment == null)
                add(new StudentEnrolment(sname, cname, semester));
            else
                System.out.println("Course already exist in the list.");
        } else if (opt == 2) {
            if (enrolment == null)
                System.out.println("Course does not exist in the list.");
            else
                studentEnrolments.remove(enrolment);
        }
    }

    @Override
    public void delete(int index) {
        Iterator<StudentEnrolment> iter = studentEnrolments.iterator();
        for (int i = 0; i <= index; i++) {
            iter.next();
        }
        iter.remove();
    }

    @Override
    public StudentEnrolment getOne(String sname, String cname, String semester) {
        StudentEnrolment temp = new StudentEnrolment(sname, cname, semester);
        for (StudentEnrolment enrolment : studentEnrolments) {
            if (enrolment.equals(temp)) {
                return enrolment.clone();
            }
        }

        return null;
    }

    @Override
    public List<StudentEnrolment> getAll() {
        List<StudentEnrolment> copy = new ArrayList<>();
        for (StudentEnrolment enrolment : studentEnrolments)
            copy.add(enrolment.clone());

        return copy;
    }

    public boolean populateFromFiles(String enrolmentFileName, String courseFileName, String studentFileName) {
        try {
            enrolmentFileManager = new EnrolmentFileManager(enrolmentFileName);
            studentFileManager = new StudentFileManager(studentFileName);
            courseFileManager = new CourseFileManager(courseFileName);
        } catch (IOException e) {
            System.out.println("Database file does not exist or cannot be found.");
            return false;
        }

        Scanner enrolmentScanner = enrolmentFileManager.getFileScanner();
        Scanner courseScanner = courseFileManager.getFileScanner();
        Scanner studentScanner = studentFileManager.getFileScanner();

        populateEnrolment(enrolmentScanner);
        populateCourse(courseScanner);
        try {
            populateStudent(studentScanner);
        } catch (ParseException ex) {
            System.out.println("Error while parsing student database.");
            return false;
        }

        enrolmentFileManager.setEnrolments(studentEnrolments);
        courseFileManager.setCourses(coursesList);
        studentFileManager.setStudents(studentsList);

        enrolmentFileManager.closeScanner();
        courseFileManager.closeScanner();
        studentFileManager.closeScanner();

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

    public void finalize() throws IOException {
        enrolmentFileManager.dumpToFile();
    }

    public List<String> getCoursesPerStudentPerSemester(String name, String semester) {
        ArrayList<String> list = new ArrayList<>();
        for (StudentEnrolment enrolment : studentEnrolments) {
            if (enrolment.getStudentName().equals(name) && enrolment.getSemester().equals(semester))
                list.add(enrolment.getCourseName());
        }

        return list;
    }

    public void printCoursesPerStudentPerSemester(String name, String semester) {
        List<String> list = getCoursesPerStudentPerSemester(name, semester);

        if (list.isEmpty()) {
            System.out.printf("\nNo courses for %s in semester %s found.\n", name, semester);
            return;
        }

        System.out.printf("\n%s's courses in semester %s:\n", name, semester);
        for (String course : list)
            System.out.printf(" |- %s\n", course);
    }

    public List<String> getStudentsPerCoursePerSemester(String name, String semester) {
        ArrayList<String> list = new ArrayList<>();
        for (StudentEnrolment enrolment : studentEnrolments) {
            if (enrolment.getCourseName().equals(name) && enrolment.getSemester().equals(semester)) {
                list.add(enrolment.getStudentName());
            }
        }

        return list;
    }

    public void printStudentsPerCoursePerSemester(String name, String semester) {
        List<String> list = getStudentsPerCoursePerSemester(name, semester);

        if (list.isEmpty()) {
            System.out.printf("No students enrolled in %s for semester %s found.\n", name, semester);
            return;
        }

        System.out.printf("\nStudents enrolling in %s for semester %s:\n", name, semester);
        for (String student : list) {
            System.out.printf(" |- %s\n", student);
        }
    }

    public List<String> getCoursesOfferedPerSemester(String semester) {
        Set<String> temp = new HashSet<>();

        for (StudentEnrolment enrolment : studentEnrolments) {
            String cname = enrolment.getCourseName();
            if (enrolment.getSemester().equals(semester) && !temp.contains(cname)) {
                temp.add(cname);
            }
        }

        return new ArrayList<>(temp);
    }

    public void printCoursesOfferedPerSemester(String semester) {
        List<String> list = getCoursesOfferedPerSemester(semester);

        if (list.isEmpty()) {
            System.out.printf("No courses currently offered for semester %s\n", semester);
            return;
        }

        System.out.printf("\nCourses offered in semester %s:\n", semester);
        for (String course : list) {
            System.out.printf(" |- %s\n", course);
        }

    }

    public List<Student> getStudents() {
        List<Student> copy = new ArrayList<>();
        for (Student student : studentsList)
            copy.add(student.clone());

        return copy;
    }

    public List<Course> getCourses() {
        List<Course> copy = new ArrayList<>();
        for (Course course : coursesList)
            copy.add(course.clone());

        return copy;
    }

}
