import java.util.ArrayList;
import java.util.List;

public class Manager implements StudentEnrolmentManager {
    List<StudentEnrolment> studentEnrolments = new ArrayList<>();

    private Manager() {
    };

    private static Manager INSTANCE = null;

    public static Manager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Manager();

        return INSTANCE;
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
    public boolean delete(int index) {

        return false;
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

}
