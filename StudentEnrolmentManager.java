import java.util.List;

public interface StudentEnrolmentManager {
    public boolean add(StudentEnrolment studentEnrolment);

    public void update(int opt, String sid, String cid, String semester);

    public void delete(int index);

    public StudentEnrolment getOne();

    public List<StudentEnrolment> getAll();
}
