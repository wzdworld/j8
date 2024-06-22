package com.star.mkdocshelper.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * 命令执行工具类
 */
public class CommandUtil {
    public static String executeBatFileSync(String batFilePath) {
        StringBuilder output = new StringBuilder();
        try {
            // 构建命令
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", batFilePath);
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取进程的输出流
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            // 等待进程执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output.append("BAT文件执行失败，退出代码：").append(exitCode);
            } else {
                //output.append("BAT文件执行成功");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("发生异常：").append(e.getMessage());
        }

        return output.toString();
    }

    public static void executeBatFileAsy(String batFilePath)  {
        new Thread(() -> {
            StringBuilder output = new StringBuilder();
            try {
                // 构建命令
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", batFilePath);
                processBuilder.redirectErrorStream(true);

                // 启动进程
                Process process = processBuilder.start();

                // 读取进程的输出流
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append(System.lineSeparator());
                    }
                }

                // 等待进程执行完成
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    output.append("BAT文件执行失败，退出代码：").append(exitCode);
                } else {
                    //output.append("BAT文件执行成功");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                output.append("发生异常：").append(e.getMessage());
            }

            // 输出结果
            //System.out.println(output.toString());
        }).start();
    }

    /**
     * 执行本地BAT文件的方法
     *
     * @param batFilePath BAT文件的路径
     * @param onSuccess   成功执行后的处理方法
     */
    public static void executeBatFile(String batFilePath, Consumer<String> onSuccess) {
        new Thread(() -> {
            StringBuilder output = new StringBuilder();
            try {
                // 构建命令
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", batFilePath);
                processBuilder.redirectErrorStream(true);

                // 启动进程
                Process process = processBuilder.start();

                // 读取进程的输出流
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append(System.lineSeparator());
                    }
                }

                // 等待进程执行完成
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    output.append("BAT文件执行失败，退出代码：").append(exitCode);
                } else {
                    //output.append("BAT文件执行成功");
                    // 执行成功后的处理方法
                    //onSuccess.accept(output.toString());
                    onSuccess.accept(output.toString());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                output.append("发生异常：").append(e.getMessage());
            }

            // 输出结果
            System.out.println(output.toString());
        }).start();
    }

    public static String executeWindowsCommandInDirectory(String workingDir, String command) {
        // 使用ProcessBuilder创建进程，可以设置工作目录和命令及参数
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(workingDir)); // 设置工作目录
        processBuilder.command("cmd.exe", "/c", command); // 使用cmd.exe来执行命令

        // 启动进程
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 获取命令执行的输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();

        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            output.append(line).append("\n");
        }

        // 等待命令执行完成
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 关闭流
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 检查命令执行是否成功
        int exitCode = process.exitValue();
        if (exitCode == 0) {
            return output.toString();
        } else {
            try {
                throw new IOException("Command execution failed with exit code: " + exitCode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void executeBatFileShowWindow(String batFilePath, Consumer<String> onSuccess) {
        new Thread(() -> {
            StringBuilder output = new StringBuilder();
            try {
                // 构建命令
                String command = String.format("cmd.exe /c start cmd.exe /k \"%s\"", batFilePath);
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
                processBuilder.redirectErrorStream(true);

                // 启动进程
                Process process = processBuilder.start();

                // 读取进程的输出流
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append(System.lineSeparator());
                    }
                }

                // 等待进程执行完成
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    output.append("BAT文件执行失败，退出代码：").append(exitCode);
                } else {
                    //output.append("BAT文件执行成功");
                    // 执行成功后的处理方法
                    onSuccess.accept(output.toString());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                output.append("发生异常：").append(e.getMessage());
            }

            // 输出结果
            System.out.println(output.toString());
        }).start();
    }
}
