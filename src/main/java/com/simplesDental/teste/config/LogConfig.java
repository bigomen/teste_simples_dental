package com.simplesDental.teste.config;

import com.simplesDental.teste.exception.MandatoryAttributeException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

@Configuration
public class LogConfig {

    @Value("${log.directory}")
    private String logDirectory;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void createLogFile(){

        if(Objects.isNull(logDirectory)){
            throw new MandatoryAttributeException("inform.log.path");
        }

        String fileName = LocalDate.now().toString().concat(".log");
        String logFilePath = logDirectory.concat("/").concat(fileName);

        Path path = Paths.get(logFilePath);

        try{
            Files.createDirectories(Paths.get(logDirectory));

            if(!Files.exists(path)){
                Files.createFile(path);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
