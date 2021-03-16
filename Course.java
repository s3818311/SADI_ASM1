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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            Course other = (Course) obj;
            return (this.id.equals(other.id));
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Course id: %s\nCourse name: %s\nNumber of credits: %d\n", id, name, credits);
    }

}
