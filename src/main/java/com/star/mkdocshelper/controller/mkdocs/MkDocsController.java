package com.star.mkdocshelper.controller.mkdocs;

import com.star.mkdocshelper.constant.CommandListConstant;
import com.star.mkdocshelper.constant.CommandMarkConstant;
import com.star.mkdocshelper.constant.WebCategoryConstant;
import com.star.mkdocshelper.controller.MainWindowController;
import com.star.mkdocshelper.properties.mkdocs.MkDocs;
import com.star.mkdocshelper.utils.CommandUtil;
import com.star.mkdocshelper.utils.FileUtil;
import com.star.mkdocshelper.utils.SerializationUtil;
import com.star.mkdocshelper.variable.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理mkdocs系列的操作
 */
public class MkDocsController {
    /**
     * 在项目文件夹里执行创建mkdocs系列的操作
     */
    public void createMkDocs(String siteName, MainWindowController mainWindowController) {
        // 初始化.helper文件夹
        initHelperFolder();

        // 创建脚本
        List<String> createCommandList = new ArrayList<>();
        createCommandList.add(CommandMarkConstant.CD_BLANK + GlobalVariables.projectPath);
        createCommandList.addAll(CommandListConstant.MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER);
        String batPath = GlobalVariables.projectPath + "/.helper/scripts/create.bat";
        // 写入到临时bat
        FileUtil.writeArrayToFile(createCommandList, batPath);
        // 执行脚本
        CommandUtil.executeBatFileShowWindow(batPath, (output) -> {
            // 在状态栏显示创建成功

            // 写入创建日志文件
            FileUtil.createFileWithContent(GlobalVariables.projectPath + "/.helper/log/createLog.txt", output);
            // 生成配置文件
            generateMKDocsConfigFile(siteName);
            // 刷新左侧列表
            mainWindowController.initListView();
            // 刷新命令窗口，加载信息
            mainWindowController.showCommandViewAndLoadItsInformation();

        });
    }

    private void generateMKDocsConfigFile(String siteName) {
        // 处理创建失败逻辑

        // 删除配置文件
        FileUtil.deleteFile(GlobalVariables.projectPath + "/mkdocs.yml");
        // 重置Java对象
        MkDocs.resetConfigInstance();
        // 创建Java对象
        MkDocs mkDocs = MkDocs.getConfigInstance();
        // 设置站点名字
        mkDocs.setSite_name(siteName);

        // 使用工具将Java对象转换成YAML文件
        SerializationUtil.javaToYaml(mkDocs, GlobalVariables.projectPath + "/mkdocs.yml");

    }

    /**
     * category.txt
     */
    private void initHelperFolder() {
        initHelperFolderStructure();
        /*
        在.helper文件夹创建属于这个网站类型的文件
         */

        // 创建.helper/category.txt
        FileUtil.createFileWithContent(GlobalVariables.projectPath + "/.helper/category.txt", WebCategoryConstant.MKDOCS);
    }

    private static void initHelperFolderStructure() {
        // 创建.helper文件夹
        FileUtil.createDirectoryIfNotExists(GlobalVariables.projectPath + "/.helper");
        // 创建.helper/scripts文件夹
        FileUtil.createDirectoryIfNotExists(GlobalVariables.projectPath + "/.helper/scripts");
        // 创建.helper/log文件夹
        FileUtil.createDirectoryIfNotExists(GlobalVariables.projectPath + "/.helper/log");


        // 创建.helper/github.yml文件，防止读时为空
        FileUtil.createFileWithContent(GlobalVariables.projectPath + "/.helper/github.yml", "");

        /**
         * 将下列命令写入.helper/gitStart.bat，然后执行，生成.git文件夹，并且进行第一次提交，再确保分支名字为main
         * git init
         * echo venv/ > .gitignore
         * echo /site >> .gitignore
         * git add *
         * git commit -m "first commit"
         * git branch -M main
         */
        String gitStartPath = GlobalVariables.projectPath + "/.helper/gitStart.bat";
        List<String> gitStartList = new ArrayList<>();
        gitStartList.add(CommandMarkConstant.CD_BLANK + GlobalVariables.projectPath);
        gitStartList.add("git init");
        gitStartList.add("echo venv/ > .gitignore");
        gitStartList.add("echo /site >> .gitignore");
        gitStartList.add("git add *");
        gitStartList.add("git commit -m \"first commit\"");
        gitStartList.add("git branch -M main");
        FileUtil.writeArrayToFile(gitStartList, gitStartPath);
        CommandUtil.executeBatFileSync(gitStartPath);
    }
}
