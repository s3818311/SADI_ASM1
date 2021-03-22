import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private String id;
    private String name;
    private Date dob;

    public Student(String id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public boolean equals(Student other) {
        return (this.id.equals(other.id));
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Student id: %s\nStudent name: %s\nStudent date of birth: %s\n", id, name,
                dateFormat.format(dob));
    }

}
