package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;

import object.Course;

public class CourseFileManager extends FileManager {
    private Set<Course> courses;

    public CourseFileManager(String fileName) throws IOException {
        super(fileName);
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public void dumpToFile() throws IOException {
        File newFile = new File(fileName + ".tmp");

        ArrayList<String> data = new ArrayList<>();

        for (Course course : courses) {
            StringJoiner s = new StringJoiner(",");
            s.add(course.getId());
            s.add(course.getName());
            s.add(Integer.toString(course.getCredits()));

            data.add(s.toString() + '\n');
        }

        FileWriter fileWriter = new FileWriter(newFile);
        for (String s : data)
            fileWriter.append(s);
        fileWriter.close();

        if (!(file.delete() && newFile.renameTo(file)))
            throw new IOException("Course file deletion or renaming failed");

    }

}
