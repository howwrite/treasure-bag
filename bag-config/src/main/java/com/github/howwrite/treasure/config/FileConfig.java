package com.github.howwrite.treasure.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileConfig extends AbstractConfig {
    @Override
    public <T> T calValue(String key, Class<T> clazz) {
        String configFolderPath = "config/";
        String filePath = configFolderPath + key;

        // 检查文件是否存在
        if (!Files.exists(Paths.get(filePath))) {
            return null;
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine.trim());
            }
        } catch (Exception e) {
            return null;
        }

        return convertValue(contentBuilder.toString(), clazz);
    }
}
