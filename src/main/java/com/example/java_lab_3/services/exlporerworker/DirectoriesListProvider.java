package com.example.java_lab_3.services.exlporerworker;

import com.example.java_lab_3.models.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoriesListProvider implements IPathReader{
    @Override
    public List<FileModel> getListOfDirs(String path) {
        String root = "D:\\git\\OOA\\java_lab_3";
        path = root + path.replace("/", "\\");
        return Stream.of(new File(path).listFiles())
                .map(file -> new FileModel(
                        file.getName().replace("\\", "/"),
                        file.isDirectory()
                ))
                .collect(Collectors.toList());
    }
}
