package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.Material;
import com.star.mkdocshelper.properties.material.PymdownxHighlight;
import com.star.mkdocshelper.properties.material.PymdownxHighlightIncluded;
import com.star.mkdocshelper.variable.GlobalVariables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodeBlockController {

    @FXML
    private CheckBox codeAnchorLinkCheckbox;

    @FXML
    private CheckBox codeTitleCheckbox;

    @FXML
    private CheckBox copyCodeCheckbox;

    @FXML
    private CheckBox lineNumberCheckbox;


    public void initialize() {
        if (GlobalVariables.CREATED_PROJECT != null && GlobalVariables.CREATED_PROJECT) {
            loadInformationAfterCreateProject();
        } else {
            loadInformationAfterOpenProject();
        }
    }

    public void loadInformationAfterOpenProject() {
        // 获取行号，锚点链接，编程语言名称
        if (Material.getConfigInstance().getMarkdown_extensions() != null) {

            LinkedHashMap map = Material.getConfigInstance().getPymdownxHighlightIncludedInMarkdownExtensions();

            lineNumberCheckbox.setSelected((Boolean) map.get("linenums"));
            codeAnchorLinkCheckbox.setSelected((Boolean) map.get("anchor_linenums"));
            codeTitleCheckbox.setSelected((Boolean) map.get("auto_title"));
        }
        // 获取代码复制按钮启用情况
        if (Material.getConfigInstance().getTheme() != null &&
                Material.getConfigInstance().getTheme().getFeatures() != null) {
            Map<String, Boolean> featuresMap = Material.getConfigInstance().getTheme().getFeaturesMap();
            copyCodeCheckbox.setSelected(featuresMap.get("content.code.copy"));
        }
    }

    public void loadInformationAfterCreateProject() {
        // 获取行号，锚点链接，编程语言名称
        if (Material.getConfigInstance().getMarkdown_extensions() != null) {
            PymdownxHighlightIncluded pymdownxHighlightIncluded = (PymdownxHighlightIncluded) Material.getConfigInstance().getMarkdown_extensions().get(8);
            PymdownxHighlight pymdownxHighlight = pymdownxHighlightIncluded.getPymdownxHighlight();
            lineNumberCheckbox.setSelected(pymdownxHighlight.getLinenums());
            codeAnchorLinkCheckbox.setSelected(pymdownxHighlight.getAnchor_linenums());
            codeTitleCheckbox.setSelected(pymdownxHighlight.getAuto_title());
        }
        // 获取代码复制按钮启用情况
        if (Material.getConfigInstance().getTheme() != null &&
                Material.getConfigInstance().getTheme().getFeatures() != null) {
            Map<String, Boolean> featuresMap = Material.getConfigInstance().getTheme().getFeaturesMap();
            copyCodeCheckbox.setSelected(featuresMap.get("content.code.copy"));
        }
    }


    @FXML
    void saveCode(ActionEvent event) {
        if (GlobalVariables.CREATED_PROJECT != null && GlobalVariables.CREATED_PROJECT) {
            saveCodeAfterCreateProject();
        } else {
            saveCodeAfterOpenProject();
        }

    }

    private void saveCodeAfterOpenProject() {
        // 保存代码复制按钮设置
        if (copyCodeCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("content.code.copy", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("content.code.copy", false);
        }
        // 刷新特征
        Material.getConfigInstance().getTheme().clearAndInitFeatures();

        LinkedHashMap map = Material.getConfigInstance().getPymdownxHighlightIncludedInMarkdownExtensions();

        // 行号
        if (lineNumberCheckbox.isSelected()) {
            map.put("linenums", true);
        } else {
            map.put("linenums", false);
        }

        if (codeAnchorLinkCheckbox.isSelected()) {
            map.put("anchor_linenums", true);
        } else {
            map.put("anchor_linenums", false);
        }

        if (codeTitleCheckbox.isSelected()) {
            map.put("auto_title", true);
        } else {
            map.put("auto_title", false);
        }

        // 更新配置文件
        Material.refreshConfigFile();
    }

    private void saveCodeAfterCreateProject() {
        // 保存代码复制按钮设置
        if (copyCodeCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("content.code.copy", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("content.code.copy", false);
        }
        // 刷新特征
        Material.getConfigInstance().getTheme().clearAndInitFeatures();


        PymdownxHighlightIncluded pymdownxHighlightIncluded = (PymdownxHighlightIncluded) Material.getConfigInstance().getMarkdown_extensions().get(8);
        PymdownxHighlight pymdownxHighlight = pymdownxHighlightIncluded.getPymdownxHighlight();

        // 行号
        if (lineNumberCheckbox.isSelected()) {
            pymdownxHighlight.setLinenums(true);
        } else {
            pymdownxHighlight.setLinenums(false);
        }

        if (codeAnchorLinkCheckbox.isSelected()) {
            pymdownxHighlight.setAnchor_linenums(true);
        } else {
            pymdownxHighlight.setAnchor_linenums(false);
        }

        if (codeTitleCheckbox.isSelected()) {
            pymdownxHighlight.setAuto_title(true);
        } else {
            pymdownxHighlight.setAuto_title(false);
        }

        // 更新配置文件
        Material.refreshConfigFile();
    }

}
