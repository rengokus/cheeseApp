package project.cheeseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.cheeseapp.model.Backup;
import project.cheeseapp.repository.BackupRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/backup")
public class AdminController {

    @Autowired
    private BackupRepository backupRepo;

    @PostMapping("/do")
    public void backup(@RequestParam String backupName) {
        Process process;
        ProcessBuilder processBuilder;
        LocalDate dateNow = LocalDate.now();
        String fileName;
        if (backupName == null) {
            fileName = LocalDate.now() + "_backup";
        } else {
            fileName = backupName;
        }
        String filePath = "E:\\backups\\" + fileName;
        processBuilder = new ProcessBuilder(
                "C:\\Program Files\\PostgreSQL\\13\\bin\\pg_dump.exe",
                "--host=localhost",
                "--port=5432",
                "--username=postgres",
                "--no-password",
                "--format=custom",
                "--blobs",
                "--verbose",
                "--file=" + filePath, "db1"
        );
        try {
            final Map<String, String> env = processBuilder.environment();
            env.put("PGPASSWORD", "admin");
            process = processBuilder.start();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );
            String line = reader.readLine();
            while (line != null) {
                System.err.println(line);
                line = reader.readLine();
            }
            reader.close();
            process.waitFor();
            System.out.println(process.exitValue());

            if (backupRepo.findByName(fileName) == null) {
                Backup backup = new Backup();
                backup.setDate(dateNow);
                backup.setName(fileName);
                backup.setPath(filePath);
                backupRepo.save(backup);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/show")
    public List<Backup> show() {
        return backupRepo.findAll();
    }

    @PostMapping("/restore")
    public void restore(@RequestParam String fileName) {

        Backup backup = backupRepo.findByName(fileName);
        Process process;
        ProcessBuilder processBuilder;
        processBuilder = new ProcessBuilder(
                "C:\\Program Files\\PostgreSQL\\13\\bin\\pg_restore.exe",
                "--host=localhost",
                "--port=5432",
                "--username=postgres",
                "--clean",
                "--dbname=db1",
                backup.getPath()
        );
        try {
            final Map<String, String> env = processBuilder.environment();
            env.put("PGPASSWORD", "admin");
            process = processBuilder.start();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );
            String line = reader.readLine();
            while (line != null) {
                System.err.println(line);
                line = reader.readLine();
            }
            reader.close();
            process.waitFor();
            System.out.println(process.exitValue());
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
