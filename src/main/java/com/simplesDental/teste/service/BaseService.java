package com.simplesDental.teste.service;

import com.simplesDental.teste.exception.MandatoryAttributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
public class BaseService {

    @Autowired
    private MessageSource messageSource;

    @Value("${log.directory}")
    private String logDirectory;

    @Value("${default.language}")
    private String defaultLanguage;

    @Value("${default.country}")
    private String defaultCountry;

    protected void log(String message){

        if(Objects.isNull(logDirectory)){
            throw new MandatoryAttributeException("inform.log.path");
        }

        String logFile = LocalDate.now().toString().concat(".log");
        Path path = Paths.get(logDirectory.concat("/").concat(logFile));

        String finalMessage;
        try {
            finalMessage = getMessage(message);
        }catch (NoSuchMessageException messageException){
            finalMessage = message;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        String logMessage = LocalDateTime.now().toString().concat(" --- ").concat(finalMessage).concat("\n");

        try {
            Files.writeString(path, logMessage, StandardOpenOption.APPEND);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    protected String sucessCreate(String message, UUID id){
        String msg = getMessage(message);
        return msg.replace("{ID}", id.toString());
    }

    protected String getMessage(String code) {
        if(Objects.isNull(this.defaultLanguage) || this.defaultLanguage.isBlank() || Objects.isNull(this.defaultCountry) || this.defaultCountry.isBlank()){
            return messageSource.getMessage(code, null, new Locale("pt", "BR"));
        }
        return messageSource.getMessage(code, null, new Locale(this.defaultLanguage, this.defaultCountry));
    }
}
