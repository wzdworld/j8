package com.star.mkdocshelper.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsListViewMap {
    public static final Map<String, String> SETTINGS_LIST_VIEW_FOR_MATERIAL = new LinkedHashMap<>();

    static {
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("基本信息", "basic-info-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("样式", "style-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("Logo", "logo-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("Favicon", "favicon-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("导航方式", "navigation-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("页脚", "footer-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("公告栏", "notification-view.fxml");
        SETTINGS_LIST_VIEW_FOR_MATERIAL.put("代码块", "code-block-view.fxml");
    }

    public static final Map<String, String> SETTINGS_LIST_VIEW_FOR_MKDOCS = new LinkedHashMap<>();
    static {
        SETTINGS_LIST_VIEW_FOR_MKDOCS.put("基本信息", "basic-info-view.fxml");
    }
    public static final Map<String, String> SETTINGS_LIST_VIEW_FOR_READTHEDOCS = new LinkedHashMap<>();
    static {
        SETTINGS_LIST_VIEW_FOR_READTHEDOCS.put("基本信息", "basic-info-view.fxml");
    }
}
