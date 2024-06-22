package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.Material;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BasicInfoController {
    @FXML
    private Label s;

    @FXML
    private TextField siteAuthorTextField;

    @FXML
    private TextArea siteDescriptionTextArea;

    @FXML
    private TextField siteNameTextField;

    /**
     * 保存material基本信息
     * @param event
     */
    @FXML
    void save(ActionEvent event) {
        // 设置站点名字
        Material.getConfigInstance().setSite_name(siteNameTextField.getText());
        // 设置站点作者
        Material.getConfigInstance().setSite_author(siteAuthorTextField.getText());
        // 设置站点描述
        Material.getConfigInstance().setSite_description(siteDescriptionTextArea.getText());
        // 更新配置文件
        Material.refreshConfigFile();
    }

    public void initialize() {
        loadInformation();
    }

    private void loadInformation() {
        siteNameTextField.setText(Material.getConfigInstance().getSite_name());
        siteAuthorTextField.setText(Material.getConfigInstance().getSite_author());
        siteDescriptionTextArea.setText(Material.getConfigInstance().getSite_description());
    }

}
