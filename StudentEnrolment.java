public class StudentEnrolment {
    private String studentId;
    private String courseId;
    private String semester;

    public StudentEnrolment() {
    }

    public StudentEnrolment(String studentId, String courseId, String semester) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSemester() {
        return semester;
    }

    public static StudentEnrolment parseCsvStr(String studString) {
        String[] data = studString.split(",");

        return new StudentEnrolment(data[0], data[1], data[2]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudentEnrolment) {
            StudentEnrolment other = (StudentEnrolment) obj;
            return (this.studentId.equals(other.studentId) && this.courseId.equals(other.courseId)
                    && this.semester.equals(other.semester));
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Student id: %s\nCourse id: %s\nSemester: %s\n", studentId, courseId, semester);
    }

    public StudentEnrolment clone() {
        return new StudentEnrolment(this.studentId, this.courseId, this.semester);
    }
}