package com.example.bookdemo.serviceimpl;

import com.example.bookdemo.entity.Book;
import com.example.bookdemo.entity.BookIcon;
import com.example.bookdemo.entity.Tag;
import com.example.bookdemo.service.BookService;
import com.example.bookdemo.DAO.BookDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;


    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }



    @Override
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public Book getBookById(int id) {

        return bookDAO.getBookById(id);
    }
    @Override
    public Book addBook(Book book){

        return bookDAO.addBook(book);
    };
    @Override
    public void deleteBook(int id){
         bookDAO.deleteBook(id);
    };
    @Override
    public void modifyBook(Book book) {

        bookDAO.addBook(book);
    }
    @Override
    public List<Book> findBookByTag(String tag){
        List<Tag> shit=bookDAO.findRelatedTags(tag);
        if (shit.isEmpty()) {
            System.out.println("No tags found for the specified tag.");
            return  Collections.emptyList();
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (Tag relatedTag : shit) {
            List<Book> booksForTag = bookDAO.findBookByTag(relatedTag.getName());
            uniqueBooks.addAll(booksForTag);
        }

        return new ArrayList<>(uniqueBooks);
    }
    @Override
    public List<Book> findBookByName(String name){
        return bookDAO.findBookByName(name);
    }

    private static final String OUTPUT_DIRSPARK = "E:/springdemo/bookdemo/sth/output";
    private static final String OUTPUT_DIRMR = "E:/springdemo/bookdemo/MR2/output";
    @Override
    public String getResult() {
        // Step 1: Delete the output directory.
        String ans="Spark result: ";
        deleteOutputDirectory(OUTPUT_DIRSPARK);
        deleteOutputDirectory(OUTPUT_DIRMR);
        System.out.println("deleted successfully\n");
        // Step 2: Execute the Spark job via Docker.
        String sparkJobOutput = runDockerSparkSubmit();
        ans+=sparkJobOutput;
        System.out.println("everything fine\n");
        // Step 3: Retrieve results from the Spark job (if needed).
        // This step is up to your implementation needs.

        runSpecificClassInJar();
        ans+=System.lineSeparator();
        ans+="Hadoop Result:";
        ans+= readSparkOutputFiles(OUTPUT_DIRMR);

        return ans;
    }

    private void deleteOutputDirectory(String directoryPath) {
        Path directory = Paths.get(directoryPath);
        if (Files.exists(directory)) {
            try {
                Files.walk(directory)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete output directory", e);
            }
        }
    }


    private String runDockerSparkSubmit() {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "docker", "exec" ,"spark-master",
                "/opt/bitnami/spark/bin/spark-submit",
                "--class", "KeywordCount",
                "--master", "spark://172.19.0.2:7077",
                "/data/WithOutputfile.jar"
        );
        processBuilder.redirectErrorStream(true);
        System.out.println("set successfully\n");
        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("exited?? successfully\n");
            if (exitCode != 0) {
                throw new RuntimeException("Docker command failed with exit code: " + exitCode);
            }
        System.out.println("here");
            return readSparkOutputFiles(OUTPUT_DIRSPARK);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to run docker command", e);
        }
    }
    private String readSparkOutputFiles(String directoryPath) {
        StringBuilder outputContent = new StringBuilder();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("part") && !path.toString().endsWith(".crc"))
                    .forEach(path -> {
                        try {
                            List<String> lines = Files.readAllLines(path);
                            lines.forEach(line -> outputContent.append(line).append(System.lineSeparator()));
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to read output file: " + path, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to walk through output directory", e);
        }
        return outputContent.toString();
    }
    public void runSpecificClassInJar() {
        String inputPath = "E:/springdemo/bookdemo/MR2/input";
        String outputPath = "E:/springdemo/bookdemo/MR2/output";
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-cp", "E:/springdemo/bookdemo/MR2/jarfile.jar", "WordCountWithKeywords", inputPath, outputPath
        );

        // 合并错误流和标准输出流
        processBuilder.redirectErrorStream(true);

        try {
            // 启动进程
            Process process = processBuilder.start();

            // 读取输出
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // 处理每一行输出
                    System.out.println(line);
                }
            }

            // 等待进程结束
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Running specific class failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            // 这里捕获了IOException和InterruptedException，并打印错误信息
            // 这也意味着IOException不需要在方法签名中声明
            System.err.println("An error occurred while running the specific class: " + e.getMessage());
            Thread.currentThread().interrupt(); // 重新中断当前线程，因为InterruptedException被捕获
        }
    }



}
