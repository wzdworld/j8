package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Theme {
    // 辅助变量
    @JsonIgnore
    @Getter
    private Map<String, Boolean> featuresMap = new LinkedHashMap<>();

    // 站点语言（必选）
    private String language;

    // 站点Favicon(设置Favicon时)
    private String favicon = "assets/images/favicon.png";

    // 扩展主题（开启公告栏时）
    private String custom_dir = "overrides";

    // LOGO(自定义LOGO时)
    private String logo = "assets/logo.png";


    private String name = "material";
    private List<ColorScheme> palette = new ArrayList<>();
    private List<String> features = new ArrayList<>();

    public Theme() {
        initFeaturesMap();

        // 初始化特征数组
        initFeatures();

        initPalette();
    }

    public void featuresToMap() {
        for (String feature : features) {
            featuresMap.put(feature, true);
        }
    }

    /**
     * 初始化特征集合
     */
    private void initFeaturesMap() {
        featuresMap.put("content.code.copy", true);
        featuresMap.put("navigation.top", true);

        featuresMap.put("header.autohide", false);
        featuresMap.put("announce.dismiss", false);
        featuresMap.put("navigation.footer", false);
        featuresMap.put("navigation.expand", false);
        featuresMap.put("navigation.tabs", false);
        featuresMap.put("navigation.tabs.sticky", false);
    }


    /**
     * 清空特征数组
     */
    public void clearFeatures() {
        features.clear();
    }

    public void clearAndInitFeatures() {
        features.clear();
        initFeatures();
    }

    /**
     * 初始化特征数组
     */
    public void initFeatures() {
        // TODO 解决硬编码问题
        //features.add("content.code.copy");
        //features.add("navigation.top");

        // 遍历特征集合，将为真的键添加到特征数组
        featuresMap.forEach((content, isAdd) -> {
            if (isAdd) {
                features.add(content);
            }
        });
    }

    /**
     * 初始化调色板数组
     */
    private void initPalette() {
        palette.add(ColorScheme.dayColorScheme);
        palette.add(ColorScheme.nightColorScheme);

    }
}
