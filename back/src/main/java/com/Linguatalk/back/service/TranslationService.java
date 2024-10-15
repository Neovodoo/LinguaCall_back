package com.Linguatalk.back.service;


import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class TranslationService {

    public String translateMessage(String message, String languageFrom, String languageTo) {
        //TODO: Реализовать перевод пходящего сообщения
        try {
            // Путь к Python-скрипту
            String scriptPath = "src/main/java/translate.py";

            // Используем ProcessBuilder для запуска Python-скрипта
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, message, languageFrom, languageTo);
            processBuilder.directory(new File("C:/Users/Кирилл/IdeaProjects/LinguaCall_back/back"));// путь, откуда сервер берет скрипт

            // Запуск процесса и чтение вывода
            Process process = processBuilder.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = stdInput.readLine()) != null) {
                result.append(line);
            }

            // Проверка завершения процесса
            if (process.waitFor() != 0) {
                System.err.println("Ошибка при выполнении Python-скрипта.");
                return null;
            }
            System.out.println("Результат перевода" + result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
