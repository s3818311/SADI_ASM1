public interface StudentEnrolmentManager {
    public boolean add(StudentEnrolment studentEnrolment);

    public boolean update(StudentEnrolment studentEnrolment);

    public void delete(int index);

    public void getOne(int opt, String id, String semester);

    public void getAll();
}
