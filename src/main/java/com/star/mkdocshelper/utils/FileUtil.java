package com.star.mkdocshelper.utils;

import com.star.mkdocshelper.MkDocsHelperApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * 文件工具
 * TODO 删除多余的调试代码
 */
public class FileUtil {
    /**
     * 创建文件夹
     *
     * @param path 文件夹的完整路径
     */
    public static void createDirectoryIfNotExists(String path) {
        File directory = new File(path);

        // 检查文件夹是否存在，如果不存在则尝试创建
        if (!directory.exists()) {
            try {
                // mkdirs() 创建多级目录，mkdir() 只能创建一级目录
                directory.mkdirs();
            } catch (SecurityException se) {
                // 处理安全异常，例如没有足够的权限
                System.err.println("创建文件夹时出现安全异常：" + se.getMessage());
            }
        } else {
            // 文件夹已存在
            System.out.println("文件夹已存在：" + path);
        }
    }


    /**
     * 删除指定路径的文件。
     * <p>
     * 如果文件不存在，则不执行任何操作。
     *
     * @param filePath 文件的完整路径
     * @return 如果文件存在且删除成功则返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                return file.delete();
            } catch (SecurityException e) {
                System.out.println("没有足够的权限删除文件: " + filePath);
                return false;
            }
        } else {
            System.out.println("文件不存在: " + filePath);
            return false;
        }
    }

    /**
     * 删除文件中全部的双引号
     *
     * @param filePath
     * @throws FileNotFoundException
     */
    public static void deleteAllDoubleQuotationInFile(String filePath) {
        String tempFile = "D:/temp.yml"; // 临时文件路径

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // 初始化文件读取和写入流
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                // 删除行中的双引号并写入临时文件
                writer.write(line.replaceAll("\"", ""));
                writer.newLine(); // 写入换行符以保持格式
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.err.println("An error occurred while processing the file.");
        } finally {
            // 关闭文件流
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                //System.err.println("Error closing the streams.");
            }

            // 替换文件
            File original = new File(filePath);
            File temp = new File(tempFile);
            if (!original.delete()) {
                //System.err.println("Failed to delete original file.");
            } else if (!temp.renameTo(original)) {
                //System.err.println("Failed to rename temporary file to original file.");
            } else {
                //System.out.println("Quotes have been successfully removed from the file.");
            }
        }
    }

    /**
     * 将文件复制到指定目录，参数为字符串路径
     *
     * @param sourceStr    源文件路径字符串
     * @param targetDirStr 目标目录路径字符串
     * @return true 如果复制成功，否则返回false
     */
    public static boolean copyFileToDirectoryWithStringPaths(String sourceStr, String targetDirStr) {
        try {
            // 将字符串路径转换为Path对象
            Path sourcePath = Paths.get(sourceStr);
            Path targetDirectory = Paths.get(targetDirStr);

            // 获取源文件的名称
            String fileName = sourcePath.getFileName().toString();
            // 创建目标文件的完整路径
            Path targetPath = Paths.get(targetDirectory.toString(), fileName);

            // 执行复制操作，覆盖已存在的文件
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("复制图片成功");
            return true;
        } catch (IOException e) {
            // 处理文件复制过程中的IO异常
            System.err.println("Error copying file to directory: " + e.getMessage());
            return false;
        }
    }

    /**
     * 判断文件是否存在。
     *
     * @param filePath 文件的完整路径
     * @return 如果文件存在则返回true，否则返回false
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }


    /**
     * 创建文件并写入初始内容。
     * <p>
     * 如果文件已存在，则覆盖原有内容。
     * 如果文件的父目录不存在，则尝试创建父目录。
     *
     * @param filePath       文件的完整路径
     * @param initialContent 要写入文件的初始内容
     * @return 如果文件创建并写入成功则返回true，否则返回false
     */
    public static boolean createFileWithContent2(String filePath, String initialContent) {
        File file = new File(filePath);
        try {
            // 如果文件的父目录不存在，则创建父目录
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.out.println("Failed to create parent directories.");
                return false;
            }

            // 使用FileWriter和BufferedWriter写入内容，第二个参数true表示追加模式，这里设为false表示覆盖模式
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write(initialContent);
                //System.out.println("File created and content written: " + filePath);
                return true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file or writing content: " + e.getMessage());
            return false;
        }
    }


    // new
    public static boolean createFileWithContent(String filePath, String initialContent) {
        File file = new File(filePath);
        try {
            // 如果文件的父目录不存在，则创建父目录
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.out.println("Failed to create parent directories.");
                return false;
            }

            // 使用OutputStreamWriter指定UTF-8编码
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.write(initialContent);
                //System.out.println("File created and content written: " + filePath);
                return true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file or writing content: " + e.getMessage());
            return false;
        }
    }




    /**
     * 读取文件内容并返回一个字符串。
     *
     * @param filePath 文件的完整路径
     * @return 文件内容字符串，如果读取失败则返回null
     */
    public static String readFileAsString2(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("读取文件时发生错误: " + e.getMessage());
            return null;
        }
        return content.toString();
    }

    /**
     * 读取文件内容并返回一个字符串。
     *
     * @param filePath 文件的完整路径
     * @return 文件内容字符串，如果读取失败则返回null
     */
    public static String readFileAsString(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("读取文件时发生错误: " + e.getMessage());
            return null;
        }
        return content.toString();
    }

    /**
     * 将字符串数组的内容写入到指定的文件中，每行一个元素。
     */
    public static void writeArrayToFile2(List<String> content, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine(); // 在每个元素后添加换行符，以便下一次写入在新的一行
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeArrayToFile(List<String> content, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine(); // 在每个元素后添加换行符，以便下一次写入在新的一行
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 判断文件夹是否存在。
     *
     * @param directoryPath 文件夹的完整路径。
     * @return 如果文件夹存在返回true，否则返回false。
     */
    public static boolean isDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() && directory.isDirectory();
    }


    public static void deleteAllDoubleQuotationInFile2(String filePath) {
        String tempFile = "D:/temp.yml"; // 临时文件路径

        // 使用 try-with-resources 自动管理资源，并指定编码为 UTF-8
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // 删除行中的双引号并写入临时文件
                writer.write(line.replace("\"", ""));
                writer.newLine(); // 写入换行符以保持格式
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("处理文件时发生错误：" + e.getMessage());
        }

        // 替换文件
        File original = new File(filePath);
        File temp = new File(tempFile);

        if (!original.delete()) {
            System.err.println("删除原文件失败。");
        } else if (!temp.renameTo(original)) {
            System.err.println("重命名临时文件为原文件失败。");
        } else {
            System.out.println("文件中的引号已成功删除。");
        }
    }
}
