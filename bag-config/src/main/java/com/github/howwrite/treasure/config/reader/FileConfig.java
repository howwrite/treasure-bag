package com.github.howwrite.treasure.config.reader;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileConfig<T> extends AbstractConfig<T> {
    @Override
    public String readConfig(String prefix, String key) {
        String configFolderPath = "config/";
        if (StringUtils.isNotBlank(prefix)) {
            configFolderPath += prefix + "/";
        }
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

        return contentBuilder.toString();
    }
}
