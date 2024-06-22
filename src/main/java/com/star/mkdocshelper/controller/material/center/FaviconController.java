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

public class FaviconController {
    public static String faviconParentDir = new File(GlobalVariables.projectPath, "docs/assets/images").getPath();

    @FXML
    private ImageView faviconImageViewSelected;

    @FXML
    private Button openFileSelectorBtn;

    private String uploadedFile;

    private String uploadFileName;

    /**
     * 打开Favicon选择器
     * @param event
     */
    @FXML
    void openFileSelector(ActionEvent event) {
        // 打开文件选择器
        //uploadedFile = JavaFxUtil.openFileSelector();
        uploadedFile = JavaFxUtil.showImageOpenDialog(null);

        // 获取上传文件的名字
        uploadFileName = new File(uploadedFile).getName();

        // 设置图片预览
        Image image = new Image("file:" +uploadedFile); // 替换为实际图片路径
        // 设置图片到ImageView
        faviconImageViewSelected.setImage(image);
    }

    public void initialize() {
        //确保装favicon的文件夹存在
        FileUtil.createDirectoryIfNotExists(faviconParentDir);

        // MkDocs.getConfigInstance().getTheme().getFavicon(): assets/images/favicon.png
        String configFavicon = Material.getConfigInstance().getTheme().getFavicon();
        if (configFavicon != null) {
            String fileName = new File(Material.getConfigInstance().getTheme().getFavicon()).getName();
            // 加载本地图片文件
            Image image = new Image("file:" + new File(faviconParentDir, fileName).getPath()); // 替换为实际图片路径
            // 设置图片到ImageView
            faviconImageViewSelected.setImage(image);
        }
    }

    /**
     * 保存Favicon设置
     * @param event
     */
    @FXML
    void saveFaviconSettings(ActionEvent event) {
        // 当上传文件的时候在保存
        if (uploadedFile != null) {
             //上传格式要求 TODO（不用管先）

            // 复制图片到目标目录
            FileUtil.copyFileToDirectoryWithStringPaths(uploadedFile, faviconParentDir);

            // 设置favicon配置
            Material.getConfigInstance().getTheme().setFavicon("assets/images/" + uploadFileName);

            // 更新配置文件
            Material.refreshConfigFile();
        }
    }
}
