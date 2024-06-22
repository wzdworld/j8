package com.star.mkdocshelper.constant;

import java.util.ArrayList;
import java.util.List;

public class CommandListConstant {
    public static final List<String> MATERIAL_CREATE_WITHOUT_CD_COMMAND_IN_HEADER = new ArrayList<>();

    static {
        MATERIAL_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("python -m venv venv");
        MATERIAL_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("call venv/Scripts/activate");
        MATERIAL_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("pip install mkdocs-material" + DownloadConstant.suffix);
        MATERIAL_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("mkdocs new .");
        MATERIAL_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("exit");
    }

    public static final List<String> MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER = new ArrayList<>();

    static {
        MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("python -m venv venv");
        MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("call venv/Scripts/activate");
        MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("pip install mkdocs" + DownloadConstant.suffix);
        MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("mkdocs new .");
        MKDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("exit");
    }

    public static final List<String> READTHEDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER = new ArrayList<>();

    static {
        READTHEDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("python -m venv venv");
        READTHEDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("call venv/Scripts/activate");
        READTHEDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("pip install mkdocs" + DownloadConstant.suffix);
        READTHEDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("mkdocs new .");
        READTHEDOCS_CREATE_WITHOUT_CD_COMMAND_IN_HEADER.add("exit");
    }

}
