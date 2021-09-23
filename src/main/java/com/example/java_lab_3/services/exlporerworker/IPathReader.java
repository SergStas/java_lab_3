package com.example.java_lab_3.services.exlporerworker;

import com.example.java_lab_3.models.FileModel;

import java.util.List;

public interface IPathReader {
    List<FileModel> getListOfDirs(String path);
}
