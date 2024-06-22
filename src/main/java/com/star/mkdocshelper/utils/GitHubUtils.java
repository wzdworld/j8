package com.star.mkdocshelper.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 结构分析：
 * 1. 判断字符串是否合法
 * 2.
 */
public class GitHubUtils {
    public static boolean isStandardGithubSSHAddress(String githubSSHAddress) {
        // 定义匹配GitHub SSH地址的正则表达式
        String gitHubSshPattern = "^git@github\\.com:[a-zA-Z0-9_-]+/[a-zA-Z0-9_-]+\\.git$";
        Pattern pattern = Pattern.compile(gitHubSshPattern);
        Matcher matcher = pattern.matcher(githubSSHAddress);

        return matcher.matches();
    }

    public static boolean isCustomGithubSSHAddress(String githubSSHAddress) {
        //String patternString = "^git@[a-zA-Z0-9_-]+:\\w+/[a-zA-Z0-9_-]+\\.git$";
        String patternString = "^git@[a-zA-Z0-9_-]+:[a-zA-Z0-9_-]+/[a-zA-Z0-9_-]+\\.git$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(githubSSHAddress);
        return matcher.matches();
    }

    /**
     * 判断给定的字符串是否是 GitHub 的 SSH 地址
     *
     * @param address 要验证的字符串
     * @return 如果是 GitHub 的 SSH 地址，返回 true；否则返回 false
     */
    public static boolean isGitHubSSHAddress(String address) {
        if (isStandardGithubSSHAddress(address) || isCustomGithubSSHAddress(address)) {
            return true;
        }
        return false;
    }

    //public static String extractUsernameStandard(String gitSshUrl) throws IllegalArgumentException {
    //    // 正则表达式匹配并提取用户名和仓库名
    //    Pattern pattern = Pattern.compile("^git@github\\.com:(.+)/(\\w+\\.git)$");
    //    Matcher matcher = pattern.matcher(gitSshUrl);
    //    if (matcher.find()) {
    //        String username = matcher.group(1);
    //        String reponameWithGit = matcher.group(2);
    //        String reponame = reponameWithGit.substring(0, reponameWithGit.length() - 4); // 移除.git后缀
    //        System.out.println(username + " " + reponame);
    //        return "https://" + username + ".github.io/" + reponame + "/";
    //    } else {
    //        // 虽然理论上这里不会执行，因为之前已经验证了URL的有效性，但作为额外的保险措施
    //        throw new IllegalStateException("Failed to parse the GitHub SSH URL.");
    //    }
    //}
    //
    //public static String extractUsernameAndRepository(String gitVariableUrl) throws IllegalArgumentException {
    //    // 正则表达式模式，匹配任意字符串
    //    String pattern = "git@[^:]+:([^/]+)/(.+)\\.git";
    //
    //    // 创建一个 Pattern 对象
    //    Pattern r = Pattern.compile(pattern);
    //
    //    // 创建 matcher 对象
    //    Matcher m = r.matcher(gitVariableUrl);
    //
    //    if (m.find()) {
    //        String username = m.group(1);
    //        String repository = m.group(2);
    //        return "https://" + username + ".github.io/" + repository + "/";
    //    } else {
    //        return null;
    //    }
    //}

    public static String url(String sshAddress) {
        // 正则表达式模式，匹配任意字符串
        String pattern = "git@[^:]+:([^/]+)/(.+)\\.git";

        // 创建一个 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 创建 matcher 对象
        Matcher m = r.matcher(sshAddress);

        if (m.find()) {
            String username = m.group(1);
            String repository = m.group(2);
            return "https://" + username + ".github.io/" + repository + "/";
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        String url = url("git@github.com:IntronRewrite/Test-soft.git");
        System.out.println(url);
    }
}
