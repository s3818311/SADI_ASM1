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
        return (this.id.equals(other.id) || this.id.equals(other.name));
    }

    public static Student parseCsvStr(String studentString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String[] data = studentString.split(",");
        Date dob = dateFormat.parse(data[2]);

        return new Student(data[0], data[1], dob);
    }

    public Student clone() {
        return new Student(this.id, this.name, this.dob);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("|- Student id: %s\n|- Student name: %s\n|- Student date of birth: %s\n", id, name,
                dateFormat.format(dob));
    }
}
