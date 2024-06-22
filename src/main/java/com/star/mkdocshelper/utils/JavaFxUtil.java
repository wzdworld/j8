package com.star.mkdocshelper.utils;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class JavaFxUtil {
    public static Alert warningAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title); // 设置对话框的标题
        alert.setHeaderText(headerText); // 设置对话框头部文本
        alert.setContentText(contentText); // 设置对话框的主要内容文本
        return alert;
    }

    public static String openFileSelector() {
        FileChooser fileChooser = new FileChooser();

        // 直接打开文件选择对话框，不设置过滤器
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public static String openDirSelector() {
        // 创建一个DirectoryChooser实例
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // 设置对话框的标题
        directoryChooser.setTitle("选择一个文件夹");

        // 打开目录选择对话框并获取用户选择的目录，使用primaryStage作为父窗口
        File selectedDirectory = directoryChooser.showDialog(null);

        // 如果用户选择了目录，则打印目录的路径
        if (selectedDirectory != null) {
            return selectedDirectory.getAbsolutePath();
        }
        return null;
    }

    /**
     * 弹出图片选择对话框并返回选中的图片文件路径。
     *
     * @param ownerWindow 用于定位文件选择器的父窗口，如果为空，则文件选择器将无归属窗口
     * @return 用户选择的图片文件路径，如果没有选择则返回null
     */
    public static String showImageOpenDialog(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();

        // 设置文件选择器的标题
        fileChooser.setTitle("请选择一张图片");

        // 定义图片文件的扩展名列表
        List<String> extensions = Arrays.asList(
                "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp", "*.svg"
        );

        // 创建一个文件过滤器，接受上述所有图片格式
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("图片文件", extensions);

        // 添加过滤器到文件选择器
        fileChooser.getExtensionFilters().add(imageFilter);

        // 显示文件选择对话框并等待用户操作
        File selectedFile = fileChooser.showOpenDialog(ownerWindow);

        // 返回用户选择的图片文件路径，如果用户取消选择则返回null
        return (selectedFile != null) ? selectedFile.getAbsolutePath() : null;
    }
}
