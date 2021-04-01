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
    public StudentEnrolment getOne(int opt, String id) {
        if (opt == 1) {
            System.out.format("%s's courses: \n", id);
            for (StudentEnrolment enrolment : studentEnrolments)
                if (enrolment.getStudentId().equals(id))
                    System.out.println(" |-" + enrolment.getCourseId());
        } else {
            System.out.format("Students enrolling in %s\n", id);
            for (StudentEnrolment enrolment : studentEnrolments)
                if (enrolment.getCourseId().equals(id))
                    System.out.println(" |-" + enrolment.getStudentId());
        }

        return null;
    }

    @Override
    public void getAll() {

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
