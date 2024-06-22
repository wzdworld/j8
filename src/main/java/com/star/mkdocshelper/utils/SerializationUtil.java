package com.star.mkdocshelper.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;

/**
 * 序列化工具
 */
public class SerializationUtil {

    public static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .enable(YAMLGenerator.Feature.SPLIT_LINES)
            .enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE)
            .configure(YAMLGenerator.Feature.INDENT_ARRAYS, true)
    );

    /**
     * 将java对象转换成yaml文件
     *
     * @param object   java对象
     * @param pathname yaml文件的路径
     * @return 是否转换成功
     */
    public static boolean javaToYaml(Object object, String pathname) {
        try {
            // 将对象转换为YAML文件并写入到文件中
            objectMapper.writeValue(new File(pathname), object);

            // 删除多余的双引号
            FileUtil.deleteAllDoubleQuotationInFile2(pathname);

            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public static Object yamlToJava(String pathname, Class<?> clazz) {
        try {
            // 从YAML文件中读取数据并将其转换为Java对象
            Object object = objectMapper.readValue(new File(pathname), clazz);
            return object;
        } catch (IOException e) {
            return null;
        }
    }
}
