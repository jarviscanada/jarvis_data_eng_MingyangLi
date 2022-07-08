package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {
    public static void main(String[] args) {
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        System.out.println(javaGrepLambdaImp.readLines(new File("/Users/Frank/dev/init.bash")));
    }

    /**
     * @param inputFile file to be read
     * @return
     */
    @Override
    public List<String> readLines(File inputFile) {
        ArrayList<String> strings = new ArrayList<>();
        try (Stream<String> stringStream = Files.lines(inputFile.toPath())) {
            stringStream.forEach(strings::add);
        } catch (IOException e) {
            System.out.println("IOException by BufferedReader readLine!");
        }
        return strings;
    }

    /**
     * @param rootDir input directory
     * @return
     */
    @Override
    public List<File> listFiles(String rootDir) {
        List<File> allFiles = new ArrayList<>();
        Stream<File> fileStream1 = Arrays.stream(new File(rootDir).listFiles());
        fileStream1.filter(File::isDirectory).forEach(dir -> allFiles.addAll(listFiles(dir.getAbsolutePath())));
        Stream<File> fileStream2 = Arrays.stream(new File(rootDir).listFiles());
        allFiles.addAll(fileStream2.filter(File::isFile).collect(Collectors.toList()));
        return allFiles;
    }
}
