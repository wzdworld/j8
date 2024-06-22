package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.Material;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class FooterController  {

    @FXML
    private CheckBox switchPageCheckbox;

    @FXML
    private TextArea copyrightTextArea;

    public void initialize() {
        loadInformation();
    }

    public void loadInformation() {
        // 复选框
        if (Material.getConfigInstance().getTheme() != null &&
                Material.getConfigInstance().getTheme().getFeatures() != null) {
            Boolean isSwitchPage = Material.getConfigInstance().getTheme().getFeaturesMap().get("navigation.footer");
            switchPageCheckbox.setSelected(isSwitchPage);
        }

        // 版权声明
        String copyright = Material.getConfigInstance().getCopyright();
        if (copyright != null) {
            copyrightTextArea.setText(copyright);
        }

    }

    @FXML
    public void saveFooter(ActionEvent event) {
        // 保存上下页切换复选框
        if (switchPageCheckbox.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.footer", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("navigation.footer", false);
        }
        Material.getConfigInstance().getTheme().clearAndInitFeatures();
        // 保存版权声明
        Material.getConfigInstance().setCopyright(copyrightTextArea.getText());

        // 更新配置文件
        Material.refreshConfigFile();
    }
}
