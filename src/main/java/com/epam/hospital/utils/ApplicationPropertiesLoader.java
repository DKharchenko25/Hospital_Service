package com.epam.hospital.utils;

import com.epam.hospital.data_access_layer.DataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

public class ApplicationPropertiesLoader {
    public static Properties load() {
        Properties properties = new Properties();
        try (InputStream inputStream = DataSource.class.getResourceAsStream("/application.properties")) {
            properties.load(new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream))));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
