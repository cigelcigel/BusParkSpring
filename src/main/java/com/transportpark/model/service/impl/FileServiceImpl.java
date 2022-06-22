package com.transportpark.model.service.impl;

import com.transportpark.model.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private static final String FILE_PATH = "src\\main\\resources\\access\\identification.txt";

    @Override
    public File openFile() {
        try {
            return new File(FILE_PATH);
        } catch (Exception e) {
            log.warn("Cann't open file: " + FILE_PATH, e);
        }
        return null;
    }

    @PostConstruct
    @Override
    public void initDefault() {
        try {
            File file = openFile();
            Files.write(file.toPath(), Boolean.FALSE.toString().getBytes());
        } catch (Exception e) {
            log.warn("Cann't write to: " + FILE_PATH, e);
        }
    }

    @Override
    public Boolean getIdentify() {
        try {
            File file = openFile();
            return Boolean.valueOf(new String(Files.readAllBytes(file.toPath())));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void writeToFile(Boolean identify) {
        try {
            File file = openFile();
            Files.write(file.toPath(), identify.toString().getBytes());
        } catch (Exception e) {
            log.warn("Cann't write to: " + FILE_PATH, e);
        }
    }

    @PreDestroy
    @Override
    public void clearFile() {
        try {
            File file = openFile();
            BufferedWriter writer = Files.newBufferedWriter(file.toPath());
            writer.write("");
            writer.flush();
        } catch (Exception e) {
            log.warn("Cann't clear: " + FILE_PATH, e);
        }
    }
}
