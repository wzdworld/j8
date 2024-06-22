package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.Material;
import com.star.mkdocshelper.utils.FileUtil;
import com.star.mkdocshelper.variable.GlobalVariables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class NotificationController {

    @FXML
    private CheckBox openNotificationCheckbox;

    @FXML
    private TextArea notificationTextArea;

    @FXML
    private CheckBox showCloseBtn;

    @FXML
    void saveNotification(ActionEvent event) {
        // 保存内容到.helper文件夹的announce.txt
        FileUtil.createFileWithContent(GlobalVariables.projectPath + "/.helper/announce.txt", notificationTextArea.getText());

        if (openNotificationCheckbox.isSelected()) {
            // 构造内容
            String fileContent = "{% extends \"base.html\" %}{% block announce %}" + notificationTextArea.getText() + "{% endblock %}";
            // 创建文件
            FileUtil.createFileWithContent(GlobalVariables.projectPath + "/overrides/main.html", fileContent);

        } else {
            // 删除文件
            FileUtil.deleteFile(GlobalVariables.projectPath + "/overrides/main.html");
        }
        // 按钮显示
        if (showCloseBtn.isSelected()) {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("announce.dismiss", true);
        } else {
            Material.getConfigInstance().getTheme().getFeaturesMap().put("announce.dismiss", false);
        }

        Material.getConfigInstance().getTheme().clearAndInitFeatures();

        // 更新配置文件
        Material.refreshConfigFile();
    }

    public void initialize() {
        loadInformation();
    }

    private void loadInformation() {
        // 判断文件存在来判断是否开启公告栏
        boolean fileExists = FileUtil.isFileExists(GlobalVariables.projectPath + "/overrides/main.html");
        if (fileExists) {
            openNotificationCheckbox.setSelected(true);
        } else {
            openNotificationCheckbox.setSelected(false);
        }

        // 读取内容
        String content = FileUtil.readFileAsString(GlobalVariables.projectPath + "/.helper/announce.txt");
        // 显示内容
        notificationTextArea.setText(content);

        if (Material.getConfigInstance().getTheme() != null
                && Material.getConfigInstance().getTheme().getFeatures() != null) {
            Boolean b = Material.getConfigInstance().getTheme().getFeaturesMap().get("announce.dismiss");
            showCloseBtn.setSelected(true);
        }
    }

}
