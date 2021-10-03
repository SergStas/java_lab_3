package com.example.java_lab_3.services.exlporerworker;

import com.example.java_lab_3.models.FileModel;

import java.util.List;

public interface IDirManager {
    List<FileModel> getUserFileSystem(String path, String login);
}
