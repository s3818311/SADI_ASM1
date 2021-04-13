package enrolmentManager;

import java.util.List;

import object.StudentEnrolment;

public interface StudentEnrolmentManager {
    public boolean add(StudentEnrolment studentEnrolment);

    public void update(int opt, String sname, String cname, String semester);

    public void delete(int index);

    public StudentEnrolment getOne(String sname, String cname, String semester);

    public List<StudentEnrolment> getAll();
}
