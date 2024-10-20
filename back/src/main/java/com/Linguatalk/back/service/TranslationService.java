package com.Linguatalk.back.service;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    public String translateMessage(String message, String sourceLanguage, String targetLanguage) {
        // Получаем URL для перевода из системной переменной
        String translateUrl = System.getenv("TRANSLATE_DOCKER_HOST");

        // Если системная переменная не установлена, можно задать значение по умолчанию
        if (translateUrl == null || translateUrl.isEmpty()) {
            throw new IllegalArgumentException("Environment variable TRANSLATE_DOCKER_HOST is not set");
        }

        // Собираем полный URL
        String url = "http://" + translateUrl + "/translate";

        // Формируем тело запроса
        String requestBody = "{\n" +
                " \"id\": \"46dc4c79-12de-4642-abd1-5e655c6090db\",\n" +
                " \"message\": \"" + message + "\",\n" +
                " \"languageFrom\":\"" + sourceLanguage + "\",\n" +
                " \"languageTo\": \"" + targetLanguage + "\"\n" +
                "}";

        // Отправляем POST запрос с телом запроса и получаем ответ
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .statusCode(200) // Убеждаемся, что статус код 200
                .extract()
                .response();

        // Извлекаем перевод из ответа
        String translatedMessage = response.jsonPath().getString("message");
        return translatedMessage;
    }

}
