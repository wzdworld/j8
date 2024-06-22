package com.star.mkdocshelper.controller.material.center;

import com.star.mkdocshelper.properties.material.Material;
import com.star.mkdocshelper.utils.FileUtil;
import com.star.mkdocshelper.utils.JavaFxUtil;
import com.star.mkdocshelper.variable.GlobalVariables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class LogoController {
    public static String logoParentDir = new File(GlobalVariables.projectPath, "docs/assets").getPath();
    private String uploadedFile;

    private String uploadFileName;


    @FXML
    private ImageView logoImageViewSelected;

    @FXML
    private Button openFileSelectorBtn;

    @FXML
    void openFileSelector(ActionEvent event) {
        // 打开文件选择器
        uploadedFile = JavaFxUtil.openFileSelector();

        // 获取上传文件的名字
        uploadFileName = new File(uploadedFile).getName();

        // 设置图片预览
        Image image = new Image("file:" +uploadedFile); // 替换为实际图片路径
        // 设置图片到ImageView
        logoImageViewSelected.setImage(image);
    }

    @FXML
    void saveLogo(ActionEvent event) {
        // 当上传文件的时候在保存
        if (uploadedFile != null) {
            // 上传格式要求 TODO

            // 复制图片到目标目录
            FileUtil.copyFileToDirectoryWithStringPaths(uploadedFile, logoParentDir);

            // 设置favicon配置
            Material.getConfigInstance().getTheme().setLogo("assets/" + uploadFileName);


            // 更新配置文件
            Material.refreshConfigFile();
        }
    }

    public void initialize() {
        //确保装logo的文件夹存在
        FileUtil.createDirectoryIfNotExists(logoParentDir);

        // MkDocs.getConfigInstance().getTheme().getLogo(): assets/logo.png
        String configLogo = Material.getConfigInstance().getTheme().getLogo();
        if (configLogo != null) {
            String fileName = new File(Material.getConfigInstance().getTheme().getLogo()).getName();
            // 加载本地图片文件
            Image image = new Image("file:" + new File(logoParentDir, fileName).getPath()); // 替换为实际图片路径
            // 设置图片到ImageView
            logoImageViewSelected.setImage(image);
        }
    }
}
