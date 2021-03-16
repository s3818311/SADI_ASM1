public class StudentEnrolment {
    private Student student;
    private Course course;
    private String semester;

    public StudentEnrolment(Student student, Course course, String semester) {
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudentEnrolment) {
            StudentEnrolment other = (StudentEnrolment) obj;
            return (this.student.equals(other.student) && this.course.equals(other.course)
                    && this.semester.equals(other.semester));
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Student id: %s\nCourse id: %s\nSemester: %s\n", student.getId(), course.getId(),
                semester);
    }

    public StudentEnrolment clone() {
        return new StudentEnrolment(this.student, this.course, this.semester);
    }

}
