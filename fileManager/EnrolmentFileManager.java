package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;

import object.StudentEnrolment;

public class EnrolmentFileManager extends FileManager {
    private Set<StudentEnrolment> enrolments;

    public EnrolmentFileManager(String fileName) throws IOException {
        super(fileName);
    }

    public void setEnrolments(Set<StudentEnrolment> enrolments) {
        this.enrolments = enrolments;
    }

    @Override
    public void dumpToFile() throws IOException {
        File newFile = new File(fileName + ".tmp");

        ArrayList<String> data = new ArrayList<>();

        for (StudentEnrolment enrolment : enrolments) {
            StringJoiner s = new StringJoiner(",");
            s.add(enrolment.getStudentName());
            s.add(enrolment.getCourseName());
            s.add(enrolment.getSemester());

            data.add(s.toString() + '\n');
        }

        FileWriter fileWriter = new FileWriter(newFile);
        for (String s : data)
            fileWriter.append(s);
        fileWriter.close();

        if (!(file.delete() && newFile.renameTo(file)))
            throw new IOException("Enrolment file deletion or renaming failed");

    }

}
