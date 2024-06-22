package com.star.mkdocshelper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MkDocsHelperApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MkDocsHelperApplication.class.getResource("view/main-window-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(MkDocsHelperApplication.class.getResource("view/material/center/guide-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MkDocsHelper");
        stage.setScene(scene);


        // 加载图片
        Image logo = new Image(
                MkDocsHelperApplication.class
                        .getResourceAsStream("/com/star/mkdocshelper/images/logo/logo.png"));


        // 设置舞台图标
        stage.getIcons().add(logo);


        //MainWindowController.primaryStage = stage;
        stage.show();
    }
}
