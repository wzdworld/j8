package com.star.mkdocshelper.properties.readthedocs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.star.mkdocshelper.utils.SerializationUtil;
import com.star.mkdocshelper.variable.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Readthedocs {
    // 单例设计模式
    @Setter
    private static Readthedocs configInstance;

    // 站点名字（必选）
    private String site_name;
    // 站点链接-重点
    private String site_url = "http://localhost:8000";

    // 站点作者（可选）
    private String site_author;
    // 站点描述（可选）
    private String site_description;

    @JsonProperty("theme")
    private ReadthedocsTheme readthedocsTheme = new ReadthedocsTheme();

    public static Readthedocs getConfigInstance() {
        if (configInstance == null) {
            configInstance = new Readthedocs();
        }
        return configInstance;
    }

    public static void resetConfigInstance() {
        configInstance = null;
    }

    public static void refreshConfigFile() {
        SerializationUtil.javaToYaml(configInstance, new File(GlobalVariables.projectPath, "mkdocs.yml").getPath());
    }
}
