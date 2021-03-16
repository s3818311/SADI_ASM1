import java.util.List;

public interface StudentEnrolmentManager {
    public boolean add(StudentEnrolment studentEnrolment);

    public boolean update(StudentEnrolment studentEnrolment);

    public boolean delete(int index);

    public StudentEnrolment getOne();

    public List<StudentEnrolment> getAll();
}
