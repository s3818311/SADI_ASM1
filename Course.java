public class Course {
    private String id;
    private String name;
    private int credits;

    public Course(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean equals(Course other) {
        return (this.id.equals(other.id) || this.id.equals(other.name));
    }

    public static Course parseCsvStr(String courseString) {
        String[] data = courseString.split(",");

        return new Course(data[0], data[1], Integer.valueOf(data[2]));
    }

    @Override
    public String toString() {
        return String.format("Course id: %s\nCourse name: %s\nNumber of credits: %d\n", id, name, credits);
    }

    public Course clone() {
        return new Course(this.id, this.name, this.credits);
    }

}
