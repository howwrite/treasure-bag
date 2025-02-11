package com.github.howwrite.treasure.config.reader;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileConfig<T> extends AbstractConfig<T> {
    @Override
    public String readConfig(String namespace, String key) {
        String configFolderPath = "config/";
        if (StringUtils.isNotBlank(namespace)) {
            configFolderPath += namespace + "/";
        }
        String filePath = judgeConfigPath(configFolderPath + key);

        // 检查文件是否存在
        if (filePath == null) {
            return null;
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            boolean firstLine = true;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine);
                if (firstLine) {
                    firstLine = false;
                } else {
                    contentBuilder.append('\n');
                }
            }
        } catch (Exception e) {
            return null;
        }

        return contentBuilder.toString();
    }

    private String judgeConfigPath(String pathName) {
        String jsonName = pathName + ".json";
        if (Files.exists(Paths.get(jsonName))) {
            return jsonName;
        }
        String confName = pathName + ".conf";
        if (Files.exists(Paths.get(confName))) {
            return confName;
        }
        return null;
    }
}
