package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.Material;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.util.Map;

public class NavigationController {

    @FXML
    private CheckBox backToTopCheckbox;

    @FXML
    private CheckBox expandDirectoryCheckbox;

    @FXML
    private CheckBox lockTapsCheckbox;

    @FXML
    private CheckBox showTabsCheckbox;

    @FXML
    public void saveNavigation(ActionEvent event) {
        // 回到顶部按钮
        if (backToTopCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.top", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.top", false);
        }

        // 选项卡
        if (showTabsCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.tabs", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.tabs", false);
        }

        // 锁定选项卡
        if (lockTapsCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.tabs.sticky", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.tabs.sticky", false);
        }


        // 展开目录
        if (expandDirectoryCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.expand", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.expand", false);
        }

        Material.getConfigInstance().getTheme().clearAndInitFeatures();

        // 更新配置文件
        Material.refreshConfigFile();
    }

    public void initialize() {
        loadInformation();
    }

    public void loadInformation() {
        // 复选框
        if (Material.getConfigInstance().getTheme() != null &&
                Material.getConfigInstance().getTheme().getFeatures() != null) {
            Map<String, Boolean> featuresMap = Material.getConfigInstance().getTheme().getFeaturesMap();
            backToTopCheckbox.setSelected(featuresMap.get("navigation.top"));
            showTabsCheckbox.setSelected(featuresMap.get("navigation.tabs"));
            lockTapsCheckbox.setSelected(featuresMap.get("navigation.tabs.sticky"));
            expandDirectoryCheckbox.setSelected(featuresMap.get("navigation.expand"));
        }
    }
}
