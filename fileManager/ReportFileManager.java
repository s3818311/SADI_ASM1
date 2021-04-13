package fileManager;

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
        List<String> data = new ArrayList<>();

        for (String s : list) {
            data.add(s + ",\n");
        }

        FileWriter fileWriter = new FileWriter(file);
        for (String s : data)
            fileWriter.append(s);
        fileWriter.close();
    }
}
