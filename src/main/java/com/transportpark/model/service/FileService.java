package com.transportpark.model.service;

import java.io.File;

public interface FileService {

    File openFile();

    Boolean getIdentify();

    void writeToFile(Boolean identify);

    void initDefault();

    void clearFile();
}
