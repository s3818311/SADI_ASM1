package fileManager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public abstract class FileManager {
    protected final String fileName;
    protected final File file;
    protected final Scanner fileScanner;

    public FileManager(String fileName) throws IOException {
        String absolutePath = new File("").getAbsolutePath();
        this.fileName = absolutePath + "/" + fileName;

        file = new File(this.fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        fileScanner = new Scanner(file);
    }

    public String getFileName() {
        return fileName;
    }

    public Scanner getFileScanner() {
        return fileScanner;
    }

    public void createFile() throws IOException {
        file.createNewFile();
    }

    public void closeScanner() {
        fileScanner.close();
    }

    public abstract void dumpToFile() throws IOException;

}
