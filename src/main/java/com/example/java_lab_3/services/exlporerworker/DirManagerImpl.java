package com.example.java_lab_3.services.exlporerworker;

import com.example.java_lab_3.models.FileModel;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirManagerImpl implements IDirManager {
    @Override
    public List<FileModel> getUserFileSystem(String path, String login) {
        try {
            String root = "D:\\git\\OOA\\java_lab_3\\rootFileSystem\\" + login;
            new File(root).mkdir();
            new File(root + "/demodir").mkdir();

            path = root + path.replace("/", "\\");
            return Stream.of(new File(path).listFiles())
                    .map(file -> new FileModel(
                            file.getName().replace("\\", "/"),
                            file.isDirectory()
                    ))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            System.out.println("Failed to enum dirs:\n\t" + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }
}
