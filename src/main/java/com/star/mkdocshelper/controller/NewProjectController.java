package com.star.mkdocshelper.controller;

import com.star.mkdocshelper.MkDocsHelperApplication;
import com.star.mkdocshelper.constant.HelpLinkConstant;
import com.star.mkdocshelper.constant.WebCategoryConstant;
import com.star.mkdocshelper.controller.material.MaterialController;
import com.star.mkdocshelper.controller.mkdocs.MkDocsController;
import com.star.mkdocshelper.controller.readthedocs.ReadthedocsController;
import com.star.mkdocshelper.utils.BrowserUtil;
import com.star.mkdocshelper.utils.FileUtil;
import com.star.mkdocshelper.utils.JavaFxUtil;
import com.star.mkdocshelper.variable.GlobalVariables;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class NewProjectController {
    public static final String TIP_PATH_PREFIX = "静态网站项目创建在：";

    public static final Map<String, String> categoryToPhotoMap = new LinkedHashMap<>();

    static {
        categoryToPhotoMap.put("material(丰富配置)", "material.png");
        categoryToPhotoMap.put("mkdocs(极简)", "mkdocs.png");
        categoryToPhotoMap.put("readthedocs(极简)", "readthedocs.png");
    }

    @Setter
    private Stage newProjectStage;
    @Setter
    private MainWindowController mainWindowController;

    @FXML
    private Button cancelCreateBtn;

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private Button createBtn;

    @FXML
    private ImageView imageViewSelected;

    @FXML
    private TextField locationTextField;

    @FXML
    private Button openDirSelectorBtn;

    @FXML
    private Label pathTipLabel;

    @FXML
    private TextField projectNameTextField;

    @FXML
    private TextField siteNameTextField;

    @FXML
    void cancelCreateProject(ActionEvent event) {
        newProjectStage.close();
    }

    @FXML
    void openDirSelector(ActionEvent event) {
        String s = JavaFxUtil.openDirSelector();
        if (s != null) {
            locationTextField.setText(s);
        }
    }


    /**
     * 创建项目
     * @param event
     */
    @FXML
    void createProject(ActionEvent event) {
        // 检验项目基本信息是否都填了，如果有没填的信息弹窗提醒
        if (projectNameTextField.getText().isEmpty() || siteNameTextField.getText().isEmpty() || locationTextField.getText().isEmpty()) {
            Alert alert = JavaFxUtil.warningAlert("警告", "有信息没填", "");
            // 显示对话框
            alert.showAndWait();
            return;
        }

        // 判断网站类型是否选择
        if (categoryListView.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = JavaFxUtil.warningAlert("警告", "没有选择网站类型", "");
            // 显示对话框
            alert.showAndWait();
            return;
        }

        // 判断要创建的文件夹是否已经存在，已经存在弹窗提醒
        if(FileUtil.isDirectoryExists(getProjectAllPath())){
            JavaFxUtil.warningAlert("警告","项目文件夹已经存在，请更换位置","").show();
            return;
        }

        // 设置中心窗口为“正在创建页面”
        mainWindowController.setCreatingPageInCenter();

        // 关闭创建项目的窗口
        newProjectStage.close();

        // 创建项目文件夹
        FileUtil.createDirectoryIfNotExists(getProjectAllPath());

        // 存储项目路径到全局变量
        GlobalVariables.projectPath = getProjectAllPath();

        // 在全局变量标记创建
        GlobalVariables.CREATED_PROJECT = true;

        // 根据不同的网站类型创建项目
        if (categoryListView.getSelectionModel().getSelectedItem().equals("material(丰富配置)")) {  // material(丰富配置)
            // 标记类型
            GlobalVariables.WEB_CATEGORY = WebCategoryConstant.MATERIAL;
            // 创建对应的对象
            MaterialController materialController = new MaterialController();
            // createMaterial
            materialController.createMaterial(siteNameTextField.getText(), mainWindowController);
        } else if (categoryListView.getSelectionModel().getSelectedItem().equals("mkdocs(极简)")) {   // mkdocs(极简)
            // 标记类型
            GlobalVariables.WEB_CATEGORY = WebCategoryConstant.MKDOCS;
            // 创建对应的对象
            MkDocsController mkDocsController = new MkDocsController();
            // createMkDocs
            mkDocsController.createMkDocs(siteNameTextField.getText(), mainWindowController);
        } else if (categoryListView.getSelectionModel().getSelectedItem().equals("readthedocs(极简)")) {  // readthedocs(极简)
            // 标记类型
            GlobalVariables.WEB_CATEGORY = WebCategoryConstant.READTHEDOCS;
            // 创建对应的对象
            ReadthedocsController readthedocsController = new ReadthedocsController();
            // createReadthedocs
            readthedocsController.createReadthedocs(siteNameTextField.getText(), mainWindowController);
        }
    }

    //private void createMaterial() {
    //    // 创建文件夹
    //    FileUtil.createDirectoryIfNotExists(getProjectAllPath());
    //
    //    // 存储项目路径到全局变量
    //    GlobalVariables.projectPath = getProjectAllPath();
    //
    //
    //    // 创建一个属于项目的配置文件夹
    //    FileUtil.createDirectoryIfNotExists(GlobalVariables.projectPath + "/.helper");
    //    // 写入静态网站类型文件
    //    // 创建通告栏需要的文件
    //    FileUtil.createFileWithContent(GlobalVariables.projectPath + "/.helper/announce.txt", "");
    //
    //
    //    // 构造一个命令执行bat
    //    List<String> createCommandList = new ArrayList<>();
    //    createCommandList.add("cd " + GlobalVariables.projectPath);
    //    createCommandList.addAll(CreateCommandListConstant.MATERIAL_WITHOUT_CD_COMMAND_IN_HEADER);
    //    // 写入到临时bat
    //    FileUtil.writeArrayToFile(createCommandList, GlobalVariables.projectPath + "/.helper/create.bat");
    //
    //    // 执行bat
    //    CommandUtil.executeBatFileShowWindow(GlobalVariables.projectPath + "/.helper/create.bat", (output) -> {
    //        // 在状态栏显示创建成功
    //        // 成功执行后的处理逻辑
    //        // 写入创建日志文件
    //        System.out.println(output);
    //
    //        MaterialCreatedAction();
    //
    //        // 刷新页面标记创建
    //
    //
    //        GlobalVariables.CREATED_PROJECT = true;
    //    });
    //}

    //public void MaterialCreatedAction() {
    //    // 假设创建成功
    //
    //    // 删除配置文件
    //    FileUtil.deleteFile(GlobalVariables.projectPath + "/mkdocs.yml");
    //
    //    // 创建Java对象
    //    // 这里全局单例的这个对象已经有了
    //    Material material = Material.getConfigInstance();
    //
    //    // 设置站点名字
    //    material.setSite_name(siteNameTextField.getText());
    //
    //    // 使用工具将Java对象转换成YAML文件
    //    String mkdocsConfigFilePath = GlobalVariables.projectPath + File.separator + "mkdocs.yml";
    //
    //    SerializationUtil.javaToYaml(material, mkdocsConfigFilePath);
    //
    //
    //    String imageDirPath = new File(GlobalVariables.projectPath, "docs/assets/images").getPath();
    //    String assetsDirPath = new File(GlobalVariables.projectPath, "docs/assets").getPath();
    //
    //    FileUtil.createDirectoryIfNotExists(imageDirPath);
    //
    //    // 复制默认图片(logo.png,favicon.png)到指定文件夹
    //    //FileUtil.copyResourceFileToFile("/com/star/mkdocshelper/images/favicon.png", imageDirPath);
    //    //FileUtil.copyResourceFileToFile("/com/star/mkdocshelper/images/logo.png", assetsDirPath);
    //
    //    InputStream in = MkDocsHelperApplication.class.getResourceAsStream("/com/star/mkdocshelper/images/favicon.png");
    //    try (OutputStream out = new FileOutputStream(imageDirPath + "/favicon.png")) {
    //        byte[] buffer = new byte[1024];
    //        int length;
    //        while ((length = in.read(buffer)) > 0) {
    //            out.write(buffer, 0, length);
    //        }
    //        //System.out.println("Image copied successfully.");
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //    in = MkDocsHelperApplication.class.getResourceAsStream("/com/star/mkdocshelper/images/logo.png");
    //    try (OutputStream out = new FileOutputStream(assetsDirPath + "/logo.png")) {
    //        byte[] buffer = new byte[1024];
    //        int length;
    //        while ((length = in.read(buffer)) > 0) {
    //            out.write(buffer, 0, length);
    //        }
    //        //System.out.println("Image copied successfully.");
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //
    //    // 创建公告栏需要的文件夹
    //    FileUtil.createDirectoryIfNotExists(GlobalVariables.projectPath + "/overrides");
    //
    //    // 读取配置信息
    //
    //    // 显示主页面，默认就是
    //
    //    // 初始化列表
    //    mainWindowController.initListViewForMaterial();
    //}

    public void initialize() {
        // 注册路径建议更新
        registerPathTipUpdate();

        // 初始化网站类型列表
        initCategoryListView();
    }

    private void initCategoryListView() {
        // 初始化内容
        ObservableList<String> listItems = FXCollections.observableArrayList(
                categoryToPhotoMap.keySet()
        );
        // 设置ListView的数据
        categoryListView.setItems(listItems);

        // 设置什么都没选中
        categoryListView.getSelectionModel().clearSelection();

        // 点击列表项事件
        categoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            Image image = new Image(Objects.requireNonNull(MkDocsHelperApplication.class.getResourceAsStream("images/category/" + categoryToPhotoMap.get(newVal))));
            imageViewSelected.setImage(image);
        });

    }

    private void registerPathTipUpdate() {
        locationTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // 当TextField的文本内容发生变化时，此方法会被调用
                pathTipLabel.setText(TIP_PATH_PREFIX + getProjectAllPath());
            }
        });
        projectNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // 当TextField的文本内容发生变化时，此方法会被调用
                pathTipLabel.setText(TIP_PATH_PREFIX + getProjectAllPath());
            }
        });
    }

    public String getProjectAllPath() {
        File file = new File(locationTextField.getText(), projectNameTextField.getText());
        return file.getPath();
    }

    @FXML
    void showHelpForConfigPython(ActionEvent event) {
        BrowserUtil.openOnBrowser(HelpLinkConstant.HOW_TO_CONFIG_PYTHON);
    }

}