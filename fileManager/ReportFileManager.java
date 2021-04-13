package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportFileManager extends FileManager {
    List<String> list;

    public ReportFileManager(String fileName) throws IOException {
        super(fileName);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void dumpToFile() throws IOException {
        File newFile = new File(fileName + ".tmp");

        List<String> data = new ArrayList<>();

        for (String s : list) {
            data.add(s + ",\n");
        }

        FileWriter fileWriter = new FileWriter(newFile);
        for (String s : data)
            fileWriter.append(s);
        fileWriter.close();

        if (!(file.delete() && newFile.renameTo(file)))
            throw new IOException("Enrolment file deletion or renaming failed");

    }

}
