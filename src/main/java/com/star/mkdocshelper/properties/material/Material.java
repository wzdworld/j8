package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.star.mkdocshelper.utils.SerializationUtil;
import com.star.mkdocshelper.variable.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Material {
    // 单例设计模式
    @Setter
    private static Material configInstance;

    // 站点名字（必选）
    private String site_name;
    // 站点链接-重点
    private String site_url = "http://localhost:8000";
    // 站点作者（可选）
    private String site_author;
    // 站点描述（可选）
    private String site_description;

    // 版权声明（可选）
    private String copyright;

    @JsonProperty("theme")
    private Theme theme = new Theme();
    private List<String> plugins = new ArrayList<>();
    private List<Object> markdown_extensions = new ArrayList<>();
    private Extra extra = new Extra();
    private List<String> extra_javascript = new ArrayList<>();
    private List<String> extra_css = new ArrayList<>();

    private Material() {
        //System.out.println("调用Mkdocs...");
        initPlugins();
        initExtraJavascript();
        initExtraCss();
        initMarkdownExtensions();
    }

    public static void refreshConfigFile() {
        SerializationUtil.javaToYaml(configInstance, new File(GlobalVariables.projectPath, "mkdocs.yml").getPath());
    }

    public static void resetConfigInstance() {
        configInstance = null;
    }

    public static Material getConfigInstance() {
        if (configInstance == null) {
            configInstance = new Material();
        }
        return configInstance;
    }

    public static Material getConfigInstanceNotCreate() {
        return configInstance;
    }

    /**
     * 初始化插件
     */
    public void initPlugins() {
        plugins.add("search");
        plugins.add("tags");
    }

    /**
     * 初始化extra_javascript
     */
    public void initExtraJavascript() {
        extra_javascript.add("javascripts/katex.js");
        extra_javascript.add("https://unpkg.com/katex@0/dist/katex.min.js");
        extra_javascript.add("https://unpkg.com/katex@0/dist/contrib/auto-render.min.js");
    }

    /**
     * 初始化extra_css
     */
    public void initExtraCss() {
        extra_css.add("https://unpkg.com/katex@0/dist/katex.min.css");
    }

    /**
     * 初始化markdown_extensions
     */
    public void initMarkdownExtensions() {
        markdown_extensions.add("def_list");
        markdown_extensions.add("md_in_html");
        markdown_extensions.add("attr_list");
        markdown_extensions.add("pymdownx.inlinehilite");
        markdown_extensions.add("pymdownx.snippets");
        markdown_extensions.add("pymdownx.superfences");

        TocIncluded tocIncluded = new TocIncluded();
        markdown_extensions.add(tocIncluded);

        PymdownxArithmatexIncluded pymdownxArithmatexIncluded = new PymdownxArithmatexIncluded();
        markdown_extensions.add(pymdownxArithmatexIncluded);

        // 索引8
        PymdownxHighlightIncluded pymdownxHighlightIncluded = new PymdownxHighlightIncluded();
        markdown_extensions.add(pymdownxHighlightIncluded);

        /*
        如果要增加配置，必须在下面增加,不得在上面增加
         */
    }

    @JsonIgnore
    public LinkedHashMap getPymdownxHighlightIncludedInMarkdownExtensions() {
        LinkedHashMap linkedHashMap = (LinkedHashMap) markdown_extensions.get(8);
        LinkedHashMap target = (LinkedHashMap) linkedHashMap.get("pymdownx.highlight");
        return target;
    }
}
