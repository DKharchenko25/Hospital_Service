package com.epam.hospital.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Properties;

public class VerifyRecaptcha {

    public static boolean verify(String recaptchaResponse) {
        Properties properties = ApplicationPropertiesLoader.load();
        if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(properties.getProperty("recaptcha.url"));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            String postParams = "secret=" + properties.getProperty("recaptcha.secretKey") + "&response=" + recaptchaResponse;
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(postParams);
            dataOutputStream.flush();
            dataOutputStream.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
