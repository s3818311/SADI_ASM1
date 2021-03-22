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
        return (this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return String.format("Course id: %s\nCourse name: %s\nNumber of credits: %d\n", id, name, credits);
    }

}
