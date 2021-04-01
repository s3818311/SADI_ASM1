public interface StudentEnrolmentManager {
    public boolean add(StudentEnrolment studentEnrolment);

    public boolean update(StudentEnrolment studentEnrolment);

    public void delete(int index);

    public StudentEnrolment getOne(int opt, String id);

    public void getAll();
}
