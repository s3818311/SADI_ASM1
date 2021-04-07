public class StudentEnrolment {
    private String studentName;
    private String courseName;
    private String semester;

    public StudentEnrolment() {
    }

    public StudentEnrolment(String studentName, String courseName, String semester) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.semester = semester;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSemester() {
        return semester;
    }

    public static StudentEnrolment parseCsvStr(String enrolString) {
        String[] data = enrolString.split(",");

        return new StudentEnrolment(data[0], data[1], data[2]);
    }

    public StudentEnrolment clone() {
        return new StudentEnrolment(this.studentName, this.courseName, this.semester);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudentEnrolment) {
            StudentEnrolment other = (StudentEnrolment) obj;
            return (this.studentName.equals(other.studentName) && this.courseName.equals(other.courseName)
                    && this.semester.equals(other.semester));
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Student name: %s\nCourse name: %s\nSemester: %s", studentName, courseName, semester);
    }
}