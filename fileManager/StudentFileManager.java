package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;

import object.Student;

public class StudentFileManager extends FileManager {
    private Set<Student> students;

    public StudentFileManager(String fileName) throws IOException {
        super(fileName);
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public void dumpToFile() throws IOException {
        File newFile = new File(fileName + ".tmp");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        ArrayList<String> data = new ArrayList<>();

        for (Student student : students) {
            StringJoiner s = new StringJoiner(",");
            s.add(student.getId());
            s.add(student.getName());
            s.add(dateFormat.format(student.getDob()));

            data.add(s.toString() + '\n');
        }

        FileWriter fileWriter = new FileWriter(newFile);
        for (String s : data)
            fileWriter.append(s);
        fileWriter.close();

        if (!(file.delete() && newFile.renameTo(file)))
            throw new IOException("Student file deletion or renaming failed");

    }

}
