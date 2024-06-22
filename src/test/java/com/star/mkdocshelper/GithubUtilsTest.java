package com.star.mkdocshelper;

import com.star.mkdocshelper.utils.GitHubUtils;
import org.junit.jupiter.api.Test;

public class GithubUtilsTest {

    @Test
    public void testUrl() {
        // 正常测试
        System.out.println(GitHubUtils.url("git@" + "github.com:" + "username" + "/" + "repository" + ".git"));
        // 长度测试+下划线+字母+数字+短线
        System.out.println(GitHubUtils.url("git@" + "github.com:" + "username-wzd_wzd123" + "/" + "repo_si-toA75ry" + ".git"));

        // 正常测试（多账户）
        System.out.println(GitHubUtils.url("git@" + "githuHJb-c_om1:" + "username" + "/" + "repository" + ".git"));
        // 长度测试+下划线+字母+数字+短线（多账户）
        System.out.println(GitHubUtils.url("git@" + "git_huYT68-b-fwacom:" + "username_fawi-fwaWS" + "/" + "reposi_tory-_FWA132" + ".git"));
    }
}
