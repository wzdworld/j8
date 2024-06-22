package com.star.mkdocshelper.controller;

import com.star.mkdocshelper.MkDocsHelperApplication;
import com.star.mkdocshelper.constant.CommandMarkConstant;
import com.star.mkdocshelper.constant.HelpLinkConstant;
import com.star.mkdocshelper.constant.WebCategoryConstant;
import com.star.mkdocshelper.properties.config.GithubProperties;
import com.star.mkdocshelper.properties.material.Material;
import com.star.mkdocshelper.properties.mkdocs.MkDocs;
import com.star.mkdocshelper.properties.readthedocs.Readthedocs;
import com.star.mkdocshelper.utils.*;
import com.star.mkdocshelper.variable.GlobalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.star.mkdocshelper.constant.SettingsListViewMap.*;

public class MainWindowController {


    public static Stage primaryStage;

    @FXML
    private TextField portTextField;

    @FXML
    private AnchorPane commandWindow;

    @FXML
    private Button localRunBtn;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private ListView<String> settngsListView;

    @FXML
    private Label statusLeftLabel;

    @FXML
    private Hyperlink hyperlinkAfterAlreadyDeploy;


    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;


    @FXML
    private TextField gitEmailField;

    @FXML
    private TextField gitUserTextField;

    @FXML
    private Button deployBtn;

    @FXML
    private Hyperlink hyperlinkForLocalRun;

    @FXML
    private Hyperlink hyperlinkAfterDeploy;
    @FXML
    private TextField sshLocationTextField;

    @FXML
    void createProject(ActionEvent event) {
        try {
            // 加载 projectWindow.fxml 文件
            FXMLLoader loader = new FXMLLoader(MkDocsHelperApplication.class.getResource("view/new-project-view.fxml"));
            Parent root = loader.load();

            // 创建一个新的 Stage
            Stage projectStage = new Stage();
            projectStage.setTitle("创建静态网站");

            NewProjectController newProjectController = loader.getController();
            newProjectController.setMainWindowController(this);
            newProjectController.setNewProjectStage(projectStage);


            // 创建 Scene，并将加载的布局设置为根节点
            Scene scene = new Scene(root);
            projectStage.setScene(scene);

            // 显示新窗口
            projectStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开项目
     */
    @FXML
    void openFileSelector(ActionEvent event) {
        // 打开文件选择器，进行项目选择
        String projectPath = JavaFxUtil.openDirSelector();  // D:\blog
        if (projectPath == null) {
            return;
        }
        // 存在项目路径到全局变量，如果第一次打开了错误的项目，也会执行下面这一步，但是执行正确的打开时会存储正确的路径到全局变量
        GlobalVariables.projectPath = projectPath;

        // 通过判断项目文件夹下是否存在mkdocs.yml文件来判断是否打开了正确的项目
        String mkdocsConfigFilePath = GlobalVariables.projectPath + File.separator + "mkdocs.yml";
        boolean fileExist = FileUtil.isFileExists(mkdocsConfigFilePath);

        // 打开错误的项目
        if (!fileExist) {
            setStatusLeftLabel("打开了错误的项目,正确的项目里面应该含有mkdocs.yml");
            return;
        }

        // 打开成功
        setStatusLeftLabel("静态网站项目打开成功");

        // 标记类型
        GlobalVariables.CREATED_PROJECT = false;

        // 读取网站类型信息并且存储到全局变量中
        // TODO 改为UTF-8
        GlobalVariables.WEB_CATEGORY = FileUtil.readFileAsString(GlobalVariables.projectPath + "/.helper/category.txt");

        //initListViewForMaterial();
        // 初始化配置列表试图
        initListView();

        // 如果打开成功，要将centerPane设置成引导页 TODO
        showGuideInMainWindowCenter();

        // 生成YAML对象
        generateJavaObject(mkdocsConfigFilePath);

        // 加载命令窗口的信息
        showCommandViewAndLoadItsInformation();
    }

    private static void generateJavaObject(String mkdocsConfigFilePath) {
        if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.MATERIAL)) {
            // 读取配置文件,获得对象
            Material material = (Material) SerializationUtil.yamlToJava(mkdocsConfigFilePath, Material.class);
            // 设置从YAML读取过来的对象的slugify属性
            LinkedHashMap linkedHashMap = (LinkedHashMap) material.getMarkdown_extensions().get(6);
            LinkedHashMap target = (LinkedHashMap) linkedHashMap.get("toc");
            target.put("slugify", "!!python/object/apply:pymdownx.slugs.slugify {}");
            // 设置单例对象为获得的对象
            Material.setConfigInstance(material);
            // 双向更新
            Material.getConfigInstance().getTheme().featuresToMap();
        } else if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.READTHEDOCS)) {
            // 读取配置文件,获得对象
            Readthedocs readthedocs = (Readthedocs) SerializationUtil.yamlToJava(mkdocsConfigFilePath, Readthedocs.class);
            // 设置单例对象为获得的对象
            Readthedocs.setConfigInstance(readthedocs);
        }
    }

    public void showCommandViewAndLoadItsInformation() {
        // 界面可见
        commandWindow.setVisible(true);

        // 隐藏部署后显示的链接
        hyperlinkAfterDeploy.setVisible(false);
        hyperlinkAfterAlreadyDeploy.setVisible(false);

        try {
            // 从配置文件加载sshLocation
            GithubProperties githubProperties = (GithubProperties) SerializationUtil.yamlToJava(GlobalVariables.projectPath + "/.helper/github.yml", GithubProperties.class);
            if (githubProperties != null) {
                String sshLocation = githubProperties.getSshLocation();
                sshLocationTextField.setText(sshLocation);
                hyperlinkAfterAlreadyDeploy.setVisible(true);

                if (githubProperties.getUser() != null) {
                    gitUserTextField.setText(githubProperties.getUser());
                }
                if (githubProperties.getEmail() != null) {
                    gitEmailField.setText(githubProperties.getEmail());
                }
            }
        } catch (Exception e) {

        }

    }

    /**
     * 在主窗口显示引导
     * <p>
     * 到了这一步之后，用户就可以参照指导，进行配置设置和命令执行了
     */
    private void showGuideInMainWindowCenter() {
        try {
            FXMLLoader loader = new FXMLLoader(MkDocsHelperApplication.class.getResource("view/material/center/guide-view.fxml"));
            Parent root = loader.load();
            centerPane.getChildren().setAll(root);
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }


    public void setStatusLeftLabel(String text) {
        statusLeftLabel.setText(text);
    }

    public void initialize() {
        commandWindow.setVisible(false);
    }

    public void setCreatingPageInCenter() {
        // 设置为引导界面
        FXMLLoader loader = new FXMLLoader(MkDocsHelperApplication.class.getResource("view/material/center/creating-view.fxml"));
        try {
            Parent root = loader.load();
            centerPane.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 本地运行
     * @param event
     */
    @FXML
    void localRun(ActionEvent event) {
        String suffix = "";
        // 获取用户填写的端口
        String port = portTextField.getText();
        // 端口不为空并且是合法数字，设置为用户填写端口
        if (!port.isEmpty()) {
            if (port.matches("^\\d+$")) {
                suffix = " -a localhost:" + port;
            } else {
                JavaFxUtil.warningAlert("警告", "端口必须是数字或者不填", "提醒：不填写端口，默认是8000端口");
                return;
            }
        }
        // 显示运行提示信息
        hyperlinkForLocalRun.setText("运行成功之后，可点此访问");
        // 用户没有填写端口，设置为默认端口
        if (port.isEmpty()) {
            port = "8000";
        }
        // 在浏览器打开预览网页
        BrowserUtil.openOnBrowser("http://localhost:" + port);

        // 构建一个bat语句组成的数组
        List<String> content = new ArrayList<>();
        content.add(CommandMarkConstant.CD_BLANK + GlobalVariables.projectPath);
        content.add("call venv/Scripts/activate");
        content.add("mkdocs serve" + suffix);
        String localRunPath = GlobalVariables.projectPath + "/.helper/localRun.bat";
        // 将运行脚本写入文件
        FileUtil.writeArrayToFile(content, localRunPath);

        // 运行“运行脚本”
        CommandUtil.executeBatFileShowWindow(localRunPath, (output) -> {
        });
    }

    /**
     * 部署
     * @param event
     */
    @FXML
    void deploy(ActionEvent event) {
        //获取ssh地址
        String sshLocation = sshLocationTextField.getText();
        // 判断ssh地址是否填写
        if (sshLocation.isEmpty()) {
            System.out.println("empty");
            JavaFxUtil.warningAlert("警告", "SSH地址没有填写", "请填写SSH地址").show();
            return;
        }
        // 判断ssh地址是不是github的ssh地址
        if (!GitHubUtils.isGitHubSSHAddress(sshLocation)) {
            JavaFxUtil.warningAlert("警告", "ssh不符合GitHub的SSH地址要求", "请仔细检查").show();
            return;
        }
        // 存储地址
        GithubProperties githubProperties = new GithubProperties(sshLocation, gitUserTextField.getText(), gitEmailField.getText());
        // 写入地址信息到github
        SerializationUtil.javaToYaml(githubProperties, GlobalVariables.projectPath + "/.helper/github.yml");
        // 网站地址
        String siteUrl = GitHubUtils.url(sshLocation);
        // 设置site_url到配置文件
        if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.MATERIAL)) {
            Material.getConfigInstance().setSite_url(siteUrl);
            Material.refreshConfigFile();
        } else if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.MKDOCS)) {
            MkDocs.getConfigInstance().setSite_url(siteUrl);
            MkDocs.refreshConfigFile();
        } else if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.READTHEDOCS)) {
            Readthedocs.getConfigInstance().setSite_url(siteUrl);
            Readthedocs.refreshConfigFile();
        }

        // 构建一个bat脚本数组
        List<String> content = new ArrayList<>();
        content.add(CommandMarkConstant.CD_BLANK + GlobalVariables.projectPath);
        content.add("call venv/Scripts/activate");
        // 如果用户填写用户名，添加用户名配置语句
        if (githubProperties.getUser() != null) {
            content.add("git config user.name " + githubProperties.getUser());
        }
        // 如果用户填写邮箱，添加邮箱配置语句
        if (githubProperties.getEmail() != null) {
            content.add("git config user.email " + githubProperties.getEmail());
        }
        content.add("git add *");
        content.add("git commit -m \"commit\"");
        content.add("git remote add origin " + sshLocation);
        content.add("git remote set-url origin " + sshLocation);
        content.add("git push -u origin main");
        content.add("mkdocs gh-deploy");
        content.add("exit");
        // 将bat数组写入bat脚本文件
        FileUtil.writeArrayToFile(content, GlobalVariables.projectPath + "/.helper/deploy.bat");
        // 运行并且提示
        CommandUtil.executeBatFileShowWindow(GlobalVariables.projectPath + "/.helper/deploy.bat", (output) -> {
            hyperlinkAfterDeploy.setVisible(true);
            hyperlinkAfterAlreadyDeploy.setVisible(false);
            hyperlinkAfterDeploy.setText("点击访问");
        });
    }

    public void initListView() {
        if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.MATERIAL)) {
            ObservableList<String> listItems = FXCollections.observableArrayList(SETTINGS_LIST_VIEW_FOR_MATERIAL.keySet());
            // 设置ListView的数据
            settngsListView.setItems(listItems);
            // 设置什么都没选中
            settngsListView.getSelectionModel().clearSelection();
            // 设置列表项样式
            settngsListView.setCellFactory(param -> {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                        } else {
                            // 设置字体大小和样式
                            setText(item);
                            setStyle("-fx-font-size: 15px;"); // 更改字体大小
                            // 如果需要，也可以在这里添加其他样式，比如颜色、背景等
                        }
                    }
                };
                return cell;
            });
            settngsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(MkDocsHelperApplication.class.getResource("view/material/center/" + SETTINGS_LIST_VIEW_FOR_MATERIAL.get(newVal)));
                    Parent root = loader.load();
                    centerPane.getChildren().setAll(root);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            });
        } else if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.MKDOCS)) {
            ObservableList<String> listItems = FXCollections.observableArrayList(SETTINGS_LIST_VIEW_FOR_MKDOCS.keySet());
            // 设置ListView的数据
            settngsListView.setItems(listItems);
            // 设置什么都没选中
            settngsListView.getSelectionModel().clearSelection();
            // 设置列表项样式
            settngsListView.setCellFactory(param -> {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                        } else {
                            // 设置字体大小和样式
                            setText(item);
                            setStyle("-fx-font-size: 15px;"); // 更改字体大小
                            // 如果需要，也可以在这里添加其他样式，比如颜色、背景等
                        }
                    }
                };
                return cell;
            });
            settngsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                try {
                    FXMLLoader loader =
                            new FXMLLoader(MkDocsHelperApplication.class.getResource("view/mkdocs/center/" + SETTINGS_LIST_VIEW_FOR_MKDOCS.get(newVal)));
                    Parent root = loader.load();
                    centerPane.getChildren().setAll(root);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            });
        } else if (GlobalVariables.WEB_CATEGORY.contains(WebCategoryConstant.READTHEDOCS)) {
            ObservableList<String> listItems = FXCollections.observableArrayList(SETTINGS_LIST_VIEW_FOR_READTHEDOCS.keySet());
            // 设置ListView的数据
            settngsListView.setItems(listItems);
            // 设置什么都没选中
            settngsListView.getSelectionModel().clearSelection();
            // 设置列表项样式
            settngsListView.setCellFactory(param -> {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                        } else {
                            // 设置字体大小和样式
                            setText(item);
                            setStyle("-fx-font-size: 15px;"); // 更改字体大小
                            // 如果需要，也可以在这里添加其他样式，比如颜色、背景等
                        }
                    }
                };
                return cell;
            });
            settngsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                try {
                    FXMLLoader loader =
                            new FXMLLoader(MkDocsHelperApplication.class.getResource("view/readthedocs/center/" + SETTINGS_LIST_VIEW_FOR_READTHEDOCS.get(newVal)));
                    Parent root = loader.load();
                    centerPane.getChildren().setAll(root);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            });
        }
    }


    @FXML
    void openBrowserForLocalRun(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                String port = portTextField.getText();
                if (!port.isEmpty()) {
                    if (port.matches("^\\d+$")) {
                        Desktop.getDesktop().browse(new URI("http://localhost:" + port));
                    } else {
                        JavaFxUtil.warningAlert("警告", "端口必须是数字或者不填", "提醒：不填写端口，默认是8000端口");
                        return;
                    }
                } else {
                    Desktop.getDesktop().browse(new URI("http://localhost:8000"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void openBrowserForDeploy(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                // 网站地址
                String siteUrl = GitHubUtils.url(sshLocationTextField.getText());
                Desktop.getDesktop().browse(new URI(siteUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void showHelpForCreateAEmptyRepository(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.CREATE_A_EMPTY_REPOSITORY);
    }

    @FXML
    void showHelpForReadyForGithub(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.READY_FOR_DEPLOY_IN_GITHUB);
    }


    /**
     * 展示Material示例
     * @param event
     */
    @FXML
    void showMaterailDemo(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.MATERIAL_DEMO_WEBSITE);
    }
    /**
     * 展示MkDocs示例
     * @param event
     */
    @FXML
    void showMkdocsDemo(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.MKDOCS_DEMO_WEBSITE);
    }
    /**
     * 展示Readthedocs示例
     * @param event
     */
    @FXML
    void showReadthedocsDemo(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.READTHEDOCS_DEMO_WEBSITE);
    }

    /**
     * 开发帮助网站
     * @param event
     */
    @FXML
    void showHelperWebSite(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.READY_FOR_DEPLOY_IN_GITHUB);
    }

}
