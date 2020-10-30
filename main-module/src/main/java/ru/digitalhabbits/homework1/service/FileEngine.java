package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Formatter;
import java.util.Objects;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        Formatter formatter = new Formatter();
        try (FileWriter fileWriter =
                     new FileWriter(formatter.format(RESULT_DIR + "/" + RESULT_FILE_PATTERN, pluginName).toString(), false)){
            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
