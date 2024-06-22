package com.star.mkdocshelper.utils;

import java.awt.*;
import java.net.URI;

public class BrowserUtil {
    public static void openOnBrowser(String url){
        if (Desktop.isDesktopSupported()) {
            try {
                // 网站地址
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
